

package vn.nlu.android.admin.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import vn.nlu.android.admin.Application;
import vn.nlu.android.admin.config.Server;

public class User {
    private int iduser;
    private String taikhoan;
    private String ten;
    private String img;
    private String sdt;
    private String diachi;
    private String matkhau;
    private String email;
    private String gioitinh;
    private String ngaysinh;
    private int quyen;
    private int active;
    private boolean expandable;


    public User() {
    }

    public User(int iduser, String taikhoan,String matkhau, String ten, String img, String sdt, String diachi, String email, String gioitinh, String ngaysinh, int quyen, int active) {
        this.iduser = iduser;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.ten = ten;
        this.img = img;
        this.sdt = sdt;
        this.diachi = diachi;
        this.email = email;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.quyen = quyen;
        this.active = active;
        this.expandable = false;
    }

    public User(int iduser, String taikhoan, String ten, String img, String sdt, String diachi, String email, String gioitinh, String ngaysinh, int quyen, int active) {
        this.iduser = iduser;
        this.taikhoan = taikhoan;
        this.ten = ten;
        this.img = img;
        this.sdt = sdt;
        this.diachi = diachi;
        this.email = email;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.quyen = quyen;
        this.active = active;
    }

    public boolean isExpandable() { return expandable; }

    public void setExpandable(boolean expandable) { this.expandable = expandable; }

    public String getMatkhau() { return matkhau; }

    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "iduser : " + iduser +
                " taikhoan : " + taikhoan +
                " ten : " + ten +
                " img : " + img +
                " sdt : " + sdt +
                " diachi : " + diachi +
                " email : " + email +
                " gioitinh : " + gioitinh +
                " ngaysinh : " + ngaysinh +
                " quyen : " + quyen +
                " active : " + active;
    }
    public String removeHostImg(){
        return img.replace(Server.HOST,"");
    }

    public static User exportUser(String response) {
        User user = null;
        try {
            JSONArray jsonArr = new JSONArray(response);

            JSONObject jsonObject = jsonArr.getJSONObject(0);
            int iduser = jsonObject.getInt("iduser");
            String taikhoan = jsonObject.getString("taikhoan");
            String ten = jsonObject.getString("ten");
            String img = jsonObject.getString("img");
            String sdt = jsonObject.getString("sdt");
            String diachi = jsonObject.getString("diachi");
            String email = jsonObject.getString("email");
            String gioitinh = jsonObject.getString("gioitinh");
            String ngaysinh = jsonObject.getString("ngaysinh");
            int quyen = jsonObject.getInt("quyen");
            int active = jsonObject.getInt("active");
            user = new User(iduser, taikhoan, ten, img, sdt, diachi, email, gioitinh, ngaysinh, quyen, active);
            Application.setPreferences("data", response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

