package com.akinci.moneybox.common.component.filedownloader

class FakeFileDownloader : FileDownloader {

    private var shouldReturnFileDownloadError = false
    fun setShouldReturnFileDownloadError(value : Boolean){ shouldReturnFileDownloadError = value }

    override suspend fun download(
        downloadUrl: String,
        fileDownloadListener: FileDownloadEventListener
    ) {
        // assume that file has been download process started..
        if(shouldReturnFileDownloadError){
            fileDownloadListener.onError("File Download encounter an error.")
        }else{
            fileDownloadListener.onEnqueued()
        }
    }
}