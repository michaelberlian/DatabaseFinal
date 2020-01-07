package sample;

public class Username {
    String Username,Grants;

    public Username(String username, String grants) {
        Username = username;
        Grants = grants;
    }

    public Username(String username) {
        Username = username;
    }

    public void setGrants(String grants) {
        Grants = grants;
    }

    public String getUsername() {
        return Username;
    }

    public String getGrants() {
        return Grants;
    }


}
