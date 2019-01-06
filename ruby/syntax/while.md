---
title: "条件指定によるループ (while と until)"
date: "2002-08-20"
---

while の構文
====
```ruby
while 条件
  文
end
```

ループを途中で抜ける場合は、`break` を使用します。


until の構文
====
Ruby では、`while not` の省略形として `until` が用意されています。
`until` の場合は、**指定した条件が満たされるまで**ループを実行します。

```ruby
until 条件
  文
end
```

