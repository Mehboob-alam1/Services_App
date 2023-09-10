package com.example.mazdoorapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mazdoorapp.ActiveOrderAdapter;
import com.example.mazdoorapp.R;
import com.example.mazdoorapp.UserInfoModel;
import com.example.mazdoorapp.databinding.FragmentActiveBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ActiveFragment extends Fragment {

private FragmentActiveBinding binding;
private ArrayList<UserInfoModel> list;
private ActiveOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding=FragmentActiveBinding.inflate(inflater,container,false);
//        return inflater.inflate(R.layout.fragment_active, container, false);


list= new ArrayList<>();




fetchActiveRide();






     return    binding.getRoot();

    }

    private void fetchActiveRide() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProviderOrder");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserInfoModel rides = snapshot.getValue(UserInfoModel.class);



         binding.noData.setVisibility(View.GONE);

                        list.add(rides);




                    adapter = new ActiveOrderAdapter(requireContext(),list);
                    binding.activeriderRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    binding.activeriderRec.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.activeriderRec.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //binding.noData.getRoot().setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.VISIBLE);
                binding.activeriderRec.setVisibility(View.GONE);
                Toast.makeText(requireContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            binding.activeriderRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.activeriderRec.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}