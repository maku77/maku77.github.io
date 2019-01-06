---
title: "ユーザライブラリの検索パスを調べる"
date: "2015-11-21"
---

Ruby を実行しているとき `$LOAD_PATH` という変数を参照すると、Ruby の実行環境がどのようなディレクトリからライブラリを検索するかを調べることができます。
下記は、著者の Mac OSX で確認したときの例です。

```
$ ruby -e 'puts $LOAD_PATH'
/Library/Ruby/Site/2.0.0
/Library/Ruby/Site/2.0.0/x86_64-darwin15
/Library/Ruby/Site/2.0.0/universal-darwin15
/Library/Ruby/Site
...
```

通常のユーザライブラリは、`Site` や `site_ruby` といったディレクトリに置きます。
その下の `2.0.0` というディレクトリは、Ruby のバージョンに依存したユーザライブラリを格納します。
`x86_64-darwin15` や `i386-sygwin` といったディレクトリには、CPU アーキテクチャや OS に依存したユーザライブラリを格納します。

