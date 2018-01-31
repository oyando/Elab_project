package xom.example.android.elab_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
  /*  private SharedPreferences auth;*/

    String userName = "Doctor";
    String passWord = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       /* auth =  getSharedPreferences("Auth", MODE_PRIVATE);
        SharedPreferences.Editor editor = auth.edit();
        editor.putString("username",userName);
        editor.putString("password",passWord);
        editor.apply();*/
        final TextInputEditText name = (TextInputEditText) findViewById(R.id.username);
        final TextInputEditText pass = (TextInputEditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(name, pass)) {
                    authenicate(name, pass);
                }
            }
        });


    }

    private boolean validate(TextInputEditText name, TextInputEditText pass) {
        String username = name.getText().toString();
        String password = pass.getText().toString();

        if (username.isEmpty()) {
            name.setError("Enter username");
            return false;
        } else if (password.isEmpty()) {
            pass.setError("Enter password");
            return false;
        }
        return true;
    }

    private void authenicate(TextInputEditText name, TextInputEditText pass) {
        String username = name.getText().toString();
        String password = pass.getText().toString();
        if (username.equalsIgnoreCase(userName) && password.equals(passWord)) {
            startActivity(new Intent(LoginActivity.this, LandingActivity.class));
            finish();
        } else if (!username.equalsIgnoreCase(userName)) {
            Toast.makeText(LoginActivity.this, "Wrong username!", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passWord)) {
            Toast.makeText(LoginActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
        }

    }

}
