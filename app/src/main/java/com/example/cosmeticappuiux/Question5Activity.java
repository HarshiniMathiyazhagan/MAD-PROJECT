package com.example.cosmeticappuiux;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class Question5Activity extends Question4Activity {
    private Button button2;
    private Button cameraButton;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        button2 = findViewById(R.id.button2);
        cameraButton = findViewById(R.id.cameraButton);

        // Check if the action bar exists and hide it
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if permission is granted before launching the camera
                if (ContextCompat.checkSelfPermission(Question5Activity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    launchCamera();
                } else {
                    // Request permission if not granted
                    ActivityCompat.requestPermissions(Question5Activity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private void launchCamera() {
        // Launch the camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle the result of the permission request
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                // Permission denied, show a toast message
                Toast.makeText(this, "Camera permission is required to take a photo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle the captured photo here if necessary
            // You can retrieve the photo from the Intent data
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
        }
    }

    public void checker() {
        // Get previous values from the intent
        Intent q2 = getIntent();
        final int[] dry5 = {q2.getIntExtra("dry", dry)};
        final int[] normal5 = {q2.getIntExtra("normal", normal)};
        final int[] oily5 = {q2.getIntExtra("oily", oily)};
        final int[] combo5 = {q2.getIntExtra("combination", combination)};

        // Randomly increment one of the skin type counters
        Random random = new Random();
        int choice = random.nextInt(4); // Random number between 0 and 3

        switch (choice) {
            case 0:
                dry5[0]++;
                break;
            case 1:
                normal5[0]++;
                break;
            case 2:
                oily5[0]++;
                break;
            case 3:
                combo5[0]++;
                break;
        }

        // Update counters
        setDry(dry5[0]);
        setNormal(normal5[0]);
        setOily(oily5[0]);
        setCombination(combo5[0]);

        // Determine which result page to navigate to
        int resultOily = getOily();
        int resultNormal = getNormal();
        int resultDry = getDry();
        int resultCombo = getCombination();

        if (resultOily > resultNormal && resultOily > resultDry && resultOily > resultCombo) {
            Intent intent = new Intent(getApplicationContext(), OilySkinResultPage.class);
            startActivity(intent);
        } else if (resultNormal > resultDry && resultNormal > resultOily && resultNormal > resultCombo) {
            Intent intent = new Intent(getApplicationContext(), NormalSkinResultPage.class);
            startActivity(intent);
        } else if (resultDry > resultOily && resultDry > resultNormal && resultDry > resultCombo) {
            Intent drySkinIntent = new Intent(this, DrySkinResultPage.class);
            startActivity(drySkinIntent);
        } else {
            Intent intent = new Intent(getApplicationContext(), CombinationSkinResultPage.class);
            startActivity(intent);
        }
    }
}




