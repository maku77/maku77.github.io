---
title: "文字列内にある文字列が含まれているか調べる (String#include, =~)"
date: "2018-12-06"
---

String#include? を使う方法
----

ある文字列の中に、指定した文字列そのものが含まれていることを調べるには、`String#include?` を使用します。

~~~ ruby
s = 'apple banana mango'
if s.include?('banana')
  puts "バナナを見つけた！"
end
~~~


演算子 `=~` を使用する方法
----

ある文字列の中に、指定した正規表現に一致する文字列が含まれているかを調べるには、`=~` 演算子を使用します。

~~~ ruby
s = 'AAA BBB maku@example.com CCC DDD'
if s =~ /\w+@\w+/
  puts "メールアドレスっぽいものを見つけた！"
end
~~~

