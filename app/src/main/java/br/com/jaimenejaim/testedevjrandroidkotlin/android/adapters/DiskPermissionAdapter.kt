package br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface DiskPermissionAdapter {

    fun hasPermission(): Boolean

    fun hasPermissionDenied(): Boolean

    fun requestPermissions()

    fun checkPermissions()

    fun resolvePermission()

    fun continueApp()

    fun setCallback(callback: DiskPermissionAdapterImpl.BluetoothPermissionCallback)

    fun setLauncher(launcher: ActivityResultLauncher<Array<String>>)

    fun setLauncherNewApi(launcher: ActivityResultLauncher<Intent>?)

}