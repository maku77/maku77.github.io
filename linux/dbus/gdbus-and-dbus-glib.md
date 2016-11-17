---
title: GDBus と dbus-glib
created: 2012-05-09
---

D-Bus の low-level API は **libdbus** というライブラリで提供されています。

通常、D-Bus アプリケーションを作成するときは、この low-level API だけ使用してコーディングするのではなく、D-Bus binding と呼ばれる、より上位のライブラリを使用します（というような記述が、D-Bus の API ドキュメントのトップに書いてあります）。

- [http://dbus.freedesktop.org/doc/api/html/index.html](http://dbus.freedesktop.org/doc/api/html/index.html)

例えば C 言語の場合は、**dbus-glib** や **GDBus** などがあります。
現在は dbus-glib は obsolete になっていて、GDBus が GIO ライブラリの一部として提供されているようです（glib-2.30.x 以降でサポート）。
とはいえ、現状は GDBus の情報はネット上にほとんどありません。

それぞれのライブラリで、API のプレフィックスが以下のように異なっているので、サンプルコードなどを読むときに、どのライブラリを使用しているかの判断基準になります。

- Low-level API: `dbus_XXX`
- dbus-glib API: `dbus_g_XXX`
- GDBus API: `g_dbus_XXX`

GDBus は dbus-glib と違い、内部で libdbus を使用していません。

GDBus は type system として **GVariant** をバリバリ使うので、GVariant についてマスターしている必要があります（はっきり言って、GVariant 使ったコードはあまり美しくないです）。

XML introspection データからグルーコード（Stub とか Proxy とか）を作成するツールがあるのですが、dbus-glib が dbus-binding-tool を使うのに対し、GDBus は gdbus-codegen というツールを使用します。

