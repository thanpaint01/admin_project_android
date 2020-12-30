package vn.nlu.android.admin.adminUI_Fragment.comment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.Adapter.AdapterComment;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Comment;

public class AdminFragment_Comment extends Fragment {

    private RecyclerView recycleview_comment;
    private ArrayList<Comment> data = new ArrayList<Comment>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_comment, container, false);
        recycleview_comment = root.findViewById(R.id.recycleview_comment);
        loadData();
        return root;
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallcomment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idbinhluan");
                        int iduser = jsonObject.getInt("iduser");
                        String productname = jsonObject.getString("tensp");
                        String active = jsonObject.getString("active");
                        String datacomment = jsonObject.getString("noidung");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);



                        Comment comment = new Comment(id, iduser,productname,datacomment, dataactive);
                        data.add(comment);
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

    private void setdata(ArrayList<Comment> data) {
        AdapterComment adpater = new AdapterComment(data, getContext());
        recycleview_comment.setAdapter(adpater);
        recycleview_comment.setHasFixedSize(false);
    }
}