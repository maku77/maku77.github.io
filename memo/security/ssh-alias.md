---
title: SSH 接続用のエイリアスを設定する (~/.ssh/config)
created: 2014-05-31
---

~~~
$ ssh maku@dev.example.com -p 22000
~~~

と同じことを、

~~~
$ ssh dev
~~~

のようなエイリアス名指定だけで実行できるようにするには、以下のような設定ファイルを用意しておきます。

#### ~/.ssh/config

~~~
Host dev
    HostName dev.example.com
    Port 22000
    User maku
~~~

SSH クライアントのより詳細な設定方法は、下記のマニュアルで確認できます。

~~~
$ man ssh_config
~~~

