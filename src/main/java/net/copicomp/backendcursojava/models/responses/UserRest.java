package net.copicomp.backendcursojava.models.responses;

import java.util.List;

public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<PostRest> posts;


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public List<PostRest> getPosts() {
        return this.posts;
    }

    public void setPosts(List<PostRest> posts) {
        this.posts = posts;
    }
    



}
