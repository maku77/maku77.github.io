---
title: "Vim/Neovim のキーマップ例: ショートカットキーでタブを切り替える"
url: "p/ksmwhv8/"
date: "2009-02-03"
lastmod: "2025-10-26"
tags: ["vim"]
changes:
  - 2025-10-26: Neovim の設定方法を追加
aliases: /vim/keymap/tab.html
---

Vim で `:tabnew` を使ってタブを開いた後は、タブの切り替えを `:tabnext`、`:tabprevious` を使って行いますが、いちいちこのコマンドを入力するのは面倒なので、ショートカットキーでタブの切り替えを行えるようにしてみます。
ここでは、`Ctrl + J` か `Ctrl + H` で前のタブへ移動、`Ctrl + K` か `Ctrl + M` で後ろのタブへ移動できるように設定しています。
ブラウザのタブ切り替えのように、`Ctrl + TAB` や `Ctrl + Shift + TAB` を割り当てることも可能ですが、これらのキーコンビネーションは端末によっては機能しないことがあります。


Vim の場合
----

{{< code lang="vim" title="~/.vimrc" >}}
" Change the tab
nmap <C-Tab> :tabnext<CR>
nmap <C-l> :tabnext<CR>
nmap <C-k> :tabnext<CR>
nmap <C-S-Tab> :tabprevious<CR>
nmap <C-j> :tabprevious<CR>
nmap <C-h> :tabprevious<CR>
{{< /code >}}

ちなみに、**`nmap`** ではノーマルモード用のキーマップ設定を行います。
インサートモードでも同様にタブ切り替えを行えるようにするには、下記のように追加で **`imap`** でキーマップ定義を行います。

```vim
" Change the tab (for insert mode)
imap <C-Tab> <ESC>:tabnext<CR>
imap <C-l> <ESC>:tabnext<CR>
imap <C-k> <ESC>:tabnext<CR>
imap <C-S-Tab> <ESC>:tabprevious<CR>
imap <C-j> <ESC>:tabprevious<CR>
imap <C-h> <ESC>:tabprevious<CR>
```

ただし、インサートモードでは、`Ctrl + H` はデフォルトで BackSpace としてマッピングされているので、その機能を上書きしてまでキーマッピングを行うほどのものではないでしょう。
`nmap` を使ってノーマルモードのマッピングをしておくだけで十分便利です。

{{% note %}}
タブ切り替え時に、コマンドライン部分に `:tabnext` や `:tabprevious` と表示と表示されるのを抑制したいときは、`nmap` の後ろに **`<silent>`** オプションを指定します。

```vim
nmap <silent> <C-l> :tabnext<CR>
```
{{% /note %}}


Neovim の場合
----

Neovim の設定ファイル (`init.lua`) でのキーマップ設定には **`vim.keymap.set`** を使用します。

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- タブの切り替え
vim.keymap.set("n", "<Leader>h", ":tabprev<CR>")
vim.keymap.set("n", "<Leader>l", ":tabnext<CR>")
vim.keymap.set("n", "<C-l>", ":tabnext<CR>")
vim.keymap.set("n", "<C-k>", ":tabnext<CR>")
vim.keymap.set("n", "<C-j>", ":tabprevious<CR>")
vim.keymap.set("n", "<C-h>", ":tabprevious<CR>")
vim.keymap.set("n", "<C-Tab>", ":tabnext<CR>")
vim.keymap.set("n", "<C-S-Tab>", ":tabprevious<CR>")
{{< /code >}}

{{% note %}}
タブ切り替え時に、コマンドライン部分に `:tabnext` や `:tabprevious` と表示と表示されるのを抑制したいときは、4 番目のパラメーターで **`{ silent = true }`** オプションを指定します。

```vim
vim.keymap.set("n", "<C-l>", ":tabnext<CR>", { silent = true })
```
{{% /note %}}

