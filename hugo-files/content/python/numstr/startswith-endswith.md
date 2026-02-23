---
title: "文字列がある文字列で始まっている／終わっているかを調べる (startswith, endswith)"
url: "p/bu3u94c/"
date: "2012-09-28"
tags: ["python"]
aliases: /python/numstr/startswith-endswith.html
---

## ある文字列で始まっているか調べる (startswith)

`str.startswith()` メソッドは、文字列が指定したプレフィックスで始まっているかを調べ、`bool` 値を返します。

```python
s = "Hello, World!"

s.startswith("Hello")  #=> True
s.startswith("World")  #=> False
```

## ある文字列で終わっているか調べる (endswith)

`str.endswith()` メソッドは、文字列が指定したサフィックスで終わっているかを調べ、`bool` 値を返します。

```python
s = "Hello, World!"

s.endswith("World!")  #=> True
s.endswith("Hello")   #=> False
```

## OR 条件の指定（タプルで複数パターンを渡す）

`startswith()` と `endswith()` は、文字列パターンをタプルで渡すことで、いずれかに一致するかどうか（OR 条件）を判定できます。

```python
filename = "report.csv"

# 拡張子が .csv または .tsv であるかを判定
if filename.endswith((".csv", ".tsv")):
    print("CSV or TSV file")  #=> CSV or TSV file

# プレフィックスが report または summary であるかを判定
if filename.startswith(("report", "summary")):
    print("Report or summary file")  #=> Report or summary file
```
