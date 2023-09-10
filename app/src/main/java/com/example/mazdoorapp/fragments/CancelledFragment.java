package com.example.mazdoorapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mazdoorapp.CancelledAdapter;
import com.example.mazdoorapp.CompletedAdapter;
import com.example.mazdoorapp.R;
import com.example.mazdoorapp.UserInfoModel;
import com.example.mazdoorapp.databinding.FragmentCancelledBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CancelledFragment extends Fragment {

    private FragmentCancelledBinding binding;
      private ArrayList<UserInfoModel> list;
      private CancelledAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentCancelledBinding.inflate(inflater, container, false);
//        return inflater.inflate(R.layout.fragment_cancelled, container, false);
list= new ArrayList<>();
fetchCancelled();
      return   binding.getRoot();
    }


    private void fetchCancelled() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ProviderCancelled");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.noData.setVisibility(View.GONE);



                    for (DataSnapshot snap : snapshot.getChildren()) {

                        UserInfoModel rides = snap.getValue(UserInfoModel.class);
                        list.add(rides);
                    }




                    adapter = new CancelledAdapter(requireContext(), list);
                    binding.CancelledRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    binding.CancelledRec.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.CancelledRec.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //binding.noData.getRoot().setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.VISIBLE);
                binding.CancelledRec.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            binding.CancelledRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.CancelledRec.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}