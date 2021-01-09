package vn.nlu.android.admin.adminUI_Fragment.sale;

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

import vn.nlu.android.admin.Activity.sale.Add;
import vn.nlu.android.admin.Adapter.AdapterSale;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Sale;

public class AdminFragment_Sale extends Fragment {

    private RecyclerView recycleview_sale;
    private ArrayList<Sale> data;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_sale, container, false);
        recycleview_sale = root.findViewById(R.id.recycleview_sale);
        data = new ArrayList<Sale>();
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
                        data.add(sale);
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

    private void setdata(ArrayList<Sale> data) {
        AdapterSale adpater = new AdapterSale(data, getContext());
        recycleview_sale.setAdapter(adpater);
        recycleview_sale.setHasFixedSize(false);
    }
}