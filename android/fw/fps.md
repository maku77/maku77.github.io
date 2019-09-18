---
title: "Choreographer で FPS を計測する（Fps クラスの実装）"
date: "2019-09-18"
---

Choreographer クラスによる FPS 計測
----

Android の [Choreographer](https://developer.android.com/reference/kotlin/android/view/Choreographer) クラスを使用すると、フレームの描画開始のタイミングで呼び出されるコールバック ([Choreographer.FrameCallback](https://developer.android.com/reference/kotlin/android/view/Choreographer.FrameCallback.html)) を登録することができます。

```
Choreographer#postFrameCallback(callback: Choreographer.FrameCallback!)
```

上記のメソッドでコールバックを登録すると、次のフレーム描画のタイミングで `doFrame(long frameTimeNanos)` メソッドが呼び出されます。

```
Choreographer.FrameCallback#doFrame(frameTimeNanos: Long)
```

パラメータとして描画開始時刻（ナノ秒単位）が渡されるため、前回のコールバック時の描画開始時刻からの差分を取れば、1 フレームの描画にかかった時間を求めることができます。
この値を使えば、FPS (Frame per second) は下記のように計算できます。

```
FPS = 1秒あたりのナノ秒 / 描画にかかった時間（ナノ秒）
```


サンプルコード
----

### Fps クラスの実装

下記の `Fps` クラスは、FPS を簡単に計測するためのクラスです。
内部で Android の `Choreographer` を使っています。

```kotlin
import android.view.Choreographer
import java.util.concurrent.TimeUnit

class Fps : Choreographer.FrameCallback {
    interface FpsCallback {
        /** Called when the latest FPS is calculated. */
        fun onFpsUpdated(fps: Float)
    }

    private val choreographer = Choreographer.getInstance()
    private var fpsCallback: FpsCallback? = null
    private var prevFrameTimeNanos: Long = 0

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: FpsCallback) {
        this.fpsCallback = fpsCallback
        prevFrameTimeNanos = 0
        choreographer.postFrameCallback(this)
    }

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: (Float) -> Unit) {
        startObserving(object : FpsCallback {
            override fun onFpsUpdated(fps: Float) = fpsCallback(fps)
        })
    }

    /**
     * Stops observing the FPS.
     */
    fun stopObserving() {
        choreographer.removeFrameCallback(this)
    }

    /**
     * Implementation for [Choreographer.FrameCallback].
     */
    override fun doFrame(frameTimeNanos: Long) {
        // Register the same callback again to be called continuously
        choreographer.postFrameCallback(this)

        // At first, just store the frame time for later calculation
        if (prevFrameTimeNanos == 0L) {
            prevFrameTimeNanos = frameTimeNanos
            return
        }

        // Calculate FPS and pass it to the callback function
        val elapsed = frameTimeNanos - prevFrameTimeNanos
        val fps = TimeUnit.SECONDS.toNanos(1) / elapsed.toFloat()
        checkNotNull(fpsCallback).onFpsUpdated(fps)

        prevFrameTimeNanos = frameTimeNanos
    }
}
```

`Choreographer#postFrameCallback()` で登録したコールバックは、一度しか呼び出されないことに注意してください。
連続してフレーム描画のタイミングを取得するには、毎回同じコールバックを登録し直す必要があります（上記では、`doFrame()` メソッドの先頭で登録しています）。

### Fps クラスの使用例

`Fps` クラスを使って FPS の観測を行うには、例えば次のようにします。

```kotlin
Fps().startObserving { fps ->
    Log.i("DEBUG", "FPS = %.3f".format(fps))
}
```

