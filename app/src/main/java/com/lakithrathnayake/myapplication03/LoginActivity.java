package com.lakithrathnayake.myapplication03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    Button b1;
    Intent in;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ed1= findViewById(R.id.editText);
        ed2= findViewById(R.id.editText2);
        ed3= findViewById(R.id.editText3);

        b1= findViewById(R.id.button);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(v -> {
            String n  = ed1.getText().toString();
            String ph  = ed2.getText().toString();
            String e  = ed3.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Name, n);
            editor.putString(Phone, ph);
            editor.putString(Email, e);
            editor.commit();

            in = new Intent(LoginActivity.this, MainActivity2.class);
            startActivity(in);
        });
    }
}