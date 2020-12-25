package vn.nlu.android.admin.model;

public class Brand {
    private int id;
    private String nameOfBrand;
    private String img;
    private int active;

    public Brand(int id, String nameOfBrand, String img, int active) {
        this.id = id;
        this.nameOfBrand = nameOfBrand;
        this.img = img;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfBrand() {
        return nameOfBrand;
    }

    public void setNameOfBrand(String nameOfBrand) {
        this.nameOfBrand = nameOfBrand;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
