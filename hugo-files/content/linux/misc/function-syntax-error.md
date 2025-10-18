---
title: "Linuxメモ: 改行コードが原因の関数定義の syntax error"
url: "p/c2ycgaj/"
date: "2011-04-27"
tags: ["linux"]
aliases: /linux/trouble/function-syntax-error.html
---

下記は簡単な関数を定義しているだけのシンプルなシェルスクリプトですが、

{{< code lang="bash" title="my_functions.sh" >}}
function hello {
  echo 'Good morning!'
}
{{< /code >}}

このファイルを `source` コマンドで読み込んだときに以下のような syntax error が出ることがあります。

```console
$ source my_functions.sh
'ash: my_functions.bash: line 1: syntax error near unexpected token `{
'ash: my_functions.bash: line 1: `function hello {
```

このようなエラーが出る場合は、テキストファイルの改行コードが Windows (CR+LF) で保存されている可能性があります。
改行コードを変更するには、例えば Vim エディタから以下のように実行します。

```vim
:set ff=unix
:w
```

