import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    private static ArrayList<User> userinfo = new ArrayList<>();

    //constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void storeUserInfo(User user){
        userinfo.add(user);
    }

    //check if the user has already logged in or not, to be considered for the first purchase discount
    public boolean checkUser(String username) {
        for (User value : userinfo) {
            if (value.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}

