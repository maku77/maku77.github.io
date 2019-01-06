---
title: "dbus-python で Session bus を使用するサーバ＆クライアントを実装する（シグナルの実装）"
date: "2012-09-05"
---

dbus-python のサーバ＆クライアント実装において、シグナルを使用するサンプルです。
シグナルを使用すると、サーバ側からクライアント側に任意のタイミングで通知を送ることができます。
以下のサンプルでは、`SetCount()` メソッドが呼ばれた場合に、`CountChanged` シグナルを発行しています。


サーバ側の実装
----

* [server-signal.py](./server-signal.py)

サーバ側に、シグナル発行用のメソッド `CountChanged()` を追加して、`SetCount()` メソッド内から呼び出すようにします。
シグナルを定義するには、`@dbus.service.signal` を付けてメソッドを定義します。

```python
class CounterObject(dbus.service.Object):
    def __init__(self, bus, obj_path):
        dbus.service.Object.__init__(self, bus, obj_path)
        self.count = 0

    @dbus.service.signal(INTERFACE, signature="i")
    def CountChanged(self, count):
        print('Emit signal: ' + str(count))

    @dbus.service.method(INTERFACE, in_signature="i", out_signature="")
    def SetCount(self, count):
        print('SetCount called: ' + str(count))
        self.count = count
        self.CountChanged(count)

    @dbus.service.method(INTERFACE, in_signature="", out_signature="i")
    def GetCount(self):
        print('GetCount called')
        return self.count
```


クライアント側の実装
----

* [client-signal.py](./client-signal.py)

リモートオブジェクトを操作するためのインタフェースを取得したら、`connect_to_signal()` メソッドを呼ぶことで、シグナルハンドラを登録することができます。
下記の例では、`CountChanged` シグナルを、`on_count_changed` というシグナルハンドラで受信するように登録しています。

```python
# Signal handler for "CountChanged"
def on_count_changed(count):
    print('CountChanged: ' + str(count))

def main():
    ...
    counter_iface.connect_to_signal('CountChanged', on_count_changed)
```

シグナルは非同期に受信する必要があるので、クライアント側にもメインループを生成する必要があります（下記の `loop.run()` の部分）。

```python
def on_timeout(counter_iface):
    val = counter_iface.GetCount()
    counter_iface.SetCount(val + 1)
    return True  # continued

def main():
    ...
    # Call the SetCount method repeatedly
    gobject.timeout_add(1000, on_timeout, counter_iface)

    # Start the mainloop to receive signals
    loop = gobject.MainLoop()
    loop.run()
```

ここでは、1 秒おきに `SetCount` するようにタイマーをしかけていますので、結果的に、1 秒おきに `CountChanged` シグナルが発生することになります。

