---
title: "Linux シェルスクリプト: echo の結果を標準エラー出力 (stderr) に出力する"
url: "p/q2k3j2h/"
date: "2022-12-05"
tags: ["Linux"]
---

標準エラー出力への出力
----

`echo` コマンドの出力結果を、デフォルトの標準出力ではなく、標準エラー出力に出力するには、末尾で __`1>&2`__ のようにリダイレクトします（__`>&2`__ という省略形でも OK）。

```bash
#!/bin/bash

echo "Hello World" 1>&2
```

このように出力先を制御したプログラムは、実行結果をリダイレクトしたり、パイプ接続した場合の振る舞いに影響してきます。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

echo AAA
echo BBB 1>&2
echo CCC
{{< /code >}}

上記のプログラムの出力結果を `out.txt` というファイルにリダイレクトすると、ターミナル上には `BBB` という標準エラー出力のみが表示されます。

{{< code lang="console" title="実行例" >}}
$ ./sample.sh > out.txt
BBB
{{< /code >}}

{{< code title="out.txt の内容" >}}
AAA
CCC
{{< /code >}}


cat の場合
----

`cat` コマンドによるヒアドキュメントでも、標準エラー出力に出力できます。

```bash
cat 1>&2 <<EOF
AAA AAA AAA
BBB BBB BBB
CCC CCC CCC
EOF
```

シェルスクリプトの Usage 出力などに便利です。

```bash
usage() {
    cat 1>&2 <<EOF
Usage: $(basename $0) [OPTIONS]

Options:
    -h        Display this message
    -d DIR    Output directory (default: .)
    -v        Use verbose output
EOF
    exit -1
}
```


その他の出力先制御
---

コマンドの出力をさまざまな形でリダイレクトすることで、本来の標準出力、標準エラー出力への出力を次のように振り分けることができます。

出力先の凡例 ... <code style="color:blue">STDOUT</code>: 標準出力、<code style="color:red">STDERR</code>: 標準エラー出力、<code style="color:green">FILE</code>: ファイル、`─`: 出力されない

| コマンド形式 | 標準出力 | 標準エラー出力 |
| ---- | :--: | :--: |
| `command` | <code style="color:blue">STDOUT</code> | <code style="color:red">STDERR</code> |
| `command >&2`<br>`command 1>&2` | <code style="color:red">STDERR</code> | <code style="color:red">STDERR</code> |
| `command 2>&1` | <code style="color:blue">STDOUT</code> | <code style="color:blue">STDOUT</code> |
| `command > /dev/null` | `─` | <code style="color:red">STDERR</code> |
| `command > /dev/null 2>&1` | `─` | `─` |
| `command > file` | <code style="color:green">FILE</code> | <code style="color:red">STDERR</code> |
| `command > file 2>&1` | <code style="color:green">FILE</code> | <code style="color:green">FILE</code> |
| `command 2>&1 > file` | <code style="color:green">FILE</code> | <code style="color:blue">STDOUT</code> |

読み解き方:

  - `&1` ... 現在、標準出力 (1) に割り当てられている出力先
  - `&2` ... 現在、標準エラー出力 (2) に割り当てられている出力先
  - `1>&2` ... 標準出力 (1) の出力先を `&2` に切り替える
  - `2>&1` ... 標準エラー出力 (2) の出力先を `&1` に切り替える
  - `> file` ... 標準出力 (1) の出力先を file に切り替える

