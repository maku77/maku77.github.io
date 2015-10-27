---
title: ネイティブサービスの実装 (3) サービスのインタフェースを定義する
created: 2011-05-09
---

クライアント側のコードをすっきりさせたい
====

`IBinder` クラスの `transact()` インタフェースだけでサービスの機能にアクセスするのは分かりにくく、すべてのクライアントコードに本質的ではない `Parcel` オブジェクトの生成コードが表れてしまいます。

#### 例: CalcService を利用する煩雑なクライアントコード

```cpp
sp<IServiceManager> sm = defaultServiceManager();
sp<IBinder> binder = sm->getService(String16("CalcService"));

Parcel data;   // Input
Parcel reply;  // Output
data.writeInt32(100);
data.writeInt32(50);
binder->transact(0, data, &reply);  // Add function
LEGI("CalcService returns: %d", reply.readInt32());
```

サービスのインタフェースを定義し、そのインタフェース経由で機能にアクセスできるようにすればクライアント側のコードがすっきりします。
Binder フレームワークには、このためのヘルパークラスなどが用意されていて、これらをうまく使用すると、以下のような感じで `CalcService` にアクセスできるようになります。

```cpp
sp<IServiceManager> sm = defaultServiceManager();
sp<IBinder> binder = sm->getService(String16("CalcService"));
sp<ICalcService> calc = interface_cast<ICalcService>(binder);

int result = calc.add(100, 50);  // すっきり！
```

まぁ、簡単に言ってしまえば、`IBinder` を直接使うのではなく、分かりやすいラッパークラスを用意してやりましょうということ。
`interface_cast` というテンプレート関数は、`IInterface.h` で定義されていて、上記のサンプルコードは以下のように展開されます。

```cpp
sp<ICalcService> calc = interface_cast<ICalcService>(binder);
↓
sp<ICalcService> calc = ICalcService::asInterface(binder);
```

要するに、`ICalcService` には、`IBinder` オブジェクトから `ICalcService` オブジェクトを取得するための `asInterface()` メソッドが定義されていないといけません。
最終的にクライアントに公開されるファイルは、サービスインタフェースである `ICalcService.h` などのヘッダファイルと、その実装である `ICalcService.so` などになるでしょう。


クライアントに公開するサービスインタフェースの実装
====

サービスのインタフェースは、`IInterface` を継承して作成します。
例えば、サービスクラス `CalcService` にアクセスするためのインタフェースは、`ICalcService` という名前で作成し、ここに `add()` や `subtract()` などの分かりやすいアクセスメソッドを用意してやります。
`IInterface.h` に定義されているマクロが、プレフィックスとして `I` を付ける前提のコードになっているので、このように「`I` + サービス名」というネーミングをしなければいけません。

#### ICalcService.h

```cpp
class ICalcService : public IInterface {
public:
    DECLARE_META_INTERFACE(CalcService);
    virtual int add(int a, int b) = 0;
    virtual int subtract(int a, int b) = 0;
};
```

`DECLARE_META_INTERFACE` というマクロは以下のように展開されます。
この中で、`IBinder` オブジェクトから `ICalcService` オブジェクトを取得するための `asInterface()` も定義されます。

#### ICalcService.h

```cpp
static const android::String16 descriptor;
static android::sp<ICalcService> asInterface(const android::sp<android::IBinder>& obj);
virtual const android::String16& getInterfaceDescriptor() const;
ICalcService();
virtual ~ICalcService();
```

これらの実装を提供するための、`IMPLEMENT_META_INTERFACE` というマクロも用意されています。
例えば、

```cpp
IMPLEMENT_META_INTERFACE(CalcService, "sample.CalcService");
```

と記述しておくと、以下のように展開されます。

#### ICalcService.cpp

```cpp
const android::String16 ICalcService::descriptor("sample.CalcService");

const android::String16& ICalcService::getInterfaceDescriptor() const {
    return ICalcService::descriptor;
}

android::sp<ICalcService> ICalcService::asInterface(const android::sp<android::IBinder>& obj) {
    android::sp<ICalcService> intr;
    if (obj != NULL) {
        intr = static_cast<ICalcService*>(obj->queryLocalInterface(ICalcService::descriptor).get());
        if (intr == NULL) {
            intr = new BpCalcService(obj);
        }
    }
    return intr;
}

ICalcService::ICalcService() {};
ICalcService::~ICalcService() {};
```

この中で、`asInterface()` で取得するオブジェクトとして、`BpCalcService` というオブジェクトを生成するように実装されます。
つまり、`ICalcService` というインタフェースを実装する `BpCalcService` クラスを作成する必要があります。

```cpp
class BpCalcService : public android::BpInterface<ICalcService) {
public:
    BpCalcService(const android::sp<android::IBinder>& obj) {};
    virtual ~BpCalcService();
    virtual int add(int a, int b);
    virtual int subtract(int a, int b);
};
```

`add()` や `subtract()` メソッドの実装で `IBinder` オブジェクトへの参照が必要になるのですが、実は親クラスの `BpInterface` クラスのコンストラクタに `IBinder` オブジェクトを渡しておけば、いつでも `remote()` メソッドを使って参照できるようになります。
つまり、`BpCalcService` の実装で、`IBinder` オブジェクトを保持しておく必要はありません。

```cpp
BpCalcService::BpCalcService(const sp<IBinder>& obj) : BpInterface(ICalcService>(obj) {}

BpCalcService::~BpCalcService() {}

int BpCalcService::add(int a, int b) {
    Parcel data, reply;
    data.writeInt32(a);
    data.writeInt32(b);
    remote()->transact(CODE_ADD, data, &reply);
    return reply.readInt32();
}
...
```

`transact` に渡す `code` は、次のように `IBinder::FIRST_CALL_TRANSACTION` (0) から割り当てていきます。
この数値は、もちろんサービス側の実装に合わせる必要があります。

```cpp
enum {
    CODE_ADD = IBinder::FIRST_CALL_TRANSACTION,
    CODE_SUBTRACT,
};
```

`ICalcService.h` をサービス側、クライアント側の両方から見える include ディレクトリに配置するのなら、`ICalcService.h` のクラス定義の中で protected に上記の enum 定義を入れてしまうのがよいかもしれません。


クライアントの実装
====

```cpp
int main() {
    sp<IServiceManager> sm = defaultServiceManager();
    sp<IBinder> binder = sm->getService(String16("CalcService"));
    if (binder == 0) {
        LOGW("CalcService has not been published yet...");
        return 0;
    }
    sp<ICalcService> calc = interface_cast<ICalcService>(binder);

    int result = calc->subtract(100, 50);  // Subtract function
    LOGI("CalcService returns: %d", result);
}
```


完全なコードと実行テスト
====

- [CalcClient2.zip](files/20110509-CalcClient2.zip)
- [CalcService2.zip](files/20110509-CalcService2.zip)

ここでは、クライアントが使用する `ICalcService.h` とその実装を、クライアントプログラム (CalcClient) に埋め込んでしまっていますが、複数のプログラムから使用するインタフェースとして切り出したい場合は、

- ICalcService.h（サービスインタフェース）
- ICalcService.so（サービスインタフェースを実装した共有ライブラリ）

といったファイルとして切り出して、共有できるように配置します。

CalcClient と CalcService プログラムをセット上の `/system/bin` に転送したら、以下のように CalcService サービスを起動し、CalcClient プログラムを使ってサービスにアクセスします。

```
# CalcService &
04-28 17:26:57.480  3178  3178 I CalcService: Add CalcService to ServiceManager

# CalcClient
04-28 17:27:33.236  3196  3196 I CalcClient: CalcService returns: 50
```

