package vn.nlu.android.admin;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import vn.nlu.android.admin.model.User;

public class InfoActivity extends AppCompatActivity {
    TextView tvName, tvGender, tvDOB, tvPhoneNo, tvEmailAddress, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String dataUser = Application.getPrefranceData("data");
        User user = User.exportUser(dataUser);
        //init  attribute
        tvName = findViewById(R.id.tvFullName);
        tvGender = findViewById(R.id.tvGender);
        tvDOB = findViewById(R.id.tvDOB);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);
        tvEmailAddress = findViewById(R.id.tvEmailAddress);
        tvAddress = findViewById(R.id.tvAddress);

        //setText for textView
        tvName.setText("" + user.getTen());
        tvGender.setText("" + user.getGioitinh());
        tvDOB.setText("" + user.getNgaysinh());
        tvPhoneNo.setText("" + user.getSdt());
        tvEmailAddress.setText("" + user.getEmail());
        tvAddress.setText("" + user.getDiachi());

    }
}