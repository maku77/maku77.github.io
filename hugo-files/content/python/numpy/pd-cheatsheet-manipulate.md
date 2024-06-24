---
title: "pandas チートシート - DataFrame のデータ加工方法まとめ"
url: "p/m4ggdkx/"
date: "2023-09-04"
tags: ["Python", "pandas"]
---

DataFrame 加工のチートシート
----

<style>
  .local-section {
    background: #333;
    color: white;
    border-color: #333;
    text-align: left;
    padding-left: 0.5em;
  }
</style>
<table>
  <tbody>
    <tr>
      <th colspan="2" class="local-section">作成／追加</th>
    </tr>
    <tr>
      <td><code>df["列"] = スカラー値</code></td>
      <td><a href="#new-column">新しいカラムの作成（すべて同じ値）</a></td>
    </tr>
    <tr>
      <td><code>df["列"] = シリーズ</code><br/><code>df.assign(列=シリーズ)</code></td>
      <td><a href="#new-column">新しいカラムの作成（<code>Series</code> の代入）</a></td>
    </tr>
    <tr>
      <td><code>df2 = df.copy()  # shallow copy</code><br/><code>df2 = df.copy(deep=True)</code></td>
      <td>DataFrame のコピー</td>
    </tr>
    <tr>
      <td><code>df["列2"] = df["列1"] + 10</code><br/><code>df["列2"] = df.apply(lambda r: r["列1"] + 10, axis=1)</code></td>
      <td><a href="#apply">既存カラムの値を使って新しいカラムを作成</a></td>
    </tr>
    <tr>
      <td><code>df2 = df[["列1", "列2", "列3"]]</code></td>
      <td><a href="#extract">カラムの抽出</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">結合 (concat, merge)</th>
    </tr>
    <tr>
      <td><code>new_df = pd.concat([df1, df2])</code></td>
      <td><a href="#concat">複数の DataFrame を縦結合</a></td>
    </tr>
    <tr>
      <td><code>new_df = pd.merge(df1, df2, on="列", how="inner")</code></td>
      <td><a href="#merge">2 つの DataFrame を横結合</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">削除 (drop, drop_duplicates)</th>
    </tr>
    <tr>
      <td>
        <code>df2 = df.drop(columns="列")</code><br />
        <code>df2 = df.drop("列", axis=1)</code>
      </td>
      <td><a href="#drop-column">指定したカラムを削除</a></td>
    </tr>
    <tr>
      <td>
        <code>df.drop(columns="列", inplace=True)</code><br/>
        <code>df.drop("列", axis=1, inplace=True)</code><br/>
        <code>del df["列"]</code> ※非推奨<br />
        <code>df.pop("列")
      </td>
      <td><a href="#drop-column">指定したカラムを削除（自分自身を変更）</a></td>
    </tr>
    <tr>
      <td>
        <code>df2 = df.drop(index="行")</code><br />
        <code>df2 = df.drop("行", axis=0)</code>
      </td>
      <td><a href="#drop-row">指定した行を削除</a></td>
    </tr>
    <tr>
      <td>
        <code>df.drop(index="行", inplace=True)</code><br />
        <code>df.drop("行", axis=0, inplace=True)</code>
      </td>
      <td><a href="#drop-row">指定した行を削除（自分自身を変更）</a></td>
    </tr>
    <tr>
      <td><code>df2 = df[df["列"] != 値]</code></td>
      <td><a href="#conditional-delete">条件に一致する行を削除</a></td>
    </tr>
    <tr>
      <td><code>df.drop_duplicates()</code></td>
      <td><a href="#drop_duplicates">重複する行を削除</a></td>
    </tr>
    <tr>
      <td><code>df.drop_duplicates(keep="last")</code></td>
      <td>重複する行を削除（最初ではなく最後の行を残す）</td>
    </tr>
    <tr>
      <td><code>df.drop_duplicates(subset=["X1", "X2"])</code></td>
      <td><a href="#drop_duplicates">重複する行を削除（指定列の値が等しい行を削除）</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">置換 (replace)</th>
    </tr>
    <tr>
      <td>
        <code>df["列"].replace(置換前の値, 置換後の値)</code><br>
        <code>df["列"].replace([前1, 前2, 前3], [後1, 後2, 後3])</code>
      </td>
      <td><a href="#replace">値の置換</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">ソート (sort_values)</th>
    </tr>
    <tr>
      <td><code>df2 = pd.sort_values(by="列")</code></td>
      <td><a href="#sort_values">特定のカラムでソート（昇順）</a></td>
    </tr>
    <tr>
      <td><code>df2 = df.sort_values(by="列", ascending=False)</code></td>
      <td><a href="#sort_values">特定のカラムでソート（降順）</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">カテゴリ変数 (get_dummies)</th>
    </tr>
    <tr>
      <td><code>new_df = df.select_dtypes(include="object")</code></td>
      <td><a href="#select_dtypes_object">カテゴリ変数を抽出した DataFrame を作成</a></td>
    </tr>
    <tr>
      <td><code>new_df = df.select_dtypes(exclude="object")</code></td>
      <td><a href="#select_dtypes_object">カテゴリ変数を削除した DataFrame を作成</a></td>
    </tr>
    <tr>
      <td>
        <code>df2 = pd.get_dummies(df)</code><br />
        <code>df2 = pd.get_dummies(df, columns=["列1", "列2"])</code>
      </td>
      <td><a href="/p/vopvucs/#get_dummies">名義カテゴリ特徴量をワンホットエンコードする</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">カラム名／インデックス名 (columns, index, rename)</th>
    </tr>
    <tr>
      <td>
        <code>df.columns = [...]</code><br />
        <code>df2 = df.rename(colums={...})</code><br />
      </td>
      <td><a href="/p/3g687f5/">カラム名の変更</a></td>
    </tr>
    <tr>
      <td>
        <code>df.index = [...]</code><br />
        <code>df2 = df.rename(index={...})</code>
      </td>
      <td><a href="/p/3g687f5/">インデックス名の変更</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">データ型 (astype)</th>
    </tr>
    <tr>
      <td>
        <code>df.loc[:, "列1"] = df.loc[:, "列1].astype(float)</code><br />
        <code>df["列1"] = df["列1"].astype(float)</code>
      </td>
      <td><a href="/p/fk2e74z/">列1のデータ型を変更</a></td>
    </tr>
    <tr>
      <td>
        <code>df2 = df.astype({"列1": "int64", "列2": "float64"})</code>
      </td>
      <td><a href="/p/fk2e74z/">列1と列2のデータ型を変更</a></td>
    </tr>
    <tr>
      <th colspan="2" class="local-section">数値変換いろいろ</th>
    </tr>
    <tr>
      <td><code>y = y.astype("float32") / 255.0</code></td>
      <td>0〜255 (int) の値を 0.0〜1.0 (float) に正規化</td>
    </tr>
    <tr>
      <td><code>y = (y > 0.5).astype(int)</code></td>
      <td>閾値を基準にして 0 or 1 の数値に変換</td>
    </tr>
    <tr>
      <td><code>X -= X.mean(axis=0)</code></td>
      <td>平均値が 0 になるよう値を平行移動</td>
    </tr>
  </tbody>
</table>


新しいカラムの作成（あるいは上書き） {#new-column}
----

```python
# 値が 0 の新しい列 X1 を作成する（あるいは上書き）
df["X1"] = 0

# 列 X1 の値をもとに新しい列 X2 を作成する（あるいは上書き）
df["X2"] = df["X1"] + 100           # 既存の DataFrame を変更する場合
df2 = df.assign(X2=df["X1"] + 100)  # 新しい DataFrame を作る場合

# 列 X1 と X2 の値をもとに新しい列 X3 を作成する（あるいは上書き）
df["X3"] = df["X1"] + df["X2"]     # 既存の DataFrame を変更する場合
df.assign(X3=df["X1"] + df["X2"])  # 新しい DataFrame を作る場合

# 任意の変換関数を適用する（下記は np.sqrt 関数で平方根を生成する例）
df["X2"] = df["X1"].apply(np.sqrt)
```


複数の DataFrame を縦結合する (pd.concat) {#concat}
----

複数の `DataFrame` を縦方向に結合する（行を増やす）には、__`pd.concat()`__ 関数に `DataFrame` のリストを渡します。

```python
import pandas as pd

# テストデータ
df1 = pd.DataFrame({"col1": [1, 2, 3], "col2": [4, 5, 6]})
df2 = pd.DataFrame({"col1": [7, 8, 9], "col3": [10, 11, 12]})

# DataFrame を連結する
new_df = pd.concat([df1, df2])
print(new_df)
```

{{< code title="実行結果" >}}
   col1  col2  col3
0     1   4.0   NaN
1     2   5.0   NaN
2     3   6.0   NaN
0     7   NaN  10.0
1     8   NaN  11.0
2     9   NaN  12.0
{{< /code >}}

片方の `DataFrame` にしか存在しないカラムの値には `NaN` が設定されます。

デフォルトでは上記のように、それぞれの `DataFrame` のもとのインデックス名が保持されます。
インデックス名を振り直すには、__`ignore_index=True`__ オプションを指定します。

```python
# インデックスを振り直す場合
new_df = pd.concat([df1, df2], ignore_index=True)
print(new_df)
```

{{< code title="実行結果" >}}
   col1  col2  col3
0     1   4.0   NaN
1     2   5.0   NaN
2     3   6.0   NaN
3     7   NaN  10.0
4     8   NaN  11.0
5     9   NaN  12.0
{{< /code >}}


複数の DataFrame を横結合する (pd.merge) {#merge}
----

複数の `DataFrame` を横方向に結合する（列を増やす）には、__`pd.merge()`__ 関数を使用します。
引数として、どの列の値で対応づけるか (__`on`__) と、どのような結合アルゴリズムを使うか (__`how`__) を指定する必要があります。
結合アルゴリズムには次のようなものを指定できます。

- 内部結合 (`how="inner"`) （デフォルト）
  - 両方に同じ値が含まれる行のみを残します（AND のイメージ）。例えば、2 つの `DataFrame` において、商品 ID が一致する行があれば、その行だけをマージして新しいデータを作成します。
- 完全外部結合 (`how="outer"`)
  - 両方に同じ値が含まれる行があれば、それらは 1 つの行としてマージされ、その他の行はそのまま残されます（OR のイメージ）。片方にしか存在しない項目は欠損値 (NaN) で埋められます。
- 左外部結合 (`how="left"`)
  - 左側（第 1 引数）で指定した `DataFrame` の行だけが残されます。
- 右外部結合 (`how="right"`)
  - 右側（第 2 引数）で指定した `DataFrame` の行だけが残されます。

下記のサンプルコードでは、それぞれの結合アルゴリズムでどのような結果になるかを確認しています。

```python
import pandas as pd

# サンプルデータ
df1 = pd.DataFrame({"Id": [1, 2, 3], "Name": ["Alice", "Bob", "Charlie"]})
df2 = pd.DataFrame({"Id": [2, 3, 4], "Age": [25, 30, 35]})

print("=== INNER JOIN ===")
result_inner = pd.merge(df1, df2, on="Id", how="inner")
print(result_inner)

print("\n=== OUTER JOIN ===")
result_outer = pd.merge(df1, df2, on="Id", how="outer")
print(result_outer)

print("\n=== LEFT JOIN ===")
result_left = pd.merge(df1, df2, on="Id", how="left")
print(result_left)

print("\n=== RIGHT JOIN ===")
result_right = pd.merge(df1, df2, on="Id", how="right")
print(result_right)
```

{{< code title="実行結果" >}}
=== INNER JOIN ===
   Id     Name  Age
0   2      Bob   25
1   3  Charlie   30

=== OUTER JOIN ===
   Id     Name   Age
0   1    Alice   NaN
1   2      Bob  25.0
2   3  Charlie  30.0
3   4      NaN  35.0

=== LEFT JOIN ===
   Id     Name   Age
0   1    Alice   NaN
1   2      Bob  25.0
2   3  Charlie  30.0

=== RIGHT JOIN ===
   Id     Name  Age
0   2      Bob   25
1   3  Charlie   30
2   4      NaN   35
{{< /code >}}


一致する値を置換する (replace) {#replace}
----

`Series` あるいは `DataFrame` オブジェクトの __`replace()`__ メソッドを使うと、値の一括置換を行えます。
通常は `DataFrame` には列ごとに性質の異なるデータが格納されているはずなので、値の置換を行う場合は `Series` オブジェクト（列）単位で置換します。
次の例では、`Class` 列の `"A"` と `"B"` という値を、それぞれ `0` と `1` に置換しています。

```python
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "Price": [100, 200, 300, 400],
    "Class": ["A", "B", "A", "B"]
})

# Class 列のカテゴリ変数 (A, B) を数値 (0, 1) に置き換え
df["Class"].replace(["A", "B"], [0, 1], inplace=True)
print(df)
```

{{< code title="実行結果" >}}
   Price  Type
0    100     0
1    200     1
2    300     0
3    400     1
{{< /code >}}

`replace()` メソッドで __`regex=True`__ フラグを指定すると、__正規表現__ を使った置換が可能です。
次の例では、電話番号を表すフィールドから数値以外の文字を削除しています。

```python
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "Name": ["Alice", "Bob", "Charlie"],
    "Phone": ["123-4567-8901", "(0120) 111-2222", "111 2222 3333"]
})

# 数値以外の文字 (`\D`) をすべて削除する
df["Phone"].replace(r"\D", "", regex=True, inplace=True)
print(df)
```

{{< code title="実行結果" >}}
      Name        Phone
0    Alice  12345678901
1      Bob  01201112222
2  Charlie  11122223333
{{< /code >}}


既存カラムの値を使って新しいカラムを作成 {#apply}
----

既存のカラムのデータ (`Series`) に対して演算を行うことで、新しいカラム用のデータを作成することができます。
次の例では、`姓` カラムと `名` カラムの値をくっつけた `氏名` カラムを作成しています。

{{< code lang="python" hl_lines="10" >}}
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "姓": ["佐藤", "鈴木", "田中"],
    "名": ["太郎", "花子", "次郎"]
})

# "姓" と "名" を結合した "氏名" 列を作成
df["氏名"] = df["姓"] + df["名"]

print(df)
{{< /code >}}

{{< code title="実行結果" >}}
    姓   名    氏名
0  佐藤  太郎  佐藤太郎
1  鈴木  花子  鈴木花子
2  田中  次郎  田中次郎
{{< /code >}}

上記のように、`+` や `-` を使ったブロードキャスト演算で新しいカラムを作成してしまうのが一番簡単ですが、より複雑な加工処理を行いたい時は __`df.apply()`__ メソッドに加工処理を行う関数を渡します（行ごとに処理することを示す __`axis=1`__ オプションも付けてください）。
次の例では、メールアドレス（`mail` カラム）からドメイン部分を抽出した `domain` カラムを作成しています。
ここでは値の加工に `str#split()` メソッドを使用しているので、ブロードキャスト演算が使えません。

{{< code lang="python" hl_lines="9" >}}
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "mail": ["host@example.com", "host@test.org", "host@sample.net"]
})

# "mail" 列からドメイン部分を抽出して新しい列 "domain" を作成する
df["domain"] = df.apply(lambda r: r["mail"].split("@")[1], axis=1)

print(df)
{{< /code >}}

{{< code title="実行結果" >}}
               mail       domain
0  host@example.com  example.com
1     host@test.org     test.org
2   host@sample.net   sample.net
{{< /code >}}


特定の列だけを抜き出した DataFrame を作成する {#extract}
----

ある `DataFrame` から特定の列だけを抽出した `DataFrame` を作成したいときは次のようにします。

```python
df2 = df[["列1", "列2", "列3"]]
```

メモリ効率のため、データの実体は共有されることに注意してください（`df2` 側で値を変更すると、`df` にも影響します）。
新しいメモリ領域にコピーする場合は、`copy()` を組み合わせて使用します。

```python
df2 = df[["列1", "列2", "列3"]].copy()
```

- 参考: [pandas.DataFrame からのデータ抽出方法のチートシート](/p/rnai4ko/)



カラムの削除 (drop, del, pop) {#drop-column}
----

__`df.drop()`__ メソッドを使うと、指定したカラムを削除した新しい `DataFrame` を取得できます。

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

Python のリスト API である __`del`__ や __`pop`__ でもカラムの削除を行うことができますが、これらは元の `DataFrame` を変更します（破壊的操作）。
`pop` は削除した列を `Series` として返します。

{{< code lang="python" title="del と pop によるカラムの削除" >}}
# A カラムを削除 （df 自身を変更）
del df["A"]

# 同上だが削除したカラムを Series として返す
series = df.pop("A")
{{< /code >}}

{{% note title="pop の使用例" %}}
`pop` によるカラムの削除は、機械学習用のデータセットから正解ラベルを分離するときに使われたりします。

```python
# 正解ラベルの列 Price を分離
train_X = train.copy()
train_y = train_X.pop("Price")
```
{{% /note %}}


`df.drop()` はデフォルトでは新しい `DataFrame` を返しますが、__`inplace=True`__ を指定すれば、元の `DataFrame` を変更できます。
この場合、戻り値は `None` になります。

```python
# 元の DataFrame 自身を変更する
df.drop(columns=["A", "B"], inplace=True)
```


行の削除 (drop)
----

### 指定した行を削除 {#drop-row}

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

### 重複する行を削除 {#drop_duplicates}

__`DataFrame#drop_duplicates()`__ メソッドを使うと、同じデータを持つ行を取り除いた `DataFrame` を作成することができます。
次の例では、1 行目と 3 行目のデータが (`100`, `"x"`, `500`) で完全に一致しているので、3 行目が削除されています。

```python
import pandas as pd

df = pd.DataFrame({
    "A": [100, 100, 100, 200, 300],
    "B": ["x", "y", "x", "y", "x"],
    "C": [500, 600, 500, 600, 700],
})

new_df = df.drop_duplicates()
print(new_df)
```

{{< code title="実行結果" >}}
     A  B    C
0  100  x  500
1  100  y  600
3  200  y  600
4  300  x  700
{{< /code >}}

特定のカラムだけに着目して重複行を判断したいときは、__`subset=[列, 列, ...]`__ オプションを指定します。
次の例では、少なくとも `B` 列と `C` 列の値が等しい行を重複行として取り除いています（部分的に一致する行を削除します）。

```python
new_df = df.drop_duplicates(subset=["B", "C"])
print(new_df)
```

{{< code title="実行結果" >}}
     A  B    C
0  100  x  500
1  100  y  600
4  300  x  700
{{< /code >}}

{{% note title="重複している行を確認する" %}}
__`df.duplicated()`__ で各行が重複しているかを示すフラグ (`True/False`) の `Series` オブジェクトを取得できます。
これを使って元のデータフレームをフィルタすると、__重複している行だけを抽出__ することができます。

```python
import pandas as pd

df = pd.DataFrame({
    "A": [100, 100, 100, 200, 300],
    "B": ["x", "x", "x", "y", "z"],
})

dup_df = df[df.duplicated(keep=False)]
print(dup_df)
```

このコードを実行すると、最初の 3 つの行が重複していることを確認できます。

```
     A  B
0  100  x
1  100  x
2  100  x
```

`df.duplicated()` の戻り値は、デフォルトでは削除すべき行だけが `True` となります。
例えば、3 つの行が重複している場合、削除すべき 2 つの行のみ `True` になります。
上記コードのように、`df.duplicated()` に __`keep=False`__ オプションを付けることで、3 つの行すべてを `True` にすることができます。
{{% /note %}}


### 条件に一致する行を削除 {#conditional-delete}

これは発想の転換ですが、「ある列の値が `A` 以外である行を抽出する」という操作は、「ある列の値が `A` である行を削除する」という操作になります。

```python
import pandas as pd

# テストデータ
df = pd.DataFrame({
    "grade": ["A", "B", "C", "B", "A", "B"],
    "point": [100, 80, 50, 70, 90, 75]
})

# grade 列が A であるものを削除 （= A でないものを抽出する）
new_df = df[df["grade"] != "A"]
print(new_df)
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


カテゴリ変数を抽出／取り除いて DataFrame を作成 {#select_dtypes_object}
----

__`df.select_dtypes()`__ を使って、カテゴリ変数の列のみを抽出した `DataFrame` を作成することができます。
NaN はカテゴリ変数とはみなされません。

{{< code lang="python" hl_lines="12" >}}
import pandas as df

# テストデータ
df = pd.DataFrame({
    "col1": ["AAA", "BBB", "CCC", "DDD"],
    "col2": ["X", "Y", "Z", np.nan],
    "col3": [100, 200, 300, 400],
    "col4": [1.0, 2.0, 3.0, np.nan],
})

# カテゴリ変数だけの DataFrame を作成
new_df = df.select_dtypes(include=["object"])
print(new_df)
print(new_df.columns.to_list())
{{< /code >}}

{{< code title="実行結果" >}}
  col1 col2
0  AAA    X
1  BBB    Y
2  CCC    Z
3  DDD  NaN

['col1', 'col2']
{{< /code >}}

__`include`__ パラメーターの代わりに、__`exclude`__ パラメーターを使うと、指定したタイプの列だけを取り除くことができます。
次の例では、上記の例とは逆に、カテゴリ変数を取り除いた（数値変数のみの）`DataFrame`を作成しています。

{{< code lang="python" hl_lines="2" >}}
# カテゴリ変数を取り除いた DataFrame を作成
new_df = df.select_dtypes(exclude=["object"])
print(new_df)
print(new_df.columns.to_list())
{{< /code >}}

{{< code title="実行結果" >}}
   col3  col4
0   100   1.0
1   200   2.0
2   300   3.0
3   400   NaN

['col3', 'col4']
{{< /code >}}

