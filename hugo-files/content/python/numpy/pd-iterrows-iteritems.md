---
title: "pandas で DataFrame をループ処理する (iterrows, items)"
url: "p/rfduqhx/"
date: "2024-01-08"
tags: ["Pandas"]
---

pandas の `DataFrame` にはブロードキャストによる演算機能が備わっているため、ほとんどの場合はループ処理を記述する必要はありませんが、明示的にループ処理することも可能です。


行のループ処理 (iterrows)
----

__`df.iterrows()`__ メソッドを使用すると、各行をイテレート処理できます。

```python
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "Name": ["Alice", "Bob", "Charlie"],
    "Age": [25, 30, 35],
    "City": ["New York", "San Francisco", "Los Angeles"],
})

# DataFrame の各行をループ処理
for i, row in df.iterrows():
    print(f"{i}: Name={row['Name']}, Age={row['Age']}, City={row['City']}")
```

{{< code title="実行結果" >}}
0: Name=Alice, Age=25, City=New York
1: Name=Bob, Age=30, City=San Francisco
2: Name=Charlie, Age=35, City=Los Angeles
{{< /code >}}


列のループ処理 (items)
----

__`df.items()`__ メソッドを使用すると、各列をイテレート処理できます。
ループごとに、各列のラベル名と `Series` オブジェクトのタプルを取得できます（ラベル名は `series.name` でも取得できるんですけどね^^）。

```python
import pandas as pd

# サンプルデータ
df = pd.DataFrame({
    "Name": ["Alice", "Bob", "Charlie"],
    "Age": [25, 30, 35],
    "City": ["New York", "San Francisco", "Los Angeles"],
})

# DataFrame の各列をループ処理
for label, series in df.items():
    print(f"{label}: {series.values}")
```

{{< code title="実行結果" >}}
Name: ['Alice' 'Bob' 'Charlie']
Age: [25 30 35]
City: ['New York' 'San Francisco' 'Los Angeles']
{{< /code >}}

