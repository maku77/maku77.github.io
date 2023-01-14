---
title: "シェルスクリプト: コマンドライン引数の数が正しいかチェックする ($#)"
url: "p/4tbzpyf/"
date: "2010-06-13"
tags: ["Linux"]
aliases: /linux/startup/check-param-count.html
---

関連記事: [Linux シェルスクリプト: コマンドライン引数を取得する (`$1`, `$@`, `$*`)](/p/c2kx7er/)

コマンドライン引数が足りないときに Usage 出力して終了する
----

シェルスクリプト起動時に渡されたコマンドライン引数の数は、__`$#`__ 変数で取得することができます。
シェルスクリプトが最低 2 つのパラメーターを必要としている場合は、先頭部分で下記のようなチェックをするのがよいでしょう。

{{< code lang="bash" title="例: 2 つ以上のパラメーターが必要" >}}
#!/bin/bash

if [ $# -lt 2 ]; then
  echo "Usage: $(basename $0) <file1> <file2>" >&2
  exit -1
fi

# 処理を継続
{{< /code >}}

下記はバリエーションです。

{{< code lang="bash" title="例: 1 つ以上のパラメーターが必要" >}}
#!/bin/bash

if [ ! "$1" ]; then
  echo "Usage: $(basename $0) <file>" >&2
  exit -1
fi

# 処理を継続
{{< /code >}}

{{< code lang="bash" title="例: 1 つは引数が必要で、かつ、その名前のファイルが存在する" >}}
#!/bin/bash

if [ ! "$1" ]; then
  echo "Usage: $(basename $0) <file>" >&2
  exit -1
fi

if [ ! -f "$1" ]; then
  echo "$1 is not found" >&2
  exit -1
fi

# 処理を継続
{{< /code >}}


コマンドライン引数が指定されていないときにデフォルト値を使用する
----

1 番目のコマンドライン引数を参照するときに __`${1:-XXX}`__ とすると、引数を省略したときに `XXX` というデフォルト値が返されます。
例えば、次の例では、コマンドライン引数で「出力先ディレクトリ」を指定できるようにしていますが、省略した場合のデフォルトとして `out` ディレクトリを使用するようにしています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

out_dir=${1:-out}
echo out_dir: $out_dir
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh foo
out_dir: foo

$ ./sample.sh foo bar
out_dir: foo

$ ./sample.sh "foo bar"
out_dir: foo bar

$ ./sample.sh
out_dir: out

$ ./sample.sh ""
out_dir: out
{{< /code >}}

