---
title: "Perforceメモ: Perforce ユーザー ID から本名やメールアドレスを調べる (p4 users)"
url: "p/pgrvsmf/"
date: "2008-04-04"
tags: ["perforce"]
aliases: ["/p4/user-info.html"]
---

`p4 users` コマンドを使用すると、Perforce に登録されているすべてのユーザーの情報を確認できます。
パラメータでユーザー名を指定すれば、そのユーザーの情報だけを表示できます。

```console
$ p4 users yamada
yamada <Taro.Yamada@example.com> (Taro YAMADA) accessed 2008/04/04
```

ワイルドカードでユーザー名を絞り込んで表示することもできます。
例えば、次のようにすると、ユーザー名が y で始まるユーザーの一覧が表示されます。

```console
$ p4 users "y*"
```
