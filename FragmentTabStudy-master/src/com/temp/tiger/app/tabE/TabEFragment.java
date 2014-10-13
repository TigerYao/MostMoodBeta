
package com.temp.tiger.app.tabE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;

public class TabEFragment extends BaseFragment implements OnItemClickListener {
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_5, null);
        list = (ListView) view.findViewById(R.id.fragment5_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.illnessitem, R.id.tv, getResources().getStringArray(
                        R.array.settings_items));
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

}
