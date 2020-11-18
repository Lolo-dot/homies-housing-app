package humber.college.homies;

public class SignupData {
    public String username;
    public String password;
    public String email;
    public String phone;

    public SignupData(){

    }

    public SignupData(String username, String password, String email, String phone){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;;
    }

    public String getPassword() {return password;}
}
