---
title: "Rubyメモ: 指定した位置の要素を削除する"
url: "p/ie33iti/"
date: "2011-10-10"
tags: ["ruby"]
aliases: ["/ruby/array/delete-at.html"]
---

インデックス指定で配列要素を削除
----

```ruby
a = [1, 2, 3, 4, 5]
a.delete_at(2)
a  # => [1, 2, 4, 5]
```

先頭要素の削除（同時に取得）
----

```ruby
a = [1, 2, 3, 4, 5]
a.shift()
a  # => [2, 3, 4, 5]
```


末尾要素の削除（同時に取得）
----

```ruby
a = [1, 2, 3, 4, 5]
a.pop()
a  # => [1, 2, 3, 4]
```
