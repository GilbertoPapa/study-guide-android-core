package com.guide.android_core

import android.R.attr
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.guide.android_core.databinding.ActivityBlurImageBinding
import com.guide.android_core.databinding.ActivityMainBinding
import android.R.attr.bitmap

import android.R.attr.data
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var btn_image : Button
    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btn_image = binding.btnSelectImage

        btn_image.setOnClickListener(){
            abreGaleria()
        }

        launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            enviandoImagem(it)
        }

    }

    private fun enviandoImagem(it: Uri?) {

    }

    private fun abreGaleria() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, valor: Intent?) {
        super.onActivityResult(requestCode, resultCode, valor)
        if (resultCode == RESULT_OK && requestCode == 0){
            val source = ImageDecoder.createSource(this.contentResolver, valor?.data!!)
            val bitmap : Bitmap  = ImageDecoder.decodeBitmap(source)

            binding.imageView.setImageBitmap(bitmap)
        }
    }
}