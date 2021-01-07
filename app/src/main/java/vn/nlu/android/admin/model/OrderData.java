package vn.nlu.android.admin.model;

public class OrderData {
    private int idchitiet;
    private int iddonhang;
    private String idsp,soluong,tamtinh,giamgia,tongcong,gia,img,tensp;

    public OrderData(int idchitiet, int iddonhang, String idsp, String soluong, String tamtinh, String giamgia, String tongcong, String gia,String img,String tensp) {
        this.idchitiet = idchitiet;
        this.iddonhang = iddonhang;
        this.idsp = idsp;
        this.soluong = soluong;
        this.tamtinh = tamtinh;
        this.giamgia = giamgia;
        this.tongcong = tongcong;
        this.gia = gia;
        this.img = img;
        this.tensp = tensp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getIdchitiet() {
        return idchitiet;
    }

    public void setIdchitiet(int idchitiet) {
        this.idchitiet = idchitiet;
    }

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTamtinh() {
        return tamtinh;
    }

    public void setTamtinh(String tamtinh) {
        this.tamtinh = tamtinh;
    }

    public String getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(String giamgia) {
        this.giamgia = giamgia;
    }

    public String getTongcong() {
        return tongcong;
    }

    public void setTongcong(String tongcong) {
        this.tongcong = tongcong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "idchitiet=" + idchitiet +
                ", iddonhang=" + iddonhang +
                ", idsp='" + idsp + '\'' +
                ", soluong='" + soluong + '\'' +
                ", tamtinh='" + tamtinh + '\'' +
                ", giamgia='" + giamgia + '\'' +
                ", tongcong='" + tongcong + '\'' +
                ", gia='" + gia + '\'' +
                '}' +"\n";
    }
}
