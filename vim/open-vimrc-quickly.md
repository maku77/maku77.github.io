---
title: .vimrc ファイルを素早く開く
created: 2014-01-27
---

ex コマンドで .vimrc を開く
====
`~/.vimrc` などの設定ファイルは、以下のように開くことができます。

```vim
:e $MYVIMRC
```

ショートカットキーで .vimrc を開く
====
下記の設定をしておくと、`F1` キーで簡単に `.vimrc` ファイルを開けるようになります。
ここでは、新しいタブで `.vimrc` を開くようにしています。


#### ~/.vimrc

```vim
nmap <F1> :tabnew $MYVIMRC<CR>
```

