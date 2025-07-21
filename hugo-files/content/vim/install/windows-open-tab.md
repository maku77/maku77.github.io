---
title: "Windows でファイルを開くときに既存の GVim 内のタブで開く"
url: "p/6mdd9es/"
date: "2013-10-25"
tags: ["vim"]
---

以下のように設定しておくと、すでに `gvim.exe` のウィンドウが起動している状態で別のファイルを開いたときに、既存ウィンドウ内のタブとして開くことができます。

1. レジストリエディタ (`regedit`) を起動
2. `/HKEY_CLASSES_ROOT/Applications/gvim.exe/shell/open/command` の値を `"C:\app\vim73-kaoriya\gvim.exe" --remote-tab-silent "%1"` のように変更

`gvim.exe` のパスは自分の環境に合わせてください。

