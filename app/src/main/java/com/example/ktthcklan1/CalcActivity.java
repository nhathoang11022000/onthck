package com.example.ktthcklan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcActivity extends AppCompatActivity {
    Button btnAdd, btnSub, btnMul, btnDiv;
    EditText txtA, txtB;
    TextView txtResult;
    private ServiceConnection serviceConnection;
    private boolean isConnected;
    private MyServiceCalc myService;
    private String pheptoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        init();
        connectService();

    }
    private void connectService() {

        Intent intent = new Intent(this, MyServiceCalc.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyServiceCalc.MyBinder myBinder = (MyServiceCalc.MyBinder) service;

                myService = myBinder.getService();
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                myService = null;
            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
    void init(){
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        txtA=findViewById(R.id.txtA);
        txtB=findViewById(R.id.txtB);
        txtResult=findViewById(R.id.txtResult);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                txtResult.setText("");
                double result = myService.add(
                        Double.parseDouble(txtA.getText().toString()),
                        Double.parseDouble(txtB.getText().toString()));

                txtResult.setText(txtResult.getText().toString()+""+result);
                Toast.makeText(myService, "Result: " + result, Toast.LENGTH_SHORT).show();
                pheptoan = " + ";
                clickStartService();
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                txtResult.setText("");
                double result = myService.sub(
                        Double.parseDouble(txtA.getText().toString()),
                        Double.parseDouble(txtB.getText().toString()));

                txtResult.setText(txtResult.getText().toString()+""+result);
                Toast.makeText(myService, "Result: " + result, Toast.LENGTH_SHORT).show();
                pheptoan = " - ";
                clickStartService();
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                txtResult.setText("");
                double result = myService.multi(
                        Double.parseDouble(txtA.getText().toString()),
                        Double.parseDouble(txtB.getText().toString()));

                txtResult.setText(txtResult.getText().toString()+""+result);
                Toast.makeText(myService, "Result: " + result, Toast.LENGTH_SHORT).show();
                pheptoan = " * ";
                clickStartService();
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected){
                    return;
                }
                txtResult.setText("");
                double result = myService.div(
                        Double.parseDouble(txtA.getText().toString()),
                        Double.parseDouble(txtB.getText().toString()));

                txtResult.setText(txtResult.getText().toString()+""+result);
                Toast.makeText(myService, "Result: " + result, Toast.LENGTH_SHORT).show();
                pheptoan = " : ";
                clickStartService();
            }
        });

    }
    private void clickStartService() {
        Intent intent = new Intent(this, MyServiceCalc.class);
        intent.putExtra("key_data_intent", txtA.getText().toString().trim() + pheptoan +
                txtB.getText().toString().trim() + " = " + txtResult.getText());
        startService(intent);
    }
    private void clickToStopService() {
        Intent intent = new Intent(this, MyServiceCalc.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}