---
title: "関数にコードブロックを渡す（yield による呼び出し）"
date: "2011-10-07"
---

yield の基本
====
メソッド内で `yield` を呼び出すと、そのメソッドを呼び出すときに与えられたコードブロック（`{...}` で囲まれたコード）をその位置で実行することができます。
下記の例では、`puts 'Hello'` を実行するコードブロックを関数に渡しています。


```ruby
def hoge
  yield
  yield
end

hoge { puts 'Hello' }
```

#### 実行結果
```
Hello
Hello
```

コードブロックにパラメータを渡す
====
`yield` をパラメータを付けて実行すると、そのパラメータがコードブロックに渡されます。

```ruby
def hoge
  yield 100, 200
  yield 200, 300
end

hoge { |x, y| puts(x + y) }
```

#### 実行結果
```
300
500
```

コードブロックは、`{...}` の形式でなく、`do ... end` の形式で記述する事もできます。
コードブロックが複数行に渡る場合は、`do ... end` の形式を使う事が多いようです。

```ruby
hoge do |x, y|
  puts(x + y)
end
```

データのリストを保持するクラスなどは、`each` メソッドが定義されている事が多く、
このメソッドは各データを 1 つずつ処理するための **each イテレータ** として働きます。

```ruby
class MyList
  def each
    [100, 200, 300].each do |x|
      yield x
    end
  end
end

myList = MyList.new
myList.each { |x| puts(x) }
```

#### 実行結果
```
100
200
300
```

