---
title: 仮想マシン側のポートを公開する（ポートフォワード設定）
created: 2016-10-24
---

Vagrant 仮想マシン内でサーバを立ち上げた場合、デフォルトではそのポートは仮想マシン内で閉じた世界のものになっています。
例えば、仮想マシン内で Web サーバをポート番号 80 で立ち上げただけでは、外の世界（ホスト側）から `http://localhost/` でアクセスできるようにはなりません。

外の世界から仮想マシン内のサーバにアクセスできるようにするには、ホスト側と仮想マシン側のポート番号のマッピングを行っておく必要があります。
下記は、仮想マシン内の HTTP (80) と HTTPS (443) のポートを、ホスト側のポート 10080 と 10443 にマッピングする例です。

#### Vagrantfile

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.network "forwarded_port", guest: 80, host: 10080   # HTTP
  config.vm.network "forwarded_port", guest: 443, host: 10443  # HTTPS
end
```

設定を変更したら、下記のように反映します。

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

現在のポートフォワード設定は `vagrant port` コマンドで確認することができます。

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

