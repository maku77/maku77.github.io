---
title: "Python の Pandas でラベル付き 1 次元データを扱う (pandas.Series)"
url: "p/wbudtbr/"
date: "2017-01-23"
tags: ["Python", "pandas"]
aliases: /python/numpy/pd-series.html
---

Python の pandas ライブラリは、データ操作および分析を容易にするためのデータ型および関数群を提供します。
pandas ライブラリの内部では、NumPy ライブラリの多次元配列 (`ndarray`) や数値演算関数が使用されています。


pandas.Series はラベル付き 1 次元配列
----

__`pandas.Series`__ は、1 次元の NumPy 配列 (`ndarray`) に、インデックス用のラベルを付加したデータ型です。
通常の配列データは 0、1、2 というインデックスで各要素にアクセスしますが、`pandas.Series` では各インデックスに意味のあるラベルを付けることができます。

### pandas.Series を生成する

`pandas.Series` コンストラクタで、次のように初期データだけを指定すると、通常の配列と同様にインデックスとして 0、1、2 が割り振られます。

{{< code lang="python" title="sample.py" >}}
import pandas as pd

s = pd.Series([100, 200, 300])
print(s)
{{< /code >}}

{{< code title="実行結果" >}}
0    100
1    200
2    300
dtype: int64
{{< /code >}}

### 各インデックスにラベルを設定する

`pandas.Series` コンストラクタで __`index`__ パラメータを追加指定すると、明示的に各インデックスのラベルを設定することができます。

```python
import pandas as pd

s = pd.Series([100, 200, 300], index=['aaa', 'bbb', 'ccc'])
print(s)
```

{{< code title="実行結果" >}}
aaa    100
bbb    200
ccc    300
dtype: int64
{{< /code >}}

