package vn.nlu.android.admin.model;

public class Comment {
    private int id;
    private int iduser;
    private String productname;
    private String comment;
    private int active;
    private boolean expandable;

    public Comment(int id, int iduser, String productname, String comment, int active) {
        this.id = id;
        this.iduser = iduser;
        this.productname = productname;
        this.comment = comment;
        this.active = active;
        this.expandable = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
