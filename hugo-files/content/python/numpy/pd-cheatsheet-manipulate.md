---
title: "pandas チートシート - DataFrame のデータ加工方法まとめ"
url: "p/m4ggdkx/"
date: "2023-09-04"
tags: ["Python", "pandas"]
---

DataFrame 加工のチートシート
----

<table>
  <thead>
    <tr>
      <th>カテゴリ</th>
      <th>コード</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>新しいカラムの作成（すべて同じ値）</td>
      <td><code>df["列"] = スカラー値</code></td>
    </tr>
    <tr>
      <td><a href="#new-column">新しいカラムの作成（<code>Series</code> の代入）</a></td>
      <td><code>df["列"] = シリーズ</code><br/><code>df.assign(列=シリーズ)</code></td>
    </tr>
    <tr>
      <td><a href="#drop-column">カラムの削除</a></td>
      <td>
        <code>df2 = df.drop(columns="列")</code><br />
        <code>df2 = df.drop("列", axis=1)</code><br />
        <code>del df["列名"]</code><br />
        <code>df.pop("列名")
      </td>
    </tr>
    <tr>
      <td><a href="#drop-row">行の削除</a></td>
      <td>
        <code>df2 = df.drop(index="行")</code><br />
        <code>df2 = df.drop("行", axis=0)</code>
      </td>
    </tr>
    <tr>
      <td><a href="#conditional-delete">行の削除（条件指定）</a></td>
      <td>
        <code>df2 = df[df["列"] != 値]</code>
      </td>
    </tr>
    <tr>
      <td><a href="#replace">値の置換</a></td>
      <td><code>df["列"].replace(置換前の値, 置換後の値)</code></td>
    </tr>
    <tr>
      <td><a href="#extract">カラムの抽出</a></td>
      <td><code>df2 = df[["列1", "列2", "列3"]]</code></td>
    </tr>
    <tr>
      <td>DataFrame のコピー</td>
      <td><code>df2 = df.copy()  # shallow copy</code><br/><code>df2 = df.copy(deep=True)</code></td>
    </tr>
    <tr>
      <td><a href="#sort_values">特定のカラムでソート</a></td>
      <td>
        <code>df2 = pd.sort_values(by="列")</code><br />
        <code>df2 = df.sort_values(by="列", ascending=False)</code>
      </td>
    </tr>
    <tr>
      <td><a href="#get_dummies">値ごとに 0/1 表現の列を作成する</a></td>
      <td>
        <code>df2 = pd.get_dummies(df)</code><br />
        <code>df2 = pd.get_dummies(df, columns=["列1", "列2"])</code>
      </td>
    </tr>
    <tr>
      <td><a href="/p/3g687f5/">カラム名／インデックス名の設定と変更</a></td>
      <td>
        <code>df.columns = [...]</code><br />
        <code>df.index = [...]</code><br />
        <code>df2 = df.rename(colums={...})</code><br />
        <code>df2 = df.rename(index={...})</code>
      </td>
    </tr>
    <tr>
      <td><a href="/p/fk2e74z/">各カラムのデータ型を変更する</a></td>
      <td>
        <code>df2 = df.astype({"列1": "int64", "列2": "float64"})</code>
      </td>
      </td>
    </tr>
  </tbody>
</table>


新しいカラムの作成（あるいは上書き） {#new-column}
----

```python
# 既存の列の値をもとにして新しい列を作成（既存の DataFrame に追加）
df["NewCol"] = df["列"] + 100
df["NewCol"] = df["列1"] + df["列2"]

# 同上（ただし新しい DataFrame を作成する）
df.assign(NewCol=df["列"] + 100)
df.assign(NewCol=df["列1"] + df["列2"])

# 任意の変換関数を適用する（下記は np.sqrt 関数で平方根を生成する例）
df["NewCol"] = df["列"].apply(np.sqrt)
```


一致する値を置換する (replace) {#replace}
----

```python
# 値に含まれている文字列をまとめて置換
df["列"].replace(置換前の値, 置換後の値)
```


特定の列だけを抜き出した DataFrame を作成する {#extract}
----

```python
df2 = df[["列1", "列2", "列3"]]  # 着目している列だけの DataFrame にする
```

- 参考: [pandas.DataFrame からのデータ抽出方法のチートシート](/p/rnai4ko/)



カラムの削除 (drop, del, pop) {#drop-column}
----

`df.drop()` メソッドを使うと、指定したカラムを削除した新しい `DataFrame` を取得できます。

{{< code lang="python" title="drop によるカラムの削除" >}}
import pandas as pd

# テストデータ
df = pd.DataFrame({"A": [1, 2, 3], "B": [4, 5, 6], "C": [7, 8, 9]})

# A カラムを削除
df2 = df.drop(columns="A")
df2 = df.drop("A", axis=1)  # 同上

# A カラムと B カラムを削除
df2 = df.drop(columns=["A", "B"])
df2 = df.drop(["A", "B"], axis=1)  # 同上
{{< /code >}}

Python のリスト API である `del` や `pop` でもカラムの削除を行うことができますが、これらは元の `DataFrame` を変更します（破壊的操作）。
`pop` は削除した列を `Series` として返します。

{{< code lang="python" title="del と pop によるカラムの削除" >}}
# A カラムを削除 （df 自身を変更）
del df["A"]

# 同上だが削除したカラムを Series として返す
series = df.pop("A")
{{< /code >}}

`df.drop()` メソッドでも、__`inplace=True`__ を指定すれば、元の `DataFrame` を変更できます。
この場合、戻り値は `None` になります。

```python
# 元の DataFrame 自身を変更する
df.drop(columns=["A", "B"], inplace=True)
```


行の削除 (drop) {#drop-row}
----

{{< code lang="python" title="drop による行の削除" >}}
import pandas as pd

# テストデータ
df = pd.DataFrame({"A": [1, 2, 3], "B": [4, 5, 6], "C": [7, 8, 9]})

# 先頭行を削除（インデックスラベルが設定されてない場合）
df2 = df.drop(0)
df2 = df.drop(0, axis=0)  # 同上
df2 = df.drop(index=0)    # 同上

# 先頭行を削除（インデックスラベルが設定されている場合）
df2 = df.index = ["idx1", "idx2", "idx3"]
df2 = df.drop("idx1")
df2 = df.drop("idx1", axis=0)  # 同上
df2 = df.drop(index="idx1")    # 同上

# 複数の行をまとめて削除することも可能
df2 = df.drop(index=[0, 1, 2])
df2 = df.drop(index=["idx1", "idx2", "idx3"])
{{< /code >}}

`df.drop()` はデフォルトでは新しい `DataFrame` を返しますが、__`inplace=True`__ を指定すると元の `DataFrame` を変更できます。
この場合、戻り値は `None` になります。

```python
# 先頭の行を削除 （元の df 自身を変更）
df.drop(index=0, inplace=True)
```


条件に一致する行を削除 {#conditional-delete}
----

これは発想の転換ですが、「ある列の値が "A" 以外である行を抽出する」という操作は、「ある列の値が "A" である行を削除する」という操作になります。

```python
import pandas as pd

# テストデータ
df = pd.DataFrame({
  "grade": ["A", "B", "C", "B", "A", "B"],
  "point": [100, 80, 50, 70, 90, 75]
})

# grade 列が A であるものを削除 （= A でないものを抽出する）
df2 = df[df["grade"] != "A"]
print(df2)
```

{{< code title="実行結果" >}}
  grade  point
1     B     80
2     C     50
3     B     70
5     B     75
{{< /code >}}


特定のカラムでソート {#sort_values}
----

{{< code lang="python" title="テストデータ" >}}
import numpy as np
import pandas as pd
df = pd.DataFrame({
  'grade': ['C', 'A', 'B', np.nan, 'B', 'A'],
  'price': [50, 100, 150, 70, 30, 200]
})
{{< /code >}}

{{< code title="grade カラムでソート" >}}
>>> df.sort_values(by="grade")
  grade  price
1     A    100
5     A    200
2     B    150
4     B     30
0     C     50
3   NaN     70
{{< /code >}}

{{< code title="欠損値 (NaN) を先頭に持ってくる" >}}
>>> df.sort_values(by="grade", na_position="first")
  grade  price
3   NaN     70
1     A    100
5     A    200
2     B    150
4     B     30
0     C     50
{{< /code >}}

{{< code title="price カラムで降順ソート" >}}
>>> df.sort_values(by="price", ascending=False)
  grade  price
5     A    200
2     B    150
1     A    100
3   NaN     70
0     C     50
4     B     30
{{< /code >}}

{{< code title="複数カラムでソート" >}}
>>> df.sort_values(by=["grade", "price"], ascending=[True, False])
  grade  price
5     A    200
1     A    100
2     B    150
4     B     30
0     C     50
3   NaN     70
{{< /code >}}

{{< code title="ソート後にインデックス (0, 1, 2, ...) を振り直す" >}}
>>> df.sort_values(by="price").reset_index()
   index grade  price
0      4     B     30
1      0     C     50
2      3   NaN     70
3      1     A    100
4      2     B    150
5      5     A    200
{{< /code >}}

元のインデックスは `index` という新規カラムに格納されます。
`index` カラムが不要な場合は `reset_index()` メソッドに `drop=True` を指定します。


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

