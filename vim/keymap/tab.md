---
title: "ショートカットキーでタブを切り替える"
date: "2009-02-03"
---

ここでは、`Ctrl + TAB` でタブを切り替えるように設定してみます。
また、`Ctrl + Shift + TAB` で１つ前のタブに戻ります。

#### ~/.vimrc

~~~ vim
"-------------
" Change the tab
"-------------
"Go to next tab
nmap <C-Tab> :tabnext<CR>

"Go to previous tab
nmap <C-S-Tab> :tabprevious<CR>
~~~

`Ctrl + TAB` の組み合わせがうまく動作しない端末のために、他のキーマッピングを割り当てておいた方がよいかもしれません。
下記の例では、`Ctrl + J` か `Ctrl + H` で前のタブへ移動、`Ctrl + K` か `Ctrl + M` で後ろのタブへ移動できるように設定しています。

~~~ vim
"-------------
" Change the tab (some terminal cannot handle C-Tab)
"-------------
"Go to next tab
nmap <C-Tab> :tabnext<CR>
nmap <C-l> :tabnext<CR>
nmap <C-k> :tabnext<CR>

"Go to previous tab
nmap <C-S-Tab> :tabprevious<CR>
nmap <C-j> :tabprevious<CR>
nmap <C-h> :tabprevious<CR>
~~~

ちなみに、`nmap` はノーマルモード用のキーマップ設定を行います。
インサートモードでも同様にタブ切り替えを行えるようにするには、下記のように追加で `imap` でキーマップ定義を行います。

~~~ vim
"Go to next tab
nmap <C-Tab> :tabnext<CR>
nmap <C-l> :tabnext<CR>
nmap <C-k> :tabnext<CR>
imap <C-Tab> <ESC>:tabnext<CR>
imap <C-l> <ESC>:tabnext<CR>
imap <C-k> <ESC>:tabnext<CR>

"Go to previous tab
nmap <C-S-Tab> :tabprevious<CR>
nmap <C-j> :tabprevious<CR>
nmap <C-h> :tabprevious<CR>
imap <C-S-Tab> <ESC>:tabprevious<CR>
imap <C-j> <ESC>:tabprevious<CR>
imap <C-h> <ESC>:tabprevious<CR>
~~~

ただ、インサートモードでは、`Ctrl + H` はデフォルトで BackSpace としてマッピングされているので、その機能を上書きしてまでキーマッピングを行うほどのものではないでしょう。
`nmap` を使ってノーマルモードのマッピングをしておくだけで十分便利です。

