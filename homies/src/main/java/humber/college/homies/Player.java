package humber.college.homies;

public class Player {

    public String name;
    public String pos;
    public String img;
    public String phone;

    public Player(){

    }
    public Player(String name, String pos, String img, String phone){
        this.name=name;
        this.pos=pos;
        this.img=img;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
