package com.example.diana.licentaschelet.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.diana.licentaschelet.Activities.AccountRelated.LogInRegister;
import com.example.diana.licentaschelet.R;

public class SplashScreen extends Activity {

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            public void run() {
                for (int progress=0; progress<125; progress+=20) {
                    try {
                        Thread.sleep(500);
                        progressBar.setProgress(progress);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                Intent intent = new Intent(getApplicationContext(), LogInRegister.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}

