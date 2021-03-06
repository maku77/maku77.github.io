---
title: "複数の値を同時に代入する（多重代入）"
date: "2002-09-02"
---

多重代入の基本
----

Ruby の代入式は、複数の左辺値や右辺値をとることができます。

```ruby
a, b, c = 100, 200, 300
```

上記のような代入式を記述すると、次のようにするのと同様に、複数の代入処理を実行します。

```ruby
a = 100
b = 200
c = 300
```

多重代入によるそれぞれの代入処理は並列に行われるため、次のように変数値をスワップすることができます。

```ruby
a, b = b, a
```

左辺値の方が多いとき
----

```ruby
a, b, c = 100, 200
```

上記の場合、`c` の値は `nil` となります。


右辺値の方が多いとき
----

```ruby
a, b = 100, 200, 300
```

上記の場合、右辺値の 300 は単純に破棄されます。

左辺値の１つにプレフィックスとしてアスタリスク `*` を付けることにより、過剰な右辺値をまとめて配列の形で受け取ることができます。
アスタリスクはどの変数につけても構いませんが、左辺値のうち１つにしか付けられません。

```ruby
a, *b = 100, 200, 300     # a=100, b=[200,300]
*a, b = 100, 200, 300     # a=[100,200], b=300
*a, *b = 100, 200, 300    # シンタックスエラー
```

アスタリスクの付いた変数は必ず配列の形になります。これは、左辺値と右辺値の数が等しい場合や、左辺値の方が多い場合も同様です。アスタリスクの付いた変数は空配列 `[]` になることはありますが、`nil` になることはありません。

```ruby
a, *b, c = 100, 200, 300  # a=100, b=[200], c=300
a, *b = 100   # a=100, b=[]
*a, b = 100   # a=[], b=100
```

右辺値に配列を指定した場合
----

右辺値として配列を１つだけ指定した場合は、配列要素が展開されてから代入処理が実行されます。

```ruby
a, b, c = [100, 200, 300]  # a=100, b=200, c=300
```

右辺値が２つ以上あり、その中に配列要素がある場合、配列のまま代入されます。


```ruby
a, b, c = 100, [200, 300], 400  # a=100, b=[200,300], c=400
```

右辺値が２つ以上ある場合でも、配列のプレフィックスとしてアスタリスク `*` を付けると、明示的にその配列要素を展開してから代入することができます。

```ruby
a, b, c = 100, *[200, 300], 400  # a=100, b=200, c=300
```


利用例
----

### 例: 配列の特定の位置の要素を受け取る

例えば、ある配列の特定のインデックスにだけ興味のある情報が格納されているという場合、下記のようにわかりやすい名前を付けた変数に取り出すことができます。

```ruby
title, url = site_info[0, 2]
```


### 例: 多値関数の戻り値を受け取る

下記の `minmax_indices` 関数は、配列の中から最小、最大の値を探し、それぞれのインデックスを配列の形で返します。
呼び出し側で多重代入の形で受け取ることにより、それぞれのインデックスを別々の変数に受け取ることができます。

```ruby
def minmax_indices(arr)
  minIndex = 0
  maxIndex = 0
  arr.each_with_index do |val, i|
    if val < arr[minIndex]
      minIndex = i
    elsif val > arr[maxIndex]
      maxIndex = i
    end
  end
  [minIndex, maxIndex]
end

x, y = minmax_indices([20, 50, 30, 10, 40])  # x=3, y=1
```

