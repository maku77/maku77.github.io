---
title: "Emulator での開発時にキーボードによるキー入力が長押しかどうかを判別する"
date: "2020-02-13"
---

Android TV の実機上では、リモコンの決定キー (`KEYCODE_DPAD_ENTER`) を長押しすると、`KeyEvent.isLongPress` が `true` になるので、このフラグを見て長押し時の処理を記述することができます。

一方、Android アプリの開発をエミュレーターで行っているとき、キーボードの Enter キー (`KEYCODE_ENTER`) を、`KEYCODE_DPAD_ENTER` の代わりにしようとしても、**`KeyEvent.isLongPress` が `true` になってくれません**。
なぜなら、キーボードのハードウェア的なキーリピート（正確には OS によるキーリピート）が働いてしまい、長押しではなくキー連打だと判定されてしまうからです。

下記の `LongKeyPressChecker` クラスは、キーボードからのキー入力であっても、そのキーが長押しされているかどうかを判別できるようにするためのユーティリティクラスです。

#### LongKeyPressChecker.kt

```kotlin
import android.view.KeyEvent

/**
 * キーボードによるキー入力が長押しかどうかを判別します。
 */
class LongKeyPressChecker {
    companion object {
        // この時間以内に連続してキー入力があったら、そのキーは押しっぱなしだと判断する
        const val REPEAT_INTERVAL_MILLIS = 200

        // 間髪いれずにこの回数だけキー入力があったら長押しされたと判断する
        const val LONG_PRESSED_COUNT = 3
    }

    // 各キーが最後に押された時間と連打回数
    private val timeAndCount = mutableMapOf<Int, Pair<Long, Int>>()

    /**
     * キーが長押しされたかどうかを判別します。
     * 下記のいずれか関数の中で毎回呼び出す必要があります。
     *   - onKeyDown
     *   - dispatchKeyEvent
     *   - onUnhandledKeyEvent
     */
    fun isLongPressed(event: KeyEvent): Boolean {
        if (event.action != KeyEvent.ACTION_DOWN) {
            return false
        }

        // KeyEvent.repeatCount 対応のキーでは KeyEvent.isLongPress を信用する
        if (event.repeatCount > 0) {
            return event.isLongPress
        }

        val key: Int = event.keyCode
        val time: Long = event.downTime
        val timeAndCount = timeAndCount.getOrDefault(key, Pair(time, 0))

        if ((time - timeAndCount.first) < REPEAT_INTERVAL_MILLIS) {
            // 間髪入れずにキー入力があればカウントアップ
            this.timeAndCount[key] = Pair(time, timeAndCount.second + 1)

            if (timeAndCount.second == LONG_PRESSED_COUNT) {
                // 短時間でのキー入力が一定回数に達したら長押しされたと判断
                return true
            }
        } else {
            // 前回のキープレスから一定時間が経過していたらそこからカウント開始
            this.timeAndCount[key] = Pair(time, 1)
        }
        return false
    }
}
```

使い方は簡単で、`View.onKeyDown` などの先頭で `isLongPressed` メソッドを呼び出すだけです。
次のようにすれば、キーボードの Enter キー (`KEYCODE_ENTER`) が長押しされたかどうかを判別できます。

#### MainActivity.kt

```kotlin
class MainActivity : FragmentActivity(R.layout.main_activity) {

    private val longKeyPressChecker = LongKeyPressChecker()

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val isLongPressed = longKeyPressChecker.isLongPressed(event)
        when (event.keyCode) {
            KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> {
                if (isLongPressed) {
                    Log.d("MainActivity", "決定キーが長押しされたよ！")
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    // ...
}
```

仕組みとしては、前回のキー入力から時間をあけずに（上記の例では `REPEAT_INTERVAL_MILLIS` ミリ秒以内）に、同じキーが何回も（上記の例では `LONG_PRESSED_COUNT` 回）押されたかを判別しているだけです。

なので、短時間にキー連打すると、長押しと判定されてしまう可能性があります。
また、Windows などの OS のキーリピート設定により、キー押しっぱなし時のカウント速度が変わってくることに注意してください。

