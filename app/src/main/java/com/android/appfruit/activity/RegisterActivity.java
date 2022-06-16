package com.android.appfruit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;
import com.android.appfruit.entity.Account;
import com.android.appfruit.service.AccountService;
import com.android.appfruit.util.RetrofitGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText text_username, text_name, text_phone, pass_confirm, text_password, text_address;
    TextView Already_registered, signIn, myFruits, userNameAlert, nameAlert, phoneAlert, passwordAlert, passwordConfirmAlert, addressAlert;
    ImageView img1;
    private TextWatcher text = null;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }
        initData();
        checkConfirmPassword();
//        createUser();
        initListener();
        redirectLogin();
//        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> {
        });
        signIn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void checkConfirmPassword() {
        pass_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String password = text_password.getText().toString();
                String checkPassword = pass_confirm.getText().toString();
                if (!password.equals(checkPassword)) {
                    // đưa ra lỗi.
                    passwordConfirmAlert.setText("Password not be matched. Please try again");
                    passwordConfirmAlert.setTextColor(Color.RED);
                } else {
                    passwordConfirmAlert.setText("Password Matched");
                    passwordConfirmAlert.setTextColor(Color.GREEN);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
    private void redirectLogin() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
    private void initData() {
        btnRegister = (Button) findViewById(R.id.button_register);
        text_username = (EditText) findViewById(R.id.txtUserName);
        text_name = (EditText) findViewById(R.id.txtName);
        text_password = (EditText) findViewById(R.id.txtPassword);
        pass_confirm = findViewById(R.id.txtConfirmPassword);
        text_phone = (EditText) findViewById(R.id.txtPhone);
        text_address = (EditText) findViewById(R.id.txtAddress);
        Already_registered = (TextView) findViewById(R.id.txtView3);
        signIn = (TextView) findViewById(R.id.textSingIn);
        myFruits = (TextView) findViewById(R.id.tView1);
        img1 = (ImageView) findViewById(R.id.imgRegister);
        userNameAlert = findViewById(R.id.UserNameAlert);
        nameAlert = findViewById(R.id.NameAlert);
        phoneAlert = findViewById(R.id.PhoneAlert);
        passwordAlert = findViewById(R.id.PasswordAlert);
        passwordConfirmAlert = findViewById(R.id.PasswordConfirmAlert);
        addressAlert = findViewById(R.id.AddressAlert);
    }
    private void initListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = text_username.getText().toString();
                String password = text_password.getText().toString();
                String name = text_name.getText().toString();
                String phone = text_phone.getText().toString();
                Account account = new Account();
                if (validateUserName())account.setUsername(username);
                if (validatePassword()) account.setPassword(password);
                if (validatePhone()) account.setPhone(phone);
                if (validateName()) account.setName(name);

                AccountService accountService = RetrofitGenerator.createService(AccountService.class);
                Log.d("ERROR", new Gson().toJson(account));
                Response<Account> accountCall = null;
                try {
                    accountCall = accountService.register(account).execute();
                    if (accountCall.isSuccessful()) {
                        CharSequence charSequence = "Tạo thành công";
                        Toast toast = Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG);
                        toast.show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    CharSequence charSequence = e.getMessage();
                    Toast toast = Toast.makeText(getApplicationContext(), charSequence, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
//    private void createUser() {
//        String username = text_username.getText().toString();
//        String password = text_password.getText().toString();
//
//        if (TextUtils.isEmpty(username)) {
//            text_username.setError("Please Enter Name");
//            text_username.requestFocus();
//        }
//        else if (TextUtils.isEmpty(password)) {
//            text_password.setError("Please Enter Password");
//            text_password.requestFocus();
//        }
//        else
//        {
//            mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Registration Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
    private boolean validatePhone() {
        String phoneInput = text_phone.getText().toString().trim();
        if (phoneInput.isEmpty()) {
            phoneAlert.setText("Field can not be empty");
            return false;
        }
        if (!phoneInput.matches("(84|0[9|8|7|1|3])+([0-9]{8})\\b")) {
            phoneAlert.setText("Please enter a valid phone number");
            return false;
        } else {
            phoneAlert.setError(null);
            return true;
        }
    }
    private boolean validateName() {
        String fullnameInput = text_name.getText().toString().trim();
        if (fullnameInput.isEmpty()) {
            nameAlert.setText("Field cannot be empty");
            return false;
        } else {
            userNameAlert.setError(null);
            return true;
        }
    }
    private boolean validateUserName() {
        String usernameInput = text_username.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            userNameAlert.setText("Field cannot be empty");
            return false;
        }
        if (usernameInput.length() >= 15) {
            userNameAlert.setText("Username has to less than 15 characters");
            return false;
        } else {
            userNameAlert.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = text_password.getText().toString().trim();
        //validate pass
        if (passwordInput.isEmpty()) {
            passwordAlert.setText("Field can not be empty");
            return false;
        }
        if (passwordInput.length() < 3) {
            passwordAlert.setText("Password must be at least 3 characters");
            return false;
        }
        return true;
    }
    public void Login(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
    public void mainActivity(View view) {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

}