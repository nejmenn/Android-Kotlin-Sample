package br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DiskPermissionAdapterImpl @Inject constructor(
    @ApplicationContext val context: Context
): DiskPermissionAdapter {

    private lateinit var _callback: BluetoothPermissionCallback
    private var launcher: ActivityResultLauncher<Array<String>>? = null
    private var newApiLauncher: ActivityResultLauncher<Intent>? = null

    override fun hasPermission(): Boolean {
        return if(Build.VERSION.SDK_INT >= VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(context, permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }

    override fun hasPermissionDenied(): Boolean {
        return if(Build.VERSION.SDK_INT >= VERSION_CODES.R) {
            Environment.isExternalStorageManager().not()
        } else {
            ContextCompat.checkSelfPermission(context, permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED &&
                    ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
        }
    }

    override fun requestPermissions() {
        if(Build.VERSION.SDK_INT >= VERSION_CODES.R) {
            val intent = Intent()
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.setData(uri)
            newApiLauncher?.launch(intent)
        } else {
            launcher?.launch(
                arrayOf(
                    permission.WRITE_EXTERNAL_STORAGE,
                    permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

    override fun checkPermissions() {
        if (!hasPermission()) {
            requestPermissions()
        } else {
            resolvePermission()
        }
    }

    override fun continueApp() {
        _callback.resolvePermission()
    }

    override fun resolvePermission() {
        _callback.resolvePermission()
    }

    override fun setCallback(callback: BluetoothPermissionCallback) {
        this._callback = callback
    }

    override fun setLauncher(launcher: ActivityResultLauncher<Array<String>>) {
        this.launcher = launcher
    }

    override fun setLauncherNewApi(launcher: ActivityResultLauncher<Intent>?) {
        this.newApiLauncher = launcher
    }

    interface BluetoothPermissionCallback {
        fun resolvePermission()
    }
}