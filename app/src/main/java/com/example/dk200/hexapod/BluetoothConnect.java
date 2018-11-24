package com.example.dk200.hexapod;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
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

    private static final int REQUEST_ENABLE_BT = 1;
    ArrayList<String> rAddress = new ArrayList<String>();
    private boolean reconect_but_vis = false;

    private  void bluetooth_test() throws InterruptedException {
        if(rBluetoothAdapter == null) {
            Toast.makeText(BluetoothConnect.this, "Bluetooth Adapter = null",
                    Toast.LENGTH_SHORT).show();
            TimeUnit.SECONDS.sleep(5);
            bluetooth_wait();
        }
        else {
            if(rBluetoothAdapter.isEnabled()) {
                bluetooth_list();
            }
            else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                TimeUnit.SECONDS.sleep(5);

                if(rBluetoothAdapter.isEnabled()) {
                    bluetooth_list();
                }
                else {
                    enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                    TimeUnit.SECONDS.sleep(5);
                    if(rBluetoothAdapter.isEnabled()) {
                        bluetooth_list();
                    }
                    else {
                        if(!reconect_but_vis) {
                            reconect_but_vis = true;
                            Toast.makeText(BluetoothConnect.this, "Can't connect",
                                    Toast.LENGTH_SHORT).show();
                            bluetooth_wait();
                        }
                    }
                }
            }
        }
    }

    private void bluetooth_connect(String address) {
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
        } catch (SecurityException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 2",
                    Toast.LENGTH_SHORT).show();
        } catch (NoSuchMethodException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 3",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 4",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 5",
                    Toast.LENGTH_SHORT).show();
        } catch (InvocationTargetException e) {
            Log.d("BLUETOOTH", e.getMessage());
            Toast.makeText(BluetoothConnect.this, "Bluetooth error in connect 6",
                    Toast.LENGTH_SHORT).show();
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
                    android.R.layout.simple_list_item_1){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary)); // ListView text color
                    return view;
                }
            };
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
                    bluetooth_connect(address);
                }
            });
        }
    }

    private void bluetooth_wait() {
        Button reconnect_but = (Button) findViewById(R.id.reconnect);
        reconnect_but.setVisibility(View.VISIBLE);

        reconnect_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bluetooth_test();
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

        try {
            TimeUnit.SECONDS.sleep(5);
            bluetooth_test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
