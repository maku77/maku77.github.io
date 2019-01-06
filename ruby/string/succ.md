---
title: "文字列をインクリメントする (succ/next)"
date: "2011-11-04"
---

Ruby では、文字列も数値と同じような感覚でインクリメント／デクリメントすることができます。

```
String#succ => String
String#next => String
```

下記の例では、アルファベットで構成された文字列に `succ` メソッドを適用しています。

```ruby
"a".succ   # => "b"
"z".succ   # => "aa"
"A".succ   # => "B"
"Z".succ   # => "AA"
"aaa".succ  # => "aab"
"AAz".succ  # => "ABa"
"aaZ".succ  # => "abA"
```

文字列内に数値が含まれている場合は、それっぽく動作してくれます。

```ruby
"1".succ   # => "2"
"9".succ   # => "10"
"-1".succ  # => "-2"
"-999".succ  # => "-1000"
"A9".succ  # => "B0"
```

