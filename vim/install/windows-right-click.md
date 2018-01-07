---
title: Windows の右クリックから Vim を開けるようにする
date: "2013-06-10"
---

（Windows Vista、Windows 7 で動作確認）

以下のようにレジストリエディタ (regedit) で設定しておくと、テキストファイルを右クリックしたときに、「Vim で開く」というメニュー項目が表示されるようになります。

* `HKEY_CLASS_ROOT/*/shell` を右クリックして `Vim` というキーを新規作成。
* `HKEY_CLASS_ROOT/*/shell/Vim` の標準の REG_SZ の値を `&Vim で開く` に修正。
* `HKEY_CLASS_ROOT/*/shell/Vim` を右クリックして `command` というキーを新規作成。
* `HKEY_CLASS_ROOT/*/shell/Vim/command` の標準の REG_SZ の値を `C:\vim\gvim.exe %1` などに修正。

