package vn.nlu.android.admin.adminUI_Fragment.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.Adapter.AdapterUser;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.User;

public class AdminFragment_User extends Fragment {

    private RecyclerView recycleview_user;
    private ArrayList<User> data = new ArrayList<User>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_user, container, false);
        recycleview_user = root.findViewById(R.id.recycleview_user);
        loadData();
        return root;
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int iduser = jsonObject.getInt("iduser");
                        String taikhoan = jsonObject.getString("taikhoan");
                        String matkhau = jsonObject.getString("matkhau");

                        String ten = jsonObject.getString("ten");
                        ten = isSet(ten);
                        String img = jsonObject.getString("img");
                        if (img.equals("") || img.equals("null")) {
                            img = "img/user/No-Image.png";
                        }
                        String sdt = jsonObject.getString("sdt");
                        sdt = isSet(sdt);
                        String diachi = jsonObject.getString("diachi");
                        diachi = isSet(diachi);
                        String email = jsonObject.getString("email");
                        email = isSet(email);
                        String gioitinh = jsonObject.getString("gioitinh");
                        gioitinh = isSet(gioitinh);
                        String ngaysinh = jsonObject.getString("ngaysinh");
                        ngaysinh = isSet(ngaysinh);

                        String quyen = jsonObject.getString("quyen");
                        String active = jsonObject.getString("active");

                        int dataquyen = 0, dataactive = 0;
                        if (quyen.equals("") || quyen.equals("null")) {
                            dataquyen = 1;
                        } else if (quyen != null) dataquyen = Integer.parseInt(quyen);

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                        User user = new User(iduser, taikhoan, matkhau, ten, Server.HOST + img, sdt, diachi, email, gioitinh, ngaysinh, dataquyen, dataactive);
                        data.add(user);
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

    private void setdata(ArrayList<User> data) {
        AdapterUser adpater = new AdapterUser(data, getContext());
        recycleview_user.setAdapter(adpater);
        recycleview_user.setHasFixedSize(false);
    }

    private String isSet(String s) {
        if (s.equals("") || s.equals("null")) {
            s = "Not Set";
        }
        return s;
    }


}