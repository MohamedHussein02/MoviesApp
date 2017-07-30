package com.mudio.movies

class ActivityCreated private constructor(){
    var introSplashActivityCreated = false

    companion object {
        val instance : ActivityCreated by lazy{ ActivityCreated() }
    }
}
