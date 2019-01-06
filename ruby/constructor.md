---
title: "コンストラクタを定義する"
date: "2011-10-07"
---


コンストラクタの定義は initialize
----

Ruby のクラスでコンストラクタを定義したいときは、`initialize` というメソッドを使用します。
ここでインスタンス変数などを初期化します。

#### クラスの定義
```ruby
class Person
  def initialize(name)
    @name = name
  end
end
```


インスタンスの生成は new
----

`initialize` メソッド自体は private メソッドとして定義されるので、インスタンスを作成するときは暗黙的に定義されている `new` メソッドを呼び出します。
`new` を呼び出すと、Ruby 内部では、初期化されていないオブジェクトが生成されて、`initialize` メソッドが呼び出されます。

#### インスタンスの生成
```ruby
p = Person.new("makkuma")
puts p.inspect    # => #<Person:0x10016a160 @name="makkuma">
```

上記の例では、Object クラスに定義されている `inspect` メソッドを使ってオブジェクトの内容を取得しています。

