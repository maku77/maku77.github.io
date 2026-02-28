---
title: "Rubyメモ: ASCII コードと文字を変換する (ord, chr, bytes, unpack, codepoints)"
url: "p/cbif895/"
date: "2011-12-05"
tags: ["ruby"]
aliases: ["/ruby/number/ascii-char.html"]
---

## 前提知識（Ruby における文字の表現方法）

Ruby には 1 文字だけを表すクラスが存在しないため、例えば `A` という 1 文字を扱いたい場合は、`'A'[0]` のように記述する必要があります。
また、Ruby 1.8 と 1.9 では、`'A'[0]` と記述した場合の意味が異なることに注意してください。

```
（Ruby 1.8 の場合） 'A'[0]  # => 65 （文字コード）
（Ruby 1.9 の場合） 'A'[0]  # => "A" （1 文字の部分文字列）
```


## 文字 → ASCII コード

{{% code lang="ruby" title="Ruby 1.8 / 1.9 共通の方法" %}}
'A'[0].ord  # => 65
'A'.bytes.to_a[0]  # => 65
{{% /code %}}

{{% code lang="ruby" title="Ruby 1.8 以下でのみ使用できる方法" %}}
'A'[0]  # => 65
{{% /code %}}

{{% code lang="ruby" title="Ruby 1.9 以上でのみ使用できる方法" %}}
'A'.ord  # => 65
{{% /code %}}

<div class="note">
<code>ord</code> は実際には ASCII コードではなく、コードポイントを表しています。
Unicode などで、1 文字が複数バイトで表現されている場合は、<code>ord</code> の返す値も 2 バイト以上の整数値になります。
</div>


## ASCII コード → 文字

```ruby
65.chr  # => "A"
```


## 文字列 → ASCII コードの配列

```ruby
"ABC".bytes.to_a  # => [65, 66, 67]
"ABC".unpack("c*")  #  => [65, 66, 67]
```

`String#bytes()` は `Enumerator` オブジェクトを返すので、ASCII コードを順番に取得したければ、以下のようにブロックを使って処理できます。

```ruby
"ABC".bytes {|code| puts code }  # ASCII コードを順番に表示
"ABC".bytes.inject(:+)  # ASCII コードの合計値を求める
```

バイトごとではなく、Unicode などのコードポイント単位で取得したい場合は、`String#codepoints` (= `each_codepoint`) が使用できます (Ruby 1.9)。

```ruby
"漢字ABC".codepoints {|cp| puts cp.to_s(16) }  # => 6f22 5b57 41 42 43
```


## ASCII コードの配列 → 文字列

```ruby
[65, 66, 67].pack("c*")  # => "ABC"
[65, 66, 67].map {|code| code.chr}.join  # => "ABC"
```
