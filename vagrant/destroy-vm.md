---
title: 不要な Vagrant 仮想マシンを削除する (vagrant destroy)
created: 2016-10-27
---

Vagrant で試行錯誤して仮想マシンを作っていると、依然作成した仮想マシンのゴミが残ってしまうことがあります。
ポートフォワード設定が重複してしまうと、新しい仮想マシンの起動も下記のように失敗してしまいます。

```
The forwarded port to 8080 is already in use on the host machine.
```

不要な仮想マシンを特定して１つずつ削除する
----

このような場合は、まず `vagrant global-status` で Vagrant で管理されている仮想マシンの一覧を表示します。
`vagrant status` と異なり、`vagrant global-status` はどのディレクトリからでも実行でき、ホスト上のすべての Vagrant 環境に関する情報を表示してくれます。

```
C:\> vagrant global-status
id       name     provider   state    directory
--------------------------------------------------------------------------
963e6d9  vagrant1 virtualbox running  D:/z/vagrant
dd68a71  vagrant2 virtualbox poweroff D:/z/vagrant

The above shows information about all known Vagrant environments
on this machine. This data is cached and may not be completely
up-to-date. To interact with any of the machines, you can go to
that directory and run Vagrant, or you can use the ID directly
with Vagrant commands from any directory. For example:
"vagrant destroy 1a2b3c4d"
```

上記の情報を頼りにして、不要な仮想マシンを削除してしまえば OK です。

```
D:\> vagrant destroy dd68a71
    vagrant2: Are you sure you want to destroy the 'vagrant2' VM? [y/N] y
==> vagrant2: Destroying VM and associated drives...
```

仮想マシン名の代わりに ID を指定すると、どのディレクトリからでも `vagrant destroy` を実行することができます。


どうしてもポートを占有しているプロセスが消えないとき
----

```
The forwarded port to 10443 is already in use on the host machine.
```

といったポート番号の占有エラーがどうしても解消できない場合は、そのポートを占有しているプログラムのプロセスを強引に kill してしまう方法があります。

下記は Windows の例です。
まずは、対象のポートを占有してしまっているプロセスの ID を調べます。

```
D:\> netstat -ao | findstr 10443
  TCP    0.0.0.0:10443          0.0.0.0:0              LISTENING       10420
```

プロセス ID 10420 が犯人だと分かりましたので、今度はどんなプログラムによるプロセスなのかを調べます。

```
D:\> tasklist | findstr 10420
VBoxHeadless.exe             10420 Console                    1     53,948 K
```

VirtualBox 関連のプログラムっぽいので、どうやら殺しても大丈夫そうなことが分かります。

```
D:\> taskkill /F /PID 10420
成功: PID 10420 のプロセスに強制終了のシグナルを送信しました。
```

強引にプロセス削除成功！

