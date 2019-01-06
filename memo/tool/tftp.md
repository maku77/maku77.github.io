---
title: "TFTP の使い方（TFTP によるファイル転送）"
date: "2009-05-25"
---

TFTP の特徴
----

- Trivial File Transfer Protocol の略。
- http://www.ietf.org/rfc/rfc1350.txt
- 非常に軽量なので、ブートストラップ用のプロトコルとしてよく使われる。

TFTP クライアントを使ったファイル転送の例
----

#### 例: ファイル取得

```
$ tftp 192.168.0.1 get sample.txt
Transfer successful: 234 bytes in 1 second, 234 bytes/s
```

#### 例: ファイル送信

```
$ tftp 192.168.0.1 put sample.txt
Transfer successful: 234 bytes in 1 second, 234 bytes/s
```

#### 例: バイナリモードで転送したい場合は -i オプション

```
$ tftp -i 192.168.0.1 get sample.data
```

