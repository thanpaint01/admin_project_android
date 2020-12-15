package vn.nlu.android.admin.ui.model;

public class Slide {
    private int id;
    private String resouceImg;
    private int active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResouceImg(String resouceImg) {
        this.resouceImg = resouceImg;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getResouceImg() {
        return resouceImg;
    }

    public Slide(int id, String resouceImg, int active) {
        this.id = id;
        this.resouceImg = resouceImg;
        this.active = active;
    }
}
