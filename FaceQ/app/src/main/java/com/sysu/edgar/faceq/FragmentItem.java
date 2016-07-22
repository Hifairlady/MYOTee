package com.sysu.edgar.faceq;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Edgar on 2016/7/19.
 */
public class FragmentItem extends Fragment {

    private int position;
    private ArrayList<Integer> resArray;
    private ArrayList<String> name_ids;
    private ImageGridAdapter mAdapter;
    private RelativeLayout display_layout;
    private Button save_img;
    private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/FaceQ/";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    private static final String[] TYPES = new String[] {"hairChange", "faceChange", "eyebrowChange",
        "eyeChange", "mouthChange", "noseChange"};

    public static FragmentItem newInstance(int position, ArrayList<Integer> res, ArrayList<String> names) {
        FragmentItem fragmentItem = new FragmentItem();
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        bundle.putIntegerArrayList("Resources", res);
        bundle.putStringArrayList("NameIds", names);
        fragmentItem.setArguments(bundle);
        return fragmentItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("Position");
        resArray = getArguments().getIntegerArrayList("Resources");
        name_ids = getArguments().getStringArrayList("NameIds");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_form, container, false);

        display_layout = (RelativeLayout)getActivity().findViewById(R.id.display_layout);
        save_img = (Button)getActivity().findViewById(R.id.btn_save);
        TextView test_text = (TextView)view.findViewById(R.id.test_text);
        final WebView webView = (WebView)getActivity().findViewById(R.id.my_webview);

        if (resArray != null) {
            test_text.setVisibility(View.GONE);
            final GridView mGridView = (GridView)view.findViewById(R.id.grid_items);
            mAdapter = new ImageGridAdapter(getContext(), resArray);
            mGridView.setAdapter(mAdapter);
            if (position >= 0 && position<= 5) {
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                        String hello = TYPES[position] + "(" + name_ids.get(position1) + ")";
                        String url = "javascript:" + hello;
                        webView.loadUrl(url);
                    }
                });
            } else {
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "Not Finished Yet!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveViewToFile(display_layout);
            }
        });

        return view;
    }

    private void saveViewToFile(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Date curDate = new Date(System.currentTimeMillis());
        String filename = sdf.format(curDate) + ".png";
        File dir = new File(ALBUM_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File myFile = new File(ALBUM_PATH + filename);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            Toast.makeText(getContext(), "Image Saved to " + ALBUM_PATH + filename,
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
