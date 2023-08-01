package com.example.login_register_sqlite_db;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.login_register_sqlite_db.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper= new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.signupEmail.getText().toString().trim();
                String password=binding.signupPassword.getText().toString().trim();
                String cPassword=binding.signupConfirm.getText().toString().trim();

                if (email.equals("") || password.equals("") || cPassword.equals(""))
                    Toast.makeText(SignupActivity.this, "All field are mandatory ", Toast.LENGTH_SHORT).show();
                else {
                    if(password.equals(cPassword)){
                        Boolean checkEmail=databaseHelper.checkEmail(email);
                        if(checkEmail == false){
                            Boolean insert = databaseHelper.insertData(email,password);
                            if (insert == true){
                                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignupActivity.this, "Signup Failed ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignupActivity.this, "Users already exists Please Login  ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                            Toast.makeText(SignupActivity.this, "Invalid Password ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}