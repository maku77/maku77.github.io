---
title: "Python の pandas.DataFrame から部分データを抽出する"
url: "p/8j4k3iy/"
date: "2017-01-23"
lastmod: "2023-05-29"
tags: ["Python", "pandas"]
changes:
  - 2023-05-29: ワンポイントなどの説明を追加
aliases: /python/numpy/dataframe-select.html
---

Python の pandas ライブラリが提供する `pandas.DataFrame` はテーブル形式データを保持していますが、ここから部分的なデータを抽出する方法がたくさん用意されています。


サンプルデータ
----

最初に、後述の説明のためにサンプルデータを用意しておきます。
次のコードでは、10 行 3 列のデータを持つ `pandas.DataFrame` を生成しています。

```python
import numpy as np
import pandas as pd

data = np.random.randn(10, 3)  # 10x3 の NumPy 配列を生成（標準正規分布の乱数）
indices = pd.date_range("2010-01-01", periods=10)  # 日付の連番からなるインデックスを生成
columns = ("A", "B", "C")  # カラム名

my_dataframe = pd.DataFrame(data, index=indices, columns=columns)
print(my_dataframe)
```

{{< code title="実行結果" >}}
                   A         B         C
2010-01-01 -1.083348  0.780602 -1.249351
2010-01-02 -0.421893  1.417954  2.196932
2010-01-03 -1.129684 -0.751984  0.920735
2010-01-04 -0.668949 -2.082924 -0.018011
2010-01-05  1.542745 -1.014717 -0.587786
2010-01-06  0.535100 -0.680070  0.630020
2010-01-07  0.563422  0.856276  1.343315
2010-01-08  1.586091 -1.304232 -1.084707
2010-01-09 -1.561553 -1.884124  0.849059
2010-01-10 -0.817422 -1.244994  0.143166
{{< /code >}}


行方向（インデックス）で絞り込んで抽出する
----

### 先頭／末尾の数データのみ抽出する (DataFrame#head, DataFrame#tail)

`DataFrame` オブジェクトの __`head`__ メソッドや __`tail`__ メソッドを使用すると、巨大なデータフレームから、先頭あるいは末尾の数データのみを抽出することができます。
最新のデータを取得したいときや、データ構成を簡単に把握したいときに便利です。
戻り値の型は `DataFrame` です。

```python
print(my_dataframe.head(3))  # 先頭の 3 つのデータのみ抽出 => DataFrame
print(my_dataframe.tail(3))  # 末尾の 3 つのデータのみ抽出 => DataFrame
```

{{< image w="650" src="img-1-1.png" title="pandas.DataFrame の head 関数と tail 関数" >}}

{{< code title="実行結果" >}}
                   A         B         C
2010-01-01 -1.083348  0.780602 -1.249351
2010-01-02 -0.421893  1.417954  2.196932
2010-01-03 -1.129684 -0.751984  0.920735

                   A         B         C
2010-01-08  1.586091 -1.304232 -1.084707
2010-01-09 -1.561553 -1.884124  0.849059
2010-01-10 -0.817422 -1.244994  0.143166
{{< /code >}}

`head` や `tail` のパラメータを省略すると、__デフォルトで 5 つ__ のデータが抽出されます。


### インデックスの範囲指定でデータを抽出する

`DataFrame` を参照するときに、Python のスライス構文を使って、インデックス番号やインデックス名で範囲を指定すると、その範囲の部分データを抽出することができます。
単一のインデックス指定ではなく、どのケースも範囲指定になっていることに注意してください。
戻り値は `DataFrame` オブジェクトです。

```python
print(my_dataframe[0:3])  # 先頭の 3 つのデータを取得
print(my_dataframe[:3])   # 同上
print(my_dataframe[-2:])  # 末尾の 2 つのデータを取得
print(my_dataframe["2010-01-05":"2010-01-07"])  # インデックス名で範囲指定
```

{{< image w="800" src="img-1-2.png" title="DataFrame から複数の行を抽出" >}}


### 特定のインデックスのデータを抽出する

`DataFrame` の __`loc`__ メソッドを使用すると、特定のインデックスのデータのみを `Series` オブジェクトとして取得することができます（配列風に 1 つのラベル名だけを指定してアクセスすると、インデックス名ではなくカラム名を指定したことになってしまうので（列方向の抽出）、インデックス名を指定するための `loc` が用意されています）。

```python
print(my_dataframe.loc["2010-01-01"])  # 特定インデックスのデータを取得 => Series
print(my_dataframe.iloc[0])            # 番号指定の場合は iloc を使う => Series
```

{{< image w="700" src="img-1-3.png" title="DataFrame から 1 つの行を抽出" >}}

{{< code title="実行結果" >}}
A   -1.083348
B    0.780602
C   -1.249351
Name: 2010-01-01 00:00:00, dtype: float64
{{< /code >}}

`DataFrame` ではカラム名だった A, B, C が、戻り値の `Series` データではインデックス名になっていることに注意してください。



列方向（カラム）で絞り込んで抽出する
----

### 特定のカラムのデータを抽出する

`DataFrame` オブジェクトの `[]` 演算子でカラム名を 1 つだけ指定すると、そのカラムのすべてのデータが `pandas.Series` データとして抽出されます。
戻り値は、指定した列のみデータを含む 1 次元データなので `pandas.Series` 型になります。

{{% note %}}
`[]` 演算子で単一の値を指定すると、インデックス（行）ではなく、カラム（列）の指定だとみなされることに注意してください。
逆に、インデックスを 1 つだけ指定して特定の行を抽出するには、`loc["インデックス名"]` や `iloc[インデックス番号]` を使用する必要があります。
{{% /note %}}

{{< code lang="python" title="カラム名 C のデータを Series として取得" >}}
print(my_dataframe["C"])  # => pandas.Series
{{< /code >}}

{{< image w="600" src="img-2-1.png" title="DataFrame から 1 つの列を抽出" >}}

{{< code title="実行結果" >}}
2010-01-01   -1.249351
2010-01-02    2.196932
2010-01-03    0.920735
2010-01-04   -0.018011
2010-01-05   -0.587786
2010-01-06    0.630020
2010-01-07    1.343315
2010-01-08   -1.084707
2010-01-09    0.849059
2010-01-10    0.143166
Freq: D, Name: C, dtype: float64
{{< /code >}}

特定のカラムのデータを `Series` ではなく、`DataFrame` として抽出するには次のようにします。

{{< code lang="python" title="カラム名 C のデータを DataFrame として取得" >}}
print(my_dataframe[("C")])  # => pandas.DataFrame
{{< /code >}}

この記法では、複数のカラムを指定することもできます。

{{< code lang="python" title="カラム名 A と C のデータを DataFrame として取得" >}}
print(my_dataframe[("A", "C")])  # カラム名 A と C の全データを取得
{{< /code >}}

{{< image w="650" src="img-2-2.png" title="DataFrame から複数の列を抽出" >}}


インデックスラベルとカラムを両方指定して取得 (loc, iloc)
----

`DataFrame` の __`loc[]`__ で、インデックスとカラムの範囲を組み合わせて指定することができます。
__戻り値の型はデータの抽出範囲によって変化する__ ことに注意してください。
取得結果が 1 次元データとなる場合は `Series` オブジェクト、2 次元データとなる場合は `DataFrame` オブジェクトとなります。

```python
# 単一インデックス ＋ 単一カラム指定 => numpy.float64
print(my_dataframe.loc['2010-01-01', 'A'])

# 単一インデックス ＋ 複数カラム指定 => pandas.Series
print(my_dataframe.loc['2010-01-01', ['A', 'C']])

# インデックス範囲指定 ＋ 単一カラム指定 => pandas.Series
print(my_dataframe.loc['2010-01-01':'2010-01-03', 'A'])

# 全インデックス指定 ＋ 複数カラム指定 => pandas.DataFrame
print(my_dataframe.loc[:, ['A', 'C']])
```

ラベルではなく、インデックス番号で指定する場合は、`loc[]` の代わりに __`iloc[]`__ を使用します。

```python
# 先頭のデータ => pandas.Series
print(my_dataframe.iloc[0])

# 先頭から 3 つのデータ => pandas.DataFrame
print(my_dataframe.iloc[0:3])

# 先頭のデータの 2 つ目のカラムの値 => numpy.float64
print(my_dataframe.iloc[0, 1])

# 全データの 3 つ目までのカラムのデータ => pandas.DataFrame
print(my_dataframe.iloc[:, 0:3])
```

