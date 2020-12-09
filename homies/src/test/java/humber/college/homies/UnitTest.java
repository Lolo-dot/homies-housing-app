package humber.college.homies;


public class UnitTest {
    public boolean validation_isCorrect(String username) {
        if (username.length() == 0) {
            return false;
        }
        return true;
    }

    public boolean validation_isCorrect2(String password){
        //Checks if password length is greater than 6
        if(password.length() < 6) {
            return false;
        }
        return true;
    }

    public boolean validation_isCorrect3(String password, String confirmedPassword){
        //Checks if password length is greater than 6
        if(!confirmedPassword.equals(password)) {
            return false;
        }
        return true;
    }
}
