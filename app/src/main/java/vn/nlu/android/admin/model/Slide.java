package vn.nlu.android.admin.model;

import vn.nlu.android.admin.config.Server;

public class Slide {
    private int id;
    private String resouceImg;
    private String removehost;
    private int active;
    private boolean expandable;

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
        this.expandable = false;
        this.removehost = resouceImg.replace(Server.HOST,"");
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getRemovehost() {
        return removehost;
    }
}
