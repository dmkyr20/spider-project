package com.example.dk200.hexapod;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class MainPanel extends AppCompatActivity {

    private BluetoothAdapter mBlurtooth = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket clientSocket = null;
    private String address = null;
    private TextView con;
    private boolean rec_con = false;

    private ImageView upButton;
    private ImageView downButton;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);

        upButton = (ImageView) findViewById(R.id.up);
        downButton = (ImageView) findViewById(R.id.down);
        leftButton = (ImageView) findViewById(R.id.left);
        rightButton = (ImageView) findViewById(R.id.right);
        image = (ImageView) findViewById(R.id.rec);

        setOnClick();

        con = (TextView) findViewById(R.id.con);
        address = (String) getIntent().getSerializableExtra("address");
        try_connect();
    }

    public void setOnClick() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rec_con) {
                    try_connect();
                }
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_send('1');
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_send('2');
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_send('3');
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_send('4');
            }
        });
    }

    public void my_send(char n) {
        try {
            Toast.makeText(MainPanel.this, "send" + n, Toast.LENGTH_SHORT).show();
            OutputStream outStream = clientSocket.getOutputStream();
            outStream.write(n);
        } catch (IOException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "er" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void try_connect() {

        BluetoothDevice Spider = mBlurtooth.getRemoteDevice(address);
        try {
            Method m = Spider.getClass().getMethod("createRfcommSocket",
                    new Class[] {int.class});

            clientSocket = (BluetoothSocket) m.invoke(Spider, 1);
            clientSocket.connect();
            Toast.makeText(MainPanel.this, "Success", Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#00961e"));
            con.setText("Connected!");
            TextView status = (TextView) findViewById(R.id.status);
            status.setText("Status: Connect");
            rec_con = true;
            ImageView image = (ImageView) findViewById(R.id.rec);
            image.setImageResource(R.drawable.ic_wifi);

        }catch (IOException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - IOException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - IOException");
            return;
        } catch (SecurityException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - " +
                            "SecurityException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - SecurityException");
            return;
        } catch (NoSuchMethodException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - " +
                            "NoSuchMethodException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - NoSuchMethodException");
            return;
        } catch (IllegalArgumentException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - " +
                            "IllegalArgumentException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - IllegalArgumentException");
            return;
        } catch (IllegalAccessException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - " +
                            "IllegalAccessException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - IllegalAccessException");
            return;
        } catch (InvocationTargetException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(MainPanel.this, "Bluetooth error in connect - " +
                            "InvocationTargetException",
                    Toast.LENGTH_SHORT).show();
            con.setTextColor(Color.parseColor("#ba1007"));
            con.setText("Bluetooth error in connect - InvocationTargetException");
            return;
        }

    }
}
