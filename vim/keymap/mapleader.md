---
title: "Leader キーを使ったキーコンビネーションを定義する (mapleader)"
date: "2020-04-04"
description: "Leader キーはキーマッピングを定義するときの、置き換え可能なプレフィックスキーです。"
---

Leader キーとは
----

例えば、`.vimrc` で次のようなキーマップ設定があったとします。

```vimrc
nmap <Leader>a :echo "Hello"<CR>
nmap <Leader>b :echo "World"<CR>
```

これは、Leader キーに続けて `a` キーや `b` キーを入力することで、`Hello` や `World` と表示するキーコンビネーションを定義しています。
つまり、__Leader キーはキーコンビネーションのためのプレフィックスキー__ です（実際には任意の位置で使えますが）。

デフォルトでは、Leader キーはバックスラッシュ (`\`) に割り当てられているため、`\a` と入力することで `Hello` と表示されることになります。
次のように直接バックスラッシュキー (`<Bslash>`) を使って定義するのとは何が違うのでしょうか？

```vimrc
nmap <Bslash>a :echo "Hello"<CR>
nmap <Bslash>b :echo "World"<CR>
```

Leader キーを使ったキーマッピングには、次のような利点があります。

* Leader キーは単なるプレフィックキーだということを強調できる（特に自分の `.vimrc` を公開するとき）
* Leader キーだけを変更したくなったときに、まとめて置き換えられる（Leader キーは任意のキーに設定できます）
* 他のユーザーの `.vimrc` 設定を使いまわしやすい（自分の好きな Leader キーで使える）
* Plugin の中でキーマッピングを定義するときに Leader キーを使うことで、ユーザーによるキー設定の余地を残す


Leader キーを設定する (mapleader)
----

Leader キーを任意のキーに設定するには、次のように __`mapleader`__ 変数を設定します。
デフォルトではバックスラッシュキーが使用されますが、決して押しやすいキーではないので、スペースキーやカンマを Leader キーとして使用する人が多いようです。

```vimrc
let mapleader = "\<SPACE>"
let mapleader = ","
```

`mapleader` 変数は、`nmap` や `nnoremap` などのキーマッピング設定で `<Leader>` を参照する前に定義しておく必要があります。
`mapleader` 変数を設定する前に `<Leader>` を参照すると、デフォルトのバックスラッシュキーに置き換えられます。

ちなみに、ノーマルモードのスペースキーには、デフォルトでは `L` キーと同じ振る舞い（カーソルを右へ移動させる）が割り当てられています。
Leader キーをスペースキーに割り当てた場合でも、スペースキーを押してから 1 秒間放置することで、このデフォルトの振る舞いを実行することができます。


Leader キーを使ったキーマップの例
----

```vimrc
" タブの切り替え
nnoremap <Leader>h :<C-u>tabprev<CR>
nnoremap <Leader>l :<C-u>tabnext<CR>

" NERDTree 用のキーマップ
nnoremap <Leader>nt :<C-u>NERDTreeToggle<CR>
nnoremap <Leader>nf :<C-u>NERDTreeFind<CR>
```

`<Leader>` は小文字で `<leader>` と記述することもできます。


Leader キーにどのキーが設定されているか調べる
----

次のようにして Leader キーの現在の割り当てを調べることができます。

```
:echo mapleader
```

何も設定されていない場合（デフォルトでバックスラッシュキーが割り当てられている場合）は、次のようなエラー表示になります。

```
E121: Undefined variable: mapleader
E15: Invalid expression: mapleader
```

Leader キーにスペースキーが割り当てられている場合は、空白文字が出力されるので、実際には何も表示されていないように見えます。


（おまけ）VS Code での Leader キー設定
----

VS Code (Visual Studio Code) の Vim プラグインでも、Leader キーの設定ができます。

```
"vim.leader" : "<space>",   // Leader キーの設定
```

