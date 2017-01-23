---
title: pandas.Series - Pandas でラベル付き一次元データを扱う
created: 2017-01-23
---

pandas.Series はラベル付き一次元配列
----

`pandas.Series` は、一次元の NumPy 配列 (`ndarray`) に、インデックス用のラベルを付加したデータ型です。
通常の配列データは 0、1、2 というインデックスで各要素にアクセスしますが、`pandas.Series` では各インデックスに意味のあるラベルを付けることができます。

下記のようにデータだけを設定すると、通常の配列と同様にインデックスとして 0、1、2 が割り振られます。

#### sample.py

~~~ python
import pandas as pd

s = pd.Series([100, 200, 300])
print(s)
~~~

#### 実行結果

~~~
0    100
1    200
2    300
dtype: int64
~~~

下記のように、`index` パラメータを指定することで、明示的に各インデックスのラベルを設定することができます。

~~~python
import pandas as pd

s = pd.Series([100, 200, 300], index=['aaa', 'bbb', 'ccc'])
print(s)
~~~

#### 実行結果

~~~
aaa    100
bbb    200
ccc    300
dtype: int64
~~~
