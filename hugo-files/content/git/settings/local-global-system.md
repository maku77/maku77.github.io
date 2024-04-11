---
title: "Git 設定のスコープ (local/global/system) を理解する"
url: "p/af7q7n3/"
date: "2014-05-25"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/settings/local-global-system.html
---

Git 設定の 3 つのスコープ
----

`git config` による設定のスコープは 3 種類あり、スコープが狭くなるほど参照時の優先度は高くなります。
下記はそれぞれのスコープでの設定方法を、優先度の高い順に示しています。
カッコの中のファイル名は、コマンドを実行したときの設定値の保存先です。

```console
$ git config --local ...   # 各リポジトリごとの設定 (<repo>/.git/config)（優先度:高）
$ git config --global ...  # 現在のユーザの共通設定 (~/.gitconfig)
$ git config --system ...  # システム内の共通設定 (/etc/gitconfig など)（優先度:低）
```

例えば、global 設定で `user.name` が `Ichiro` になっていても、local 設定が `Jiro` になっていれば、`Jiro` の方が優先的に使用されます。
プロジェクト（リポジトリ）ごとに固有の設定をする場合は、local なスコープで設定を行うとよいでしょう。
この場合、プロジェクトの作業ツリーのルートにある __`.git/config`__ ファイルに設定が保存されます。

例えば、次のようにして local 設定の `user.name` と `user.email` を変更することができます。

```console
$ git config --local user.name maku77
$ git config --local user.email maku77@example.com
```

上記コマンドを実行した後で、`<リポジトリ>/.git/config` ファイルを開くと次のように設定情報が保存されていることが分かります。

{{< code lang="ini" title="<リポジトリ>/.git/config（抜粋）" >}}
[user]
	name = maku77
	email = maku77@example.com
{{< /code >}}

- 参考: [Git の設定値がどのファイルで設定されているか調べる (`config --show-origin`)](/p/msds6iv/)


それぞれの設定ファイルをコマンドで開く (`git config --edit`)
----

各スコープの設定ファイルは、__`git config --edit`__ コマンドで開くことができます。

```console
$ git config --edit           # リポジトリの設定ファイルを開く（local 設定）
$ git config --edit --local   # （同上）
$ git config --edit --global  # ユーザーの設定ファイルを開く（global 設定）
$ git config --edit --system  # システムの設定ファイルを開く（system 設定）
```

使用するエディタは、__`core.editor`__ 設定や、__`GIT_EDITOR`__ 環境変数などで指定できます。

- 参考: [コミット時にコメント記述に使用するエディタを設定する (`core.editor`)](/p/cqjv7wv/)


Windows の場合のホームディレクトリ (`USERPROFILE`, `HOME`)
----

global スコープの設定は、ユーザのホームディレクトリの __`.gitconfig`__ ファイルに設定値が保存されますが、Windows の場合のホームディレクトリは、デフォルトで __`USERPROFILE`__ 環境変数で取得できるディレクトリ（通常は `C:\Users\<ユーザー名>`）が使用されます。

```
C:\> echo %USERPROFILE%
C:\Users\maku
```

例えば、上記の場合は `C:\Users\maku` が Git のホームディレクトリとして使用されるので、global スコープの設定は、`C:\Users\maku\.gitconfig` に保存されます。
ただし、__`HOME`__ 環境変数が設定されている場合は、そちらのディレクトリがホームディレクトリとして使用されます。

