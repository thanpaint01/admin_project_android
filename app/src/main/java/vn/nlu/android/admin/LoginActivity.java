package vn.nlu.android.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.config.Server;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Toolbar mToolbar;
    EditText edtextUsername,edtextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = findViewById(R.id.toolbar);
        btnLogin = findViewById(R.id.btnLogin);
        //forToolbar
        mToolbar.setTitle("2DHP MOBILE");
        // init attribute from layout
        edtextUsername = findViewById(R.id.edtextUsername);
        edtextPassword = findViewById(R.id.edtextPassword);
        Application.setPreferences("data", "");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("User Php File Conection", Server.URLLogin + "=login");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLLogin + "=login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("error")) {
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

                        if (quyen > 1 && active == 1) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            Application.setPreferences("data", response);
                        }else {
                            Toast.makeText(getApplicationContext(), "Tài khoản không đủ quyền để truy cập", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error:" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("taikhoan", edtextUsername.getText().toString().trim());
                params.put("matkhau", edtextPassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}