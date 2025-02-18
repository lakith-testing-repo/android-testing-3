package com.lakithrathnayake.myapplication03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    Button b1,b2, b3;
    TextView tv;
    EditText ed1;

    String data;
    private String file = "mydata";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1= findViewById(R.id.button);
        b2= findViewById(R.id.button2);
        b3 = findViewById(R.id.btnNext);

        ed1= findViewById(R.id.editText);
        tv= findViewById(R.id.textView2);

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        b1.setOnClickListener(v -> {
            data = ed1.getText().toString();

            try {
                FileOutputStream fOut = openFileOutput(file, MODE_PRIVATE);
                fOut.write(data.getBytes());
                fOut.close();
                Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
                ed1.getText().clear();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(),"error file saving", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });

        b2.setOnClickListener(v -> {
            spinner.setVisibility(View.VISIBLE);
            try {
                FileInputStream fIn = openFileInput(file);
                int c;
                StringBuilder temp = new StringBuilder();
                while ((c = fIn.read()) != -1) {
                    temp.append((char) c);
                }
                tv.setText(temp);
                spinner.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(),"file read",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        b3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

}