---
title: "Docker のネットワークについて理解する (none, host, bridge)"
url: "p/7fjnqtw/"
date: "2022-06-15"
tags: ["Docker"]
---

Docker の 3 つのネットワーク
----

Docker をインストールすると、デフォルトで __`none`__、__`host`__、__`bridge`__ という 3 つのネットワークが生成されます。
`docker network ls` コマンドの出力の `NAME` カラムを見ると、これら 3 つの名前があることを確認できます。

```console
$ docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
1d32c46c83f6   bridge    bridge    local
a97adbf7b226   host      host      local
7543afe52cd6   none      null      local
```

none
: Docker コンテナにネットワークインタフェースを持たせたくない場合に指定します。
つまり、外部との通信が一切できないコンテナになります。

host
: ホスト側のネットワークインタフェースを共有するときに指定します。
つまり、ホストと同じ IP アドレスがコンテナに割り当てられます。

bridge
: 一番よく使用されるネットワークで、`bridge` という名前の仮想ブリッジに接続されたネットワーク環境であることを示します。
`docker container create (run)` でコンテナを作成するときにネットワーク (`--net`) を指定しないと、デフォルトでこの `bridge` が使われます。
Linux のブリッジ機能を利用しており、このネットワークに参加したコンテナからは、インターネットにアクセスすることができます。
同じ `bridge` に接続するコンテナは、同じ仮想ブリッジで接続された状態（同じネットワークに所属する状態）になるため、相互に通信ができます（`ping` など）。

Docker コンテナを作成する際には、どのネットワークを使うかを `--net` オプションで指定します。
指定しない場合はデフォルトで `bridge` が使われます。

{{< code lang="console" title="例: ネットワーク接続できないコンテナを作成する" >}}
$ docker container create --name my-ubuntu --net none ubuntu:22.04
{{< /code >}}


bridge ネットワークのアドレスを確認してみる
----

デフォルトで作成される `bridge` には、`172.17.0.0/16` などのネットワークアドレスが割り当てられています。

```console
$ docker network inspect bridge --format "{{.IPAM.Config}}"
[{172.17.0.0/16   map[]}]
```

このネットワークに参加させるコンテナには、`172.17.0.2` や `172.17.0.3` などの IP アドレスが割り当てられていきます。
`172.17.0.1` など、末尾が 1 のアドレスは通常はデフォルトゲートウェイに割り当てられています。
コンテナに割り当てられた IP アドレスを確認するには、次のようにコンテナ情報を表示します。

```console
$ docker container inspect --format "{{.NetworkSettings.IPAddress}}" my-cont
172.17.0.2
```

あるいは、次のようにネットワーク情報を表示して、__`Containers`__ プロパティ以下の情報を参照すれば、そのネットワークに参加しているすべてのコンテナの IP アドレスを確認できます。

```console
$ docker network inspect bridge
```

新しいブリッジネットワークを作成すると、別のネットワークアドレス（`172.18.0.0/16` など）が割り当てられます。

```console
$ docker network create my-net
$ docker network inspect my-net --format "{{.IPAM.Config}}"
[{172.18.0.0/16  172.18.0.1 map[]}]
```

Docker Compose を使えば、複数のコンテナを同一ネットワークに参加させるということをシンプルに表現できます。

{{% note title="format オプションについて" %}}
上記の例では、`--format` オプションで出力を絞り込んでいますが、ここで使われている書式は、[Go 言語の template パッケージ](https://golang.org/pkg/text/template) に従っています。
{{% /note %}}


コンテナ名による通信
----

Docker ネットワークを作成し、コンテナをそのネットワークに参加させると、各コンテナ同士が __コンテナ名__ で通信できるようになります。
つまり、各コンテナに割り当てられた IP アドレスを知る必要がありません。

{{< image w="500" src="img-001.drawio.svg" title="Docker コンテナ間のコンテナ名での通信" >}}

次のように実行すれば、コンテナ名で通信を行えることを確認できます。

```console
（新しいネットワーク my-net を作成）
$ docker network create my-net

（1 つ目のコンテナ alpine1 を起動）
$ docker run --rm -it --net my-net --name alpine1 alpine /bin/sh

（別のターミナルから 2 つ目のコンテナ alpine2 を起動）
$ docker run --rm -it --net my-net --name alpine2 alpine /bin/sh

（コンテナ名で互いに ping できるか確認）　
# ping alpine1
# ping alpine2
```


（コラム）仮想ブリッジ bridge0
----

Docker 用のブリッジは、ホスト側に __`bridge0`__ という名前の仮想ブリッジとして作成されます。

```console
$ ifconfig bridge0
bridge0: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
    options=63<RXCSUM,TXCSUM,TSO4,TSO6>
    ether 36:f6:6f:f3:5a:c0
    Configuration:
        id 0:0:0:0:0:0 priority 0 hellotime 0 fwddelay 0
        maxage 0 holdcnt 0 proto stp maxaddr 100 timeout 1200
        root id 0:0:0:0:0:0 priority 0 ifcost 0 port 0
        ipfilter disabled flags 0x0
    member: en1 flags=3<LEARNING,DISCOVER>
            ifmaxaddr 0 port 10 priority 0 path cost 0
    member: en2 flags=3<LEARNING,DISCOVER>
            ifmaxaddr 0 port 11 priority 0 path cost 0
    member: en3 flags=3<LEARNING,DISCOVER>
            ifmaxaddr 0 port 12 priority 0 path cost 0
    Address cache:
    nd6 options=201<PERFORMNUD,DAD>
    media: <unknown type>
    status: inactive
```

ちなみにこの `bridge0` は、Docker Desktop for Windows/macOS を使っている場合は直接は参照できないことに注意してください。
Docker Desktop の場合は、その仮想マシン内に `bridge0` が作成されます。

