---
title: "Linuxコマンド: ファイルやディレクトリを検索する (find, grep)"
url: "p/hudubr8/"
permalink: "p/hudubr8/"
date: "2008-09-08"
lastmod: "2022-05-08"
tags: ["find", "grep", "Linux"]
redirect_from:
  - /linux/basic/find-and-grep
  - /linux/basic/find-iname
  - /linux/basic/grep-or
---

find と grep の基本
----

### 指定した拡張子のファイルを検索する (find)

次のようにすると、カレントディレクトリ (`.`) を起点にして、拡張子 `.txt` を持つファイルを検索して一覧表示することができます。

```console
$ find . -name '*.txt'
```

### ファイル内の文字列を検索する (grep)

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


grep で NOT、AND、OR 検索する
----

### NOT 検索

`grep` の __`-v`__ オプションを指定すると、指定した __文字列を含まない__ 行だけを抽出できます。

#### 例: TODO という文字列を含まない行を検索する

```console
$ grep -v 'TODO' file.txt
```

### AND 検索

`grep` で AND 検索したいときは、`grep` の検索結果をパイプで繋いでさらに `grep` 検索してしまうのが簡単です。

#### 例: すべての .txt ファイルから AAA と BBB の両方を含む行を検索する

```console
$ find . -name '*.txt' | xargs grep AAA | grep BBB
```

#### 例: すべての .html ファイルから http: を含む行（ただし http://localhost ではない）を検索する

```console
$ find . -name '*.html' | xargs grep 'http:' | grep -v 'http://localhost'
```

### OR 検索

`grep` で OR 検索したいときは、検索した文字列を __`-e`__ オプションで複数回指定します。

#### 例: AAA あるいは BBB を含む行を検索する

```console
$ grep -e AAA -e BBB file.txt
```

別の方法として、正規表現（__`-E`__ オプション）を使用して OR 検索する方法もあります。

```console
$ grep -E 'AAA|BBB' file.txt
$ egrep 'AAA|BBB' file.txt
```


find のオプションいろいろ
----

### ファイル名で OR 検索する

`find` でファイル名の OR 検索をするときは、__`-o`__ オプションで `-name` などの検索オプションを繋ぎます。

#### 例: .py ファイルと .rb ファイルを検索する

```console
$ find . -name '*.py' -o -name '*.rb'
```

### ファイル名の大文字と小文字を区別しない (-iname)

`-name` オプションの代わりに __`-iname`__ オプションを使用すると、ファイル名の大文字と小文字を区別せずにファイル検索することができます。

#### 例: カレントディレクトリ以下の foo.txt や FOO.TXT や Foo.Txt などを検索

```console
$ find . -iname foo.txt
```

### 特定のディレクトリを検索対象外にする (-not -path)

__`-not -path`__ あるいは __`! -path`__ で検索対象外にするパスのパターンを指定できます。

#### 例: node_modules ディレクトリを検索対象外にする

```console
$ find . -name '*.md' -not -path '*/node_modules/*'
$ find . -name '*.md' ! -path '*/node_modules/*'
```

### 空のファイル／ディレクトリを検索する (-empty)

```console
$ find . -type f -empty  # サイズが 0 のファイルを検索する
$ find . -type d -empty  # 空っぽのディレクトリを検索する
```

