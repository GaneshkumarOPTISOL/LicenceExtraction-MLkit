package com.obs.demoappclient.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.obs.demoappclient.R
import com.optisol.documentscanner.ScanActivity
import com.optisol.documentscanner.model.DocumentScannerErrorModel
import com.optisol.documentscanner.model.ScannerResults
import kotlinx.android.synthetic.main.activity_app_scan.*
import java.io.File

class AppScanActivity : ScanActivity() {
    companion object {
        private val TAG = AppScanActivity::class.simpleName

        fun start(context: Context) {
            val intent = Intent(context, AppScanActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var alertDialogBuilder: android.app.AlertDialog.Builder? = null
    private var alertDialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_scan)
        addFragmentContentLayout()
    }

    override fun onError(error: DocumentScannerErrorModel) {
        showAlertDialog(
            getString(R.string.error_label),
            error.errorMessage?.error,
            getString(R.string.ok_label)
        )
    }

    override fun onSuccess(scannerResults: ScannerResults) {
        val intent = Intent(this, OverallDetailsActivity::class.java)
        intent.putExtra("VisionText", scannerResults.croppedImageFile.toString())
        startActivity(intent)
    }

    override fun onClose() {
        Log.d(TAG, "onClose")
        finish()
    }

    private fun showProgressBar() {
        progressLayoutApp.isVisible = true
    }

    private fun hideProgessBar() {
        progressLayoutApp.isVisible = false
    }


    private fun showAlertDialog(title: String?, message: String?, buttonMessage: String) {
        alertDialogBuilder = android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonMessage) { dialog, which ->

            }
        alertDialog?.dismiss()
        alertDialog = alertDialogBuilder?.create()
        alertDialog?.setCanceledOnTouchOutside(false)
        alertDialog?.show()
    }

    val File.size get() = if (!exists()) 0.0 else length().toDouble()
    val File.sizeInKb get() = size / 1024
    val File.sizeInMb get() = sizeInKb / 1024
}