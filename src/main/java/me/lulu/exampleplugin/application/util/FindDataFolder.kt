package me.lulu.exampleplugin.application.util

import java.nio.file.Path

interface FindDataFolder {
    fun getDataFolder(): Path
}