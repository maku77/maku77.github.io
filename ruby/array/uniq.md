---
title: 重複する要素を削除する (uniq)
created: 2011-11-16
---

配列内の重複する要素を取り除くには `Array#uniq` メソッドを使用します。

```ruby
arr = [1, 2, 3, 2, 1]
arr.uniq!
p arr  # => [1, 2, 3]
```
