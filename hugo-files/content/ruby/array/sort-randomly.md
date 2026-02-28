---
title: "Rubyメモ: 配列をランダムに並び替える (sort_by)"
url: "p/rdck2o4/"
date: "2012-05-01"
tags: ["ruby"]
aliases: ["/ruby/array/sort-randomly.html"]
---

```ruby
arr = [1, 2, 3, 4, 5]
arr.sort_by! {rand}
```

参考
----

* [配列をソートする（昇順ソート／降順ソート）(sort)](/p/xt2xzxi/)
