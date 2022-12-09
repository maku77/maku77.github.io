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
        run: |
          ls ${{ github.workspace }}
      - run: echo "🍏 This job's status is ${{ job.status }}."
{{< /code >}}

お試し用の GitHub リポジトリの準備が面倒な場合は、下記リポジトリを clone してください。

```console
$ git clone https://github.com/maku77/p-iudtbr8
```

`act` コマンドは、__デフォルトで `push` イベントを発生させる__ ので、上記のワークフローのように、`on: [push]` トリガーが設定されたジョブが起動します。
`act` の初回起動時には、実行環境とする Docker イメージの選択肢が表示されます。
今回のテスト実行であれば、一番小さなイメージ (Micro) を選択しておけば OK です。

```
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

