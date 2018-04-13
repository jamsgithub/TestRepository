package com.myself.myrepository.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.myself.myrepository.R;
import com.myself.myrepository.widght.CustomImageView;
import com.myself.myrepository.widght.DragFrameLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView dragImg;
    private DragFrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomImageView image = (CustomImageView) findViewById(R.id.image);
        image.setLeftImage(getResources().getDrawable(R.drawable.selector_image) ,
                getResources().getDrawable(R.mipmap.add_sticky_note_normal));
        /*setContentView(R.layout.drag_frame_layout);
        dragImg = (ImageView)findViewById(R.id.dragImg);
        frameLayout = (DragFrameLayout)findViewById(R.id.becausefloat);
        frameLayout.setDragImageListener(new DragFrameLayout.DragImageClickListener() {

            private boolean isDaix;

            @Override
            public void onClick() {
                // TODO Auto-generated method stub
                if (isDaix) {
                    dragImg.setBackgroundResource(R.drawable.selector_image);
                    isDaix = false;
                } else {
                    dragImg.setBackgroundResource(R.mipmap.add_sticky_note_normal);
                    isDaix = true;
                }
                Toast.makeText(MainActivity.this, "点击",
                        Toast.LENGTH_LONG).show();
            }
        });
        
        dragImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了图片!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
