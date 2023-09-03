---
title: "DRAFT - pandas で DataFrame の統計値を求める方法のまとめ"
url: "p/q2pm7r3/"
date: "2023-09-03"
tags: ["pandas"]
draft: true
---

pandas による統計値の計算方法まとめ
----

### pandas の統計関数

pandas には様々な統計関数（集計関数）が定義されており、各列の統計値をまとめて取得することができるようになっています。
例えば、列の平均値を求めるには次のようにします。

{{< code lang="python" title="各列の平均値 (mean) を求める" >}}
df.mean()  # 列ごとに平均値を取得する（戻り値: Series 型）
df["列名"]  # 指定した列の平均値を取得する（戻り値: float64 型）
df[["列名1", "列名2"]]  # 複数の列の平均値を取得する（戻り値: Series 型）
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

[`DataFrame#describe()`](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.describe.html) で要約統計量 (descriptive statistics) を出力できます。
データ数、平均値、最小値・最大値、四分位数を一度に確認できます。

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

