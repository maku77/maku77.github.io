---
title: "配列の要素をランダムに取得する"
date: "2012-05-01"
---

下記の例では、`rand()` を利用して配列内の要素をランダムに取得しています。

```ruby
arr = [1, 2, 3, 4, 5]
puts arr[rand(arr.size)]
```
