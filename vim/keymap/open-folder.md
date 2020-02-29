---
title: "ショートカットキーでカレントディレクトリを Windows エクスプローラーや Mac の Finder で開く"
date: "2020-02-29"
---

次の設定を行っておくと、現在編集中のファイルが格納されたディレクトリをショートカットキー（ここでは `F12` キー）一発で開くことができます。
Windows の場合は `start` コマンドを使って「エクスプローラー」を開き、Mac の場合は `open` コマンドを使って「Finder」を開くようにしています。

### ~/.vimrc

```vim
" 編集中ファイルのディレクトリを Explorer や Finder で開く
if has("win32") || has("win64") || has("win32unix")
    nmap <F12> :silent ! start %:h<CR>
elseif has("macunix")
    nmap <F12> :silent ! open %:h<CR>
endif
```

