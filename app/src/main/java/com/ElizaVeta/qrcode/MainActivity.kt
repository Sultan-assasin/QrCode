package com.ElizaVeta.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    private lateinit var imageQRCode: ImageView
    private lateinit var editText: EditText
    private lateinit var buttonGenerateQr: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        clickListener()

    }

    private fun init() {
        imageQRCode = findViewById(R.id.imageQrCode)
        editText = findViewById(R.id.etData)
        buttonGenerateQr = findViewById(R.id.buttonGenerateQr)
    }

    private fun clickListener() {
        buttonGenerateQr.setOnClickListener {
            val data = editText.text.toString().trim()
            if (data.isEmpty()) {
                Toast.makeText(this, "Enter some data", Toast.LENGTH_SHORT).show()
            } else {
                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until height) {
                        for (y in 0 until height) {
                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    imageQRCode.setImageBitmap(bmp)
                } catch (e: WriterException) {
                    Log.d("error", e.message.toString())
                }
            }


        }
    }
}