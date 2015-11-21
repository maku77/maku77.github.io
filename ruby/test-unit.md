---
title: test-unit によるユニットテスト
created: 2015-11-21
---

Ruby のユニットテストフレームワークとして、**test-unit** という RubyGems モジュールが提供されています。

* http://www.rubydoc.info/gems/test-unit/
* http://test-unit.rubyforge.org/test-unit/ja/Test/Unit.html

test-unit のインストール
====
test-unit は `gem` コマンドで簡単にインストールできます。

```
$ gem install test-unit
```

インストールが完了すると、Ruby スクリプトの中から `require 'test/unit'` できるようになります。

test-unit の基本的な使い方
====
test-unit によるユニットテストを記述するには、下記のようにコードを記述していきます。

* `require 'test/unit'` する
* `Test::Unit::TestCase` 継承したクラスを作る
* `test` で始まるテストメソッドを作る
* `assert_xxx` で実行結果と期待結果を比べる

下記はとても簡単なユニットテストの例です。
この例では、`1 + 2` という足し算の計算結果が、`3` という期待値と等しいか（あるいは引き算の結果が正しいか）をテストしています。

#### sample.rb
```ruby
require 'test/unit'

class SampleTest < Test::Unit::TestCase
  def test_add
    assert_equal(1 + 2, 3)
  end
  def test_subtract
    assert_equal(1 - 2, -1)
  end
end
```

このコードは、そのままテストコードとして実行することができるようになっています（`require 'test/unit'` による効果）。

#### テスト実行
```
$ ruby sample.rb
Loaded suite sample
Started
..

Finished in 0.000548 seconds.
---------------------------------------------------------------------------------------
2 tests, 2 assertions, 0 failures, 0 errors, 0 pendings, 0 omissions, 0 notifications
100% passed
---------------------------------------------------------------------------------------
3649.64 tests/s, 3649.64 assertions/s
```

無事、2 つのテストメソッドをパスしました。


クラスのテストを記述する
====
ユニットテストは、別ファイルとして作成されたクラス、モジュールなどをテストするために使用されるのが一般的です。
ここでは、下記のような `Counter` クラスをテストすることを考えます。

#### counter.rb
```ruby
class Counter
  def initialize
    @cnt = 0
  end
  def increment
    @cnt += 1
  end
  def get
    @cnt
  end
end
```

`Counter` クラスは、内部的に `0` に初期化されるカウンタを保持しており、1 つずつカウントアップしていく機能を持っています。
下記の `CounterTest` クラスは、この `Counter` クラスをテストするためコードです。
同じディレクトリに `counter.rb` ファイルが存在すると仮定して、`require './counter'` とロードしています。

#### counter_test.rb
```ruby
require 'test/unit'

class CounterTest < Test::Unit::TestCase
  require './counter'

  def test_increment
    counter = Counter.new

    # 初期値のチェック
    assert_equal(counter.get, 0)

    # カウントアップ後のチェック
    counter.increment
    assert_equal(counter.get, 1)
  end
end
```

#### 実行方法
```
$ ruby counter_test.rb
```

assert_xxx 系のメソッドは他にもいろいろ用意されていますので、下記のドキュメントを参考に探してみてください。

* https://test-unit.github.io/test-unit/ja/Test/Unit/Assertions.html

