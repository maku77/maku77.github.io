---
title: "Gitメモ: リモートリポジトリに略称を付ける (git remote add)"
url: "p/mhqsei2/"
date: "2010-07-22"
tags: ["git"]
aliases: [/git/server/remote-alias.html]
---

**`git remote add`** コマンドを使うと、リモートリポジトリの URL に短い名前（エイリアス）を付けることができます。
長い URL を毎回入力する必要がなくなり、`git pull` や `git push` の操作が簡単になります。


リモートリポジトリにエイリアスを設定する
----

例えば、以下のように長い URL を指定して `git pull` するのは面倒です。

```console
$ git pull git://example.com/myapp.git main
```

**`git remote add <エイリアス名> <URL>`** としてエイリアス名を登録しておくと、URL の代わりに使えるようになります。

```console
$ git remote add myapp git://example.com/myapp.git
```

これ以降は、エイリアス名で操作できます。

```console
$ git pull myapp main
$ git push myapp main
```

実は、`git clone` でリポジトリを取得すると、クローン元の URL に **`origin`** というエイリアスが自動的に設定されます。
普段 `git push origin main` のように使っている `origin` は、このエイリアスのことです。


登録済みのリモートリポジトリを確認する
----

現在登録されているリモートリポジトリの一覧は、`git remote -v` で確認できます。

```console
$ git remote -v
origin  https://github.com/maku77/myrepo.git (fetch)
origin  https://github.com/maku77/myrepo.git (push)
myapp   git://example.com/myapp.git (fetch)
myapp   git://example.com/myapp.git (push)
```


エイリアスを削除・変更する
----

不要になったエイリアスは **`git remote remove`** で削除できます。

```console
$ git remote remove myapp
```

エイリアス名を変更したい場合は **`git remote rename`** を使います。

```console
$ git remote rename myapp proj_a
```


参考
----

- [`git pull` の引数省略の仕組み](/p/xonap3b/)

