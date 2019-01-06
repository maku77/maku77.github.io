---
title: "invalid byte sequence in Windows-31J というエラーが出る場合"
date: "2016-07-13"
---

外部リソースから取り込んだテキストなどを `String#sub()` メソッドなどで処理していると、下記のようなエンコーディング形式に関するエラーが出ることがあります。

```
in `gsub!': invalid byte sequence in US-ASCII
in `gsub!': invalid byte sequence in Windows-31J
```

このような場合は、下記のように環境変数でエンコーディング形式を明示してやるか、

```
$ export LANG=ja_JP.utf8
```

あるいは、Ruby スクリプトの中で直接エンコーディング形式を設定すると動作するかもしれません。

```ruby
Encoding.default_external = 'utf-8'
```

