package com.github.bassaer.androidcache

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.StringWriter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        createCache()

        val textView = findViewById<TextView>(R.id.main_text)
        textView.text = getCacheInfo()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun createCache() {
        val str = object : StringWriter() {
            init {
                for (i in 1..1024) write("a")
            }
        }.toString()
        log(str)
        val newFilePath = "${cacheDir.absolutePath}/${System.currentTimeMillis() / 1000}.txt"
        val file = FileOutputStream(File(newFilePath))
        val outputStream = BufferedOutputStream(file)
        outputStream.write(str.toByteArray())
        log("create file : $newFilePath")
    }

    private fun getCacheInfo() : String {

        log("cacheDir : ${cacheDir.absoluteFile}")
        val cacheDirectory =  File(cacheDir.absolutePath)
        val files = cacheDirectory.listFiles()

        val builder = StringBuilder()
        builder.append("[Info]\n")
        builder.append("size: ${cacheDirectory.length()}\n")
        builder.append("[Dirs]\n")
        files.forEach {
            if (it.isDirectory) {
                builder.append("${it.name}\n")
            }
        }
        builder.append("[Files]\n")
        files.forEach {
            if (it.isFile) {
                builder.append("${it.name}\n")
            }
        }
        builder.append("[Done]")

        return builder.toString()
    }

    private fun log(msg: String) {
        Log.d("ANDROID_CACHE_LOG", msg)
    }
}
