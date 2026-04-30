---
title: "Windowsメモ: Windows のログオフ時にネットワーク接続を維持する"
url: "p/u7mm35a/"
date: "2012-12-13"
tags: ["windows"]
aliases: /windows/network/keep-connection-after-logging-off.html
---

（下記は Windows 7/Vista で確認）

Windows からログオフした状態になると、デフォルトではネットワーク接続が切れてしまいます。
ログオフ後もネットワーク接続を維持するには下記のようにします。

1. `regedit.exe` を管理者権限で起動する
2. `HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\services\RasMan\Parameters`
の `KeepRasConnections` (REG_DWORD) を `1` に変更する
3. Windows を再起動する

参考: [http://support.microsoft.com/kb/q950918/](http://support.microsoft.com/kb/q950918/)
