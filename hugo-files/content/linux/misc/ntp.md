---
title: "Linuxメモ: NTP でシステム時刻を設定する"
url: "p/4zi9s4y/"
date: "2010-07-22"
tags: ["linux"]
aliases: /linux/settings/ntp.html
---

（Ubuntu 9.04 で確認）

NTP による時刻設定を有効にする
----

まず、`ntp` パッケージをインストールします。

```console
$ sudo apt-get install ntp
```

使用する NTP サーバは、下記のファイルで指定します。

{{< code title="/etc/ntp.conf" >}}
server ntp.ubuntu.com
{{< /code >}}

上記のような行を編集して、任意の NTP サーバのアドレスを指定してください。
複数の NTP サーバを指定することもできます。

最後に NTP daemon を再起動すれば NTP による自動時刻設定が有効になります。

```console
$ sudo /etc/init.d/ntp restart
```

NTP の動作確認
----

```console
$ sudo ntpq -p
```

上記のコマンドで、サーバ名が表示されれば OK です。

手動で NTP サーバの時刻を取得
----

`ntpdate` コマンドを使用して、明示的に OS のシステム時刻を NTP サーバの時刻に同期させることができます。

```console
$ sudo ntpdate -u ntp.ubuntu.com
```

