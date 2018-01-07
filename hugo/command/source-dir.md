---
title: カレントディレクトリを気にせずに hugo コマンドを実行する
date: "2017-08-25"
---

`hugo` コマンドを実行するときに、`-s (--source)` オプションを使用することで、ソースディレクトリのパスを指定して起動することができます。

#### 例: ~/mysite 以下のソースコードを使って Hugo サーバーを起動する

~~~
$ hugo server -s ~/mysite
~~~

#### 例: ~/mysite 以下の記事を新規作成する

~~~
$ hugo new sample.md -s ~/mysite
~~~

上記の例では、`-s ~/mysite` というソースディレクトリの指定を末尾に持ってきましたが、`hugo` の直後に指定しても動作するようです。
これを利用して、下記のようなコマンドエイリアスを作成しておけば、どのディレクトリからでもすぐに記事作成を始められて便利です。

#### ~/.bash_profile

~~~ shell
alias hugo-mysite=hugo -s ~/mysite
~~~

例えば、下記のように使用することができます。

~~~
$ hugo-mysite new sample.md  # 記事の作成
$ hugo-mysite server         # Hugo サーバー起動
$ hugo-mysite                # アップロード用コンテンツ生成 (~/mysite/public/)
~~~

