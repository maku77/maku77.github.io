---
title: "WSL2 内の Docker サーバーに LAN 内の別 PC からアクセスする (netsh)"
url: "p/w6cjckc/"
date: "2023-02-01"
tags: ["Docker", "WSL"]
---

別 PC から WSL2 内のサーバーへのアクセス
----

Windows の WSL2 環境で Docker コンテナを動かしているとき、そこで起動した Web サーバーなどへは、LAN 内の別 PC からはデフォルトではアクセスできません。

```
LAN 内の別 PC ---> Windows ---> WSL2 (Docker)
```

このようなアクセスを許可するには、Windows のコマンドプロンプトを管理者として実行し、次のように __`netsh interface portproxy`__ コマンドでポートフォワード設定をしてやる必要があります。
`netsh` ユーティリティ（ネットワークサービスシェル）は、Windows 上の様々なネットワーク構成を設定／参照するためのコマンドです。

{{< code title="Windows のポートフォワード設定を追加する" >}}
C:\> netsh interface portproxy add v4tov4 listenaddress=0.0.0.0 listenport=80 connectaddress=172.28.72.28 connectport=80
{{< /code >}}

上記の例では、外部からの Windows の 80 番ポートへのアクセス (`0.0.0.0:80`) を、WSL2 の 80 番ポート（この例では `172.28.72.28:80`）へ転送するように設定しています。

現在のポートフォワード設定 (portproxy) は次のように確認できます。

{{< code title="Windows のポートフォワード設定を確認する" >}}
C:\> netsh interface portproxy show all

ipv4 をリッスンする:         ipv4 に接続する:

Address         Port        Address         Port
--------------- ----------  --------------- ----------
0.0.0.0         80          172.28.72.28    80
{{< /code >}}

必要のなくなったポートフォワード設定は、次のように削除できます（Windows を再起動することでも削除できます）。
削除するときは、転送元のアドレスとポートを指定するだけで OK です。

{{< code title="Windows のポートフォワード設定を削除する" >}}
C:\> netsh interface portproxy delete v4tov4 listenaddress=0.0.0.0 listenport=80
{{< /code >}}

ちなみに、転送先となる WSL2 の IP アドレスは、WSL2 側の bash シェルで、__`ip a`__ コマンドで調べることができます。
通常は `eth0` デバイスに割り当てられた IPv4 アドレス (inet) の部分を参照すれば OK です。

{{< code title="WSL2 の IP アドレスを調べる" >}}
maku@win:/mnt/c$ ip a show dev eth0
6: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP group default qlen 1000
    link/ether 00:15:5d:93:85:2c brd ff:ff:ff:ff:ff:ff
    inet 172.28.72.28/20 brd 172.28.79.255 scope global eth0
       valid_lft forever preferred_lft forever
    inet6 fe80::215:5dff:fe93:852c/64 scope link
       valid_lft forever preferred_lft forever
{{< /code >}}

Windows 側から直接実行したい場合は、__`wsl -e`__ コマンドが使えます。

```
C:\> wsl -e ip a show dev eth0
```


適当な Web サーバーコンテナを起動して試してみる
----

今回の構成で接続テストするには、次のように WSL2 側で軽量な nginx コンテナを立ち上げてしまうのがお手軽です。

```
# Windows から WSL2 の bash シェルを起動
C:\> bash

# WSL2 側で Docker デーモンを起動
maku@win:/mnt/c$ sudo service docker start

# nginx コンテナを起動
maku@win:/mnt/c$ docker container run --rm -p 8000:80 nginx:alpine-slim
```

ここでは、コンテナを __`-p 8000:80`__ オプション付きで起動し、ホスト側の `localhost:8000` へのアクセスを、コンテナの `80` 番ポートに転送するよう設定しています。
この状態で、Windows 上のブラウザから `http://localhost:8000/` を開くと、WSL2 側の Web サーバーにアクセスできるはずです（WSL2 では、デフォルトで Windows 側の `localhost` へのアクセスを、WSL2 側に転送してくれるようになりました）。

ただし、この時点では、外部の PC からはまだアクセスできません。
次のように Windows 上でポートフォワード設定を追加する必要があります。

```
# 管理者権限でコマンドプロンプトを開いてポートフォワード設定
C:\> netsh interface portproxy add v4tov4 listenaddress=0.0.0.0 listenport=8000 connectaddress=172.28.72.28 connectport=8000
```

ここでのポイントは、転送先のポート番号を `8000` と指定するところです (__`connectport=8000`__)。
`80` 番ポートを使っているのは、あくまで WSL2 の中の Docker コンテナなので、外側からのアクセスには、コンテナ起動時に公開した `8000` 番ポートの方を使う必要があります。

これで、外部の PC から WSL2 内の Docker サーバーへアクセスできます（例えば、`192.168.1.100:8000` などで）。

٩(๑❛ᴗ❛๑)۶ わーぃ


将来的には Windows のポートフォワード設定がいらなくなる？
----

下記のマイクロソフトのページに、__Microsoft は、このエクスペリエンスを改善する方法を検討しています__ という記載があります（2023-01 時点）。
毎回、`netsh` でポートフォワード設定を行うのはさすがに面倒なので、この改善が早く実装されるとよいですね。

- [WSL を使用したネットワーク アプリケーションへのアクセス | Microsoft Learn](https://learn.microsoft.com/ja-jp/windows/wsl/networking)

