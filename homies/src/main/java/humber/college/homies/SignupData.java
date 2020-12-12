package humber.college.homies;
//Team Name: Homies

import java.util.ArrayList;

public class SignupData {
    public String username;
    public String password;
    public String email;
    public ArrayList<House> userHouses;
    public ArrayList<House> userBookmarkedHouses;

    public SignupData(String username, String password, String email, ArrayList<House> userHouses, ArrayList<House> userBookmarkedHouses){
        this.username = username;
        this.password = password;
        this.email = email;
        this.userHouses= userHouses;
        this.userBookmarkedHouses = userBookmarkedHouses;
    }
}
