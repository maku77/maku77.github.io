---
title: "Rubyメモ: 部分配列を取得する（配列スライシング）"
url: "p/jrf5596/"
date: "2011-10-10"
tags: ["ruby"]
aliases: ["/ruby/array/slice.html"]
---

部分配列のアクセス方法には以下の３種類があります。
文字列変数から部分文字列を取得する場合も同様にアクセスできます。

* `arr[startIndex, count]`      -- 指定した文字数を取得
* `arr[startIndex..endIndex]`   -- 指定した位置まで取得（endIndex を含む）
* `arr[startIndex...endIndex]`  -- 指定した位置まで取得（endIndex を含まない）

```ruby
s = "ABCDEFGH"
s[2, 0]    # => ""
s[2, 4]    # => "CDEF"
s[2, 100]  # => "CDEFGH"
s[2, -1]   # => nil
s[-3, -1]  # => nil

s = "ABCDEFGH"
s[2..0]    # => ""
s[2..4]    # => "CDE"
s[2..100]  # => "CDEFGH"
s[2..-1]   # => "CDEFGH"
s[-3..-1]  # => "FGH"

s = "ABCDEFGH"
s[2...0]    # => ""
s[2...2]    # => ""
s[2...4]    # => "CD"
s[2...100]  # => "CDEFGH"
s[2...-1]   # => "CDEFG"
s[-3...-1]  # => "FG"
```
