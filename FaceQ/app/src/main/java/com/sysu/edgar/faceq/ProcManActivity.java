package com.sysu.edgar.faceq;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Edgar on 2016/7/19.
 */
public class ProcManActivity extends AppCompatActivity {

    private MyViewPagerAdapter mAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private int[] icons_up = new int[17];
    private int[] icons_down = new int[17];
    private Field[] ID_FIELDS = R.drawable.class.getFields();
    int[] resArray = new int[ID_FIELDS.length];
    String[] resNames = new String[ID_FIELDS.length];
    private ArrayList<ArrayList<Integer>> gridItems = new ArrayList<>();
    private View shareView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc_man);

        init();

        Button btn_back = (Button)this.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager = (ViewPager)this.findViewById(R.id.my_viewpager);
        mTablayout = (TabLayout)this.findViewById(R.id.my_tablayout);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < 17; i++) {
            fragments.add(FragmentItem.newInstance(i, gridItems.get(i)));
            mTablayout.addTab(mTablayout.newTab());
            if (i == 0) {
                mTablayout.setSelected(true);
            }
        }

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            tab.setIcon(icons_up[i]);
            if (tab.isSelected()) {
                tab.setIcon(icons_down[i]);
            }
        }

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(icons_down[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(icons_up[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setIcon(icons_down[tab.getPosition()]);
            }
        });

        Button btn_share = (Button)this.findViewById(R.id.btn_share);
        shareView = LayoutInflater.from(this).inflate(R.layout.popup_window_share_1, null);
        Button btn_close = (Button)shareView.findViewById(R.id.btn_close);
        setupPopupWindow(shareView, btn_close, btn_share);

    }

    private void init() {
        for (int i = 0; i < 17; i++) {
            int tmp = i + 1;
            String fp1 = "tab_" + tmp;
            String fp2 = "tab_" + tmp + "_down";
            icons_up[i] = this.getResources().getIdentifier(fp1, "drawable", this.getPackageName());
            icons_down[i] = this.getResources().getIdentifier(fp2, "drawable", this.getPackageName());
        }

        for (int i = 0; i < ID_FIELDS.length; i++) {
            try {
                resArray[i] = ID_FIELDS[i].getInt(null);
                resNames[i] = ID_FIELDS[i].getName();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println(ID_FIELDS.length);
        int hhh = 1;
        for (int i = 0; i < 17; i++) {
            if (i == 1) {
                ArrayList<Integer> ans = new ArrayList<>();
                for (int k = 1; k <= 25; k++) {
                    String str = "pure_color_s1_" + k;
                    ans.add(this.getResources().getIdentifier(str, "drawable", this.getPackageName()));
                }
                gridItems.add(ans);
            } else if (i == 3) {
                ArrayList<Integer> ans = new ArrayList<>();
                for (int k = 1; k <= 21; k++) {
                    String str = "pure_color_s3_" + k;
                    ans.add(this.getResources().getIdentifier(str, "drawable", this.getPackageName()));
                }
                gridItems.add(ans);
            } else {
                String str = "pic_s" + hhh + "_";
                ArrayList<Integer> ans = new ArrayList<>();
                for (int j = 0; j < resNames.length; j++) {
                    try {
                        if (resNames[j].contains(str)) {
                            ans.add(resArray[j]);
                            continue;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                gridItems.add(ans);
                hhh++;
            }
        }
        gridItems.add(7, null);
        Collections.swap(gridItems, 7, 16);
        gridItems.remove(16);
        Collections.swap(gridItems, 14, 15);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void setupPopupWindow(final View view, View btn_close, View startButton) {
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popup_win_anim);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                popupWindow.dismiss();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(1.0f);
                popupWindow.dismiss();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundAlpha(0.5f);
                if (popupWindow != null || !popupWindow.isShowing()) {
                    backgroundAlpha(0.5f);
                    popupWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
                }
            }
        });
    }
}
