---
title: D-Bus 関連リンクと関連ツール
created: 2012-05-10
---

GDBus
----

* [[本家] Highlevel D-Bus Support](http://developer.gnome.org/gio/stable/gdbus-convenience.html)
* [[本家] Lowlevel D-Bus Support](http://developer.gnome.org/gio/stable/gdbus-lowlevel.html)
* [GDBusServer (Peer-to-Peer の実装サンプル）](http://developer.gnome.org/gio/2.32/GDBusServer.html)
* [gdbus-codegen ツールの使い方](http://developer.gnome.org/gio/stable/ch30s05.html)


D-Bus Java
----

* [DBus-Java Documentation（Javadoc もあります）](http://dbus.freedesktop.org/doc/dbus-java/)

D-Bus low-level API や GLib bindings のコードを最初に見るよりも、先に Java bindings を使ってみるのが使い方のイメージを掴みやすいかも。


その他のリンク
----

* [D-Bus GLib bindings - Reference Manual](http://dbus.freedesktop.org/doc/dbus-glib/)
* [Development/Tutorials/D-Bus - KDE TechBase](http://techbase.kde.org/Development/Tutorials/D-Bus)
* [maemo.org - Plain_html: D. Source code for the GLib D-Bus synchronous example](http://maemo.org/development/training/maemo_platform_development_content/plain_html/node13/)
* [DBus low-level API を使ったサンプルコード](http://www.matthew.ath.cx/misc/dbus)


D-Bus 関連ツール
----

qdbusviewer (for Linux)
: GUI で、System Bus、Session Bus 上のサービスを一覧にし、そのサービスが公開しているオブジェクトと、メソッド＆シグナルも一覧にできるツール。メソッドの呼び出しと、その Reply の確認なども行えます。

qdbus (for Linux)
: qdbusviewer を CUI 版にしたようなツール。特に理由がなければ、qdbusviewer を使ったほうが便利です。

