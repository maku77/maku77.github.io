---
title: "Vim/Neovim で全角スペースを見えるように表示する"
url: "p/preoa93/"
date: "2009-02-03"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: Neovim の設定方法を追記。
aliases: /vim/settings/show-double-byte-space.html
---

全角スペースを表示するための設定
----

Vim の設定ファイルに下記のように記述しておくと、全角スペースに背景色が付いて判別できるようになります。
ここでは、GVim の場合に背景色として `darkgray` を指定していますが、好みの背景色に変更して適用してください。

{{< code lang="vim" title="Vim (~/.vimrc) の場合" >}}
" Show double byte spaces
hi DoubleByteSpace term=underline ctermbg=blue guibg=darkgray
match DoubleByteSpace /　/
{{< /code >}}

{{< code lang="lua" title="Neovim (~/.config/nvim/init.lua) の場合" >}}
-- Show double byte spaces
vim.cmd[[
  hi DoubleByteSpace term=underline ctermbg=blue guibg=darkgray
  match DoubleByteSpace /　/
]]
{{< /code >}}


参考
----

- [制御文字（改行、タブ文字、行末のスペースなど）を表示する (`list`, `listchars`)](/p/s596qii/)

