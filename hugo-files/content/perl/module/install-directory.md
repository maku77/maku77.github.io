---
title: "Perlメモ: 指定したディレクトリにモジュールをインストールする"
url: "p/796h6t8/"
date: "2008-07-06"
tags: ["perl"]
aliases: ["/perl/module/install-directory.html"]
---

Perl モジュールをインストールするときに `PREFIX` オプションを使用して、インストール先のディレクトリを指定することができます。

```
$ perl Makefile.PL PREFIX=~/YourDir
```

以下のようなパスにモジュールの関連ファイルがインストールされます。

- `$PREFIX/bin` -- スクリプト
- `$PREFIX/lib/site_perl` -- ライブラリ
- `$PREFIX/man` -- マニュアルページ

`PREFIX` を指定してモジュールをインストールした場合、モジュールを使用するときに次のような環境変数を設定する必要があります。

- `PATH` -- bin ディレクトリのパス
- `MANPAGE` -- man ディレクトリのパス
- `@INC` -- lib/site_perl ディレクトリのパス
