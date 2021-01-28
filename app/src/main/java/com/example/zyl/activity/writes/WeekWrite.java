package com.example.zyl.activity.writes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.example.zyl.recycle.adapter.WeekRecycleviewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WeekWrite extends Activity implements View.OnClickListener {
    public static final int ON_PHOTO_SELECTED = 3 ;
    private ImageView buttonBack;

    private TextView buttonOversee;
    private TextView textType;
    private ImageView buttonPullBack;
    private ImageView buttonAddPicture;
    private ImageView buttonAddText;
    private RecyclerView weekRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myweek);

        setUi();
    }
    private void setUi(){
        buttonBack=findViewById(R.id.week_head_nav).findViewById(R.id.head_back);
        buttonOversee=findViewById(R.id.week_head_nav).findViewById(R.id.head_text_oversee);
        buttonAddPicture=findViewById(R.id.week_bottom_nav).findViewById(R.id.choosePhoto);
        buttonPullBack=findViewById(R.id.week_head_nav).findViewById(R.id.head_pullback);
        buttonAddText=findViewById(R.id.week_bottom_nav).findViewById(R.id.chooseText);
        textType=findViewById(R.id.week_head_nav).findViewById(R.id.head_text);
        textType.setText("Week");
        weekRecyclerView=findViewById(R.id.week_recyclerview);
        ///////////////
        List lists=new ArrayList();
        WeekRecycleviewAdapter weekRecycleviewAdapter=new WeekRecycleviewAdapter(lists);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        weekRecyclerView.setAdapter(weekRecycleviewAdapter);
        weekRecyclerView.setLayoutManager(linearLayout);
        //////////////

        buttonBack.setOnClickListener(this);
        buttonOversee.setOnClickListener(this);
        buttonPullBack.setOnClickListener(this);
        textType.setOnClickListener(this);
        buttonAddPicture.setOnClickListener(this);
        buttonAddText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()){
            case R.id.head_back:
                intent=new Intent(WeekWrite.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.head_text_oversee:
                break;
           /* case R.id.choosePhoto:
                Log.d("WeekWrite","clikced");
                AlbumHelper.openGalary(this);
                break;*/
            case R.id.chooseText:
                break;
            case R.id.choosePhoto:
                intent = new Intent(WeekWrite.this, PhotoSelectActivity.class);
                startActivityForResult(intent,ON_PHOTO_SELECTED);
                break;
            case R.id.head_pullback:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("WeekWrite",""+requestCode);

            switch (requestCode) {
                case AlbumHelper.OPEN_ALBUM:
                    String path = AlbumHelper.utilHandler(this, data);
                    setNewPicPos(path);
                    break;
                case ON_PHOTO_SELECTED:
                    if(data != null) {
                        String img_path = data.getStringExtra("img_path");
                    }
                    break;
                case Constants.CAMERA_REQUEST_CODE:
                    Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
                    break;

        }
    }

    private void setNewPicPos(String path) {
        ImageView imageView=new ImageView(this);
        imageView.setImageURI(Uri.parse(path));
        imageView.setX(200);
        imageView.setY(200);
        weekRecyclerView.addView(imageView);

    }


}
