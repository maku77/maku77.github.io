---
title: Gradle を Mac OSX にインストールする
date: "2014-06-01"
---

ここでは、Groovy enVironment Manager (GVM) を使って gradle コマンドをインストールします。
GVM を使うと、複数バージョンの Gradle を管理することができます。

1. GVM のインストール

```sh
$ curl -s get.gvmtool.net | bash
```

2. GVM で gradle のインストール

```sh
$ gvm install gradle
```

3. 動作確認

```sh
$ gradle --version
```

