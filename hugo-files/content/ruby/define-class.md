---
title: "Rubyメモ: クラスとメソッドを定義する"
url: "p/twb57kz/"
date: "2003-10-13"
tags: ["ruby"]
aliases: ["/ruby/define-class.html"]
---

クラスとメソッドの定義
====

下記は、メソッドを 1 つだけ持つクラスの例です。

```ruby
class MyClass
  def add(a, b)
    a + b;
  end
end
```

定義したクラスを利用するには、`new` メソッドによりインスタンスを作成します。

```ruby
# インスタンス化
obj = MyClass.new

# メソッドの呼び出し
puts obj.add(1, 2)
```

