
package com.temp.tiger.app.base;

import java.io.File;
import java.util.HashMap;
import java.util.Stack;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.research.fragmenttabstudy.R;
import com.rfstar.kevin.BaseActivity;
import com.rfstar.kevin.rfstar.kevin.params.BLEDevice.RFStarBLEBroadcastReceiver;
import com.rfstar.kevin.rfstar.kevin.service.RFStarBLEService;
import com.temp.tiger.app.tabA.AddUserFregment;
import com.temp.tiger.app.tabA.DisplayListTempFragment;
import com.temp.tiger.app.tabA.GraphFragment;
import com.temp.tiger.app.tabA.HandPutFragment;
import com.temp.tiger.app.tabA.TabAFragment;
import com.temp.tiger.app.tabB.TabBFragment;
import com.temp.tiger.app.tabC.IllnessListFragment;
import com.temp.tiger.app.tabC.TabCFragment;
import com.temp.tiger.app.tabD.TabDFragment;
import com.temp.tiger.app.tabE.TabEFragment;
import com.yhz.tem.yh.ch.temp.view.GraphicsBitmapUtils;

public class AppMainTabActivity extends BaseActivity implements OnClickListener,
        RFStarBLEBroadcastReceiver {
    /* Your Tab host */
    private TabHost mTabHost;

    /* A HashMap of stacks, where we use tab identifier as keys.. */
    private HashMap<String, Stack<Fragment>> mStacks;

    /* Save current tabs identifier in this.. */
    private String mCurrentTab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_tab_fragment_layout);
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstants.TAB_A, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_B, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_C, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_D, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_E, new Stack<Fragment>());
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();
        initializeTabs();
    }

    /**
     * 给Tab按钮设置图标和文�?
     */
    private int mImageViewArray[] = {
            R.drawable.tab_temp_btn,
            R.drawable.tab_message_btn, R.drawable.tab_testsilk_btn,
            R.drawable.tab_baike_btn, R.drawable.tab_settings_btn
    };
    private String mTextviewArray[] = {
            "温度计", "用药提醒", "测病症", "百科", "设置"
    };

    private View getTabItemView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tab_item_view, null);
        // ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        // imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);
        textView.setBackgroundResource(mImageViewArray[index]);

        return view;
    }

    public void initializeTabs() {
        /* Setup your tab icons and content views.. Nothing special in this.. */
        TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_A);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(getTabItemView(0));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(AppConstants.TAB_B);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(getTabItemView(1));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(AppConstants.TAB_C);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(getTabItemView(2));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(AppConstants.TAB_D);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(getTabItemView(3));
        mTabHost.addTab(spec);
        spec = mTabHost.newTabSpec(AppConstants.TAB_E);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(getTabItemView(4));
        mTabHost.addTab(spec);
    }

    /* Comes here when user switch tab, or we do programmatically */
    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {
            /* Set current tab.. */
            mCurrentTab = tabId;

            if (mStacks.get(tabId).size() == 0) {
                /*
                 * First time this tab is selected. So add first fragment of
                 * that tab. Dont need animation, so that argument is false. We
                 * are adding a new fragment which is not present in stack. So
                 * add to stack is true.
                 */
                if (tabId.equals(AppConstants.TAB_A)) {
                    pushFragments(tabId, new TabAFragment(), false, true);
                } else if (tabId.equals(AppConstants.TAB_B)) {
                    pushFragments(tabId, new TabBFragment(), false, true);
                } else if (tabId.equals(AppConstants.TAB_C)) {
                    pushFragments(tabId, new TabCFragment(), false, true);
                } else if (tabId.equals(AppConstants.TAB_D)) {
                    pushFragments(tabId, new TabDFragment(), false, true);
                } else if (tabId.equals(AppConstants.TAB_E)) {
                    pushFragments(tabId, new TabEFragment(), false, true);
                }
            } else {
                pushFragments(tabId, mStacks.get(tabId).lastElement(), false, false);
            }
        }
    };

    /*
     * Might be useful if we want to switch tab programmatically, from inside
     * any of the fragment.
     */
    public void setCurrentTab(int val) {
        mTabHost.setCurrentTab(val);
    }

    /*
     * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
     * show, in tab identified by tag shouldAnimate -> should animate
     * transaction. false when we switch tabs, or adding first fragment to a tab
     * true when when we are pushing more fragment into navigation stack.
     * shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)).
     * false when we are switching tabs (except for the first time) true in all
     * other cases.
     */
    public void pushFragments(String tag, Fragment fragment, boolean shouldAnimate,
            boolean shouldAdd) {
        if (shouldAdd)
            mStacks.get(tag).push(fragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate)
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.realtabcontent, fragment);
        ft.commit();
    }

    public void popFragments() {
        /*
         * Select the second last fragment in current tab's stack.. which will
         * be shown after the fragment transaction given below
         */
        Fragment fragment = mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

        /* pop current fragment from stack.. */
        mStacks.get(mCurrentTab).pop();

        /*
         * We have the target fragment in hand.. Just show it.. Show a standard
         * navigation animation
         */
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.realtabcontent, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (((BaseFragment) mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false) {
            /*
             * top fragment in current tab doesn't handles back press, we can do
             * our thing, which is if current tab has only one fragment in
             * stack, ie first fragment is showing for this tab. finish the
             * activity else pop to previous fragment in stack for the same tab
             */
            if (mStacks.get(mCurrentTab).size() == 1) {
                super.onBackPressed(); // or call finish..
            } else {
                popFragments();
            }
        } else {
            // do nothing.. fragment already handled back button press.
        }
    }

    /*
     * Imagine if you wanted to get an image selected using ImagePicker intent
     * to the fragment. Ofcourse I could have created a public function in that
     * fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mStacks.get(mCurrentTab).size() == 0) {
            return;
        }

        /* Now current fragment on screen gets onActivityResult callback.. */
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.pictureline:
                pushFragments(AppConstants.TAB_A, new GraphFragment(), true, true);
                break;
            case R.id.tab_back:
                onBackPressed();
                break;
            case R.id.addp:
                pushFragments(AppConstants.TAB_A, new AddUserFregment(), true, true);
                break;
            case R.id.fresh:
                break;
            case R.id.puttem:
                pushFragments(AppConstants.TAB_A, new HandPutFragment(), true, true);
                break;
            case R.id.chose_statiu:
                showChoseImage();
                break;
            case R.id.tem_list:
                pushFragments(AppConstants.TAB_A, new DisplayListTempFragment(), true, true);
                break;
            case R.id.measur:
                pushFragments(AppConstants.TAB_C, new IllnessListFragment(), true, true);
                break;
            default:
                break;
        }
    }

    public File tempFile = new File(Environment.getExternalStorageDirectory(),
            System.currentTimeMillis() + ".jpg");

    private void showChoseImage() {
        tempFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("操作");
        builder.setItems(new String[] {
                "照相机", "本地图库"
        },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        // TODO Auto-generated method stub
                        switch (position) {
                            case 0:
                                GraphicsBitmapUtils.startCamearPicCut(AppMainTabActivity.this,
                                        dialog, tempFile);
                                break;
                            case 1:
                                GraphicsBitmapUtils.startImageCaptrue(AppMainTabActivity.this,
                                        dialog);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        // TODO Auto-generated method stub

        String action = intent.getAction();
        this.connectedOrDis(intent.getAction());
        if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            if (uuid.contains("ffe4")) {
                byte[] data = intent
                        .getByteArrayExtra(RFStarBLEService.EXTRA_DATA);

                String content = new String(data);// AppUtil.bytesToHexString(data);
                Toast.makeText(context, content, 3000).show();
//                if (content != null) {
//                    beginDraw(Float.parseFloat(content));
//                }
//                mHandler.post(mScrollToBottom);
            }
            // }
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(action)) {

        }

    }
    @Override
    protected void onResume() {

    super.onResume();
    if (app.manager.cubicBLEDevice != null) {
        app.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
        app.manager.cubicBLEDevice.setNotification("ffe0", "ffe4", true);
    }
    }
}
