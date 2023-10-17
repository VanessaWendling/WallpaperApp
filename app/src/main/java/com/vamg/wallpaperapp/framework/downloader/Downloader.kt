package com.vamg.wallpaperapp.framework.downloader

interface Downloader {

    fun downloadFile(url: String, description: String) : Long
}