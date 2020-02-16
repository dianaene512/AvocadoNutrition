package com.example.diana.licentaschelet.Activities.AccountRelated;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diana.licentaschelet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Settings extends Activity {


    private Button btnLogOut;
    private Button btnResetPass;
    private Button btnMyInfo;
    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnResetPass = findViewById(R.id.btnResetPassword);
        btnMyInfo = findViewById(R.id.btnMyInfo);
        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LogInRegister.class));
            }
        });

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPassword.class));
            }
        });

        btnMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccountInformation.class));
            }
        });

    }
}
