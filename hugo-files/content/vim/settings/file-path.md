---
title: "Vim/NeoVim の設定ファイルのパスを確認する ($MYVIMRC)"
url: "p/7mabuvq/"
date: "2007-12-26"
lastmod: "2024-01-10"
changes:
  - 2024-01-10: NeoVim の説明を追加
tags: ["vim"]
aliases: /vim/settings/rc-files.html
---

設定ファイルのパスを調べる
----

Vim や NeoVim の設定ファイル（やディレクトリ）のパスは、`:echo` コマンドを使って以下のように確認することができます。

- Vim の場合:
  - __`:echo $MYVIMRC`__<br>
    出力例: `C:\Users\maku\_vimrc` （Windows の場合）<br>
    出力例: `/Users/maku/.vimrc` （Linux/macOS の場合）
  - __`:echo $MYGVIMRC`__<br>
    出力例: `C:\Users\maku\_gvimrc` （Windows の場合）<br>
    出力例: `/User/maku/.gvimrc` （Linux/macOS の場合）
- NeoVim の場合（`init.lua` と `init.vim` のうち実際に読み込まれたファイルのパス）:
  - __`:echo $MYVIMRC`__ or __`:=vim.env.MYVIMRC`__<br>
    出力例: `C:\Users\maku\AppData\Local\nvim\init.lua` （Windows の場合）<br>
    出力例: `/Users/maku/.config/nvim/init.lua` （Linux/macOS の場合）

また、`nvim` ディレクトリのパスは、以下のように確認できます。

- __`:echo stdpath('config')`__<br>
  出力例: `C:\Users\maku\AppData\Local\nvim` （Windows の場合）<br>
  出力例: `/Users/maku/.config/nvim` （Linux/macOS の場合）


デフォルトのパス
----

デフォルトでは、次のようなファイルパスに置かれた設定ファイルが読み込まれます。

- Vim の場合
  - Windows の場合: __`%USERPROFILE%/_vimrc`__ （ただし `HOME` 環境変数が設定されている場合は `%HOME%/_vimrc`）
  - Linux/macOS の場合: __`~/.vimrc`__
- NeoVim の場合
  - Windows の場合: __`%USERPROFILE%\AppData\Local\nvim\init.vim`__
  - Linux/macOS の場合: __`~/.config/nvim/init.vim`__

Vim/NeoVim の設定ファイルは基本的に自分で作成する必要があります。

{{< code lang="console" title="NeoVim の設定ファイル (init.lua) を作成する" >}}
$ mkdir -p ~/.config/nvim
$ touch ~/.config/nvim/init.lua
{{< /code >}}


version コマンド
----

Vim の `:version` コマンドの出力の中央あたりを見ると、起動時に Vim がロードする設定ファイルの一覧を確認することができます。

{{< code title="VIM7.0 (Windows) の :version コマンドの出力（抜粋）" >}}
...
      システム vimrc: "$VIM\vimrc"
      ユーザー vimrc: "$HOME\_vimrc"
   第2ユーザー vimrc: "$HOME\vimfiles\vimrc"
   第3ユーザー vimrc: "$VIM\_vimrc"
       ユーザー exrc: "$HOME\_exrc"
    第2ユーザー exrc: "$VIM\_exrc"
     システム gvimrc: "$VIM\gvimrc"
     ユーザー gvimrc: "$HOME\_gvimrc"
  第2ユーザー gvimrc: "$HOME\vimfiles\gvimrc"
  第3ユーザー gvimrc: "$VIM\_gvimrc"
  デフォルトファイル: "$VIMRUNTIME\defaults.vim"
    システムメニュー: "$VIMRUNTIME\menu.vim"
...
{{< /code >}}

