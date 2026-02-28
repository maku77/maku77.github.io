---
title: "Rubyメモ: 条件を満たす要素と満たさない要素の配列に分割する (partition)"
url: "p/inqqbua/"
date: "2011-11-04"
tags: ["ruby"]
aliases: ["/ruby/array/partition.html"]
---

`Array` クラスの下記のメソッドを使用すると、指定した条件に一致するかどうかで、配列を 2 つに分割できます。

```
Array#partition {|x| ...}  =>  [arr1, arr2]
```

{{< code lang="ruby" title="例: 1〜10 の数を、2 で割り切れる数と割り切れない数の配列に分割" >}}
arr = (1..10).to_a
a1, a2 = arr.partition {|x| x % 2 == 0}    # => [[2, 4, 6, 8, 10], [1, 3 ,5, 7, 9]]
p a1    # => [2, 4, 6, 8, 10]
p a2    # => [1, 3, 5, 7, 9]
{{< /code >}}
