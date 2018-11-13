---
title: "expect で ssh や rsync のパスワード入力を自動化する"
date: "2018-11-10"
description: "expect コマンドを使用すると、任意のコマンドの出力を待ち受けて、自動でそれに対する入力を行うことができます。"
---

expect の基本的な使い方
----

Linux のコマンド `expect` がインストールされていない場合は、yum コマンドなどを使用してインストールしてください（macOS Mojave には最初からインストールされていました）。

まずは、簡単な例として、下記のような簡単なユーザ入力を必要とするスクリプトを自動で動作させてみます。

#### greet.sh

~~~ shell
#!/bin/bash

echo -n "Enter your name: "
read name
echo "Hello, $name"
~~~

これを単純に実行すると、下記のようにユーザ入力の待ち受け状態となります。

~~~
$ ./greet.sh
Enter your name:
~~~

このような状態になったときに、自動的に入力を行うには、下記のように `expect -c` コマンドを使ったシェルスクリプトを作成します。

#### sample.sh

~~~ sh
#!/bin/bash

expect -c "
  set timeout 3
  spawn ./greet.sh
  expect \"Enter your name:\"
  send \"Maku\n\"
  interact
"
~~~

`expect -c` の後ろのダウブルクォートで囲まれた部分には、`expect` で実行するスクリプトを記述します。
この中でダブルクォートを使用したいときは、`\"` のようにエスケープしなければいけないことに注意してください。
スクリプト中の各行は次のような意味を持っています。

- `set timeout 3` -- 外部プログラムの実行を3秒待つ
- `spawn ./greet.sh` -- 指定した外部プログラム (./greet.sh) を実行する
- `expect \"Enter your name:\" -- 外部プログラムの出力 (Enter your name:) を待つ
- `send \"Maku\n\" -- 外部プログラムに Maku と入力して、最後に Enter を入力する
- `interact` -- 外部プログラムとのインタラクションへ戻る

上記のシェルスクリプトを実行すると、外部プログラム `greet.sh` の入力待ちに対して、自動的に `Maku` という入力を行います。

#### 実行結果

~~~
$ ./sample.sh
spawn ./greet.sh
Enter your name: Maku
Hello, Maku
~~~


ssh 接続や rsync 接続のパスワード入力を自動化する
----

`ssh` や `rsync` コマンドなどのパスワード入力も同様に自動化することができます。
下記のスクリプトを実行すると、`ssh` コマンドがパスワード入力を求めてきたときに、自動でパスワードを入力してログイン処理が完了します。

#### ssh 接続時のパスワード入力を自動化 (autologin.sh)

~~~ shell
#!/bin/bash

expect -c "
  set timeout 3
  spawn ssh username@example.com
  expect \"password:\"
  send \"x5sd6sfc\n\"
  interact
"
~~~

#### 実行結果

~~~
$ ./autologin.sh
spawn ssh username@example.com
username@examle.com's password:
Last login: Sat Nov 10 19:58:51 2018 from hogehoge.ap.example.jp

[username ~]$
~~~

ログイン後に、何らかのコマンド（例えば `ls -aFl`）を実行するところまで自動化したいのであれば、下記のようにプロンプトの `$` を待ち受けて次の入力を行えばよいでしょう。

~~~ shell
#!/bin/bash

expect -c "
  set timeout 3
  spawn ssh username@example.com
  expect \"password:\"
  send \"x5sd6sfc\n\"

  expect \"$\"
  send \"ls -aFl\n\"

  interact
"
~~~

ちなみに、`rsync` コマンドでファイル転送する時なども同様にパスワード入力を自動化できます。

~~~ shell
#!/bin/bash

expect -c "
  spawn rsync -r -h -v --delete public user@example.com:public
  expect \"Password:\"
  send \"abcabcabc\n\"
  interact
"
~~~

ただし、このような使い方をすると、シェルスクリプトなどにパスワードをそのまま記入することになるので、ファイルの扱いには十分に注意しましょう。

