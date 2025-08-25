---
title: "Vagrantメモ: 仮想マシンでシェルスクリプトを実行する"
url: "p/4qkuf64/"
date: "2016-10-21"
tags: ["vagrant"]
aliases: ["/vagrant/shell-script.html"]
---

Vagrant では、仮想マシンの作成時にシェルスクリプトを実行して、サーバ環境のセットアップを行うことができます。
下記のように、`config.vm.provision` でシェルスクリプトを指定しておくと、仮想マシン側で任意の処理を実行することができます。


外部のシェルスクリプトファイルを実行する
----

{{< code lang="ruby" title="Vagrantfile" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.provision :shell, path: "bootstrap.sh"
end
{{< /code >}}

シェルスクリプトは、`Vagrantfile` があるディレクトリと同じディレクトリに置きます。

{{< code lang="sh" title="bootstrap.sh" >}}
apt-get update
apt-get install -y nginx
{{< /code >}}

`config.vm.provision` で指定したシェルスクリプトは、仮想マシンの作成し直したときか、`vagrant provision` コマンドを実行したときに実行されます。
仮想マシンを作成し直すには、`vagrant destroy` と `vagrant up` の実行が必要なため時間がかかります。
テストで実行する場合は、`vagrant provision` コマンドを使用するとよいでしょう。

{{< code lang="console" title="起動中の仮想マシンで provision 実行" >}}
$ vagrant provision
{{< /code >}}


Vagrantfile に埋め込んだシェルスクリプトを実行する
----

下記のようにすれば、仮想マシンで実行したいシェルスクリプトを `Vagrantfile` ファイル内に埋め込むことができます。

{{< code lang="ruby" title="Vagrantfile" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.provision :shell, inline: $script
end

$script = <<END
apt-get update
apt-get install -y nginx
END
{{< /code >}}

{{< code lang="console" title="provision 実行" >}}
$ vagrant provision
{{< /code >}}


詳細なコンフィギュレーションには Ansible を使うべし
----

上記のように、シェルスクリプトを利用してサーバ環境のセットアップを行うものよいのですが、シェルスクリプトは、あくまで仮想マシン作成時に一度だけ実行することを前提としています。
設定を少しずつ変更しながら環境を構築していきたい場合などは、Ansible や Chef などのツールを使用するのがオススメです。
Ansible や Chef には冪統性（べきとうせい）を保証する仕組みが備わっており、試行錯誤しながら最終的なコンフィギュレーションに近づけていくことができます。

