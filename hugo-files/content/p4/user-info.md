---
title: "Perforceメモ: Perforce ユーザ ID から本名やメールアドレスを調べる (p4 users)"
url: "p/pgrvsmf/"
date: "2008-04-04"
tags: ["perforce"]
aliases: ["/p4/user-info.html"]
---

`p4 users` コマンドを使用すると、Perforce に登録されているすべてのユーザの情報を確認することができます。
パラメータでユーザ名を指定すれば、そのユーザの情報だけを表示できます。

```console
$ p4 users yamada
yamada <Taro.Yamada@example.com> (Taro YAMADA) accessed 2008/04/04
```

ワイルドカードでユーザ名を絞り込んで表示することもできます。
例えば、次のようにすると、ユーザ名が y で始まるユーザの一覧が表示されます。

```console
$ p4 users "y*"
```
