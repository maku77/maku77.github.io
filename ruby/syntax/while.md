---
title: Ruby の制御構文 -- while と until
created: 2002-08-20
---

while の構文
====
```ruby
while 条件
  文
end
```


until の構文
====
Ruby では、`while not` の省略形として `until` が用意されています。
`until` の場合は、**指定した条件が満たされるまで**ループを実行します。

```ruby
until 条件
  文
end
```

