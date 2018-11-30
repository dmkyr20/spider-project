package com.example.dk200.hexapod;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BluetoothConnect extends AppCompatActivity {

    BluetoothAdapter rBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice Spider = null;
    BluetoothSocket clientSocket = null;
    TextView status_find;
    String find_str = "Found devices:";
    String nofind_str = "No devices found";

    private static final int REQUEST_ENABLE_BT = 1;
    ArrayList<String> rAddress = new ArrayList<String>();

    private  void bluetooth_connect() throws InterruptedException {
        if(rBluetoothAdapter == null) {
            Toast.makeText(BluetoothConnect.this, "Bluetooth Adapter = null",
                    Toast.LENGTH_SHORT).show();
            TimeUnit.SECONDS.sleep(5);
            status_find.setText(nofind_str);
            bluetooth_reconnect();
        }
        else {
            if(rBluetoothAdapter.isEnabled()) {
                status_find.setText(find_str);
                bluetooth_list();
            }
            else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                Toast.makeText(BluetoothConnect.this, "Can't connect",
                        Toast.LENGTH_SHORT).show();
                status_find.setText(nofind_str);

                bluetooth_reconnect();
            }
        }
    }

    private void bluetooth_connect_to_device(String address) {
        Spider = rBluetoothAdapter.getRemoteDevice(address);
        try {
            Method m = Spider.getClass().getMethod("createRfcommSocket",
                    new Class[] {int.class});

            clientSocket = (BluetoothSocket) m.invoke(Spider, 1);
            clientSocket.connect();

        }catch (IOException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 1",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (SecurityException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 2",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchMethodException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 3",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalArgumentException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 4",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalAccessException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 5",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (InvocationTargetException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 6",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(BluetoothConnect.this, "Connect!", Toast.LENGTH_SHORT).show();
    }

    private void bluetooth_list() {
        String status;
        String mydevicename= rBluetoothAdapter.getName();
        status= "Hello " + mydevicename;

        Toast.makeText(BluetoothConnect.this, status, Toast.LENGTH_SHORT).show();

        Set<BluetoothDevice> pairedDevices = rBluetoothAdapter.getBondedDevices();
        ListView mList = (ListView) findViewById(R.id.h22);
        mList.setVisibility(View.VISIBLE);

        if(pairedDevices.size() > 0) {
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1);
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName());
                rAddress.add(device.getAddress());
            }
            mList.setAdapter(mArrayAdapter);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String address = rAddress.get(position);
                    Toast.makeText(BluetoothConnect.this, "Try connect to " + address,
                            Toast.LENGTH_SHORT).show();
                    bluetooth_connect_to_device(address);
                }
            });
        }
    }

    private void bluetooth_reconnect() {
        Button reconnect_but = (Button) findViewById(R.id.reconnect);
        reconnect_but.setVisibility(View.VISIBLE);

        reconnect_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(rBluetoothAdapter != null) {
                        if(rBluetoothAdapter.isEnabled()) {
                            status_find.setText(find_str);
                            bluetooth_list();
                        }
                        else {
                            bluetooth_connect();
                        }
                    }
                    else {
                        bluetooth_connect();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);
        status_find = (TextView) findViewById(R.id.status);

        try {
            TimeUnit.SECONDS.sleep(1);
            bluetooth_connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
