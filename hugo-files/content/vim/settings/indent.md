---
title: "Vim/Neovim でインデント（シフトコマンド）を設定する (shiftwidth, shiftround)"
url: "p/b5o6ksu/"
date: "2007-02-20"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: Neovim の設定方法を追加
aliases: /vim/settings/indent.html
---

シフトコマンドで挿入／削除するスペースの量を設定する
----

__`shiftwidth`__ オプションに設定した値は、`>>` コマンドなどで行頭に挿入するスペースの数を示します。

```vim
:set shiftwidth=4  " デフォルトは 8
```

タブストップ (`tabstop`) と混同しがちなので注意してください。
タブストップは、タブ文字を入力した場合に表示位置をどこへずらすかの基準を指定します。
シフトコマンド (`>>`) に効いてくる値はあくまで `shiftwidth` の方です。


シフトしたときに shiftwidth の値の倍数になるようにスペースを挿入する
----

シフトコマンド（`>>` など）を実行すると、行の先頭に `shiftwidth` で設定した数だけスペースが挿入されます。
このとき、すでに行頭に何文字かのスペースが存在する場合に、スペース数が `shiftwidth` の倍数になるように調整してスペースを挿入したい場合は、次のように __`shiftround`__ を有効化します（デフォルトはオフ (`noshiftround`) です）。

{{< code lang="vim" title="Vim（/.vimrc の場合）" >}}
:set shiftround  "シフトコマンドでのインデント量を丸める
{{< /code >}}

{{< code lang="lua" title="Neovim（~/.config/nvim/init.lua の場合）" >}}
vim.opt.shiftround = true  -- シフトコマンドでのインデント量を丸める
{{< /code >}}

ほとんどの場合は、行頭からのインデント量は 2 の倍数とか 4 の倍数とかに揃えておきたいはずなので、`shiftround` はセットしておくことをお勧めします。


ファイルタイプごとのインデントを設定する
----

以下のように設定しておくと、ソースコードを開いたときに、その言語別（Vim 的にはファイルタイプ別）にインデント設定を切り替えることができます。
現在の編集中のファイルのファイルタイプが切り替わると、`FileType` イベントが発生するので、これを autocmd で捕まえて設定を行います。
現在のバッファが認識しているファイルタイプを確認するには、`:set filetype?` コマンドを実行します。

下記の例では、Lua/Markdown/Svelte/Ruby ファイルを開いたときに、インデント幅を 2 スペースに設定しています。
カレントバッファーのみに設定を反映するため、Neovim では `vim.opt_local`、Vim では `setlocal (setl)` を使用することに注意してください。

{{< code lang="lua" title="Neovim/Lua用 (~/.config/nvim/init.lua)" >}}
-- ファイルタイプ検出は Neovim では自動で有効なので通常不要ですが、明示するなら以下
vim.cmd('filetype on')

-- autocmd グループを定義（すでに存在する場合はクリア）
local my_group = vim.api.nvim_create_augroup("my_group", { clear = true })

-- ファイルタイプ別にインデント設定
vim.api.nvim_create_autocmd("FileType", {
  group = my_group,
  pattern = { "lua", "markdown", "ruby", "svelte" },
  callback = function()
    vim.opt_local.expandtab = true   -- タブキーでスペースを入力する
    vim.opt_local.shiftround = true  -- シフトコマンドでのインデント量を tabstop 単位に丸める
    vim.opt_local.tabstop = 2        -- タブ文字の表示幅
    vim.opt_local.shiftwidth = 2     -- シフトコマンドでのインデント量
    vim.opt_local.softtabstop = -1   -- タブキーで入力するスペース数 (-1: tabstop に合わせる)
  end,
})
{{< /code >}}

{{< code lang="vim" title="Vim用 (~/.vimrc)" >}}
" ファイルタイプ検出を有効にする
filetype on

augroup my_group
    " 以前の autocmd コマンドをクリア
    autocmd!

    " ファイルタイプ別にインデント設定
    autocmd FileType lua,markdown,ruby,svelte  setl expandtab tabstop=2 shiftwidth=2 softtabstop=2 shiftround
augroup END
{{< /code >}}

{{% note title="ファイルタイプを追加する" %}}
例えば、`.svelte` という拡張子のファイルを開いたときに `svelte` というファイルタイプとして認識してくれない場合は、次のようにファイルタイプの設定を追加します。

```lua
vim.filetype.add({
  extension = {
    svelte = "svelte",
    foo = "mycustomtype",
  },
})
```
{{% /note %}}


参考
----
- [自動インデントモードを有効にする (`autoindent`, `smartindent`, `cindent`)](/p/oe94dkh/)
- [インデント用のスペースを入力する（シフトコマンド） (`>>`, `<<`, `Ctrl-T`, `Ctrl-D`)](/p/i2m4nqt/)

