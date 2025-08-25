---
title: "Vagrantメモ: 仮想マシンのプロキシを設定する"
url: "p/jgcwrrg/"
date: "2016-10-21"
tags: ["vagrant"]
aliases: ["/vagrant/proxy.html"]
---

Vagrant の仮想マシン側のプロキシ設定を行うには、**vagrant-proxyconf** プラグインを使用すると便利です。


vagrant-proxyconf によるプロキシ設定
----

Vagrant のプラグインは、`vagrant plugin install` で簡単にインストールすることができます。

{{< code lang="console" title="vagrant-proxyconf プラグインのインストール" >}}
$ vagrant plugin install vagrant-proxyconf
Installing the 'vagrant-proxyconf' plugin. This can take a few minutes...
Installed the plugin 'vagrant-proxyconf (1.5.2)'!
{{< /code >}}

あとは、`Vagrantfile` の中で下記のようにプロキシ設定を行います。

{{< code lang="ruby" title="Vagrantfile" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.proxy.http     = "http://proxy.example.com:8080"
  config.proxy.https    = "http://proxy.example.com:8080"
  config.proxy.no_proxy = "localhost,127.0.0.1"
end
{{< /code >}}

すると、Vagrant 仮想マシンを立ち上げるときに、自動的に各アプリ用のプロキシが設定されます。

```console
$ vagrant up
...
==> default: Configuring proxy for Apt...
==> default: Configuring proxy environment variables...
```

これで、Vagrant 仮想マシンから `apt-get` コマンドなどを実行したときにプロキシ経由でアクセスするようになります。


ホスト側の環境変数 http_proxy の値で仮想マシンのプロキシを設定する
----

Ruby では、環境変数の値を `ENV` で取得できるので、下記のようにすれば、ホスト側のプロキシ設定 (`http_proxy`) を、仮想マシン側のプロキシ設定にも反映することができます。

{{< code lang="ruby" title="Vagrantfile" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.proxy.http = ENV['http_proxy'] if ENV['http_proxy']
  config.proxy.https = ENV['https_proxy'] if ENV['https_proxy']
  config.proxy.no_proxy = "localhost,127.0.0.1"
end
{{< /code >}}

連動させるのが好ましくない場合は、独自の環境変数 `VAGRANT_VM_HTTP_PROXY` などを定義するという運用にするのもよいでしょう。

