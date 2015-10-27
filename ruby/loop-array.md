---
title: 配列をループで処理する
created: 2011-10-01
---

配列内の要素を順番に処理
====

配列内の値だけを順番に処理したい場合は、**Array#each** を使用するのが一番簡単です。

```ruby
arr.each {|x| puts x}
arr.reverse_each {|x| puts x}  # 逆順に処理
```


インデックスと値を同時に取得しながらループ
====

ループ中にインデックスも同時に使いたい場合は、**Array#each_with_index** が使えます。

```ruby
arr.each_with_index do |val, i|
  puts "#{i}: #{val}"
end
```

#### 実行結果
```
0: elem1
1: elem2
2: elem3
```


インデックスだけでループ
====

C 言語風に、インデックスベースでループを回したい場合は、**upto**、**for~in**、**times** など、いろいろな方法が使えます。

upto を使う方法
----
```ruby
0.upto(arr.size-1) {|i| puts arr[i] }
```

for ~ in を使う方法
----

```ruby
for i in 0...arr.size
  puts arr[i]
end
```

`配列サイズ−1` のインデックスまでループさせるために、ドットの数は 3 つになることに注意してください。ドットの数を 2 つにすると、配列サイズと同じインデックス位置の要素までアクセスしてしまいます（結果として最後に `nil` が取得される）。
ドット 1 つで動作が変わるので、ちょっと怖いですね…。

times を使う方法
---

```ruby
arr.size.times {|i| puts arr[i] }
```

`times` のブロックに渡されるパラメータは `0` からスタートするので、配列インデックスとして使用できます。


