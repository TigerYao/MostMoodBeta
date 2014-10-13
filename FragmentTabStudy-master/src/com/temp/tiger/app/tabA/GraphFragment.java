
package com.temp.tiger.app.tabA;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;
import com.yhz.tem.yh.ch.temp.view.TemperatureViewGraph;

public class GraphFragment extends BaseFragment {
    // SeekBar seekBar;

    TemperatureViewGraph tempGraph;

    RadioGroup changeTimeGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.grpha, null);
        initView(view);
        tempGraph.setType(24);
        float points[][] = {
                {
                        35.8f, 1
                }, {
                        36f, 2
                }, {
                        37, 3
                }, {
                        36.8f, 4
                },
                {
                        35.8f, 5
                }, {
                        38, 6
                }, {
                        39, 8
                }, {
                        36, 11
                }, {
                        38, 11.2f
                },
                {
                        36, 12
                }
        };
        tempGraph.setCurrentTemps(points);

        changeTimeGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub
                        switch (checkedId) {
                            case R.id.dayid:
                                break;
                            case R.id.weekid:
                                break;
                            case R.id.mouthid:
                                break;
                            default:
                                break;
                        }
                    }
                });

        return view;// super.onCreateView(inflater, container,
                    // savedInstanceState);
    }

    /**
     * @param view
     */
    private void initView(View view) {
        changeTimeGroup = (RadioGroup) view.findViewById(R.id.changeTime);
        tempGraph = (TemperatureViewGraph) view.findViewById(R.id.scrollerTem);
        ImageView back = (ImageView) view.findViewById(R.id.addp);
        back.setId(R.id.tab_back);
        back.setImageResource(R.drawable.selcet_bkup_btn);
        ImageView fresh = (ImageView) view.findViewById(R.id.fresh);
        fresh.setId(R.id.tem_list);
        fresh.setImageResource(R.drawable.temp_list);
    }

}
