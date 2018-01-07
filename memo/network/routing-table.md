---
title: "ルーティングテーブルの管理"
date: "2008-04-22"
---

ルーティングテーブルを表示する
----

#### Linux の場合

~~~
$ /sbin/route
~~~

#### Windows の場合

~~~
C:\> route print
~~~


ルーティングテーブルにエントリを追加する
----

#### Linux の場合

~~~
$ route add -net <NetworkAddr> netmask <NetMask> gw <GatewayAddr> [Metric] [Interface]

例)
$ route add -net 192.168.1.0 netmask 255.255.255.0 gw 192.168.100.254 metric 1 eth0
~~~

#### Windows の場合

~~~
C:\> route add <NetworkAddr> netmask <NetMask> gateway <GatewayAddr>
~~~


ルーティングテーブルからエントリを削除する
----

#### Linux の場合

~~~
$ route del -net 192.168.1.0 netmask 255.255.255.0
~~~


ネットワーク・トラブルの調査 (Windows)
----

### ルータに ping は通るが他のネットワークに ping が通らないケース

`route print` コマンド（あるいは `netstat -r`）でルーティングテーブルを表示し、デフォルトゲートウェイのアドレスを確認します。
正しいルータのアドレスになっていなければ、NIC の設定で正しいアドレスを設定します。

~~~
C:\> route print
...
Default Gateway: 192.168.1.254
~~~

`tracert` コマンドで他のネットワークの経路の先頭にデフォルトゲートウェイの IP Address が表示されるかを確認します。

複数の NIC が刺さっている場合は、ルータに実際に繋がっている方の NIC の設定のみにデフォルトゲートウェイのアドレスを設定するようにします。


