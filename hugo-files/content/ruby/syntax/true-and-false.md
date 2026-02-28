---
title: "Rubyメモ: Ruby の真偽値"
url: "p/fe3ufno/"
date: "2002-08-21"
tags: ["ruby"]
aliases: ["/ruby/syntax/true-and-false.html"]
---

Ruby では、「偽」として評価される値は `false` と `nil` のみです。
例えば、他の多くの言語とは異なり、`0` も「真」と評価されるため注意が必要です。

```ruby
puts '0 は真' if 0
```
