package rest;


import retrofit2.Retrofit;

public class ApiClient {

    public static final String BASE_url = "https://api.github.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_url)
                    .build();
        }
        return retrofit;
    }
}
