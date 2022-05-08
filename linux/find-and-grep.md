---
title: "Linuxコマンド: ファイルやディレクトリを検索する (find, grep)"
url: "p/hudubr8"
permalink: "p/hudubr8"
date: "2008-09-08"
lastmod: "2022-05-08"
tags: ["find", "grep", "Linux"]
redirect_from:
  - /linux/basic/find-and-grep
  - /linux/basic/find-iname
  - /linux/basic/grep-or
---

指定した拡張子のファイルを検索する (find)
----

次のようにすると、カレントディレクトリ (`.`) を起点にして、拡張子 `.txt` を持つファイルを検索して一覧表示することができます。

```console
$ find . -name '*.txt'
```


ファイル内の文字列を検索する (grep)
----

次のようにすると、指定したテキストファイル内の文字列を検索することができます。

```console
$ grep '検索文字列' hello.txt
```


find で見つけたファイルの中身を grep 検索する
----

`find` で検索した結果のファイルそれぞれに対して、`grep` コマンドを実行するには、`xargs` を組み合わせて使用します。

#### 例: 拡張子に txt を持つファイルを grep

```console
$ find . -type f -name '*.txt' | xargs grep '検索文字列'
```

拡張子などを絞り込まず、単純にそのディレクトリ以下のすべてのテキストファイルを検索するだけでよいのであれば、`find` を使わず、`grep` の再帰オプション (`-r`) を使った方が簡単です。
念のため、テキストファイルのみを `grep` 対象にするために `-I` オプション (`--binary-files-without-match`) も一緒に指定しておきます。

#### 例: すべてのテキストファイルを grep

```console
$ grep -r -I '検索文字列' .
```


grep で OR 検索する
----

`grep` コマンドで検索文字列を指定するときに __`-e`__ オプションを使うと、複数の検索文字列を指定して OR 検索することができます。

#### 例: aaa か bbb を含む行を検索する

```console
$ grep -e 'aaa' -e 'bbb' file.txt
```

別の方法として、正規表現を使用して OR 検索する方法もあります。

```console
$ grep -E 'aaa|bbb' file.txt
$ egrep 'aaa|bbb' file.txt
```


find いろいろ
----

### ファイル名の大文字と小文字を区別しない (-iname)

`-name` オプションの代わりに __`-iname`__ オプションを使用すると、ファイル名の大文字と小文字を区別せずにファイル検索することができます。

#### 例: カレントディレクトリ以下の foo.txt や FOO.TXT や Foo.Txt などを検索

```console
$ find . -iname foo.txt
```

### 空のファイル／ディレクトリを検索する (-empty)

#### 例: サイズが 0 のファイルを検索する

```console
$ find . -type f -empty
```

#### 例: 空っぽのディレクトリを検索する

```console
$ find . -type d -empty
```

