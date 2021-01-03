package vn.nlu.android.admin.Activity.user;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Add extends AppCompatActivity {
    EditText addFullName, addDay, addMonth, addYear,addImgurl, addPhoneNo, addEmailAddress, addAddress, addUsername, addPassword, addConfirmPassword;
    Button btn_adduser;
    RadioButton rdoMale, rdoFemale, rdoActive, rdoBan, rdoAdmin, rdoUser;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useradd);

        //init attribute of layout
        addFullName = findViewById(R.id.addFullName);
        addDay = findViewById(R.id.addDay);
        addMonth = findViewById(R.id.addMonth);
        addYear = findViewById(R.id.addYear);
        addPhoneNo = findViewById(R.id.addPhoneNo);
        addImgurl = findViewById(R.id.addImgurl);
        addEmailAddress = findViewById(R.id.addEmailAddress);
        addAddress = findViewById(R.id.addAddress);
        addUsername = findViewById(R.id.addUsername);
        addPassword = findViewById(R.id.addPassword);
        addConfirmPassword = findViewById(R.id.addConfirmPassword);
        btn_adduser = findViewById(R.id.btn_adduser);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);
        rdoActive = findViewById(R.id.rdoActive);
        rdoBan = findViewById(R.id.rdoBan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);

        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    add();
                    finish();
                }
            }
        });
    }

    boolean isEditTextEmpty(EditText text) {
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
        String year = addYear.getText().toString();
        String month = addMonth.getText().toString();
        String day = addDay.getText().toString();

        if (isEditTextEmpty(addFullName) || addFullName.getText().toString() == "^[a-zA-Z\\\\s]+") {
            addFullName.requestFocus();
            addFullName.setError("Full Name not VALID");
            return false;
        }
        if (isEditTextEmpty(addDay) || isEditTextEmpty(addMonth) || isEditTextEmpty(addYear)) {
            addYear.requestFocus();
            addYear.setError("Date not EMPTY");
            return false;
        }

        if (!isDate(day + "/" + month + "/" + year) || Integer.parseInt(year) > Calendar.getInstance().get(Calendar.YEAR)) {
            addYear.requestFocus();
            addYear.setError("Date not VALID");
            return false;
        }

        if (isEditTextEmpty(addPhoneNo)) {
            addPhoneNo.requestFocus();
            addPhoneNo.setError("Phone not EMPTY");
            return false;
        }

        if (isEditTextEmpty(addPassword)) {
            addPassword.requestFocus();
            addPassword.setError("Password not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addConfirmPassword)) {
            addConfirmPassword.requestFocus();
            addConfirmPassword.setError("Confirm Password not EMPTY");
            return false;
        }
        if (isEmail(addEmailAddress) == false) {
            addEmailAddress.requestFocus();
            addEmailAddress.setError("Email not VALID");
            return false;
        }

        if (!addConfirmPassword.getText().toString().equals(addPassword.getText().toString())) {
            addConfirmPassword.requestFocus();
            addConfirmPassword.setError("Confirm not match");
            return false;
        }
        return true;
    }

    private void add() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.addrow + "nguoidung", new Response.Listener<String>() {
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
                params.put("ten", addFullName.getText().toString().trim());
                String year = addYear.getText().toString();
                String month = addMonth.getText().toString();
                String day = addDay.getText().toString();
                String dob = year + "-" + month + "-" + day;
                params.put("ngaysinh", dob);
                params.put("gioitinh", rdoMale.isChecked() ? "Nam" : "Nu");
                params.put("sdt", addPhoneNo.getText().toString());
                params.put("email", addEmailAddress.getText().toString());
                params.put("diachi", addAddress.getText().toString());
                params.put("taikhoan", addUsername.getText().toString());
                params.put("matkhau", addPassword.getText().toString());
                params.put("img", addImgurl.getText().toString());
                params.put("quyen", rdoAdmin.isChecked() ? "2" : "1");
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }
}