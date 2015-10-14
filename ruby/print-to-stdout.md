---
title: 標準出力へ出力する
created: 2011-10-01
layout: ruby
---

単純な出力 (puts / print)
====

変数展開して出力する場合は、文字列をダブルクォーテーションで囲みます。

```ruby
a = 100
puts "a = #{a}"     # 改行あり
print "a = #{a}"    # 改行なし
```

変数展開をしなくてもよい場合は、シングルクォーテーションで囲みます。

```
puts 'Hello, Ruby!'
```

以下のようにカンマでパラメータを区切ると、`puts` の場合は改行されながら、`print` の場合は連結されて出力されます。

```ruby
>> puts 1, 2, 3
1
2
3

>> print 1, 2, 3
123
```


書式指定して出力 (printf)
====

`printf` は C 言語の `printf` と同様に書式付き文字列を使用してデータを出力します。

```ruby
>> printf("Line: %d says %s", 100, 'hello')
Line: 100 says hello
```

