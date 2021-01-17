package vn.nlu.android.admin.Activity.product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    EditText addname, addprice,addstorage, addguarantee, addscreen, addfrontcam, addrearcam,
            addtitle1, addtitle2, addtitle3, adddetail1, adddetail2, adddetail3;
    Spinner addbrand, addsale, addram, addrom, addbattery, addstatus;
    Button btn_addproduct;
    RadioButton rdoActive, rdoDisable;
    int currentimage;

    String namebrand;
    String productname;

    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
    Button buttonChoose1,buttonChoose2,buttonChoose3,buttonChoose4,buttonChoose5,buttonChoose6,buttonChoose7;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;

    private String encodedImage1,encodedImage2,encodedImage3,encodedImage4,encodedImage5,encodedImage6,encodedImage7;
    private String name1,name2,name3,name4,name5,name6,name7;

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
        currentimage = 0;
        namebrand="";productname="";
        name1= "";name2= "";name3= "";name4= "";name5= "";name6= "";name7= "";
        encodedImage1 = "";encodedImage2= "";encodedImage3= "";encodedImage4= "";encodedImage5= "";encodedImage6= "";encodedImage7= "";
        //init attribute of layout
        addname = findViewById(R.id.addname);

        addstorage = findViewById(R.id.addstorage);
        addguarantee = findViewById(R.id.addguarantee);
        addscreen = findViewById(R.id.addscreen);
        addfrontcam = findViewById(R.id.addfrontcam);
        addrearcam = findViewById(R.id.addrearcam);
        addtitle1 = findViewById(R.id.addtitle1);
        addtitle2 = findViewById(R.id.addtitle2);
        addtitle3 = findViewById(R.id.addtitle3);

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

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);

        buttonChoose1 = findViewById(R.id.buttonChoose1);
        buttonChoose2 = findViewById(R.id.buttonChoose2);
        buttonChoose3 = findViewById(R.id.buttonChoose3);
        buttonChoose4 = findViewById(R.id.buttonChoose4);
        buttonChoose5 = findViewById(R.id.buttonChoose5);
        buttonChoose6 = findViewById(R.id.buttonChoose6);
        buttonChoose7 = findViewById(R.id.buttonChoose7);

        buttonChoose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,1);
            }
        });
        buttonChoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,2);
            }
        });
        buttonChoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,3);
            }
        });
        buttonChoose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,4);
            }
        });
        buttonChoose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,5);
            }
        });
        buttonChoose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,6);
            }
        });
        buttonChoose7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v,7);
            }
        });


        rdoActive = findViewById(R.id.rdoActive);
        rdoDisable = findViewById(R.id.rdoDisable);

        btn_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    productname= addname.getText().toString().replace(" ","").trim().toLowerCase();
                    saveData(v);
                    addproduct();
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

        requestStoragePermission();
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
                loadDataRam();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
               loadDataRom();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
                loadDataBattery();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
                loadDataBrand();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
                loadDataSale();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Server.addrow + "mota", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response" + response);
                        if (response.trim().equals("success")) {
                            Toast.makeText(getApplicationContext(), "Add Success", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addproduct();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idsp", response);
                        params.put("tieude1", addtitle1.getText().toString().trim());
                        params.put("tieude2", addtitle2.getText().toString().trim());
                        params.put("tieude3", addtitle3.getText().toString().trim());
                        params.put("chitiet1", adddetail1.getText().toString().trim());
                        params.put("chitiet2", adddetail2.getText().toString().trim());
                        params.put("chitiet3", adddetail3.getText().toString().trim());
                        params.put("anh1", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name5);
                        params.put("anh2", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name6);
                        params.put("anh3", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name7);
                        return params;
                    }
                };
                stringRequest2.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addproduct();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("img", "img/sanpham/"+namebrand+"/"+name1);
                params.put("ten", addname.getText().toString().trim());
                params.put("idhang", ""+idhang);
                params.put("gia", addprice.getText().toString().trim());
                params.put("soluong", addstorage.getText().toString().trim());
                params.put("idram", ""+idram);
                params.put("idrom", ""+idrom);
                params.put("idpin", ""+idpin);
                params.put("baohanh", addguarantee.getText().toString().trim());
                params.put("idkm", ""+idkm);
                params.put("img01", "img/sanpham/"+namebrand+"/"+productname+"/"+name1);
                params.put("img02", "img/sanpham/"+namebrand+"/"+productname+"/"+name2);
                params.put("img03", "img/sanpham/"+namebrand+"/"+productname+"/"+name3);
                params.put("img04", "img/sanpham/"+namebrand+"/"+productname+"/"+name4);
                params.put("kichthuoc", addscreen.getText().toString().trim());
                params.put("cameratruoc", addfrontcam.getText().toString().trim());
                params.put("camerasau", addrearcam.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("tinhtrang", ""+status);
                return params;
            }
        };
        stringRequestCheckExist.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
                        namebrand = brand.getNameOfBrand().trim().toLowerCase().replace(" ","");
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

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private void ShowFileChooser(int i) {
        currentimage = i;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                switch (currentimage){
                    case 1:
                        imageView1.setImageBitmap(bitmap);
                        name1 = getName(filepath);
                        break;
                    case 2:
                        imageView2.setImageBitmap(bitmap);
                        name2 = getName(filepath);
                        break;
                    case 3:
                        imageView3.setImageBitmap(bitmap);
                        name3 = getName(filepath);
                        break;
                    case 4:
                        imageView4.setImageBitmap(bitmap);
                        name4 = getName(filepath);
                        break;
                    case 5:
                        imageView5.setImageBitmap(bitmap);
                        name5 = getName(filepath);
                        break;
                    case 6:
                        imageView6.setImageBitmap(bitmap);
                        name6 = getName(filepath);
                        break;
                    case 7:
                        imageView7.setImageBitmap(bitmap);
                        name7 = getName(filepath);
                        break;
                }
                imageStore(bitmap,currentimage);

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap,int currentimage) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        switch (currentimage){
            case 1:
                encodedImage1 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 2:
                encodedImage2 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 3:
                encodedImage3 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 4:
                encodedImage4 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 5:
                encodedImage5 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 6:
                encodedImage6 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
            case 7:
                encodedImage7 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                break;
        }

    }

    public void selectImage(View view, int i) {
        ShowFileChooser(i);
    }

    private String getName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        cursor.close();
        return name;
    }

    public void saveData(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, Server.createfolder +productname+"&brand="+namebrand
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                StringRequest request2 = new StringRequest(Request.Method.POST, Server.upload +"product"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        saveData(view);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("image1", encodedImage1);
                        params.put("name1", name1);
                        params.put("image2", encodedImage2);
                        params.put("name2", name2);
                        params.put("image3", encodedImage3);
                        params.put("name3", name3);
                        params.put("image4", encodedImage4);
                        params.put("name4", name4);
                        params.put("image5", encodedImage5);
                        params.put("name5", name5);
                        params.put("image6", encodedImage6);
                        params.put("name6", name6);
                        params.put("image7", encodedImage7);
                        params.put("name7", name7);
                        params.put("namebrand", namebrand);
                        params.put("productname", productname);
                        System.out.println("brandpost"+namebrand);
                        System.out.println("productnamepost"+productname);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                request2.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(request2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                saveData(view);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue2.add(request);
    }
}