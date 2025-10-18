---
title: "Linuxメモ: シェルスクリプト: 定数を定義する (readonly)"
url: "p/dwsvs5j/"
date: "2010-09-07"
tags: ["linux"]
aliases: /linux/var/read-only.html
---

Bash スクリプト内で定数を定義するには、**`readonly`** コマンドを使用します。
`readonly` を使うことで、変数や関数を読み取り専用に設定でき、誤って値を変更したり削除したりすることを防げます。
下記のように実行すると、指定した変数や関数に対する代入や `unset` ができなくなります。

```bash
readonly my_var      # 変数 my_var を readonly に
readonly -a my_arr   # 配列 my_arr を readonly に
readonly -f my_func  # 関数 my_func を readonly に
```

変数の定義と同時に `readonly` 指定することも可能です。

```bash
readonly USERNAME=maku
```

下記の例では、変数 `MAX_SIZE` を値 100 で初期化し、その後 200 という値を代入しようとしています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

readonly MAX_SCORE=100
MAX_SCORE=200
{{< /code >}}

`MAX_SCORE` 変数は `readonly` 化されているので、代入を実行しようとしたときにエラーになります。

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
./sample.sh: 行 4: MAX_SCORE: 読み取り専用の変数です
{{< /code >}}

