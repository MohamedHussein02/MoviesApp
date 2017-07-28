package com.mudio.movies

class ActivityCreated private constructor(){
    var introSplashActivityCreated = false
    var mainActivityCreated = false

    companion object {
        val instance : ActivityCreated by lazy{ ActivityCreated() }
    }
}
