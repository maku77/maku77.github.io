---
title: Python でユニットテストを記述する
created: 2012-07-03
---

Python でユニットテストを書くには、**unittest モジュール**を使用します。
unittest モジュールは、Python 2.1 以降に標準で搭載されています。

テストケースを作成するには、`unittest.TestCase` を継承したクラスを作成し、`test` で始まる名前のテストメソッドを記述するだけで OK です。
`unittest.main()` を呼び出すことにより、それらのテストメソッドが自動的に実行されます。
各テストの前には、`setUp()` が呼び出され、各テストの後には `tearDown()` が呼び出されます。


#### sample.py
```python
#!/usr/bin/env python
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
```


#### 実行結果
```
$ ./sample.py
..
--------------------------------------
Ran 2 tests in 0.000s

OK
```

