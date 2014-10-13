
package com.temp.tiger.app.tabA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.research.fragmenttabstudy.R;
import com.temp.tiger.app.base.BaseFragment;
import com.yhz.tem.yh.ch.temp.view.GraphicsBitmapUtils;

public class AddUserFregment extends BaseFragment {

    ImageView chose_statiu;

    EditText username;
    Button userbirthday;
    RadioGroup sexCheck;
    Calendar calendar;
    TextView changed_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        View view = inflater.inflate(R.layout.addview, null);
        ImageView back = (ImageView) view.findViewById(R.id.addp);
        back.setId(R.id.tab_back);
        back.setImageResource(R.drawable.selcet_bkup_btn);
        ImageView fresh = (ImageView) view.findViewById(R.id.fresh);
        fresh.setId(R.id.tem_list_fresh);
        chose_statiu = (ImageView) view.findViewById(R.id.chose_statiu);
        username = (EditText) view.findViewById(R.id.add_user_name);
        userbirthday = (Button) view.findViewById(R.id.add_user_birthday);
        userbirthday.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog dateDialog = new DatePickerDialog(mActivity,
                        new OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                    int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub
                                int age = calendar.get(Calendar.YEAR) - year;

                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat fromat = new SimpleDateFormat(
                                        "yyyy-MM-dd");
                                String birthday = fromat.format(new Date(calendar
                                        .getTimeInMillis()));
                                userbirthday.setText(birthday);

                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show();
            }
        });
        sexCheck = (RadioGroup) view.findViewById(R.id.sexCheck);
        changed_name = (TextView) view.findViewById(R.id.changed_name);

        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                    int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null && arg0.length() > 0) {
                    changed_name.setText(arg0);
                } else
                    changed_name.setText("");
            }
        });
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

        return view;
    }

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

                        chose_statiu.setImageBitmap(photo);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    ProgressDialog prd;

    private void showProgressDialog() {
        prd = new ProgressDialog(mActivity);
        prd.setMessage("正在添加用户");
        prd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prd.show();
    }
}
