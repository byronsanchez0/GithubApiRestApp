package rest;

import model.GitHubUsers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
//esto es una interface
public interface GithubUserEndPoint {

    @GET("/user/{users}")
    Call<GitHubUsers> getUser(@Path("user") String user);
}
