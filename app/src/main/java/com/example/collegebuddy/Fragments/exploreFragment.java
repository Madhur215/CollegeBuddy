package com.example.collegebuddy.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.Local.Entity.ExploreEntity;
import com.example.collegebuddy.R;
import com.example.collegebuddy.ViewModel.ExploreViewModel;
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

//    private ArrayList<members> membersList;
    private List<ExploreEntity> membersList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ProgressBar members_progress_bar;
    private TextView college_name_explore_fragment;
    private ExploreViewModel exploreViewModel;
    private membersListAdapter membersAdapter;
    private RecyclerView memberRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_fragment, container, false);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        if(!isConnected(getContext())){
            buildDialog(getActivity()).show();
        }

        else {
            setAdapter();
            getMembers();
            jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
            pr = new prefUtils(getContext());
            members_progress_bar = view.findViewById(R.id.members_progress_bar);
            college_name_explore_fragment = view.findViewById(R.id.college_name_explore_fragment);
            college_name_explore_fragment.setText(MainActivity.pres.getCollege());
            members_progress_bar.setVisibility(View.VISIBLE);

//            getMembers();
        }
        return view;
    }

//    private void getMembers() {
//
//        HashMap<String , String> sendToken =  pr.getUserDetails();
//        String token = sendToken.get(prefUtils.KEY_TOKEN);
//
//        Call<List<members>> call = jsonApiHolder.getMembers(token);
//
//        call.enqueue(new Callback<List<members>>() {
//            @Override
//            public void onResponse(Call<List<members>> call, Response<List<members>> response) {
//                members_progress_bar.setVisibility(View.GONE);
//                if(response.isSuccessful()){
//                    try{
//                        setAdapter();
//                        List<members> studentsList = response.body();
//
//                        for(members student : studentsList) {
//
//                            String name = student.getMember_name();
//                            String branch = student.getMember_branch();
//                            String year = student.getMember_year();
//
//                            members addMember = new members(name, branch, year);
//                            membersList.add(addMember);
//
//                        }
//                    }
//                    catch (NullPointerException e){
//                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
//                    }
//                }
//                else{
//                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<members>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void getMembers(){
        exploreViewModel = ViewModelProviders.of(exploreFragment.this).get(ExploreViewModel.class);
        exploreViewModel.getMembersList().observe(getViewLifecycleOwner() ,
                new Observer<List<ExploreEntity>>() {
                @Override
                    public void onChanged(List<ExploreEntity> exploreEntities) {
                        membersAdapter = new membersListAdapter(exploreEntities , getContext());
                        memberRecyclerView.setAdapter(membersAdapter);
                    }
                });
    }

    private void setAdapter() {
        try {
            memberRecyclerView = getView().findViewById(R.id.members_list_recycler_view);
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            memberRecyclerView.setHasFixedSize(true);
            membersList = new ArrayList<>();
            membersListAdapter mAdapter = new membersListAdapter(membersList , getContext());
            memberRecyclerView.setAdapter(mAdapter);
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER");
        }
    }

    private boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Dismiss.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                dialog.cancel();
            }
        });

        return builder;
    }



}
