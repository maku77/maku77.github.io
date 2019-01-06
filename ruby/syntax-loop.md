---
title: "Ruby のループ構文"
date: "2011-12-01"
---

C/C++ の ```for (int i = 0; i < N; ++i)``` に相当することは、Ruby では times, Range#each, upto, downto, step を使って直感的に表現できます。
配列の要素を順番に処理するには、for x in arr を使うか、arr.each を使用します。

次のループ内では以下のジャンプ命令が使用できます。redo や retry は C や Java にはない機能です。

* ```break``` -- ループを抜ける
* ```next``` -- 次のステップへ
* ```redo``` -- ループブロックの先頭へジャンプ
* ```retry``` -- 最初からやり直し

無限ループ（break するまで）
====
```ruby
loop { ... }

loop do
  ...
end
```

指定した回数の繰り返し
====
```ruby
3.times {|i| print i }    #=> 012
```


x から y までインクリメントしながら
====
```ruby
0.upto(3) {|i| print i }    #=> 0123
(0..3).each {|i| print i }  #=> 0123

for i in 0..3  #=> 0123
  puts i
end
````

x から y までデクリメントしながら
====
```ruby
3.downto(0) {|i| print i }    #=> 3210
```

注意： (a..b) で表す Range オブジェクトは単調増加なので、以下のようにはできません

```ruby
for i in 3..0  # 間違い
  puts i
end
```

ステップごとの増分を指定してループ
====
```ruby
0.step(9, 3) {|i| print i }    #=> 0369
9.step(0, -3) {|i| print i }    #=> 9630
```

while ループ
====
```ruby
while line = gets
    print line
end

while i < 3
  i += 1
end

begin
  i += 1
end while i < 3
```

until ループ
====
```ruby
{ i += 1 } until i > 3

begin
  i += 1
end until i > 3
```

配列要素のループ処理
====
```ruby
arr = [1, 2, 3]
arr.each {|x| print x }    #=> 123
```

```for x in arr``` の形式も使用できます。

```ruby
for x in ['AAA', 'BBB', 'CCC']
  puts x
end
```

ハッシュ要素のループ処理
====
```ruby
h = {'A'=>1, 'B'=>2, 'C'=>3}
h.each_key {|key| print key }    #=> ABC
h.each_value {|value| print value }    #=> 123
h.each_pair {|key, value| print "#{key}=#{value} " }    #=> A=1 B=2 C=3
```

upto, downto, step の途中で終了条件が変化したらどうなるか？
====
```ruby
upper = 9
1.upto(upper) do |x|
  puts x
  upper = 3 if x == 5
end
```

上記のようにして途中で upper の値を 3 に減らしても、1～9 の値が表示されます。
つまり、一度 upto() のブロックに入ったら終了条件は変更できないということ。
途中で終了条件を変更したい場合は、while ループを使用します。

```ruby
i = 1
upper 9
while i < upper
  puts i
  i += 1
  upper = 3 if i == 5
end
```

#### 実行結果
```
1
2
3
4
```
