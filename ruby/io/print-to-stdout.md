---
title: 標準出力へ出力する (puts/print/printf)
created: 2011-10-01
---

単純な出力 (puts / print)
----

標準出力へ文字列を出力するには、組み込みの `puts` 関数を使用します。
代わりに `print` を使用すると、最後に改行を行いません。


```ruby
puts 'Hello, Ruby!'   # 改行あり
print 'Hello, Ruby!'  # 改行なし
```

文字列をダブルクォーテーションで囲むと、変数展開を行って出力することができます。

```ruby
val = 100
puts "val = #{val}"
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


書式指定して出力 (printf / puts + %)
----

`printf` は C 言語の `printf` と同様に書式付き文字列を使用してデータを出力します。

```ruby
printf('Line: %d says %s', 100, 'hello')
```

`puts` と `%` を組み合わせて、書式付き文字列を扱うこともできます。

```ruby
puts '%4d-%02d-%02d' % [year, month, day]
```

