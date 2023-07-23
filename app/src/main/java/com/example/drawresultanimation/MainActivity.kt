package com.example.drawresultanimation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.drawresultanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    initViewModel()
    initView()
    initialImages()
    initAnimator()
  }

  private fun initViewModel() {
    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    viewModel.currentIndex.observe(this) {
      if (it == 0) return@observe
      runScaleAnimation()
    }
  }

  private fun initView() {
    binding.playGifButton.setOnClickListener {
      it.isEnabled = false
      binding.playWebpButton.isEnabled = false
      viewModel.currentType = ResourceType.GIF
      runScaleAnimation()
    }

    binding.playWebpButton.setOnClickListener {
      it.isEnabled = false
      binding.playGifButton.isEnabled = false
      viewModel.currentType = ResourceType.WEBP
      runScaleAnimation()
    }
  }

  private val images: MutableList<DrawImageView> = mutableListOf()

  private fun initialImages() {
    images.addAll(
      listOf(
        binding.first,
        binding.second,
        binding.third,
        binding.fourth,
        binding.fifth,
        binding.sixth,
        binding.seventh,
        binding.eighth
      )
    )
  }

  private fun resetImagesVisible() {
    viewModel.updateCurrentIndex()
    binding.playGifButton.isEnabled = true
    binding.playWebpButton.isEnabled = true
    images.forEach {
      it.visibility = View.INVISIBLE
    }
  }

  private val animatorSet = AnimatorSet()
  private val animatorListener = object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator) {
      val resource =
        if (viewModel.currentType == ResourceType.GIF) R.drawable.test
        else R.drawable.test_webp
      images[viewModel.currentIndex.value ?: 0].loadBackgroundOnce(resource) {
        val currentIndex = viewModel.currentIndex.value ?: 0
        if (images.size == currentIndex + 1) {
          resetImagesVisible()
        } else {
          viewModel.updateCurrentIndex(currentIndex + 1)
        }
      }
    }

    override fun onAnimationEnd(animator: Animator) {}

    override fun onAnimationCancel(p0: Animator) {}

    override fun onAnimationRepeat(p0: Animator) {}

  }

  private lateinit var scaleX: ObjectAnimator
  private lateinit var scaleY: ObjectAnimator

  private fun initAnimator() {
    with(animatorSet) {
      duration = 1000L
      addListener(animatorListener)
    }
    scaleX = ObjectAnimator.ofFloat(binding.first, "scaleX", 1f, 1.7f, 1f)
    scaleY = ObjectAnimator.ofFloat(binding.first, "scaleY", 1f, 1.7f, 1f)
  }

  private fun runScaleAnimation() {
    val currentView = images[viewModel.currentIndex.value ?: 0]

    currentView.visibility = View.VISIBLE
    scaleX.target = currentView
    scaleY.target = currentView

    animatorSet.cancel()
    animatorSet.play(scaleX).with(scaleY)
    animatorSet.start()
  }

}