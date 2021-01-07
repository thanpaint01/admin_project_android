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
    EditText addstorage;
    RadioButton rdoActive, rdoInactive;
    Button btn_addtag;
    int tag;
    String unit = "GB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagadd);

        //init attribute of layout
        addtag = findViewById(R.id.addtag);
        addstorage = findViewById((R.id.addstorage));
        btn_addtag = findViewById(R.id.btn_addtag);
        rdoActive = findViewById(R.id.rdoActive);

        //spinner init
        setdataTag();

        btn_addtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()){
                    addTag();
                }
            }
        });
    }

    boolean isEditTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEditTextPositiveNumber(EditText text){
        String num = text.getText().toString();
        if (Integer.parseInt(num) > 0  )
            return true;
        return false;
    }

    public boolean checkValid(){
        if (isEditTextEmpty(addstorage)) {
            addstorage.requestFocus();
            addstorage.setError("Storage is empty");
            return false;
        }
        if (!isEditTextPositiveNumber(addstorage)){
            addstorage.requestFocus();
            addstorage.setError("Must be a positive number");
            return false;
        }
        return true;
    }

    private void addTag() {
        String tagName = addtag.getSelectedItem().toString().toLowerCase();
        if (tagName.equals("Battery")) {
            tagName = "pin";
            unit = "mAh";
        } else if (tagName.equals("Price")) {
            tagName = "gia";
            unit = "triệu";
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
                params.put("dungluong", addstorage.getText().toString().trim() + " " + unit);
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
                tag = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                tag = 0;
            }
        });
    }
}
