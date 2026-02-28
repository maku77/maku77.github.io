---
title: "Rubyメモ: 指定した値と同じ値を持つ要素を削除する"
url: "p/kjxrxs6/"
date: "2012-01-29"
tags: ["ruby"]
aliases: ["/ruby/array/delete-same-elements.html"]
---

```ruby
arr = [1, 2, 3, 1, 2, 3]
arr.delete(1)
p arr  # => [2, 3, 2, 3]
```

`Array#delete` は、指定した値に一致する要素をすべて削除します。
配列そのものを変更することに注意してください。
