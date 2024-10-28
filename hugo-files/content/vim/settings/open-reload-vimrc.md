---
title: "Vim/NeoVim で設定ファイル (.vimrc) を開く、リロードする"
url: "p/zneoq8d/"
date: "2007-12-26"
tags: ["vim"]
aliases: /vim/settings/reload-vimrc.html
---

設定ファイルを開く
----

### NeoVim の場合

NeoVim の設定ファイル (__`init.lua`__ or __`init.vim`__) を開くには次のようにします。

{{< code lang="vim" title="NeoVim の設定ファイルを開く（OS に依存しない方法）" >}}
:e $MYVIMRC
{{< /code >}}

NeoVim は、設定ファイルとして `init.lua` と `init.vim` のどちらかを使うことができます（`init.lua` が優先されます）。
実際に読み込まれた設定ファイルのパスが __`$MYVIMRC`__ に格納されるので、上記のように設定ファイルを開くことができます（`:echo $MYVIMRC` でパスを確認できます）。

明示的に `init.lua` または `init.vim` を開きたい場合は次のようにします。

{{< code lang="vim" title="NeoVim の設定ファイルを開く（Linux/macOS の場合）" >}}
:e ~/.config/nvim/init.lua
:e ~/.config/nvim/init.vim
{{< /code >}}

{{< code lang="vim" title="NeoVim の設定ファイルを開く（Windows の場合）" >}}
:e %USERPROFILE%\AppData\Local\nvim\init.lua
:e %USERPROFILE%\AppData\Local\nvim\init.vim
{{< /code >}}

- 参考: [Vim/NeoVim の設定ファイルのパスを確認する](/p/7mabuvq/)

### Vim の場合

Vim エディタから設定ファイル（__`.vimrc`__ や __`.gvimrc`__）を開くには、下記のようにします（`:e` は `:edit` コマンドの省略系です）。

{{< code lang="vim" title="Vim の設定ファイルを開く（Linux/macOS の場合）" >}}
:e ~/.vimrc
:e ~/.gvimrc
{{< /code >}}

{{< code lang="vim" title="Vim の設定ファイルを開く（Windows の場合）" >}}
:e ~/_vimrc
:e ~/_gvimrc
{{< /code >}}

設定ファイルの名前が Linux (`.vimrc`) と Windows (`_vimrc`) で微妙に異なるので注意しなければいけませんが、実は、これらの設定ファイル名（パス）は、__`$MYVIMRC`__、__`$MYGVIMRC`__ という変数に格納されているので、次のように実行すれば、環境に依存しない指定方法で設定ファイルを開くことができます。

{{< code lang="vim" title="設定ファイルを開く（OS に依存しない方法）" >}}
:e $MYVIMRC
:e $MYGVIMRC
{{< /code >}}


設定ファイルをリロードする
----

設定ファイルを変更した後で、その内容を反映させるには下記のように __`:source`__ コマンドでそのファイルを読み込みます。

{{< code lang="vim" title="Vim の場合" >}}
:source $MYVIMRC   " ~/.vimrc のリロード
:source $MYGVIMRC  " ~/.gvimrc のリロード
{{< /code >}}

{{< code lang="vim" title="NeoVim の場合" >}}
:source $MYVIMRC                  " init.vim のリロード
:luafile ~/.config/nvim/init.lua  " init.lua のリロード
{{< /code >}}


参考
----

- [ショートカットキーで設定ファイル (.vimrc) を開く](/p/r5fcfgk/)

