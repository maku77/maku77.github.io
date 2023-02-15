---
title: "カレントディレクトリを気にせずに hugo コマンドを実行する"
url: "p/wdyk5n7/"
date: "2017-08-25"
tags: ["Hugo"]
aliases: /hugo/command/source-dir.html
---

`hugo` コマンドを実行するときに、__`-s (--source)`__ オプションを使用することで、ソースディレクトリのパスを指定して起動することができます。

{{< code lang="console" title="例: ~/mysite 以下のソースコードを使って Hugo サーバーを起動する" >}}
$ hugo server -s ~/mysite
{{< /code >}}

{{< code lang="console" title="例: ~/mysite 以下の記事を新規作成する" >}}
$ hugo new sample.md -s ~/mysite
{{< /code >}}

上記の例では、`-s ~/mysite` というソースディレクトリの指定を末尾に持ってきましたが、`hugo` の直後に指定しても動作するようです。
これを利用して、下記のようなコマンドエイリアスを作成しておけば、どのディレクトリからでもすぐに記事作成を始められて便利です。

{{< code lang="bash" title="~/.bash_profile" >}}
alias hugo-mysite=hugo -s ~/mysite
{{< /code >}}

例えば、下記のように使用することができます。

```console
$ hugo-mysite new sample.md  # 記事の作成
$ hugo-mysite server         # Hugo サーバーを起動
$ hugo-mysite                # サイトをビルド（~/mysite/public に出力）
```

