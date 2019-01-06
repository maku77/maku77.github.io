---
title: "クラス定数を定義する"
date: "2011-10-10"
---


下記の `MAX_VALUE` はクラス定数を表しています。
先頭文字を大文字で始めるとクラス定数となり、値を変更することはできなくなります。

```ruby
class MyClass
  MAX_VALUE = 100 * 5

  def self.printMaxValue
    puts MAX_VALUE
  end
end
```

クラス定数の可視性はデフォルトで public になり、クラスの外からは MyClass::MAX_VALUE のような形で参照できます。

```ruby
MyClass.printMaxValue    #=> 500
puts MyClass::MAX_VALUE  #=> 500
```

