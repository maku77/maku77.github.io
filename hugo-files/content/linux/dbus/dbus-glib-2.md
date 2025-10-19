---
title: "Linuxメモ: dbus-glib で method call を実装する（ヘッダファイルの生成）"
url: "p/uzr93f8/"
date: "2012-05-08"
tags: ["linux", "d-bus"]
aliases: /linux/dbus/dbus-glib-2.html
---

ここでは、D-Bus (GLib bindings) を使って、リモートメソッド呼び出しを実現してみます。
名前は以下のように定義し、リモートでの足し算 (Add) と引き算 (Subtract) を実装します。

- Bus name
  * `com.example.MyApp`
- Object path
  * `/com/example/MyApp`
- Interface
  * `com.example.MyApp.Calc`
- Method
  * `Add(in INT32, in INT32, out INT32)`
  * `Subtract(in INT32, in INT32, out INT32)`

`dbus-binding-tool` を使って、XML ファイルからサーバ、クライアント実装用のヘッダファイルを生成できます。

{{< code lang="xml" title="interface.xml" >}}
<?xml version="1.0"?>
<node name="/com/example/MyApp">
    <interface name="com.example.MyApp.Calc">
        <method name="Add">
            <arg type="i" name="val1" direction="in" />
            <arg type="i" name="val2" direction="in" />
            <arg type="i" name="ret_val" direction="out" />
        </method>
        <method name="Subtract">
            <arg type="i" name="val1" direction="in" />
            <arg type="i" name="val2" direction="in" />
            <arg type="i" name="ret_val" direction="out" />
        </method>
    </interface>
</node>
{{< /code >}}

{{< code lang="console" title="ヘッダファイルの生成" >}}
$ dbus-binding-tool --mode=glib-server --prefix=ServerImpl interface.xml > server_autogen.h
$ dbus-binding-tool --mode=glib-client interface.xml > client_autogen.h
{{< /code >}}

サーバ側のヘッダファイルを生成するときは、必ずプレフィックスを指定する必要があるようです。
これは、メソッド実装側のメソッド名のプレフィックスとして付きます。

