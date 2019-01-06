---
title: "配列を展開して関数に渡す"
date: "2011-10-01"
---

配列を関数に渡すときに頭に `*` を付けて渡すと、配列が展開されて渡されます。

```ruby
def hoge(a, b, c)
  puts a, b, c
end

arr = [1, 2, 3]
hoge(*arr)
```

