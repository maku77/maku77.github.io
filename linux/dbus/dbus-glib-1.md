---
title: dbus-glib 開発用ライブラリをインストールする
date: "2012-05-02"
---

（Ubuntu 12.04 で確認）

dbus-glib ライブラリのインストール
----

ここでは、D-Bus library (GLib bindings) をインストールして、C/C++ で D-Bus を使ったコードをコンパイルできるようにします。

```
$ sudo apt-get install libdbus-glib-1-dev
```

ちなみに、`apt-cache search 'libdbus'` のように実行すると、それっぽいパッケージを検索することができます。

上記を実行すると、以下のようなヘッダファイルがインストールされます。

```
/usr/include/dbus-1.0/dbus/dbus.h
/usr/include/dbus-1.0/dbus/dbus-glib.h
...
/usr/include/glib-2.0/glib.h
...
/usr/lib/x86_64-linux-gnu/glib-2.0/include  （64 bit Linux の場合）
```

D-Bus、GLib bindings のヘッダをインクルードするコードをビルドしてみる
----

#### main.cpp

```cpp
#include <dbus/dbus.h>
#include <dbus/dbus-glib.h>
#include <glib.h>

int main() {
}
```

#### ビルド

```
$ clang++ main.cpp `pkg-config --cflags --libs dbus-glib-1`
```

ここで使用している `pkg-config` コマンドは、指定したライブラリのインクルードパスなどのオプション文字列を生成してくれます。
単独で実行すると、例えば以下のような出力が得られます。

```
$ pkg-config --cflags --libs dbus-glib-1
-I/usr/include/dbus-1.0 -I/usr/lib/x86_64-linux-gnu/dbus-1.0/include
-I/usr/include/glib-2.0 -I/usr/lib/x86_64-linux-gnu/glib-2.0/include
-ldbus-glib-1 -ldbus-1 -lpthread -lrt -lgobject-2.0 -lglib-2.0
```

