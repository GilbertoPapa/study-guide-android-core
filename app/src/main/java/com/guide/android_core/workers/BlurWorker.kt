package com.guide.android_core.workers

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.guide.android_core.*
import java.lang.IllegalArgumentException

private const val TAG = "In BlurWorker"
class BlurWorker(contexto : Context, parametros : WorkerParameters) : Worker(contexto, parametros) {

    override fun doWork(): Result {
        val contextoAplicacao = applicationContext
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        notificaoStatus("Imagem com BLur", contextoAplicacao)


        return try{
//            val foto = BitmapFactory.decodeResource(
//                contextoAplicacao.resources,
//                R.drawable.download)

            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Entrada invalida da uri")
                throw IllegalArgumentException("Entrada invalida da uri")
            }
            val resolver = contextoAplicacao.contentResolver
            val foto = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val saida = blurBitmap(foto, contextoAplicacao)

            val saidaUri = writeBitmapToFile(contextoAplicacao, saida)
            val saidaArquivo = workDataOf(KEY_IMAGE_URI to saidaUri.toString())

//            notificaoStatus("A saida é $saidaUri", contextoAplicacao)
            Result.success(saidaArquivo)

        }catch (throwable: Throwable){
            Log.e(TAG, "Erro para aplicar o Blur")
            throwable.printStackTrace()
            Result.failure()
        }

    }

}