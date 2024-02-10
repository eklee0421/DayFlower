package com.nyangzzi.dayFlower.data.repository

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.nyangzzi.dayFlower.domain.repository.OpenSourceLicenseRepository

class OpenSourceLicenseRepositoryImpl(
    private val context: Context
) : OpenSourceLicenseRepository {
    override fun getOpenSourceLicense() {
        val intent = Intent(context, OssLicensesMenuActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}