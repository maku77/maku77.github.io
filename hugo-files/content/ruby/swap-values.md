---
title: "Rubyメモ: 2 つの変数の値をスワップする"
url: "p/55yq5h2/"
date: "2011-11-05"
tags: ["ruby"]
aliases: ["/ruby/swap-values.html"]
---

Ruby では以下のように記述すれば、一時変数を使わずに値をスワップできます。

```ruby
a, b = b, a
```


例えば、Array クラスを以下のように拡張しておくと、配列の任意の 2 要素を簡単に入れ替えることができるようになります。

```ruby
class Array
  def swap!(a, b)
    self[a], self[b] = self[b], self[a]
  end
end

# 使用例
arr = [1, 2, 3, 4, 5]
arr.swap!(0, 4)  # => [5, 2, 3, 4, 1]
```

