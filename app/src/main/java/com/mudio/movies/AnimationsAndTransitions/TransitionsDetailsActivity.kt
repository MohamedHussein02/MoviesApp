package com.mudio.movies.AnimationsAndTransitions

import android.view.View
import android.view.ViewGroup
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.Transition
import com.transitionseverywhere.TransitionManager

class TransitionsDetailsActivity {

    private var goneNext =false
    private var visibleNext =true


    fun ExpandCollapseTransitioning(layout: ViewGroup, view: View) {
        TransitionManager.beginDelayedTransition(layout, ManageTransition(view))
        view.visibility=View.INVISIBLE

    }

    fun ManageTransition(view: View): Transition {
        var transition = ChangeBounds().addListener(
                object : Transition.TransitionListener {
                    override fun onTransitionResume(transition: Transition?) {}

                    override fun onTransitionEnd(transition: Transition?) {
                        if (goneNext == true) {
                            if (view.visibility == View.INVISIBLE) {
                                view.visibility = View.GONE
                                visibleNext =!visibleNext
                                goneNext =!goneNext
                            }
                        }

                    }

                    override fun onTransitionPause(transition: Transition?) {}

                    override fun onTransitionCancel(transition: Transition?) {}

                    override fun onTransitionStart(transition: Transition?) {
                        if(visibleNext ==true){
                            if(view.visibility == View.INVISIBLE){
                                view.visibility= View.VISIBLE
                                visibleNext =!visibleNext
                                goneNext =!goneNext
                            }
                        }
                    }

                })
        return transition
    }
}
