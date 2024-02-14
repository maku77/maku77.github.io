---
title: "DRAFT: pandas チートシート - DataFrame の集計方法まとめ（統計＆集計）"
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
| [形状](#shape) | `df.shape` | `(行数, 列数)` のタプル |
| [形状](#shape) | `df.size` | 行数 x 列数 |
| [レコード数（欠損値を含む）](#len) | `len(df)`<br/>`df.shape[0]` | 行数 |
| [レコード数（欠損値を含まない）](#count) | `df["列"].count()`<br/>`df.count()` | 非欠損値のみをカウント |
| [主要な統計値](#describe) | `df.describe()` | Non-null 数、平均値、標準偏差、最小値、最大値、四分位数 |
| [ユニークな要素を列挙](#unique) | `df["列"].unique()` | |
| [ユニークな要素の種類をカウント](#nunique) | `df["列"].nunique()`<br/>`df.nunique()` | |
| [ユニークな値ごとにカウント](#value_counts) | `df["列"].value_counts()`<br/>`df.value_counts()` | |
| 重複するレコードをカウント | `df.duplicated().sum()` |
| [条件に一致する値をカウント](#conditional_sum) | `(df["列"] >= 値).sum()` | |
| [列と列の相関係数](#corrwith) | `df.corrwith(df["列"])` | |
| [グループ化してから集計](#groupby) | `df.groupby(["列"]).mean()` | `mean()` の部分は任意の統計関数に置換可 |
| あるカラムの値を合計 | `df["列"].sum()` |
| あるカラムの平均値 | `df["列"].mean()` |


DataFrame の要約情報を取得する (info) {#info}
----

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


DataFrame の形状を調べる (shape) {#shape}
----

__`DataFrame#shape`__ 属性は、`DataFrame` の形状（行数, 列数）を表すタプルです。

```python
df = pd.DataFrame({"X1": [1, 2, 3, 4], "X2": [5, 6, 7, 8]})

print(df.shape)     #=> (4, 2)
print(df.shape[0])  #=> 4（行数）
print(df.shape[1])  #=> 2（列数）
```

__`DataFrame#size`__ 属性で「行数 x 列数」の値を調べることができますが、こちらの使用頻度はあまり高くないでしょう。

```python
print(df.size)  #=> 8
```


レコード数（行数）をカウント (len, shape[0]) {#len}
----

__`len()`__ 関数に `DataFrame` を渡すと、全体のデータ数（行数）を取得することができます。
`df.shape[0]` でデータ形状を調べるのと同様です。

```python
df = pd.DataFrame({"X1": [1, 2, 3, 4, 5], "X2": [6, 7, 8, 9, 10]})

print(len(df))      #=> 5（行数）
print(df.shape[0])  #=> 5（行数）
```

レコード数（欠損値を除く）をカウント (count) {#count}
----

__`Series#count()`__ メソッドを使って、`Series` 内にいくつの有効な値（非欠損値）が含まれているかを調べることができます。

```python
df = pd.DataFrame(
    {
        "fruit": ["apple", "banana", np.nan, "banana", "apple"],
        "weight": [200, np.nan, np.nan, 500, 300],
    }
)

print(df["fruit"].count())   #=> 4
print(df["weight"].count())  #=> 3
```

__`DataFrame#count()`__ メソッドを使うと、`DataFrame` 内のすべてのカラムのデータ数をしらべることができます。
戻り値は `Series` オブジェクトです。

```python
print(df.count())
```

{{< code title="実行結果" >}}
fruit     4
weight    3
dtype: int64
{{< /code >}}


ユニークな要素を取得 (unique) {#unique}
----

__`Series#unique()`__ メソッドを使用すると、その `Series` 内のユニークな値を列挙できます。
戻り値は NumPy 配列 (ndarray) になります。

```python
df = pd.DataFrame(
    {
        "fruit": ["apple", "banana", np.nan, "banana", "apple"],
        "weight": [200, np.nan, np.nan, 500, 300],
    }
)

print(df["fruit"].unique())    #=> ["apple", "banana", nan]
print(df["weight"].unique())   #=> [200., nan, 500., 300.]
```

ちなみに、`DataFrame` には `unique()` メソッドはありません。


ユニークな要素が何種類あるかをカウント (nunique) {#nunique}
----

__`Series#unique()`__ メソッドを使うと、`Series` 内に何種類のユニークな値があるかをカウントできます。
デフォルトでは NaN はカウントしませんが、`dropna=False` を指定することで NaN も 1 つのユニークな値としてカウントできます。

```python
df = pd.DataFrame(
    {
        "fruit": ["apple", "banana", np.nan, "banana", "apple"],
        "weight": [200, np.nan, np.nan, 500, 300],
    }
)

print(df["fruit"].nunique())               # => 2
print(df["fruit"].nunique(dropna=False))   # => 3
print(df["weight"].nunique())              # => 3
print(df["weight"].nunique(dropna=False))  # => 4
```

__`DataFrame#nunique()`__ メソッドを使用すると、`DataFrame` 内の全ての列を一度にカウントできます。
戻り値は `Series` オブジェクトになります。

```python
print(df.nunique())
# fruit     2
# weight    3
# dtype: int64

print(df.nunique(dropna=False))
# fruit     3
# weight    4
# dtype: int64
```


ユニークな値ごとにカウント (value_counts) {#value_counts}
----

__`Series#value_counts()`__ メソッドを使用すると、`Series` 内のユニーク値ごとに出現回数をカウントできます。
戻り値は `Series` オブジェクトになります。

```python
df = pd.DataFrame(
    {
        "fruit": ["apple", "banana", "orange", "banana", "apple"],
        "rank": ["A", "B", "B", "A", "A"],
    }
)

print(df["fruit"].value_counts())
# fruit
# apple     2
# banana    2
# orange    1
# Name: count, dtype: int64

print(df["rank"].value_counts())
# rank
# A    3
# B    2
# Name: count, dtype: int64
```

出現回数ではなく、割合で取得したい場合は、__`normalize=True`__ オプションを指定します。

```python
print(df["fruit"].value_counts(normalize=True))
# fruit
# apple     0.4
# banana    0.4
# orange    0.2
# Name: proportion, dtype: float64

print(df["rank"].value_counts(normalize=True))
# rank
# A    0.6
# B    0.4
# Name: proportion, dtype: float64
```

__`DataFrame#value_counts()`__ メソッドを使用すると、各列の値の組み合わせで、ユニークなデータをカウントできます。

```python
print(df.value_counts())
# fruit   rank
# apple   A       2
# banana  A       1
#         B       1
# orange  B       1
# Name: count, dtype: int64
```


条件に一致する値の数をカウント {#conditional_sum}
----

次の例では、`fruit` 列の値が `apple` であるレコードの数と、`price` 列の値が 200 以下であるレコードの数をカウントしています。

```python
df = pd.DataFrame(
    {
        "fruit": ["apple", "banana", "orange", "banana", "apple"],
        "price": [100, 200, 250, 150, 200],
    }
)

print((df["fruit"] == "apple").sum())  #=> 2
print((df["price"] <= 200).sum())  #=> 4
```

内部的には、`True`/`False` の一次元 `Series` を生成し、`True` の数を合計するという処理になっています。
ちなみに次のようにすれば、条件で絞り込んだ `DataFrame` を作成することができます。

```python
# fruit 列の値が apple のレコードだけで構成された DataFrame を作成
df2 = df[df["fruit"] == "apple"]
```


複数列の条件を AND でまとめることもできます。

```python
print(((df["fruit"] == "apple") & (df["price"] <= 200)).sum())  #=> 2
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

__`groupby()`__ で特定の列の値でグループ化してから各フィールドを集計することができます。
例えば、性別 (sex) のフィールドがある場合は、男性と女性で分けて統計値を求めることができます。

{{< code lang="python" title="例: 性別ごとに各フィールドの平均値を求める" >}}
import pandas as pd

def load_titanic() -> pd.DataFrame:
    """Titanic データをロード"""
    from sklearn.datasets import fetch_openml
    data = fetch_openml(name="titanic", version=1, as_frame=True, parser="auto")
    return data.frame

df = load_titanic()

# "sex" 列の値でグループ化し、グループごとに各フィールドの平均値を求める
mean_by_sex = df.groupby("sex").mean(numeric_only=True)
print(mean_by_sex)
{{< /code >}}

{{< code title="実行結果" >}}
          pclass        age     sibsp     parch       fare       body
sex
female  2.154506  28.687071  0.652361  0.633047  46.198097  166.62500
male    2.372479  30.585233  0.413998  0.247924  26.154601  160.39823
{{< /code >}}

特定の列（例: 年齢）の平均値だけが欲しければ次のように記述します。

```python
# "sex" 列の値でグループ化し、グループごとに「年齢」の平均値を求める
age_mean_by_sex = df.groupby("sex")["age"].mean()
print(age_mean_by_sex)
```

{{< code title="実行結果" >}}
sex
female    28.687071
male      30.585233
Name: age, dtype: float64
{{< /code >}}

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

