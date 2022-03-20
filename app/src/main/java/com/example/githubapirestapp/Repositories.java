package com.example.githubapirestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ReposAdapter;
import model.GitHubRepo;
import model.GitHubUsers;
import rest.ApiClient;
import rest.GitHubRepoEndPoint;
import rest.GithubUserEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories extends AppCompatActivity {

    String recievedUserName;
    TextView userNametv;
    RecyclerView mRecyclerView;
    List<GitHubRepo> mDataSource = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        Bundle extras = getIntent().getExtras();
        recievedUserName = extras.getString("username");

        userNametv = findViewById(R.id.tv_userName);
        userNametv.setText("Usuario: "+ recievedUserName);

        mRecyclerView = findViewById(R.id.repo_recycl_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //se necesita un adapter

        mAdapter = new ReposAdapter(mDataSource, R.layout.list_item_repo, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        loadRepositories();
    }

    private void loadRepositories(){
        GitHubRepoEndPoint  apiService = ApiClient.getClient().create(GitHubRepoEndPoint.class);
        try {
            Call<List<GitHubRepo>> call = apiService.getRepo(recievedUserName);
            call.enqueue(new Callback<List<GitHubRepo>>() {
                @Override
                public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                    mDataSource.clear();
                    mDataSource.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                    Log.d("Repos", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("FALLO0 ", e.toString());
        }


    }
}