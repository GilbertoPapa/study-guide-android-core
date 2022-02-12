package com.guide.android_core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.guide.android_core.workers.BlurWorker

class BlurViewModel(aplicacao: Application) : ViewModel() {
    private val workManager = WorkManager.getInstance(aplicacao)

    internal fun aplicarBlur(nivelBlur: Int) {
        workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))
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