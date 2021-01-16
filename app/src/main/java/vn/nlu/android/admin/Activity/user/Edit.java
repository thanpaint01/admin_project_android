package vn.nlu.android.admin.Activity.user;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Edit extends AppCompatActivity {
    ImageView imageView;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    private String encodedImage;
    private String name,checkname;

    EditText edtFullName, edtDay, edtMonth, edtYear, edtPhoneNo, edtEmailAddress, edtAddress, edtUsername, edtPassword, edtConfirmPassword;
    Button btn_edituser,buttonChoose;
    RadioButton rdoMale, rdoFemale, rdoActive, rdoBan, rdoAdmin, rdoUser;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);

        //init attribute of layout
        edtFullName = findViewById(R.id.edtFullName);
        edtDay = findViewById(R.id.edtDay);
        edtMonth = findViewById(R.id.edtMonth);
        edtYear = findViewById(R.id.edtYear);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtAddress = findViewById(R.id.edtAddress);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btn_edituser = findViewById(R.id.btn_edituser);
        buttonChoose = findViewById(R.id.buttonChoose);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);
        rdoActive = findViewById(R.id.rdoActive);
        rdoBan = findViewById(R.id.rdoBan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        imageView = findViewById(R.id.imageView);
        // set default
        Intent i = getIntent();
        b = i.getBundleExtra("data");

        edtFullName.setText(b.getString("name"));
        edtPhoneNo.setText(b.getString("phone"));
        edtEmailAddress.setText(b.getString("mail"));
        edtAddress.setText(b.getString("address"));
        edtUsername.setText(b.getString("username"));
        if (b.getString("gender").equals("Nam")) {
            rdoMale.setChecked(true);
        } else rdoFemale.setChecked(true);

        if (b.getInt("permission") == 1) {
            rdoUser.setChecked(true);
        } else rdoAdmin.setChecked(true);

        if (b.getInt("active") == 1) {
            rdoActive.setChecked(true);
        } else rdoBan.setChecked(true);


        String date = b.getString("dob");
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        edtDay.setText(day);
        edtMonth.setText(month);
        edtYear.setText(year);

        name = Server.HOST + b.getString("imgurl");
        checkname = Server.HOST + b.getString("imgurl");

        Picasso.get().load(name)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView);



        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        btn_edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    saveData(v);
                    edit();
                    finish();
                }
            }
        });
        imageView = (ImageView) findViewById(R.id.imageView);
        requestStoragePermission();
    }

    boolean isEdtTextEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean isDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean checkValid() {
        String year = edtYear.getText().toString();
        String month = edtMonth.getText().toString();
        String day = edtDay.getText().toString();

        if (isEdtTextEmpty(edtFullName) || edtFullName.getText().toString() == "^[a-zA-Z\\\\s]+") {
            edtFullName.requestFocus();
            edtFullName.setError("Full Name not VALID");
            return false;
        }
        if (isEdtTextEmpty(edtDay) || isEdtTextEmpty(edtMonth) || isEdtTextEmpty(edtYear)) {
            edtYear.requestFocus();
            edtYear.setError("Date not EMPTY");
            return false;
        }

        if (!isDate(day + "/" + month + "/" + year) || Integer.parseInt(year) > Calendar.getInstance().get(Calendar.YEAR)) {
            edtYear.requestFocus();
            edtYear.setError("Date not VALID");
            return false;
        }

        if (isEdtTextEmpty(edtPhoneNo)) {
            edtPhoneNo.requestFocus();
            edtPhoneNo.setError("Phone not EMPTY");
            return false;
        }

        if (isEdtTextEmpty(edtPassword)) {
            edtPassword.requestFocus();
            edtPassword.setError("Password not EMPTY");
            return false;
        }
        if (isEdtTextEmpty(edtConfirmPassword)) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError("Confirm Password not EMPTY");
            return false;
        }
        if (isEmail(edtEmailAddress) == false) {
            edtEmailAddress.requestFocus();
            edtEmailAddress.setError("Email not VALID");
            return false;
        }

        if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConfirmPassword.requestFocus();
            edtConfirmPassword.setError("Confirm not match");
            return false;
        }
        return true;
    }

    private void edit() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + "nguoidung", new Response.Listener<String>() {
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
                params.put("ten", edtFullName.getText().toString().trim());
                String year = edtYear.getText().toString();
                String month = edtMonth.getText().toString();
                String day = edtDay.getText().toString();
                String dob = year + "-" + month + "-" + day;
                params.put("ngaysinh", dob);
                params.put("gioitinh", rdoMale.isChecked() ? "Nam" : "Nu");
                params.put("sdt", edtPhoneNo.getText().toString());
                params.put("email", edtEmailAddress.getText().toString());
                params.put("diachi", edtAddress.getText().toString());
                params.put("taikhoan", edtUsername.getText().toString());
                params.put("matkhau", edtPassword.getText().toString());
                params.put("img", name.equals(checkname) ? b.getString("imgurl") : "img/user/"+name);
                params.put("quyen", rdoAdmin.isChecked() ? "2" : "1");
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                params.put("iduser","" +b.getInt("iduser"));
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private void ShowFileChooser() {
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
                imageView.setImageBitmap(bitmap);
                imageStore(bitmap);
                name = getName(filepath);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void selectImage(View view) {
        ShowFileChooser();
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
        name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        cursor.close();
        return name;
    }

    public void saveData(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, Server.upload +"user"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", encodedImage);
                params.put("name", name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }


}