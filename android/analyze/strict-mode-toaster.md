---
title: "StrictMode の違反メッセージを Toast で表示する (StrictModeToaster)"
date: "2019-10-16"
---

[StrictMode の設定](./strict-mode.html)をしておくと、メインスレッドから I/O アクセスなどを行った場合に、違反 (violation) 通知を受けることができます。

StrictMode の設定時に、`ThreadPolicy.Builder.penaltyLog()` を呼び出しておくと、違反時に LogCat にメッセージを出力してくれるのですが、ここではさらに Toast で violation 名を表示するようにしてみます。
violation 発生時に Toast 表示を行うことで、violation の見逃しを防ぎやすくなります。


StrictModeToaster クラスで violation をトースト表示する
----

下記の **`StrictModeToaster`** クラスを使用すると、StrictMode の violation が発生したときに、violation 名を Toast 表示することができます。
同時に、violation の詳細内容（コールスタック）が LogCat に出力されます。
使い方は、Application クラスなどの先頭で `StrictModeToaster.setup()` を呼び出すだけです。

#### App.kt

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StrictModeToaster.setup(applicationContext)
    }
}
```

#### StrictModeToaster.kt

```kotlin
package com.example.myapp

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.strictmode.Violation
import android.widget.Toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * StrictMode utility to show a violation message as a toast.
 * To enable the StrictMode, just call [setup] at the beginning of the application.
 */
object StrictModeToaster {
    // To show a toast
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private lateinit var appContext: Context

    // To handle violation messages
    private val workerThreadExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }
    private val vmViolationListener by lazy {
        StrictMode.OnVmViolationListener(::showViolationName)
    }
    private val threadViolationListener by lazy {
        StrictMode.OnThreadViolationListener(::showViolationName)
    }

    /**
     * Starts handling strict mode violation messages.
     * This method must be called as soon as possible after launching the app.
     */
    fun setup(context: Context) {
        // Enable the strict mode only when the debuggable flag is true
        if (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE == 0) {
            return
        }

        appContext = context.applicationContext
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyListener(workerThreadExecutor, threadViolationListener)
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyListener(workerThreadExecutor, vmViolationListener)
                .build()
        )
    }

    /**
     * Shows the [violation] name with a Toast.
     */
    private fun showViolationName(violation: Violation) {
        val message: String = violation.javaClass.simpleName
        mainThreadHandler.post {
            Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
        }
    }
```

StrictModeToaster クラスの説明
----

### デバッグビルドの判断

`StrictModeToaster.setup()` を実行すると、DEBUG ビルドのときのみ StrictMode に関する設定が行われるようになっています。
デバッグビルドかのチェックには、よく `BuildConfig.DEBUG` に設定されている Boolean フラグを見たりするのですが、このクラスはビルド時にアプリパッケージ以下に自動生成されるクラスなので、汎用的なライブラリの実装からは参照できません。
ここでは、`ApplicationInfo.flags` を見て、デバッグビルドかどうかの判別を行うようにしています。
`build.gradle` や `AndroidManifest.xml` で余計な設定をしていない限り、デバッグビルド時に FLAG_DEBUGGABLE のビットが 1 になります。

### violation 発生時のハンドル

`ThreadPolicy.Build.penaltyListener()` を使用すると、StrictMode の violation 発生時に呼び出されるコールバック関数を登録することができます。
上記の例では、`threadViolationListener` をコールバック関数として登録しています。
このコールバック関数自体はワーカースレッド (`workerThraderExecutor`) 上で呼び出されるようにしているので、Toast の表示時にはメインスレッド用のハンドラ (`mainThreadHandler`) へ処理を委譲する必要があります。

