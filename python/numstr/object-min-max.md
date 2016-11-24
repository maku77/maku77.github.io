---
title: 独自オブジェクトの配列から最小値、最大値を求める (min_by/max_by)
created: 2011-11-27
---

任意のオブジェクトの配列から最大値／最小値を持つ要素を取り出すには、大小判断をどのフィールドで行うかを指定しなければいけません。
`min_by`/`max_by` メソッドには、大小比較に使用する値を指定するためのブロックを渡すことができます。

下記の例では、独自クラス `Num` の `val` フィールドの値を大小の判断基準として、最大値を持つ要素を検索しています。

#### sample.rb

```ruby
class Num
  attr_reader :val
  def initialize(val)
    @val = val
  end
end

arr = [Num.new(1), Num.new(2), Num.new(3)]
max = arr.max_by {|x| x.val }
puts max.val
```

#### 実行結果

```
$ ruby sample.rb
3
```

