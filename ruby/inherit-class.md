---
title: クラスを継承する
created: 2011-10-07
---

Ruby は単一継承に制限されているので、多重継承することはできません。
派生クラスのコンストラクタ (`intialize`) から、基底クラスのコンストラクタ (`intialize`) を呼び出すには、`super` をコールします。

```ruby
class Base
  def initialize(name)
    @name = name
  end
end

class Derived < Base
  def initialize(name, age)
    super(name)
    @age = age
  end
end

d = Derived.new('makkuma')
puts d.inspect
```

`super` というキーワードは、`initialize` メソッドに限らず、親クラスの同名のメソッドを呼び出す目的で使用することができます。
`super` に渡すパラメータを省略すると、呼び出し元のメソッドに渡されたパラメータと同じパラメータが渡されます。

