---
title: "Vim/Neovim で自動インデントモードを有効にする (autoindent, smartindent, cindent)"
url: "p/oe94dkh/"
date: "2007-11-16"
tags: ["vim"]
aliases: /vim/settings/auto-indent.html
---

3 種類のインデントモードの設定
----

Vim には、大きく分けて、以下のようなオートインデント設定があります。

autoindent
: カレント行のインデントをキープする。シンプル。

smartindent
: C言語風のプログラミング言語向けの自動インデント。

cindent
: C言語に特化したインデント（`smartindent` よりも厳密に文法を考慮）

### autoindent を有効／無効にする

```vim
:set autoindent    " autoindent を ON
:set noautoindent  " autoindent を OFF
:set autoindent?   " autoindent の設定を確認 (autoindent or noautoindent)
```

__`autoindent`__ の設定を有効にすると、新しい行を追加したときに、前の行のインデントが引き継がれるようになります。
ただそれだけのシンプルなインデントモードです。
新しい行の追加は例えば、以下のような入力があった場合が対象になります。

- `o` コマンド（あるいは `O` コマンド）
- インサートモード時の <kbd>Enter</kbd>

インサートモードのまま、インデントの量を変えるには `CTRL-T`、`CTRL-D` などのコマンドを使用します。
下記は、`:help index` からのキーマップ説明の抜粋です。

- `i_CTRL-T`: 現在の行に `shiftwidth` 分のインデントを挿入する。
- `i_CTRL-D`: 現在の行から `shiftwidth` 分のインデントを削除する。


### smartindent を有効／無効にする

```vim
:set smartindent    " smartindent を ON
:set nosmartindent  " smartindent を OFF
:set smartindent?   " smartindent の設定を確認 (smartindent or nosmartindent)
```

__`smartindent`__ は、C-like な言語を編集するときに、下記のようにそれっぽく字下げや字上げを自動化してくれるインデントモードです。

- `{` ごとにインデントを深くし、`}` ごとにインデントを浅くする。
- 前の行が `cinwords` に設定されたキーワードで始まっているときにインデントを深くする（`cinwords` のデフォルト値は `if,else,while,do,for,switch`）。

ただし、C 言語風のプリプロセッサのために、`#` で始まる行はインデント数を 0 にするといった特殊ルールも設定されています。

### cindent を有効／無効にする

```vim
:set cindent   " cindent を ON
:set noindent  " cindent を OFF
:set cindent?  " cindent の設定を確認 (cindent or nocindent)
```

__`cindent`__ を有効にしておくと、C/C++ や Java などの言語構文をより厳密に考慮して、非常に賢いインデントを自動で行ってくれます。
例えば、if ブロックを `{}` で囲まなかった場合に、次の一行だけインデントを深くする、といったことを行ってくれます。

`cindent` が設定されている場合は、`smartindent` の設定は無効になります。
`cindent` の詳しい設定方法は、`:help C-indenting` で参照できます。


C/C++ のソースファイルを開いたときに自動的に cindent モードを設定する
----

以下のように設定しておくと、C/C++ 言語や Java 言語のソースコードファイルを開いたときに、`cindent` モードが自動的に有効になります。
ここでは、インデント量の設定もついでに行っています。

{{< code lang="vim" title="~/.vimrc" >}}
" ファイルタイプ検出を有効にする
filetype on

augroup vimrc
    " 以前の autocmd コマンドをクリア
    autocmd!

    " C/C++/Java 言語系のファイルタイプが設定されたら cindent モードを有効にする
    autocmd FileType c,cpp,java  setl cindent
    autocmd FileType c,cpp,java  setl expandtab tabstop=4 shiftwidth=4 softtabstop=4 shiftround
augroup END
{{< /code >}}


参考
----

- [インデント（シフトコマンド）を設定する (`shiftwidth`, `shiftround`)](/p/b5o6ksu/)
- [インデント用のスペースを入力する（シフトコマンド） (`>>`, `<<`, `Ctrl-T`, `Ctrl-D`)](/p/i2m4nqt/)
- [選択した範囲を自動インデントする (`=`)](/p/pxpgasg/)

