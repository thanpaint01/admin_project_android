package vn.nlu.android.admin.adminUI_Fragment.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.Activity.product.Add;
import vn.nlu.android.admin.Adapter.AdapterProduct;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Product;

public class AdminFragment_Product extends Fragment {

    private RecyclerView recycleview_product;
    private ArrayList<Product> data = new ArrayList<Product>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_product, container, false);
        recycleview_product = root.findViewById(R.id.recycleview_product);
        loadData();
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = root.getContext();
                Intent i = new Intent(context, Add.class);
                context.startActivity(i);
            }
        });
        return root;
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallproduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int idsp = jsonObject.getInt("idsp");

                        String img = jsonObject.getString("img");
                        if (img.equals("") || img.equals("null")) {
                            img = "img/user/No-Image.png";
                        }
                        String img01 = jsonObject.getString("img01");
                        if (img01.equals("") || img01.equals("null")) {
                            img01 = "img/user/No-Image.png";
                        }
                        String img02 = jsonObject.getString("img02");
                        if (img02.equals("") || img02.equals("null")) {
                            img02 = "img/user/No-Image.png";
                        }
                        String img03 = jsonObject.getString("img03");
                        if (img03.equals("") || img03.equals("null")) {
                            img03 = "img/user/No-Image.png";
                        }
                        String img04 = jsonObject.getString("img04");
                        if (img04.equals("") || img04.equals("null")) {
                            img04 = "img/user/No-Image.png";
                        }

                        String ten = jsonObject.getString("ten");
                        ten = isSet(ten);
                        String tenhang = jsonObject.getString("tenhang");
                        tenhang = isSet(tenhang);

                        String gia = jsonObject.getString("gia");
                        if (gia.equals("") || gia.equals("null")) {
                            gia = "0";
                        }
                        String soluong = jsonObject.getString("soluong");
                        if (soluong.equals("") || soluong.equals("null")) {
                            soluong = "0";
                        }
                        String baohanh = jsonObject.getString("baohanh");
                        if (baohanh.equals("") || baohanh.equals("null")) {
                            baohanh = "0";
                        }

                        String sale = jsonObject.getString("sale");
                        sale = isSet(sale);
                        String ram = jsonObject.getString("ram");
                        ram = isSet(ram);
                        String rom = jsonObject.getString("rom");
                        rom = isSet(rom);
                        String pin = jsonObject.getString("pin");
                        pin = isSet(pin);
                        String kichthuoc = jsonObject.getString("kichthuoc");
                        kichthuoc = isSet(kichthuoc);
                        String cameratruoc = jsonObject.getString("cameratruoc");
                        cameratruoc = isSet(cameratruoc);
                        String camerasau = jsonObject.getString("camerasau");
                        camerasau = isSet(camerasau);

                        String tinhtrang = jsonObject.getString("tinhtrang");
                        String active = jsonObject.getString("active");

                        int datatinhtrang = 0, dataactive = 0;
                        if (tinhtrang.equals("") || tinhtrang.equals("null")) {
                            datatinhtrang = 0;
                        } else if (tinhtrang != null) datatinhtrang = Integer.parseInt(tinhtrang);
                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        String tieude1 = jsonObject.getString("tieude1");
                        String tieude2 = jsonObject.getString("tieude2");
                        String tieude3 = jsonObject.getString("tieude3");
                        String chitiet1 = jsonObject.getString("chitiet1");
                        String chitiet2 = jsonObject.getString("chitiet2");
                        String chitiet3 = jsonObject.getString("chitiet3");
                        String anh1 = jsonObject.getString("anh1");
                        String anh2 = jsonObject.getString("anh2");
                        String anh3 = jsonObject.getString("anh3");


                        Product p = new Product(idsp, Server.HOST+img01, ten, tenhang, sale, gia, soluong,
                                 ram,  rom,  pin,  baohanh,  Server.HOST+img01,  Server.HOST+img02,
                                Server.HOST+img03,  Server.HOST+img04,  kichthuoc,  cameratruoc,  camerasau,
                                dataactive, datatinhtrang , tieude1,tieude2,tieude3,chitiet1,chitiet2,chitiet3,
                                Server.HOST+anh1,Server.HOST+anh2,Server.HOST+anh3);
                        data.add(p);
                    }
                    // SET DATA HERE
                    setdata(data);

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

    private void setdata(ArrayList<Product> data) {
        AdapterProduct adpater = new AdapterProduct(data, getContext());
        recycleview_product.setAdapter(adpater);
        recycleview_product.setHasFixedSize(false);
    }
    private String isSet(String s){
        if (s.equals("") || s.equals("null")) {
            s = "Not Set";
        }
        return s;
    }

}