---
title: Ruby の制御構文 -- Ruby の真偽値
created: 2002-08-21
---

Ruby では、「偽」を表す組み込み変数は `FALSE (false)` と `NIL (nil)` のみです。
例えば、他の多くの言語とは異なり、`0` も「真」と評価されるので注意が必要です。

```ruby
puts '0 は真' if 0
```
