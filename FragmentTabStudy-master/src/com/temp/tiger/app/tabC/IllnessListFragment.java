
package com.temp.tiger.app.tabC;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.AppConstants;
import com.temp.tiger.app.base.BaseFragment;

public class IllnessListFragment extends BaseFragment implements
        OnItemClickListener {
    ListView illness_list;
    String[] list;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.illnesslist, null);
        illness_list = (ListView) view.findViewById(R.id.illness_list);
        list = getResources().getStringArray(R.array.test_result);
        TextView tv = (TextView) view.findViewById(R.id.empty_list_view);
        illness_list.setEmptyView(tv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.illnessitem, R.id.tv, getResources().getStringArray(
                        R.array.test_result));
        illness_list.setAdapter(adapter);
        illness_list.setOnItemClickListener(this);
        return view;// super.onCreateView(inflater, container,
                    // savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,
            int position, long id) {
        // TODO Auto-generated method stub
        s = list[position];
        mActivity.pushFragments(AppConstants.TAB_C, new TreatmentFragment(), true,
                true);
    }

}
