package com.nyang.dayFlower.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Job

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    /*val applicationContext = getApplication<Application>().applicationContext*/
    var job : Job? = null
}