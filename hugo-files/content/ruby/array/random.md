---
title: "Rubyメモ: 配列の要素をランダムに取得する"
url: "p/ggqktqi/"
date: "2012-05-01"
tags: ["ruby"]
aliases: ["/ruby/array/random.html"]
---

下記の例では、`rand()` を利用して配列内の要素をランダムに取得しています。

```ruby
arr = [1, 2, 3, 4, 5]
puts arr[rand(arr.size)]
```
