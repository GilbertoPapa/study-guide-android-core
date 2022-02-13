package com.guide.android_core

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.guide.android_core.workers.BlurWorker

class BlurViewModel(aplicacao: Application) : ViewModel() {
    internal lateinit var outputUri: Uri
    internal lateinit var uriImagemSelecionada: Uri
    internal var imagemUri: Uri? = null
    private val workManager = WorkManager.getInstance(aplicacao)


    internal fun aplicarBlur(nivelBlur: Int) {
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(criandoEntradaDadosDeUri())
            .build()

        workManager.enqueue(blurRequest)

    }

    private fun criandoEntradaDadosDeUri() : Data {
        val construtor = Data.Builder()
        imagemUri?.let {
            construtor.putString(KEY_IMAGE_URI, imagemUri.toString())
        }
        return construtor.build()
    }
}

class BlurViewModelFactory(private val aplicacao: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
            BlurViewModel(aplicacao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}