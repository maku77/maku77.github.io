---
title: "Rubyメモ: 条件を満たす要素を削除する"
url: "p/4k4e4nv/"
date: "2011-11-04"
tags: ["ruby"]
aliases: ["/ruby/array/delete-if.html"]
---

条件を指定して配列内の要素を削除するには、`Array` クラスの下記のようなメソッドを使用します。

```
Array#delete_if {|x| ...}  # 元の配列が変更される
Array#reject {|x| ...}     # 元の配列は変更されない
```

{{< code lang="ruby" title="例: 配列内の偶数の値を削除する" >}}
arr = (1..10).to_a  # => [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
arr.delete_if {|x| x % 2 == 0}
p arr  # => [1, 3, 5, 7, 9]
{{< /code >}}

`delete_if` は、元の配列が変更されることに注意してください。
元の配列を保持したまま、条件を満たす要素を除外した新しい配列を作成したい場合は `reject` を使用します。

```ruby
new_arr = arr.reject {|x| x % 2 == 0}
```
