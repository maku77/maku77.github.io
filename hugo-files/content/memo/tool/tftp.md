---
title: "TFTP の使い方（TFTP によるファイル転送）"
url: "p/46b5wxe/"
date: "2009-05-25"
tags: ["memo"]
aliases: ["/memo/tool/tftp.html"]
---

TFTP の特徴
----

- Trivial File Transfer Protocol の略。
- https://www.ietf.org/rfc/rfc1350.txt
- 非常に軽量なので、ブートストラップ用のプロトコルとしてよく使われる。


TFTP クライアントを使ったファイル転送の例
----

{{< code lang="console" title="例: ファイル取得" >}}
$ tftp 192.168.0.1 get sample.txt
Transfer successful: 234 bytes in 1 second, 234 bytes/s
{{< /code >}}

{{< code lang="console" title="例: ファイル送信" >}}
$ tftp 192.168.0.1 put sample.txt
Transfer successful: 234 bytes in 1 second, 234 bytes/s
{{< /code >}}

{{< code lang="console" title="例: バイナリモードで転送したい場合は -i オプション" >}}
$ tftp -i 192.168.0.1 get sample.data
{{< /code >}}

