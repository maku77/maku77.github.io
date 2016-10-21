---
title: find で見つけたファイルを grep 検索する
created: 2008-09-08
---

`find` で検索した結果のファイルそれぞれに対して、`grep` コマンドを実行するには、`xargs` を組み合わせて使用します。
`grep` をテキストファイルのみに適用するには `-I` オプション (`--binary-files-without-match`) を指定します。

#### 例: 拡張子に cpp を持つファイルの中の XXX という文字列を検索

```
$ find . -type f -name '*.cpp' | xargs grep -I 'XXX'
```

拡張子などを絞り込まず、単純にそのディレクトリ以下のテキストファイルを検索するには `grep` 再帰オプション (`-r`) を使った方が簡単です。

```
$ grep -Ir 'XXX' *
```
