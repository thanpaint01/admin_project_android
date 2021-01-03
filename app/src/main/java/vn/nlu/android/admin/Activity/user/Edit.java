package vn.nlu.android.admin.Activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Edit extends AppCompatActivity {
    EditText edtFullName, edtDay, edtMonth, edtYear,edtImgurl, edtPhoneNo, edtEmailAddress, edtAddress, edtUsername, edtPassword, edtConfirmPassword;
    Button btn_edituser;
    RadioButton rdoMale, rdoFemale, rdoActive, rdoBan, rdoAdmin, rdoUser;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);

        //init attribute of layout
        edtFullName = findViewById(R.id.edtFullName);
        edtDay = findViewById(R.id.edtDay);
        edtMonth = findViewById(R.id.edtMonth);
        edtYear = findViewById(R.id.edtYear);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtImgurl = findViewById(R.id.edtImgurl);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtAddress = findViewById(R.id.edtAddress);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btn_edituser = findViewById(R.id.btn_edituser);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);
        rdoActive = findViewById(R.id.rdoActive);
        rdoBan = findViewById(R.id.rdoBan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        // set default
        Intent i = getIntent();
        b = i.getBundleExtra("data");

        edtFullName.setText(b.getString("name"));
        edtPhoneNo.setText(b.getString("phone"));
        edtEmailAddress.setText(b.getString("mail"));
        edtAddress.setText(b.getString("address"));
        edtUsername.setText(b.getString("username"));
        edtImgurl.setText(b.getString("imgurl"));
        if (b.getString("gender").equals("Nam")) {
            rdoMale.setChecked(true);
        } else rdoFemale.setChecked(true);

        if (b.getInt("permission") == 1) {
            rdoUser.setChecked(true);
        } else rdoAdmin.setChecked(true);

        if (b.getInt("active") == 1) {
            rdoActive.setChecked(true);
        } else rdoBan.setChecked(true);


        String date = b.getString("dob");
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        edtDay.setText(day);
        edtMonth.setText(month);
        edtYear.setText(year);


        btn_edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    edit();
//                    String year = edtYear.getText().toString();
//                    String month = edtMonth.getText().toString();
//                    String day = edtDay.getText().toString();
//
//                    Application.setPreferences("edit-name",edtFullName.getText().toString());
//                    Application.setPreferences("edit-email",edtFullName.getText().toString());
//                    Application.setPreferences("edit-phone",edtFullName.getText().toString());
//                    Application.setPreferences("edit-address",edtFullName.getText().toString());
//                    Application.setPreferences("edit-username",edtFullName.getText().toString());
//                    Application.setPreferences("edit-password",edtFullName.getText().toString());
//                    Application.setPreferences("edit-birthday",year + "-" + month + "-" + day);
//                    Application.setPreferences("edit-gender",edtFullName.getText().toString());
//                    Application.setPreferences("edit-permisson",edtFullName.getText().toString());
//                    Application.setPreferences("edit-active",edtFullName.getText().toString());
                    finish();
                }
            }
        });
    }

    boolean isEdtTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean isDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean checkValid() {
        String year = edtYear.getText().toString();
        String month = edtMonth.getText().toString();
        String day = edtDay.getText().toString();

        if (isEdtTextEmpty(edtFullName) || edtFullName.getText().toString() == "^[a-zA-Z\\\\s]+") {
            edtFullName.requestFocus();
            edtFullName.setError("Full Name not VALID");
            return false;
        }
        if (isEdtTextEmpty(edtDay) || isEdtTextEmpty(edtMonth) || isEdtTextEmpty(edtYear)) {
            edtYear.requestFocus();
            edtYear.setError("Date not EMPTY");
            return false;
        }

        if (!isDate(day + "/" + month + "/" + year) || Integer.parseInt(year) > Calendar.getInstance().get(Calendar.YEAR)) {
            edtYear.requestFocus();
            edtYear.setError("Date not VALID");
            return false;
        }

        if (isEdtTextEmpty(edtPhoneNo)) {
            edtPhoneNo.requestFocus();
            edtPhoneNo.setError("Phone not EMPTY");
            return false;
        }

        if (isEdtTextEmpty(edtPassword)) {
            edtPassword.requestFocus();
            edtPassword.setError("Password not EMPTY");
            return false;
        }
        if (isEdtTextEmpty(edtConfirmPassword)) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError("Confirm Password not EMPTY");
            return false;
        }
        if (isEmail(edtEmailAddress) == false) {
            edtEmailAddress.requestFocus();
            edtEmailAddress.setError("Email not VALID");
            return false;
        }

        if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError("Confirm not match");
            return false;
        }
        return true;
    }

    private void edit() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + "nguoidung", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ten", edtFullName.getText().toString().trim());
                String year = edtYear.getText().toString();
                String month = edtMonth.getText().toString();
                String day = edtDay.getText().toString();
                String dob = year + "-" + month + "-" + day;
                params.put("ngaysinh", dob);
                params.put("gioitinh", rdoMale.isChecked() ? "Nam" : "Nu");
                params.put("sdt", edtPhoneNo.getText().toString());
                params.put("email", edtEmailAddress.getText().toString());
                params.put("diachi", edtAddress.getText().toString());
                params.put("taikhoan", edtUsername.getText().toString());
                params.put("matkhau", edtPassword.getText().toString());
                params.put("img", edtImgurl.getText().toString());
                params.put("quyen", rdoAdmin.isChecked() ? "2" : "1");
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("iduser","" +b.getInt("iduser"));
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }
}