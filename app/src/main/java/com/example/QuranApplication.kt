package com.example

import android.app.Application
import android.system.Os
import java.io.File

class QuranApplication : Application() {

    companion object {
        lateinit var instance: QuranApplication
            private set

        init {
            try {
                // Set native process environment variables before any graphics/EGL subsystem is initialized
                Os.setenv("LIBGL_ALWAYS_SOFTWARE", "1", true)
                Os.setenv("MESA_LOADER_DRIVER_OVERRIDE", "swrast", true)
                Os.setenv("GALLIUM_DRIVER", "softpipe", true)
                Os.setenv("EGL_LOG_LEVEL", "fatal", true)
                Os.setenv("MESA_LOG_LEVEL", "fatal", true)
                Os.setenv("LIBGL_DEBUG", "quiet", true)
                Os.setenv("MESA_DEBUG", "0", true)
            } catch (_: Throwable) {}
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        try {
            Os.setenv("LIBGL_ALWAYS_SOFTWARE", "1", true)
            Os.setenv("MESA_LOADER_DRIVER_OVERRIDE", "swrast", true)
            Os.setenv("GALLIUM_DRIVER", "softpipe", true)
            Os.setenv("EGL_LOG_LEVEL", "fatal", true)
            Os.setenv("MESA_LOG_LEVEL", "fatal", true)
            Os.setenv("LIBGL_DEBUG", "quiet", true)
            Os.setenv("MESA_DEBUG", "0", true)
        } catch (_: Throwable) {}
        try {
            // Pre-create Chromium WebView cache directories to prevent initial cold-start index reconstruction logs
            val jsCache = File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/js")
            val wasmCache = File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/wasm")
            if (!jsCache.exists()) jsCache.mkdirs()
            if (!wasmCache.exists()) wasmCache.mkdirs()
        } catch (_: Exception) {}
    }
}
