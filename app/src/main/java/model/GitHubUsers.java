package model;

import com.google.gson.annotations.SerializedName;

public class GitHubUsers {
    @SerializedName("login")
    private String login;

    @SerializedName("usname")
    private String usname;

    @SerializedName("seguidores")
    private String seguidores;

    @SerializedName("siguiendo")
    private String siguiendo;

    @SerializedName("userImg")
    private String userImg;

    @SerializedName("email")
    private String email;


    public GitHubUsers(String login, String usname, String seguidores, String siguiendo, String userImg, String email) {
        this.login = login;
        this.usname = usname;
        this.seguidores = seguidores;
        this.siguiendo = siguiendo;
        this.userImg = userImg;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(String seguidores) {
        this.seguidores = seguidores;
    }

    public String getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(String siguiendo) {
        this.siguiendo = siguiendo;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
