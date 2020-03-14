---
title: "Choreographer で FPS を計測する（Fps クラスの実装）"
date: "2019-09-18"
lastmod: "2020-03-04"
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


（応用） 1 秒おきに平均 FPS を求める
----

上記のサンプルコードでは、毎フレーム FPS を求めてコールバック関数を呼び出していましたが、これでは高頻度すぎるという場合は、下記のようにすれば約 1 秒ごとに呼び出すように軽量化できます。
`FrameCallback.doFrame()` が呼び出されたときに、前回のコールバックから 1 秒以上経過している場合だけ、コールバックするようにしています。
ここでは、**約 1 秒間のフレーム数を数えて平均 FPS を求めています**。

```kotlin
import android.view.Choreographer
import java.util.concurrent.TimeUnit

/**
 * Utility class for obtaining FPS (frames per second).
 */
class Fps : Choreographer.FrameCallback {
    companion object {
        /** Callbacks are invoked at intervals of this time. */
        private val CALLBACK_INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(1)
    }

    interface FpsCallback {
        /** Called when the latest FPS is calculated. */
        fun onFpsUpdated(fps: Double)
    }

    private val choreographer = Choreographer.getInstance()
    private var fpsCallback: FpsCallback? = null
    private var prevCallbackTimeNanos: Long = 0

    /** How many times doFrame is called since the last onFpsUpdated. */
    private var frames = 0

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: FpsCallback) {
        this.fpsCallback = fpsCallback
        prevCallbackTimeNanos = 0
        frames = 0

        // Add a frame callback but prevents duplicate registration
        choreographer.removeFrameCallback(this)
        choreographer.postFrameCallback(this)
    }

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: (Double) -> Unit) {
        startObserving(object : FpsCallback {
            override fun onFpsUpdated(fps: Double) = fpsCallback(fps)
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
        if (prevCallbackTimeNanos == 0L) {
            prevCallbackTimeNanos = frameTimeNanos
            return
        }

        frames++

        // Callback at the intervals of CALLBACK_INTERVAL_NANOS
        val elapsed = frameTimeNanos - prevCallbackTimeNanos
        if (elapsed >= CALLBACK_INTERVAL_NANOS) {
            // Calculate FPS and pass it to the callback function
            val fps = frames.toDouble() * TimeUnit.SECONDS.toNanos(1) / elapsed
            checkNotNull(fpsCallback).onFpsUpdated(fps)

            // Reset counters
            prevCallbackTimeNanos = frameTimeNanos
            frames = 0
        }
    }
}
```


（応用） 1 秒おきに最低 FPS を求める
----

上記の例では、1 秒間の描画フレーム数を数えることで秒間平均 FPS を求めていましたが、その方法だと、あるフレームが非常に遅くても、残りのフレームが高速であれば、FPS としては比較的高い値が出てしまいます。

ここでは、もっとストイックに、過去 1 秒間で最も時間のかかったフレームの描画時間をもとに、FPS 計算するようにしてみます。
つまり、 **秒間最低 FPS（秒間最悪 FPS）** です。

```
秒間最低FPS = 1秒あたりのns / 最も時間のかかったフレームの描画時間(ns)
```

```kotlin
import android.view.Choreographer
import java.lang.Long.max
import java.util.concurrent.TimeUnit

/**
 * Utility class for obtaining FPS (frames per second).
 */
class Fps : Choreographer.FrameCallback {
    companion object {
        /** Callbacks are invoked at intervals of this time. */
        private val CALLBACK_INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(1)
    }

    interface FpsCallback {
        /** Called when the latest FPS is calculated. */
        fun onFpsUpdated(fps: Double)
    }

    private val choreographer = Choreographer.getInstance()
    private var fpsCallback: FpsCallback? = null
    private var prevCallbackTimeNanos: Long = 0
    private var prevFrameTimeNanos: Long = 0

    /** Maximum frame time over the last one minute. */
    private var worstFrameTimeNanos: Long = 1

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: FpsCallback) {
        this.fpsCallback = fpsCallback
        prevCallbackTimeNanos = 0

        // Add a frame callback but prevents duplicate registration
        choreographer.removeFrameCallback(this)
        choreographer.postFrameCallback(this)
    }

    /**
     * Starts observing the FPS.
     * [fpsCallback] is invoked continuously after calling this method.
     */
    fun startObserving(fpsCallback: (Double) -> Unit) {
        startObserving(object : FpsCallback {
            override fun onFpsUpdated(fps: Double) = fpsCallback(fps)
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
        if (prevCallbackTimeNanos == 0L) {
            prevCallbackTimeNanos = frameTimeNanos
            prevFrameTimeNanos = frameTimeNanos
            return
        }

        worstFrameTimeNanos = max(worstFrameTimeNanos, frameTimeNanos - prevFrameTimeNanos)

        // Callback at the intervals of CALLBACK_INTERVAL_NANOS
        val elapsed = frameTimeNanos - prevCallbackTimeNanos
        if (elapsed >= CALLBACK_INTERVAL_NANOS) {
            // Calculate the minimum FPS and pass it to the callback function
            val fps = TimeUnit.SECONDS.toNanos(1).toDouble() / worstFrameTimeNanos
            checkNotNull(fpsCallback).onFpsUpdated(fps)

            // Reset counters
            prevCallbackTimeNanos = frameTimeNanos
            worstFrameTimeNanos = 1
        }

        prevFrameTimeNanos = frameTimeNanos
    }
}
```

