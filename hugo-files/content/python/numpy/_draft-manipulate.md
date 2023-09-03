---
title: "DRAFT - pandas で DataFrame を加工する方法のまとめ"
url: "p/m4ggdkx/"
date: "2023-09-04"
tags: ["pandas"]
draft: true
---

既存の列の値をもとにした計算結果から新しい列を作成する (assign)
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

