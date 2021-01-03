package vn.nlu.android.admin.Activity.product;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Brand;
import vn.nlu.android.admin.model.Product;
import vn.nlu.android.admin.model.Sale;
import vn.nlu.android.admin.model.Tag;

public class Add extends AppCompatActivity {
    EditText addname, addimg, addimg1, addimg2, addimg3, addimg4, addprice,addstorage, addguarantee, addscreen, addfrontcam, addrearcam,
            addtitle1, addtitle2, addtitle3, addimgdes1, addimgdes2, addimgdes3, adddetail1, adddetail2, adddetail3;
    Spinner addbrand, addsale, addram, addrom, addbattery, addstatus;
    Button btn_addproduct;
    RadioButton rdoActive, rdoDisable;
    Bundle b;
    private ArrayList<Tag> dataram = new ArrayList<Tag>();
    private ArrayList<Tag> datarom = new ArrayList<Tag>();
    private ArrayList<Tag> databattery = new ArrayList<Tag>();
    private ArrayList<Brand> databrand = new ArrayList<Brand>();
    private ArrayList<Sale> datasale = new ArrayList<Sale>();
    private ArrayList<String> datastatus = new ArrayList<String>();
    int status,idhang,idram,idrom,idpin,idkm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productadd);

        //init attribute of layout
        addname = findViewById(R.id.addname);
        addimg = findViewById(R.id.addimg);
        addimg1 = findViewById(R.id.addimg1);
        addimg2 = findViewById(R.id.addimg2);
        addimg3 = findViewById(R.id.addimg3);
        addimg4 = findViewById(R.id.addimg4);
        addstorage = findViewById(R.id.addstorage);
        addguarantee = findViewById(R.id.addguarantee);
        addscreen = findViewById(R.id.addscreen);
        addfrontcam = findViewById(R.id.addfrontcam);
        addrearcam = findViewById(R.id.addrearcam);
        addtitle1 = findViewById(R.id.addtitle1);
        addtitle2 = findViewById(R.id.addtitle2);
        addtitle3 = findViewById(R.id.addtitle3);
        addimgdes1 = findViewById(R.id.addimgdes1);
        addimgdes2 = findViewById(R.id.addimgdes2);
        addimgdes3 = findViewById(R.id.addimgdes3);
        adddetail1 = findViewById(R.id.adddetail1);
        adddetail2 = findViewById(R.id.adddetail2);
        adddetail3 = findViewById(R.id.adddetail3);
        addprice = findViewById(R.id.addprice);

        addbrand = findViewById(R.id.addbrand);
        addsale = findViewById(R.id.addsale);
        addram = findViewById(R.id.addram);
        addrom = findViewById(R.id.addrom);
        addbattery = findViewById(R.id.addbattery);
        addstatus = findViewById(R.id.addstatus);




        btn_addproduct = findViewById(R.id.btn_addproduct);
        rdoActive = findViewById(R.id.rdoActive);
        rdoDisable = findViewById(R.id.rdoDisable);

        btn_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    addproduct();
                    adddescription();
                    finish();
                }
            }
        });
        loadDataBrand();
        loadDataRam();
        loadDataRom();
        loadDataBattery();
        loadDataSale();
        setdataStatus();
    }



    private void loadDataRam(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallram, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idram");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");
                        int dataactive = 0;
                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        Tag ramRomBattery = new Tag(id,dungluong,dataactive);
                        dataram.add(ramRomBattery);
                    }
                    // SET DATA HERE
                    setdataRam(dataram);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadDataRom() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallrom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idrom");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        Tag rom = new Tag(id,dungluong,dataactive);
                        datarom.add(rom);
                    }
                    // SET DATA HERE
                    setdataRom(datarom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadDataBattery() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallpin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idpin");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        Tag pin = new Tag(id,dungluong,dataactive);
                        databattery.add(pin);
                    }
                    // SET DATA HERE
                    setdataBattery(databattery);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadDataBrand() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallbrand, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idhang");
                        String tenhang = jsonObject.getString("tenhang");
                        String img = jsonObject.getString("logohang");
                        if (img.equals("") || img.equals("null")) {
                            img = "img/user/No-Image.png";
                        }

                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        Brand brand = new Brand(id, tenhang,Server.HOST + img, dataactive);
                        databrand.add(brand);
                    }
                    // SET DATA HERE
                    setdataBrand(databrand);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadDataSale() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallsale, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idkm");
                        String discount = jsonObject.getString("sale");
                        String ngaybdkm = jsonObject.getString("ngaybdkm");
                        String ngayktkm = jsonObject.getString("ngayktkm");

                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        Sale sale = new Sale(id, discount,ngaybdkm,ngayktkm, dataactive);
                        datasale.add(sale);
                    }
                    // SET DATA HERE
                    setdataSale(datasale);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    boolean isEditTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    private boolean checkValid() {
        if (isEditTextEmpty(addname)) {
            addname.requestFocus();
            addname.setError("Name Product not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimg)) {
            addimg.requestFocus();
            addimg.setError("Image Url not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimg1)) {
            addimg1.requestFocus();
            addimg1.setError("Image Url 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimg2)) {
            addimg2.requestFocus();
            addimg2.setError("Image Url 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimg3)) {
            addimg3.requestFocus();
            addimg3.setError("Image Url 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimg4)) {
            addimg4.requestFocus();
            addimg4.setError("Image Url 4 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addstorage)) {
            addstorage.requestFocus();
            addstorage.setError("Storage not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addguarantee)) {
            addguarantee.requestFocus();
            addguarantee.setError("Guarantee not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addscreen)) {
            addscreen.requestFocus();
            addscreen.setError("Screen not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addfrontcam)) {
            addfrontcam.requestFocus();
            addfrontcam.setError("Front Camera not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addrearcam)) {
            addrearcam.requestFocus();
            addrearcam.setError("Rear Camera not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addtitle1)) {
            addtitle1.requestFocus();
            addtitle1.setError("Title 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addtitle2)) {
            addtitle2.requestFocus();
            addtitle2.setError("Title 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addtitle3)) {
            addtitle3.requestFocus();
            addtitle3.setError("Title 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimgdes1)) {
            addimgdes1.requestFocus();
            addimgdes1.setError("Image Description 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimgdes2)) {
            addimgdes2.requestFocus();
            addimgdes2.setError("Image Description 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addimgdes3)) {
            addimgdes3.requestFocus();
            addimgdes3.setError("Image Description 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(adddetail1)) {
            adddetail1.requestFocus();
            adddetail1.setError("Detail 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(adddetail2)) {
            adddetail2.requestFocus();
            adddetail2.setError("Detail 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(adddetail3)) {
            adddetail3.requestFocus();
            adddetail3.setError("Detail 3 not EMPTY");
            return false;
        }
        return true;
    }

    private void addproduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.addrow + "sanpham", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Add Product Success", Toast.LENGTH_LONG).show();
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
                params.put("img", addimg.getText().toString().trim());
                params.put("ten", addname.getText().toString().trim());
                params.put("idhang", ""+idhang);
                params.put("gia", addprice.getText().toString().trim());
                params.put("soluong", addstorage.getText().toString().trim());
                params.put("idram", ""+idram);
                params.put("idrom", ""+idrom);
                params.put("idpin", ""+idpin);
                params.put("baohanh", addguarantee.getText().toString().trim());
                params.put("idkm", ""+idkm);
                params.put("img01", addimg1.getText().toString().trim());
                params.put("img02", addimg2.getText().toString().trim());
                params.put("img03", addimg3.getText().toString().trim());
                params.put("img04", addimg4.getText().toString().trim());
                params.put("kichthuoc", addscreen.getText().toString().trim());
                params.put("cameratruoc", addfrontcam.getText().toString().trim());
                params.put("camerasau", addrearcam.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("tinhtrang", ""+status);
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    private void adddescription() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.addrow + "mota", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Add Description Success", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                        addtitle1,addtitle2,addtitle3,addimgdes1,addimgdes2,addimgdes3,adddetail1,adddetail2,adddetail3;
                Map<String, String> params = new HashMap<>();
                params.put("tieude1", addtitle1.getText().toString().trim());
                params.put("tieude2", addtitle2.getText().toString().trim());
                params.put("tieude3", addtitle3.getText().toString().trim());
                params.put("chitiet1", adddetail1.getText().toString().trim());
                params.put("chitiet2", adddetail2.getText().toString().trim());
                params.put("chitiet3", adddetail3.getText().toString().trim());
                params.put("anh1", addimgdes1.getText().toString().trim());
                params.put("anh2", addimgdes2.getText().toString().trim());
                params.put("anh3", addimgdes3.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    private void setdataRam(ArrayList<Tag> dataram) {
        String[] item = new String[dataram.size()];
        for (int i = 0; i < dataram.size(); i++) {
            Tag tag = dataram.get(i);
            item[i] = tag.getData();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addram.setAdapter(adapter);
        addram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag ram:dataram){
                    if (ram.getData().equals(addram.getSelectedItem().toString())){
                        idram = ram.getId();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                idram = 1;
            }
        });
    }
    private void setdataRom(ArrayList<Tag> datarom) {
        String[] item = new String[datarom.size()];
        for (int i = 0; i < datarom.size(); i++) {
            Tag tag = datarom.get(i);
            item[i] = tag.getData();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addrom.setAdapter(adapter);
        addrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag rom:datarom){
                    if (rom.getData().equals(addrom.getSelectedItem().toString())){
                        idrom = rom.getId();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                idrom = 1;
            }
        });
    }
    private void setdataBattery(ArrayList<Tag> databattery) {
        String[] item = new String[databattery.size()];
        for (int i = 0; i < databattery.size(); i++) {
            Tag tag = databattery.get(i);
            item[i] = tag.getData();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addbattery.setAdapter(adapter);
        addbattery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag battery:databattery){
                    if (battery.getData().equals(addbattery.getSelectedItem().toString())){
                        idpin = battery.getId();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                idpin = 1;
            }
        });
    }
    private void setdataBrand(ArrayList<Brand> databrand) {
        String[] item = new String[databrand.size()];
        for (int i = 0; i < databrand.size(); i++) {
            Brand brand = databrand.get(i);
            item[i] = brand.getNameOfBrand();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addbrand.setAdapter(adapter);
        addbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Brand brand:databrand){
                    if (brand.getNameOfBrand().equals(addbrand.getSelectedItem().toString())){
                        idhang = brand.getId();
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                idhang = 1;
            }
        });
    }

    private void setdataSale(ArrayList<Sale> datasale) {
        String[] item = new String[datasale.size()];
        for (int i = 0; i < datasale.size(); i++) {
            Sale sale = datasale.get(i);
            item[i] = sale.getSale();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addsale.setAdapter(adapter);
        addsale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Sale sale:datasale){
                    if (sale.getSale().equals(addsale.getSelectedItem().toString())){
                        idkm = sale.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                idkm = 1;
            }
        });
    }
    private void setdataStatus() {
        String[] item = new String[4];
        item[0] = "New";
        item[1] = "Sale";
        item[2] = "Hot";
        item[3] = "Other";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        addstatus.setAdapter(adapter);

        addstatus.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = position;
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                status = 3;
            }
        });
    }
}