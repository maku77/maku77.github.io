---
title: "Rubyメモ: 配列内で最大値を持つ要素のインデックスを取得する"
url: "p/9xi4svg/"
date: "2011-12-10"
tags: ["ruby"]
aliases: ["/ruby/number/max-value-index.html"]
---

以下は、配列の中の最大値のインデックスを探すいろいろな方法を示しています。


## 最速の方法（index と max を組み合わせる）

```ruby
p a.index(a.max)
```

## やや遅い方法（自力でループ）

```ruby
max_i = 0
max_val = arr[0]
for i in 1...arr.size
  if arr[i] > max_val
    max_i = i
    max_val = arr[i]
  end
end

p max_i
```

## 遅い方法（each_with_index と max を組み合わせる）

```ruby
a.each_with_index.max[1]
```
