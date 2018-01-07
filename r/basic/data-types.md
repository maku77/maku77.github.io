---
title: R のデータ型
date: "2014-12-08"
---

実数 (numeric)
----

```r
x <- 12.34
```

複素数 (complex)
----

```r
x <- 1+2i
```

文字列 (character)
----

```r
x <- 'Hello'
x <- "Hello"
```

論理型 (logical)
----

```r
x <- TRUE (or T)
x <- FALSE (or F)
```

`T` と `F` は、`TRUE` と `FALSE` の代わりに使用することができますが、別の値を代入してしまうことができます（`T` と `F` は定数ではなく変数ということ）。
そのため、公式ドキュメントでは、常に `TRUE` と `FALSE` の方を使うことを推奨しています。

ベクトル
----

```r
x = c(1, 2, 3)
```

ベクトル内に複数の型を含めると、**「文字列＞実数＞論理値」**の優先度で同じ型になるように自動的に変換されます。

```r
> c(1, 2, 3.14)
[1] 1.00 2.00 3.14

> c(1, 2+3i)
[1] 1+0i 2+3i

> c(123, TRUE, FALSE)
[1] 123   1   0

> c("ABC", 123, TRUE)
[1] "ABC"  "123"  "TRUE"
```

mode 関数で型を調べる
----

`mode()` 関数を使用すると、指定した値を、R がどのような型とみなしているかを調べることができます。

```r
> mode(10)
[1] "numeric"

> mode(1+2i)
[1] "complex"

> mode('Hello')
[1] "character"

> mode(TRUE)
[1] "logical"

> mode(c("ABC", 123, TRUE))
[1] "character"
```

