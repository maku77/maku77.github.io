---
title: "サービスからコールバックできるようにする"
date: "2011-04-25"
---

ここでは、別プロセスで動いているサービスにバインドし、そのサービスに対してコールバックを登録できるようにする方法を説明します。
サービスからのコールバックの仕組みを実現するには、以下のような実装が必要です

- サービス側にコールバックを登録する実装
- サービス側からコールバックする実装
- コールバックを登録するクライアント側の実装
- コールバックされたときのクライアント側の実装


AIDL ファイルを作成する
----

２つの AIDL ファイルで以下のインタフェースを定義します。

- `IEchoServiceListener.aidl`（コールバック用のインタフェース）
- `IEchoService.aidl`（サービスのコールバック登録用 API）

AIDL ファイルから生成されるインタフェース（Java ファイル）は、クライアント側とサービス側の両方で共通のものを使用します。

#### IEchoServiceListener.aidl

~~~ java
package com.example.hello;

interface IEchoServiceListener {
    void onReceiveMessage(String message);
}
~~~

#### IEchoService.aidl

~~~ java
package com.example.hello;

import com.example.hello.IEchoServiceListener;

interface IEchoService {
    void addListener(IEchoServiceListener listener);
    void removeListener(IEchoServiceListener listener);
    void hello(String name);
}
~~~

`IEchoServiceListener` は同じパッケージに定義されていますが、AIDL ファイル内では import 宣言しないとエラーになるようです。


サービス側を実装する
----

サービス側で、コールバックを登録／解除するためのメソッドを実装します。
ここでは簡単のため、クライアント側から `hello()` メソッドを呼び出したときに、すぐにコールバックするように実装しています。

#### EchoService.java

~~~ java
package com.example.hello;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class EchoService extends Service {
    private RemoteCallbackList<IEchoServiceListener> listeners =
            new RemoteCallbackList<IEchoServiceListener>();

    private final IEchoService.Stub mBinder = new IEchoService.Stub() {
        @Override
        public void addListener(IEchoServiceListener listener) throws RemoteException {
            listeners.register(listener);
        }

        @Override
        public void removeListener(IEchoServiceListener listener) throws RemoteException {
            listeners.unregister(listener);
        }

        @Override
        public void hello(String name) throws RemoteException {
            int n = listeners.beginBroadcast();
            try {
                listeners.getBroadcastItem(i).onReceiveMessage("Hello, " + name);
            } catch (RemoteException e) {
                // The RemoteCallbackList will take care of removing
                // the dead object for us. We can ignore this exception.
            }
            listeners.finishBroadcast();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
~~~

コールバックを管理するためのユーティリティクラスとして、`android.os.RemoteCallbackList` が用意されています。

サービス側から実際にコールバック処理を行う場合は、`RemoteCallbackList` クラスの、次の３つのメソッドを利用します。

1. `beginBroadcast()` でコールバック区間の開始。
2. `getBroadcastItem(index)` でコールバック用のオブジェクトを取得し、コールバック実行。
3. `finishBroadcast()` でコールバック区間の終了。


クライアント側を実装する
----

クライアント側では、以下の実装を行います。

- サービスへのバインド処理。
- サービスへコールバックを登録／解除する処理。
- コールバック時の処理内容（`IEchoServiceListener.Stub` の実装）。

ここでは、サービスに対してコールバックを登録した直後に `hello()` メソッドを呼び出し、サービス側からの `onReceiveMessage()` を発火させています。

#### MainActivity.java

~~~ java
package com.example.hello;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends Activity {
    private IEchoService mEchoService = null;
    private boolean mIsBound = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Bind to EchoService.
        Intent intent = new Intent(IEchoService.class.getName());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(connection);
            mIsBound = false;
        }
    }

    // コールバックされたときの処理
    private IEchoServiceListener listener = new IEchoServiceListener.Stub() {
        @Override
        public void onReceiveMessage(String message) throws RemoteException {
            Log.i("Test", message);
        }
    };

    // bind 時の処理
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mEchoService = IEchoService.Stub.asInterface(binder);
            try {
                mEchoService.addListener(listener);
                mEchoService.hello("Joe");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mEchoService.removeListener(listener);
            mEchoService = null;
        }
    };
}
~~~

