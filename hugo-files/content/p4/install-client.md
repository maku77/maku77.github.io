---
title: "Perforceメモ: Perforce クライアントのインストールと初期設定"
url: "p/vrgwxff/"
date: "2006-06-16"
tags: ["perforce"]
aliases: ["/p4/install-client.html"]
---

## Perforce クライアント p4、p4v のインストール

Linux での Perforce クライアントのインストールは、p4（CUI 版クライアント）、p4v（GUI 版クライアント）をダウンロードし、パスの通った場所に置くだけです。
Windows の場合はインストーラが用意されています。

```console
$ su
# chmod +x p4
# cp p4 /usr/local/bin
```

## Perforce クライアントの初期設定

`~/.bash_profile` に環境変数を設定しておきます。

{{< code lang="bash" title=".bash_profile" >}}
export P4PORT=192.168.100.1:1666  # 接続先
export P4USER=joe                 # 接続用アカウント
export P4PASSWD=xxxxxx            # 接続用パスワード
export P4CLIENT=joe.test          # 接続時にデフォルトで使用するクライアント設定名（新規のものでも可）
export P4CHARSET=utf8             # 文字セット
export P4EDITOR=gvim              # 設定で使用するエディタ
{{< /code >}}

Windows の場合は、`p4 set` コマンドを使用して、上記の設定をレジストリに保存します（パスワードは暗号化されて保存されます）。
以下のように実行して Perforce サーバの情報が表示されれば接続はできるようになっています。

```console
$ . ~/.bash_profile
$ p4 info
```

次に、`p4 client` コマンドを使用して、クライアント情報を設定します。
ここでクライアント名を省略すると、`P4CLIENT` 変数に設定したクライアント名が使用されます。

```console
$ p4 client
```

最低限、ローカルのワーキングディレクトリのルートパスを示す *Root:* の情報だけは設定しておきましょう。
`p4 client` コマンドによって開かれたエディタを閉じると、

```
Client <ClientName> saved.
```

と表示され、サーバにクライアント情報が登録されます。
あとは、次のようにしてディポ上のファイルをローカルに取得できます。

```console
$ p4 sync //depot/project1/...
```
