---
title: "Rubyメモ: case による分岐"
url: "p/2qpgt7a/"
date: "2002-08-22"
tags: ["ruby"]
aliases: ["/ruby/syntax/case.html"]
---

case の構文
====
C/C++ や Java の switch 文のような分岐を行いたい場合は、Ruby では `case` を使用します。
Ruby の `case` では、下記のように複数の値をカンマで指定することで、いずれかの値に一致した場合の処理を記述できます。

{{% code title="例: a の値により分岐" lang="ruby" %}}
case a
when 1, 2, 3, ...
  文1
when 'aaa', 'bbb', ...
  文2
else
  文3
end
{{% /code %}}
