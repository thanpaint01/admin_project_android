

package vn.nlu.android.admin.ui.model;

import android.os.Build;

import androidx.annotation.RequiresApi;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class User {
    private int iduser;
    private String taikhoan;
    private String ten;
    private String img;
    private String sdt;
    private String diachi;
    private String email;
    private String gioitinh;
    private String ngaysinh;
    private int quyen;
    private int active;

    public User() {
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
}

