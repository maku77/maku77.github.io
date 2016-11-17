---
title: dbus-python で Session bus を使用するサーバ＆クライアントを実装する（単純なメソッドの実装）
created: 2012-09-05
---

dbus-python を利用したサーバとクライアントの実装例です。
ここでは、`SetCount()` と `GetCount()` だけを行える `Counter` オブジェクトを提供するサービスと、クライアントを作成してみます。
インタフェース名などは以下のようにします。

```
Bus name: com.example.CounterService
Object path: /com/example/CounterObject
Interface: com.example.Counter
```

Session bus を使用する Server を実装する
----

* [server.py](./server.py)

D-Bus server 側では、export するオブジェクトを `dbus.service.Object` を継承して作成します。
公開するメソッドには `@dbus.service.method` アノテーションを付けて、以下の設定を行います。

* 何という名前のインタフェース名で公開するか
* 入力パラメータの Type string (in_signature)
* 戻り値の Type string (out_signature)

```python
class CounterObject(dbus.service.Object):
    @dbus.service.method(INTERFACE, in_signature="i", out_signature="")
    def SetCount(self, count):
        self.count = count
        print('SetCount called: ' + str(count))

    @dbus.service.method(INTERFACE, in_signature="", out_signature="i")
    def GetCount(self):
        print('GetCount called')
        return self.count
```

上記のように作成したオブジェクトは、以下のような手順で export します。

1. SessionBus オブジェクトの作成
  * `session_bus = dbus.SessionBus()`
2. 1 の SessionBus に、バス名を関連付ける
  * `bus_name = dbus.service.BusName(BUS_NAME, session_bus)`
3. SessionBus 上で任意のオブジェクトを export（ここでは CounterObject インスタンス）
  * `counter_object = CounterObject(session_bus, OBJECT_PATH)`
4. MainLoop 開始


Session bus を使用する Client を実装する
----

* [client.py](./client.py)

D-Bus クライアント側は、以下のような手順でリモートオブジェクト、インタフェースを取得します。

1. SessionBus オブジェクトの作成
  * `session_bus = dbus.SessionBus()`
2. リモートオブジェクトを取得
  * `counter_object = session_bus.get_object(BUS_NAME, OBJECT_PATH)`
3. リモートオブジェクトを操作するためのインタフェースを取得
  * `counter_iface = dbus.Interface(counter_object, INTERFACE)`

インタフェースを取得したら、後はローカルのオブジェクトのようにメソッドを呼び出せます。

```python
counter_iface.SetCount(100)
val = counter_iface.GetCount()
```

