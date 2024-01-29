package com.serhii_00_tymoshenko.notes.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Note(
    val title: String,
    val content: String? = null,
    val imageUri: Uri? = null,
    val id: String = UUID.randomUUID().toString()
) : Parcelable
