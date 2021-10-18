package com.gokulsundar4545.gokulchat4545;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView Splachimage1;
    TextView Txt,Txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Splachimage1=findViewById(R.id.Splachimage);
        Txt=findViewById(R.id.Txt);
        Txt1=findViewById(R.id.Txt1);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.intro_animation);
        Splachimage1.setAnimation(animation);
        Txt.setAnimation(animation);
        Txt1.setAnimation(animation);



        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);

            }
        },2000);


    }
}
