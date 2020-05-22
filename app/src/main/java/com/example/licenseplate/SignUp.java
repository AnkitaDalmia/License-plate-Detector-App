package com.example.licenseplate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Variables
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
   // ImageView image;
  //  TextView logoText,sloganText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        // image=findViewById(R.id.logo_image);
        // logoText=findViewById(R.id.logo_name);
       // sloganText=findViewById(R.id.slogan_name);

        //Save data in Firebase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("users");

                //get all values
                String name=regName.getEditText().getText().toString();
                String username=regUsername.getEditText().getText().toString();
                String email=regEmail.getEditText().getText().toString();
                String phoneNo=regPhoneNo.getEditText().getText().toString();
                String password=regPassword.getEditText().getText().toString();

               UserHelperClass helperClass =new UserHelperClass(name,username,email,phoneNo,password);
                reference.child(username).setValue(helperClass);

            }
        });

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,Login.class);

              /*  Pair[] pairs=new Pair[7];

                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(logoText,"logo_text");
                pairs[2]=new Pair<View,String>(sloganText,"logo_desc");
                pairs[3]=new Pair<View,String>(regName,"username_trans");
                pairs[4]=new Pair<View,String>(regPassword,"password_trans");
                pairs[5]=new Pair<View,String>(regBtn,"button_trans");
                pairs[6]=new Pair<View,String>(regToLoginBtn,"login_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                    startActivity(intent,options.toBundle());
                }*/
              startActivity(intent);
            }
        });
    }//onCreate Method End
}
