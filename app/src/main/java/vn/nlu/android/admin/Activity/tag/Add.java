package vn.nlu.android.admin.Activity.tag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class Add extends AppCompatActivity {
    Spinner addtag;
    EditText addstorage, addTu, addDen;
    RadioButton rdoActive, rdoInactive;
    Button btn_addtag;
    int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagadd);

        //init attribute of layout
        addtag = findViewById(R.id.addtag);
        addstorage = findViewById((R.id.addstorage));
        btn_addtag = findViewById(R.id.btn_addtag);
        rdoActive = findViewById(R.id.rdoActive);
        addTu = findViewById(R.id.addTu);
        addDen = findViewById(R.id.addDen);

        //spinner init
        setdataTag();

        btn_addtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    addTag();
                    finish();
                }
            }
        });
    }

    boolean isEditTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    public boolean checkValid() {
        if (!addtag.getSelectedItem().toString().equals("Price")) {
            if (isEditTextEmpty(addstorage)) {
                addstorage.requestFocus();
                addstorage.setError("Storage is empty");
                return false;
            }
        }else {
            if (isEditTextEmpty(addTu) || Integer.parseInt(addTu.getText().toString()) <=0 ){
                 addTu.requestFocus();
                addTu.setError("Must be a number > 0");
                return false;
                }else if (isEditTextEmpty(addDen)|| Integer.parseInt(addDen.getText().toString()) <=0){
                     addDen.requestFocus();
                addDen.setError("Must be a number > 0");
                return false;
                }
        }
        return true;
    }


    private void addTag() {
        String tagName = addtag.getSelectedItem().toString().toLowerCase();
        if (tagName.equalsIgnoreCase("Battery")) {
            tagName = "pin";
        } else if (tagName.equalsIgnoreCase("Price")) {
            tagName = "gia";
        } else if (tagName.equalsIgnoreCase("Rom")) {
            tagName = "rom";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.addrow + tagName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Add Tag Success", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (!addtag.getSelectedItem().toString().equals("Price")) {
                    params.put("dungluong", addstorage.getText().toString().trim());
                } else {
                    params.put("dungluong", addTu.getText().toString().trim() + " - " + addDen.getText().toString().trim() + " Triệu");
                }
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    private void setdataTag() {
        String[] item = new String[4];
        item[0] = "Ram";
        item[1] = "Rom";
        item[2] = "Battery";
        item[3] = "Price";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addtag.setAdapter(adapter);

        addtag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //View EditText for price
                if ("Price".equals(addtag.getSelectedItem().toString())) {
                    addTu.setVisibility(View.VISIBLE);
                    addDen.setVisibility(View.VISIBLE);
                    addstorage.setVisibility(View.GONE);
                } else {
                    addTu.setVisibility(View.GONE);
                    addDen.setVisibility(View.GONE);
                    addstorage.setVisibility(View.VISIBLE);
                }
                tag = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                tag = 0;
            }
        });
    }
}
