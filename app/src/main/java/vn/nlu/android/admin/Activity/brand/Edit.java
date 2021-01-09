package vn.nlu.android.admin.Activity.brand;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Edit extends AppCompatActivity {
    EditText brandnamedata, brandimgdata;
    Button btn_editbrand;
    RadioButton rdoActive, rdoDisable;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandedit);

        //init attribute of layout
        brandnamedata = findViewById(R.id.brandnamedata);
        brandimgdata = findViewById(R.id.brandimgdata);

        btn_editbrand = findViewById(R.id.btn_editbrand);

        rdoActive = findViewById(R.id.rdoActive);
        rdoDisable = findViewById(R.id.rdoDisable);

        // set default
        Intent i = getIntent();
        b = i.getBundleExtra("data");

        brandnamedata.setText(b.getString("name"));
        brandimgdata.setText(b.getString("img"));
        if (b.getString("active").equals("1")) {
            rdoActive.setChecked(true);
        } else rdoDisable.setChecked(true);

        btn_editbrand.setOnClickListener(new View.OnClickListener() {
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
    private boolean checkValid() {
        if (isEditTextEmpty(brandnamedata)) {
            brandnamedata.requestFocus();
            brandnamedata.setError("Field can not EMPTY");
            return false;
        }
        if (isEditTextEmpty(brandimgdata)) {
            brandimgdata.requestFocus();
            brandimgdata.setError("Field can not EMPTY");
            return false;
        }
        return true;
    }

    private void add() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + "hang", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Add Brand Success", Toast.LENGTH_LONG).show();
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
                params.put("tenhang", brandnamedata.getText().toString().trim());
                params.put("logohang", brandimgdata.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("id","" +b.getInt("id"));
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }
}