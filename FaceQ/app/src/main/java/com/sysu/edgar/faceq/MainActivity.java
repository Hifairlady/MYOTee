package com.sysu.edgar.faceq;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private View shareView, newProdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn_join_us = (ImageButton)this.findViewById(R.id.btn_join_us);
        btn_join_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, JoinUsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_our_team = (ImageButton)this.findViewById(R.id.btn_our_team);
        btn_our_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout btn_dmode = (LinearLayout)this.findViewById(R.id.btn_double_mode);
        btn_dmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DoubleModeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout btn_history = (LinearLayout)this.findViewById(R.id.btn_history_image);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HistoryImageActivity.class);
                startActivity(intent);
            }
        });

        Button btn_feedback = (Button)this.findViewById(R.id.btn_feedback);
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_share = (ImageButton)this.findViewById(R.id.btn_share);
        shareView = LayoutInflater.from(this).inflate(R.layout.popup_window_share, null);
        Button btn_close = (Button)shareView.findViewById(R.id.btn_close);
        setupPopupWindow(shareView, btn_close, btn_share);

        LinearLayout btn_new_prod = (LinearLayout)this.findViewById(R.id.btn_new_product);
        newProdView = LayoutInflater.from(this).inflate(R.layout.popup_window_new_product, null);
        Button btn_close_1 = (Button)newProdView.findViewById(R.id.btn_close);
        setupPopupWindow(newProdView, btn_close_1, btn_new_prod);
        Button btn_qxw = (Button)newProdView.findViewById(R.id.btn_qxw);
        btn_qxw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.lemon.faceu");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        ImageView btn_man = (ImageView)this.findViewById(R.id.btn_man);
        btn_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProcManActivity.class);
                startActivity(intent);
            }
        });
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
