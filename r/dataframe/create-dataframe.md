---
title: ベクトルからデータフレームを作成する (data.frame)
created: 2015-05-05
---

`data.frame` 関数を使って、ベクトルデータからデータフレームを作成することができます。
下記の例では、`name` と `price` というベクトルデータからデータフレームを作成しています。

```r
> name <- c("apple", "banana", "strawberry")
> price <- c(200, 100, 300)
> df <- data.frame(name, price)
> df
        name price
1      apple   200
2     banana   100
3 strawberry   300
```

デフォルトでは、パラメータに渡したオブジェクト名が、そのままデータフレームのカラム名になります。
パラメータに名前をつけて渡すことで、任意のカラム名に変更することができます。

```r
> df <- data.frame(n=name, p=price)
> df
           n   p
1      apple 200
2     banana 100
3 strawberry 300
```

