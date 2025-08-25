---
title: "Vagrantメモ: 不要な box を削除する (vagrant remove box)"
url: "p/6wsa466/"
date: "2016-11-01"
tags: ["vagrant"]
aliases: ["/vagrant/remove-box.html"]
---

box ファイルとは
----

Vagrant の仮想マシンは、OS のスナップショットともいえる box ファイルをベースにして生成されるようになっています。
この仕組みによって、仮想マシンの破棄 (`vagrant destroy`)、再生性 (`vagrant up`)、Ansible による環境構築のテスト、といった試行錯誤が短時間で実行できるようになっています。

box ファイルの実体は VirtualBox などの仮想イメージなので、ファイルサイズは非常に大きくなります（数百 MB ～ 数 GB 程度）。
そのため、box ファイルは複数の Vagrant プロジェクトから共有されるようになっており、デフォルトでは `~/.vagrant.d` ディレクトリに格納されています（Windows の場合は `%USERPROFILE%\.vagrant.d`）。

```
$ ls ~/.vagrant.d/boxes
centos-VAGRANTSLASH-7  hashicorp-VAGRANTSLASH-precise64
```

この例では、`centos/7` と `hashicorp/precise64` という box が格納されていることが分かります（スラッシュはファイル名に含められないので `-VAGRANTSLASH-` と置換されているようです）。


不要な box の削除
----

box ファイルはサイズが大きく、ホスト上のディスクスペースを消費してしまうため、本当に不要になったら削除してしまいましょう。
現在存在している box ファイルを確認するには、`vagrant box list` コマンドを使用します。

```
$ vagrant box list
centos/7            (virtualbox, 1609.01)
hashicorp/precise64 (virtualbox, 1.1.0)
```

不要な box が存在していることが分かったら、その box 名を `vagrant box remove` コマンドに渡します。

```
$ vagrant box remove centos/7
Removing box 'centos/7' (v1609.01) with provider 'virtualbox'...
```

これで完全に box ファイルが削除されます。
再び同じ box イメージを使用して `vagrant up` で仮想環境を立ち上げようとすると、box イメージのダウンロードから始まるので時間がかかります。

box ファイルを削除しようとしたときに、その box から生成した仮想マシンが動作している場合は警告が表示されます。

```
$ vagrant box remove hashicorp/precise64
Box 'hashicorp/precise64' (v1.1.0) with provider 'virtualbox' appears
to still be in use by at least one Vagrant environment. Removing
the box could corrupt the environment. We recommend destroying
these environments first:

webserver1 (ID: 3894c5d21ba1487c889d285dd9ff42c6)
```

この状態で box を削除しても、既に動作している仮想マシンが停止してしまうことはありませんが、念のため `vagrant global-status` コマンドでその仮想マシンを使用しているプロジェクトを確認し、本当に削除してよい box なのかを判断しましょう。


box ファイルの格納場所を変える VAGRANT_HOME
----

複数の Vagrant プロジェクトから共有される box ファイルなどは、デフォルトで `~/.vagrant.d` 以下に格納されますが、`VAGRANT_HOME` という環境変数を設定することで異なるディレクトリを扱うように変更することができます。

