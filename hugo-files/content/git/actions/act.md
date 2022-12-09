---
title: "GitHub Actions のワークフローをローカルで実行する (act)"
url: "p/iudtbr8/"
date: "2022-12-09"
tags: ["GitHub"]
---

act とは？
----

__act コマンド__ は、Casey Lee 氏 ([@nektos](https://github.com/nektos/)) が作成した、GitHub Actions ワークフローをローカル実行するためのコマンドです。

- [nektos/act: Run your GitHub Actions locally 🚀](https://github.com/nektos/act)

通常、GitHub Actions のワークフローを実行するには、ワークフローファイル (`.github/workflows/*.yml`) を作成して、GitHub へコミット＆プッシュする必要がありますが、`act` コマンドを使うと、ローカルのワークフローファイルをそのまま実行できます。
ローカルでの実行を主目的とした、タスクランナーとしての利用も可能です。

`act` コマンドは、[GitHub Blog でも紹介されています](https://github.blog/2020-07-03-github-action-hero-casey-lee/)。


Docker と act のインストール
----

`act` はワークフローの実行環境として Docker コンテナを利用するので、Docker 環境はあらかじめインストールしておいてください。
Docker Desktop をインストールすれば、簡単に Docker 環境が整います。

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

`act` コマンドは Go 言語で実装されており、各 OS 用の実行ファイルが提供されています。
下記の公式ページの説明に従って、OS ごとのパッケージ管理コマンドでインストールしてしまうのが簡単です。

- [nektos/act: Run your GitHub Actions locally 🚀](https://github.com/nektos/act)

例えば、macOS であれば `brew install act`、Windows であれば `choco install act-cli` などでインストールできます。
インストールが終わって、次のように実行できるようになっていれば準備完了です。

```console
$ act --version
act version 0.2.34
```


act でワークフローを実行する
----

### ワークフローファイル (.yml) の準備

ワークフローファイルがないと始まらないので、まずは GitHub で管理されているリポジトリにワークフローファイルを用意します。
既存のワークフローファイルがなければ、次のように適当に作成してください。
`.github/workflows/*.yml` というパスで配置すれば、YAML ファイル名は何でも構いません。

{{< code lang="yaml" title=".github/workflows/sample.yml" >}}
name: GitHub Actions Demo
run-name: ${{ github.actor }} is testing out GitHub Actions 🚀
on: [push]
jobs:
  Explore-GitHub-Actions:
    runs-on: ubuntu-latest
    steps:
      - run: echo "🎉 The job was automatically triggered by a ${{ github.event_name }} event."
      - run: echo "🐧 This job is now running on a ${{ runner.os }} server hosted by GitHub!"
      - run: echo "🔎 The name of your branch is ${{ github.ref }} and your repository is ${{ github.repository }}."
      - name: Check out repository code
        uses: actions/checkout@v3
      - run: echo "💡 The ${{ github.repository }} repository has been cloned to the runner."
      - run: echo "🖥️ The workflow is now ready to test your code on the runner."
      - name: List files in the repository
        run: ls ${{ github.workspace }}
      - run: echo "🍏 This job's status is ${{ job.status }}."
{{< /code >}}

お試し用の GitHub リポジトリの準備が面倒な場合は、下記リポジトリを clone してください。

```console
$ git clone https://github.com/maku77/p-iudtbr8
```

### act コマンドの実行

`act` コマンドは、__デフォルトで `push` イベントを発生させる__ ので、上記のワークフロー定義のように、`on: [push]` トリガーが設定されたものが実行されます。
`act` の初回起動時には、実行環境とする Docker イメージの選択肢が表示されます。
今回のような簡単な処理であれば、一番小さなイメージ (Micro) を選択しておけば OK です。

```console
$ act
? Please choose the default image you want to use with act:

  - Large size image: +20GB Docker image, includes almost all tools used on GitHub Actions (IMPORTANT: currently only ubuntu-18.04 platform is available)
  - Medium size image: ~500MB, includes only necessary tools to bootstrap actions and aims to be compatible with all actions
  - Micro size image: <200MB, contains only NodeJS required to bootstrap actions, doesn't work with all actions

Default image and other options can be changed manually in ~/.actrc (please refer to https://github.com/nektos/act#configuration for additional information about file structure)  [Use arrows to move, type to filter, ? for more help]
  Large
  Medium
> Micro
```

実行用の Docker イメージのダウンロードが完了すると、ワークフロー内のジョブが実行されます。

{{% accordion title="act コマンドの出力例" %}}
```
[GitHub Actions Demo/Explore-GitHub-Actions] 🚀  Start image=node:16-buster-slim
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker pull image=node:16-buster-slim platform= username= forcePull=false
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker create image=node:16-buster-slim platform= entrypoint=["/usr/bin/tail" "-f" "/dev/null"] cmd=[]
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker run image=node:16-buster-slim platform= entrypoint=["/usr/bin/tail" "-f" "/dev/null"] cmd=[]
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "🎉 The job was automatically triggered by a push event."
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/0] user= workdir=
| 🎉 The job was automatically triggered by a push event.
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "🎉 The job was automatically triggered by a push event."
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "🐧 This job is now running on a Linux server hosted by GitHub!"
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/1] user= workdir=
| 🐧 This job is now running on a Linux server hosted by GitHub!
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "🐧 This job is now running on a Linux server hosted by GitHub!"
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "🔎 The name of your branch is refs/heads/main and your repository is maku77/p-iudtbr8."
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/2] user= workdir=
| 🔎 The name of your branch is refs/heads/main and your repository is maku77/p-iudtbr8.
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "🔎 The name of your branch is refs/heads/main and your repository is maku77/p-iudtbr8."
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main Check out repository code
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker cp src=/mnt/d/y/gitwork/maku77/p-iudtbr8/. dst=/mnt/d/y/gitwork/maku77/p-iudtbr8
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main Check out repository code
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "💡 The maku77/p-iudtbr8 repository has been cloned to the runner."
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/4] user= workdir=
| 💡 The maku77/p-iudtbr8 repository has been cloned to the runner.
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "💡 The maku77/p-iudtbr8 repository has been cloned to the runner."
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "🖥️ The workflow is now ready to test your code on the runner."
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/5] user= workdir=
| 🖥️ The workflow is now ready to test your code on the runner.
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "🖥️ The workflow is now ready to test your code on the runner."
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main List files in the repository
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/6] user= workdir=
| README.md
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main List files in the repository
[GitHub Actions Demo/Explore-GitHub-Actions] ⭐ Run Main echo "🍏 This job's status is success."
[GitHub Actions Demo/Explore-GitHub-Actions]   🐳  docker exec cmd=[bash --noprofile --norc -e -o pipefail /var/run/act/workflow/7] user= workdir=
| 🍏 This job's status is success.
[GitHub Actions Demo/Explore-GitHub-Actions]   ✅  Success - Main echo "🍏 This job's status is success."
[GitHub Actions Demo/Explore-GitHub-Actions] 🏁  Job succeeded
```
{{% /accordion %}}

何らかのコマンドが足りないというエラーが出たら、Docker の実行イメージを変えて実行してみてください。


使用する Docker イメージを変更する
----

`act` のワークフロー実行に使用する Docker イメージを切り替えたくなったら、[公式サイトの configuration の項目](https://github.com/nektos/act#configuration) に従って設定してください。
例えば、Medium Docker Image (ubuntu-latest) を使いたくなった場合は、カレントディレクトリ、あるいはホームディレクトリに __`.actrc`__ というファイルを作成して、次のように記述すれば OK です。
これは、ワークフローファイルの中で、`runs-on: ubuntu-latest` と指定されたときに、具体的にどの Docker イメージを使用するかを示しています。

{{< code title="~/.actrc" >}}
-P ubuntu-latest=catthehacker/ubuntu:act-latest
{{< /code >}}

このファイルは、`act` コマンドに渡すデフォルトのオプションを列挙したものです。
`act` コマンド実行時に次のように直接オプション指定しても OK です。

```console
$ act -P ubuntu-latest=catthehacker/ubuntu:act-latest
```


いろんな使い方
----

### push 以外のイベントを発生させる

```console
$ act pull_request
```

`act` コマンドは、最初の引数でイベント名を受け取ります（デフォルトは `pull`）。
例えば上記のように実行すると、`on: [pull_request]` と定義されているワークフローが実行されます。

### ワークフローの一覧

```console
$ act --list
Stage  Job ID                  Job name                Workflow name        Workflow file  Events
0      log-the-inputs             log-the-inputs             dispatch.yml         dispatch.yml   workflow_dispatch
0      specific_review_requested  specific_review_requested  pull.yml             pull.yml       pull_request
0      Explore-GitHub-Actions     Explore-GitHub-Actions     GitHub Actions Demo  sample.yml     push
```

`act` コマンドの __`-l (--list)`__ オプションで、ワークフローの定義一覧を表示できます（要するに、`.github/workflows` 以下の `.yml` ファイルの内容の一覧です）。
トリガーとなるイベントの一覧もここで確認できます。

### シークレットを渡す

```console
$ act -s MY_SECRET1=value1 -s MY_SECRET2=value2
$ act --secret-file my.secrets
```

GitHub 上で設定するシークレット変数をシミュレートするために、__`-s (--secret)`__ オプションを使用できます。
あるいは、__`--secret-file`__ オプションで、キー＆バリュー情報を列挙したシークレットファイルを読み込むことができます（このオプションを指定しなくても、`act` はデフォルトで `.secrets` という名前のファイルを読み込みます）。
シークレットファイルのフォーマットは `.env` と同等です。

{{< code lang="env" title="my.secrets" >}}
# この行はコメント
MY_SECRET1=value1
MY_SECRET2=value2
{{< /code >}}

