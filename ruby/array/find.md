---
title: 条件に一致する要素を検索する (find/find_all)
created: 2011-10-10
---

配列内から任意の条件に一致する要素を抜き出すには以下のいずれかを使用します。

* `arr.find {|x| ... }`  -- ブロックで true を返した要素を 1 つ返す
* `arr.find_all {|x| ... }`  -- ブロックで true を返したすべての要素を配列で返す


下記は、独自クラス `Person` のオブジェクトを格納した配列から、条件に一致する要素を検索するサンプルです。

```ruby
class Person
  attr_accessor :name
  def initialize(name)
    @name = name
  end
  def to_s
    return @name
  end
end

a = [
  Person.new('Andrew'),
  Person.new('Makkuma'),
  Person.new('Mike'),
  Person.new('John')
]

# 名前が M で始まる要素を１つだけ抽出
b = a.find {|x| x.name =~ /^M/ }
b  # => #<Person:0x100167578 @name="Makkuma">

# 名前が M で始まる要素をすべて抽出
b = a.find_all {|x| x.name =~ /^M/ }
b  # => [#<Person:0x100167578 @name="Makkuma">, #<Person:0x100167528 @name="Mike">]
```

