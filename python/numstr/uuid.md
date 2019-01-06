---
title: "Python で UUID を生成する (uuid.uuid4)"
date: "2013-05-29"
---

uuid モジュールを使用して UUID を生成する
----

Python では **uuid** モジュールを使って簡単に UUID を生成することができます。
UUID version 1（MAC アドレスを利用）、UUID version 4（完全ランダム）の UUID 文字列を生成するには以下のようにします。

```python
import uuid

u1 = str(uuid.uuid1())    # => '0e4e88ae-c880-11e2-8598-5855cafa776b'
u4 = str(uuid.uuid4())    # => 'f315bac8-3d1a-42cb-b590-bbf423d3172f'
```

`uuid.uuid1()` や `uuid.uuid4()` は、UUID インスタンスを返すため、`str()` 関数でハイフンつなぎの文字列に変換しています。


自力でランダム UUID を作ってみる
----

```python
import random
import string

def create_uuid4():
    x = lambda: string.hexdigits[random.randint(0, 15)]
    h = lambda digits: ''.join([x() for _ in range(0, digits)])
    return h(8) + '-' + h(4) + '-' + h(4) + '-' + h(4) + '-' + h(12)
```

#### テスト (test.py)

```python
if __name__ == '__main__':
    for i in range(10):
        print(create_uuid4())
```

#### 実行結果

```
$ ./test.py
c836f011-069d-901a-5968-0af11c552ba0
fdaa2e38-8b34-93be-f64d-b5d596edd979
ea89f082-dd11-d650-94cd-53a96f116207
ce245826-b576-6f69-d395-b1956c98212b
e04b7693-3021-8ba6-0d76-6d32c181309d
02685892-f7ef-95f3-6261-f150eaeafafc
b262adbf-eccf-c741-0116-0a9c2c614e7d
a63b386b-672a-325c-b8bc-5f90ab0599e9
c2806fd9-9ffd-d0f2-0dbf-fc0c674733b6
b0f24157-af8c-7067-c489-fd3d1b68688c
```

