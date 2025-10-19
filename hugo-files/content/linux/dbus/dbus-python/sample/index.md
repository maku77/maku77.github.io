---
title: "Linuxメモ: dbus-python の公式サンプルコード"
url: "p/k2c9k5k/"
date: "2012-09-04"
tags: ["linux", "d-bus"]
aliases: /linux/dbus/dbus-python/sample.html
---

dbus-python のサンプルコード
----

下記は、dbus-python-1.1.1 に付属しているサンプルコードです。

* 単純なメソッドを実装したサーバー＆クライアント
  * [example-service.py](./example-service.py)
  * [example-client.py](./example-client.py)
  * [example-async-client.py](./example-async-client.py)
* シグナルを実装したサーバー＆クライアント
  * [example-signal-emitter.py](./example-signal-emitter.py)
* その他
  * [list-system-services.py](./list-system-services.py)

python-dbus のインストール
----

Python で `import dbus` の実行時にエラーが発生する場合は、`python-dbus` がインストールされていません。
以下のようにしてインストールしてください。

{{< code lang="console" title="Ubuntu の場合" >}}
$ sudo apt-get install python-dbus
{{< /code >}}

