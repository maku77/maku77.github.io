---
title: "Python でユニットテストを記述する (unittest)"
url: "p/fueucsa/"
date: "2012-07-03"
tags: ["Python", "UnitTest"]
aliases: /python/unittest.html
---

Python でユニットテストを書くには、__`unittest` モジュール__ を使用します。
`unittest` モジュールは、Python 2.1 以降に標準で搭載されています。

テストケースを作成するには、__`unittest.TestCase`__ を継承したクラスを作成し、__`test`__ で始まる名前のテストメソッドを記述するだけで OK です。
__`unittest.main`__ 関数を呼び出すことにより、それらのテストメソッドが自動的に実行されます。

{{< code lang="python" title="sample.py" >}}
import unittest

class Counter:
    def __init__(self):
        self.count = 0

    def increment(self):
        self.count += 1

    def decrement(self):
        self.count -= 1

    def get_count(self):
        return self.count


class CounterTest(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_increment(self):
        c = Counter()
        c.increment()
        self.assert_(c.get_count() == 1)
        c.increment()
        self.assert_(c.get_count() == 2)

    def test_decrement(self):
        c = Counter()
        c.decrement()
        self.assert_(c.get_count() == -1)
        c.decrement()
        self.assert_(c.get_count() == -2)


if __name__ == '__main__':
    unittest.main()
{{< /code >}}

各テストの実行前と実行後には、`setUp()` と `tearDown()` がそれぞれ呼び出されるようになっているので、ここに各テストの共通コードを記述しておくことができます。
上記の例では、共通コードはないので `pass` とだけ記述しています。

{{< code lang="console" title="実行結果" >}}
$ python3 sample.py
..
--------------------------------------
Ran 2 tests in 0.000s

OK
{{< /code >}}

