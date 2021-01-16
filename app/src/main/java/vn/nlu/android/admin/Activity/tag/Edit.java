package vn.nlu.android.admin.Activity.tag;

import android.content.Intent;
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

public class Edit extends AppCompatActivity {
    Spinner edttag;
    EditText edtstorage, edtTu, edtDen;
    RadioButton rdoActive, rdoInactive;
    Button btn_edttag;
    int tag;
    Bundle b;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagedit);

        //init attribute of layout
        //init attribute of layout
        edttag = findViewById(R.id.edttag);
        edtstorage = findViewById((R.id.edtstorage));
        btn_edttag = findViewById(R.id.btn_edttag);
        rdoActive = findViewById(R.id.rdoActive);
        rdoInactive = findViewById(R.id.rdoInactive);
        edtTu = findViewById(R.id.edtTu);
        edtDen = findViewById(R.id.edtDen);


        i = getIntent();
        b = i.getBundleExtra("data");
        if (!b.getString("tag").equals("Price")) {
            edtstorage.setText(b.getString("storage"));
        } else {
            String[] arrPrice = (b.getString("storage")).split(" ");
            edtTu.setText(arrPrice[0]);
            edtDen.setText(arrPrice[2]);
        }
        if (b.getInt("active") == 1) {
            rdoActive.setChecked(true);
            rdoInactive.setChecked(false);
        } else {
            rdoInactive.setChecked(true);
            rdoActive.setChecked(false);
        }
        //spinner init
        setdataTag();

        edtstorage.setText((b.getString("storage")).trim());
        if (b.getInt("active") == 1) {
            rdoActive.setChecked(true);
        } else rdoInactive.setChecked(true);


        btn_edttag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    editTag();
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
        if (!edttag.getSelectedItem().toString().equals("Price")) {
            if (isEditTextEmpty(edtstorage)) {
                edtstorage.requestFocus();
                edtstorage.setError("Storage is empty");
                return false;
            }
        }else {
            if (isEditTextEmpty(edtTu) || Integer.parseInt(edtTu.getText().toString()) <=0 ){
                 edtTu.requestFocus();
                edtTu.setError("Must be a number > 0");
                return false;
                }else if (isEditTextEmpty(edtDen)|| Integer.parseInt(edtDen.getText().toString()) <=0){
                     edtDen.requestFocus();
                edtDen.setError("Must be a number > 0");
                return false;
                }
        }
        return true;
    }
    
    private void editTag() {
        String tagName = edttag.getSelectedItem().toString().toLowerCase();
        if (tagName.equalsIgnoreCase("Battery")) {
            tagName = "pin";
        } else if (tagName.equalsIgnoreCase("Price")) {
            tagName = "gia";
        } else if (tagName.equalsIgnoreCase("Rom")) {
            tagName = "rom";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + tagName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Edit Tag Success", Toast.LENGTH_LONG).show();
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
                if (!b.getString("tag").equals("Price")) {
                    params.put("dungluong", edtstorage.getText().toString());
                } else{
                    params.put("dungluong", edtTu.getText().toString().trim() + " - " + edtDen.getText().toString().trim() + " Triệu");
                }
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("id", "" + b.getInt("id"));
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    private void setdataTag() {
        String[] item = new String[1];
        item[0] = b.getString("tag");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        edttag.setAdapter(adapter);
        int position = adapter.getPosition("" + b.getString("tag"));
        edttag.setSelection(position);
        edttag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ("Price".equals(edttag.getSelectedItem().toString())) {
                    edtTu.setVisibility(View.VISIBLE);
                    edtDen.setVisibility(View.VISIBLE);
                    edtstorage.setVisibility(View.GONE);
                } else {
                    edtTu.setVisibility(View.GONE);
                    edtDen.setVisibility(View.GONE);
                    edtstorage.setVisibility(View.VISIBLE);
                }
                tag = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
