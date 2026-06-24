---
title: "Windowsメモ: 日本語キーボードのノート PC で英字配列の USB キーボードを使用する"
url: "p/ybmz5ak/"
date: "2012-12-01"
lastmod: "2026-06-24"
tags: ["windows"]
aliases: /windows/usb-us-keyboard.html
changes:
  - 2026-06-24: Windows 11 でも使えることと説明画像を追加。
---

（下記は Windows 7、8、10、11 で動作することを確認しています）

ノート PC が日本語配列のキーボードを搭載している場合に、USB 接続でつないだキーボードだけを US 英字配列で使用したい場合は、下記のようにレジストリを修正します。

{{< image border="true" src="img-001.png" title="レジストリの修正" >}}

1. スタートメニューからレジストリエディター (`regedit.exe`) を「管理者として実行」
2. **`HKEY_LOCAL_MACHINE¥SYSTEM¥CurrentControlSet¥services¥i8042prt¥Parameters`** を開く（Ctrl-F で `LayerDriver JPN` を検索すれば一発でジャンプできます）
3. `LayerDriver JPN` の値を `kbd106.dll` から **`kbd101.dll`** に変更
4. `OverrideKeyboardIdentifier` の値を `PCAT_106KEY` から **`PCAT_101KEY`** に変更
5. `OverrideKeyboardSubtype` の値は **`2`** のままでキープ
6. Windows を再起動

5 番目のステップで、`OverrideKeyboardSubtype` の値を `2` から `0` に変更すると、本体側のキーボードの配列も US 英字配列として動作してしまうので注意してください（逆に、ノート PC 側のキーボードも US 英字配列として扱いたい場合は、ここを `0` にしてしまえば OK です）。

