---
title: 配列を作成する
created: 2011-10-07
---

空の配列を作成
====
```ruby
a = []
a = Array.new
```

初期値を与えて作成
====

各要素の値を与えて配列を作成する
----
```ruby
arr = [1, 2, 3]
arr = ['aaa', 'bbb', 'ccc']
```

同じ値の繰り返しの配列を作成する
----
```ruby
arr = [0] * 5          # => [0, 0, 0, 0, 0]
arr = Array.new(5, 0)  # => [0, 0, 0, 0, 0]
```

クォーテーションマークを省略して配列要素を指定する
----
単語の配列を作りたい場合は、%w を使用すると、それぞれの単語をクォーテーションで囲まなくても済み、単語間のカンマも必要なくなります（逆に、カンマを入れてしまうと、カンマも値として含まれてしまうのでカンマは入力してはいけません）。

```ruby
arr = %w{aaa bbb ccc}    #=> ["aaa", "bbb", "ccc"]
```

注意: `%w{` の3文字の間にスペースが入ると構文エラーになります。


数値の羅列からなる配列を作成する
====
`Range#to_a` を使って、ある範囲の数値の羅列を作成できます。
さらに `step` を組み合わせて使用することで、飛び飛びの値からなる配列を簡単に作成することができます。

```ruby
arr = (1..5).to_a   # => [1, 2, 3, 4, 5]
arr = (1...5).to_a  # => [1, 2, 3, 4]
arr = 10.step(0, -2).to_a    # => [10, 8, 6, 4, 2, 0]
```

`Range#map` (`Range#collect`) を使うと、数値の羅列を加工しながら配列を作成することができます。

```ruby
arr = (1..5).map  # => [1, 2, 3, 4, 5]  # これをやるなら (1..5).to_a で！
arr = (1..5).map {|x| x*2}  # => [2, 4, 6, 8, 10]
```

以下のように実行するよりも、シンプルなコードで配列を作成できます。

```ruby
arr = []
1.upto(5) {|i| arr << i}
```

配列の各要素の値を加工して配列を作成する
====

`Range` オブジェクトでも同様のことができますが、配列に対しても、各要素の値を加工しながら新しい配列を作成することができます（`!` のついたバージョンを使用すれば、自分自身の要素を書き換えることができます）。

```ruby
arr.collect {|x| ...}  => arr
arr.collect! {|x| ...}
arr.map {|x| ...}  => arr
arr.map! {|x| ...}
```

#### 使用例
```ruby
[1, 2, 3, 4, 5].map {|x| x**2}  # => [1, 4, 9, 16, 25]
```
