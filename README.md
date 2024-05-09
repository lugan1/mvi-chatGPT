# ChatGPT Compose (Kotlin + Jetpack Compose + Navigation + MVI)

![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/0426cb9b-0ce9-4486-8100-ec329b78fcc0" width="200" height="400"/>
<img src="https://github.com/lugan1/mvi-chatGPT/assets/39528583/f8b2311f-e66d-46b7-856d-1f1057f04174" width="200" height="400"/>
![KakaoTalk_20240507_054320116](https://github.com/lugan1/mvi-chatGPT/assets/39528583/d45b9607-dc9b-4b11-b76c-5adb2dcd0c1c)
![KakaoTalk_20240507_054320116_01](https://github.com/lugan1/mvi-chatGPT/assets/39528583/ac7448a2-ca97-4e74-8920-165e5a644400)

<br/>
<br/>
  
ChatGPT Compose 앱은 MVI(Model-View-Intent)를 적용해 MVVM 패턴과의 어떤점이 다른지 비교를 해볼 수 있습니다.

<br/>
  
## Description

* UI
    * [Compose](https://developer.android.com/jetpack/compose) 선언적 UI 프레임워크
    * [Material design](https://material.io/design)

* Tech/Tools
    * [Kotlin](https://kotlinlang.org/) 100% 사용
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) 비동기 작업
    * [Hilt]([https://insert-koin.io/](https://developer.android.com/codelabs/android-hilt?hl=ko)) 의존성 주입
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) composable 사이에 화면이동
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) UI state 를 관리 및 변경 (reduce)
    * [OpenAI API]([https://square.github.io/retrofit/](https://github.com/Aallam/openai-kotlin)) kotlin + OpenAI API

* Modern Architecture
    * Single activity architecture (with [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started))
    * MVI
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions
    
## Architecture
프로젝트의 구조는 뷰(View), 프리젠테이션(Presentation), 모델(Model) 으로 나뉘어져 있으며 Model-View-Intent (MVI) 패턴을 구현합니다.
<br/>

![1_xprZjYydI9YhVKC3CKp-dQ (1)](https://github.com/lugan1/mvi-chatGPT/assets/39528583/d44304b7-3e6f-4e0b-acf5-e4679785bd6e)

Architecture layers:
* 뷰(View) - 상태(State) 와 바인딩되고, Effect를 를 감지하고, 이벤트를 위임하는 컴포즈 화면.
* 뷰모델(ViewModel) - 해당 화면의 상태를 관리하고 사용자의 Intent(Event)를 수신하면 UI상태를 변경(Reduce)합니다. 추가로, UI 이벤트를 가로채고 Side Effect를 발생시킵니다. 뷰모델의 수명 주기는 해당 화면 컴포저블과 연결되어 있습니다.
* 모델(Model) - 변경될 수 있는 데이터들을 의미합니다. 모델이 변경되면 컴포즈는 리컴포지션(리렌더링) 을 수행해 UI 를 다시그려야 합니다. 

![](https://i.imgur.com/UXwFbmv.png)


다음은 설명된 세 가지 핵심 구성 요소입니다:

* **상태(State)** - 해당 화면의 상태 내용을 저장하는 데이터 클래스입니다. 예를 들어 User의 목록, 로딩 상태 등이 있습니다. 상태는 초기 값으로 연속 업데이트를 받는 사용 사례에 완벽하게 맞는 Compose 런타임 MutableState 객체로 노출됩니다.
* **이벤트(Event)** - UI에서 프리젠테이션 계층으로 콜백을 통해 전송되는 일반 객체입니다. 이벤트는 사용자에 의해 발생한 UI 이벤트를 반영해야 합니다. 이벤트 업데이트는 MutableSharedFlow 유형으로 노출되며, 이는 StateFlow와 유사하고 구독자가 없는 경우 게시된 이벤트가 즉시 삭제된다는 방식으로 작동합니다.
* **효과(Effect)** - 한 번의 부수 효과(Side Effect) 동작을 알리는 일반 객체로, UI에 영향을 줘야 합니다. 예를 들어, 네비게이션 동작을 시작하거나, 토스트, 스낵바 등을 표시합니다. 효과는 ChannelFlow로 노출되며, 각 이벤트가 단일 구독자에게 전달되는 방식으로 동작합니다. 구독자 없이 이벤트를 방출하려고 하면 채널 버퍼가 가득 차는 즉시 일시 중지되어 구독자가 나타날 때까지 대기합니다.
각 화면/플로우(coroutine flow)는 상태 내용, 이벤트 및 효과에 대해 설명된 위의 핵심 구성 요소를 모두 명시하는 Contract 클래스를 정의해 사용합니다.


# Reference
MVI : https://blog.stackademic.com/mvi-architecture-explained-on-android-e36ee66bceaa
