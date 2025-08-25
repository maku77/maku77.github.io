---
title: "Vagrantメモ: Vagrant により仮想マシンを立ち上げる"
url: "p/4trtqtw/"
date: "2016-10-19"
tags: ["vagrant"]
aliases: ["/vagrant/up-and-running.html"]
---

Vagrant は、仮想マシンの構成をするための情報として **`Vagrantfile`** というファイルを参照するので、まずはこのファイルを作成する必要があります。

Vagrantfile を生成する
----

`vagrant init` コマンドで、`Vagrantfile` の雛形を生成することができます。
ここでは、HashiCorp の提供している Ubuntu 12.04 LTS 64-bit のイメージ (`hashicorp/precise64`) を使用するように指定しています。

```
$ vagrant init hashicorp/precise64

A `Vagrantfile` has been placed in this directory. You are now
ready to `vagrant up` your first virtual environment! Please read
the comments in the Vagrantfile as well as documentation on
`vagrantup.com` for more information on using Vagrant.
```

仮想マシンのベースとなる OS イメージのことを **box** と呼びます。
デフォルトで使用可能な box は下記のようなものが用意されており、他の box は [HashiCorp's Atlas](https://atlas.hashicorp.com/boxes/search) で探すことができます。

- **ubuntu/trusty64**: Ubuntu 14.04 LTS 64-bit (Trusty Tahr)
- **hashicorp/precise64**: Ubuntu 12.04 LTS 64-bit (Precise Pangolin)
- **debian/jessie64**: Vanilla Debian 8 (Jessie)
- **centos/7**: CentOS Linux 7 x86_64

`vagrant init` を実行すると、次のような Vagrantfile ファイルがカレントディレクトリに生成されます。
Vagrantfile のあるディレクトリが、Vagrant プロジェクトのルートとなります。

{{< code lang="ruby" title="Vagrantfile（コメント群は省略）" >}}
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
end
{{< /code >}}

仮想マシンを立ち上げる
----

`vagrant up` コマンドを実行すると、`Vagrantfile` の記述に基いて、仮想マシンが構築されます。

```
$ vagrant up

Bringing machine 'default' up with 'virtualbox' provider...
==> default: Box 'hashicorp/precise64' could not be found. Attempting to find and install...
    default: Box Provider: virtualbox
    default: Box Version: >= 0
==> default: Loading metadata for box 'hashicorp/precise64'
    default: URL: https://atlas.hashicorp.com/hashicorp/precise64
==> default: Adding box 'hashicorp/precise64' (v1.1.0) for provider: virtualbox
    default: Downloading: https://atlas.hashicorp.com/hashicorp/boxes/precise64/versions/1.1.0/providers/virtualbox.box
    default: Progress: 7% (Rate: 132k/s, Estimated time remaining: 0:47:29))
...（ダウンロードが終わるまでしばらく待つ）...
==> default: Successfully added box 'hashicorp/precise64' (v1.1.0) for 'virtualbox'!
==> default: Importing base box 'hashicorp/precise64'...
==> default: Matching MAC address for NAT networking...
==> default: Checking if box 'hashicorp/precise64' is up to date...
==> default: Setting the name of the VM: vagrant_default_1476854130179_87997
==> default: Clearing any previously set network interfaces...
==> default: Preparing network interfaces based on configuration...
    default: Adapter 1: nat
==> default: Forwarding ports...
    default: 22 (guest) => 2222 (host) (adapter 1)
==> default: Booting VM...
==> default: Waiting for machine to boot. This may take a few minutes...
    default: SSH address: 127.0.0.1:2222
    default: SSH username: vagrant
    default: SSH auth method: private key
    default:
    default: Vagrant insecure key detected. Vagrant will automatically replace
    default: this with a newly generated keypair for better security.
    default:
    default: Inserting generated public key within guest...
    default: Removing insecure key from the guest if it's present...
    default: Key inserted! Disconnecting and reconnecting using new SSH key...
==> default: Machine booted and ready!
==> default: Checking for guest additions in VM...
    default: The guest additions on this VM do not match the installed version of
    default: VirtualBox! In most cases this is fine, but in rare cases it can
    default: prevent things such as shared folders from working properly. If you see
    default: shared folder errors, please make sure the guest additions within the
    default: virtual machine match the version of VirtualBox you have installed on
    default: your host and reload your VM.
    default:
    default: Guest Additions Version: 4.2.0
    default: VirtualBox Version: 5.1
==> default: Mounting shared folders...
    default: /vagrant => D:/z/vagrant
```

仮想環境が何もインストールされていない場合は、下記のように「デフォルトの provider が見つかりません」といった類のエラーメッセージが表示されます。
[VirtualBox](https://www.virtualbox.org/) などをインストールしましょう。

```
No usable default provider could be found for your system.
```

初回の `vagrant up` 実行時には、指定した OS の仮想マシンイメージがダウンロードされるため、しばらく時間がかかります。
ダウンロードが終わると、自動的に仮想マシンが立ち上がります。
仮想マシンが起動中かどうかは `vagrant status` コマンドで確認できます。

```
$ vagrant status
Current machine states:

default                   running (virtualbox)

The VM is running. To stop this VM, you can run `vagrant halt` to
shut it down forcefully, or you can run `vagrant suspend` to simply
suspend the virtual machine. In either case, to restart it again,
simply run `vagrant up`.
```

仮想マシンの停止と破棄
----

仮想マシンを停止したい場合は `vagrant halt` コマンドを実行します。

```
$ vagrant halt
==> default: Attempting graceful shutdown of VM...
```

仮想マシンそのものを削除してしまいたい場合は、`vagrant destroy` コマンドを実行します。

```
$ vagrant destroy
    default: Are you sure you want to destroy the 'default' VM? [y/N]
==> default: Destroying VM and associated drives...
```

