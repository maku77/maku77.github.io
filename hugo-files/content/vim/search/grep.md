---
title: "Vim 内で grep を実行して見つかったファイルへジャンプする (:vimgrep, :grep)"
url: "p/c4q8amz/"
date: "2010-09-13"
lastmod: "2024-10-19"
tags: ["vim"]
changes:
  - 2024-10-19: 詳細な説明を追加。
aliases: /vim/advanced/grep.html
---

内部 grep (internal grep) と外部 grep (external grep)
----

Vim には自身に搭載されている検索機能を実行する __内部 `grep`__ と、外部の grep 系コマンドを実行する __外部 `grep`__ の機能が存在します。

- 内部 grep を起動するコマンド ... __`:vim`__（__`:vimgrep`__ の省略形）
- 外部 grep を起動するコマンド ... __`:grep`__


`:vimgrep` による検索（内部 grep）
----

### `:vimgrep` の概要

内部 grep（`:vim` あるいは `:vimgrep`）は、Vim に組み込まれた grep 機能を使用するため、どの OS 上でも共通の振る舞いの検索を実行することができます。
検索にヒットした行は Vim 内部の __QuickFix List__ という配列データに追加され、そこから見つけた行にジャンプすることができます（参考: `:help quickfix.txt`）。

{{< code title="書式" >}}
:vim /{pattern}/[g][j] {file} ...
{{< /code >}}

- __`g`__ オプション ... 同じ行の中にパターンに一致する部分が複数あった場合に、QuickFix List に別々に追加します。デフォルトでは、同じ行内で複数回検索にヒットしても、その行は 1 度だけ QuickFix List に追加されます。多くのケースでは、このオプションを指定する必要はないでしょう。
- __`j`__ オプション ... デフォルトでは検索で最初にマッチした行にカーソルが移動します。`j` オプションを付けると、カーソルの位置は移動しません。

### 基本的な検索

{{< code title="例: 現在開いているファイルを grep" >}}
:vim /hoge/ %
{{< /code >}}

__`%`__ は現在開いているファイル名を表します。

{{< code title="例: *.txt ファイルを grep" >}}
:vim /hoge/ *.txt
{{< /code >}}

{{< code title="例: カレントディレクトリ以下のファイルをすべて grep" >}}
:vim /hoge/ **/*.txt
{{< /code >}}

### 大文字・小文字の区別

デフォルトではパターンの大文字・小文字を区別して検索しますが、オプション __`ignorecase`__ がセットされている場合は、パターンの大文字・小文字を区別せずに検索します。
`ignorecase` の設定に関わらず、大文字・小文字を区別して検索したい場合は、パターンの中に __`\C`__ を含めます。
逆に、大文字・小文字を区別せずに検索したい場合は、パターンの中に __`\c`__ を含めます。

{{< code title="例: 大文字・小文字を区別して include という文字列を検索" >}}
:vim /\Cinclude/ **/*.cpp
{{< /code >}}

### QuickFix List のウィンドウを開く

`:vim` (`:vimgrep`) による検索が終了したら、__`copen`__ で QuickFix List のウィンドウ（__QuickFix ウィンドウ__）を開いて、見つけた行の一覧を確認することができます。
対象の行に移動して `Enter` キーを入力することで、その場所にジャンプすることができます。

{{< code title="QuickFix ウィンドウを開く" >}}
:copen
{{< /code >}}

`:vimgrep` の実行と同時に QuickFix ウィンドウを開きたいときは、以下のように __`cw[indow]`__ コマンドを続けて実行します。

{{< code title="検索と同時に QuickFix ウィンドウを開く" >}}
:vim /hoge/ *.txt | cw
{{< /code >}}

毎回 `| cw` と入力するのが面倒な場合は、__`.vimrc`__ に以下のように設定しておきます。

{{< code title=":vimgrep 後に自動で copen する設定" >}}
au QuickfixCmdPost grep,grepadd,vimgrep,vimgrepadd copen
{{< /code >}}

この設定により、`vimgrep` を実行した後に、自動的に `copen` が実行されるようになります。
上記の例では、他にも `grep`、`grepadd`、`vimgrepadd` を実行した際も QuickFix ウィンドウを開くよう設定しています。
必要に応じて変更してください。

{{% note %}}
`:copen` コマンドの C はおそらく "Current List of Errors" の C です。
`:copen` コマンドで開いたウィンドウの `buftype` は `quickfix` となり、全体で 1 つだけ表示できるようになっています。
すでに QuickFix ウィンドウが開いている状態で `:vimgrep` や `:grep` を実行した場合、そのウィンドウに検索結果が表示されます。
{{% /note %}}


`:grep` による検索（外部 grep）
----

`:vimgrep` コマンドの代わりに __`:grep`__ コマンドを使用すると、__`grepprg`__ オプションに設定されている外部の grep 系コマンドを実行することができます。

{{< code title="外部 grep の実行" >}}
:grep [arguments]
{{< /code >}}

`grepprg` オプションのデフォルト値は、Windows の場合は __`findstr /n`__、Linux の場合は __`grep -n $* /dev/null`__ に設定されています。

{{< code lang="vim" title="grepprg オプションの値を確認" >}}
:set grepprg?  " grep -n $* /dev/null のように表示されます
{{< /code >}}

通常は、以下のような感じで実行できるはずです。

{{< code lang="vim" title="外部 grep による検索" >}}
:grep hoge *.txt   " *.txt ファイルを grep
:grep hoge %       " 編集中のファイルを grep
{{< /code >}}

`:vimgrep` コマンドと同様、`:copen` コマンドで QuickFix ウィンドウを開いて、検索にヒットした行にジャンプできます。

{{< code title="QuickFix ウィンドウを開く" >}}
:copen
{{< /code >}}


おまけ
----

`:vimgrep (:vim)` や `:grep` コマンドの代わりに、__`:vimgrepadd`__ や __`:grepadd`__ コマンドを使用すると、検索結果を既存の QuickFix List に追加することができます。

{{< code title="既存の QuickFix List に検索結果を追加する" >}}
:vimgrepadd /AAA/ *.txt
:grepadd AAA *.txt
{{< /code >}}

`setqflist()` 関数を呼び出すと、QuickFix List の内容を修正することができます。
次のように実行すると、QuickFix List がクリアされます（空配列 `[]` がセットされます）。

{{< code title="QuickFix List をクリアする" >}}
:call setqflist([])
{{< /code >}}

