package com.akinci.moneybox.common.component

import android.app.DownloadManager
import android.app.DownloadManager.Request.*
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class FileDownloader @Inject constructor(
    @ApplicationContext val context: Context
) {
    /** This function automatically downloads document via system services **/
    fun downloadDocument(
        downloadUrl : String,
        onComplete : (() -> (Unit))? = null,
        onError : ((String) -> (Unit))? = null
    ) {
        try{
            val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(downloadUrl))
            request.setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
            request.setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setVisibleInDownloadsUi(true)
            request.setAllowedOverMetered(true)
            request.setAllowedOverRoaming(false)
            request.setTitle("document")
            request.setDescription("moneybox document")

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                request.setDestinationInExternalFilesDir(context, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath, "document.pdf")
            }else{
                request.setDestinationInExternalPublicDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,"document.pdf")
            }

            downloadManager.enqueue(request)
            onComplete?.invoke()
        }catch (exception : Exception){
            Timber.d("Exception Occurred on document download : ${exception.message}")
            onError?.invoke(exception.message!!)
        }
    }
}