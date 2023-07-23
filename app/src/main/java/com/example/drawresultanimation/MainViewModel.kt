package com.example.drawresultanimation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

  private val defaultIndex = 0

  private val _currentIndex = MutableLiveData<Int>()
  val currentIndex: LiveData<Int> = _currentIndex

  fun updateCurrentIndex(index: Int = defaultIndex) {
    _currentIndex.value = index
  }

  var currentType: ResourceType = ResourceType.GIF
}