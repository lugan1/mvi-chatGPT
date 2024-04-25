package com.example.mvi_chatgpt.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface ViewEvent

interface ViewState

interface ViewSideEffect

abstract class MVIViewModel<Event: ViewEvent, UiState: ViewState, Effect: ViewSideEffect> : ViewModel() {

    // UI의 초기 상태를 설정
    abstract fun setInitialState(): UiState

    // 사용자의 입력을 처리
    abstract fun handleEvents(event: Event)

    // 초기 상태를 지연 초기화
    private val initialState: UiState by lazy { setInitialState() }

    // 현재 UI 의 상태를 mutableState 로 변경감지
    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: MutableState<UiState> = _viewState

    // 사용자의 입력을 받는 SharedFlow
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    // SideEffect 를 받는 Channel
    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: MutableSharedFlow<Effect> = _effect

    init {
        subscribeToEvents()
    }

    // 사용자의 입력을 구독해서 감지
    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { event -> handleEvents(event) }
        }
    }

    // 사용자의 입력을 설정하는 메서드
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    // 현재 UI 의 상태를 변경하는 메서드
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = _viewState.value.reducer()
        _viewState.value = newState
    }

    // Side Effect 를 설정하는 메서드
    // Intent -> Model -> View 의 사이클을 벗어난 비동기 작업이 완료된 후 UI 상태 변경 외의 작업을 수행할 때 사용
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.emit(effectValue) }
    }
}