package com.sysu.edgar.faceq;

import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Edgar on 2016/7/19.
 */
public class FragmentItem extends Fragment {

    private int position;
    private ArrayList<Integer> resArray;
    private ImageGridAdapter mAdapter;
    private RelativeLayout display_layout;
    private Button save_img;

    public static FragmentItem newInstance(int position, ArrayList<Integer> res) {
        FragmentItem fragmentItem = new FragmentItem();
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        bundle.putIntegerArrayList("Resources", res);
        fragmentItem.setArguments(bundle);
        return fragmentItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("Position");
        resArray = getArguments().getIntegerArrayList("Resources");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_form, container, false);
        display_layout = (RelativeLayout)getActivity().findViewById(R.id.display_layout);
        save_img = (Button)getActivity().findViewById(R.id.btn_save);
        TextView test_text = (TextView)view.findViewById(R.id.test_text);
        test_text.setText("Hello Position " + position);
        if (resArray != null) {
            test_text.setVisibility(View.GONE);
            final GridView mGridView = (GridView)view.findViewById(R.id.grid_items);
            mAdapter = new ImageGridAdapter(getContext(), resArray);
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println("ResId: " + resArray.get(position) + " " + id);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new LayoutParams(300,
                            300));
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageResource((int)id);
                    display_layout.addView(imageView);
                }
            });
        } else {
            test_text.setVisibility(View.VISIBLE);
        }

        save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_layout.removeAllViews();
            }
        });

        return view;
    }
}
