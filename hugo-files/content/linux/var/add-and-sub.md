---
title: "Linuxメモ: シェルスクリプト: 数値変数を加算／減算する"
url: "p/3cukwcu/"
date: "2010-06-13"
tags: ["linux"]
aliases: /linux/var/add-and-sub.html
---

数値変数 `val` の値に対して加算／減算を行うには下記のようにします。

```bash
val=100
let val=$val+200
echo $val
```

あるいは、下記のように記述することもできます。

```bash
val=100
val=$(($val+200))
echo $val
```

{{< code title="実行結果" >}}
300
{{< /code >}}
