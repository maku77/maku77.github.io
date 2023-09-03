---
title: "pandas の DataFrame のカラム（列）名やインデックス（行）名を変更する (rename)"
url: "p/3g687f5/"
date: "2023-09-04"
tags: ["pandas"]
---

pandas の `DataFrame` のカラム名やインデックス名を変更するには、__`rename`__ メソッドを使用します。


新しい名前を明示する方法
----

{{< code lang="python" title="カラム名（列名）の変更" >}}
df2 = df.rename(
    columns={
        "既存の列名1": "新しい列名1",
        "既存の列名2": "新しい列名2",
        "既存の列名3": "新しい列名3"
    }
)
{{< /code >}}

{{< code lang="python" title="インデックス名（行名）の変更" >}}
df2 = df.rename(
    index={
        "既存の行名1": "新しい行名1",
        "既存の行名2": "新しい行名2",
        "既存の行名3": "新しい行名3"
    }
)
{{< /code >}}

元の `DataFrame` のカラム名／インデックス名が置き換えられるわけではなく、新しい `DataFrame`（元のデータを参照するビュー）が返されることに注意してください。


変換関数を指定する方法
----

`rename` メソッドの `columns` 引数（あるいは `index` 引数）に名前の変換関数を指定すると、すべてのカラム名やインデックス名をまとめて置換できます。
次の例では、変換関数として `str.lower` を指定しています。

{{< code lang="python" title="全てのカラム名（列名）を小文字にする" >}}
df2 = df.rename(columns=str.lower)
df2 = df.rename(mapper=str.lower, axis=1)  # 同上
{{< /code >}}

{{< code lang="python" title="全てのインデックス名（行名）を小文字にする" >}}
df2 = df.rename(index=str.lower)
df2 = df.rename(mapper=str.lower, axis=0)  # 同上
{{< /code >}}

次の例では、変換関数としてラムダ関数を指定しています。

{{< code lang="python" title="デフォルトの行番号 (0, 1, 2, ...) から Y1, Y2, Y3, ... のような行名を生成" >}}
df2 = df.rename(index=lambda y: "Y{}".format(y + 1))
{{< /code >}}

