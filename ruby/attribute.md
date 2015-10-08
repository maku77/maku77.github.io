---
title: 属性を定義する（アクセサメソッド）
created: 2011-10-07
---

Setter と Getter を同時に定義
====
クラスのメンバ変数は**デフォルトでは private** になっているので、外部から参照できません。
値の読み書きともにできる属性を定義したい場合は、`attr_accessor` を使用します。

```ruby
class MyPoint
  attr_accessor :posX, :posY
end

p = MyPoint.new
p.posX = 100
p.posY = 200
puts p.posX    #=> 100
puts p.posY    #=> 200
```


Getter の定義
====
外部からは、メンバ変数の参照だけできるようにしたいときは、`attr_reader` で対象となるメンバ変数を列挙します。

```ruby
class MyPoint
  attr_reader :posX, :posY

  def initialize(posX, posY)
    @posX = posX
    @posY = posY
  end
end

p = MyPoint.new(10, 20)
puts "#{p.posX}, #{p.posY}"    #=> 10, 20
```

あるいは、以下のようにメンバ変数を参照するためのメソッドを明示的に定義しても、`attr_reader` と同様の効果が得られます。

```ruby
def posX
  return @posX
end
```


Setter の定義
====
外部からメンバ変数を書き換えできるようにするには、`attr_writer` で公開したいメンバ変数を列挙します。

```ruby
class MyPoint
  attr_writer :posX, :posY
  # ...
end

p = MyPoint.new(10, 20)
p.posX = 30
```

あるいは、以下のようにメンバ変数に代入するための `=` 演算子を定義しても、`attr_writer` と同様の効果が得られます。

```ruby
def posX=(posX)
  @posX = posX
end
```

