---
title: 複数の仮想マシンを同時に立ち上げる
created: 2016-10-25
---

Vagrant 仮想マシンとして複数のサーバを同時に立ち上げるには、`Vagrantfile` の中で下記のようにそれぞれの仮想マシン定義を行います。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.define "vagrant1" do |c|
    c.vm.box = "hashicorp/precise64"
    c.vm.network "forwarded_port", guest: 80, host: 10080
    c.vm.network "forwarded_port", guest: 443, host: 10443
  end
  config.vm.define "vagrant2" do |c|
    c.vm.box = "hashicorp/precise64"
    c.vm.network "forwarded_port", guest: 80, host: 20080
    c.vm.network "forwarded_port", guest: 443, host: 20443
  end
  config.vm.define "vagrant3" do |c|
    c.vm.box = "hashicorp/precise64"
    c.vm.network "forwarded_port", guest: 80, host: 30080
    c.vm.network "forwarded_port", guest: 443, host: 30443
  end
end
```

ここでは、HTTP 用のポートと HTTPS 用のポートをホスト側のポートにそれぞれマッピングしています。
あとは、`vagrant up` とするだけで、すべての仮想マシンが順番に立ち上がります。

```
$ vagrant up
Bringing machine 'vagrant1' up with 'virtualbox' provider...
Bringing machine 'vagrant2' up with 'virtualbox' provider...
Bringing machine 'vagrant3' up with 'virtualbox' provider...
...
```

ちゃんと稼働しているかどうかは `vagrant status` コマンドで確認することができます。

```
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

```
$ vagrant ssh vagrant1
```

