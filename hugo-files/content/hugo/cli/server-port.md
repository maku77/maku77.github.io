---
title: "ポート番号を指定して Hugo サーバーを起動する"
url: "p/jj7rcvf/"
date: "2017-08-25"
tags: ["Hugo"]
aliases: /hugo/command/server-port.html
---

Hugo サーバーを `hugo server` コマンドで起動すると、デフォルトでは __1313__ ポートを使用する Web サーバが立ち上がります。
ただし、複数の Hugo サーバーを同時に立ち上げようとすると、2 つめ以降の Hugo サーバーにはランダムなポート番号が割り当てられます（おそらく Private ports である 49152～65535 の範囲のポート番号が使用されます）。

任意のポート番号を使うように指定するには、下記のように __`-p (--port)`__ オプションを使用します。

{{< code lang="console" title="例: ポート番号 51234 で Hugo サーバーを立ち上げる" >}}
$ hugo server -p 51234
{{< /code >}}

たくさんの Hugo サーバーを同時に起動するようなケースでは、それぞれのサイトでどのポート番号を使用するかを決めておくとよいでしょう。

{{< code lang="console" title="例: 3 つの Hugo サーバーを同時に立ち上げる" >}}
$ hugo server -p 50001 -s ~/mysite1
$ hugo server -p 50002 -s ~/mysite2
$ hugo server -p 50003 -s ~/mysite3
{{< /code >}}

上記のように立ち上げた Web サイトは、それぞれ下記のようなアドレスでアクセスできるようになります。

- `http://localhost:50001/`
- `http://localhost:50002/`
- `http://localhost:50003/`

