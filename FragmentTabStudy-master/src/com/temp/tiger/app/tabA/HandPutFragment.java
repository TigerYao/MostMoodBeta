
package com.temp.tiger.app.tabA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;
import com.yhz.tem.yh.ch.temp.view.GraphicsBitmapUtils;

public class HandPutFragment extends BaseFragment {
    EditText temp;

    ImageView edituserStatiu;
    TextView edituserName;
    private Button userbirthday;
    private RadioGroup sexCheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.handputfragment, null);
        ImageView back = (ImageView) view.findViewById(R.id.addp);
        back.setId(R.id.tab_back);
        back.setImageResource(R.drawable.selcet_bkup_btn);
        ImageView fresh = (ImageView) view.findViewById(R.id.fresh);
        fresh.setId(R.id.tem_list_fresh);
        temp = (EditText) view.findViewById(R.id.add_user_temp);
        edituserStatiu = (ImageView) view.findViewById(R.id.chose_statiu);
        edituserName = (TextView) view.findViewById(R.id.changed_name);
        userbirthday = (Button) view.findViewById(R.id.add_user_birthday);
        sexCheck = (RadioGroup) view.findViewById(R.id.sexCheck);
        sexCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.boy_check:
                        break;
                    case R.id.gril_check:
                        break;
                    default:
                        break;
                }
            }
        });
        userbirthday.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                calendar = Calendar.getInstance();
                DatePickerDialog dateDialog = new DatePickerDialog(mActivity,
                        new OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                    int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                int age = calendar.get(Calendar.YEAR) - year;

                                // values.put(UserInfo.Columns.AGE, age);
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat fromat = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String birthday = fromat.format(new Date(calendar
                                        .getTimeInMillis()));
                                userbirthday.setText(birthday);

                                // values.put(UserInfo.Columns.BIRTHDAY,
                                // birthday);
                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show();
            }
        });
        return view;
    }

    Calendar calendar;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GraphicsBitmapUtils.PHOTO_REQUEST_TAKEPHOTO:
                GraphicsBitmapUtils
                        .startPhotoZoom(mActivity, Uri.fromFile(mActivity.tempFile), 150);
                break;

            case GraphicsBitmapUtils.PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    GraphicsBitmapUtils.startPhotoZoom(mActivity, data.getData(), 150);
                }
                break;

            case GraphicsBitmapUtils.PHOTO_REQUEST_CUT:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = bundle.getParcelable("data");
                        photo = GraphicsBitmapUtils.getRoundedCornerBitmap(mActivity,
                                photo);
                        edituserStatiu.setImageBitmap(photo);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
