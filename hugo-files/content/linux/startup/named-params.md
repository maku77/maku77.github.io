---
title: "Linuxシェルスクリプト: 名前付きのコマンドラインオプションを扱う (getopts)"
url: "p/2fyizgw/"
date: "2022-12-05"
tags: ["Linux"]
---

Bash 組み込みの __`getopts`__ コマンドを使用すると、シェルスクリプトに渡されたコマンドラインオプション (`-a` など）を解析することができます。
`getopts` は 1 文字のオプションしか扱えないことに注意してください。


引数なしのオプション
----

下記は `getopts` の基本的な使用例です。
`getopts` のオプション文字列として `abc` を指定することにより、このシェルスクリプトには、__`-a`__、__`-b`__、__`-c`__ という 3 種類のオプションを受け取れるようになっています。
ユーザーが指定したオプションが、1 文字ずつ `opt` 変数に格納されるので、その値で `case` による分岐処理を行います。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

while getopts 'abc' opt; do
    case "${opt}" in
        a) echo "オプション -a を指定しました" ;;
        b) echo "オプション -b を指定しました" ;;
        c) echo "オプション -c を指定しました" ;;
    esac
done
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh -a
オプション -a を指定しました

$ ./sample.sh -a -c
オプション -a を指定しました
オプション -c を指定しました

$ ./sample.sh -x
./sample.sh: illegal option -- x
{{< /code >}}

最後の実行例から分かるように、`getopts` は定義されていないオプション (`-x`) を指定したときに、自動的にエラーメッセージ (`illegal option`) を出力します。
このエラー出力を抑制するには、次のように、オプション文字列の先頭に __`:`__ を付け、__`:abc`__ のように指定します。
不正なオプションが指定された場合は、`opt` 変数の値が __`?`__ になるので、自分でエラー処理を行うことも可能です。

{{< code lang="bash" title="sample.sh" hl_lines="3 8" >}}
#!/bin/bash

while getopts ':abc' opt; do
    case "${opt}" in
        a) echo "オプション -a を指定しました" ;;
        b) echo "オプション -b を指定しました" ;;
        c) echo "オプション -c を指定しました" ;;
        ?) echo "不正なオプション" >&2 ;;
    esac
done
{{< /code >}}

ちなみに、`echo` の末尾の `>&2` は、標準エラー出力への出力を意味しています（参考: [echo の結果を標準エラー出力 (stderr) に出力する](/p/q2k3j2h/)）。


引数ありのオプション
----

`getopts` では、次のような引数ありのオプションを扱うこともできます。

```console
$ ./sample -b 100 -c 200
```

引数ありのオプションにしたいときは、オプション文字列の対象文字の後ろに `:` を付けます。
次の例では、`-b` オプションと `-c` オプションに引数を指定できるようにしています。
オプションに指定した引数の値は、__`$OPTARG`__ 変数で参照できます。

{{< code lang="bash" title="sample.sh" hl_lines="3 6 7" >}}
#!/bin/bash

while getopts 'ab:c:' flag; do
    case "${flag}" in
        a) echo "オプション -a を指定しました" ;;
        b) echo "オプション -b を指定しました（引数=${OPTARG}）" ;;
        c) echo "オプション -c を指定しました（引数=${OPTARG}）" ;;
    esac
done
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh -a -b 100
オプション -a を指定しました
オプション -b を指定しました（引数=100）
{{< /code >}}

なお、引数ありのオプション（`b:` など）にした場合は、引数を指定しなかった場合はエラーになります。

```console
$ ./sample.sh -b
./sample.sh: option requires an argument -- b
```

getopts の実践的な使用例
----

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

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

outdir='.'
verbose=''

while getopts ':d:vh' opt; do
    case "${opt}" in
        d) outdir="${OPTARG}" ;;
        v) verbose='true' ;;
        h) usage ;;
        ?) usage ;;
    esac
done

if [ ! -z "$verbose" ]; then
    echo "Verbose mode"
fi

echo Output directory is "${outdir}"
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh
Output directory is .

$ ./sample.sh -d out -v
Verbose mode
Output directory is out

$ ./sample.sh -h
Usage: sample.sh [OPTIONS]

Options:
    -h        Display this message
    -d DIR    Output directory (default: .)
    -v        Use verbose output
{{< /code >}}

