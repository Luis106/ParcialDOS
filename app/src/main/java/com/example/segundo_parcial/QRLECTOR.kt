package com.example.segundo_parcial

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

class QRLECTOR : AppCompatActivity() {

    private lateinit var codescanner: CodeScanner
    val MY_CAMERA_PERMISSION_REQUEST = 11111;
    private lateinit var scannerview: CodeScannerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrlector)

        scannerview = findViewById(R.id.scannerView)

        codescanner = CodeScanner(this, scannerview)
        codescanner.camera =  CodeScanner.CAMERA_BACK
        codescanner.formats = CodeScanner.ALL_FORMATS
        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled = true
        codescanner.isFlashEnabled = false


        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread{
                // Toast.makeText(this, "Scan Result: ${it.text}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Welcome_Activity::class.java)
                //Pasar valores a la siguiente actividad

                intent.putExtra("qrCodeValue", it.text.toString())
                startActivity(intent)
                codescanner.stopPreview()

            }
        }
        codescanner.errorCallback = ErrorCallback  {
            runOnUiThread{
                Toast.makeText(this, "Scan Result: ${it.message}", Toast.LENGTH_LONG).show()
                //codescanner.stopPreview()
            }
        }

        codescanner.startPreview()
        checkPermission()


    }

    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_REQUEST)
        } else {
            codescanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==MY_CAMERA_PERMISSION_REQUEST&&grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            codescanner.startPreview()
        } else {
            Toast.makeText(this, "CAn not scan until you give the camera permission", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        codescanner.startPreview()

    }

    override fun onPause() {
        codescanner.releaseResources()
        super.onPause()

    }

}