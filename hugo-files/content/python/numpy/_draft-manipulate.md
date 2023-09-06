---
title: "DRAFT - pandas で DataFrame を加工する方法のまとめ"
url: "p/m4ggdkx/"
date: "2023-09-04"
tags: ["pandas"]
draft: true
---

DataFrame 加工のチートシート
----

<table>
  <thead>
    <tr>
      <th>カテゴリ</th>
      <th>コード</th>
      <th>説明</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><a href="#delete-column">カラムの削除</a></td>
      <td>
        <code>df2 = df.drop("列名", axis=1)</code><br />
        <code>del df["列名"]</code><br />
        <code>df.pop("列名")
      </td>
      <td>
        <code>del</code> と <code>pop</code> は元の <code>DataFrame</code> を変更します（破壊的操作）。
        <code>pop</code> は削除した列を <code>Series</code> として返します。
      </td>
    </tr>
    <tr>
      <td><a href="#get_dummies">値ごとに 0/1 表現の列を作成する</a></td>
      <td>
        <code>df2 = pd.get_dummies(df)</code><br />
        <code>df2 = pd.get_dummies(df, columns=["列1", "列2"])</code>
      </td>
      <td></td>
    </tr>
  </tbody>
</table>


カラムの削除 {#delete-column}
----

```python
import pandas as pd

# DataFrameを作成
df = pd.DataFrame({'A': [1, 2, 3], 'B': [4, 5, 6], 'C': [7, 8, 9]})

# 'B'カラムを削除
df = df.drop('B', axis=1)
```


値ごとに True/False (0/1) のカラムを生成 {#get_dummies}
----

__`pd.get_dummies()`__ 関数を使うと、カテゴリ変数（典型的には文字列型）のカラムを True or False (0 or 1) 情報のカラムに変換した `DataFrame` を生成できます。
ロジスティック回帰モデルなどを使用した機械学習において、入力データを数値に変換する際に `pd.get_dummies()` 関数を使うと便利です。

次の例では、`col1` カラムを `col1_AAA`、`col1_BBB`、`col1_CCC` という 3 つのカラムに変換し、`col2` カラムを `col2_X`、`col2_Y` という 2 つのカラムに変換しています。

```python
df = pd.DataFrame({
    "col1": ["AAA", "BBB", "CCC", "BBB", "AAA"],
    "col2": ["X", "Y", np.nan, "X", np.nan]
})
df2 = pd.get_dummies(df)
print(df2)
```

{{< code title="実行結果" >}}
   col1_AAA  col1_BBB  col1_CCC  col2_X  col2_Y
0      True     False     False    True   False
1     False      True     False   False    True
2     False     False      True   False   False
3     False      True     False    True   False
4      True     False     False   False   False
{{< /code >}}

欠損値 (NaN) のカラムも生成するには、__`dummy_na=True`__ パラメーターを追加します。

```
>>> pd.get_dummies(df, dummy_na=True)
   col3  col1_AAA  col1_BBB  col1_CCC  col1_nan  col2_X  col2_Y  col2_nan
0     1      True     False     False     False    True   False     False
1     2     False      True     False     False   False    True     False
2     3     False     False      True     False   False   False      True
3     4     False      True     False     False    True   False     False
4     5      True     False     False     False   False   False      True
```

デフォルトでは上記のように `bool` 型のカラムに変換されますが、__`dtype=int`__ を指定することで、`int` 型 (0 or 1) のカラムに変換することができます。

```
>>> pd.get_dummies(df, dtype=int)
   col3  col1_AAA  col1_BBB  col1_CCC  col2_X  col2_Y
0     1         1         0         0       1       0
1     2         0         1         0       0       1
2     3         0         0         1       0       0
3     4         0         1         0       1       0
4     5         1         0         0       0       0
```


新しいカラムの作成（あるいは上書き）
----

```python
# 既存の列の値をもとにして新しい列を作成（既存の DataFrame に追加）
df["NewCol"] = df["列名"] + 100
df["NewCol"] = df["列1"] * df["列2"]

# 同上（ただし新しい DataFrame を作成する）
df.assign(NewCol=df["列名"] + 100)
df.assign(NewCol=df["列1"] * df["列2"])

# 任意の変換関数を適用する（下記は np.sqrt 関数で平方根を生成する例）
df["NewCol"] = df["列名"].apply(np.sqrt)
```


一致する値を置換する (replace)
----

```python
# 値に含まれている文字列をまとめて置換
df["列名"].replace(置換前の値, 置換後の値)
```


特定の列だけを抜き出した DataFrame を作成する
----

```python
df = df[["列名1", "列名2", "列名3]]  # 着目している列だけの DataFrame にする
```

- 参考: [pandas.DataFrame からのデータ抽出方法のチートシート](/p/rnai4ko/)


