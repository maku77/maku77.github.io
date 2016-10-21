---
title: 仮想マシンでシェルスクリプトを実行する
created: 2016-10-21
---

下記のように、`config.vm.provision` でシェルスクリプトを指定しておくと、仮想マシン側で任意の処理を実行することができます。


外部のシェルスクリプトファイルを実行する
----

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.provision :shell, path: "bootstrap.sh"
end
```

シェルスクリプトは、`Vagrantfile` があるディレクトリと同じディレクトリに置きます。

#### bootstrap.sh

```sh
echo Hellooooooooooo
apt-get install -y nginx
```

`config.vm.provision` で指定したシェルスクリプトは、仮想マシンの作成しなおしたときか、`vagrant provision` コマンドを実行したときに実行されます。
仮想マシンを作成しなおすには、`vagrant destory` と `vagrant up` の実行が必要なため時間がかかります。
テストで実行する場合は、`vagrant provision` コマンドを使用するとよいでしょう。

#### 起動中の仮想マシンで provision 実行

```
$ vagrant provision
```


Vagrantfile に埋め込んだシェルスクリプトを実行する
----

下記のようにすれば、仮想マシンで実行したいシェルスクリプトを `Vagrantfile` ファイル内に埋め込むことができます。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.provision :shell, inline: $script
end

$script = <<END
echo Helooooooooooo
apt-get install -y nginx
END
```

#### provision 実行

```
$ vagrant provision
```

