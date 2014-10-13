package com.temp.tiger.app.tabC;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;
import com.temp.tiger.app.tabC.Fragment3Adatper.onDistesesCheckedListener;

public class TabCFragment extends BaseFragment {
	Fragment3Adatper adapter;
	GridView gridview;
	String disteses[] = null;
	ArrayList<String> checkedArray = null;
	private FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_3, null);
		gridview = (GridView) view.findViewById(R.id.measur_disease);
		disteses = getResources().getStringArray(R.array.disease);
		adapter = new Fragment3Adatper(getActivity(), disteses);

		adapter.setOnCheckedListener(new onDistesesCheckedListener() {

			@Override
			public void onChecked(String dist, int position, boolean isChecked) {
				// TODO Auto-generated method stub
				if (checkedArray == null) {
					checkedArray = new ArrayList<String>();
				}
				if (isChecked && checkedArray.indexOf(dist) == -1) {
					checkedArray.add(dist);
				} else if (!isChecked && checkedArray.indexOf(dist) != -1) {
					checkedArray.remove(dist);
				}
			}
		});
		gridview.setAdapter(adapter);
		return view;
	}

}
