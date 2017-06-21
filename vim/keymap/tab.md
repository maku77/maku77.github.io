---
title: ショートカットキーでタブを切り替える
created: 2009-02-03
---

ここでは、`Ctrl + TAB` でタブを切り替えるように設定してみます。
また、`Ctrl + Shift + TAB` で１つ前のタブに戻ります。

#### $HOME/.vimrc

~~~ vim
"-------------
" Key mapping
"-------------
"Go to next tab
map <C-Tab> :tabnext<CR>

"Go to previous tab
map <C-S-Tab> :tabprevious<CR>
~~~

`Ctrl + TAB` の組み合わせがうまく動作しない端末のために、他のキーマッピングを割り当てておいた方がよいかもしれません。
下記の例では、`Ctrl + J` か `Ctrl + H` で前のタブへ移動、`Ctrl + K` か `Ctrl + M` で後ろのタブへ移動できるように設定しています。

~~~ vim
"-------------
" Key mapping
" Some terminal cannot handle Ctrl+Tab..
"-------------
"Go to next tab
map <C-Tab> :tabnext<CR>
map <C-l> :tabnext<CR>
map <C-k> :tabnext<CR>
imap <C-Tab> <ESC>:tabnext<CR>
imap <C-l> <ESC>:tabnext<CR>
imap <C-k> <ESC>:tabnext<CR>

"Go to previous tab
map <C-S-Tab> :tabprevious<CR>
map <C-j> :tabprevious<CR>
map <C-h> :tabprevious<CR>
imap <C-S-Tab> <ESC>:tabprevious<CR>
imap <C-j> <ESC>:tabprevious<CR>
imap <C-h> <ESC>:tabprevious<CR>
~~~

ちなみに、`map` はノーマルモード用のキーマップ設定、`imap` はインサートモード用のキーマップ設定を示しています。

