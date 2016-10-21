---
title: Vagrant プラグインを vagrant up で自動的にインストールする
created: 2016-10-21
---

Vagrant のプラグインは下記のようにインストールすることができます。

```
$ vagrant plugin install ＜プラグイン名＞
```

`Vagrantfile` に下記のように記述しておけば、プラグインのインストールを、`vagrant up` 時に自動的に行うことができるようになります。

#### Vagrantfile

```ruby
def install_plugin(plugin)
  system "vagrant plugin install #{plugin}" unless Vagrant.has_plugin? plugin
end

# 必要なプラグインを指定
install_plugin('vagrant-proxyconf')

Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  # ...
end
```

#### 実行例

```
$ vagrant up
Installing the 'vagrant-proxyconf' plugin. This can take a few minutes...
Installed the plugin 'vagrant-proxyconf (1.5.2)'!
Bringing machine 'default' up with 'virtualbox' provider...
```

