---
title: Vagrant の仮想マシンに SSH で接続する
created: 2016-10-19
---

vagrant ssh コマンドで SSH 接続する
----

`vagrant up` コマンドによる仮想マシンの起動が終わると、SSH でシェル接続が可能になります。
通常、SSH での接続にはサーバアドレスやユーザ ID の指定などが必要ですが、Vagrant によって立ち上げられた仮想マシンには、`vagrant ssh` コマンドを使用して簡単に SSH 接続できるようになっています。

```
$ vagrant ssh

Welcome to Ubuntu 12.04 LTS (GNU/Linux 3.2.0-23-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
Welcome to your Vagrant-built virtual machine.
Last login: Wed Oct 19 06:59:47 2016 from 10.0.2.2

vagrant@precise64:~$
```

`vagrant ssh` 経由で Vagrant の仮想マシンに接続するには、OpenSSH ベースの SSH クライアントがインストールされている必要があります。

#### SSH クライアントがインストールされていない場合のエラー

```
$ vagrant ssh
`ssh` executable not found in any directories in the %PATH% variable.
```

#### OpenSSH ではない SSH クライアントがインストールされている場合（ここでは PuTTy）のエラー
```
$ vagrant ssh
The `ssh` executable found in the PATH is a PuTTY Link SSH client.
Vagrant is only compatible with OpenSSH SSH clients. Please install
an OpenSSH SSH client or manually SSH in using your existing client
using the information below.

Host: 127.0.0.1
Port: 2222
Username: vagrant
Private key: D:/z/vagrant/.vagrant/machines/default/virtualbox/private_key
```

Windows 用の OpenSSH クライアントは下記からダウンロードできます。

- [PowerShell/Win32-OpenSSH](https://github.com/PowerShell/Win32-OpenSSH/releases)


通常の SSH クライアントで Vagrant 仮想マシンへ接続する
----

`vagrant ssh` コマンド経由ではなく、直接 `ssh` コマンドを使用して仮想マシンに接続したい場合は、接続先のアドレスや、ユーザ名、Private キーなどの情報を知る必要があります。
これらの情報を調べるには、`vagrant ssh-config` を使用します。

```
$ vagrant ssh-config
Host default
  HostName 127.0.0.1 ★
  User vagrant ★
  Port 2222 ★
  UserKnownHostsFile /dev/null
  StrictHostKeyChecking no
  PasswordAuthentication no
  IdentityFile /Users/maku/sample/.vagrant/machines/default/virtualbox/private_key ★
  IdentitiesOnly yes
  LogLevel FATAL
```

上記の★のついた部分が SSH 接続に必要な情報です。
これらの情報を `ssh` コマンドのパラメータで指定することで、Vagrant 仮想マシンに SSH 接続することができます。

```
$ ssh vagrant@127.0.0.1 -p 2222 -i /Users/maku/sample/.vagrant/machines/default/virtualbox/private_key

Welcome to Ubuntu 12.04 LTS (GNU/Linux 3.2.0-23-generic x86_64)

 * Documentation:  https://help.ubuntu.com/
Welcome to your Vagrant-built virtual machine.
Last login: Wed Oct 19 06:54:45 2016 from 10.0.2.2

vagrant@precise64:~$
```

