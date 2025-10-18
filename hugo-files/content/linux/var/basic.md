---
title: "Linuxメモ: シェルスクリプト: 変数の基本"
url: "p/cjn9dbq/"
date: "2012-11-05"
tags: ["linux"]
aliases: /linux/var/basic.html
---

変数の定義と参照
----

変数へ値を代入するときは `$` を付けず、変数を参照するときは `$` を付けます。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/sh
name=Michael        # 変数の定義
echo Hello $name    # 変数の参照
echo "Hello $name"  # ダブルクォーテーションは変数を展開する
echo 'Hello $name'  # シングルクォーテーションは変数を展開しない
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
Hello Michael
Hello Michael
Hello $name
{{< /code >}}


定義していない変数は空文字と同じ
----

未定義の変数を参照すると、空文字列と同様に扱われます。
エラーにはなりません。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/sh
echo "Hello $name !"
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./sample.sh
Hello  !
{{< /code >}}


"=" の前後にスペースは入れちゃだめ！
----

変数定義の `=` の前後にはスペースを入れてはいけません。
スペースを入れると、変数名の部分がシェル上で実行可能なコマンドとして認識されてしまいます。
例えば、以下のようにすると、`value` というコマンドを、引数 `"= hello"` で実行すると解釈されてしまいます。

{{< code lang="bash" title="間違った代入（その１）" >}}
value = hello
{{< /code >}}

さらに、以下のように、`"="` の後ろだけにスペースを入れると…

{{< code lang="bash" title="間違った代入（その２）" >}}
value= hello
{{< /code >}}

これは、変数 `value` を空にした状態で、コマンド `hello` を呼び出すと解釈されてしまいます。

