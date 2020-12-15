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

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.ui.model.User;

public class AdminFragment_User extends Fragment {

    private TableLayout mTableLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.adminfragment_user, container, false);
        // setup the table
        mTableLayout = (TableLayout) root.findViewById(R.id.admintable_user);
        mTableLayout.setStretchAllColumns(true);
        loadData();
        return root;
    }
    public void loadData() {
        UserViewModel uvm = new UserViewModel();
        ArrayList<User> data = uvm.getUser(1,getContext());
        System.out.println("data size here ... "+data.size());
    }
}