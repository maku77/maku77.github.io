---
title: "Vagrantメモ: 複数の仮想マシンで共通の SSH キーを使用する"
url: "p/y9zrmbf/"
date: "2016-10-28"
tags: ["vagrant"]
aliases: ["/vagrant/same-ssh-key.html"]
---

Vagrant で作成した複数の仮想マシンを Ansible などでまとめて制御する場合、同じ SSH キーでアクセスできると便利だったりします。

Vagrant 1.7 以降のデフォルトでは、作成した仮想マシンごとに異なる SSH キーが設定されます。
例えば、ここでは、下記のような `Vagrantfile` を使用して３つの仮想マシンを作成してみます。

{{< code lang="ruby" title="Vagrantfile" >}}
BOX = "hashicorp/precise64"

Vagrant.configure("2") do |config|
  config.vm.define "vagrant1" do |c|
    c.vm.box = BOX
  end
  config.vm.define "vagrant2" do |c|
    c.vm.box = BOX
  end
  config.vm.define "vagrant3" do |c|
    c.vm.box = BOX
  end
end
{{< /code >}}

立ち上げます。

```console
$ vagrant up
Bringing machine 'vagrant1' up with 'virtualbox' provider...
Bringing machine 'vagrant2' up with 'virtualbox' provider...
Bringing machine 'vagrant3' up with 'virtualbox' provider...
```

仮想マシンが作成されたら、`vagrant ssh-config` コマンドを使用して、それぞれの SSH 接続情報を確認することができます。
仮想マシンごとに別々のプライベートキーが作成されていることが分かります（★の部分）。

```console
$ vagrant ssh-config
Host vagrant1
  HostName 127.0.0.1
  User vagrant
  Port 2222
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile /home/maku/sandbox/.vagrant/machines/vagrant1/virtualbox/private_key ★
  IdentitiesOnly yes
  LogLevel FATAL

Host vagrant2
  HostName 127.0.0.1
  User vagrant
  Port 2200
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile /home/maku/sandbox/.vagrant/machines/vagrant2/virtualbox/private_key ★
  IdentitiesOnly yes
  LogLevel FATAL

Host vagrant3
  HostName 127.0.0.1
  User vagrant
  Port 2201
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile /home/maku/sandbox/.vagrant/machines/vagrant3/virtualbox/private_key ★
  IdentitiesOnly yes
  LogLevel FATAL
```

複数の仮想マシンでひとつの SSH キーを使用するようにするには、下記のように `config.ssh.insert_key = false` を設定します。

{{< code lang="ruby" title="Vagrantfile（同じ SSH キーを使用するように修正）" >}}
BOX = "hashicorp/precise64"

Vagrant.configure("2") do |config|
  # Use the same key for each machine
  config.ssh.insert_key = false

  config.vm.define "vagrant1" do |c|
    c.vm.box = BOX
  end
  config.vm.define "vagrant2" do |c|
    c.vm.box = BOX
  end
  config.vm.define "vagrant3" do |c|
    c.vm.box = BOX
  end
end
{{< /code >}}

仮想マシンを作り直します。

```console
$ vagrant destroy
$ vagrant up
```

SSH 接続情報を確認してみると、すべての仮想マシンで同じ SSH キーが設定されていることが分かります。

```console
$ vagrant ssh-config
Host vagrant1
  HostName 127.0.0.1
  User vagrant
  Port 2222
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile ~/.vagrant.d/insecure_private_key ★
  IdentitiesOnly yes
  LogLevel FATAL

Host vagrant2
  HostName 127.0.0.1
  User vagrant
  Port 2200
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile ~/.vagrant.d/insecure_private_key ★
  IdentitiesOnly yes
  LogLevel FATAL

Host vagrant3
  HostName 127.0.0.1
  User vagrant
  Port 2201
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile ~/.vagrant.d/insecure_private_key ★
  IdentitiesOnly yes
  LogLevel FATAL
```

例えば、Ansible を使ってこれらの Vagrant 仮想マシンを制御する場合は、設定ファイル `ansible.cfg` には次のような感じで共通の SSH キーを設定しておくことができます。

{{< code lang="ini" title="ansible.cfg" >}}
hostfile = hosts
remote_user = vagrant
host_key_checking = False
private_keyfile = ~/.vagrant.d/insecure_private_key
{{< /code >}}

