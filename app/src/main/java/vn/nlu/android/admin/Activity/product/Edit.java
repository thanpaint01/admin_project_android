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
import com.squareup.picasso.Picasso;

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
import vn.nlu.android.admin.model.Sale;
import vn.nlu.android.admin.model.Tag;

public class Edit extends AppCompatActivity {
    EditText editname, editprice, editstorage, editguarantee, editscreen, editfrontcam, editrearcam,
            edittitle1, edittitle2, edittitle3, editdetail1, editdetail2, editdetail3;
    Spinner editbrand, editsale, editram, editrom, editbattery, editstatus;
    Button btn_editproduct;
    RadioButton rdoActive, rdoDisable;
    Bundle b;

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

    private ArrayList<Tag> dataram = new ArrayList<Tag>();
    private ArrayList<Tag> datarom = new ArrayList<Tag>();
    private ArrayList<Tag> databattery = new ArrayList<Tag>();
    private ArrayList<Brand> databrand = new ArrayList<Brand>();
    private ArrayList<Sale> datasale = new ArrayList<Sale>();
    private ArrayList<String> datastatus = new ArrayList<String>();
    int status, idhang, idram, idrom, idpin, idkm;
    Intent i;
    boolean isbrandchange;

    String productimg1,productimg2,productimg3,productimg4,productimg5,productimg6,productimg7;
    String checkimg1,checkimg2,checkimg3,checkimg4,checkimg5,checkimg6,checkimg7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productedit);
        currentimage = 0;
        namebrand="";productname="";
        name1= "";name2= "";name3= "";name4= "";name5= "";name6= "";name7= "";
        encodedImage1 = "";encodedImage2= "";encodedImage3= "";encodedImage4= "";encodedImage5= "";encodedImage6= "";encodedImage7= "";

        isbrandchange = false;

        //init attribute of layout
        editname = findViewById(R.id.editname);

        editstorage = findViewById(R.id.editstorage);editguarantee = findViewById(R.id.editguarantee);
        editscreen = findViewById(R.id.editscreen);editfrontcam = findViewById(R.id.editfrontcam);
        editrearcam = findViewById(R.id.editrearcam);edittitle1 = findViewById(R.id.edittitle1);
        edittitle2 = findViewById(R.id.edittitle2);edittitle3 = findViewById(R.id.edittitle3);

        editdetail1 = findViewById(R.id.editdetail1);editdetail2 = findViewById(R.id.editdetail2);editdetail3 = findViewById(R.id.editdetail3);
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

        imageView1 = findViewById(R.id.imageView1);imageView2 = findViewById(R.id.imageView2);imageView3 = findViewById(R.id.imageView3);imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);imageView6 = findViewById(R.id.imageView6);imageView7 = findViewById(R.id.imageView7);

        buttonChoose1 = findViewById(R.id.buttonChoose1);buttonChoose2 = findViewById(R.id.buttonChoose2);buttonChoose3 = findViewById(R.id.buttonChoose3);buttonChoose4 = findViewById(R.id.buttonChoose4);
        buttonChoose5 = findViewById(R.id.buttonChoose5);buttonChoose6 = findViewById(R.id.buttonChoose6);buttonChoose7 = findViewById(R.id.buttonChoose7);


        buttonChoose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,1); }
        });
        buttonChoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,2); }
        });
        buttonChoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,3); }
        });
        buttonChoose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,4); }
        });
        buttonChoose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,5); }
        });
        buttonChoose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,6); }
        });
        buttonChoose7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectImage(v,7); }
        });

        // set default data
        i = getIntent();
        b = i.getBundleExtra("data");

        productimg1 = Server.HOST + b.getString("img01");checkimg1 = Server.HOST + b.getString("img01");productimg2 = Server.HOST + b.getString("img02");checkimg2 = Server.HOST + b.getString("img02");
        productimg3 = Server.HOST + b.getString("img03");checkimg3 = Server.HOST + b.getString("img03");productimg4 = Server.HOST + b.getString("img04");checkimg4 = Server.HOST + b.getString("img04");
        productimg5 = Server.HOST + b.getString("anh1");checkimg5 = Server.HOST + b.getString("anh1");productimg6 = Server.HOST + b.getString("anh2");checkimg6 = Server.HOST + b.getString("anh2");
        productimg7 = Server.HOST + b.getString("anh3");checkimg7 = Server.HOST + b.getString("anh3");


        Picasso.get().load(productimg1).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView1);
        Picasso.get().load(productimg2).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView2);
        Picasso.get().load(productimg3).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView3);
        Picasso.get().load(productimg4).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView4);
        Picasso.get().load(productimg5).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView5);
        Picasso.get().load(productimg6).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView6);
        Picasso.get().load(productimg7).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.ic_baseline_broken_image_24).into(imageView7);


        editname.setText(b.getString("ten"));

        editstorage.setText(b.getString("soluong"));editguarantee.setText(b.getString("baohanh"));editscreen.setText(b.getString("kichthuoc"));editfrontcam.setText(b.getString("cameratruoc"));
        editrearcam.setText(b.getString("camerasau"));edittitle1.setText(b.getString("tieude1"));edittitle2.setText(b.getString("tieude2"));edittitle3.setText(b.getString("tieude3"));

        editdetail1.setText(b.getString("chitiet1").trim());editdetail2.setText(b.getString("chitiet2").trim());
        editdetail3.setText(b.getString("chitiet3").trim());editprice.setText(b.getString("gia"));

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
                    productname= editname.getText().toString().replace(" ","").trim().toLowerCase();
                    saveData(v);
                    editproduct();
                    editdescription();
                    finish();
                }
            }
        });

        requestStoragePermission();
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
                params.put("img", productimg1.equals(checkimg1) || !isbrandchange? b.getString("img01") :"img/sanpham/"+namebrand+"/"+productname+"/"+name1);
                params.put("ten", editname.getText().toString().trim());
                params.put("idhang", ""+idhang);
                params.put("gia", editprice.getText().toString().trim());
                params.put("soluong", editstorage.getText().toString().trim());
                params.put("idram", ""+idram);
                params.put("idrom", ""+idrom);
                params.put("idpin", ""+idpin);
                params.put("baohanh", editguarantee.getText().toString().trim());
                params.put("idkm", ""+idkm);
                params.put("img01", productimg1.equals(checkimg1) || !isbrandchange? b.getString("img01") :"img/sanpham/"+namebrand+"/"+productname+"/"+name1);
                params.put("img02", productimg2.equals(checkimg2) || !isbrandchange? b.getString("img02") :"img/sanpham/"+namebrand+"/"+productname+"/"+name2);
                params.put("img03", productimg3.equals(checkimg3) || !isbrandchange? b.getString("img03") :"img/sanpham/"+namebrand+"/"+productname+"/"+name3);
                params.put("img04", productimg4.equals(checkimg4) || !isbrandchange? b.getString("img04") :"img/sanpham/"+namebrand+"/"+productname+"/"+name4);
                params.put("kichthuoc", editscreen.getText().toString().trim());
                params.put("cameratruoc", editfrontcam.getText().toString().trim());
                params.put("camerasau", editrearcam.getText().toString().trim());
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("tinhtrang", ""+status);
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
                params.put("anh1", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name5);
                params.put("anh2", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name6);
                params.put("anh3", "img/sanpham/"+namebrand+"/"+productname+"/mota"+"/"+name7);
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
                idram = position;
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
                idrom = position;
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
                idpin = position;
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
                        namebrand = brand.getNameOfBrand().trim().toLowerCase().replace(" ","");
                        isbrandchange = false;
                    }else isbrandchange = true;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                idhang = position;
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
                idkm = position;
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
                        productimg1 = name1;
                        break;
                    case 2:
                        imageView2.setImageBitmap(bitmap);
                        name2 = getName(filepath);
                        productimg2 = name2;
                        break;
                    case 3:
                        imageView3.setImageBitmap(bitmap);
                        name3 = getName(filepath);
                        productimg3 = name3;
                        break;
                    case 4:
                        imageView4.setImageBitmap(bitmap);
                        name4 = getName(filepath);
                        productimg4 = name4;
                        break;
                    case 5:
                        imageView5.setImageBitmap(bitmap);
                        name5 = getName(filepath);
                        productimg5 = name5;
                        break;
                    case 6:
                        imageView6.setImageBitmap(bitmap);
                        name6 = getName(filepath);
                        productimg6 = name6;
                        break;
                    case 7:
                        imageView7.setImageBitmap(bitmap);
                        name7 = getName(filepath);
                        productimg7 = name7;
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
                        System.out.println("error savedata"+ error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("namebrand", namebrand);
                        params.put("productname", productname);
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

                        return params;
                    }
                };
                int MY_SOCKET_TIMEOUT_MS = 4000;
                request2.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error savedata "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        requestQueue2.add(request);
    }
}