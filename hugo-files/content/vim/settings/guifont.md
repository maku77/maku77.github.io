---
title: "Vim/NeoVim で GUI モード用のフォントを設定する (guifont)"
url: "p/e3xdbxe/"
date: "2008-05-12"
lastmod: "2024-10-28"
tags: ["neovim", "vim"]
changes:
  - 2024-10-28: NeoVim（nvim-qt の設定方法を追加）
aliases: /vim/settings/font-dialog.html
---

フォント設定ダイアログを開く
----

nvim-qt や gVim などの GUI モードで動作する Vim/NeoVim 環境では、次のようにフォント設定ダイアログを開くことができます。

```vim
:set guifont=*
```

ターミナル上で `vim` や `nvim` を動かしているときは、そのターミナルで設定されているフォントがそのまま使われるので、この設定は必要ありません。

ダイアログでフォントの設定を行った後は、

```vim
set guifont?
```

として `guifont` オプションに具体的にどのような設定値が格納されているかを確認することができます。
この設定値を、設定ファイルに記述しておけば、次回の起動時からは自動的にそのフォント設定が反映されます。


設定ファイルでフォントを指定する
----

### NeoVim (nvim-qt) の場合

GUI モードの NeoVim（`nvim-qt` など）のフォントを設定するには、コマンドラインモードでの `:set guifont=Consolas:h14` に相当する下記のようなコードを実行すれば OK です。

{{< code lang="lua" title="NeoVim (~/.config/nvim/init.lua) の場合" >}}
vim.o.guifont = "Consolas:h14"
{{< /code >}}

現在の環境が GUI モードで動作しているかどうかを調べるには、`vim.fn.has('gun_running')` の値が 1 かどうかをチェックします。
下記は、[HackGen フォント](https://github.com/yuru7/HackGen/releases)を使用するように設定した例です。

{{< code lang="lua" title="NeoVim (~/.config/nvim/init.lua) の場合" >}}
--
-- GUI モード（nvim-qt など）のフォント設定
--
-- 白源フォント (HackGen Console NF) を下記からダウンロードしておく。
-- Nerd Fonts が組み込まれているのでリッチな UI 表示に使える。
-- https://github.com/yuru7/HackGen/releases
--
if vim.fn.has('gui_running') == 1 then
  vim.o.guifont = "HackGen Console NF:h14"
end
{{< /code >}}

さらに Windows かどうかで分岐させたいときは、`vim.fn.has("win64") == 1` による条件分岐を追加してください。

### gVim/MacVim の場合

gVim/MacVim では、`.gvimrc` ファイルに GUI モード用のフォント設定を記述します。
次の例では、Windows と macOS でそれぞれ異なるフォントを設定しています。

{{< code lang="vim" title="~/.gvimrc" >}}
" Font settings for Windows and macOS
if has("win64")
    set guifont=ＭＳ_ゴシック:h14::cSHIFTJIS
    set ambiwidth=double  "Display double-width symbols properly
elseif has("macunix")
    set guifont=Menlo\ Regular:h14
endif
{{< /code >}}

