---
title: "Vim で構文強調（シンタックスハイライト）を有効にする (syntax)"
url: "p/wcfbnqj/"
date: "2014-01-27"
tags: ["vim"]
aliases: [/vim/settings/syntax.html]
---

構文強調（シンタックスハイライト）の機能を有効にしておくと、プログラムのソースコードをなどを編集しているときに、各種キーワードをカラフルに表示してくれるようになります。

```vim
:syntax on   "構文強調を有効にする（:syntax enable でも OK）
:syntax off  "構文強調を無効にする
```

起動時に有効にしておきたい場合は、`~/.vimrc` に以下のように記述しておきます。

```vim
syntax on
```

特定のバッファでのみ設定を変更するには、コマンドモードから以下のように実行します。

```vim
:set syntax=ON   "カレントバッファのみ構文強調を有効にする
:set syntax=OFF  "カレントバッファのみ構文強調を無効にする
```

