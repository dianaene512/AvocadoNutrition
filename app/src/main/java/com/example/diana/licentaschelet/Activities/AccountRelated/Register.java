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
import android.widget.Toast;

import com.example.diana.licentaschelet.Activities.HomeScreen;
import com.example.diana.licentaschelet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends Activity {

    private  TextView txtRegistration;
    private  EditText txtEmail;

    private  EditText txtPasswordRegistration;
    private  EditText txtPasswordAgain;
    private  Button btnSignUp;
    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtRegistration = findViewById(R.id.txtRegistration);
        txtEmail = findViewById(R.id.txtEmailReg);

        txtPasswordRegistration = findViewById(R.id.txtPasswordRegistration);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtPasswordAgain = findViewById(R.id.txtPasswordAgain);
        fbAuth = FirebaseAuth.getInstance();

        Typeface typeface = ResourcesCompat.getFont(this,R.font.galada);
        txtRegistration.setTypeface(typeface);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();
                String pass = txtPasswordRegistration.getText().toString().trim();
                if (validInput()){
                    onSignUpClick(email, pass);
                }

            }
        });

    }

    public boolean validInput(){
        boolean ok = true;

        String email = txtEmail.getText().toString().trim();

        String pass = txtPasswordRegistration.getText().toString().trim();
        String pass2 = txtPasswordAgain.getText().toString().trim();

        if (email.length()==0 || !email.contains("@") || !email.contains(".")){
            ok=false;
            txtEmail.setError("Please provide a valid email address");
        }



        if (pass.length()==0 || !pass.matches(".*\\d+.*")){
            ok=false;
            txtPasswordRegistration.setError("Password must contain at least a number");

        }

        if(!pass2.equals(pass)){
            ok=false;
            txtPasswordAgain.setError("Passwords don't match");
        }

        return ok;

    }

    public void onSignUpClick(String email, String password){


        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, R.string.tryAgain ,Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(new Intent(Register.this, HomeScreen.class));
                            finish();
                        }
                    }
                });



    }


}
