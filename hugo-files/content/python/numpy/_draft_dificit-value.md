---
title: "DRAFT - pandas の DataFrame の欠損値 (NaN) の扱い方まとめ"
url: "p/rtc38u8/"
date: "2023-09-06"
tags: ["pandas"]
draft: true
---

チートシート（DataFrame の欠損値の扱い）
----

| 概要 | コード | 補足 |
| ---- | ---- | ---- |
| [非欠損値の数（全カラム）](#info) | `df.info()` | |
| [非欠損値の数（単一カラム）](#count) | `df["列"].count()` | |
| 欠損値を含む行を除去 | `df.dropna()`<br />`df.dropna(axis=0)` | `subset=["列1", "列2"]` という引数を追加することで対象列を絞ることが可能。 |
| 欠損値を含む列を除去 | `df.dropna(axis=1)` | `subset=["列1", "列2"]` という引数を追加することで対象列を絞ることが可能。 |
| [欠損値の補完](#fillna) | `df["列"].fillna(値)` | 欠損値部分を「値」に置き換えた `Series` が返されます。 |


Python コードでの欠損値 (NaN) の表現方法
----

NumPy の `np.nan` で欠損値を表すことができます。

```python
import numpy as np

print(np.nan)        # nan
print(type(np.nan))  # <class 'float'>
```

ちなみに、CSV ファイルを `pd.read_csv()` で読み込んだ場合も、その欠損値（何も記述されていないセル）は `np.nan` として表現されます。


非欠損値の数（全カラム） - df.info() {#info}
----

`DataFrame#info()` メソッドは `DataFrame` の概要情報を取得するメソッドですが、各カラムの Non-null 値（非欠損値）の数を調べることができます。

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
 3   age       360 non-null    float6
 4   sibsp     445 non-null    int64
 5   parch     445 non-null    int64
 6   fare      445 non-null    float6
 7   embarked  443 non-null    object
dtypes: float64(2), int64(4), object(2)
memory usage: 31.3+ KB
```

非欠損値の数（単一カラム） - df["列"].count() {#count}
----

特定の列の Non-null 値（非欠損値）の数をスカラー値（整数）で取得するには次のようにします。

{{< code title="例: age 列の Non-null 値の数を取得" >}}
>>> df["age"].count()
360
{{< /code >}}


欠損値の補完 {#fillna}
----

__`DataFrame#fillna(値)`__ メソッドは、`DataFrame` 内の欠損値 (NaN) 部分を指定した値で埋め合わせます。
機械学習において、欠損値が含まれている行をすべて削除 (`dropna`) してしまうと、学習に使用するデータ数が不足してしまうことがあります。
そのような場合は、欠損値を平均値や中央値で補完するというテクニックがあります。

```python
df["age"] = df["age"].fillna(df["age"].mean())  # 年齢の欠損値は平均値 (mean) で補う
df["salary"] = df["salary"].fillna(df["salary"].mode())  # 給料の欠損値は中央値 (mode) で補う
```

