---
title: Ruby の制御構文 -- for と each
created: 2002-08-22
---

for ~ in の構文
====
下記の例では、`for ~ in` の構文を利用して、0 から 9 までループ処理を行っています。

```ruby
for i in 0..9 do
  puts i
end
```


each メソッドを使用したイテレート
====
上記のように `for ~ in` 構文でループ処理を記述することもできますが、Ruby でループ処理を行う場合は、各種クラスに用意されている `each` メソッドなどを使ってイテレートするのが一般的です。

```ruby
(0..9).each do |i|
  puts i
end

['a', 'b', 'c'].each do |i|
  puts i
end
```

Ruby では、指定した範囲の文字でループさせることも簡単にできます。

```ruby
('a'..'f').each {|char| print char }    #=> abcdef
(5..10).each {|char| print char }       #=> 5678910
```

