---
title: "Rubyメモ: 配列の各要素の値を変更する (collect)"
url: "p/eoudxxo/"
date: "2007-10-10"
tags: ["ruby"]
aliases: ["/ruby/array/collect-array.html"]
---

`Array#collect` メソッドを使用すると、配列の各要素に対して任意の計算を行った結果の配列を作成できます。
末尾に `!` を付けて実行すると、自分自身の配列を書き換えます。
下記の例では、配列の各要素の値を 2 乗しています。

```ruby
arr = [1, 2, 3, 4, 5]
arr.collect!{|x| x ** 2 }

print arr  # => [1, 4, 9, 16, 25]
```
