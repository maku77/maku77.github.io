---
title: ポートフォワードにより仮想マシン内のサーバにアクセスする
created: 2016-10-24
---

ポートフォワードによるネットワーク構成について
----

Vagrant 仮想マシン内でサーバを立ち上げた場合、デフォルトではそのポートは仮想マシン内で閉じた世界のものになっています。
例えば、仮想マシン内で Web サーバをポート番号 80 で立ち上げただけでは、外の世界（ホスト側）から `http://localhost/` でアクセスできるようにはなりません。
外の世界から仮想マシン内のサーバにアクセスできるようにするための方法として、ポートフォワードがあります（[ブリッジネットワークを構成する方法](bridged-network.html)もあります）。

ポートフォワードの設定では、ホストマシンのあるポート番号を、特定の仮想マシンのポート番号にマッピングします。
ホストマシンの特定のポート番号にアクセスがあったときに、ホストマシンが仮想マシンに対してリクエストを転送することで、間接的に仮想マシンへ接続されます。

```
LAN 上のマシン ----[port:10080]----> ホスト ----[port:80]----> 仮想マシン
```

ポートフォワードを利用したネットワーク構成では、既存の物理 LAN 上の別のマシンからは、ホストマシンの IP アドレスしか見えません。
仮想マシンの存在は隠蔽されており、ホストマシンですべてのサービスが提供されているかのように見えます。

ポートフォワードの設定を行う
----

`Vagrantfile` ファイルの中で、`config.vm.network` に **forwarded_port** を設定することで、ポートフォワードの設定を行うことができます。
下記は、仮想マシン内の HTTP (80) と HTTPS (443) のポートを、ホスト側のポート 10080 と 10443 にそれぞれマッピングする例です。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.network "forwarded_port", guest: 80, host: 10080   # HTTP
  config.vm.network "forwarded_port", guest: 443, host: 10443  # HTTPS
end
```

設定を変更したら、仮想マシンをリロードして反映させます。

```
$ vagrant reload
...
==> default: Forwarding ports...
    default: 80 (guest) => 10080 (host) (adapter 1)
    default: 443 (guest) => 10443 (host) (adapter 1)
    default: 22 (guest) => 2222 (host) (adapter 1)
...
```

これで、Web ブラウザなどから `http://localhost:10080/` にアクセスしたときに、仮想マシン内の Web サーバの 80 ポートに繋がるようになります（もちろん、仮想マシン側で nginx などの Web サーバを立ち上げておく必要があります）。


ポートフォワードの設定を確認する
----

現在動作している仮想マシンのポートフォワード設定は `vagrant port` コマンドで確認することができます。

```
$ vagrant port
The forwarded ports for the machine are listed below. Please note that
these values may differ from values configured in the Vagrantfile if the
provider supports automatic port collision detection and resolution.

    22 (guest) => 2222 (host)
    80 (guest) => 10080 (host)
   443 (guest) => 10443 (host)
```

ポート番号 22 (SSH) はデフォルトでホスト側のポート 2222 にマッピングされています（これによって `ssh vagrant@127.0.0.1 -p 2222` という接続が可能になっています）。


UDP トラフィックのフォワード
----

`config.vm.network "forwarded_port"` のデフォルトの設定では、TCP プロトコルによるフォワード設定しか行われません。
UDP プロトコルのパケットも仮想マシンにフォワードしたい場合は、明示的に `protocol: "udp"` オプションを指定して設定する必要があります。


#### Vagrantfile
```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.network "forwarded_port", guest: 2003, host: 12003, protocol: "tcp"
  config.vm.network "forwarded_port", guest: 2003, host: 12003, protocol: "udp"
end
```


ポート番号のコンフリクトの自動解決
----

ポートフォワード設定を行って Vagrant の仮想マシンを立ち上げようとしたときに、ホスト側のポート番号がすでに使用されていてコンフリクトした場合、仮想マシンの立ち上げは失敗します。

```
$ vagrant reload
...
Vagrant cannot forward the specified ports on this VM, since they
would collide with some other application that is already listening
on these ports. The forwarded port to 8080 is already in use
on the host machine.
...
```

下記のように、**auto_correct** オプションを設定しておくと、ポート番号がコンフリクトした場合に、ホスト側で空いているポートを探して自動的にマッピングすることができます。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.network "forwarded_port", guest: 80, host: 8080, auto_correct: true
end
```

仮想マシンを立ち上げると、自動的にポート番号が割り当てられます。

```
$ vagrant up
==> default: Checking if box 'hashicorp/precise64' is up to date...
==> default: Fixed port collision for 80 => 8080. Now on port 2200.
==> default: Clearing any previously set network interfaces...
==> default: Preparing network interfaces based on configuration...
    default: Adapter 1: nat
==> default: Forwarding ports...
    default: 80 (guest) => 2200 (host) (adapter 1)
    default: 22 (guest) => 2222 (host) (adapter 1)
...
```

ここでは、ホスト側のポート 8080 が開いていなかったため、自動的に 2200 が割り当てられています。
この自動割り当ての範囲は、デフォルトで 2200 から 2250 になっていますが、下記のように調整することもできます。

```ruby
config.vm.usable_port_range = (10000..10050)
```

