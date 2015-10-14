---
title: コンストラクタを定義する
created: 2011-10-07
layout: ruby
---

Ruby のクラスでコンストラクタを定義したいときは、`initialize` というメソッドを使用します。
`initialize` メソッド自体は private メソッドとして定義されるので、インスタンスを作成するときは `new` メソッドとして呼び出します。
`new` を呼び出すと、Ruby では初期化されていないオブジェクトを生成してから、内部的に `initialize` メソッドを呼び出します。
つまり、ここでインスタンス変数などを初期化すればよいわけです。

#### クラスの定義
```ruby
class Person
  def initialize(name)
    @name = name
  end
end
```

#### インスタンスの生成
```ruby
p = Person.new("makkuma")
puts p.inspect    # => #<Person:0x10016a160 @name="makkuma">
```

上記の例では、Object クラスに定義されている `inspect` メソッドを使ってオブジェクトの内容を取得しています。

