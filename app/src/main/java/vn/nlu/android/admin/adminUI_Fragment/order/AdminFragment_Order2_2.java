package vn.nlu.android.admin.adminUI_Fragment.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.Adapter.AdapterOrder;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Order;
import vn.nlu.android.admin.model.OrderData;

public class AdminFragment_Order2_2 extends Fragment {

    private ArrayList<Order> data;
    RecyclerView recycleview_order2_2;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_order2_2, container, false);
        recycleview_order2_2 = root.findViewById(R.id.recycleview_order2_2);
        data = new ArrayList<Order>();
        loadData();
        return root;
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        HashMap<Integer, Integer> saveID = new HashMap<Integer, Integer>();
        ArrayList<Integer> checkexistIDdonhang = new ArrayList<Integer>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.admin_donhang2+"?idtinhtrang=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response "+response);
                if(response.length()==2){
                    loadData();
                } else{
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        int back = 0;
                        for (int i = 0; i < jsonArr.length(); i++) {
                            boolean isExist = false;
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            int iddonhang = jsonObject.getInt("iddonhang");
                            String iduser = jsonObject.getString("iduser");
                            String idtinhtrang = jsonObject.getString("idtinhtrang");
                            String ngaytao = jsonObject.getString("ngaytao");
                            String diachi = jsonObject.getString("diachi");
                            String hinhthuctt = jsonObject.getString("hinhthuctt");
                            String ten = jsonObject.getString("ten");
                            String sdt = jsonObject.getString("sdt");
                            String img = jsonObject.getString("img");

                            int idchitiet = jsonObject.getInt("idchitiet");
                            String idsp = jsonObject.getString("idsp");
                            String soluong = jsonObject.getString("soluong");
                            String tamtinh = jsonObject.getString("tamtinh");
                            String giamgia = jsonObject.getString("giamgia");
                            String tongcong = jsonObject.getString("tongcong");
                            String gia = jsonObject.getString("gia");
                            String tensp = jsonObject.getString("tensp");

                            OrderData orderData = new OrderData(idchitiet,iddonhang, idsp, soluong, tamtinh, giamgia, tongcong, gia,img,tensp);
                            Order order;

                            if (checkexistIDdonhang.contains(iddonhang)) {
                                // exist
                                back++;
                                Integer index = saveID.get(iddonhang);
                                order = data.get(index);
                                isExist = true;

                            } else {
                                // not exist
                                order = new Order(iddonhang, iduser, idtinhtrang, ngaytao, diachi, hinhthuctt, ten, sdt,img);
                                saveID.put(iddonhang, i - back);
                                checkexistIDdonhang.add(iddonhang);
                                isExist = false;
                            }
                            ArrayList<OrderData> orderdata = order.getData();
                            orderdata.add(orderData);
                            order.setData(orderdata);
                            if (!isExist) {
                                data.add(order);
                            }
                        }
                        // SET DATA HERE
                        setdata(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadData();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void setdata(ArrayList<Order> data) {
        AdapterOrder adpater = new AdapterOrder(data,getContext());
        recycleview_order2_2.setAdapter(adpater);
        recycleview_order2_2.setHasFixedSize(false);
    }


}