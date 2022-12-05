---
title: "Linux シェルスクリプト: expect で外部コマンドの出力を待機する"
url: "p/3i3j2hx/"
date: "2018-11-10"
tags: ["Linux"]
description: "expect コマンドを使用すると、任意のコマンドの出力を待ち受けて、自動でそれに対する入力を行うことができます。"
aliases: /linux/io/expect.html
---

expect コマンドを使用すると、任意のコマンドの出力を待ち受けて、自動でそれに応答するといったことを実現できます。

expect の基本的な使い方
----

Linux のコマンド __`expect`__ がインストールされていない場合は、パッケージ管理ツール（`apt` や `yum`）を使用してインストールしてください（macOS には最初からインストールされています）。
まずは、簡単な例として、下記のような簡単なユーザ入力を必要とするスクリプトを自動で動作させてみます。

{{< code lang="bash" title="greet.sh" >}}
#!/bin/bash

echo -n "Enter your name: "
read name
echo "Hello, $name"
{{< /code >}}

これをそのまま実行すると、下記のようにユーザ入力の待ち受け状態となります。

```console
$ ./greet.sh
Enter your name:
```

このような状態になったときに、自動的に入力を行うには、下記のように __`expect -c`__ コマンドを使ったシェルスクリプトを作成します。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

expect -c "
  set timeout 3
  spawn ./greet.sh
  expect \"Enter your name:\"
  send \"Maku\n\"
  interact
"
{{< /code >}}

`expect -c` の後ろのダブルクォートで囲まれた部分には、`expect` で実行するスクリプトを記述します。
この中でダブルクォートを使用したいときは、`\"` のようにエスケープしなければいけないことに注意してください。
スクリプト中の各行は次のような意味を持っています。

- {{< label-code "set timeout 3" >}} 外部プログラムの実行を 3 秒待つ
- {{< label-code "spawn ./greet.sh" >}} 指定した外部プログラム (`./greet.sh`) を実行する
- {{< label-code "expect \"Enter your name:\"" >}} 外部プログラムの出力 (`Enter your name:`) を待つ
- {{< label-code "send \"Maku\n\"" >}} 外部プログラムに Maku と入力して、最後に Enter を入力する
- {{< label-code "interact" >}} 外部プログラムとのインタラクションへ戻る

上記のシェルスクリプトを実行すると、外部プログラム `greet.sh` の入力待ちに対して、自動的に `Maku` という入力を行います。

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
spawn ./greet.sh
Enter your name: Maku
Hello, Maku
{{< /code >}}


コマンドラインからのパスワード入力を自動化する
----

あまりよくない例かもしれませんが、`ssh` や `rsync` コマンドなどのパスワード入力も同様に自動化することができます。
下記のスクリプトを実行すると、`ssh` コマンドがパスワード入力を求めてきたときに、自動でパスワードを入力してログイン処理が完了します。

{{< code lang="bash" title="例: ssh 接続時のパスワード入力を自動化 (autologin.sh)" >}}
#!/bin/bash

expect -c "
  set timeout 3
  spawn ssh username@example.com
  expect \"password:\"
  send \"x5sd6sfc\n\"
  interact
"
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./autologin.sh
spawn ssh username@example.com
username@examle.com's password:
Last login: Sat Nov 10 19:58:51 2018 from hogehoge.ap.example.jp

username$
{{< /code >}}

ログイン後に、何らかのコマンド（例えば `ls -aFl`）を実行するところまで自動化したいのであれば、下記のようにプロンプトの `$` を待ち受けて次の入力を行えばよいでしょう。

```bash
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
```

ちなみに、`rsync` コマンドでファイル転送するときも同様にパスワード入力を自動化できます。

```bash
#!/bin/bash

expect -c "
  spawn rsync -r -h -v --delete public user@example.com:public
  expect \"Password:\"
  send \"abcabcabc\n\"
  interact
"
```

ただし、このような使い方をすると、シェルスクリプトにパスワードをそのまま記述することになるので、ファイルの保存場所には十分に注意してください。
ここでは、プログラムの例としてパスワード入力を扱いましたが、SSH 接続を行うのであれば、通常は [`~/.ssh/config` ファイルによる SSH キー設定](https://maku.blog/p/szajt4d/) を用いた方がよいでしょう。

