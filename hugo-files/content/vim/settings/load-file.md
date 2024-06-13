---
title: "Vim/NeoVim の設定ファイルから別の設定ファイルを読み込む (source, dofile)"
url: "p/dnso7ds/"
date: "2024-06-11"
tags: ["vim"]
---

複数の環境で Vim や NeoVim を使用しているときは、設定ファイルを GitHub や Dropbox などの共有サービスで共有すると便利です。
共有した設定ファイルは、Vim の __`:source`__ や NeoVim (Lua) の __`:dofile()`__ などで読み込むことができます。

{{< code lang="vim" title="Vim (~/.vimrc) の場合" >}}
" Linux/macOS の例
source $HOME/Dropbox/share/config/vim/vimrc

" Windows の例
source D:/Dropbox/share/config/vim/vimrc
{{< /code >}}

{{< code lang="lua" title="NeoVim (~/.config/nvim/init.lua) の場合" >}}
-- Linux/macOS の例
local home = os.getenv("HOME")
dofile(home .. "/Dropbox/share/config/nvim/init.lua")

-- Windows の例
dofile("D:/Dropbox/share/config/nvim/init.lua")
{{< /code >}}

