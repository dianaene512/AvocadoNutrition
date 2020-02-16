package com.example.diana.licentaschelet.Activities.AccountRelated;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diana.licentaschelet.Activities.HomeScreen;
import com.example.diana.licentaschelet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInRegister extends Activity {

     private TextView tvWelcome;
     private TextView forgotPassword;
     private TextView txtErr;
     private Button btnLogIn;
     private Button btnRegister;
     private  EditText txtMail;
     private EditText txtPass;
     private FirebaseUser fbUser;
     private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_register);

            tvWelcome = findViewById(R.id.textViewWelcome);
            btnLogIn = findViewById(R.id.btnLogIn);
            btnRegister = findViewById(R.id.btnRegister);
            txtMail=findViewById(R.id.txtEmail);
            txtPass=findViewById(R.id.txtPassword);
            forgotPassword = findViewById(R.id.txtForgotPassword);
            fbAuth = FirebaseAuth.getInstance();
            fbUser = fbAuth.getCurrentUser();
            txtErr = findViewById(R.id.txtErr);



        FirebaseUser user = fbAuth.getCurrentUser();
        if(fbUser!=null){
            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
            finish();
        }

        Typeface typeface = ResourcesCompat.getFont(this,R.font.galada);
        tvWelcome.setTypeface(typeface);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });




        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtMail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                onLogInClick(email,password);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ResetPassword.class));
            }
        });

    }

    private boolean validCredentials(){


        boolean ok = true;

        String email = txtMail.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        if (email.length()==0 || !email.contains("@") || !email.contains(".")){
            ok=false;
            txtMail.setError(getString(R.string.failmail));
        }
        if (pass.length()==0 ){
            ok=false;
            txtPass.setError(getString(R.string.failpass));

        }

        return ok;

    }

    private void onLogInClick(String email, String password){
        if(validCredentials()){

            fbAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                                finish();

                            } else {


                                txtErr.setError(getString(R.string.failAuth));

                            }

                        }
                    });



        }
    }

}
