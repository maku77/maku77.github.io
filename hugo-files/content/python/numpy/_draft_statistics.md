---
title: "DRAFT - pandas で DataFrame を解析する方法まとめ（統計＆集計）"
url: "p/q2pm7r3/"
date: "2023-09-03"
tags: ["pandas"]
draft: true
---

チートシート（DataFrame の情報を取得）
----

| 概要 | コード | 補足 |
| ---- | ---- | ---- |
| [要約情報](#info) | `df.info()` | カラム名、Non-Null 数、データタイプ、メモリ使用量 |
| [形状](#shape) | `df.shape` | 行数と列数のタプル |
| データ数（欠損値を含む） | `len(df)` | `df.shape[0]` と同じ |
| [データ数（欠損値を含まない）](#count) | `df["列"].count()` | 非欠損値のみをカウント |
| [主要な統計値](#describe) | `df.describe()` | Non-null 数、平均値、標準偏差、最小値、最大値、四分位数 |
| [ユニークな要素を列挙](#unique) | `df["列"].unique()` | |
| [ユニークな要素をカウント](#value_counts) | `df["列"].value_counts()` | |
| [条件に一致する値をカウント](#condition_sum) | `(df["列"] == 値).sum()` | |
| [列と列の相関係数](#corrwith) | `df.corrwith(df["列"])` | |
| [グループ化してから集計](#groupby) | `df.groupby(["列"]).mean()` | `mean()` の部分は任意の統計関数に置換可 |


DataFrame の基本情報
----

### DataFrame#info（要約情報） {#info}

```
>>> df.info()

<class 'pandas.core.frame.DataFrame'>
Int64Index: 445 entries, 3 to 888
Data columns (total 8 columns):
 #   Column    Non-Null Count  Dtype
---  ------    --------------  -----
 0   survived  445 non-null    int64
 1   pclass    445 non-null    int64
 2   sex       445 non-null    object
 3   age       360 non-null    float64
 4   sibsp     445 non-null    int64
 5   parch     445 non-null    int64
 6   fare      445 non-null    float64
 7   embarked  443 non-null    object
dtypes: float64(2), int64(4), object(2)
memory usage: 31.3+ KB
```

### DataFrame#shape（形状） {#shape}

```python
# 行数と列数
>>> df.shape
(200, 5)

# 行数
>>> df.shape[0]
200

# 列数
>>> df.shape[1]
5
```


データ数のカウント
----

{{< code lang="python" title="テストデータ" >}}
>>> df = pd.DataFrame({"fruit": ["apple", "banana", "apple", np.nan, "orange", "banana", "apple"]})
{{< /code >}}

### 要素数（非欠損値）をカウント {#count}

`fruit` 列にいくつのデータがあるかを数えます（欠損値はカウントしません）。

```python
>>> df["fruit"].count()
fruit    6
dtype: int64
```

### ユニークな要素を取得 {#unique}

`fruit` 列からユニークな要素を配列形式で抽出します。

```python
>>> df["fruit"].unique()
array(['apple', 'banana', nan, 'orange'], dtype=object)
```

### ユニークな要素ごとにカウント {#value_counts}

`fruit` 列のユニークな値ごとに出現回数をカウントします。

```python
>>> df["fruit"].value_counts()
fruit
apple     3
banana    2
orange    1
Name: count, dtype: int64
```

出現回数ではなく、割合で取得したい場合は、__`normalize=True`__ オプションを指定します。

```python
>>> df["fruit"].value_counts(normalize=True)
fruit
apple     0.500000
banana    0.333333
orange    0.166667
Name: proportion, dtype: float64
```

### 条件に一致する値の数 {#condition_sum}

`fruit` 列の値が `apple` であるデータ数をカウントします。

```python
>>> (df["fruit"] == "apple").sum()
3
```


相関係数 {#corrwith}
----

相関係数は、1 に近いほど正の相関が強く、-1 に近いほど負の相関が強いことを表します。
相関係数が 0 の場合は、その 2 つの列にはまったく相関がないことを表します。

```python
# ある列との相関係数を調べる（すべての列は数値型である必要がある）
df.corrwith(df["列名"])

# ある列との相関係数を調べる（先にカテゴリ変数を 0/1 の数値列に変換する）
pd.get_dummies(df).corrwith(df["列名"])
```


グループ化してから集計 {#groupby}
----

```
# pclass 列の値で行をグループ化してから各列の平均値を求める
>>> df.groupby(["pclass"]).mean()

# 出力列を絞りたい場合
>>> df[["pclass", "survived"]].groupby(["pclass"]).mean()
```

`DataFrame#groupby()` メソッドの戻り値は `DataFrame` ではなく、グループ化情報を示す `DataFrameGroupBy` オブジェクトであることに注意してください。


pandas による統計値の計算方法まとめ
----

### pandas の統計関数

pandas には様々な統計関数（集計関数）が定義されており、各列の統計値をまとめて取得することができるようになっています。
例えば、列の平均値を求めるには次のようにします。

{{< code lang="python" title="各列の平均値 (mean) を求める" >}}
df.mean()  # 列ごとに平均値を取得する（戻り値: Series 型）
df["列名"].mean()  # 指定した列の平均値を取得する（戻り値: float64 型）
df[["列名1", "列名2"]].mean()  # 複数の列の平均値を取得する（戻り値: Series 型）
{{< /code >}}

他にもいろいろな統計関数があります。

| 関数名 | 説明 |
| ---- | ---- |
| `df.count()` | 非欠損値の数をカウントします。 |
| `df.sum()` | 値の合計を計算します。 |
| `df.min()` | 最小値を返します。 |
| `df.max()` | 最大値を返します。 |
| `df.mean()` | 平均値を計算します。 |
| `df.median()`<br>`df.quantile(0.5)` | 中央値（50 パーセンタイル）を計算します。 |
| `df.quantile(0.25)` | 25 パーセンタイルを計算します。 |
| `df.quantile(0.75)` | 75 パーセンタイルを計算します。 |
| `df.std()` | 標準偏差を計算します。 |
| `df.var()` | 分散を計算します。 |
| `df.mode()` | 最頻値を返します。 |

{{% private %}}
```
累積関数:
  cumsum(): 累積合計を計算します。
  cumprod(): 累積積を計算します。
  cummax(): 累積最大値を計算します。
  cummin(): 累積最小値を計算します。

その他の関数:
  nunique(): ユニークな値の数をカウントします。
  value_counts(): 各ユニーク値の出現回数をカウントします。
  describe(): データの要約統計量を表示します。
  corr(): 列間の相関係数を計算します。
  cov(): 列間の共分散を計算します。

集約関数:
  agg(): 一つ以上の操作を列に適用します。
  groupby(): 特定の列に基づいてデータをグループ化し、各グループに対して集計操作を適用します。
```
{{% /private %}}

### 基本的な統計値を取得する (describe) {#describe}

[`DataFrame#describe()`](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.describe.html) メソッドを使うと、次のような要約統計量 (descriptive statistics) をまとめて取得できます。

- `count` ... （非欠損値）データ数
- `unique` ... ユニークな要素数 ※
- `top` ... 最頻値 ※
- `freq` ...最頻値の出現回数 ※
- `mean` ... 平均
- `std` ... 標準偏差
- `min` ... 最小値
- `25%` ... 25 パーセンタイル（第 1 四分位数）
- `50%` ... 50 パーセンタイル（中央値）
- `75%` ... 75 パーセンタイル（第 3 四分位数）
- `max` ... 最大値

※ デフォルトでは数値カラムの要約統計量のみを算出するので、`unique`、`top`、`freq` などは表示されません。テキストデータなどの統計も求めたいときは、`include="all"` オプションを指定します。

```python
>>> df.describe()
              X1         X2         X3         X4         X5
count   5.000000   5.000000   5.000000   5.000000   5.000000
mean   11.000000  12.000000  13.000000  14.000000  15.000000
std     7.905694   7.905694   7.905694   7.905694   7.905694
min     1.000000   2.000000   3.000000   4.000000   5.000000
25%     6.000000   7.000000   8.000000   9.000000  10.000000
50%    11.000000  12.000000  13.000000  14.000000  15.000000
75%    16.000000  17.000000  18.000000  19.000000  20.000000
max    21.000000  22.000000  23.000000  24.000000  25.000000
```

特定の列に絞って出力することも可能です。

```python
df["列名"].describe()  # 単一の列の統計
df["列名1", "列名2", "列名3"]].describe()  # 複数の列の統計
```

### 指定した統計値をまとめて取得する (agg)

[`DataFrame#agg()`](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.agg.html) を使うと、指定した統計値（例: `max` や `mean` など）をまとめて取得できます。
[`describe()`](#describe) のカスタマイズバージョンのようなものです。
NumPy の関数や、pandas の `Series`/`DataFrame` オブジェクトのメソッドであれば、大体は集計関数として指定できます。

{{< code lang="python" title="各列の平均値、中央値、合計値を取得する" >}}
>>> df.agg(["mean", "median", "sum"])
          X1    X2    X3    X4    X5
mean    11.0  12.0  13.0  14.0  15.0
median  11.0  12.0  13.0  14.0  15.0
sum     55.0  60.0  65.0  70.0  75.0
{{< /code >}}

単一の集計関数を指定する場合は、その指定方法によって `Series` と `DataFrame` のどちらの型で返されるかが変わります。

```python
df.agg("mean")  # Series で取得（df.mean() と同じ）
df.agg(["mean"])  # DataFrame で取得
```

列ごとに取得する統計値を指定することもできます。

```python
df.agg({
    "列名1": ["min", "max", "median", "skew"],
    "列名2": ["min", "max", "median", "mean"],
})
```

次のように任意の集計関数を指定することもできます。

{{< code lang="python" title="各列の 25%タイル、中央値、75%タイルを求める" >}}
df.agg([
    lambda x: x.quantile(0.25),
    np.median,  # "median" と文字列で指定するのと同様
    lambda x: x.quantile(0.75)
])
{{< /code >}}

