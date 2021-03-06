---
title: "文字列を結合する"
date: "2015-11-21"
---

2 つの文字列の結合
====
Ruby で文字列を結合するには、`+` 演算子を使用します。

```ruby
s1 = 'AAA'
s2 = 'BBB'
puts s1 + s2  #=> "AAABBB"
```

末尾への結合
====
既存の文字列変数の末尾に、別の文字列を追加するには `concat` メソッド、あるいは `<<` や `+=` を使用します。

```ruby
s = 'AAA'
s << 'BBB'
s.concat('CCC')
puts s  #=> "AAABBBCCC"
```

先頭への結合
====
既存の文字列変数の先頭に、別の文字列を追加するには以下のように `insert` メソッドを使用します。

```ruby
s = 'AAA'
s.insert(0, 'BBB')
puts s  #=> "BBBAAA"
```

文字列リテラルの結合
====
文字列リテラル同士をスペースで区切って並べておくと、それらはコンパイル時に結合されます。
ダブルクォーテーションで囲まれた文字列内では、適切に式展開が行われます。
間のスペースの数は結合結果に影響しません。

```ruby
name = 'maku'
s = 'AAA' 'BBB'   "#{name}"
puts s  #=> "AAABBBmaku"
```

