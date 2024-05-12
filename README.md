# ChatGPT Compose (Jetpack Compose + MVI)

![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/0426cb9b-0ce9-4486-8100-ec329b78fcc0" width="200" height="400"/>
<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/f8b2311f-e66d-46b7-856d-1f1057f04174" width="200" height="400"/>
<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/d45b9607-dc9b-4b11-b76c-5adb2dcd0c1c" width="200" height="400"/>
<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/ac7448a2-ca97-4e74-8920-165e5a644400" width="200" height="400"/>

<br/>
<br/>
  
## Tech Stack

* UI
    * [Compose](https://developer.android.com/jetpack/compose)
    * [Material design](https://material.io/design)


* Dependency
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) , [Flow](https://developer.android.com/kotlin/flow)
    * [Hilt]([https://insert-koin.io/](https://developer.android.com/codelabs/android-hilt?hl=ko))
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [OpenAI API](https://github.com/Aallam/openai-kotlin)


* Modern Architecture
    * Single activity architecture
    * MVI
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions
    
## Architecture
뷰(View), 프리젠테이션(Presentation), 모델(Model) 으로 나뉘어져 있으며 Model-View-Intent (MVI) 패턴을 사용합니다.
<br/>

![1_xprZjYydI9YhVKC3CKp-dQ (1)](https://github.com/lugan1/mvi-chatGPT/assets/39528583/d44304b7-3e6f-4e0b-acf5-e4679785bd6e)

Architecture layers:
* **뷰(View)** - 상태(State) 와 바인딩되고, Effect를 를 감지하고, 이벤트를 위임하는 컴포즈 화면.
* **뷰모델(ViewModel)** - 해당 화면의 상태를 관리하고 사용자의 Intent(Event)를 수신하면 UI상태를 변경(Reduce)합니다. 추가로, UI 이벤트를 가로채고 Side Effect를 발생시킵니다. 뷰모델의 수명 주기는 해당 화면 컴포저블과 연결되어 있습니다.
* **모델(Model)** - 변경될 수 있는 데이터들을 의미합니다. 모델이 변경되면 컴포즈는 리컴포지션(리렌더링) 을 수행해 UI 를 다시그려야 합니다. 

![](https://i.imgur.com/UXwFbmv.png)

MVI 패턴을 따르는 모든 뷰모델은 다음과 같이 MVIViewModel 추상 클래스를 상속받도록 구현했습니다.
```kotlin
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
```

화면에서 발생할 수 있는 Event, State, SideEffect 를 미리 정의합니다.
```kotlin
class ChatContract {
    sealed class Event: ViewEvent {
        data class InputChange(val message: String): Event()
        data object Send: Event()
        data object RequestPermission: Event()
        data object DismissBottomSheet: Event()
        data class DismissPermission(val isGranted: Boolean): Event()
    }

    data class State(
        val inputMessage: String = "",
        val isLoading: Boolean = false,
        val isShowPermission: Boolean = false,
        val isRecording: Boolean = false,
        val recordState: RecordUiState = RecordUiState(),
        val messageList: List<UiChatMessage> = emptyList(),
    ): ViewState


    sealed class Effect: ViewSideEffect {
        data object StartRecording: Effect()
        data object StopRecording: Effect()
        data class ShowSnackBar(val message: String? = null, val messageRes: Int? = null): Effect()
    }
}
```





# MVI Reference
MVI : https://blog.stackademic.com/mvi-architecture-explained-on-android-e36ee66bceaa  
GitHub MVI : https://github.com/myofficework000/MVI-JetpackCompose-Github
