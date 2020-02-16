package com.example.diana.licentaschelet.Activities.AccountRelated;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diana.licentaschelet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends Activity {

    private EditText txtEmail;
    private Button btnSubmit;
    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        txtEmail = findViewById(R.id.txtMailReset);
        btnSubmit = findViewById(R.id.btnResetPassword);
        fbAuth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();

                if (email.length()==0 || !email.contains("@") || !email.contains(".")){
                    txtEmail.setError(getString(R.string.failemail));
                }
                else{

                    fbAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword.this, R.string.checkMailLink, Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ResetPassword.this, R.string.tryAgain, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                }
            }
        });



    }
}
