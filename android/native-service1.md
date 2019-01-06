---
title: "ネイティブサービスの実装 (1) Binder 関連のクラス"
date: "2011-04-27"
---

Binder 関連クラス
----

ネイティブレイヤのサービスを実装するには、以下のような Binder 関連クラスを使用します。

### 継承構造

```
RefBase …… 各種オブジェクトの生存期間を参照カウンタ
  +-- IBinder …… サービスの基底クラス。プロセス間通信の共通インタフェースとしても使われる。
  |     +-- BpBinder …… クライアント側のプロキシクラス（ServiceManager が自動的にインスタンスを生成する）
  |     +-- BBinder …… サービスの基底クラス（基本的な実装を提供）
  |           +-- BnInterface
  +-- IInterface …… クライアントに提供するサービスインタフェースの基底クラス
  +-- BpRefBase
        +-- BpInterface …… クライアントに提供するサービスインタフェースの実装
```

### ポイント

* (a) サービスクラスは BBinder をサブクラス化して作成する。
* (b) サービスにアクセスするインタフェースは IInterface をサブクラス化して作成する。
* (c) サービスにアクセスするインタフェースの実装は BpInterface をサブクラス化して作成する。

(a) さえ準備してサービスを立ち上げれば、クライアントは `IBinder` インタフェースでサービスにアクセスできるようになります。
(b)、(c) はまともなサービスインタフェースとしてクライアントに公開する場合に必要です。


### ファイルのパス

- クラス定義
    - frameworks/base/include/binder/Binder.h (BBinder, BpRefBase)
    - frameworks/base/include/binder/BpBinder.h (BpBinder)
    - frameworks/base/include/binder/IBinder.h (IBinder)
    - frameworks/base/include/binder/IInterface.h (IInterface, BnInterface, BpInterface)
    - frameworks/base/include/utils/RefBase.h (RefBase)
    - frameworks/base/include/utils/StrongPointer.h (sp)
- 実装
    - frameworks/base/libs/binder/BpBinder.cpp
    - frameworks/base/libs/binder/IInterface.cpp
    - frameworks/base/libs/utils/RefBase.cpp


Binder 関連クラスをざっと眺めてみる
----

### RefBase

`RefBase` クラスは、すべての Binder 関連クラスの基底クラスで、インスタンスの生存期間をリファレンスカウンタで管理しています。

### IInteface

```cpp
class IInterface : public virtual RefBase {
public:
    sp<IBinder> asBinder();
    sp<const IBinder> asBinder() const;

protected:
    virtual IBinder* onAsBinder() = 0;
};
```

`IInterface` クラスは、ユーザが定義するサービスのインタフェースのベースクラスになります。

```cpp
class IEchoService : public IInterface {
public:
    virtual int add(int lhs, int rhs);
};
```

`IInterface` クラスには、サービスへアクセスするための `IBinder` オブジェクトを取得するための `asBinder()` メソッドが実装されています。
この中で使用している `onAsBinder()` メソッドは、pure virtual メソッドなので、サービス側で実装する必要があります。

```cpp
sp<IBinder> IInterface::asBinder() {
    return this ? onAsBinder() : NULL;
}

sp<const IBinder> IInterface::asBinder() const {
    return this ? const_cast<IInterface*>(this)->onAsBinder() : NULL;
}
```

`asBinder()` は、スマートポインタである `sp` クラスのインスタンスを返すため、`IBinder` オブジェクトは参照カウンタを使って自動的に生存期間が制御されます。

### IBinder

```cpp
class IBinder : public virtual RefBase {
public:
    virtual sp<IInterface> queryLocalInterface(const String16& descriptor);
    virtual const String16& getInterfaceDescriptor() const = 0;
    virtual bool isBinderAlive() const = 0;
    virtual status_t pingBinder() = 0;
```

- queryLocalInterface を使用して、名前指定で IInterface オブジェクトを取得できる。
- queryLocalInterface は、テンプレートクラス BnInterface を継承したサブクラスを作成することで容易に実装できる。

### BBinder

`BBinder` クラスは `IBinder` を継承しており、`BBinder::transact()` の中で abstract method である `onTransact()` を呼び出すようになっています。
この `onTransact()` を実装するようにすれば OK です。

```cpp
class BBinder : public IBinder {
public:
    virtual const String16& getInterfaceDescriptor() const;
    virutal bool isBinderAlive() const;
    virtual status_t pingBinder();
    virtual status_t dump(int fd, const Vector<String16>& args);
    virtual status_t transact(...);
    virtual status_t linkToDeath(...);
    virtual status_t unlinkToDeath(...);
    virtual void attachObject(...);
    virtual void* findObject(const void* objectID) const;
    virtual void detachObject(const void* objectID);
    virtual BBinder* localBinder();

protected:
    virtual status_t onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags = 0);
    ...
};
```

### BpInterface

```cpp
template<typename INTERFACE>
class BpInterface : public INTERFACE, public BpRefBase {
    ...
};
```

### BnInterface

```cpp
template<typename INTERFACE>
class BnInterface : public INTERFACE, public BBinder {
    ...
};
```

使い方は、こんな感じ。

```cpp
class BnMyClass : public BnInterface<IMyClass> {
public:
    virtual status_t onTransact(uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags = 0);
};
```

