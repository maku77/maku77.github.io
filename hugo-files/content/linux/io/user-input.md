---
title: "Linux シェルスクリプト: ユーザー入力を取得する (read)"
url: "p/6m6n5k3/"
date: "2012-11-05"
tags: ["Linux"]
aliases: /linux/io/user-input.html
---

ユーザー入力の基本
----

bash スクリプトの中で、__`read 変数名`__ とすると、ユーザーがキーボードで入力したテキストを変数に取得することができます。
`変数名` の部分には、`$` プレフィックスを付けないことに注意してください（変数の内容を参照するときは `$` が必要です）。

{{< code lang="bash" title="hello.sh" >}}
#!/bin/sh
echo -n "Enter your name: "
read name
echo "Hello, $name"
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ ./hello.sh
Enter your name: まくまく
Hello, まくまく
{{< /code >}}

{{% note title="echo の -n オプション" %}}
`echo` コマンドの出力はデフォルトで末尾で改行されますが、__`-n`__ オプションを使うとこの改行を抑制できます。
上記の例のように、プロンプト表示で利用できます。
{{% /note %}}


ユーザーが y を入力したときだけ処理を継続する
----

`read` コマンドの典型的な使用例として、ユーザーに `y/n` の選択肢を入力させるものがあります。
次の `remove_all` 関数は、何もかもを削除する前に、ユーザーに最終確認を行っています。

{{< code lang="bash" title="remove.sh" >}}
#!/bin/bash

function remove_all {
  echo -n 'Are you sure? (y/n): '
  read input
  if [ "$input" = 'Y' -o "$input" = 'y' ]; then
    echo 'All things have been removed!'
  fi
}

remove_all
{{< /code >}}

`if` の中で `$input` の内容を確認するときに、ダブルクォーテーションで囲んでいるのは、ユーザーの入力がなかった場合に空文字 `""` を取得するためです。
これがないと、ユーザーが何も入力せずにエンターした場合にエラーになってしまいます。

{{< code lang="console" title="実行例" >}}
$ ./remove.sh
Are you sure? (y/n): y
All things have been removed!
{{< /code >}}


スペースで区切られたユーザー入力を別々の変数に取得する
----

`read` コマンドの後ろに複数の変数を指定すると、スペースで区切られたユーザー入力を別々の変数に取得することができます。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/sh
read aaa bbb ccc
echo "aaa = $aaa"
echo "bbb = $bbb"
echo "ccc = $ccc"
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh
100 200 300
aaa = 100
bbb = 200
ccc = 300
{{< /code >}}

`read` に指定した変数の数よりも、入力が少ない場合は、変数が空になります。

```bash
$ ./sample.sh
100 200
aaa = 100
bbb = 200
ccc =
```

逆に、入力の方が多い場合は、`read` コマンドの最後に指定した変数にまとめて格納されます。

```bash
$ ./sample.sh
100 200 300 400 500
aaa = 100
bbb = 200
ccc = 300 400 500
```

