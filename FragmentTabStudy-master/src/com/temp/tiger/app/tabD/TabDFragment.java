
package com.temp.tiger.app.tabD;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewSwitcher.ViewFactory;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;

public class TabDFragment extends BaseFragment implements
        OnCheckedChangeListener, OnClickListener {
    private ViewPager viewpage;
    private List<View> pageViews;
    private RadioGroup pageTitles;
    private ListView illness_list, medicine_list;
    private ViewPage4Adapter pageAdapter;
    private ImageSwitcher mISwitche;
    private int imageId[] = null;
    private ImageButton leftB, rightB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        imageId = new int[] {
                R.drawable.baby, R.drawable.statiu,
                R.drawable.add, R.drawable.arrow, R.drawable.backup
        };

        View view = inflater.inflate(R.layout.fragment_4, null);
        viewpage = (ViewPager) view.findViewById(R.id.vPager);
        pageTitles = (RadioGroup) view.findViewById(R.id.pageTitles);
        pageTitles.setOnCheckedChangeListener(this);
        illness_list = new ListView(mActivity);
        medicine_list = new ListView(mActivity);
        ArrayAdapter<String> adapter_illness = new ArrayAdapter<String>(
                getActivity(), R.layout.illnessitem, R.id.tv, getResources()
                        .getStringArray(R.array.settings_items));
        ArrayAdapter<String> adapter_medicines = new ArrayAdapter<String>(
                getActivity(), R.layout.illnessitem, R.id.tv, getResources()
                        .getStringArray(R.array.settings_items));
        illness_list.setBackgroundResource(R.drawable.bk_all);
        illness_list.setAdapter(adapter_illness);

        medicine_list.setBackgroundResource(R.drawable.bk_all);
        medicine_list.setAdapter(adapter_medicines);

        initViewpage();
        mISwitche = (ImageSwitcher) view.findViewById(R.id.imageswitcher);

        mISwitche.setFactory(new ViewFactory() {

            @Override
            public View makeView() {
                ImageView img = new ImageView(getActivity());
                img.setBackgroundColor(Color.TRANSPARENT);// ���ñ���
                img.setScaleType(ImageView.ScaleType.CENTER);// ���ж���
                img.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                return img;
            }
        });

        mISwitche.setImageResource(imageId[index]);
        leftB = (ImageButton) view.findViewById(R.id.baike_click_left);
        leftB.setOnClickListener(this);
        rightB = (ImageButton) view.findViewById(R.id.baike_click_right);
        rightB.setOnClickListener(this);
        return view;
    }

    private void initViewpage() {
        // TODO Auto-generated method stub
        pageViews = new ArrayList<View>();
        pageViews.add(illness_list);
        pageViews.add(medicine_list);
        pageAdapter = new ViewPage4Adapter(pageViews);
        viewpage.setAdapter(pageAdapter);
        viewpage.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    int currentItem = 0;

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            RadioButton radio = (RadioButton) pageTitles.getChildAt(position);
            if (!radio.isChecked())
                radio.setChecked(true);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.text1:
                currentItem = 0;
                break;
            case R.id.text2:
                currentItem = 1;
                break;
            case R.id.text3:
                currentItem = 2;
                break;
            default:
                break;
        }
        viewpage.setCurrentItem(currentItem, true);
    }

    int index = 0;

    private void checkEnable() {
        if (index == 0) {
            leftB.setClickable(false);
        } else
            leftB.setClickable(true);
        if (index == imageId.length - 1) {
            rightB.setClickable(false);
        } else
            rightB.setClickable(true);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.baike_click_left:
                if (index > 0)
                    index--;
                break;
            case R.id.baike_click_right:
                index++;
                break;
            default:
                break;
        }
        checkEnable();
        mISwitche.setImageResource(imageId[index]);

    }

}
