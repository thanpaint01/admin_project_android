package vn.nlu.android.admin.model;

public class Sale {
    private int id;
    private String sale;
    private String ngaybdkm;
    private String ngayktkm;
    private int active;
    private boolean expandable;

    public Sale(int id, String sale, String ngaybdkm, String ngayktkm, int active) {
        this.id = id;
        this.sale = sale;
        this.ngaybdkm = ngaybdkm;
        this.ngayktkm = ngayktkm;
        this.active = active;
        expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getNgaybdkm() {
        return ngaybdkm;
    }

    public void setNgaybdkm(String ngaybdkm) {
        this.ngaybdkm = ngaybdkm;
    }

    public String getNgayktkm() {
        return ngayktkm;
    }

    public void setNgayktkm(String ngayktkm) {
        this.ngayktkm = ngayktkm;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
