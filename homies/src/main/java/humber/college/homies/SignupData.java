package humber.college.homies;
//Team Name: Homies
public class SignupData {
    public String username;
    public String password;
    public String email;

    public SignupData(){

    }

    public SignupData(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {return password;}
}
