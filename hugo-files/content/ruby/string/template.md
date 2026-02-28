---
title: "Rubyメモ: テンプレート機能（プレースホルダ）で文字列の一部を置換する (%)"
url: "p/3yvtnt6/"
date: "2019-02-11"
tags: ["ruby"]
aliases: ["/ruby/string/template.html"]
---

文字列を置換するためのメソッドとしては、[sub や gsub](/p/upbbkhw/) が用意されていますが、置換対象の文字列が決まった形（テンプレート）の場合、**`%`** によるプレースホルダを使って置換してしまうのが楽です。

- [String クラスの % のマニュアル](https://docs.ruby-lang.org/ja/latest/class/String.html#I_--25)

次の例では、テンプレート文字列 (`template`) の中のプレースホルダ部分 (`%s`) を置換しています。

```ruby
template = '/Users/%s/temp'
s = template % 'maku'  #=> /Users/maku/temp
```

複数のプレースホルダを配置することもできます。

```ruby
template = '%s costs %d yen'
s = template % ['AAA', 100]  #=> AAA costs 100 yen
```

プレースホルダ部分に名前を指定しておくと、Hash オブジェクトを渡すことができます。
下記のように名前を `<>` で囲んで指定し、その後ろにフォーマット（`s` や `d`）を指定します。

```ruby
template = '%<name>s costs %<price>d yen'
s = template % {name:'AAA', price:100}  #=> AAA costs 100 yen
```

名前を囲む記号を `{}` にすると、後ろのフォーマット指定を省略することができます。

```ruby
template = '%{name} costs %{price} yen'
s = template % {name:'AAA', price:100}  #=> AAA costs 100 yen
```
