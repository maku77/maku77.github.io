---
title: 擬似コンストラクタを定義する
created: 2011-10-10
layout: ruby
---

コンストラクタに複数パターンのパラメータを持たせたい場合、通常はコンストラクタ `initialize` をオーバーロードしますが、
以下のようにクラスメソッド内で `new` を実行することで、擬似的なコンストラクタとして使用することが可能です。
インスタンスの生成パラメータを簡略化したい場合や、シングルトンを作成する時などに利用できます。

```ruby
class MyValue
  def initialize(val)
    @val = val
  end

  # 疑似コンストラクタ
  def self.createBig
    self.new(99999)
  end

  def printValue
    puts @val
  end
end

val1 = MyValue.new(100)  # 通常のコンストラクタ呼び出し
val1.printValue  # 100

val2 = MyValue.createBig  # 疑似コンストラクタ
val2.printValue  # 99999
```

