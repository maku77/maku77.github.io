---
title: "R の構文"
date: "2015-05-04"
---

条件分岐
----

```r
if (cond1) {
  ...
} else if (cond) {
  ...
} else {
  ...
}
```

繰り返し
----

```r
for (i in 1:5) { ... }
while (cond) { ... }
repeat { ... }
```

ループ内では `break` や `next` などのキーワードを使用することができます。


より詳しく調べるには
----

`if` や `for` などの詳しい説明を読みたい場合は、下記のようにヘルプを表示することができます（これらのキーワードは、ダブルクォーテーションで括る必要があることに注意してください）。

```r
?"if"
help("if")
```

