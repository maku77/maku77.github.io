---
title: "pandas で CSV/TSV ファイルを出力する (pd.to_csv)"
url: "p/ioj6bqf/"
date: "2023-10-14"
tags: ["pandas"]
---

{{% maku-common/reference %}}
- [pandas で CSV/TSV ファイルを読み込む (`pd.read_csv`, `pd.read_table`)](/p/78ns8r5/)
{{% /maku-common/reference %}}

pandas で `DataFrame` オブジェクトの内容を CSV/TSV ファイルに出力するには、[`DataFrame#to_csv()`](https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.to_csv.html) メソッドを使用します。

テスト用の DataFrame
----

ここでは、出力用の `DataFrame` オブジェクトを次のように作成しておきます。
インデックス名に `idx1`〜`idx3`、カラム名に `col1`〜`col3` をセットしています。

{{< code lang="python" >}}
import numpy as np
import pandas as pd

np.random.seed(12345) # 乱数の再現性を確保

df = pd.DataFrame({
    "col1": np.random.randint(10, size=3),
    "col2": np.random.randint(10, size=3),
    "col3": ["AAA", "BBB", "CCC"],
}, index=["idx1", "idx2", "idx3"])

print(df)
{{< /code >}}

{{< code title="実行結果" >}}
      col1  col2 col3
idx1     2     4  AAA
idx2     5     9  BBB
idx3     1     5  CCC
{{< /code >}}


CSV/TSV に出力する
----

### to_csv() メソッドの基本

パラメーターなしで `to_csv()` メソッドを呼び出すと、インデックス名やカラム名（CSV のヘッダー行）も含めて出力されます。
これは、パラメーターのデフォルト値が `index=True, header=True` になっているからです。

```python
df.to_csv("output.csv")
```

{{< code lang="csv" title="output.csv" >}}
,col1,col2,col3
idx1,2,4,AAA
idx2,5,9,BBB
idx3,1,5,CCC
{{< /code >}}

ちなみに、`DataFrame` にインデックス名が明示的にセットされていない場合は、次のように 0, 1, 2 という連番が振られます。

{{< code lang="csv" title="output.csv" >}}
,col1,col2,col3
0,2,4,AAA
1,5,9,BBB
2,1,5,CCC
{{< /code >}}

### TSV 形式で出力する (sep)

セパレーター文字はデフォルトでカンマ (`,`) ですが、__`sep`__ パラメーターで任意のセパレーター文字を設定できます。
タブ (__`\t`__) を指定すれば、TSV ファイルとして出力できます。

{{< code lang="python" title="TSV 形式で出力する" >}}
df.to_csv("output.tsv", sep="\t")
{{< /code >}}

{{< code lang="tsv" title="output.tsv" >}}
	col1	col2	col3
idx1	2	4	AAA
idx2	5	9	BBB
idx3	1	5	CCC
{{< /code >}}

### インデックス列やヘッダー行を出力しない (index, header)

インデックス列を出力したくないときは、__`index=False`__ パラメーターを指定します。
同様に、ヘッダー行（カラム名）を出力したくないときは、__`header=False`__ パラメーターを指定します。

{{< code lang="python" title="インデックス列を出力しない" >}}
df.to_csv("output.csv", index=False)
{{< /code >}}

```csv
col1,col2,col3
2,4,AAA
5,9,BBB
1,5,CCC
```

{{< code lang="python" title="インデックス列もヘッダー行も出力しない" >}}
df.to_csv("output.csv", index=False, header=False)
{{< /code >}}

```csv
2,4,AAA
5,9,BBB
1,5,CCC
```

### インデックス列のカラム名を指定する (index_label)

インデックス列のカラム名（ラベル）を指定するには、__`index_label`__ パラメーターを指定します。
デフォルトでは空文字列になってしまうので、カラム列を出力する場合は、この `index_label` も設定しておくことをお勧めします。

{{< code lang="python" title="インデックス列に id というラベルを付ける" >}}
df.to_csv("output.csv", index=True, index_label="id")
{{< /code >}}

```csv
id,col1,col2,col3
idx1,2,4,AAA
idx2,5,9,BBB
idx3,1,5,CCC
```
