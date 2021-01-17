package vn.nlu.android.admin.adminUI_Fragment.slide;

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
import com.android.volley.DefaultRetryPolicy;
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

import vn.nlu.android.admin.Activity.slide.Add;
import vn.nlu.android.admin.Adapter.AdapterSlide;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Sale;
import vn.nlu.android.admin.model.Slide;

public class AdminFragment_Slide extends Fragment {

    private RecyclerView recycleview_slide;
    private ArrayList<Slide> data = new ArrayList<Slide>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_slide, container, false);
        recycleview_slide = root.findViewById(R.id.recycleview_slidedetail);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallslide, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()==2){
                    loadData();
                } else{
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            int id = jsonObject.getInt("idslide");

                            String img = jsonObject.getString("srcslide");
                            if (img.equals("") || img.equals("null")) {
                                img = "img/user/No-Image.png";
                            }

                            String active = jsonObject.getString("active");

                            int dataactive = 0;

                            if (active.equals("") || active.equals("null")) {
                                dataactive = 1;
                            } else if (active != null) dataactive = Integer.parseInt(active);
                            Slide slide = new Slide(id,Server.HOST + img, dataactive);
                            data.add(slide);
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void setdata(ArrayList<Slide> data) {
        AdapterSlide adpater = new AdapterSlide(data, getContext());
        recycleview_slide.setAdapter(adpater);
        recycleview_slide.setHasFixedSize(false);
    }
    public ArrayList<Slide> getData(){
        loadData();
        return data;
    }
}