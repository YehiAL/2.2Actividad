package com.example.a22actividad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Bitmap loadedBitmap;
    private Button button;

    private static final String IMAGE_KEY = "image_bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iView);
        button = findViewById(R.id.btnDescargar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (savedInstanceState != null){
            String imagenCodificada = savedInstanceState.getString(IMAGE_KEY);
            if(imagenCodificada != null){
                byte[] decodedString = Base64.decode(imagenCodificada,Base64.DEFAULT);
                loadedBitmap = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
                imageView.setImageBitmap(loadedBitmap);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = loadImageFromNetwork("https://i.pinimg.com/236x/a3/7d/12/a37d1250db955c21709d652730c430eb.jpg");
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                loadedBitmap = bitmap;
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();

            }
        });

    }

    private Bitmap loadImageFromNetwork(String url) {
        Bitmap bitmap = null;
        try {
            java.net.URL imageUrl = new java.net.URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(loadedBitmap != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        }
    }
}