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

    public Product(int idsp, String img, String tensp, int idkm, int tinhtrang, double gia) {
        this.idsp = idsp;
        this.img = img;
        this.tensp = tensp;
        this.idkm = idkm;
        this.tinhtrang = tinhtrang;
        this.gia = gia;
    }

    public Product(int idsp, String img, String tensp, double gia) {
        this.idsp = idsp;
        this.img = img;
        this.tensp = tensp;
        this.gia = gia;
    }

    public Product(int idsp, String img, String tensp, double gia, int active, int tinhtrang) {
        this.idsp = idsp;
        this.img = img;
        this.tensp = tensp;
        this.gia = gia;
        this.active = active;
        this.tinhtrang = tinhtrang;
    }

    public Product(int idsp, String img, String tensp, String tenhang, String sale, String gia, String soluong,
                   String ram, String rom, String pin, String baohanh, String img01, String img02,
                   String img03, String img04, String kichthuoc, String cameraTruoc, String cameraSau, int active, int tinhtrang) {
        this.idsp = idsp;
        this.img = img;
        this.tensp = tensp;
        this.tenhang = tenhang;
        this.sale = Integer.parseInt(sale);
        this.gia = Double.parseDouble(gia);
        this.soluong = Integer.parseInt(sale);
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
