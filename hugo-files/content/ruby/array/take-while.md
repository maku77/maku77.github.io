---
title: "Rubyメモ: 先頭から条件を満たす範囲の配列を取得する"
url: "p/kmuzhww/"
date: "2011-11-04"
tags: ["ruby"]
aliases: ["/ruby/array/take-while.html"]
---

{{< code lang="ruby" title="例: 先頭から見て、0 以下の値をすべて取得する" >}}
arr = [-3, -2, -1, 0, 1, 2, 3, -100, -200]
new_arr = arr.take_while {|x| x <= 0}
p new_arr    # => [-3, -2, -1, 0]
{{< /code >}}

関連
----
- [先頭から条件を満たす間だけ要素を削除する](/p/jqimc9v/)
