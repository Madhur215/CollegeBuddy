package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.members;
import com.example.collegebuddy.Adapters.membersListAdapter;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class exploreFragment extends Fragment {

    private ArrayList<members> membersList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ProgressBar members_progress_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.explore_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        members_progress_bar = getView().findViewById(R.id.members_progress_bar);
        members_progress_bar.setVisibility(View.VISIBLE);
        getMembers();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getMembers() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<members>> call = jsonApiHolder.getMembers(token);

        call.enqueue(new Callback<List<members>>() {
            @Override
            public void onResponse(Call<List<members>> call, Response<List<members>> response) {
                members_progress_bar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    try{
                        setAdapter();
                        List<members> studentsList = response.body();

                        for(members student : studentsList) {

                            String name = student.getMember_name();
                            String branch = student.getMember_branch();
                            String year = student.getMember_year();

                            members addMember = new members(name, branch, year);
                            membersList.add(addMember);

                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
                    }
                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<members>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter() {
        try {
            RecyclerView memberRecyclerView = getView().findViewById(R.id.members_list_recycler_view);
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            memberRecyclerView.setHasFixedSize(true);
            membersList = new ArrayList<>();
            membersListAdapter mAdapter = new membersListAdapter(membersList);
            memberRecyclerView.setAdapter(mAdapter);
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER");
        }
    }
}
