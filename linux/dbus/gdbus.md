---
title: GDBus で簡単な P2P（クライアント＆サーバ）アプリを実装する
date: "2012-06-11"
---

GDBus 用のスタブを自動生成する
----

`gdbus-codegen` コマンドを使用すると、以下のような introspection データからスタブとなるコードを作成することができます。
`gdbus-codegen` コマンドがインストールされていない場合は以下のようにインストールできます。

```
$ sudo apt-get install libglib2.0-dev
$ sudo apt-get install libglib2.0-doc
```

ここでは、足し算と引き算を行う `com.example.MyApp.Calc` インタフェースを考えてみます。

#### interface.xml

```xml
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
```

以下のように実行すると、`calc.h` と `calc.c` が生成されます。

```
$ gdbus-codegen --generate-c-code=calc interface.xml
```

* [calc.h](./gdbus/calc.h)
* [calc.c](./gdbus/calc.c)


GDBus プログラムをビルドするための Makefile を作成する
----

GDBus を使ったコードをビルドのための `Makefile` を用意します。
ここでは、例として `server.cpp` というソースコードから、`server` というプログラムを生成することにします。

#### Makefile

```makefile
CXX = clang++
MY_LIBS = glib-2.0 gio-unix-2.0
CPPFLAGS = $(shell pkg-config --cflags ${MY_LIBS})
LDLIBS = $(shell pkg-config --libs ${MY_LIBS})

server: server.cpp calc.o
client: client.cpp calc.o
```

glib-2.0、gio-unix-2.0 のインクルードパスや、ライブラリ指定は、`pkg-config` コマンドを利用して生成しています。
`glib-object.h` で定義されている、`g_type_init()` などを呼び出すためには、gio-unix-2.0 というライブラリも必要です。

`calc.o` というのは、`gdbus-codegen` で自動生成した `calc.c` から生成されるものとします。


GDBus を使ったサーバとクライアントを実装する
----

クライアントとサーバは、`gdbus-codegen` によって生成した、プロキシ、スケルトン用の関数を利用して実装します。
クライアント側の通信にはプロキシオブジェクトを使用し、サーバ側のメソッド実装にはスケルトンオブジェクトを使用します。

### サーバ側の実装

* [server.cpp](./gdbus/server.cpp)

`gdbus-codegen` で生成したヘッダファイルを除くと、以下のような `handle_XXX` という関数ポインタをもつ構造体が定義されています。
Introspection XML で、`Add` メソッドと `Subtract` メソッドを定義したので、`handle_add` と `handle_subtract` という関数ポインタが生成されていることが分かります。

```cpp
struct _ComExampleMyAppCalcIface
{
  GTypeInterface parent_iface;

  gboolean (*handle_add) (
    ComExampleMyAppCalc *object,
    GDBusMethodInvocation *invocation,
    gint arg_val1,
    gint arg_val2);

  gboolean (*handle_subtract) (
    ComExampleMyAppCalc *object,
    GDBusMethodInvocation *invocation,
    gint arg_val1,
    gint arg_val2);
};
```

この関数ポインタのシグネチャに合わせて、各メソッドを実装します。
各メソッドから値を返すには、それぞれのメソッド用に生成された `complete_XXX` 関数を呼び出します。

```cpp
// Implementation of "Add" method.
gboolean handleAdd(ComExampleMyAppCalc *object, GDBusMethodInvocation *invocation,
        gint val1, gint val2) {
    gint ret = val1 + val2;
    com_example_my_app_calc_complete_add(object, invocation, ret);
    return TRUE;
}

// Implementation of "Subtract" method.
gboolean handleSubtract(ComExampleMyAppCalc *object, GDBusMethodInvocation *invocation,
        gint val1, gint val2) {
    gint ret = val1 - val2;
    com_example_my_app_calc_complete_subtract(object, invocation, ret);
    return TRUE;
}
```

後は、クライアントからの接続時に毎回呼び出されるコネクションハンドラの中で、`XXX_skeleton_new()` を使ってサーバサイドの Skeleton オブジェクトを生成し、上記で実装した各関数のポインタをセットしたら、`XXX_skeleton_export()` すれば OK です。

```cpp
// Called when the client connects to the server.
void handleConnect(GDBusServer *server, GDBusConnection *conn, gpointer data) {
    ComExampleMyAppCalc *skel = com_example_my_app_calc_skeleton_new();
    ComExampleMyAppCalcIface *iface = COM_EXAMPLE_MY_APP_CALC_GET_IFACE(skel);
    iface->handle_add = handleAdd;
    iface->handle_subtract = handleSubtract;

    GError *error = NULL;
    gboolean ret = g_dbus_interface_skeleton_export(
            G_DBUS_INTERFACE_SKELETON(skel), conn, OBJECT_PATH, &error);
    if (!ret) {
        g_error("g_dbus_interface_skeleton_export() failed: %s", error->message);
        exit(-1);
    }
}
```

### クライアント側の実装

* [client.cpp](./gdbus/client.cpp)

クライアントの実装でプロキシオブジェクトを生成するには、`gdbus-codegen` で自動生成された `XXX_proxy_new_sync()` などの関数を使用します。
プロキシオブジェクトを生成したら、各メソッドを呼び出せるようになります。
メソッドの呼び出しは、`gdbus-coden` で生成された `_call_XXX_sync()` などの関数を使用します。

```cpp
gint ret;
com_example_my_app_calc_call_add_sync(proxy, 100, 200, &ret, NULL, NULL);
```

