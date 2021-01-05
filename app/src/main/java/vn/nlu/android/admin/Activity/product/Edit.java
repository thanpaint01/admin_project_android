package vn.nlu.android.admin.Activity.product;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Brand;
import vn.nlu.android.admin.model.Sale;
import vn.nlu.android.admin.model.Tag;

public class Edit extends AppCompatActivity {
    EditText editname, editimg, editimg1, editimg2, editimg3, editimg4, editprice, editstorage, editguarantee, editscreen, editfrontcam, editrearcam,
            edittitle1, edittitle2, edittitle3, editimgdes1, editimgdes2, editimgdes3, editdetail1, editdetail2, editdetail3;
    Spinner editbrand, editsale, editram, editrom, editbattery, editstatus;
    Button btn_editproduct;
    RadioButton rdoActive, rdoDisable;
    Bundle b;
    private ArrayList<Tag> dataram = new ArrayList<Tag>();
    private ArrayList<Tag> datarom = new ArrayList<Tag>();
    private ArrayList<Tag> databattery = new ArrayList<Tag>();
    private ArrayList<Brand> databrand = new ArrayList<Brand>();
    private ArrayList<Sale> datasale = new ArrayList<Sale>();
    private ArrayList<String> datastatus = new ArrayList<String>();
    int status, idhang, idram, idrom, idpin, idkm;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productedit);

        //init attribute of layout
        editname = findViewById(R.id.editname);
        editimg = findViewById(R.id.editimg);
        editimg1 = findViewById(R.id.editimg1);
        editimg2 = findViewById(R.id.editimg2);
        editimg3 = findViewById(R.id.editimg3);
        editimg4 = findViewById(R.id.editimg4);
        editstorage = findViewById(R.id.editstorage);
        editguarantee = findViewById(R.id.editguarantee);
        editscreen = findViewById(R.id.editscreen);
        editfrontcam = findViewById(R.id.editfrontcam);
        editrearcam = findViewById(R.id.editrearcam);
        edittitle1 = findViewById(R.id.edittitle1);
        edittitle2 = findViewById(R.id.edittitle2);
        edittitle3 = findViewById(R.id.edittitle3);
        editimgdes1 = findViewById(R.id.editimgdes1);
        editimgdes2 = findViewById(R.id.editimgdes2);
        editimgdes3 = findViewById(R.id.editimgdes3);
        editdetail1 = findViewById(R.id.editdetail1);
        editdetail2 = findViewById(R.id.editdetail2);
        editdetail3 = findViewById(R.id.editdetail3);
        editprice = findViewById(R.id.editprice);

        editbrand = findViewById(R.id.editbrand);
        editsale = findViewById(R.id.editsale);
        editram = findViewById(R.id.editram);
        editrom = findViewById(R.id.editrom);
        editbattery = findViewById(R.id.editbattery);
        editstatus = findViewById(R.id.editstatus);

        btn_editproduct = findViewById(R.id.btn_editproduct);
        rdoActive = findViewById(R.id.rdoActive);
        rdoDisable = findViewById(R.id.rdoDisable);

        // set default data
        i = getIntent();
        b = i.getBundleExtra("data");

        editname.setText(b.getString("ten"));
        editimg.setText(b.getString("img").replace(Server.HOST,""));
        editimg1.setText(b.getString("img01").replace(Server.HOST,""));
        editimg2.setText(b.getString("img02").replace(Server.HOST,""));
        editimg3.setText(b.getString("img03").replace(Server.HOST,""));
        editimg4.setText(b.getString("img04").replace(Server.HOST,""));
        editstorage.setText(b.getString("soluong"));
        editguarantee.setText(b.getString("baohanh"));
        editscreen.setText(b.getString("kichthuoc"));
        editfrontcam.setText(b.getString("cameratruoc"));
        editrearcam.setText(b.getString("camerasau"));
        edittitle1.setText(b.getString("tieude1"));
        edittitle2.setText(b.getString("tieude2"));
        edittitle3.setText(b.getString("tieude3"));
        editimgdes1.setText(b.getString("anh1").replace(Server.HOST,""));
        editimgdes2.setText(b.getString("anh2").replace(Server.HOST,""));
        editimgdes3.setText(b.getString("anh3").replace(Server.HOST,""));
        editdetail1.setText(b.getString("chitiet1").trim());
        editdetail2.setText(b.getString("chitiet2").trim());
        editdetail3.setText(b.getString("chitiet3").trim());
        editprice.setText(b.getString("gia"));

        loadDataBrand();
        loadDataRam();
        loadDataRom();
        loadDataBattery();
        loadDataSale();
        setdataStatus();


        btn_editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    editproduct();
                    editdescription();
                    finish();
                }
            }
        });

    }


    private void loadDataRam() {
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
                        Tag ramRomBattery = new Tag(id, dungluong, dataactive);
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
                        Tag rom = new Tag(id, dungluong, dataactive);
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
                        Tag pin = new Tag(id, dungluong, dataactive);
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
                        Brand brand = new Brand(id, tenhang, Server.HOST + img, dataactive);
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
                        Sale sale = new Sale(id, discount, ngaybdkm, ngayktkm, dataactive);
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
        if (isEditTextEmpty(editname)) {
            editname.requestFocus();
            editname.setError("Name Product not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimg)) {
            editimg.requestFocus();
            editimg.setError("Image Url not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimg1)) {
            editimg1.requestFocus();
            editimg1.setError("Image Url 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimg2)) {
            editimg2.requestFocus();
            editimg2.setError("Image Url 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimg3)) {
            editimg3.requestFocus();
            editimg3.setError("Image Url 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimg4)) {
            editimg4.requestFocus();
            editimg4.setError("Image Url 4 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editstorage)) {
            editstorage.requestFocus();
            editstorage.setError("Storage not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editguarantee)) {
            editguarantee.requestFocus();
            editguarantee.setError("Guarantee not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editscreen)) {
            editscreen.requestFocus();
            editscreen.setError("Screen not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editfrontcam)) {
            editfrontcam.requestFocus();
            editfrontcam.setError("Front Camera not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editrearcam)) {
            editrearcam.requestFocus();
            editrearcam.setError("Rear Camera not EMPTY");
            return false;
        }
        if (isEditTextEmpty(edittitle1)) {
            edittitle1.requestFocus();
            edittitle1.setError("Title 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(edittitle2)) {
            edittitle2.requestFocus();
            edittitle2.setError("Title 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(edittitle3)) {
            edittitle3.requestFocus();
            edittitle3.setError("Title 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimgdes1)) {
            editimgdes1.requestFocus();
            editimgdes1.setError("Image Description 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimgdes2)) {
            editimgdes2.requestFocus();
            editimgdes2.setError("Image Description 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editimgdes3)) {
            editimgdes3.requestFocus();
            editimgdes3.setError("Image Description 3 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editdetail1)) {
            editdetail1.requestFocus();
            editdetail1.setError("Detail 1 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editdetail2)) {
            editdetail2.requestFocus();
            editdetail2.setError("Detail 2 not EMPTY");
            return false;
        }
        if (isEditTextEmpty(editdetail3)) {
            editdetail3.requestFocus();
            editdetail3.setError("Detail 3 not EMPTY");
            return false;
        }
        return true;
    }

    private void editproduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequesteditproduct = new StringRequest(Request.Method.POST, Server.updaterow + "sanpham", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {

                    Toast.makeText(getApplicationContext(), "Edit Product Success", Toast.LENGTH_LONG).show();
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
                params.put("img", editimg.getText().toString().trim());
                params.put("ten", editname.getText().toString().trim());
                params.put("idhang", "" + idhang);
                params.put("gia", editprice.getText().toString().trim());
                params.put("soluong", editstorage.getText().toString().trim());
                params.put("idram", "" + idram);
                params.put("idrom", "" + idrom);
                params.put("idpin", "" + idpin);
                params.put("baohanh", editguarantee.getText().toString().trim());
                params.put("idkm", "" + idkm);
                params.put("img01", editimg1.getText().toString().trim());
                params.put("img02", editimg2.getText().toString().trim());
                params.put("img03", editimg3.getText().toString().trim());
                params.put("img04", editimg4.getText().toString().trim());
                params.put("kichthuoc", editscreen.getText().toString().trim());
                params.put("cameratruoc", editfrontcam.getText().toString().trim());
                params.put("camerasau", editrearcam.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("tinhtrang", "" + status);
                params.put("idsp", "" + b.get("id"));
                return params;
            }
        };
        requestQueue.add(stringRequesteditproduct);
    }

    private void editdescription() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequesteditdescription = new StringRequest(Request.Method.POST, Server.updaterow + "mota", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response"+response);
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Edit Description Success", Toast.LENGTH_LONG).show();
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
                params.put("tieude1", edittitle1.getText().toString().trim());
                params.put("tieude2", edittitle2.getText().toString().trim());
                params.put("tieude3", edittitle3.getText().toString().trim());
                params.put("chitiet1", editdetail1.getText().toString().trim());
                params.put("chitiet2", editdetail2.getText().toString().trim());
                params.put("chitiet3", editdetail3.getText().toString().trim());
                params.put("anh1", editimgdes1.getText().toString().trim());
                params.put("anh2", editimgdes2.getText().toString().trim());
                params.put("anh3", editimgdes3.getText().toString().trim());
                params.put("idsp", "" + b.get("id"));
                return params;
            }
        };
        requestQueue.add(stringRequesteditdescription);
    }


    private void setdataRam(ArrayList<Tag> dataram) {
        String[] item = new String[dataram.size()];
        for (int i = 0; i < dataram.size(); i++) {
            Tag tag = dataram.get(i);
            item[i] = tag.getData();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        editram.setAdapter(adapter);
        int position = adapter.getPosition(b.getString("ram"));
        editram.setSelection(position);
        editram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag ram : dataram) {
                    if (ram.getData().equals(editram.getSelectedItem().toString())) {
                        idram = ram.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                for (Tag ram : dataram) {
                    if (ram.getData().equals(b.getString("ram"))) {
                        idram = ram.getId();
                    }
                }
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
        editrom.setAdapter(adapter);
        int position = adapter.getPosition(b.getString("rom"));
        editrom.setSelection(position);
        editrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag rom : datarom) {
                    if (rom.getData().equals(editrom.getSelectedItem().toString())) {
                        idrom = rom.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                for (Tag rom : datarom) {
                    if (rom.getData().equals(b.getString("rom"))) {
                        idrom = rom.getId();
                    }
                }
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
        editbattery.setAdapter(adapter);
        int position = adapter.getPosition(b.getString("pin"));
        editbattery.setSelection(position);
        editbattery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Tag battery : databattery) {
                    if (battery.getData().equals(editbattery.getSelectedItem().toString())) {
                        idpin = battery.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                for (Tag battery : databattery) {
                    if (battery.getData().equals(b.getString("pin"))) {
                        idpin = battery.getId();
                    }
                }
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
        editbrand.setAdapter(adapter);
        int position = adapter.getPosition(b.getString("hang"));
        editbrand.setSelection(position);
        editbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Brand brand : databrand) {
                    if (brand.getNameOfBrand().equals(editbrand.getSelectedItem().toString())) {
                        idhang = brand.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                for (Brand brand : databrand) {
                    if (brand.getNameOfBrand().equals(b.getString("hang"))) {
                        idhang = brand.getId();
                    }
                }
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
        editsale.setAdapter(adapter);
        int position = adapter.getPosition(""+b.getInt("sale"));
        editsale.setSelection(position);
        editsale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Sale sale : datasale) {
                    if (sale.getSale().equals(editsale.getSelectedItem().toString())) {
                        idkm = sale.getId();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                for (Sale sale : datasale) {
                    if (sale.getSale().equals(""+b.getInt("sale"))) {
                        idkm = sale.getId();
                    }
                }
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
        editstatus.setAdapter(adapter);
        editstatus.setSelection(b.getInt("tinhtrang"));
        editstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = position;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                status = b.getInt("tinhtrang");
            }
        });

    }
}