---
title: "Vagrantメモ: 複数の仮想マシンを同時に立ち上げる"
url: "p/uu87xz5/"
date: "2016-10-25"
tags: ["vagrant"]
aliases: ["/vagrant/multiple-machines.html"]
---

複数の仮想マシンを立ち上げる
----

Vagrant 仮想マシンとして複数のサーバを同時に立ち上げるには、`Vagrantfile` の中で下記のようにそれぞれの仮想マシン定義を行います。
ここでは、すべて仮想マシンの box として `hashicorp/precise` (Ubuntu 12.04) を指定していますが、仮想マシンごとに異なる box を設定することもできます。

{{< code lang="ruby" title="Vagrantfile" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"

  config.vm.define "vagrant1" do |c|
    c.vm.network "forwarded_port", guest: 80, host: 10080
    c.vm.network "forwarded_port", guest: 443, host: 10443
  end

  config.vm.define "vagrant2" do |c|
    c.vm.network "forwarded_port", guest: 80, host: 20080
    c.vm.network "forwarded_port", guest: 443, host: 20443
  end

  config.vm.define "vagrant3" do |c|
    c.vm.network "forwarded_port", guest: 80, host: 30080
    c.vm.network "forwarded_port", guest: 443, host: 30443
  end
end
{{< /code >}}

各仮想マシンのコンフィギュレーションブロック内でポートフォワード設定を行っていますが、単純に仮想マシンを立ち上げるだけであれば、このあたりの個別の設定は後回しにしても構いません。
あとは、`vagrant up` とするだけで、すべての仮想マシンが順番に立ち上がります。

```console
$ vagrant up
Bringing machine 'vagrant1' up with 'virtualbox' provider...
Bringing machine 'vagrant2' up with 'virtualbox' provider...
Bringing machine 'vagrant3' up with 'virtualbox' provider...
...
```

ちゃんと稼働しているかどうかは `vagrant status` コマンドで確認することができます。

```console
$ vagrant status
Current machine states:

vagrant1                  running (virtualbox)
vagrant2                  running (virtualbox)
vagrant3                  running (virtualbox)

This environment represents multiple VMs. The VMs are all listed
above with their current state. For more information about a specific
VM, run `vagrant status NAME`.
```

それぞれの仮想マシンに SSH 接続するときは、下記のように `vagrant ssh` コマンドのパラメータで仮想マシン名を指定します。

```console
$ vagrant ssh vagrant1
```


複数の仮想マシンを同時に制御する
----

複数の仮想マシンが存在する環境では、Vagrant のコマンドも複数の仮想マシンに影響するように変化します。

```console
$ vagrant reload            # すべての仮想マシンを再起動
$ vagrant reload web1       # web1 という名前の仮想マシンを再起動
$ vagrant reload web1 web2  # web1 と web2 を再起動
$ vagrant reload /web\d/    # 正規表現で仮想マシンを指定することも可能
```

上記は、`vagrant reload` の例ですが、`vagrant up` や `vagrant halt` も同様に仮想マシン名を指定した制御を行えます。

