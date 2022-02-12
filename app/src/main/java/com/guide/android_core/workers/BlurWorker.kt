package com.guide.android_core.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.guide.android_core.R
import java.lang.IllegalArgumentException

class BlurWorker(contexto : Context, parametros : WorkerParameters) : Worker(contexto, parametros) {
    override fun doWork(): Result {
        val contextoAplicacao = applicationContext
        val resourceUri = inputData.getString("KEY_IMAGE_URI")

        notificaoStatus("Imagem com BLur", contextoAplicacao)


        return try{
            val fotoTeste = BitmapFactory.decodeResource(
                contextoAplicacao.resources,
                R.drawable.download)

            val saida = blurBitmap(fotoTeste, contextoAplicacao)

            val saidaUri = writeBitmapToFile(contextoAplicacao, saida)

            notificaoStatus("A saida é $saidaUri", contextoAplicacao)
            Result.success()

        }catch (throwable: Throwable){
            Log.e("In BlurWorker", "Erro para aplicar o Blur")
            throwable.printStackTrace()
            Result.failure()
        }

    }
}