---
title: 文字列配列から正規表現に一致する要素を検索する (grep)
created: 2011-11-04
---

```ruby
arr = %w{ aaa abc bbb bab cca ccc dab ddd }
newArr = arr.grep(/ab/)    # => ["abc", "bab", "dab"]
```

