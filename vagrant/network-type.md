---
title: Vagrant の３種類のネットワーク構成の違い
created: 2016-10-31
---

３種類のネットワーク構成の概要
----

Vagrant におけるホストマシンと仮想マシンの間のネットワーク構成は、下記の３種類の形態から選択して構築します。

ポートフォワード (forwarded port)
: ホストマシンの特定のポート番号へのアクセスを、仮想マシンの特定のポート番号にフォワードする。物理ネットワーク上では、ホストマシン１台ががすべてのサービスを提供しているかのように見える。

ホストオンリーネットワーク (private network)
: ホストマシンと仮想マシンの間でプライベートなアドレス空間を割り当て、その範囲内で通信できるようにする。ホスト内で閉じた世界。

ブリッジネットワーク (bridge network)
: ホストマシンが参加している物理ネットワークに仮想マシンを参加させる。物理ネットワーク上で IP アドレスが割り当てられるため、あたかも物理的なマシンが増えたかのように見える。

どのネットワーク構成を選択するべきか
----

それぞれのネットワーク構成に一長一短があり、状況に応じて適切なネットワーク構成を選ぶことになります。
ここでは、各マシン同士のアクセスが可能かどうかと、そこから判断した仮想マシン側のセキュリティリスクという観点でざっと特徴をまとめておきます。

| ネットワーク構成 | host<br>↓<br>guest | guest<br>↓<br>host | guest<br>↓<br>guest | physical net<br>↓<br>guest | Security risk |
| ---- | :----: | :----: | :----: | :----: | ---- |
| ポートフォワード<br>(forwarded port) | OK | -- | -- | OK | Medium（仮想マシンの特定のポートだけアクセス可能） |
| ホストオンリーネットワーク<br>(private network) | OK | OK | OK | -- | Low（仮想マシンは外から見えない） |
| ブリッジネットワーク<br>(public network) | OK | OK | OK | OK | High（仮想マシンが物理ネットワークに参加する） |
