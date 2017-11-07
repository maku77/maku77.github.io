---
title: "NAT の種類のメモ"
created: 2008-05-07
description: "NAT (Network Address Translation) 関連の用語 SNAT、DNAT、NAPT、IP マスカレードなどの説明です。"
---

**NAT (Network Address Translation)** とは、パケット中の IP アドレスを書き換える技術のことをいいます。
送信元、送信先のどちらを書き換えるかで次のように区別することもあります。

SNAT (Source NAT)
: 送信元の IP アドレスを書き換える

DNAT (Destination NAT)
: 送信先の IP アドレスを書き換える

IP アドレスだけでなく、TCP や UDP のポート番号も同時に変換する機能を **NAPT (Network Address Port Translation)**、あるいは **IP マスカレード**といいます。
この機能は、ブロードバンドルータなどによく実装されています。

ネットワークのインタフェースを複数持っている端末で、NAT による設定を行うことで、柔軟なルーティングを行えるようになります。
例えば、Linux マシンに 2 枚の NIC（ネットワークカード）を挿して、`iptables` で NAT の設定を行えば、その Linux マシンをルータとして利用できるようになります。

