---
title: "Vim でウィンドウ（タブ）移動時にカレントディレクトリも自動で移動する (autochdir)"
url: "p/dpccbv7/"
date: "2020-05-29"
tags: ["neovim", "vim"]
aliases: /vim/settings/autochdir.html
---

カレントディレクトリとは
----

Vim のカレントディレクトリは、Vim で最初に開いたファイルがあるディレクトリに設定されます。
カレントディレクトリは、例えば下記のようなコマンドを実行したときに、起点となるディレクトリとして参照されます。

- `:vim pattern **/*.txt` などで複数ファイルを grep するとき
- `:e sample.txt` でファイルを開くとき
- `:cd dirname` でカレントディレクトリを移動するとき
- `:terminal` でターミナルを開くとき

このカレントディレクトリは、`:e` や `:tabnew` コマンドで別のディレクトリにあるファイルを開いた場合も変化しません。
`:vim (:vimgrep)` コマンドでファイルを検索してもなぜかヒットしないというときは、カレントディレクトリが正しくセットされてない可能性があります。
カレントディレクトリは `:pwd` で確認することができます。


カレントディレクトリを変更する
----

### 手動で移動する

明示的にカレントディレクトリを設定するには **`:cd`** コマンドを使用します。

{{< code lang="vim" title="編集中のファイルがあるディレクトリへ移動する" >}}
:cd %:h
{{< /code >}}

と実行すると、現在編集中のファイルがあるディレクトリ (**`%:h`**) へ移動することができます。
カレントウィンドウ（カーソルのあるウィンドウ）のみを対象にカレントディレクトリを移動したいときは、`:cd` の代わりに **`:lcd`** コマンドを使用します。

### 自動で移動する

**`autochdir`** オプションをセットしておくと、新しくウィンドウを開いた時や、タブを切り替えたときなどに、そのファイルがあるディレクトリへ自動で移動してくれるようになります。

{{< code lang="vim" title="~/.vimrc" >}}
" Change the current working directory automatically
set autochdir
{{< /code >}}

{{< code lang="lua" title="Neovim の場合 (~/.config/nvim/init.lua)" >}}
-- Change the current working directory automatically
vim.opt.autochdir = true
{{< /code >}}

現在の設定は `:set autochdir?` コマンドで確認できます。

[`autocmd` を設定してディレクトリ移動を自動化する方法](/p/4ekh9ba/) もありますが、`autochdir` が使える Vim 環境であればそちらを使った方が簡単です。

