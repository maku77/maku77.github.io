---
title: "Rubyメモ: クラスを継承する"
url: "p/9pjsrsu/"
date: "2011-10-07"
tags: ["ruby"]
aliases: ["/ruby/inherit-class.html"]
---

Ruby は単一継承に制限されているので、多重継承はできません。
派生クラスのコンストラクタ (`initialize`) から基底クラスのコンストラクタ (`initialize`) を呼び出すには、`super` を使用します。

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

`super` というキーワードは、`initialize` メソッドに限らず、親クラスの同名メソッドを呼び出す目的で使用できます。
`super` に渡すパラメータを省略すると、呼び出し元のメソッドに渡されたものと同じパラメータが渡されます。

