package com.example.tmdb.ui.screens.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.MovieRepository
import com.example.tmdb.model.BasicMovie
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PersonDetailViewModel.PersonDetailViewModelFactory::class)
class PersonDetailViewModel @AssistedInject constructor(
    @Assisted private val personId: Long,
    private val movieRepository: MovieRepository
) : ViewModel() {

    @AssistedFactory
    interface PersonDetailViewModelFactory {
        fun create(personId: Long): PersonDetailViewModel
    }

    private val _uiState: MutableStateFlow<PersonDetailUiState> = MutableStateFlow(
        PersonDetailUiState.Loading
    )
    val uiState: StateFlow<PersonDetailUiState> = _uiState.asStateFlow()

    init {
        getPersonMovieCredits(personId)
    }

    private fun getPersonMovieCredits(personId: Long) {
        viewModelScope.launch {
            _uiState.value = PersonDetailUiState.Loading
            movieRepository.getPersonMovieCredits(personId)

                .onSuccess {
                    _uiState.value = PersonDetailUiState.Success(it)
                }
                .onFailure {
                    _uiState.value = PersonDetailUiState.Error(it)
                }
        }
    }


    sealed interface PersonDetailUiState {
        data object Loading : PersonDetailUiState
        data class Success(val movies: List<BasicMovie>) : PersonDetailUiState
        data class Error(val exception: Throwable) : PersonDetailUiState
    }
}