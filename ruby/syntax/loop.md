---
title: "繰り返し処理（ループ） (while, until, times, for, each, step)"
date: "2002-08-22"
lastmod: "2021-07-05"
---

条件指定によるループ (while と until)
----

### while の構文

```ruby
while 条件
  文
end
```

ループを途中で抜ける場合は、__`break`__ を使用します。

### until の構文

Ruby では、`while not` の省略形として __`until`__ が用意されています。
`until` の場合は、__指定した条件が満たされるまで__ ループを実行します。

```ruby
until 条件
  文
end
```

範囲指定によるループ (times, for, each, step)
----

### 指定した回数の繰り返し (times)

`Integer` クラスの `times` メソッドを使うと、指定した回数だけループ処理できます。
次のようにすると、変数 `i` に 0、1、2 という順番で格納されます。

```ruby
# 0 から 2 までのループ
3.times do |i|
  puts i
end
```

現在のインデックスが必要ない場合は、変数を省略できます。

```ruby
# 単純に 3 回くりかえす
3.times do
  puts 'こんにちは'
end
```

### for ~ in の構文

下記の例では、`for ~ in` の構文に対して [Range オブジェクト](http://ruby-doc.org/core-2.3.0/Range.html)を渡すことで、指定した整数範囲のループ処理を行っています。
範囲指定時にドットの数を 3 つにすると、ループ回数が 1 回減ることに注意してください。
マイナス方向へのループ処理も行えます。

```ruby
# 0 から 3 までのループ
for i in 0..3 do
  puts i
end

# 0 から 2 までのループ
for i in 0...3 do
  puts i
end

# 3 から 0 までのループ
for i in 3..0 do
  puts i
end

# 3 から 1 までのループ
for i in 3...1 do
  puts i
end
```

### each メソッドによるイテレート

列挙可能なオブジェクトには、多くの場合 `each` メソッドが用意されています。
下記は、Range オブジェクト、あるいは配列の `each` メソッドを使用する例です。

```ruby
(0..9).each do |i|
  puts i
end

['a', 'b', 'c'].each do |i|
  puts i
end
```

指定した範囲の文字でループさせることも簡単にできます。

```ruby
('a'..'f').each {|char| print char }    #=> abcdef
```

### step メソッドを使用した浮動小数点数でのループ

Numeric クラスの `step` メソッドを使用すると、小数幅でのループを実現できます。

- [Numeric#step](http://ruby-doc.org/core-2.3.0/Numeric.html#method-i-step)

```ruby
0.step(0.5, 0.1) do |x|
  printf "%.1f ", x
end
```

#### 実行結果

```
0.0 0.1 0.2 0.3 0.4 0.5
```

マイナス方向へのループも行えます。

```ruby
0.step(-0.5, -0.1) do |x|
  printf "%.1f ", x
end
```

#### 実行結果

```
0.0 -0.1 -0.2 -0.3 -0.4 -0.5
```

