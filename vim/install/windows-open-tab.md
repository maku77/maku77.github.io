---
title: Windows でファイルを開くときに既存の GVim 内のタブで開く
created: 2013-10-25
---

以下のように設定しておくと、すでに gvim.exe のウィンドウが起動している状態で、別のファイルを開いたときに、既存ウィンドウ内のタブとして開くことができます。

1. regedit を起動
2. `/HKEY_CLASSES_ROOT/Applications/gvim.exe/shell/open/command` の値を `"C:\app\vim73-kaoriya\gvim.exe" --remote-tab-silent "%1"` のように変更

gvim.exe のパスは自分の環境に合わせてください。
