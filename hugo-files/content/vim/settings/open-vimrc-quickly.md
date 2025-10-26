---
title: "Vim/Neovim のキーマップ例: ショートカットキーで設定ファイル (.vimrc, init.lua) を開く"
url: "p/r5fcfgk/"
date: "2014-01-27"
lastmod: "2024-06-11"
changes:
  - 2024-01-10: Neovim の設定を追加
  - 2024-06-11: Neovim の Lua スクリプトでの設定方法を追加
tags: ["neovim", "vim"]
aliases: /vim/settings/open-vimrc-quickly.html
---

Vim の場合
----

Vim の設定ファイルで下記のようにキーマッピング設定をしておくと、`F1` キーを押すだけで簡単に `~/.vimrc` ファイルを開くことができます。
Neovim の設定ファイルとして `~/.config/nvim/init.vim` を使用している場合も同様に開くことができます。

{{< code lang="vim" title="Vim の .vimrc あるいは Neovim の init.vim を開く" >}}
" ノーマルモード時に F1 キーで設定ファイルを開く
nnoremap <silent> <F1> :tabnew $MYVIMRC<CR>
{{< /code >}}

ここでは、__`:tabnew`__ コマンドを使って、新しいタブで設定ファイルを開くようにしています。
オプションとして __`<silent>`__ を指定すると、`F1` キーを押したときにコマンドライン領域に `:tabnew $MYVIMRC` と表示されるのを抑制できます。

{{% private %}}
（この分岐は必要ないかも）

```vimrc
if has('nvim')
  "Neovim の場合
  nnoremap <F1> :tabnew <C-R>=expand(stdpath('config')) . '/init.vim'<CR><CR>
else
  "Vim の場合
  nnoremap <F1> :tabnew $MYVIMRC<CR>
endif
```

Neovim と Vim で設定ファイルのパスが異なるので、__`if has('nvim')`__ で分岐しています。
さらに、Neovim の設定ファイルのパスは、Windows と Linux/macOS で異なりますが、__`stdpath('config')`__ を使って親ディレクトリのパスを取得すればコードを共通化できます。
{{% /private %}}


Neovim (init.lua) の場合
----

Neovim の Lua 版の設定ファイル (`~/.config/nvim/init.lua`) を使う場合は、次のように設定します。

{{< code lang="lua" title="Neovim の init.lua を開く" >}}
-- ノーマルモード時に F1 キーで設定ファイルを開く
vim.keymap.set("n", "<F1>", ":tabnew $MYVIMRC<CR>", { silent = true })

-- $MYVIMRC を使わずに Lua ファイルのパスを指定する方法
-- vim.keymap.set("n", "<F1>",
--   ":tabnew " .. vim.fn.stdpath("config") .. "/init.lua<CR>",
--   { silent = true }
-- )
{{< /code >}}

上記のようにオプションとして __`silent = true`__ を指定すると、`F1` キーを押したときにコマンドライン領域に `:tabnew $MYVIMRC` と表示されるのを抑制できます。


参考
----

- [Vim/Neovim の設定ファイルのパスを確認する (`$MYVIMRC`)](/p/7mabuvq/)
- [Vim/Neovim で設定ファイル (`.vimrc`, `init.lua`) を開く、リロードする](/p/zneoq8d/)

