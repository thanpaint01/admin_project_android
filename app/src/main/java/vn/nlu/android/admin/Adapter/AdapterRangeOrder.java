package vn.nlu.android.admin.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.nlu.android.admin.adminUI_Fragment.order.AdminFragment_Order2_1;
import vn.nlu.android.admin.adminUI_Fragment.order.AdminFragment_Order2_2;
import vn.nlu.android.admin.adminUI_Fragment.order.AdminFragment_Order2_3;
import vn.nlu.android.admin.adminUI_Fragment.order.AdminFragment_Order2_4;


public class AdapterRangeOrder extends FragmentStatePagerAdapter {
    private List<String> listTab;
    private AdminFragment_Order2_1 adminFragment_Order2_1;
    private AdminFragment_Order2_2 adminFragment_Order2_2;
    private AdminFragment_Order2_3 adminFragment_Order2_3;
    private AdminFragment_Order2_4 adminFragment_Order2_4;


    public AdapterRangeOrder(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        listTab = new ArrayList<>();
        listTab.add("Waiting Accept");
        listTab.add("Delivering");
        listTab.add("Delivered");
        listTab.add("Cancel");
        adminFragment_Order2_1 = new AdminFragment_Order2_1();
        adminFragment_Order2_2 = new AdminFragment_Order2_2();
        adminFragment_Order2_3 = new AdminFragment_Order2_3();
        adminFragment_Order2_4 = new AdminFragment_Order2_4();
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return adminFragment_Order2_1;
            case 1:
                return adminFragment_Order2_2;
            case 2:
                return adminFragment_Order2_3;
            case 3:
                return adminFragment_Order2_4;
            default:
                return adminFragment_Order2_1;
        }
    }

    @Override
    public int getCount() {
        return listTab.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab.get(position);
    }
}
