---
title: "VMware のネットワーク設定"
url: "p/vugpfvz/"
date: "2007-03-22"
description: "VMware によるネットワーク構築を理解するためのメモと、各種トラブルシューティング情報です。"
tags: ["memo", "network", "vmware"]
aliases: ["/memo/network/vmware.html"]
---

VMware Player 1.0.3 で確認しています。


仮想ネットワーク (virtual network)／仮想スイッチ (virtual switch)
----

VMware をインストールすると、最大 9 つの virtual network（VMnet0 ～ VMnet8）が利用できるようになります。
デフォルトの設定では、VMnet0、VMnet1、VMnet8 がそれぞれ次のような構成のネットワークとして設定されています。

- **VMnet0**: bridged network（ブリッジ接続ネットワーク）
- **VMnet1**: host-only network（ホストオンリー接続ネットワーク）
- **VMnet8**: NAT network（NAT 接続ネットワーク）

例えば、デフォルトで VMnet0 は物理的な NIC と接続するための virtual bridge（仮想ブリッジ）として使えるようになっているので、virtual machine（ゲスト OS）の virtual network adapter（仮想ネットワークアダプタ）を VMnet0 に接続するように設定すれば、物理的な NIC が繋がっている外のネットワークに接続することができます。

```
[virtual machine]
virtual network adapter ---> VMnet0 (bridge) ---> [physical NIC] ---> external network
```

virtual network (`VMnet\*`) の数は上記のように 9 個までと制限されていますが、ひとつの virtual network に対して複数の virtual machine（正確には virtual network adapter）を接続することができるので、ほとんど問題にはなりません（Windows ホストでは接続数に制限なし、Linux ホストでは 32 個の virutal network adapter をひとつの virtual network に接続可能）。

```
virtual network adapter -------+
                               |
virtual network adapter ---- VMnet0 (bridge) ---> [Physical NIC]
                               |
virtual network adapter -------+
```

{{< note title="virtual network = virtual switch?" >}}
VMnet0 ～ VMnet8 という名前は、正確には virtual network を示しているのだと思いますが、VMware のマニュアルでは、virtual switch と呼んでいたりします。よく考えてみると、

```
「ネットワークに接続する」＝「そのネットワークに繋がっているスイッチに接続する」
```

とみなしても大差はないので、VMnet0 ～ VMnet8 のことを virtual network ではなく、virutal switch と呼んでも問題ないのでしょう。
VMnet0 ～ VMnet8 までの 9 個の仮想的なスイッチングハブがあるんだと考えると分かりやすいです。
ホスト OS と、ゲスト OS は必ず `VMnet\*` を介して通信します。

```
[Host OS]                                  [Guest OS]
virtual network adapter ----> VMnet1 <---- virtual network adapter
```
{{< /note >}}


virtual network adapter（仮想ネットワークアダプタ）
----

### ホスト OS 側の virtual network adapter

Windows に VMware Player をインストールすると、virtual network に接続するための virtual network adapter（仮想の NIC）が追加されます。
デフォルトでは、次の 2 つの仮想ネットワークアダプタが追加されます。

- VMware Network Adapter VMnet1 (for host-only network)
- VMware Network Adapter VMnet8 (for NAT network)

※ bridged network (VMnet0) は、ホスト OS 側の物理的 NIC に直接接続されているような状態になるため、ホスト OS 側に VMnet0 用の virtual network adapter は追加されません。

{{< image src="img-001.png" >}}

※ VMnet1、VMnet8 という仮想スイッチングハブに接続された 2 つのネットワークカード (VMware Network Adapter) があると考えると分かりやすいです。

host-only network も NAT network も通常は private IP アドレスを使用するネットワークであるため、上記の virtual network adapter には `192.168.***.***` という IP アドレスが割り当てられているはずです。
VMnet1 と VMnet8 は仮想といえども別のネットワークなので、それぞれ別のネットワークアドレスが割り振られます（例えば、`192.168.100.1` と `192.168.200.1`）。

これらの IP アドレスは固定で指定することも、VMware 内部の DHCP サーバによって割り当てることもできます。
ここで割り当てた IP アドレスが、ゲスト OS 側から見えるホスト OS の IP アドレスとなります。

### ゲスト OS 側の virtual network adapter

virtual machine 側では、virtual network (VMnet0-8) に接続するための virtual network adapter を最大 3 つまで使用することができます。
ホスト OS 側の virtual network adapter と同様に、これらはそれぞれ別の virtual network (virtual switch) に接続することができます。

3 つの virtual network adapter を、どの virtual network (virtual switch) に接続するかは、virtucal machine の設定ファイル (`.vmx`) で指定します。

```
ethernet0.present = "true"
ethernet0.connectionType = "custom"
ethernet0.vnet = "vmnet5"

ethernet1.present = "true"
ethernet1.connectionType = "custom"
ethernet1.vnet = "vmnet2"
```

`connectionType` を `bridged`, `hostonly`, `nat` のようなネットワーク構成で指定する場合は、以下のように記述します。
このとき、`vnet` の指定は必要ありません。

```
ethernet0.present= "true"
ethernet0.connectionType = "bridged"

ethernet0.present= "true"
ethernet0.connectionType = "hostonly"

ethernet0.present= "true"
ethernet0.connectionType ="nat"
```


ネットワーク構成の種類
----

### bridged network（ブリッジ接続ネットワーク）

virtual switch を bridged network として構成した場合、物理的 NIC に接続された virtual bridge として振る舞うようになります。
デフォルトでは VMnet0 が bridged network として構成されています。

virtual machine 側の virtual network adapter を、この virtual bridge に接続する場合、ホスト OS が使っている物理的 NIC を利用して virtual network adapter に新しい IP アドレスを割り当てます。
つまり、物理的 NIC が接続しているネットワークの IP アドレスがもう 1 つ必要になります。

```
[virtual machine]
vitual network adapter (xxx.yyy.zzz.128)
         |
         |
      virtual bridge ---- physical NIC ---- external network
      (VMnet0)                              (xxx.yyy.zzz.0)
```

イメージとしては、virtual machine が直接、物理的 NIC を利用して外部のネットワークに接続しているようなものです。
NAT network や host-only network の場合とは違い、private ネットワークは構成されません。
つまり、以下のように、ゲスト OS が物理的 NIC を使用していると考えることができます。

```
[virtual machine]
physical NIC (xxx.yyy.zzz.128) ---- external network
                                    (xxx.yyy.zzz.0)
```

bridged network では、virtual machine とホスト OS に対して、物理的 NIC が接続しているネットワークの IP アドレスをそれぞれ個別に割り当てるため、外のマシンからは単純に複数のマシンが稼動しているかのように見えます（1 つの物理 NIC が複数の IP アドレスを持つ）。
そのため、vitual machine 上で Web サーバなどを立ち上げれば、外のマシンからアクセスすることができます。

複数の vitual machine をひとつの virtual bridge (VMnet0) に繋ぐことも可能です。
ただし、その数だけ IP アドレスが必要になることに注意が必要です。

ホストに複数の物理的 NIC が接続されている場合は、その数だけ virtual bridge を追加することができます（VMnet0 ～ 8 のいずれかをその NIC 用の virtual bridge とする）。

### NAT (Network address translation) network（NAT 接続ネットワーク）

VMware のアドレス変換機能 (NAT) を用いて、virtual machine 側の virtual network adapter に割り当てた private IP アドレスと、物理的 NIC に割り当てた IP アドレスによる通信を仲介します。
デフォルトでは VMnet8 が NAT network として構成されています。

ホスト OS が使っている物理的 NIC の IP アドレスと MAC アドレスを共有して外部ネットワークに接続することができるため、新しい IP アドレスを追加できない場合に便利です。
ただし、NAT ネットワークには通常 private IP アドレスが割り当てられるため、外部のネットワークから virtual machine にアクセスすることはできません（外部に公開する Web サーバなどは構築できません）。

```
[virtual machine]
virtual network adapter (192.168.0.128)
          |
          |
      virtual switch ---- VMware NAT device ---- external network
      (VMnet8)                                   (xxx.yyy.zzz.0)
```

ホスト OS 側にも、NAT ネットワーク (VMnet8) に接続するための virtual network adapter が追加され、ホスト OS と virtual machine の間で private IP アドレスを用いて通信することができます。

```
[virtual machine]
virtual network adapter (192.168.0.128)
          |
          |
      virtual switch
      (VMnet8)
          |
          |
virtual network adapter (192.168.0.1)
[host OS]
```

NAT 接続では、単純にネットワーク間のアドレス変換、パケットの仲介をするだけなので、物理的 NIC の存在は意識されません。
これは、物理的 NIC がどのような種類のネットワークに接続されていても、IP アドレスさえ割り当てられていれば virtual machine がそのネットワークへ接続できることを意味しています。
例えば、Ethernet 以外の次のようなネットワークにも接続することが可能です。

- ダイアルアップ
- トークンリング
- ATM

### host-only network (ホストオンリー接続ネットワーク)

ホスト OS と virtual machine の間を繋ぐためのプライベートネットワークです。
基本的には物理的 NIC や、外部のネットワークとは隔離されたものなので、ローカル環境からのみアクセス可能なセキュアなサーバを構築することができます。
テスト用の virtual machine を動作させる場合は、このモードで動作させるのが安全です。

```
[virtual machine]
virtual network adapter (192.168.34.128)
          |
          |
      virtual switch
      (VMnet1)
          |
          |
virtual network adapter (192.168.34.1)
[host OS]
```

基本的に、private ネットワークに配置された virtual machine は外部のネットワークにアクセスできませんが、ホスト OS 側でルーティングソフトウェアや、Proxy ソフトウェアを動作させることで、外部のネットワークに接続することが可能です。
例えば、Windows の「インターネット共有」の機能を使えば、host-only network からインターネットへ接続することができます。
このような構成を利用することで、NAT network の場合と同様に、Ethernet 以外のネットワーク（ダイアルアップ等）にも接続することができます。

### VMware DHCP サーバによる private IP アドレス自動割り当て

NAT network、host-only network に接続する virtual network adapter の private IP アドレスは、VMware の提供する DHCP サーバ機能によって自動的に割り振ることができます。
この場合、virtual switch (VMnet1, VMnet8) にそれぞれ DHCP サーバ機能が組み込まれているかのように振る舞います。

```
[virtual machine]
virtual network adapter (192.168.0.128)
         |
         |
     virtual switch ---- VMware DHCP server
     (VMnet8)            (for 192.168.0.0 network)
```

DHCP サーバの IP アドレス割り当て範囲などは、VMware のネットワーク設定ツールで変更できます（Windows の場合は vmnetcfg.exe の DHCP タブで設定）。

{{< image src="img-002.png" >}}

{{< image src="img-003.png" >}}


NAT 接続の設定
----

[VMware のマニュアル](https://www.vmware.com/jp/pdf/server_vm_manual.pdf) に NAT ネットワーク上の DHCP について分かりやすい説明があるので抜粋しておきます。

> ネットワークの構成作業を簡易化するために、VMware Server のインストール時にDHCP サーバが自動的にインストールされます。
> このため、NAT デバイスを使ってネットワーク上で稼働する仮想マシンは、DHCP リクエストを送信してIP アドレスをダイナミックに取得することができます。
> NAT ネットワーク上のDHCP サーバ（ホストオンリーネットワーク構成でも使用されます）は、`<net>.128 ～ <net>.254` の範囲でIP アドレスをダイナミックに割り当てます。
> ここで`<net>` は、NAT ネットワークに割り当てあられたネットワーク番号のことです。
> VMware Server は NAT ネットワークには常にクラス C アドレスを使用します。
> `<net>.3 ～ <net>.127` の IP アドレスは、静的 IP アドレスに使用することができます。
> IP アドレス `<net>.1` はホストアダプタに、`<net>.2` はNAT デバイスに割り当てられています。

簡単にまとめると、

- NAT で GuestOS を外部ネットワークに繋ぐには、GuestOS の Virtual Adapter を VMnet8 に繋げばよい。
- VMnet8 に繋ぐ Host OS 側の Virtual Adapter には固定 IP アドレス 192.168.11.1 などが割り当てられている。
- VMnet8 に繋ぐ Guest OS 側の Virtual Adapter は通常 DHCP でアドレス取得 (128～254) するようにしておけば問題ない。固定 IP アドレスを割り当てたい場合は、3 ～ 127 の範囲で割り当てること。この割り当て範囲は、Virutla Network Editor (vmnetcfg.exe) の DHCP タブで変更可能。
- NAT デバイスは、デフォルトで VMnet8 に接続されていることになっているが、Virtual Network Editor (vmnetcfg.exe) で変更できる。NAT デバイスの IP アドレスは、192.168.11.2 などになっている（これも変更可能）。

### 設定例

（Host OS: Windows Vista、Guest OS: CentOS 5.1 で確認しています）

#### Windows 側 (Host OS) の設定

インターネットに接続された NIC があることを前提とします。
VMnet8 用の仮想アダプタ（Local Area Connection 4 など）には、192.168.11.1 などの固定 IP アドレスが割り当てられます（デフォルト）。
VMware の vmnetcfg.exe の Host Virtual Network Mapping タブで、以下のようになっているか確認してください。

```
VMnet8 ==> Local Area Connection 4  # NAT network
```

#### Linux 側 (Guest OS) の設定

仮想マシンの構成ファイル (CentOS_5.1.vmx) を編集して、NAT 接続 (VMnet8) できるようにします。

```
ethernet0.present = TRUE
ethernet0.connectionType = "custom"
ethernet0.vnet = "VMnet8"
```

Linux の eth0 の IP アドレスを DHCP で割り当てます（GATEWAY の設定はなくてもよいっぽい）。

{{< code lang="ini" title="/etc/sysconfig/network-scripts/ifcfg-eth0" >}}
DEVICE=eth0
ONBOOT=yes
BOOTPROTO=dhcp
TYPE=Ethernet
USERCTL=yes
IPV6INIT=no
PEERDNS=yes
{{< /code >}}


virtual bridge と接続する物理的 NIC を指定する
----

物理的 NIC が 1 つしか存在しない場合、デフォルトでは VMnet0 の virtual bridge はその NIC に接続されます。
複数の NIC が存在する場合は、いずれかの NIC が接続されます。

Windows の場合、仮想ネットワークエディタ (Vertual Network Editor) vmnetcfg.exe を使って virtual bridge をどの物理的 NIC に接続するかを明示的に指定することができます。

{{< image src="img-004.png" >}}

デフォルトでは、「自動ブリッジ」ページの、「VMnet0 へブリッジできる物理ネットワークアダプタを自動的に選択する。」 にチェックが入っているので、VMnet0 の virtual bridge は、自動的にいずれかの物理的 NIC に接続されます。

{{< image src="img-005.png" >}}

物理的 NIC が複数存在し、VMnet0 の virtual bridge を特定の NIC に接続したい場合は、「ホスト仮想ネットワークの割り当て」ページを開き、VMnet0 のプルダウンメニューから接続したい物理的 NIC を選択します。

※ この図は、左側の VMnet0～9 のどのネットワーク（仮想スイッチ）に、Host OS のどの NIC（物理 NIC、仮想 NIC 含む）を接続するかの対応表だと考えればよいです。
ここでの設定は、あくまで Host OS 側の設定です。Guest OS 側の VMnet と仮想 NIC の接続の設定は、構成ファイル (.vmx) で行います。

※ ちなみに、ここで表示される「ホストと共有プライベートネットワーク」を選択すると、いずれかの NIC が自動的に選択されます。
つまり、「自動ブリッジ」ページのチェックボックスにチェックを入れるのと同じことです。


その他
----

### Guest OS で Multi-core CPU を使用する

VMware 構成ファイル (`.vmx`) の以下のコメントアウト行の `#` を外して有効にする。

```
# If you wish to use multi-core CPU on guest OS, uncomment a below line.
# numvcpus = "2"
```

### VMnetDHCP を停止する

1. スタートメニューから services.msc を起動
2. サービス一覧から "VMware DHCP Service" を右クリック ==> Properties
3. Stop をクリック
4. Startup type: を Disabled に設定

### トラブルシューティング: NIC 二枚挿しで Bridge 接続がうまくいかない

Guest OS (Linux) 側の仮想アダプタの 1 枚目を Bridge 接続用に設定するとうまくいくことがあります。

```
# Bridge 接続用
ethernet0.present = "true"
ethernet0.connectionType = "custom"
ethernet0.vnet = "VMnet0"

# NAT 接続用
ethernet1.present = "true"
ethernet1.connectionType = "custom"
ethernet1.vnet = "VMnet8
```

- Virtual Network Editor (vmnetcfg.exe) の "Host Virtual Network Mapping" タブの VMnet0 で、ブリッジする NIC を選択する（自動選択にしない）。
- Windows のネットワーク設定で、ブリッジ接続に使用する NIC だけ、Properties のダイアログで "VMware Bridge Protocol" にチェックを入れておくのがよいかも。

### トラブルシューティング: VMware の DHCP が動いているはずなのに IP アドレスを取得できない

（Host OS: Windows Vista、Guest OS: CentOS 5.1 で確認）

- `/etc/sysconfig/network-scripts/ifcfg-eth0` などで `BOOTPROTO=dhcp` になっているか確認。
- Virtual Network Editor (vmnetcfg.exe) で DHCP サービスがちゃんと動いているか確認。
- 仮想マシン構成ファイル (CentOS_5.1.vmx) で仮想アダプタが DHCP の有効な仮想ネット (VMnet8) などに接続されているか確認。
- うまく行かなかったら、Host OS と Guest OS を再起動してみる。※意外と重要

### トラブルシューティング: vmnetcfg.exe で任意の物理 NIC を VMnet に割り当てられない

（Host OS: Windows Vista、VMware 2.5.2 で確認）

症状はこんな感じ。

- 仮想ネットワークエディタ (Virtual Network Editor) の「自動ブリッジ (Automatic Bridging)」タブの次のような項目がグレーアウトされていてチェックが外せない。`VMnet0 へブリッジできる物理ネットワークアダプタを自動的に選択する。(Automatically choose an available physical network adapter to bridge to VMnet0)`
- 仮想ネットワークエディタ (Virtual Network Editor) の「ホスト仮想ネットワークの割り当て (Host Virtual Network Mapping)」タブで、任意の物理 NIC を選択できない。

原因は、`vmnetcfg.exe` を Administrator 権限で実行していないから。
Vista の場合は以下のように設定しておかないとダメらしい。まぎらわしいっ (T-T)

1. `vmnetcfg.exe` を右クリック ==> Properties。
2. Conpatibility タブの "Run this program as an administrator" にチェックを入れて OK。

### トラブルシューティング: Windows Vista の Windows Firewall で Guest OS から Host OS へ ping が通らない

1. スタートメニューから "Windows Firewall"
2. "Change settings" を選択して、Off にしてみる

- 参考: [Windows Vistaのファイアウォールでpingへの応答を許可する － ＠IT](https://www.atmarkit.co.jp/fwin2k/win2ktips/896vistaping/vistaping.html)

### トラブルシューティング: ウィルスバスターの Firewall で Host OS と Guest OS の接続がうまくいかない

1. スタートメニューから `services.msc` を起動
2. サービス一覧から "OfficeScan NT Firewall" を右クリック ==> Properties
3. Stop をクリック
4. Startup type: を Disabled に設定

### トラブルシューティング: VMware 上の Red Hat Linux 9 で DHCP でアドレスをとれない

`ifcfg-eth0` ファイルなどに、おまじないコードみたいなのを書けば直るらしい。
確かに直った。

```
DEVICE=eth0
BOOTPROTO=dhcp
ONBOOT=yes
check_link_down () {
    return 1;
}
``` 

