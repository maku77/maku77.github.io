---
title: "pandas の DataFrame から数値カラムだけ抽出する (df.select_dtypes)"
url: "p/pa2us3b/"
date: "2023-10-23"
tags: ["pandas"]
---

Pandas の `DataFrame` から、数値タイプ（`int64` や `float64`）のカラムだけを抽出するには、__`df.select_dtypes()`__ メソッドを使用します。
次の例では、サンプルの `DataFrame` から数値タイプのカラム名を抽出して列挙しています。

{{< code lang="python" hl_lines="13" >}}
import numpy as np
import pandas as pd

# サンプルの DataFrame を作成
data = {
    '列1': [1, 2, 3],
    '列2': [1.1, 2.2, 3.3],
    '列3': ['A', 'B', 'C']
}
df = pd.DataFrame(data)

# 数値タイプのカラムを抽出してカラム名のリストを取得
numerical_columns = df.select_dtypes(include=[np.number]).columns

# 結果表示
print(numerical_columns)
for col in numerical_columns:
    print(col)
{{< /code >}}

{{< code title="実行結果" >}}
Index(['列1', '列2'], dtype='object')
列1
列2
{{< /code >}}

なお、上の例では数値型として `np.number` を指定していますが、`"number"` という文字列でも大丈夫です。

