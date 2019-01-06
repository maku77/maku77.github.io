---
title: "クラスとメソッドを定義する"
date: "2003-10-13"
---

クラスとメソッドの定義
====

下記は、メソッドを 1 つだけを持つクラスの例です。

```ruby
class MyClass
  def add(a, b)
    a + b;
  end
end
```

定義したクラスを利用するには、`new` メソッドによりインスタンスを作成します。

```
# インスタンス化
obj = MyClass.new

# メソッドの呼び出し
puts obj.add(1, 2)
```

