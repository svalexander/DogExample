package nyc.c4q.shannonalexander_navarro.dogexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText userNameET;
    EditText passwordET;
    Button submitBtn;
    String usernameResponse;
    String passwordResponse;
    String use;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        submitActions();
        checkSharedPref();
    }

    private void initViews() {
        userNameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);
        submitBtn = findViewById(R.id.submitBtn);
    }

    private void submitActions() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            }
        });
    }

    private void checkSharedPref() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.contains("key")) {
            startBreedsAct();
        }
    }

    private void checkInput() {

        usernameResponse = userNameET.getText().toString();
        passwordResponse = passwordET.getText().toString();

        if (usernameResponse.length() == 0) {
            userNameET.setError("Please enter a user name");
        }
        if (passwordResponse.length() == 0) {
            passwordET.setError("Please enter a password");
        }
        if (passwordResponse.contains(usernameResponse)) {
            passwordET.setError("Password cannot contain username");
        } else {
            sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("key", usernameResponse + "");
            editor.commit();
            use = sharedPref.getString("key", "default");
            Intent intent = new Intent(LoginActivity.this, BreedsActivity.class);

            intent.putExtra("pass", use + "");


            Log.d("what?", use + "");
            startActivity(intent);
            startBreedsAct();
        }

    }

    private void startBreedsAct() {

    }
}
