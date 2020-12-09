package humber.college.homies;
//Team Name: Homies
public class ProfileData {//username age phonenumber roomates descirption
    public String username;
    public String age;
    public String phoneNumber;
    public String roomMates;
    public String description;
    public String profilepicture;

    public ProfileData(){

    }

    public ProfileData(String username, String age, String phoneNumber, String roomMates, String description, String profilepicture){
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.roomMates = roomMates;
        this.description = description;
        this.profilepicture = profilepicture;
    }

    public String getUsername() {return username;}
    public String getAge() {return age;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getRoomMates() {return roomMates;}
    public String getDescription() {return description;}
    public String getProfilepicture() {return profilepicture;}
}
