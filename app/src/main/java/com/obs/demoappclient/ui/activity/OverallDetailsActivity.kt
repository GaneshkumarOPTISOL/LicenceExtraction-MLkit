package com.obs.demoappclient.ui.activity

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.obs.demoappclient.R
import kotlinx.android.synthetic.main.activity_overall_details.*
import java.io.*

class OverallDetailsActivity : AppCompatActivity() {
    private var overallText: String? = ""
    var selectedBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overall_details)
        initView()
    }

    private fun initView() {
        overallText = intent.getStringExtra("VisionText")
        Log.v("OverallTextName", overallText.toString())
        convertFileToBitmap(overallText)
    }

    private fun convertFileToBitmap(overallText: String?) {
        var uri = Uri.fromFile(File(overallText))
        val image = InputImage.fromFilePath(this, uri)
        val result = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        result.process(image)
            .addOnSuccessListener { visionText ->
                setValues(visionText.text)
                Log.v("Overalltextname", visionText.text.toString())
            }
    }

    private fun setValues(overallText: String?) {
        try {
            Log.v("detailsoverall", overallText.toString())
            val txtr = overallText.toString().split("\n")
            rawtext.append(txtr[0])
            var firstNameSplit = txtr[3].trim().split(" ")
            var firstNam = firstNameSplit[1]
            var firstName = firstNam + " " + txtr[4].trim()
            tv_first_name.append(firstName)
            var lastNameSplit = txtr[2].trim().split(" ")
            var lastNam = lastNameSplit[1]
//            var lastName = txtr[2].trim()
            tv_last_name.append(lastNam)
            var dobandNaitonality = txtr[5].trim().split(" ")
            var dob = dobandNaitonality[1]
            tv_dob.append(dob)
            var nationality = dobandNaitonality[2] + " " + dobandNaitonality[3]
            tvnationality.append(nationality)

            var validFromS = txtr[6].trim().split(" ")
            var validToS = txtr[7].trim().split(" ")

            var validFrom = validFromS[1]
            validfrom.append(validFrom)
            var validTo = validToS[1]
            tvvalidto.append(validTo)

            var issuingAuthority = validFromS[3]
            tvissue.append(issuingAuthority)
            var uniqueid = txtr[9].trim().split(" ")
            tv_unique.append(uniqueid[0])
            var address =
                txtr[13].trim() + " " + txtr[14].trim() + " " + txtr[15].trim()
            tv_address.append(address)
        } catch (e: Exception) {
            Log.v("something went wrong", "Something wrong")
        }
    }
}