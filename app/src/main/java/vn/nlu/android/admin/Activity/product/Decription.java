package vn.nlu.android.admin.Activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import vn.nlu.android.admin.R;

public class Decription extends AppCompatActivity {
    TextView productdescription_title1product,productdescription_title2product,productdescription_title3product,
            productdescription_info1product,productdescription_info2product,productdescription_info3product;
    ImageView productdescription_img1product,productdescription_img2product,productdescription_img3product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdescription);
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("data");
        String tieude1 = b.getString("tieude1");
        String tieude2 = b.getString("tieude2");
        String tieude3 = b.getString("tieude3");
        String chitiet1 = b.getString("chitiet1");
        String chitiet2 = b.getString("chitiet2");
        String chitiet3 = b.getString("chitiet3");
        String anh1 = b.getString("anh1");
        String anh2 = b.getString("anh2");
        String anh3 = b.getString("anh3");

        productdescription_title1product = (TextView) findViewById(R.id.productdescription_title1product);
        productdescription_title2product = (TextView) findViewById(R.id.productdescription_title2product);
        productdescription_title3product = (TextView) findViewById(R.id.productdescription_title3product);
        productdescription_info1product = (TextView) findViewById(R.id.productdescription_info1product);
        productdescription_info2product = (TextView) findViewById(R.id.productdescription_info2product);
        productdescription_info3product = (TextView) findViewById(R.id.productdescription_info3product);
        productdescription_img1product = (ImageView) findViewById(R.id.productdescription_img1product);
        productdescription_img2product = (ImageView) findViewById(R.id.productdescription_img2product);
        productdescription_img3product = (ImageView) findViewById(R.id.productdescription_img3product);

        productdescription_title1product.setText(tieude1);
        productdescription_title2product.setText(tieude2);
        productdescription_title3product.setText(tieude3);
        productdescription_info1product.setText(chitiet1);
        productdescription_info2product.setText(chitiet2);
        productdescription_info3product.setText(chitiet3);

        Picasso.get().load(anh1).placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24).into(productdescription_img1product);
        Picasso.get().load(anh2).placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24).into(productdescription_img2product);
        Picasso.get().load(anh3).placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24).into(productdescription_img3product);

    }
}