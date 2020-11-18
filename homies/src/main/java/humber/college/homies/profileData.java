package humber.college.homies;

public class profileData {//username age phonenumber roomates descirption
    public String username;
    public String age;
    public String phoneNumber;
    public String roomMates;
    public String description;

    public profileData(){

    }

    public profileData(String username, String age, String phoneNumber, String roomMates, String description){
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.roomMates = roomMates;
        this.description = description;
    }

    public String getUsername() {return username;}
    public String getAge() {return age;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getRoomMates() {return roomMates;}
    public String getDescription() {return description;}
}
