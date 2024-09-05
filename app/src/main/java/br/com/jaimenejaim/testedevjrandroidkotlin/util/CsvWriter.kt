package br.com.jaimenejaim.testedevjrandroidkotlin.util

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.OutputStream
import kotlin.reflect.full.memberProperties

class CsvWriter<T: Any>(
    private val context: Context,
    private val fileName: String,
    private val clazz: Class<T>) {

    suspend fun writeData(dataList: List<T>) = withContext(Dispatchers.IO) {
        if (dataList.isEmpty()) return@withContext

        val csvFileName = fileName
        val outputStream: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Para Android 10 (API 29) e superior, usamos o MediaStore
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, csvFileName)
                put(MediaStore.Downloads.MIME_TYPE, "text/csv")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let { context.contentResolver.openOutputStream(it) }
        } else {
            // Para versões anteriores do Android 10, usamos o caminho tradicional para a pasta Downloads
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, csvFileName)
            file.outputStream()
        }

        outputStream?.use { writer ->
            // Escreve o cabeçalho
            val header = dataList.first()::class.memberProperties.joinToString(",") { it.name }
            writer.write((header + "\n").toByteArray())

            // Escreve os dados
            dataList.forEach { item ->
                val row = clazz.kotlin.memberProperties.joinToString(",") { prop ->
                    (prop.get(item) as? String ?: prop.get(item)?.toString() ?: "")
                }
                writer.write((row + "\n").toByteArray())
            }
        }
    }
}