package vn.nlu.android.admin.adminUI_Fragment.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import vn.nlu.android.admin.Adapter.AdapterRangeOrder;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.model.Order;

public class AdminFragment_Order2 extends Fragment {

    private ArrayList<Order> data = new ArrayList<Order>();
    TabLayout tabLayout;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_order2, container, false);
        tabLayout = root.findViewById(R.id.tablayout);
        viewPager = root.findViewById(R.id.viewpager);

        AdapterRangeOrder adapterRangeOrder = new AdapterRangeOrder(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapterRangeOrder);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        return root;
    }
}