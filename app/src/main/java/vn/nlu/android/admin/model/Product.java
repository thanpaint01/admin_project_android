package vn.nlu.android.admin.model;

public class Product {

    private int idsp;
    private String img;
    private String tensp;
    private String idhang;
    private String tenhang;
    private String ram;
    private String rom;
    private String pin;
    private int sale;
    private double gia;
    private int soluong;
    private int soluongInCart;
    private int idram;
    private int idrom;
    private int idpin;
    private int baohanh;
    private int idkm;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
    private String kichthuoc;
    private String cameraTruoc;
    private String cameraSau;
    private int active;
    private int tinhtrang;
    private boolean expandable;
    private String tieude1;
    private String tieude2;
    private String tieude3;
    private String chitiet1;
    private String chitiet2;
    private String chitiet3;
    private String anh1;
    private String anh2;
    private String anh3;



    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
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

    public String getIdhang() {
        return idhang;
    }

    public void setIdhang(String idhang) {
        this.idhang = idhang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getSoluongInCart() {
        return soluongInCart;
    }

    public void setSoluongInCart(int soluongInCart) {
        this.soluongInCart = soluongInCart;
    }

    public int getIdram() {
        return idram;
    }

    public void setIdram(int idram) {
        this.idram = idram;
    }

    public int getIdrom() {
        return idrom;
    }

    public void setIdrom(int idrom) {
        this.idrom = idrom;
    }

    public int getIdpin() {
        return idpin;
    }

    public void setIdpin(int idpin) {
        this.idpin = idpin;
    }

    public int getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(int baohanh) {
        this.baohanh = baohanh;
    }

    public int getIdkm() {
        return idkm;
    }

    public void setIdkm(int idkm) {
        this.idkm = idkm;
    }

    public String getImg01() {
        return img01;
    }

    public void setImg01(String img01) {
        this.img01 = img01;
    }

    public String getImg02() {
        return img02;
    }

    public void setImg02(String img02) {
        this.img02 = img02;
    }

    public String getImg03() {
        return img03;
    }

    public void setImg03(String img03) {
        this.img03 = img03;
    }

    public String getImg04() {
        return img04;
    }

    public void setImg04(String img04) {
        this.img04 = img04;
    }

    public String getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(String kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
    }

    public String getCameraSau() {
        return cameraSau;
    }

    public void setCameraSau(String cameraSau) {
        this.cameraSau = cameraSau;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(int tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getTieude1() {
        return tieude1;
    }

    public String getTieude2() {
        return tieude2;
    }

    public String getTieude3() {
        return tieude3;
    }

    public String getChitiet1() {
        return chitiet1;
    }

    public String getChitiet2() {
        return chitiet2;
    }

    public String getChitiet3() {
        return chitiet3;
    }

    public String getAnh1() {
        return anh1;
    }

    public String getAnh2() {
        return anh2;
    }

    public String getAnh3() {
        return anh3;
    }

    public Product(int idsp, String img, String tensp, String tenhang, String sale, String gia, String soluong,
                   String ram, String rom, String pin, String baohanh, String img01, String img02,
                   String img03, String img04, String kichthuoc, String cameraTruoc, String cameraSau, int active, int tinhtrang,
                   String tieude1,String tieude2,String tieude3,String chitiet1,String chitiet2,String chitiet3,String anh1,String anh2,String anh3) {
        this.idsp = idsp;
        this.img = img;
        this.tensp = tensp;
        this.tenhang = tenhang;
        this.sale = Integer.parseInt(sale);
        this.gia = Double.parseDouble(gia);
        this.soluong = Integer.parseInt(soluong);
        this.ram = ram;
        this.rom = rom;
        this.pin = pin;
        this.baohanh = Integer.parseInt(baohanh);
        this.img01 = img01;
        this.img02 = img02;
        this.img03 = img03;
        this.img04 = img04;
        this.kichthuoc = kichthuoc;
        this.cameraTruoc = cameraTruoc;
        this.cameraSau = cameraSau;
        this.active = active;
        this.tinhtrang = tinhtrang;
        this.tieude1 = tieude1;
        this.tieude2 = tieude2;
        this.tieude3 = tieude3;
        this.chitiet1 = chitiet1;
        this.chitiet2 = chitiet3;
        this.chitiet3 = chitiet3;
        this.anh1 = anh1;
        this.anh2 = anh2;
        this.anh3 = anh3;
        this.expandable = false;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
