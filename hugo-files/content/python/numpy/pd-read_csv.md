---
title: "DRAFT - pandas で CSV や TSV ファイルを読み込む方法のまとめ"
url: "p/78ns8r5/"
date: "2023-09-04"
tags: ["pandas"]
---

{{% private %}}
- [IO tools (text, CSV, HDF5, …) — pandas documentation](https://pandas.pydata.org/docs/user_guide/io.html)
{{% /private %}}

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


特定の列だけを読み込む (colindex)
----

多数の列がある CSV ファイルを読み込む場合、必要な列だけを読み込むようにすればメモリ効率がよくなります。

{{< code lang="python" title="読み込む列を列番号で指定する（0 始まり）" >}}
df = pd.read_csv("input.csv", usecols=[0, 1, 3])
{{< /code >}}

{{< code lang="python" title="読み込む列を列名で指定する" >}}
df = pd.read_csv("input.csv", usecols=["col1", "col2", "col4"])
{{< /code >}}

