package com.mudio.movies

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

inline fun<reified T: Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun<reified T: Activity> Activity.createIntent() = Intent(this, T::class.java)

inline fun<reified T: Any> parseJson(jsonAsString: String) = jacksonObjectMapper().readValue<T>(jsonAsString)

fun Activity.startYoutubeIntent(url: String){ startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }

