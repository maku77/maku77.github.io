---
title: "pandas で CSV/TSV ファイルを読み込む (pd.read_csv, pd.read_table)"
url: "p/78ns8r5/"
date: "2023-09-04"
tags: ["pandas"]
---

{{% private %}}
- [IO tools (text, CSV, HDF5, …) — pandas documentation](https://pandas.pydata.org/docs/user_guide/io.html)
{{% /private %}}

{{% maku-common/reference %}}
- [pandas で CSV/TSV ファイルを出力する (`pd.to_csv`)](/p/ioj6bqf/)
{{% /maku-common/reference %}}

pandas で CSV ファイルを読み込んで `DataFrame` オブジェクトを生成するには、[pd.read_csv()](https://pandas.pydata.org/docs/reference/api/pandas.read_csv.html) 関数を使用します。
`read_csv()` 関数は、任意のセパレーター (`sep`) を指定できるので、CSV ファイルだけでなく、TSV ファイルなども読み込むことができます。


ヘッダーの有無の指定 (header)
----

```python
import pandas as pd

# ヘッダー有りの CSV ファイルを読み込む
df = pd.read_csv("input.csv")

# ヘッダー無しの CSV ファイルを読み込む
df = pd.read_csv("input.csv", header=None)

# ヘッダーが 2 行目にある場合
df = pd.read_csv("input.csv", header=1)
```


TSV ファイルを読み込む
----

タブ区切りファイル (TSV) を読み込む場合は、`pd.read_csv()` 関数でセパレーター文字 (__`sep`__) を指定するか、__`pd.read_table()`__ 関数を使用します。

{{< code lang="python" title="TSV ファイルを読み込む" >}}
df = pd.read_csv("input.tsv", sep="\t")
df = pd.read_table("input.tsv")  # 同上
{{< /code >}}


カラム名を設定する (names)
----

CSV/TSV ファイルから `DataFrame` オブジェクトを生成するときに __`names`__ 引数を指定すると、明示的にカラム名をセットすることができます。
この場合、CSV/TSV ファイルの最初の行からデータが始まっているとみなされるため、ヘッダー行を含む CSV/TSV ファイルを読み込む場合、ヘッダー行をうまいこと無視する必要があります。
__`header=0`__ を指定して 1 行目がヘッダー行であることを伝えるか、__`skiprows=1`__ で単純に 1 行スキップする方法が使えます。

{{< code lang="python" title="カラム名を設定する" >}}
# CSV ファイルにヘッダー行が存在しない場合
df = pd.read_csv("input.csv", names=["COL1", "COL2", "COL3"])

# CSV ファイルにヘッダー行が存在する場合
df = pd.read_csv("input.csv", names=["COL1", "COL2", "COL3"], header=0)
df = pd.read_csv("input.csv", names=["COL1", "COL2", "COL3"], skiprows=1)
{{< /code >}}

もちろん、`DataFrame` 生成後にカラム名を変更する方法でも OK です。

```python
df = pd.read_csv("input.csv")
df.columns = ["COL1", "COL2", "COL3"]
```

- 参考: [DataFrame のカラム名やインデックス名を変更する](/p/3g687f5/)


カンマの後ろのスペースを削除する (skipinitialspace)
----

CSV ファイル内のデータが、次のように「カンマ + スペース」で区切られている場合、デフォルトではスペースもデータの一部として扱われます。

{{< code lang="csv" title="input.csv" >}}
col1, col2, col3
100, AAA, 300
400, BBB, 600
700, CCC, 900
{{< /code >}}

たとえば、2 つ目のカラム名は `"col2"` ではなく `" col2"` とみなされてしまいます。
`read_csv()` 関数の引数として __`skipinitialspace=True`__ を指定すると、カンマの後のスペースを無視してくれます。

```python
df = pd.read_csv("input.csv", skipinitialspace=True)
```


特定の列をインデックス列として扱う (index_col)
----

例えば、CSV ファイルの先頭列に次のようにデータの ID (1, 2, 3, ...) が付いているとします。

{{< code lang="csv" title="input.csv" >}}
ID,col1,col2,col3
1,100,AAA,300
2,400,BBB,600
3,700,CCC,900
{{< /code >}}

`pd.read_csv()` 関数の __`index_col`__ パラメーターでカラム名を指定すると、そのカラムを `DataFrame` のインデックスとして取り込むことができます。

```
>>> df = pd.read_csv("input.csv", index_col="ID")
>>> df
    col1 col2  col3
ID
1    100  AAA   300
2    400  BBB   600
3    700  CCC   900
```

上記ではカラムの名前（文字列）を指定していますが、次のように数値で指定することもできます。

```python
df = pd.read_csv("input.csv", index_col=0)
```


特定の列だけを読み込む (usecols) {#usecols}
----

多数の列がある CSV ファイルを読み込む場合、必要な列だけを読み込むようにすればメモリ効率がよくなります。

{{< code lang="python" title="読み込む列を列番号で指定する（0 始まり）" >}}
df = pd.read_csv("input.csv", usecols=[0, 1, 3])
{{< /code >}}

{{< code lang="python" title="読み込む列を列名で指定する" >}}
df = pd.read_csv("input.csv", usecols=["col1", "col2", "col4"])
{{< /code >}}


各カラムのデータタイプを指定する (dtype)
----

各カラムのデータタイプは CSV に含まれている実際のデータから推測されますが、__`dtype`__ パラメーターでカラムごとに明示することができます。
適切なデータタイプを設定することで、メモリ使用量の節約に繋がります。

例えば、次のような 3 つのカラムを持つ CSV ファイルを読み込むとします。

{{< code lang="csv" title="input.csv" >}}
col1,col2,col3
100,A,120.5
200,B,236.3
300,C,398.2
400,A,437.8
500,B,525.8
600,C,671.1
{{< /code >}}

まずは `dtype` パラメーターなしで CSV ファイルを読み込んで、`DataFrame` の各カラムのデータタイプや、全体のメモリ使用量を __`df.info()`__ で確認してみます。

```
>>> df = pd.read_csv("input.csv")

>>> df.info()
<class 'pandas.core.frame.DataFrame'>
RangeIndex: 6 entries, 0 to 5
Data columns (total 3 columns):
 #   Column  Non-Null Count  Dtype
---  ------  --------------  -----
 0   col1    6 non-null      int64
 1   col2    6 non-null      object
 2   col3    6 non-null      float64
dtypes: float64(1), int64(1), object(1)
memory usage: 272.0+ bytes
```

データタイプ (`dtype`) を明示せずに読み込むと、上記のように、整数が `int64` 型、テキストが `object` 型、少数点数が `float64` 型と推測されて `DataFrame` が生成されます。
実際には、`col1` 列と `col3` 列の数値を表現するのに 64 ビットも必要ないかもしれませんし、`col2` 列は汎用的な `object` 型ではなく `category` 型（カテゴリカルデータ）として扱いたいかもしれません。
そのような場合は、`dtype` で次のようにデータタイプを指定します。

```
>>> df = pd.read_csv("input.csv", dtype={
...   "col1": "int32",
...   "col2": "category",
...   "col3": "float32"
... })

>>> df.info()
<class 'pandas.core.frame.DataFrame'>
RangeIndex: 6 entries, 0 to 5
Data columns (total 3 columns):
 #   Column  Non-Null Count  Dtype
---  ------  --------------  -----
 0   col1    6 non-null      int32
 1   col2    6 non-null      category
 2   col3    6 non-null      float32
dtypes: category(1), float32(1), int32(1)
memory usage: 206.0 bytes
```

適切なデータタイプを指定することにより、メモリ使用量を 272 バイトから 206 バイトまで減らすことができました。
巨大な CSV ファイルを読み込むときは、この `dtype` パラメーターと [`usecols` パラメーター](#usecols) を適切に指定することで、メモリ使用量を大幅に削減できます。

ちなみに、上記の例ではカラムごとにデータタイプを指定しましたが、`dtype=str` のように指定することで、すべてのカラムを文字列データとして扱うことができます。

