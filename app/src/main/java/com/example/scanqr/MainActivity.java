package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.scanqr.databinding.ActivityMainBinding;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.nio.charset.StandardCharsets;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        createQR();
        binding.btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan!!");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                //第 98 行IntentIntegrator(ActiveActivator) the Constructor
                //第 146 行IntentIntegrator forFragment(Mảnh phân mảnh)
                //第 167 行IntentIntegrator setPrompt(Dấu nhắc chuỗi) Đặt lời nhắc để hiển thị trên màn hình chụp, thay vì sử dụng mặc định.
                //第 179 行IntentIntegrator setOrientationLocked(boolean bị khóa) Theo mặc định, hướng bị khóa. Đặt thành false để không khóa.
                //第 189 行IntentIntegrator setCameraId(int cameraId) Sử dụng ID camera được chỉ định.
                //第 214 行IntentIntegrator setBeepEnabled(boolean đã bật) Đặt thành false để tắt tiếng bíp khi quét.
                //第 225 行IntentIntegrator setBarcodeImageEnabled(đã bật boolean) Đặt thành true để cho phép lưu hình ảnh mã vạch và gửi đường dẫn của nó trong Intent kết quả.
                //第 247 行IntentIntegrator setDesiredBarcodeFormats(Chuỗi…mong muốnBarcodeFormats) Đặt định dạng mã vạch mong muốn để quét.
                //第 258 行 void startedScan() Bắt đầu quét tất cả các loại mã vạch đã biết bằng máy ảnh mặc định.
            }
        });
        binding.btnSave.setOnClickListener(v ->
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    saveFile();
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
    }

    private void saveFile() {
        QRGSaver qrgSaver = new QRGSaver();
        qrgSaver.save(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/QRCode/",
                "qrs", bitmap, QRGContents.ImageType.IMAGE_PNG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                Log.d("sssssssssssss", "onActivityResult: " + result.getBarcodeImagePath());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void createQR() {
        QRGEncoder qrgEncoder = new QRGEncoder(null, bundle(), QRGContents.Type.CONTACT, 1000);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        binding.img.setImageBitmap(bitmap);
    }

    private Bundle bundle() {
        Bundle bundle = new Bundle();
        bundle.putString("name", "editTextName.getText().toString()");
        bundle.putString("postal", "editTextAddress.getText().toString()");
        bundle.putString("phone", "editTextPhone.getText().toString()");
        bundle.putString("email", "editTextAddressMail.getText().toString()");
        bundle.putString("notes", " editTextNotes.getText().toString()");
        bundle.putString("company", " editTextOrganization.getText().toString()");
        bundle.putString("data", "editTextURL.getText().toString()");
        return bundle;
    }

}