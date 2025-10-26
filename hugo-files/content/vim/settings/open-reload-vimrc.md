---
title: "Vim/Neovim で設定ファイル (.vimrc, init.lua) を開く、リロードする"
url: "p/zneoq8d/"
date: "2007-12-26"
tags: ["vim"]
aliases: /vim/settings/reload-vimrc.html
---

設定ファイルを開く
----

### Vim の場合

Vim エディタから設定ファイル（__`.vimrc`__ や __`.gvimrc`__）を開くには、下記のようにします（`:e` は `:edit` コマンドの省略系です）。

{{< code lang="vim" title="設定ファイルを開く（OS に依存しない方法）" >}}
:e $MYVIMRC
:e $MYGVIMRC
{{< /code >}}

設定ファイルの名前は Linux (`.vimrc`) と Windows (`_vimrc`) で微妙に異なりますが、実際に読み込まれた設定ファイルのパスが __`$MYVIMRC`__、__`$MYGVIMRC`__ という変数に格納されているので、上記のようにして OS に依存しない方法で設定ファイルを開くことができます（`:echo $MYVIMRC` でパスを確認できます）。

明示的にファイルパスを指定して開きたいときは次のようにします。

{{< code lang="vim" title="Vim の設定ファイルを開く（Linux/macOS の場合）" >}}
:e ~/.vimrc
:e ~/.gvimrc
{{< /code >}}

{{< code lang="vim" title="Vim の設定ファイルを開く（Windows の場合）" >}}
:e ~/_vimrc
:e ~/_gvimrc
{{< /code >}}

### Neovim の場合

Neovim の設定ファイル (__`init.lua`__ or __`init.vim`__) を開くには次のようにします。

{{< code lang="vim" title="Neovim の設定ファイルを開く（OS に依存しない方法）" >}}
:e $MYVIMRC
{{< /code >}}

Neovim は、設定ファイルとして `init.lua` と `init.vim` のどちらかを使うことができます（`init.lua` が優先されます）。
実際に読み込まれた設定ファイルのパスが __`$MYVIMRC`__ に格納されるので、上記のように設定ファイルを開くことができます（`:echo $MYVIMRC` でパスを確認できます）。

明示的にファイルパスを指定して `init.lua` または `init.vim` を開きたいときは次のようにします。

{{< code lang="vim" title="Neovim の設定ファイルを開く（Linux/macOS の場合）" >}}
:e ~/.config/nvim/init.lua
:e ~/.config/nvim/init.vim
{{< /code >}}

{{< code lang="vim" title="Neovim の設定ファイルを開く（Windows の場合）" >}}
:e %USERPROFILE%\AppData\Local\nvim\init.lua
:e %USERPROFILE%\AppData\Local\nvim\init.vim
{{< /code >}}

- 参考: [Vim/Neovim の設定ファイルのパスを確認する (`$MYVIMRC`)](/p/7mabuvq/)


設定ファイルをリロードする
----

設定ファイルを変更した後で、その内容を反映させるには下記のように __`:source`__ コマンドでそのファイルを読み込みます。

{{< code lang="vim" title="Vim の場合" >}}
:source $MYVIMRC   " ~/.vimrc のリロード
:source $MYGVIMRC  " ~/.gvimrc のリロード
{{< /code >}}

Neovim の場合、`$MYVIMRC` が示すパスは、`init.lua` または `init.vim` のうち実際に読み込まれたファイルのパスになります。
どちらの場合も `:source` コマンドで読み込むことができます。

{{< code lang="vim" title="Neovim の場合" >}}
:source $MYVIMRC   " init.lua あるいは init.vim のリロード
{{< /code >}}

{{% note title=":luafile コマンド" %}}
Neovim には Lua スクリプトを読み込むための **`:luafile`** コマンドもあります。
こちらは純粋に `.lua` ファイルしか読み込めないため、Vimscript として記述された `init.vim` を開こうとするとエラーになります。
`:source` コマンドを使った場合は、Lua スクリプトが指定された場合に、内部的に `:luafile` コマンドで読み込んでくれます。

現在編集中の Lua ファイルを実行したいときは、`:luafile %` とします。
{{% /note %}}


参考
----

- [ショートカットキーで設定ファイル (`.vimrc`, `init.lua`) を開く](/p/r5fcfgk/)

