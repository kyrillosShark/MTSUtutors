package com.example.myapplication;

public class User {
    private String username;
    private String email;
    private String profilePicture;

    public User()
    {

    }

    public User(String username,String profilePicture,String email)  {
        this.username = username;
        this.email=email;
        this.profilePicture=profilePicture;

    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getProfilePicture() {
        return profilePicture;
    }



    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }


}
