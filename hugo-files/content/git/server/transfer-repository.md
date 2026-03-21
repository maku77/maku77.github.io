---
title: "Gitメモ: Git リポジトリを移行する (git clone --mirror, git push --mirror)"
url: "p/bfxgbf4/"
date: "2014-05-19"
tags: ["git"]
aliases: [/git/server/transfer-repository.html]
---

Git リポジトリを別のサーバに移行するときは、**`git clone --mirror`** を使ってリポジトリの完全なコピーを作成し、新しいリモートに push します。


--mirror オプションとは
----

通常の `git clone` は、デフォルトブランチの作業ツリーを含むリポジトリのコピーを作成します。
一方、**`git clone --mirror`** を使うと、すべてのブランチ、タグ、リモート追跡情報などを含むベアリポジトリ（作業ツリーを持たないリポジトリ）として完全なコピーを作成します。

同様に、**`git push --mirror`** は、ローカルのすべての参照（ブランチ、タグなど）をリモートに反映します。
リモートにだけ存在する参照は削除されるため、ローカルの状態がリモートに完全にコピーされます。

{{% note title="注意" %}}
`git push --mirror` は、移行先リポジトリの既存の参照をすべて上書き・削除します。
移行先として必ず **空のリポジトリ** を用意してください。
{{% /note %}}


GitHub 内のリポジトリを別のリポジトリへ移行する
----

GitHub 内（または異なるサーバ間）でリポジトリを移行する基本的な手順です。
移行先に空のリポジトリ `dst.git` を作成済みだと仮定しています。

{{< code lang="console" title="例: src.git から dst.git への移行" >}}
$ git clone --mirror https://github.com/user/src.git  # ミラーとしてクローン
$ cd src.git
$ git push --mirror https://github.com/user/dst.git   # すべての参照を移行先に push
{{< /code >}}

この方法は、GitHub 内での移行だけでなく、異なるサーバ間の移行（例えば GitLab → GitHub）にも使えます。
すべてのブランチ、タグ、コミット履歴がそのまま移行されます。


GitHub のリポジトリを社内サーバ（イントラ）の Git サーバへ移行する
----

社内サーバに移行する場合は、まず GitHub のリポジトリをミラークローンし、それを社内サーバに配置します。

{{< code lang="console" title="1. GitHub からミラークローンして ZIP にバックアップ" >}}
$ git clone --mirror git@github.com:maku/proj.git
$ zip -r proj.zip proj.git
{{< /code >}}

{{< code lang="console" title="2. 社内サーバ上で ZIP を展開して共有リポジトリとして配置" >}}
$ unzip proj.zip
{{< /code >}}

`git clone --mirror` で作成されたディレクトリはベアリポジトリなので、そのまま共有リポジトリとして使用できます。
あとは、各メンバーがこのリポジトリを `clone` すれば開発を開始できます。

