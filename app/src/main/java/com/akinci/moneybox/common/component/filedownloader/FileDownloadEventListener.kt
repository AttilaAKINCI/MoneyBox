package com.akinci.moneybox.common.component.filedownloader

interface FileDownloadEventListener {
    fun onError(message : String)
    fun onEnqueued()
}