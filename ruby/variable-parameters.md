---
title: 可変長引数を扱う関数を定義する
created: 2011-10-01
---

Ruby の可変長引数は、最後のパラメータに `*` を付けることで定義できます。
関数の内部では、配列として参照します。

```ruby
def hoge(arg, *rest_args)
  # do something
end

hoge("aaa", 1, 2, 3)   # rest_args => [1, 2, 3]
hoge("aaa", 1)         # rest_args => [1]
hoge("aaa")            # rest_args => []
hoge()                 # ERROR!
```

