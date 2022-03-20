package com.example.githubapirestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import model.GitHubUsers;
import okhttp3.HttpUrl;
import rest.ApiClient;
import rest.GithubUserEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;
import retrofit2.http.Url;

public class UserActivity extends AppCompatActivity {


    ImageView userImg;
    TextView login, usname, seguidores, siguiendo, email;
    Button rePositorios;

    Bundle extras;
    String newString;

    Bitmap miimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        login = findViewById(R.id.login);
        userImg = findViewById(R.id.userimg);
        usname = findViewById(R.id.et_username);
        seguidores = findViewById(R.id.seguidores);
        siguiendo = findViewById(R.id.siguiendo);
        email = findViewById(R.id.correo);
        rePositorios = findViewById(R.id.btn_repos);

        extras = getIntent().getExtras();
        newString = extras.getString("userName_String");

        rePositorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(UserActivity.this, Repositories.class);
                    intent.putExtra("username", newString);
                    startActivity(intent);
            }
        });

        loadData();

    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    private void loadData(){
        final GithubUserEndPoint apiService  = ApiClient.getClient().create(GithubUserEndPoint.class);

        Call<GitHubUsers> call = apiService.getUser(newString);
        call.enqueue(new Callback<GitHubUsers>() {
            @Override
            public void onResponse(Call<GitHubUsers> call, Response<GitHubUsers> response) {
                ImageDownloader task  = new ImageDownloader();

                try {
                    miimg  = task.execute(response.body().getUserImg()).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                userImg.setImageBitmap(miimg);
                userImg.getLayoutParams().height = 220;
                userImg.getLayoutParams().width = 220;


                if (response.body().getUsname() == null){
                    usname.setText("No hay nombre ");
                }else{
                    usname.setText("Nombre de usuario: "+ response.body().getUsname());
                }

                seguidores.setText(("Seguidores: "+ response.body().getSeguidores()));
                siguiendo.setText(("Siguiendo: "+ response.body().getSiguiendo()));
                login.setText(("Seguidores: "+ response.body().getLogin()));

                if(response.body().getEmail() == null){
                    email.setText(("No email"));
                }else{
                    email.setText("Email: "+ response.body().getEmail());
                }

            }

            @Override
            public void onFailure(Call<GitHubUsers> call, Throwable t) {
                System.out.println("Failed!" + t.toString());
            }
        });
    }
}