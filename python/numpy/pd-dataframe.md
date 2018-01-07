---
title: pandas.DataFrame - Pandas でラベル付き二次元データを扱う
date: "2017-01-23"
---


pandas.DataFrame はラベル付きニ次元配列
----

`pandas.DataFrame` は、x軸、y軸のインデックスにラベルを付けられる二次元データ型です（`pandas.Series` は一次元データ）。
行と列にそれぞれ名前が付けられるテーブルデータだと思えばよいです。
この `pandas.DataFrame` は、pandas によるデータ解析の要となるデータ形式となります。

下記のように二次元データだけを設定すると、X 軸と Y 軸のラベルとして、0、1、2 が割り振られます。

~~~ python
import pandas as pd

data = [[100, 200, 300], [400, 500, 600], [700, 800, 900]]
df = pd.DataFrame(data)
print(df)
~~~

#### 実行結果

~~~
     0    1    2
0  100  200  300
1  400  500  600
2  700  800  900
~~~

X 軸のラベル、Y 軸のラベルを設定するには、それぞれ `index`、`columns` パラメータを指定します。

~~~ python
import pandas as pd

data = [[100, 200, 300], [400, 500, 600], [700, 800, 900]]
index = ('y1', 'y2', 'y3')
columns = ('x1', 'x2', 'x3')
df = pd.DataFrame(data, index=index, columns=columns)
print(df)
~~~

#### 実行結果

~~~
     x1   x2   x3
y1  100  200  300
y2  400  500  600
y3  700  800  900
~~~


ディクショナリから DataFrame を生成する
----

ディクショナリのキーを `pandas.DataFrame` のカラム名として使用することもできます。

~~~ python
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
~~~

#### 実行結果

~~~
     A    B  C    D      E          F
0  1.0  100  a  0.1  apple 2017-01-01
1  1.0  200  b  0.2  lemon 2017-01-02
2  1.0  300  c  0.3  peach 2017-01-03
~~~

この例では、キーがたまたまディクショナリの初期化時と同じ順番で並んでいますが、ディクショナリの要素には順序性はありませんので、このような順番でカラムが並ぶことは保証されていないことに注意してください（カラム名とそれに関連付けられた Series データがあるだけということです）。

