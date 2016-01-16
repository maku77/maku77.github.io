---
title: ベクトルのサブセットを生成する（ベクトルのフィルタ）
created: 2015-04-12
---

インデックスベクトルについて
----

ベクトルデータに対して、`[]` 演算子を使って**インデックスベクトル (Index Vector)** を指定すると、そのベクトルの要素を特定の条件で組み合わせたベクトルを作成することができます。
インデックスベクトルに指定するベクトルの型により、動作が変わります。

* 数値ベクトル（`1 〜 length(x)` の範囲の数値で構成されるベクトル）を指定した場合
  * 指定した要素位置で構成されたベクトルを作成
* 論理ベクトル（`TRUE`, `FALSE` で構成されるベクトル）を指定した場合
  * `TRUE` になる位置の要素のみで構成されたベクトルを作成（フィルタ）

インデックスベクトルとして数値ベクトルを指定する
----

下記は、数値ベクトルを渡し、その指定した位置の要素から構成されるベクトルを作成しています。

```r
> x <- c('A', 'B', 'C')
> x[c(1, 2, 3, 2, 1)]
[1] "A" "B" "C" "B" "A"
```

多くの言語の配列と同様なインデックスアクセスや、配列スライシングできるのはこの仕組みによります。

```r
> x <- c('A', 'B', 'C')
> x[2]
[1] "B"

> x <- c('A', 'B', 'C')
> x[2:3]
[1] "B" "C"
```

範囲外のインデックスを指定すると、その位置の要素は `NA` になります。

```r
> x <- c('A', 'B', 'C')
> x[1:5]
[1] "A" "B" "C" NA NA
```

数値ベクトルを作成する関数を組み合わせて使用すれば、柔軟なベクトル作成を行うことができます。

```r
> x <- c('A', 'B', 'C')
> x[rep(c(1, 2, 2), times=3)]
[1] "A" "B" "B" "A" "B" "B" "A" "B" "B"
```

負の数値からなる数値ベクトルを渡すと、その位置の要素を削除したベクトルを作成することができます。次の例では、2 番目と、3 番目の要素を削除したベクトルを作成しています。

```r
> x <- 1:5
> x[c(-2, -3)]
[1] 1 4 5
```

インデックスベクトルとして論理ベクトルを指定する
----

下記は、論理ベクトルを指定する例です。偶数の数値にフィルタしたベクトルを作成しています。

```r
> x <- c(4, 7, 3, 2, 6)
> x[x%%2 == 0]
[1] 4 2 6
```

内部的には `X%%2==0` というところで、以下のような `TRUE` と `FALSE` から成る論理ベクトルを作成しています。

```r
TRUE FALSE FALSE TRUE TRUE
```

このベクトルの要素が `TRUE` になる位置（偶数）のみの要素を抽出していることになります。
