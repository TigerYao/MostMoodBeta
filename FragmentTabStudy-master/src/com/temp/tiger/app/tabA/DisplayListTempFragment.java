
package com.temp.tiger.app.tabA;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;

public class DisplayListTempFragment extends BaseFragment {
    ListView display_temp_list;
    private DisplayListTempAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.display_temp, null);
        initView(view);
        return view;
    }

    /**
     * @param view
     */
    private void initView(View view) {
        ImageView back = (ImageView) view.findViewById(R.id.addp);
        back.setId(R.id.tab_back);
        back.setImageResource(R.drawable.selcet_bkup_btn);
        ImageView fresh = (ImageView) view.findViewById(R.id.fresh);
        fresh.setId(R.id.tem_list_fresh);

        display_temp_list = (ListView) view.findViewById(R.id.displaytem_list);

        adapter = new DisplayListTempAdapter(mActivity, null);
        if (display_temp_list != null)
            display_temp_list.setAdapter(adapter);
    }
}
