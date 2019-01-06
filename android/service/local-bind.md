---
title: "同じプロセス内のサービスへバインドする (Local Bind)"
date: "2011-04-21"
---

同じプロセス内（主に同じアプリ内）のサービスに対してバインドする場合は、`bindService` メソッドを使用して比較的簡単にバインドを実行することができます。
[別プロセスで動作するサービスへのバインド](./remote-bind.html)とは異なり、AIDL を使った `Binder` のスタブ作成などは必要はありません。

- 参考 [Service - Android Developers](http://developer.android.com/reference/android/app/Service.html#LocalServiceSample)


バインド先のサービスを実装する
----

ここでは、`EchoService` というサービスを作成します。
`EchoService` は、文字列を返す簡単な echo API を提供します。

サービスにバインドできるようにするには、サービスの `onBind` メソッドで適切な `IBinder` オブジェクトを返す必要があります。
ローカルプロセス内のバインドでは、特にプロセス間通信などは必要ないので、ここでは、単純に `Binder` クラスを継承したクラス (`LocalBinder`) を作成しています。
クライアント側ではバインドが成功したときに、このオブジェクトを取得することになります。

~~~ java
package com.example.hello;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class EchoService extends Service {
    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public String echo(String message) {
        return "EchoService: " + message;
    }

    /**
     * Class for clients to access.
     * Since we know this service always runs in the same process
     * as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        EchoService getService() {
            return EchoService.this;
        }
    }
}
~~~

独自のサービスを作成したら、忘れずに `AndroidManifest.xml` にエントリを追加しておきます。

~~~ xml
<service android:name=".EchoService" />
~~~


バインド元のクライアントを実装する
----

クライアント側でサービスに対してバインドして、API を参照できるようになるまでの流れは以下のような感じです。

1. バインド先のクラスを指定して `bindService` を実行。
2. バインドされると、`bindService` で指定した `ServiceConnection` オブジェクトの `onServiceConnected` が呼ばれる。
3. `onServiceConnected` メソッドで受け取った `IBinder` オブジェクトから、`Service` オブジェクトを取得。
4. 取得した `Service` オブジェクトの API を利用する。

`bindService` をした場合は、最後に忘れずに `unbindService` するようにします。

~~~ java
package com.example.hello;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EchoService mEchoService = null;
    private boolean mIsBound = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Bind to EchoService.
        Intent intent = new Intent(this, EchoService.class);
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mEchoService = ((EchoService.LocalBinder) binder).getService();

            // あとは echoService の public メソッドを呼び出せば OK.
            String result = mEchoService.echo("Hello");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mEchoService = null;
        }
    };
}
~~~

