---
title: "pandas の DataFrame のカラムのデータ型を変更する (df.astype)"
url: "p/fk2e74z/"
date: "2023-10-23"
tags: ["pandas"]
---

Series と DataFrame の as_type メソッド
----

pandas の Series/DataFrame オブジェクトのデータ型を変換するには、__`astype()`__ メソッドを使用します。

- 参考: [pandas.Series.astype](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.Series.astype.html)
- 参考: [pandas.DataFrame.astype](https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.astype.html)

`Series` の `astype()` メソッドは、指定したデータ型に変換された新しい `Series` オブジェクトを返します。

{{< code lang="python" title="Series のデータ型変換" hl_lines="2" >}}
ser1 = pd.Series([1, 2, 3], dtype="int64")  # = np.int64
ser2 = ser1.astype("float64")  # = np.float64

print(ser1.dtype)  # int64
print(ser2.dtype)  # float64
{{< /code >}}

`DataFrame` の `astype()` メソッドを使用すると、複数カラムのデータ型をまとめて変換することができます。

{{< code lang="python" title="DataFrame のデータ型変換" hl_lines="6" >}}
data = {
    "列1": [1, 2, 3, 4],
    "列2": [1.5, 2.8, 3.1, 4.3],
}
df1 = pd.DataFrame(data)
df2 = df1.astype({"列1": "float64", "列2": "int32"})

print(df2.dtypes)  # int64 float64
print(df2.dtypes)  # floay64 int32
{{< /code >}}

もちろん、次のように `DataFrame` から個々の列を `Series` として取り出してから変換するのもありです。

```python
df["列"] = df["列"].astype("float64")
```


整数にするときの切り捨て／切り上げ／四捨五入
----

`astype()` メソッドを使って浮動小数点数型 (`float`) のデータを整数型 (`int`) のデータに変換する場合、デフォルトでは少数点数以下は切り捨てられます。
少数点数以下を切り上げたり、四捨五入したい場合は、先に `Series` オブジェクトに対して、__`np.ceil()`__ や __`np.round()`__ などの NumPy 関数を適用しておきます。

```python
df["列"] = df["列"].astype(int)            # 切り捨て（デフォルト）
df["列"] = np.floor(df["列"]).astype(int)  # 切り捨て（同上）
df["列"] = np.ceil(df["列"]).astype(int)   # 切り上げ
df["列"] = np.round(df["列"]).astype(int)  # 四捨五入
```


具体例
----

次の例では、サンプルの DataFrame を作成して、その中の数値カラムの型を変換しています。

{{< code lang="python" title="DataFrame のデータタイプ変換" hl_lines="17" >}}
import pandas as pd

# サンプルの DataFrame を作成
data = {
    "col-1": [1, 2, 3, 4],
    "col-2": [1.5, 2.8, 3.1, 4.3],
    "col-3": ["A", "B", "C", "D"]
}
df = pd.DataFrame(data)

print("変換前 ====================")
print(df.dtypes)
print()
print(df)

# col-1 列を浮動小数点数 (float64)、col-2 列を整数 (int64) に変換
df = df.astype({"col-1": "float64", "col-2": "int64"})

print("\n変換後 ====================")
print(df.dtypes)  # カラムのデータ型を確認
print()
print(df)
{{< /code >}}

{{< code title="実行結果" >}}
変換前 ====================
col-1      int64
col-2    float64
col-3     object
dtype: object

   col-1  col-2 col-3
0      1    1.5     A
1      2    2.8     B
2      3    3.1     C
3      4    4.3     D

変換後 ====================
col-1    float64
col-2      int64
col-3     object
dtype: object

   col-1  col-2 col-3
0    1.0      1     A
1    2.0      2     B
2    3.0      3     C
3    4.0      4     D
{{< /code >}}

