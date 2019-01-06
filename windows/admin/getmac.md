---
title: "NIC に割り当てられた MAC アドレスを調べる (getmac)"
date: "2015-04-13"
---

`getmac` コマンドを使用すると、各 Network カード (NIC) に割り当てられた MAC アドレスを確認することができます。

```
C:\> getmac

物理アドレス        トランスポート名
=================== ==========================================================
64-00-6B-86-25-80   \Device\Tcpip_{6A6905F8-DD64-4F0D-8652-1F0253153C87}
```

`-v` オプションを追加すると、もう少し詳しい情報が表示されます。

```
C:\> getmac /v

Connection Name Network Adapter Physical Address    Transport Name
=============== =============== =================== ==========================================================
Local Area Conn Intel(R) 82578D 78-2B-CC-82-C6-E5   \Device\Tcpip_{FDDCD4F0-C2F7-4FCF-AD1B-97B6CF028174}
VirtualBox Host VirtualBox Host 08-00-27-01-C0-67   \Device\Tcpip_{D09796D5-9C03-46DD-B0E9-8C0232EC421B}
VirtualBox Host VirtualBox Host 08-00-27-01-F0-E8   \Device\Tcpip_{70E58079-AD61-46FE-BC4E-78990A394849}
```

