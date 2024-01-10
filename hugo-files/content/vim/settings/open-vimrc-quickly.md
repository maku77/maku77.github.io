---
title: "Vim/NeoVim でショートカットキーで .vimrc ファイルを開く"
url: "p/r5fcfgk/"
date: "2014-01-27"
lastmod: "2024-01-10"
changes:
  - 2024-01-10: NeoVim の設定を追加
tags: ["vim"]
aliases: /vim/settings/open-vimrc-quickly.html
---

ショートカットキーで .vimrc を開く
----

Vim/NeoVim の設定ファイルで、下記のようにキーマッピング設定をしておくと、`F1` キーを押すだけで簡単に `.vimrc` ファイル（NeoVim の場合は `init.vim`）を開けるようになります。

{{< code lang="vim" title=".vimrc（NeoVim なら init.vim）" >}}
" F1 キーで設定ファイルを開く
if has('nvim')
  "NeoVim の場合
  nnoremap <F1> :tabnew <C-R>=expand(stdpath('config')) . '/init.vim'<CR><CR>
else
  "Vim の場合
  nnoremap <F1> :tabnew $MYVIMRC<CR>
endif
{{< /code >}}

ここでは、__`:tabnew`__ コマンドを使って、新しいタブで設定ファイルを開くようにしています。
NeoVim と Vim で設定ファイルのパスが異なるので、`if has('nvim')` で分岐しています。
さらに、NeoVim の設定ファイルのパスは、Windows と Linux/macOS で異なりますが、`stdpath('config')` を使って親ディレクトリのパスを取得すればコードを共通化できます。


参考
----

- [Vim/NeoVim の設定ファイルのパスを確認する](/p/7mabuvq/)
- [Vim/NeoVim で設定ファイル (.vimrc) を開く、リロードする](/p/zneoq8d/)

