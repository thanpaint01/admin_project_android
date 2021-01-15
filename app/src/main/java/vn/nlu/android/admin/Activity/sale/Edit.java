package vn.nlu.android.admin.Activity.sale;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Edit extends AppCompatActivity {
    EditText salenamedata, salengaybdkmdata,salengayktkmdata;
    Button btn_editsale;
    RadioButton rdoActive, rdoDisable;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleedit);

        //init attribute of layout
        salenamedata = findViewById(R.id.salenamedata);
        salengaybdkmdata = findViewById(R.id.salengaybdkmdata);
        salengayktkmdata = findViewById(R.id.salengayktkmdata);

        btn_editsale = findViewById(R.id.btn_editsale);

        rdoActive = findViewById(R.id.rdoActive);
        rdoDisable = findViewById(R.id.rdoDisable);

        // set default
        Intent i = getIntent();
        b = i.getBundleExtra("data");

        salenamedata.setText(b.getString("sale"));
        salengaybdkmdata.setText(b.getString("ngaybdkm"));
        salengayktkmdata.setText(b.getString("ngayktkm"));
        if (b.getInt("active") == 1) {
            rdoActive.setChecked(true);
        } else rdoDisable.setChecked(true);

        btn_editsale.setOnClickListener(new View.OnClickListener() {
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
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    private boolean checkValid() {
        if (isEditTextEmpty(salenamedata)) {
            salenamedata.requestFocus();
            salenamedata.setError("Field can not EMPTY");
            return false;
        }
        if (isEditTextEmpty(salengaybdkmdata)) {
            salengaybdkmdata.requestFocus();
            salengaybdkmdata.setError("Field can not EMPTY");
            return false;
        }
        if (isEditTextEmpty(salengayktkmdata)) {
            salengayktkmdata.requestFocus();
            salengayktkmdata.setError("Field can not EMPTY");
            return false;
        }
        if (!isDate(salengaybdkmdata.getText().toString())) {
            salengaybdkmdata.requestFocus();
            salengaybdkmdata.setError("Field not VALID");
            return false;
        }
        if (!isDate(salengayktkmdata.getText().toString())) {
            salengayktkmdata.requestFocus();
            salengayktkmdata.setError("Field not VALID");
            return false;
        }
        return true;
    }

    private void add() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + "khuyenmai", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Edit Sale Success", Toast.LENGTH_LONG).show();
                }else Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sale", salenamedata.getText().toString().trim());
                params.put("ngaybdkm", salengaybdkmdata.getText().toString().trim());
                params.put("ngayktkm", salengayktkmdata.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("id","" +b.getInt("id"));
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }
}