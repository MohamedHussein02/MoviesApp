package com.mudio.movies

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

inline fun<reified T: Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun<reified T: Activity> Activity.createIntent() = Intent(this, T::class.java)

inline fun<reified T: Any> parseJson(jsonAsString: String) = jacksonObjectMapper().readValue<T>(jsonAsString)
