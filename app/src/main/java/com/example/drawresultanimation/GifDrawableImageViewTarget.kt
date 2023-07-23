package com.example.drawresultanimation

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget


class GifDrawableImageViewTarget(
  view: ImageView?,
  loopCount: Int,
  private val onAnimationEnd: (() -> Unit)? = null
) : ImageViewTarget<Drawable>(view) {

  private var mLoopCount = loopCount

  override fun setResource(resource: Drawable?) {
    if (resource is GifDrawable) {
      resource.setLoopCount(mLoopCount)
      resource.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
          view.setImageDrawable(null)
          onAnimationEnd?.invoke()
        }
      })
    } else if (resource is AnimatedImageDrawable) {
      resource.repeatCount = 0
      resource.registerAnimationCallback(object : Animatable2.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
          view.setImageDrawable(null)
          onAnimationEnd?.invoke()
        }
      })
    }
    view.setImageDrawable(resource)
  }
}