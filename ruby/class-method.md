---
title: クラスメソッドを定義する
date: "2011-10-10"
---

Ruby でクラスメソッドを定義するには、下記のように **クラス名.メソッド名** という名前でメソッドを定義します。

```ruby
class MyClass
  def MyClass.classMethod
    puts 'Hello'
  end
end

MyClass.classMethod()  #=> Hello
```

あるいは以下のように **self.メソッド名** と書くこともできます。

```ruby
class MyClass
  def self.classMethod
    puts 'Hello'
  end
end
```

