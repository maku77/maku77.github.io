---
title: "Vim のキーマップ例: ショートカットキーでカレントディレクトリを Windows エクスプローラーや Mac の Finder で開く"
url: "p/tqmr4od/"
date: "2020-02-29"
lastmod: "2024-06-21"
tags: ["neovim", "vim"]
changes:
  - 2024-06-21: NeoVim の設定方法を追加。
aliases: /vim/keymap/open-folder.html
---

Vim/NeoVim で次の設定を行っておくと、現在編集中のファイルが格納されたディレクトリをショートカットキー（ここでは `F12` キー）一発で開くことができます。
Windows の場合は `start` コマンドを使って「エクスプローラー」を開き、Mac の場合は `open` コマンドを使って「Finder」を開くようにしています。

NeoVim（Lua スクリプト）の場合
----

{{< code lang="lua" title="~/.config/nvim/init.lua" >}}
-- 編集中ファイルが格納されたディレクトリを開く関数
function open_dir()
  local command
  if vim.fn.has('mac') == 1 then
    command = 'open'
  elseif vim.fn.has('win32') == 1 or vim.fn.has('win64') == 1 then
    command = 'start'
  else
    print('Could not open the directory (unsupported OS)')
    return
  end

  local file_dir = vim.fn.expand('%:p:h')
  vim.fn.system(command .. ' ' .. file_dir)
end

-- F12 キーにマップする
vim.api.nvim_set_keymap("n", "<F12>",
  ":lua open_dir()<CR>",
  { noremap = true, silent = true }
)
{{< /code >}}


Vim（Vim スクリプト）の場合
----

{{< code lang="vim" title="~/.vimrc" >}}
" 編集中ファイルのディレクトリを Explorer や Finder で開く
if has("win32") || has("win64") || has("win32unix")
    nmap <F12> :silent ! start %:h<CR>
elseif has("macunix")
    nmap <F12> :silent ! open %:h<CR>
endif
{{< /code >}}

