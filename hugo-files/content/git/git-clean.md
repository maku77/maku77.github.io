---
title: "Gitメモ: git clean で追跡されていないファイルを削除する (git clean -df)"
url: "p/scqf3sw/"
date: "2010-08-27"
tags: ["git"]
aliases: [/git/git-clean.html]
---

Git 管轄下にないファイルを削除する
----

`git status` コマンドを実行したときに **Untracked files** の欄に表示されるファイルは、Git の管轄下にないファイル（追跡されていないファイル）です。
これらのファイルをすべて削除するには、対象のディレクトリ内で以下のコマンドを実行します。

```console
$ git clean -f
```

空になったディレクトリも同時に削除するには、さらに `-d` オプションを指定します。

```console
$ git clean -df
```

`git clean` で削除したファイルは元に戻せないので、このコマンドは慎重に実行する必要があります。

{{% note title="なぜいちいち -f オプションが必要なのか？" %}}
`make clean` とタイプしようとして、間違えて `git clean` とタイプしてしまうと大変なことになるので、`git clean` コマンドは `-f` オプションを指定しないとエラーで止まるようになっているらしいです。
```console
$ git clean
fatal: clean.requireForce defaults to true and neither -i, -n, nor -f given; refusing to clean
```
{{% /note %}}


git clean でどのファイルが削除されるか確認する
----

実際に `git clean -f` を実行する前に、どのファイルが消されるのかを `git clean -n` で確認することができます。

```console
$ git clean -n
```

同様に、空のディレクトリを消す `-d` オプションを同時に指定した場合に、どのファイルとディレクトリが削除されるかは以下のように調べることができます。

```console
$ git clean -nd
```

