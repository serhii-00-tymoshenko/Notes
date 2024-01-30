package com.serhii_00_tymoshenko.notes.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.serhii_00_tymoshenko.notes.R
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class FileProvider {
    companion object {
        fun getTempUri(context: Context): Uri {
            val tempImagesDirName = context.getString(R.string.temp_images_dir)
            val tempImageName = context.getString(R.string.temp_image)
            val authoritiesName = context.getString(R.string.authorities)

            val tempImagesDir = File(
                context.filesDir,
                tempImagesDirName
            )
            tempImagesDir.mkdir()

            val tempImage = File(
                tempImagesDir,
                tempImageName
            )

            val uri = FileProvider.getUriForFile(
                context,
                authoritiesName,
                tempImage
            )

            return uri
        }

        fun getGalleryUri(context: Context, tempImageUri: Uri?): Uri? {
            val contentResolver = context.contentResolver

            val contentValues = ContentValues().apply {
                val imageName = "Image_${System.currentTimeMillis()}.jpg"
                put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val imageUriGallery: Uri? =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            try {
                val outputStream: OutputStream? = imageUriGallery?.let { uri ->
                    contentResolver.openOutputStream(uri)
                }

                outputStream?.use { output ->
                    val inputStream: InputStream? = tempImageUri.let { uri ->
                        uri?.let { contentResolver.openInputStream(it) }
                    }

                    inputStream?.use { input ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return imageUriGallery
        }
    }
}