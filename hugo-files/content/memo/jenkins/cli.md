---
title: "Jenkins CLI を使ってコマンドラインから Jenkins を操作する"
date: 2016-08-23
url: "p/j3ujkhd/"
tags: ["memo"]
aliases: ["/memo/jenkins/cli.html"]
---


Jenkins CLI とは
----
[Jenkins CLI (Jenkins Command Line Interface)](https://wiki.jenkins-ci.org/display/JENKINS/Jenkins+CLI) クライアントを使用すると、コマンドラインから Jenkins サーバを操作することができるようになります。
例えば、Job の設定変更や、ビルドのトリガなどをコマンドラインから行うことができます。
また、Groovy のスクリプトを流し込んで Jenkins サーバ上で実行することも行えるため、Jenkins に対して行う作業のほとんどを自動化することができるようになっています。

Jenkins CLI のインストール
----

CLI の実体は、Jenkins サーバからダウンロードできる `jenkins-cli.jar` です。
このファイルは任意の Jenkins サーバからダウンロードできますが、一応バージョンの問題が発生しないように、操作対象としている Jenkins サーバからダウンロードするのがよいでしょう。

ローカルホストで Jenkins サーバを稼働しているのであれば、下記のアドレスからダウンロードできます。

* `http://localhost:8080/jnlpJars/jenkins-cli.jar`


Jenkins CLI を使ってみる
----

Jenkins CLI は、下記のようなフォーマットで使用します。

```console
$ java -jar jenkins-cli.jar -s <Jenkinsサーバアドレス> <コマンド>
```

下記は、Jenkins CLI を使ってログイン、ヘルプの表示、ログアウトを実行する例です。
Jenkins CLI の各種コマンドを実行するには、最初に `login` コマンドを使用して、Jenkins ユーザ名を指定してログインしておく必要があります。

```console
$ java -jar jenkins-cli.jar -s http://localhost:8080 login --username yourname
Password: （指定した Jenkins ユーザのパスワードを入力）

$ java -jar jenkins-cli.jar -s http://localhost:8080 help
（CLI コマンドのヘルプが表示される）

$ java -jar jenkins-cli.jar -s http://localhost:8080 logout
```

接続時に Java の Exception が発生する場合は、[Jenkins CLI](https://wiki.jenkins-ci.org/display/JENKINS/Jenkins+CLI) のサイトでエラーメッセージを検索して対応しましょう。


Jenkins CLI を使いやすくする
----

Jenkins CLI コマンドを実行するときは、`jenkins-cli.jar` ファイルを指定する必要があったり、Jenkins サーバのアドレスを指定する必要があったりして、そのままでは若干扱いにくいです。
下記のような方法で、Jenkins CLI を使いやすくすることができます。

### 環境変数 JENKINS_URL で Jenkins のアドレスを指定する方法

環境変数 `JENKINS_URL` に Jenkins サーバのアドレス（`http://localhost:8080` など）を指定しておくことで、Jenkins CLI を使用するときに毎回 `-s` オプションでアドレスを指定する必要がなくなります。

{{< code lang="bash" title="~/.bash_profile" >}}
export JENKINS_URL=http://localhost:8080
{{< /code >}}

### 専用のコマンドを作ってしまう方法

下記は Windows のバッチファイルの例ですが、このファイルと `jenkins-cli.jar` ファイルを同じディレクトリに入れて、そのディレクトリに PATH を通しておけば、どのディレクトリからでも Jenkins CLI コマンドを実行できるようになります。

{{< code lang="bat" title="jenkins-cli-localhost.cmd" >}}
@echo off
set jarfile="%~dp0%jenkins-cli.jar"
set url="http://localhost:8080"
java -jar %jarfile% -s %url% %*
{{< /code >}}

ここでは、ローカルホスト上の Jenkins サーバにアクセスするためのコマンドだということを示すために、ファイル名に `-localhost` というサフィックスを付けています。
このような命名規則にしておけば、複数の Jenkins サーバを扱っている場合にも対応できます。

{{< code title="使用例" >}}
C:\> jenkins-cli-localhost login --username yourname
C:\> jenkins-cli-localhost help
C:\> jenkins-cli-localhost logout
{{< /code >}}

