package humber.college.homies;

import java.util.Comparator;

public class House {

    public String name;
    public String pos;
    public String img;
    public String phone;
    public Boolean bookmarked;

    //needed for java beans and firebase getting/setting to read/write objects
    public House(){
    }
    public House(String name, String pos, String img, String phone, Boolean bookmarked){
        this.name=name;
        this.pos=pos;
        this.img=img;
        this.phone=phone;
        this.bookmarked=bookmarked;
    }

    public static Comparator<House>HouseNameAZ = new Comparator<House>() {
        @Override
        public int compare(House h1, House h2) {
            if(Integer.parseInt(h1.getPos())<Integer.parseInt(h2.getPos())){
                return -1;
            }else if(Integer.parseInt(h1.getPos())>Integer.parseInt(h2.getPos())){
                return 1;
            }else
            return 0;
        }
    };

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

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean  bookmarked) {
        this.bookmarked = bookmarked;
    }


}
