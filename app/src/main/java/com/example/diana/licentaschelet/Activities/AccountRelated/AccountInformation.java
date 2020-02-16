package com.example.diana.licentaschelet.Activities.AccountRelated;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diana.licentaschelet.R;

public class AccountInformation extends Activity {

    private EditText txtAge;
    private RadioButton rMale;
    private RadioButton rFemale;
    private EditText txtHeight;
    private EditText txtWeight;
    private Spinner spnGoal;
    private CheckBox cbxNotifications;
    private Button btnUpdateUserInfo;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        txtAge = findViewById(R.id.txtAge);
        rMale = findViewById(R.id.rMale);
        rFemale = findViewById(R.id.rFemale);
        txtHeight=findViewById(R.id.txtHeight);
        txtWeight=findViewById(R.id.txtWeight);
        spnGoal = findViewById(R.id.spnGoal);
        cbxNotifications = findViewById(R.id.cbxNotifications);
        btnUpdateUserInfo= findViewById(R.id.btnUpdateUserInfo);
        sharedPreferences = getSharedPreferences("SharedPreferences",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        txtAge.setText(String.valueOf(sharedPreferences.getInt("userAge",0)));

       if(sharedPreferences.getString("userGender","M").equals("M"))
       {
           rMale.setChecked(true);
           rFemale.setChecked(false);
       }
       else{
           rFemale.setChecked(true);
           rMale.setChecked(false);
       }

        txtHeight.setText(String.valueOf(sharedPreferences.getFloat("userHeight",0)));
        txtWeight.setText(String.valueOf(sharedPreferences.getFloat("userWeight",0)));

       for(int poz = 0; poz<spnGoal.getCount(); poz++){
           if(spnGoal.getItemAtPosition(poz).equals(sharedPreferences.getString("userGoal","Maintain weight"))){
               spnGoal.setSelection(poz);
           }

       }


        cbxNotifications.setChecked(!sharedPreferences.getBoolean("receivesNotifications",false));


        //rMale.setChecked(!rFemale.isChecked());
        rMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rFemale.setChecked(!rMale.isChecked());
            }
        });
        rFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rMale.setChecked(!rFemale.isChecked());
            }
        });

        btnUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userAge = 0;
                float userHeight=0;
                float userWeight=0;
                boolean receivesNotifications;
                userAge = Integer.parseInt(txtAge.getText().toString().trim());
                userHeight = Float.parseFloat(txtHeight.getText().toString().trim());
                userWeight  = Float.parseFloat(txtWeight.getText().toString().trim());
                String userGender="";
                if(rFemale.isChecked())  userGender = "F";
                if(rMale.isChecked()) userGender = "M";

                String userGoal = "";

                userGoal = spnGoal.getSelectedItem().toString();

                if(cbxNotifications.isChecked()) receivesNotifications = false;
                else receivesNotifications = true;


                editor.putInt("userAge",userAge);
                editor.putFloat("userHeight",userHeight);
                editor.putFloat("userWeight",userWeight);
                editor.putString("userGender",userGender);
                editor.putString("userGoal",userGoal);
                editor.putBoolean("receivesNotifications",receivesNotifications);
                editor.putFloat("kcalGoal",getGoalKcal());
                editor.apply();



                Toast.makeText(getApplicationContext(),"Information updated",Toast.LENGTH_SHORT).show();

                finish();

            }
        });

    }

    public float getGoalKcal(){
        float goal = 1111;




        return goal;
    }
}
