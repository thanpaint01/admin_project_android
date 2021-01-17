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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;

public class Add extends AppCompatActivity {
    ImageView imageView;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    private String encodedImage;
    private String name;


    EditText addFullName, addDay, addMonth, addYear, addPhoneNo, addEmailAddress, addAddress, addUsername, addPassword, addConfirmPassword;
    Button btn_adduser, buttonChoose;
    RadioButton rdoMale, rdoFemale, rdoActive, rdoBan, rdoAdmin, rdoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useradd);

        //init attribute of layout
        addFullName = findViewById(R.id.addFullName);
        addDay = findViewById(R.id.addDay);
        addMonth = findViewById(R.id.addMonth);
        addYear = findViewById(R.id.addYear);
        addPhoneNo = findViewById(R.id.addPhoneNo);
        addEmailAddress = findViewById(R.id.addEmailAddress);
        addAddress = findViewById(R.id.addAddress);
        addUsername = findViewById(R.id.addUsername);
        addPassword = findViewById(R.id.addPassword);
        addConfirmPassword = findViewById(R.id.addConfirmPassword);
        btn_adduser = findViewById(R.id.btn_adduser);
        buttonChoose = findViewById(R.id.buttonChoose);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);
        rdoActive = findViewById(R.id.rdoActive);
        rdoBan = findViewById(R.id.rdoBan);
        rdoAdmin = findViewById(R.id.rdoAdmin);
        rdoUser = findViewById(R.id.rdoUser);
        name = "";
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    if(!name.equals("")){
                        saveData(v);
                        add();
                        finish();
                    }else Toast.makeText(getApplicationContext(),"Please Choose Image",Toast.LENGTH_LONG).show();

                }
            }
        });
        imageView = (ImageView) findViewById(R.id.imageView);





        requestStoragePermission();


    }

    boolean isEditTextEmpty(EditText text) {
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
        String year = addYear.getText().toString();
        String month = addMonth.getText().toString();
        String day = addDay.getText().toString();

        if (isEditTextEmpty(addFullName) || addFullName.getText().toString() == "^[a-zA-Z\\\\s]+") {
            addFullName.requestFocus();
            addFullName.setError("Full Name not VALID");
            return false;
        }
        if (isEditTextEmpty(addDay) || isEditTextEmpty(addMonth) || isEditTextEmpty(addYear)) {
            addYear.requestFocus();
            addYear.setError("Date not EMPTY");
            return false;
        }

        if (!isDate(day + "/" + month + "/" + year) || Integer.parseInt(year) > Calendar.getInstance().get(Calendar.YEAR)) {
            addYear.requestFocus();
            addYear.setError("Date not VALID");
            return false;
        }

        if (isEditTextEmpty(addPhoneNo)) {
            addPhoneNo.requestFocus();
            addPhoneNo.setError("Phone not EMPTY");
            return false;
        }

        if (isEditTextEmpty(addPassword)) {
            addPassword.requestFocus();
            addPassword.setError("Password not EMPTY");
            return false;
        }
        if (isEditTextEmpty(addConfirmPassword)) {
            addConfirmPassword.requestFocus();
            addConfirmPassword.setError("Confirm Password not EMPTY");
            return false;
        }
        if (isEmail(addEmailAddress) == false) {
            addEmailAddress.requestFocus();
            addEmailAddress.setError("Email not VALID");
            return false;
        }

        if (!addConfirmPassword.getText().toString().equals(addPassword.getText().toString())) {
            addConfirmPassword.requestFocus();
            addConfirmPassword.setError("Confirm not match");
            return false;
        }
        return true;
    }

    private void add() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.addrow + "nguoidung", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                add();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ten", addFullName.getText().toString().trim());
                String year = addYear.getText().toString();
                String month = addMonth.getText().toString();
                String day = addDay.getText().toString();
                String dob = year + "-" + month + "-" + day;
                params.put("ngaysinh", dob);
                params.put("gioitinh", rdoMale.isChecked() ? "Nam" : "Nu");
                params.put("sdt", addPhoneNo.getText().toString());
                params.put("email", addEmailAddress.getText().toString());
                params.put("diachi", addAddress.getText().toString());
                params.put("taikhoan", addUsername.getText().toString());
                params.put("matkhau", addPassword.getText().toString());
                params.put("img", "img/slide/"+name);
                params.put("quyen", rdoAdmin.isChecked() ? "2" : "1");
                params.put("active", rdoActive.isChecked() ? "1" : "0");
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
        StringRequest request = new StringRequest(Request.Method.POST, Server.upload +"slide"
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
                params.put("image", encodedImage);
                params.put("name", name);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy( 1000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}