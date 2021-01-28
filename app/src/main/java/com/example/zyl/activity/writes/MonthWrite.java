package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zyl.Helper.AlbumHelper;
import com.example.zyl.Helper.Constants;
import com.example.zyl.MainActivity;
import com.example.zyl.R;
import com.example.zyl.activity.PhotoSelectActivity;
import com.example.zyl.activity.view.MonthSelectorView;
import com.example.zyl.recycle.adapter.WeekRecycleviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MonthWrite extends Activity implements View.OnClickListener{
    private ImageView buttonBack;
    private TextView buttonOversee;
    private TextView textType;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView monthRecyclerView;
    private MonthSelectorView selectorView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymonth);

        setUi();
    }
    private void setUi() {
        buttonBack = findViewById(R.id.month_head_nav).findViewById(R.id.head_back);
        buttonOversee=findViewById(R.id.month_head_nav).findViewById(R.id.head_text_oversee);
        buttonPullBack=findViewById(R.id.month_head_nav).findViewById(R.id.head_pullback);
        textType=findViewById(R.id.month_head_nav).findViewById(R.id.head_text);
        textType.setText("Month");
        buttonAddPicture = findViewById(R.id.month_bottom_nav).findViewById(R.id.choosePhoto);
        buttonAddText = findViewById(R.id.month_bottom_nav).findViewById(R.id.chooseText);
        monthRecyclerView = findViewById(R.id.month_recyclerview);

        List lists = new ArrayList();
        WeekRecycleviewAdapter weekRecycleviewAdapter = new WeekRecycleviewAdapter(lists);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        monthRecyclerView.setAdapter(weekRecycleviewAdapter);
        monthRecyclerView.setLayoutManager(linearLayout);
        //////////////

        buttonBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_back:
                Intent intent=new Intent(MonthWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:
                break;
            case R.id.chooseText:
                break;
            case R.id.choosePhoto:
                intent = new Intent(MonthWrite.this, PhotoSelectActivity.class);
                startActivityForResult(intent, Constants.ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:
                // Show monthSelectorView and add callback of it
                selectorView = new MonthSelectorView(this);
                selectorView.setListener(new MonthSelectorView.OnMonthDialogDismissedListener() {
                    @Override
                    public void onDismissed(int year, int month) {
                        Toast.makeText(MonthWrite.this, year+"-"+month, Toast.LENGTH_SHORT).show();
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
                }
                break;

        }
    }
}
