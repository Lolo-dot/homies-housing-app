package humber.college.homies;


public class UnitTest {
    public boolean validation_isCorrect(String username) {
        //Checks if a if a username is empty or not
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
        //Checks if passwords are equal
        if(!confirmedPassword.equals(password)) {
            return false;
        }
        return true;
    }

    public boolean validation_isCorrect4(String phoneNumber){
        //Checks if phone number contains a +
        if(phoneNumber.contains("+")) {
            return false;
        }
        return true;
    }
}
