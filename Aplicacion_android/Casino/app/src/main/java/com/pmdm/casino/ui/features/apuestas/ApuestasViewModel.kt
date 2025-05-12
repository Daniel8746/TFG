package com.pmdm.casino.ui.features.apuestas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.repositorys.ApuestasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApuestasViewModel @Inject constructor(
    private val apuestasRepository: ApuestasRepository
) : ViewModel() {
    fun finalizarBlackJack() {
        viewModelScope.launch {
            apuestasRepository.finalizarBlackJack()
        }
    }
}