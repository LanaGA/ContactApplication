package com.example.contact.base

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val IMAGE_TYPE = "image/*"
const val READ_EXTERNAL_STORAGE = "READ_EXTERNAL_STORAGE"
const val RESULT_AMOUNT = 1
const val REQUEST_PERMISSION = 2
fun Fragment.requestPermissionForReadFromStorage() {
    requestPermissions(
        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
        REQUEST_PERMISSION
    )
}

fun Fragment.checkPermissionForReadFromStorage(): Boolean {
    val result = ContextCompat.checkSelfPermission(
        requireContext(),
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    return  result == PackageManager.PERMISSION_GRANTED
}

