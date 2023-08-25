---
title: "Python の Pandas でラベル付き 2 次元データを扱う (pandas.DataFrame)"
url: "p/watbs9p/"
date: "2017-01-23"
tags: ["Python", "pandas"]
aliases: /python/numpy/pd-dataframe.html
---

pandas.DataFrame はラベル付き 2 次元配列
----

Python の Pandas ライブラリが提供する __`pandas.DataFrame`__ 型は、テーブル形状のデータを表す 2 次元データ型で、Pandas によるデータ解析の要となるデータ形式です。

内部では NumPy ライブラリの `ndarray` が利用されていますが、`pandas.DataFrame` を使うと、X 軸（列）、Y 軸（行）の各インデックスに任意のラベルを付けることができます （[1 次元データを表現する pandas.Series 型](/p/wbudtbr/)も用意されています）。
NumPy の `ndarray` は同じデータタイプの値しか持てませんが、`pandas.DataFrame` であれば、列ごとに異なるデータタイプの値を保持することができます。


### pandas.DataFrame を生成する

`pandas.DataFrame` コンストラクタで、次のように初期値となる 2 次元データだけを渡すと、行と列のラベルとして、0、1、2 という連番が割り振られます。
`pandas.DataFrame` オブジェクトを `print` 関数に渡すと、見やすい形で出力してくれます。

{{< code lang="python" title="sample.py" >}}
import pandas as pd

data = [[100, 200, 300], [400, 500, 600], [700, 800, 900]]
df = pd.DataFrame(data)
print(df)
{{< /code >}}

{{< code title="実行結果" >}}
     0    1    2
0  100  200  300
1  400  500  600
2  700  800  900
{{< /code >}}

### 各インデックスにラベルを設定する

`pandas.DataFrame` コンストラクタの __`columns`__ パラメータを使うと、X 軸（列）の各インデックスのラベルを設定することができます。
同様に、__`index`__ パラメータを使うと、Y 軸（行）の各インデックスのラベルを設定することができます。

```python
import pandas as pd

data = [[100, 200, 300], [400, 500, 600], [700, 800, 900]]
columns = ('x1', 'x2', 'x3')
index = ('y1', 'y2', 'y3')
df = pd.DataFrame(data, columns=columns, index=index)
print(df)
```

{{< code title="実行結果" >}}
     x1   x2   x3
y1  100  200  300
y2  400  500  600
y3  700  800  900
{{< /code >}}


ディクショナリから DataFrame を生成する
----

Python のディクショナリオブジェクトを `pandas.DataFrame` に変換することができます。
`pandas.DataFrame` コンストラクタにディクショナリを渡すと、そのキーがカラム名として使用されます。

{{< code lang="python" title="ディクショナリから DataFrame を生成する" >}}
import numpy as np
import pandas as pd

data = {
    'A': 1.0,
    'B': [100, 200, 300],
    'C': np.array(['a', 'b', 'c']),
    'D': pd.Series([0.1, 0.2, 0.3]),
    'E': pd.Categorical(['apple', 'lemon', 'peach']),
    'F': pd.date_range('2017-01-01', periods=3)
}
df = pd.DataFrame(data)
print(df)
{{< /code >}}

{{< code title="実行結果" >}}
     A    B  C    D      E          F
0  1.0  100  a  0.1  apple 2017-01-01
1  1.0  200  b  0.2  lemon 2017-01-02
2  1.0  300  c  0.3  peach 2017-01-03
{{< /code >}}

この例では、キーがたまたまディクショナリの初期化時と同じ順番で並んでいますが、ディクショナリの要素には順序性はないので、このような順番でカラムが並ぶことは保証されていないことに注意してください（カラム名とそれに関連付けられた `Series` データがあるだけです）。


ランダム値や連番からなる DataFrame を生成する
----

NumPy 配列から `pandas.DataFrame` を生成することもできます。
NumPy ライブラリには、いろいろな形の NumPy 配列を生成するユーティリティ関数が用意されているので、これを利用してテスト用の `pandas.DataFrame` を簡単に生成できます。

### ランダムな DataFrame

```python
import numpy as np
import pandas as pd

np.random.seed(2525)  # 乱数シードを設定
arr = np.random.randint(0, 1000, size=(2, 3))
df = pd.DataFrame(arr, columns=("x1", "x2", "x3"))
print(df)
```

{{< code title="実行結果" >}}
    x1   x2   x3
0  636  952  324
1   21  289  966
{{< /code >}}

ランダムな値を使うときは、上記のようにシード値を設定しておくことで、同じデータを再現できるようになります。

### 連番の DataFrame

```python
arr = np.arange(10).reshape((2, 5))
df = pd.DataFrame(arr, columns=("A", "B", "C", "D", "E"))
print(df)
```

{{< code title="実行結果" >}}
   A  B  C  D  E
0  0  1  2  3  4
1  5  6  7  8  9
{{< /code >}}

- 参考: [NumPy 配列 (ndarray) の作成方法まとめ](/p/htducs9/)

