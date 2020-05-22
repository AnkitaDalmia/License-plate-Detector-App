package com.example.licenseplate;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp,login_btn;
    ImageView image;
    TextView logoText,sloganText;
    TextInputLayout username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Hooks
        callSignUp=findViewById(R.id.signup_screen);
        image=findViewById(R.id.logo_image);
        logoText=findViewById(R.id.logo_name);
        sloganText=findViewById(R.id.slogan_name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login_btn);



        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_trans");
                pairs[4] = new Pair<View, String>(password, "password_trans");
                pairs[5] = new Pair<View, String>(login_btn, "button_trans");
                pairs[6] = new Pair<View, String>(callSignUp, "login_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                    startActivity(intent, options.toBundle());

                }
            }
        });
        }

        private Boolean validateUsername() {
            String val = username.getEditText().getText().toString();
            /*String noWhiteSpace = "\\A\\w{4,20}\\z";*/
            if(val.isEmpty()){
                username.setError("Field cannot be empty");
            return false;}
            /*else if(val.length()>=15){
                username.setError("Username too long");
            }
            else if(!val.matches(noWhiteSpace)){
                username.setError("White Spaces are not allowed");
                return false;
            }*/
            else{
                username.setError(null);
                username.setErrorEnabled(false);
                return true;
            }
        }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;}

        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    public void loginUser(View view){
        //validate Login Info
        if(!validateUsername()| !validatePassword()){
            return;
        }
        else{
            //fetchdata from firebase and check
            isUser();
        }
    }
    private  void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser=reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB=dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){
                       /* String nameFromDB=dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB=dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB=dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);

                        String emailFromDB=dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);*/
                        username.setError(null);
                        username.setErrorEnabled(false);

                        Intent intent = new Intent(Login.this,LoggedIn.class);
                        //Intent intent=new Intent(SignUp.this,Login.class);
                        startActivity(intent);


                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();

                    }
                    }
                else {
                    username.setError("No such User exist");
                    username.requestFocus();
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void resetPassword(View view) {

    }
}
