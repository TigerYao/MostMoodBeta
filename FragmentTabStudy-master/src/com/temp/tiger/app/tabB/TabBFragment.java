
package com.temp.tiger.app.tabB;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;
import com.yhz.tem.yh.ch.temp.view.TemperatureView;

public class TabBFragment extends BaseFragment {
    private ImageView edituserStatiu;
    private TextView edituserName;
    private TemperatureView tempre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_2, null);
        edituserStatiu = (ImageView) view.findViewById(R.id.edituserstatiu);
        edituserName = (TextView) view.findViewById(R.id.editusername);
        tempre = (TemperatureView) view.findViewById(R.id.tempera);
        return view;
    }

}
