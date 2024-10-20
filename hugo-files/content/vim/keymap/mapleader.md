---
title: "Vim で <Leader> キーを使ったキーコンビネーションを定義する (mapleader)"
url: "p/c9kmay4/"
date: "2020-04-04"
lastmod: "2024-10-20"
tags: ["vim"]
changes:
  - 2024-10-24: LocalLeader キーの説明を追加
aliases: /vim/keymap/mapleader.html
---

Vim の **Leader キー** は、キーマッピングを定義するときに使用可能なプレフィックスキーです。


Leader キーとは
----

例えば、`.vimrc` で次のようなキーマップ設定があったとします。

{{< code lang="vim" title=".vimrc（Leader キーの使用例）" >}}
nmap <Leader>a :echo "Hello"<CR>
nmap <Leader>b :echo "World"<CR>
{{< /code >}}

これは、Leader キーに続けて `a` キーや `b` キーを入力することで、`Hello` や `World` と表示するキーコンビネーションを定義しています。
つまり、__Leader キーはキーコンビネーションのためのプレフィックスキー__ です（実際には任意の位置で使えます）。

Leader キーには、__デフォルトでバックスラッシュ (`\`)__ が割り当てられているため、上記の設定を行った場合は、`\a` と入力することで `Hello` と表示されることになります。
次のように直接バックスラッシュキー (`<Bslash>`) を使って定義するのとは何が違うのでしょうか？

{{< code lang="vim" title=".vimrc" >}}
nmap <Bslash>a :echo "Hello"<CR>
nmap <Bslash>b :echo "World"<CR>
{{< /code >}}

Leader キーを使ったキーマッピングには、次のような利点があります。

* Leader キーは単なるプレフィックキーだということを強調できる（特に自分の `.vimrc` を公開するとき）
* Leader キーだけを変更したくなったときに、まとめて置き換えられる（Leader キーは任意のキーに設定できます）
* 他のユーザーの `.vimrc` 設定を使いまわしやすい（自分の好きな Leader キーで使える）
* Plugin の中でキーマッピングを定義するときに Leader キーを使うことで、ユーザーによるキー設定の余地を残す


Leader キーを設定する (mapleader)
----

Leader キーを任意のキーに設定するには、次のように __`g:mapleader`__ 変数を設定します。
デフォルトではバックスラッシュキーが使用されますが、決して押しやすいキーではないので、スペースキーやカンマを Leader キーとして使用する人が多いようです。

```vimrc
let g:mapleader = "\<Space>"
let g:mapleader = ","
```

スペースキーを表現するときは、**`"\<Space>"`** と記述することに注意してください。
同様に、`CTRL-W` という入力は `"\<C-W>"` と記述します（参考: `:help expr-quote`, `:help key-notation`）。

`g:mapleader` 変数は、`nmap` や `nnoremap` コマンドなどで **`<Leader>` を参照する前に定義しておく必要があります**。
`mapleader` 変数を設定する前に `<Leader>` を参照すると、デフォルトのバックスラッシュキーに置き換えられてしまいます。

{{% note title="スペースキーのデフォルトの振る舞い" %}}
ノーマルモードのスペースキーには、デフォルトでは `L` キーと同じ振る舞い（カーソルを右へ移動させる）が割り当てられています。
Leader キーをスペースキーに割り当てた場合でも、スペースキーを押してから 1 秒間放置することで、このデフォルトの振る舞いを実行することができます。
このあたりの振る舞いは、`timeout`、`ttimeout`、`timeoutlen` などのオプションで調整できます。
{{% /note %}}


Leader キーを使ったキーマップの例
----

```vimrc
" Leader キーをスペースキーに変更 （注: <Leader> を参照する前に設定する）
let g:mapleader = "\<Space>"

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

Leader キーの現在の割り当てを調べるには次のように実行します。

{{< code lang="vim" title="Leader キーの割り当てを表示" >}}
:echo g:mapleader
{{< /code >}}

Leader キーにスペースキーが割り当てられている場合は、空白文字が出力されるので、実際には何も表示されていないように見えるので注意してください。

何も設定されていない場合（デフォルトでバックスラッシュ `\` が割り当てられている状態）は、次のようなエラー表示になります。

```
E121: 未定義の変数です: g:mapleader
E121: Undefined variable: g:mapleader
```


おまけ
----

### LocalLeader キー

`<Leader>` とは別に、バッファローカルな Leader キーとして参照可能な __`<LocalLeader>`__ が用意されています。
`<LocalLeader>` 用のキーは、**`b:maplocalleader`** 変数で設定します。

```vimrc
" LocalLeader キーをカンマに変更
let b:maplocalleader = ","

" バッファローカルなキーマップ
nnoremap <buffer> <LocalLeader>h :<C-u>tabprev<CR>
nnoremap <buffer> <LocalLeader>l :<C-u>tabnext<CR>
```


### VS Code での Leader キー設定

VS Code (Visual Studio Code) の Vim プラグインでも、Leader キーの設定ができます。

{{< code lang="js" title="settings.json（VS Code の設定ファイル抜粋）" >}}
{
  /*
   * Vim 拡張用の設定。
   */
  "vim.leader": "<Space>",  // Leader キーとしてスペースキーを使う
  "vim.useSystemClipboard": true,  // システムクリップボードを使う

  // ...
}
{{< /code >}}

