---
title: ネイティブサービスの実装 (2) サービスの実装から利用まで
created: 2011-04-27
layout: android
---

サービスの実装
====

サービスとなるクラスは `IBinder` で定義されたインタフェースを実装する必要があります。
`IBinder` を直接実装しようとすると、多くの abstract メソッドを実装しないといけませんが、基本的な実装を提供している `BBinder` クラスを継承してサービスを作成すれば、最低限のインタフェースを実装するだけで済みます。

一番重要なメソッドは、サービスの機能を提供する `onTransact()` メソッドです。
例えば、計算を行うためのサービス `CalcService` を考えます。

#### CalcService.h
```cpp
#include <binder/Binder.h>

class CalcService : public android::BBinder {
public:
    virtual status_t onTransact(
            uint32_t code,                // 処理内容分岐のための ID
            const android::Parcel& data,  // 入力値
            android::Parcel* reply,       // 戻り値
            uint32_t flags                // 戻り値が必要かどうかなどを示すフラグ
    );
    virtual ~CalcService() {}
};
```

クライアントからの入力値は `data` パラメータで受け取り、クライアントへの戻り値は `reply` パラメータに格納します。
この `CalcService` に複数の機能（足し算、引き算など）を持たせたい場合は、`onTransact()` メソッドの第一引数で渡される `code`（int 値）をもとに処理を分岐させます。

```cpp
status_t CalcService::onTransact(uint32_t code, const Parcel& data,
        Parcel* reply, uint32_t flags) {
    switch (code) {
    case 0:  // Add function
        {
        int a = data.readInt32();
        int b = data.readInt32();
        reply->writeInt32(a + b);
        }
        break;
    case 1:  // Subtract function
        {
        int a = data.readInt32();
        int b = data.readInt32();
        reply->writeInt32(a - b);
        }
        break;
    default:
        return BBinder::onTransact(code, data, reply, flags);
    }
    return NO_ERROR;
}
```

正確には `IBinder::transact()` メソッドがサービスクラスの実装すべき API なのですが、`BBinder` クラスの `IBinder::transact()` の実装の内部で `onTransact()` を呼び出すようになっているので、`BBinder` を継承してサービスを実装する場合は、`onTransact()` メソッドを実装すればよいことになります。


サービスの起動
====

サービスクラスを作成したら、実際にこのクラスのインスタンスを生成するための `main` 関数を持つ実行ファイルを作成します。
この実行ファイル内で、サービスのインスタンスに名前を付けて `ServiceManager` に登録することで、他のプロセスからサービスインスタンスを参照できるようになります。

```cpp
int main() {
    sp<ProcessState> proc(ProcessState::self());
    sp<IServiceManager> sm = defaultServiceManager();
    sm->addService(String16("CalcService"), new CalcService());
    ProcessState::self()->startThreadPool();
    IPCThreadState::self()->joinThreadPool();
}
```

`ServiceManager` の内部まで追っていくと、最終的に binder driver を介してプロセス間通信を行っています。


サービスを利用するクライアントの実装
====

他のプロセスからサービスを利用するには、`ServiceManager` から名前で `IBinder` オブジェクトを取得します。
ここで `ServiceManager` が返す `IBinder` オブジェクトは、プロセス間通信を実現するためのプロキシクラスである `BpBinder` オブジェクトです。
もちろん、サービスインスタンスが `ServiceManager` に登録されるまでは `IBinder` オブジェクトの取得には失敗します。

一度 `IBinder` オブジェクトを取得できてしまえば、あとは `transact` メソッドを呼び出してサービスの機能にアクセスできます。

```cpp
int main() {
    sp<IServiceManager> sm = defaultServiceManager();
    binder = sm->getService(String16("CalcService"));
    if (binder == 0) {
        LOGW("CalcService has not been published yet...");
        return 0;
    }

    Parcel data;   // Input
    Parcel reply;  // Output
    data.writeInt32(100);
    data.writeInt32(50);
    binder->transact(0, data, &reply);  // Add function
    LEGI("CalcService returns: %d", reply.readInt32());
}
```

ここまでの例では、Android の提供している `IBinder::transact()` インタフェースだけを使用してプロセス間通信を行っているので、サービス側と、クライアント側で共通のヘッダファイルを用意することなく通信が行えています。


完全なコードと実行テスト
====

- [CalcService.zip](files/20110509-CalcService.zip)
- [CalcClient.zip](files/20110509-CalcClient.zip)

実行ファイルをデバイスに転送したら、以下のように CalcService サービスを起動できます。

```
# CalcService &
04-28 17:26:57.480  3178  3178 I CalcService: Add CalcService to ServiceManager
```

サービスを起動している状態で CalcClient プログラムを起動すると、サービスにアクセスできていることが分かります。

```
# CalcClient
04-28 17:27:33.236  3196  3196 I CalcClient: CalcService returns: 150
```

