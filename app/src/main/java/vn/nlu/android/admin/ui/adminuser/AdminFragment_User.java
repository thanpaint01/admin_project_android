package vn.nlu.android.admin.ui.adminuser;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.ui.config.Server;
import vn.nlu.android.admin.ui.model.User;

public class AdminFragment_User extends Fragment {

    private TableLayout mTableLayout;
    private ArrayList<User> data = new ArrayList<User>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.adminfragment_user, container, false);
        // setup the table
        mTableLayout = (TableLayout) root.findViewById(R.id.admintable_user);
        mTableLayout.setStretchAllColumns(true);
        loadData();
        return root;
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.URLLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("error")) {
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            int iduser = jsonObject.getInt("iduser");
                            String taikhoan = jsonObject.getString("taikhoan");
                            String ten = jsonObject.getString("ten");
                            String img = jsonObject.getString("img");
                            String sdt = jsonObject.getString("sdt");
                            String diachi = jsonObject.getString("diachi");
                            String email = jsonObject.getString("email");
                            String gioitinh = jsonObject.getString("gioitinh");
                            String ngaysinh = jsonObject.getString("ngaysinh");
                            String quyen = jsonObject.getString("quyen");
                            String active = jsonObject.getString("active");

                            int dataquyen = 0;
                            int dataactive = 0;
                            if (quyen.equals("")) {
                                dataquyen = 1;
                            }
                            if (active.equals("")) {
                                dataactive = 1;
                            }
                            User user = new User(iduser, taikhoan, ten, img, sdt, diachi, email, gioitinh, ngaysinh, dataquyen, dataactive);
                            data.add(user);
                        }
                        setdata(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        System.out.println(data.size());
    }
}