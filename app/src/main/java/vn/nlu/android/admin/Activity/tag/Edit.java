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
    EditText edtstorage;
    RadioButton rdoActive, rdoInactive;
    Button btn_edttag;
    int tag;
    Bundle b;
    String tagName = edttag.getSelectedItem().toString().toLowerCase();

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


        Intent i = getIntent();
        b = i.getBundleExtra("data");
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
                }
            }
        });
    }

    boolean isEditTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isEditTextPositiveNumber(EditText text) {
        String num = text.getText().toString();
        if (Integer.parseInt(num) > 0)
            return true;
        return false;
    }

    public boolean checkValid() {
        if (isEditTextEmpty(edtstorage)) {
            edtstorage.requestFocus();
            edtstorage.setError("Must not empty");
            return false;
        }
//        if (!tagName.equalsIgnoreCase("Price")) {
//            if (!isEditTextPositiveNumber(edtstorage)) {
//                edtstorage.requestFocus();
//                edtstorage.setError("Must be a positive number");
//                return false;
//            }
//        }
        return true;
    }

    private void editTag() {
        if (tagName.equals("Battery")) {
            tagName = "pin";
        } else if (tagName.equals("Price")) {
            tagName = "gia";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + tagName, new Response.Listener<String>() {
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
                params.put("dungluong", edtstorage.getText().toString());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("id", "" + b.getInt("id"));
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
        edttag.setAdapter(adapter);
        int position = adapter.getPosition(""+b.getInt("tag"));
        edttag.setSelection(position);
        edttag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                tag = position;
            }
        });
    }

}
