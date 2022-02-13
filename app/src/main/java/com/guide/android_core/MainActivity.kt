package com.guide.android_core

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.guide.android_core.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var btn_image : Button
    private val viewModel: BlurViewModel by viewModels { BlurViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btn_image = binding.btnSelectImage

        btn_image.setOnClickListener(){
            abreGaleria()
        }

        binding.btnGo.setOnClickListener{ viewModel.aplicarBlur(nivelBlur) }

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
            viewModel.imagemUri = valor?.data!!

            binding.imageView.setImageBitmap(bitmap)
        }
    }

    private val nivelBlur: Int
        get() =
            when (binding.optionGroup.checkedRadioButtonId) {
                R.id.option_blur_1 -> 1
                R.id.option_blur_2 -> 2
                R.id.option_blur_3 -> 3
                else -> 1
            }
}