---
title: "Vagrantメモ: Vagrant プラグインを vagrant up で自動的にインストールする"
url: "p/3wvm5wi/"
date: "2016-10-21"
tags: ["vagrant"]
aliases: ["/vagrant/install-plugin.html"]
---

Vagrant のプラグインは下記のようにインストールすることができます。

```console
$ vagrant plugin install ＜プラグイン名＞
```

`Vagrantfile` に下記のように記述しておけば、プラグインのインストールを、`vagrant up` 時に自動的に行うことができるようになります。

{{< code lang="ruby" title="Vagrantfile" >}}
def install_plugin(plugin)
  system "vagrant plugin install #{plugin}" unless Vagrant.has_plugin? plugin
end

# 必要なプラグインを指定
install_plugin('vagrant-proxyconf')

Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  # ...
end
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ vagrant up
Installing the 'vagrant-proxyconf' plugin. This can take a few minutes...
Installed the plugin 'vagrant-proxyconf (1.5.2)'!
Bringing machine 'default' up with 'virtualbox' provider...
{{< /code >}}

