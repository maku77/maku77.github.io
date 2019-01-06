---
title: "Ruby の制御構文 -- case による分岐"
date: "2002-08-22"
---

case の構文
====
C/C++ や Java の switch 文のような分岐を行いたい場合は、Ruby では `case` を使用します。
Ruby の `case` では、下記のように、複数の値をカンマで指定することで、いずれかの値に一致した場合の処理を記述することができます。

#### 例: a の値により分岐
```ruby
case a
when 1, 2, 3, ...
  文1
when 'aaa', 'bbb', ...
  文2
else
  文3
end
```

