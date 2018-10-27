package com.example.saidivya.barcodescanner;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkpermission()) {
                Toast.makeText(MainActivity.this, "permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }
    private boolean checkpermission()
    {
        return (ContextCompat.checkSelfPermission(MainActivity.this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission()
    {

        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[])
    {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                if (grantResults.length>0)
            {
                boolean cameraAccepted=grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        if (cameraAccepted)
                        {
                            Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                            {
                                if(shouldShowRequestPermissionRationale(CAMERA)) {
                                    displayAlertMessage("you need to allow access for both permissions", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);

                                        }
                                    });
                                    return;
                                }
                                    }
                                }
                            }
                        break;
                        }

            }
            @Override
                    public void onResume()
        {
                super.onResume();
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M);
                {
                    if (checkpermission())
                    {
                        if (scannerView==null)
                        {
                            scannerView = new ZXingScannerView(this);
                            setContentView(scannerView);
                        }
                        scannerView.setResultHandler(this);
                        scannerView.startCamera();
                    }
                else
                    {
                        requestPermission();
                    }
                }

                }
                @Override
                        public void onDestroy() {
                        super.onDestroy();
                        scannerView.stopCamera();

    }

    private void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",listener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                scannerView.resumeCameraPreview(MainActivity.this);

            }

        });
                builder.setNeutralButton("visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                        startActivity(intent);

                    }
                });
    builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }





}
