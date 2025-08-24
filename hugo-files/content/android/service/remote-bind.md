---
title: "Androidメモ: 別プロセスのサービスへバインドする (Remote Bind)"
url: "p/qjc27rj/"
date: "2011-04-21"
tags: ["android"]
aliases: ["/android/service/remote-bind.html"]
---

別のプロセスで動いているサービスに対してバインドを実行するには、サービス側とクライアント側でプロセス間通信 (IPC) を行う必要があります。

- 参考: [Service - Android Developers](https://developer.android.com/reference/android/app/Service.html#RemoteMessengerServiceSample)


AIDL で IPC 用スタブを自動生成する
----

プロセス間通信を実現するためのスタブクラスのひな型を自動生成するために、Android では AIDL という言語を使用します。

例えば、`echo` メソッドを提供する `EchoService` にアクセスするための AIDL は以下のようになります。
`EchoService` が公開するインタフェースを表しているので、頭文字に `I` をつけて、`IEchoService` というインタフェース名にしています。
クライアント側から見ると、サービス名は `IEchoService` として見えるようになります。

{{< code lang="java" title="IEchoService.aidl" >}}
package com.example.hello;

interface IEchoService {
    String echo(String message);
}
{{< /code >}}

ADT プラグインのインストールされた Eclipse を使っている場合は、この AIDL ファイルをソースファイルと同じディレクトリ入れると、自動的に Java ファイルが生成されます。

- `src/com/example/hello/IEchoService.aidl`（手書きで作成する AIDL ファイル）
- `gen/com/example/hello/IEchoService.java`（自動生成される Java ファイル）

自動生成される `IEchoService.java` の内容は以下のようになっています。

{{< code lang="java" title="IEchoService.java（自動生成）" >}}
package com.example.hello;

public interface IEchoService extends android.os.IInterface {
    public static abstract class Stub extends android.os.Binder
            implements com.example.hello.IEchoService {
        ...
    }
    ...
}
{{< /code >}}


バインド先のサービスを実装する
----

サービス本体の実装では、`onBind` メソッドで適切な `IBinder` オブジェクトを返す必要があります。
AIDL から自動生成された `IEchoService.java` ファイルで、`IBinder` オブジェクトのひな型となる `Stub` のサブクラスが定義されているので、この `Stub` クラスを実装するだけで、プロセス間通信に対応した `Binder` クラスが完成します。
実装しなければいけないメソッドは、AIDL で定義した公開 API です。

{{< code lang="java" title="EchoService.java" >}}
package com.example.hello;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class EchoService extends Service {
    private final IEchoService.Stub mBinder = new IEchoService.Stub() {
        @Override
        public String echo(String message) throws RemoteException {
            return "EcoService: " + message;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
{{< /code >}}

独自のサービスを作成したら、`AndroidManifest.xml` にエントリを忘れずに追加します。
Remote Bind の場合は、AIDL ファイルから自動生成されたクラス名を `intent-filter` で指定しておく必要があります。
これにより、`IEchoService` のクラス名を使ってクライアント側からバインドすることができるようになります。

~~~ xml
<service android:name=".EchoService">
    <intent-filter>
        <action android:name="com.example.hello.IEchoService" />
    </intent-filter>
</service>
~~~

プロセス間通信に対応したバインドを行えるようにしたので、以下のようにサービスを別プロセスで動かすように指定することもできます。
`EchoService` は別プロセスで動くようになり、プロセス名にサフィックスとして `:remote` が付くようになります。

~~~ xml
<service android:name=".EchoService" android:process=":remote">
    ...
</service>
~~~


バインド元のクライアントを実装する
----

サービスにバインドするクライアント側でも、`IEchoService.aidl` から自動生成された `IEchoService.java` を用意する必要があります。
つまり、サービスの実装側となるアプリと、クライアントになるアプリの両方に `IEchoService.java` が必要です。
もちろん同じアプリ内でサービスとクライアントを実装する場合は、`IEchoService.java` は 1 つだけになります。

サービスにバインドするまでの流れは以下のようになります。
クライアントがアクセスするときは、実際に `Service` クラスを実装している `EchoService` ではなく、`IEchoService` の名前を使ってサービス起動やバインド処理を記述するのがポイントです。

1. `IEchoService` のクラス名を使って `bindService` を実行。
2. バインドされると、`bindService` で指定した `ServiceConnection` オブジェクトの `onServiceConneted` が呼び出される。
3. `onServiceConnected` メソッドで渡される `IBinder` オブジェクトから `IXxxService` オブジェクトを取得する。
4. `IXxxService` オブジェクトを使ってサービスの API を呼び出す。

{{< code lang="java" title="MainActivity.java" >}}
package com.example.hello;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mEchoService = IEchoService.Stub.asInterface(binder);

            // あとは echoService の public メソッドを呼び出せば OK.
            try {
                String result = mEchoService.echo("Hello");
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mEchoService = null;
        }
    };
}
{{< /code >}}

