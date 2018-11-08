---
title: 日本語キーボードのノート PC で英字配列の USB キーボードを使用する
date: "2012-12-01"
---

（下記は Windows 7、8、10 で動作することを確認しています）

ノート PC が日本語配列のキーボードを搭載している場合に、USB 接続でつないだキーボードだけを US 配列で使用したい場合は、下記のようにレジストリを修正します。

1. `regedit.exe` を管理者として起動
2. `HKEY_LOCAL_MACHINE¥SYSTEM¥CurrentControlSet¥services¥i8042prt¥Parameters` を開く（Ctrl-F で `LayerDriver JPN` を検索すれば一発でジャンプできます）
3. `LayerDriver JPN` の値を `kbd106.dll` から `kbd101.dll` に変更
4. `OverrideKeyboardIdentifier` の値を `PCAT_106KEY` から `PCAT_101KEY` に変更
5. `OverrideKeyboardSubtype` の値は `2` のままでキープ
6. Windows を再起動

5 番目のステップで、`OverrideKeyboardSubtype` の値を `2` から `0` に変更すると、本体側のキーボードの配列も US 配列として動作してしまうので注意してください。

