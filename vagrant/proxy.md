---
title: 仮想マシンのプロキシを設定する
created: 2016-10-21
---

Vagrant の仮想マシン側のプロキシ設定を行うには、**vagrant-proxyconf** プラグインを使用すると便利です。

vagrant-proxyconf によるプロキシ設定
----

#### vagrant-proxyconf プラグインのインストール

Vagrant のプラグインは、`vagrant plugin install` で簡単にインストールすることができます。

```
$ vagrant plugin install vagrant-proxyconf
Installing the 'vagrant-proxyconf' plugin. This can take a few minutes...
Installed the plugin 'vagrant-proxyconf (1.5.2)'!
```

あとは、`Vagrantfile` の中で下記のようにプロキシ設定を行います。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.proxy.http     = "http://proxy.example.com:8080"
  config.proxy.https    = "http://proxy.example.com:8080"
  config.proxy.no_proxy = "localhost,127.0.0.1"
end
```

