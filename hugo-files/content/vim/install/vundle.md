---
title: "Vundle をインストールして Vim のプラグイン環境を作る"
url: "p/b85489c/"
date: "2013-04-07"
lastmod: "2025-07-22"
tags: ["vim"]
changes:
  - 2025-07-21: GitHub リポジトリを gmarik/Vundle.vim から VundleVim/Vundle.vim に修正
---

Vundle とは
----

Vundle をインストールすると、Vim のプラグインを `:BundleInstall` コマンドで簡単にインストールすることができるようになります。

Vundle のインストール
----

基本的には、以下のサイトの手順に従ってインストールするだけです。

* [https://github.com/VundleVim/Vundle.vim](https://github.com/VundleVim/Vundle.vim)

下記のようにインストールすると、Vim のプラグインを `~/.vim/bundle` ディレクトリ内で管理することができるようになります。
インストールしたプラグインを消す場合も、ディレクトリごと削除するだけなのでお手軽です。

{{< code lang="console" title="Vundle のダウンロード" >}}
$ git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
{{< /code >}}

本家サイトには、`.vimrc` に Vundle 用の設定を記述する方法が説明されていますが、個人的には、プラグイン関連の情報は別ファイル (`~/vimrc_vundle.vim`) で管理して `~/.vimrc` から読み込むようにしています。

{{< code lang="vim" title="~/.vimrc（Vundle 用の設定をインクルード）" >}}
source ~/vundle_vimrc.vim
{{< /code >}}

{{< code lang="vim" title="~/vundle_vimrc.vim（Vundle 用の設定）" >}}
""" Enable vundle commands
set nocompatible  " be iMproved, required
filetype off      " required
" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim

call vundle#begin()
  " let Vundle manage Vundle, required
  Plugin 'VundleVim/Vundle.vim'

  " ★
  " ★ここにプラグインを追加していく
  " ★
call vundle#end()            " required

filetype plugin indent on    " required
{{< /code >}}

上記のように設定して Vim を立ち上げ直すと、`:BundleList`、`:BundleInstall`、`:BundleSearch`、`:BundleClean` などのコマンドを実行できるようになります。


Vundle を使ったプラグインのインストール
----

Vundle を使ってインストールしたいプラグインは、以下のように **`call vundle#begin()`** と **`call vundle#end()`** の間に **`Plugin`** コマンドで列挙していきます。
ここでは、モードラインを格好よく表示する、`vim-powerline` をインストールしてみます。

{{< code lang="vim" title="~/vundle_vimrc.vim" hl_lines="6-7" >}}
""" Enable vundle commands
call vundle#begin()
  " let Vundle manage Vundle, required
  Plugin 'VundleVim/Vundle.vim'

  " プラグインの追加
  Plugin 'Lokaltog/vim-powerline'
call vundle#end()
{{< /code >}}

上記のように列挙したプラグインを実際にインストールするには、もう一度 `:BundleInstall` コマンドを実行します。
逆に使わなくなったプラグインは **`:BundleClean`** コマンドで削除できます。
現在インストールされているプラグインを列挙するには、**`:BundleList`** コマンドを実行します。

