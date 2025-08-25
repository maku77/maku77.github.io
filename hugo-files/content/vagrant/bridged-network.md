---
title: "Vagrantメモ: ブリッジネットワークを構築する"
url: "p/ymenseo/"
date: "2016-10-31"
tags: ["vagrant"]
aliases: ["/vagrant/bridged-network.html"]
---

Vagrant でブリッジネットワークを有効にすると、ホストマシンと同じ LAN 内に、あたかももう一台の物理マシンがあるかのように、仮想マシンに IP アドレスを割り当てることができます。

* [Public Networks - Networking - Vagrant by HashiCorp](https://www.vagrantup.com/docs/networking/public_network.html)

ホストオンリーネットワークと同様に、仮想マシンからホストマシンへアクセスできるようになるだけでなく、LAN 内の他のマシンから仮想マシンにアクセスすることもできます（LAN のセキュリティポリシーでマシン間のアクセスが制限されている場合を除く）。

ブリッジネットワークを有効にするには、下記のように `config.vm.network` のネットワークタイプとして、**`public_network`** を設定します。

{{< code lang="ruby" title="LAN 内の DHCP サーバから IP アドレスを割り当てる" >}}
Vagrant.configure("2") do |config|
  config.vm.network "public_network"
end
{{< /code >}}

{{< code lang="ruby" title="静的に IP アドレスを割り当てる" >}}
Vagrant.configure("2") do |config|
  config.vm.network "public_network", ip: "192.168.0.17"
end
{{< /code >}}

`Vagrantfile` を変更したら、仮想マシンを立ち上げなおせば設定が反映されます。

```console
$ vagrant reload
```

