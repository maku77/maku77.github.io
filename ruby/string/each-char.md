---
title: "文字列を１文字ずつに分割する"
date: "2016-07-05"
---

１文字ずつ切り出す
----

String クラスの `chars` メソッド（別名 `each_char`）を使用すると、文字列内の文字を１文字ずつ取り出しながら処理することができます。

#### sample.rb

```ruby
s = 'ABC'
p s.chars
s.chars { |ch| puts ch }
```

#### 実行結果

```
$ ruby sample.rb
["A", "B", "C"]
A
B
C
```


１文字ずつコードポイントを得る
----

文字列内の文字１文字ごとのコードポイントを取得するには、`codepoints`（別名 `each_codepoint`）を使用します。

#### sample.rb

```ruby
# encoding: utf-8

s = 'ABCあいう'
s.codepoints { |x| printf('U+%04X ', x) }
```

#### 実行結果

```
$ ruby sample.rb
U+0041 U+0042 U+0043 U+3042 U+3044 U+3046
```

もとの文字とコードポイントの対応付けがわかるようにするには、下記のように `ord` メソッドを組み合わせて使用するのがよいでしょう。
`ord` は、その文字列の先頭の文字のコードポイントを返します。

#### sample.rb

```ruby
# encoding: utf-8

s = 'ABCあいう'
s.chars { |ch| printf("U+%04X %s\n", ch.ord, ch) }
```

#### 実行結果

```
U+0041 A
U+0042 B
U+0043 C
U+3042 あ
U+3044 い
U+3046 う
```


コラム: Ruby では 1 文字ずつに分割しても文字列
----

Java や C++ には、1 文字を表す `char` 型がありますが、Ruby の場合はすべて文字列です。
なので、下記のように分割したあとの型 (`class`) を調べると、`String` と表示されます。

```ruby
'ABC'.each_char do |ch|
  puts "#{ch} -- #{ch.class}"
end
```

#### 実行結果

```
A -- String
B -- String
C -- String
```

