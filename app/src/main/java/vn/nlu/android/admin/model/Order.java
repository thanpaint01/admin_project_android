package vn.nlu.android.admin.model;

import java.util.ArrayList;

public class Order {
    private int iddonhang;
    private String iduser;
    private String idtinhtrang;
    private String ngaytao;
    private String diachi;
    private String hinhthuctt;
    private String ten;
    private String sdt;
    private String img;
    private boolean expandable;
    private ArrayList<OrderData> data;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Order(int iddonhang, String iduser, String idtinhtrang, String ngaytao, String diachi, String hinhthuctt, String ten, String sdt, String img) {
        this.iddonhang = iddonhang;
        this.iduser = iduser;
        this.idtinhtrang = idtinhtrang;
        this.ngaytao = ngaytao;
        this.diachi = diachi;
        this.img = img;
        this.hinhthuctt = hinhthuctt;
        this.ten = ten;
        this.sdt = sdt;
        this.expandable = false;
        this.data = new ArrayList<OrderData>();
    }

    public ArrayList<OrderData> getData() {
        return data;
    }

    public void setData(ArrayList<OrderData> data) {
        this.data = data;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public String getIduser() {
        return iduser;
    }

    public String getIdtinhtrang() {
        return idtinhtrang;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getHinhthuctt() {
        return hinhthuctt;
    }

    public String getTen() {
        return ten;
    }

    public String getSdt() {
        return sdt;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public void setIdtinhtrang(String idtinhtrang) {
        this.idtinhtrang = idtinhtrang;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setHinhthuctt(String hinhthuctt) {
        this.hinhthuctt = hinhthuctt;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "iddonhang=" + iddonhang +
                ", iduser='" + iduser + '\'' +
                ", idtinhtrang='" + idtinhtrang + '\'' +
                ", ngaytao='" + ngaytao + '\'' +
                ", diachi='" + diachi + '\'' +
                ", hinhthuctt='" + hinhthuctt + '\'' +
                ", ten='" + ten + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}
