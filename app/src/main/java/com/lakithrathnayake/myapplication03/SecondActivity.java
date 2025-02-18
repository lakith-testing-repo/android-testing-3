package com.lakithrathnayake.myapplication03;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    Button b1;
    private static final String CHANNEL_ID = "my_channel_id";
    private Random random;

    public static final String EXTRA_TITLE = "notification_title";
    public static final String EXTRA_SUBJECT = "notification_subject";
    public static final String EXTRA_BODY = "notification_body";
    public static final String EXTRA_NOTIFICATION_ID = "notification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ed1 = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText2);
        ed3 = findViewById(R.id.editText3);
        b1 = findViewById(R.id.button);
        random = new Random();

        checkForNotificationData();
        createNotificationChannel();

        b1.setOnClickListener(v -> {
            String title = ed1.getText().toString().trim();
            String subject = ed2.getText().toString().trim();
            String body = ed3.getText().toString().trim();

            int notificationId = generateUniqueId();

            Intent intent = new Intent(this, SecondActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_SUBJECT, subject);
            intent.putExtra(EXTRA_BODY, body);
            intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSubText(subject)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();
            }

            notificationManager.notify(notificationId, notification);

            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
        });
    }

    private int generateUniqueId() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "My Channel";
            String description = "Channel for MyApplication03";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void checkForNotificationData() {
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(EXTRA_TITLE);
            String subject = intent.getStringExtra(EXTRA_SUBJECT);
            String body = intent.getStringExtra(EXTRA_BODY);

            // If we have data from the notification, populate the EditText fields
            if (title != null) ed1.setText(title);
            if (subject != null) ed2.setText(subject);
            if (body != null) ed3.setText(body);
        }
    }
}