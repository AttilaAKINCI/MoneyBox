package com.akinci.moneybox.common.component.filedownloader

interface FileDownloader {
    suspend fun download(downloadUrl : String, fileDownloadListener : FileDownloadEventListener)
}