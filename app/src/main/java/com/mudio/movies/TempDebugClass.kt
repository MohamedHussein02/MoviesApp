package com.mudio.movies

class TempDebugClass private constructor(){

    var httpResultCount = 0

    companion object {
        val instance: TempDebugClass by lazy { TempDebugClass() }
    }
}
