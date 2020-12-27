package vn.nlu.android.admin.adminUI.tag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import vn.nlu.android.admin.Adapter.AdapterBattery;
import vn.nlu.android.admin.Adapter.AdapterPrice;
import vn.nlu.android.admin.Adapter.AdapterRam;
import vn.nlu.android.admin.Adapter.AdapterRom;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Tag;

public class AdminFragment_Tag extends Fragment {

    private RecyclerView recycleview_ram,recycleview_rom,recycleview_pin,recycleview_price;
    private TextView tag_ramtitle,tag_romtitle,tag_batterytitle,tag_pricetitle;
    private ArrayList<Tag> dataram = new ArrayList<Tag>();
    private ArrayList<Tag> datarom = new ArrayList<Tag>();
    private ArrayList<Tag> databattery = new ArrayList<Tag>();
    private ArrayList<Tag> dataprice = new ArrayList<Tag>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_tag, container, false);

        tag_ramtitle = root.findViewById(R.id.tag_ramtitle);
        tag_romtitle = root.findViewById(R.id.tag_romtitle);
        tag_batterytitle = root.findViewById(R.id.tag_batterytitle);
        tag_pricetitle = root.findViewById(R.id.tag_pricetitle);

        recycleview_ram = root.findViewById(R.id.recycleview_ram);
        recycleview_rom = root.findViewById(R.id.recycleview_rom);
        recycleview_pin = root.findViewById(R.id.recycleview_pin);
        recycleview_price = root.findViewById(R.id.recycleview_price);

        loadDataRam();
        loadDataRom();
        loadDataBattery();
        loadDataPrice();

        return root;
    }

    public void loadDataRam() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallram, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idram");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                            Tag ramRomBattery = new Tag(id,dungluong,dataactive);
                            dataram.add(ramRomBattery);
                    }
                    // SET DATA HERE
                        setdataRam(dataram);
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

    public void loadDataRom() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallrom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idrom");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                            Tag rom = new Tag(id,dungluong,dataactive);
                            datarom.add(rom);
                    }
                    // SET DATA HERE
                        setdataRom(datarom);
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

    public void loadDataBattery() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallpin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idpin");
                        String dungluong = jsonObject.getString("dungluong");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                            Tag pin = new Tag(id,dungluong,dataactive);
                            databattery.add(pin);
                    }
                    // SET DATA HERE
                        setdataBattery(databattery);
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

    public void loadDataPrice() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getallprice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        int id = jsonObject.getInt("idtgia");
                        String dungluong = jsonObject.getString("khoanggia");
                        String active = jsonObject.getString("active");

                        int dataactive = 0;

                        if (active.equals("") || active.equals("null")) {
                            dataactive = 1;
                        } else if (active != null) dataactive = Integer.parseInt(active);
                            Tag price = new Tag(id,dungluong,dataactive);
                            dataprice.add(price);
                    }
                    // SET DATA HERE
                        setdataPrice(dataprice);
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

    private void setdataRam(ArrayList<Tag> data) {
        AdapterRam adpaterram = new AdapterRam(data,getContext());
        recycleview_ram.setAdapter(adpaterram);
        recycleview_ram.setHasFixedSize(false);
        //
        // if true it will create scroll in recycleview
        //
        tag_ramtitle.setText("Ram");
    }

    private void setdataRom(ArrayList<Tag> data) {
        AdapterRom adpaterrom = new AdapterRom(data,getContext());
        recycleview_rom.setAdapter(adpaterrom);
        recycleview_rom.setHasFixedSize(false);
        tag_romtitle.setText("Rom");
    }

    private void setdataBattery(ArrayList<Tag> data) {
        AdapterBattery adapterbattery = new AdapterBattery(data,getContext());
        recycleview_pin.setAdapter(adapterbattery);
        recycleview_pin.setHasFixedSize(false);
        tag_batterytitle.setText("Battery");
    }

    private void setdataPrice(ArrayList<Tag> data) {
        AdapterPrice adpaterprice = new AdapterPrice(data,getContext());
        recycleview_price.setAdapter(adpaterprice);
        recycleview_price.setHasFixedSize(false);
        tag_pricetitle.setText("Price");
    }

}