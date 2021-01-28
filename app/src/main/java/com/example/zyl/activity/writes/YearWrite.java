package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.MainActivity;
import com.example.zyl.R;
import com.example.zyl.activity.PhotoSelectActivity;
import com.example.zyl.activity.view.YearSelectorView;
import com.example.zyl.recycle.adapter.WeekRecycleviewAdapter;
import com.example.zyl.recycle.adapter.YearRecyclerviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class YearWrite extends Activity implements View.OnClickListener {
    private ImageView buttonBack;
    private TextView buttonOversee;
    private TextView textType;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView yearRecyclerView;
    private YearSelectorView selectorView;
    private LinearLayoutManager linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myyear);

        setUi();
    }

    private void setUi() {
        buttonBack = findViewById(R.id.year_head_nav).findViewById(R.id.head_back);
        buttonOversee = findViewById(R.id.year_head_nav).findViewById(R.id.head_text_oversee);
        textType=findViewById(R.id.year_head_nav).findViewById(R.id.head_text);
        textType.setText("Year");
        buttonPullBack = findViewById(R.id.year_head_nav).findViewById(R.id.head_pullback);
        buttonAddPicture = findViewById(R.id.year_bottom_nav).findViewById(R.id.choosePhoto);
        buttonAddText = findViewById(R.id.year_bottom_nav).findViewById(R.id.chooseText);
        yearRecyclerView = findViewById(R.id.year_recyclerview);

        YearRecyclerviewAdapter yearRecyclerviewAdapter = new YearRecyclerviewAdapter(new ArrayList<String>());
        linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yearRecyclerView.setAdapter(yearRecyclerviewAdapter);
        yearRecyclerView.setLayoutManager(linearLayout);
        yearRecyclerView.setItemViewCacheSize(13);
        //////////////

        buttonBack.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_back:
                Intent intent = new Intent(YearWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:
                break;
            case R.id.chooseText:
                break;
            case R.id.choosePhoto:
                intent = new Intent(YearWrite.this, PhotoSelectActivity.class);
                startActivityForResult(intent, Constants.ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:
                selectorView = new YearSelectorView(YearWrite.this);
                selectorView.setListener(new YearSelectorView.OnYearSelectorDismissedListener() {
                    @Override
                    public void onDismissed(int year, int month) {
                        Toast.makeText(YearWrite.this, year+"--"+month, Toast.LENGTH_SHORT).show();
                    }
                });
                selectorView.show();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.OPEN_ALBUM:
                String path = AlbumHelper.utilHandler(this, data);
                break;
            case Constants.ON_PHOTO_SELECTED:
                if(data != null) {
                    String img_path = data.getStringExtra("img_path");
                    int currentPosition = linearLayout.findFirstCompletelyVisibleItemPosition();
                    if(currentPosition!=-1) {
                        generateImg(img_path,currentPosition);

                    }
                }
                break;

        }
    }
    private void generateImg(String url,int curPos){
        ImageView imageView = new ImageView(this);
        Glide.with(this).load(url).into(imageView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
        imageView.setLayoutParams(params);
        imageView.setPadding(30,5,5,30);

        View childAt = yearRecyclerView.getChildAt(curPos);
        RecyclerView.ViewHolder holder = yearRecyclerView.getChildViewHolder(childAt);
        ConstraintLayout layout = holder.itemView.findViewById(R.id.year_inner_layout);
        layout.addView(imageView);

    }
}
