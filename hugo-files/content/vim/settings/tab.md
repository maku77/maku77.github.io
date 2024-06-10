---
title: "Vim/NeoVim でタブ文字に関する設定を行う (tabstop, expandtab, softtabstop)"
url: "p/8okf7d3/"
date: "2007-04-11"
lastmod: "2024-06-10"
tags: ["vim"]
changes:
  - 2024-06-10: NeoVim の設定方法を追記
aliases: /vim/settings/tab.html
---

タブ文字 1 文字分の表示幅を設定する
----

タブ文字がどれくらいの幅で表示されるかの設定は __`tabstop`__ オプションで行います。

{{< code lang="vim" title="Vim (~/.vimrc) の場合" >}}
:set tabstop=4  "タブ 1 文字の表示幅 (default: 8)
{{< /code >}}

{{< code lang="lua" title="NeoVim (~/.config/nvim/init.lua) の場合" >}}
vim.opt.tabstop = 4  -- タブ 1 文字の表示幅 (default: 8)
{{< /code >}}

例えば、`tabstop` を 4 に設定すると、ファイル内のタブ文字は 4 文字分のスペースとして表示されます。
この設定は、あくまで見え方の設定であって、タブは 1 文字のタブ文字 (`\t`) として存在します。
他のエディタでファイルを開くと、タブ文字の見え方は変わってきます。


タブを入力したときにタブ文字の代わりにスペースを挿入する
----

TAB キーを押したときに、タブ文字の代わりに半角スペース x N を入力したいときは、__`expandtab`__ を有効化します。
さらに、TAB キーを押したときに何文字分の半角スペースを入力するかは、__`softtabstop`__ で設定します。
`softtabstop=4` と具体的な値を設定するのもよいですが、負の値（-1 など）を設定すると、`tabstop` で設定した値に合わせてくれます。

{{< code lang="vim" title="Vim (~/.vimrc) の場合" >}}
:set expandtab       "タブキーでスペースを入力する (default: noexpandtab)
:set softtabstop=-1  "タブキーで入力するスペース数 (-1: tabstop に合わせる)
{{< /code >}}

{{< code lang="lua" title="NeoVim (~/.config/nvim/init.lua) の場合" >}}
vim.opt.expandtab = true  -- タブキーでスペースを入力する (default: false)
vim.opt.softtabstop = -1  -- タブキーで入力するスペース数 (-1: tabstop に合わせる)
{{< /code >}}

ちなみに、`expandtab` が有効化されているときにタブ文字を明示的に入力したくなった場合は、__`CTRL-V <TAB>`__ あるいは __`CTRL-Q <TAB>`__ で入力できます。


参考
----

- [すでに入力されているタブをスペースに変換する (`:retab`)](/p/w4qm7ok/)

