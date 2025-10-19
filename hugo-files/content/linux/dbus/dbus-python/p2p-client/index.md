---
title: "Linuxメモ: dbus-python で D-Bus の P2P クライアントを実装する"
url: "p/pzyxbo7/"
date: "2012-09-05"
tags: ["linux", "d-bus"]
aliases: /linux/dbus/dbus-python/p2p-client.html
---

python-dbus では、今のところ P2P 用のサーバを作成できないようです。
なので、ここでは P2P クライアントのサンプルだけを示します。

* [p2p-client.py](./p2p-client.py)

P2P 通信を行うには、`dbus.SessionBus` オブジェクトを使ってバス名を指定するのではなく、`dbus.connection.Connection` オブジェクトを使って、アドレス指定で接続します。

```python
con = dbus.connection.Connection('tcp:host=127.0.0.1,port=12300')
```

Connection が確立できたら、あとは Object path を指定して、リモートオブジェクトを取得できます。

```python
counter_object = con.get_object(object_path=OBJECT_PATH)
counter_iface = dbus.Interface(counter_object, INTERFACE)
```

