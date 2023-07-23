package com.example.drawresultanimation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.drawresultanimation.databinding.ViewDrawImageBinding

class DrawImageView @JvmOverloads constructor(
  context: Context,
  attributeSet: AttributeSet? = null,
  defStyleAttr: Int = 0,
  defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

  private val binding: ViewDrawImageBinding = ViewDrawImageBinding.inflate(LayoutInflater.from(context), this)

  private val defaultBackgroundRes = R.drawable.test

  fun loadBackgroundOnce(res: Int = defaultBackgroundRes, onAnimationEnd: (() -> Unit)? = null) {
    Glide
      .with(context)
      .load(res)
      .diskCacheStrategy(DiskCacheStrategy.NONE)
      .into(GifDrawableImageViewTarget(binding.background, 1, onAnimationEnd))
  }
}