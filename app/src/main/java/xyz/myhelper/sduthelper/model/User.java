package xyz.myhelper.sduthelper.model;

/**
 * Created by dream on 15-11-19.
 */
public class User {
    private int id;
    private String userXh;
    private String userPsw;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserXh() {
        return userXh;
    }

    public void setUserXh(String userXh) {
        this.userXh = userXh;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }
}
