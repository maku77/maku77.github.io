---
title: SSH 鍵の作成と登録
created: 2008-08-13
---

SSH によるマシン間接続を有効にするには、接続元のマシンで SSH 鍵（公開鍵＋秘密鍵）を作成し、接続先のマシンに公開鍵を登録する必要があります。
ここでは OpenSSH を使用した例を紹介します。

SSH 鍵の作成
----

```
$ ssh-keygen -t dsa      # DSA 鍵を作成する場合（SSH2 の環境では必ず使えるが、1024 bits しか選べないことが多い）
$ ssh-keygen -t rsa      # RSA 鍵を作成する場合（4096 bits の長い鍵長を選べば強度は出るが遅くなる）
$ ssh-keygen -t ecdsa    # ECDSA 鍵を作成する場合（楕円曲線暗号。少ない鍵長で強度あり。ED25519 より普及している）
$ ssh-keygen -t ed25519  # Ed25519 鍵を作成する場合（楕円曲線暗号。少ない鍵長で強度あり。ECDSA より高速）
$ ssh-keygen -t rsa1     # RSA1 鍵を作成する場合（SSH1 しか使えないレガシー環境用。強度弱いので使わない方がよい）
```

米国 NIST の提示している暗号化方式としては、2031 年以降は下記のものしか使えなくなります（参考: [米国における暗号技術をめぐる動向: 図表10](https://www.ipa.go.jp/files/000055177.pdf)）。

* RSA/DSA 3072 bit 以上
* ECDSA 256 bit 以上

GitHub でも SSH 鍵の作成方法として、RSA 4096 bits の作成例が示されるようになりました（2016年確認）。
これらを踏まえると、下記のような感じで作成しておくのがよいでしょう。

```
$ ssh-keygen -t rsa -b 4096
$ ssh-keygen -t ecdsa -b 256
```

Ed25519 が使える環境であれば、ECDSA よりも高速なので、そちらを使うのがよいかもしれません。


SSH 鍵の登録
----

RSA 鍵を作成済みだとして、公開鍵 (`id_rsa.pub`) を接続先の Linux マシンに登録するには、接続先の Linux マシン上で下記のように設定します。
接続元クライアント（の公開鍵）の一覧を保持する `~/.ssh/authorized_keys2` ファイルが存在しない場合は、最初に作成しておく必要があります。

```
$ mkdir -m 700 ~/.ssh  # ディレクトリがなければ作成しておく
$ touch ~/.ssh/authorized_keys2
$ chmod 600 ~/.ssh/authorized_keys2
```

次に、そのファイルの末尾に、接続元ライアントの公開鍵を追記します。

```
$ cat id_rsa.pub >> ~/.ssh/authorized_keys2
```

これで、秘密鍵 (`id_rsa`) を使って接続できるようになります。


作成済みの SSH 鍵の種類を確認する
----

すでに存在する SSH 鍵ファイルの種類を確認する場合も、`ssh-keygen` コマンドを使用できます。
指定する鍵ファイルは、秘密鍵でも公開鍵でも構いません。
下記は OpenSSH 7.1 の ssh-keygen で作成した各種 SSH キーの種類を確認したときのサンプルです。

```
$ ssh-keygen -l -f ~/.ssh/id_dsa.pub
1024 SHA256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx yourname@example.com (DSA)

$ ssh-keygen -l -f ~/.ssh/id_rsa.pub
2048 SHA256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx yourname@example.com (RSA)

$ ssh-keygen -l -f ~/.ssh/id_ecdsa.pub
256 SHA256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx yourname@example.com (ECDSA)

$ ssh-keygen -l -f ~/.ssh/id_ed25519.pub
256 SHA256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx yourname@example.com (ED25519)
```

Windows PC の場合は、`~/.ssh` の部分は `%USERPROFILE%\.ssh` と適宜読み替えてください。

