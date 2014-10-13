
package com.temp.tiger.app.tabC;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;

public class TreatmentFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.illness_treatment, null);

        return view;// super.onCreateView(inflater, container,
                    // savedInstanceState);
    }

}
