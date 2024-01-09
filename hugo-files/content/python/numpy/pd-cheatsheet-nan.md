---
title: "pandas チートシート - DataFrame の欠損値 (NaN) の扱い方まとめ"
url: "p/rtc38u8/"
date: "2023-09-06"
tags: ["Python", "pandas"]
---

チートシート（DataFrame の欠損値の扱い）
----

| 概要 | コード |
| ---- | ---- |
| [欠損値を表現する](#nan) | `np.nan` |
| [欠損値部分を True、それ以外を False にする](#isnull) | `df2 = df.isnull()` |
| [欠損値以外の True、それ以外を True にする](#isnull) | `df2 = df.notnull()` |
| [欠損値を数える](#count-nan) | `df.isnull().sum()`<br/>`df.isnull().sum(axis=1)` |
| [非欠損値を数える](#count-nan) | `df.notnull().sum()`<br/>`df.count()`<br/>`df.info()` |
| [欠損値がある列／行を探す](#isnull-any) | `df.isnull().any()`<br/>`df.isnull().any(axis=1)` |
| [欠損値を含む「行」を削除する](#dropna-rows) | `df.dropna()`<br/>`df.dropna(axis=0)`<br/>`df.dropna(axis="index")` |
| [欠損値を含む「列」を削除する](#dropna-cols) | `df.dropna(axis=1)`<br/>`df.dropna(axis="columns")` |
| [欠損値を補完する](#fillna) | `df["列"] = df["列"].fillna(値)`<br/>`df["列"].fillna(値, inplace=True)` |

- `isnull()` は `isna()` のエイリアスです。


Python コードでの欠損値 (NaN) の表現方法 {#nan}
----

pandas では、値が存在しないことを欠損値 (NaN: Not a Number) が存在すると表現します（プログラム的には何らかの値で「値がない」ことを表現しないといけないため）。
欠損値は、NumPy の __`np.nan`__ で表現することができます。

```python
import numpy as np

print(np.nan)        # nan
print(type(np.nan))  # <class 'float'>
```

ちなみに、CSV ファイルを `pd.read_csv()` で読み込んだ場合、何も記述されていない部分に欠損値 (`np.nan`) が格納されます。

逆に値が存在することは、Non-null（非欠損値）と表現したりします。


欠損値部分とそれ以外の要素を True/False 値に変換する (isnull, notnull) {#isnull}
----

`DataFrame` の __`isnull()`__ メソッドを使うと、各データが欠損値かどうかを True/False の形で取得できます。

```python
import numpy as np
import pandas as pd

# テストデータ
df = pd.DataFrame({
  "title": ["Title-1", "Title-2", np.nan, "Title-4", "Title-5"],
  "price": [1000, np.nan, 3000, 4000, np.nan]
})

print(df.isnull())
```

{{< code title="実行結果" >}}
   title  price
0  False  False
1  False   True
2   True  False
3  False  False
4  False   True
{{< /code >}}

`isnull()` の代わりに __`notnull()`__ を使うと、True/False が反転した結果を得られます。

```python
print(df.notnull())
```

{{< code title="実行結果" >}}
   title  price
0   True   True
1   True  False
2  False   True
3   True   True
4   True  False
{{< /code >}}


欠損値・非欠損値を数える (isnull, notnull, sum, count, info) {#count-nan}
----

### 欠損値 (NaN) を数える

`df.isnull()` を実行すると、欠損値部分だけが True になった DataFrame を生成できます。
その True の数を __`sum()`__ で集計すれば、欠損値 (NaN) の数を数えることができます。

```
>>> df.isnull().sum()
title    1
price    2
dtype: int64
```

これで、`title` 列には欠損値が 1 つ、`price` 列には欠損値が 2 つ存在することが分かりました。

特定の列の欠損値 (NaN) の数をスカラー値（整数）で取得するには次のようにします。

{{< code title="例: price 列の欠損値 (NaN) の数を取得" >}}
>>> df["price"].isnull().sum()
2
{{< /code >}}

多くの場合、欠損値は列方向で集計しますが、__`sum(axis=1)`__ とすることで、行ごとの欠損値数を取得することもできます。

```
>>> df.isnull().sum(axis=1)
0    0
1    1
2    1
3    0
4    1
dtype: int64
```

### 非欠損値 (Non-null) を数える

`DataFrame` の __`count()`__ メソッドを使うと、各カラムの Non-null 値（非欠損値）の数を簡単に調べることができます。

```
>>> df.count()  # あるいは df.notnull().sum()
title    4
price    3
dtype: int64
```

特定の列の非欠損値 (Non-null) の数をスカラー値（整数）で取得するには次のようにします。

{{< code title="例: price 列の非欠損値 (Non-null) 値の数を取得" >}}
>>> df["title"].count()  # あるいは df["title"].nonnull().sum()
4
{{< /code >}}

非欠損値の数は、__`info()`__ メソッドの出力の Non-Null Count 列でも確認できます。

```
>>> df.info()
<class 'pandas.core.frame.DataFrame'>
RangeIndex: 5 entries, 0 to 4
Data columns (total 2 columns):
 #   Column  Non-Null Count  Dtype
---  ------  --------------  -----
 0   title   4 non-null      object
 1   price   3 non-null      float64
dtypes: float64(1), object(1)
memory usage: 208.0+ bytes
```


欠損値がある列／行を探す {#isnull-any}
----

`df.isnull()` による欠損値の抽出結果に対して、__`any()`__ メソッドを適用することで、欠損値の存在する列を調べることができます。

{{< code lang="python" hl_lines="10" >}}
import numpy as np
import pandas as pd

# テストデータ
df = pd.DataFrame({
    "title": ["Title-1", "Title-2", "Title-3"],
    "price": [1000, np.nan, np.nan],
})

print(df.isnull().any())
{{< /code >}}

{{< code title="実行結果" >}}
title    False
price     True
dtype: bool
{{< /code >}}

この結果から、`price` 列に欠損値が存在することが分かります。

欠損値の有無を行ごとに調べたいときは、`any()` メソッドの軸オプションで行方向 __`axis=1`__ を指定します。

```python
print(df.isnull().any(axis=1))
```

{{< code title="実行結果" >}}
0    False
1     True
2     True
dtype: bool
{{< /code >}}


欠損値を含む「行」を削除する {#dropna-rows}
----

欠損値を 1 つでも含む行を削除するには、__`df.dropna()`__ を使用します（あるいは __`df.dropna(axis=0)`__ や __`df.dropna(axis="index")`__ でも同様）。

```python
import numpy as np
import pandas as pd

# テストデータ
df = pd.DataFrame({
  "title": ["Title-1", "Title-2", np.nan, "Title-4", "Title-5"],
  "price": [1000, np.nan, 3000, 4000, np.nan]
})

df2 = df.dropna()
print(df2)
```

{{< code title="実行結果" >}}
     title   price
0  Title-1  1000.0
3  Title-4  4000.0
{{< /code >}}

欠損値の有無を調べる列を絞るには、__`subset=["列1", "列2"]`__ のようなオプションパラメーターで指定します。
指定する列が 1 つだけの場合は、リストではなく `subset="列1"` のように指定することもできます。

{{< code title="title 列に欠損値を含む行を削除" >}}
>>> df.dropna(subset=["title"])
     title   price
0  Title-1  1000.0
1  Title-2     NaN
3  Title-4  4000.0
4  Title-5     NaN
{{< /code >}}


欠損値を含む「列」を削除する {#dropna-cols}
----

欠損値を 1 つでも含むカラム（列）を丸ごと削除するには、__`df.dropna(axis=1)`__（あるいは __`df.dropna(axis="columns")`__）を使います。

```python
import numpy as np
import pandas as pd

# テストデータ
df = pd.DataFrame({
  "col1": ["AAA", "BBB", "CCC", "DDD", "EEE"],
  "col2": [100, np.nan, 300, 400, 500],
  "col3": [1.0, 2.0, np.nan, 4.0, 5.0]
})

df2 = df.dropna(axis=1)
print(df2)
```

`col2` 列と `col3` 列には欠損値 (NaN) が含まれているので、それらの列が丸ごと削除されます。

{{< code title="実行結果" >}}
  col1
0  AAA
1  BBB
2  CCC
3  DDD
4  EEE
{{< /code >}}

`col1` 列だけの `DataFrame` になってしまいました。。。

{{% private %}}
- `dropna(how='all')`
{{% /private %}}


欠損値を補完する {#fillna}
----

__`Series#fillna(値)`__ メソッドは、`Series` 内の欠損値 (NaN) 部分を指定した値に置き換えた `Series` を返します。
__`DataFrame#fillna(値)`__ メソッドも使えますが、通常は特定列の `Series` データに対して使うことになると思います。

機械学習において、欠損値が含まれている行をすべて削除 (`dropna()`) してしまうと、学習に使用するデータ数が不足してしまうことがあります。
そのような場合は、欠損値を平均値や中央値で補完するというテクニックがあります。

{{< code lang="python" title="age 列の欠損値部分に 0 を入れる" >}}
df["age"].fillna(0, inplace=True)
df["age"] = df["age"].fillna(0)  // 同上
{{< /code >}}

{{< code lang="python" title="age 列の欠損値を平均値 (mean) で補う" >}}
df["age"].fillna(df["age"].mean(), inplace=True)
df["age"] = df["age"].fillna(df["age"].mean())  // 同上
{{< /code >}}

{{< code lang="python" title="age 列の欠損値を中央値 (mode) で補う" >}}
df["age"].fillna(df["age"].mode(), inplace=True)
df["age"] = df["age"].fillna(df["age"].mode())  // 同上
{{< /code >}}

{{% private %}}
- `df.fillna(method="ffill")` / `df.ffill()`
- `df.asfreq("4h")`
{{% /private %}}

