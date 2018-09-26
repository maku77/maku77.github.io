---
title: Windows の右クリックから Vim を開けるようにする
date: "2013-06-10"
---

（Windows Vista、Windows 7 で動作確認）

以下のようにレジストリエディタ (`regedit`) で設定しておくと、テキストファイルを右クリックしたときに、「Vim で開く」というメニュー項目が表示されるようになります。

1. <kbd>Windows + R</kbd>（ファイル名を指定して実行）から **`regedit`** と入力してレジストリエディタを起動する。
2. `HKEY_CLASS_ROOT/*/shell` を右クリックして **`Vim`** というキーを新規作成。
3. `HKEY_CLASS_ROOT/*/shell/Vim` の規定の REG_SZ の値を **`&Vim で開く`** に修正。
4. `HKEY_CLASS_ROOT/*/shell/Vim` を右クリックして **`command`** というキーを新規作成。
5. `HKEY_CLASS_ROOT/*/shell/Vim/command` の規定の REG_SZ の値を **`C:\vim\gvim.exe "%1"`** などに修正（パラメータをダブルクォーテーションで囲んでおかないと、空白を含んだファイルを開けません）。

