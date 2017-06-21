---
title: Vundle をインストールして Vim のプラグイン環境を作る
created: 2013-04-07
---

Vundle とは
----

Vundle をインストールすると、Vim のプラグインを、`~/.vim/bundle` ディレクトリ内で管理することができるようになります。
インストールしたプラグインを消すのもディレクトリごと削除するだけで済みます。

Vundle のインストール
----

基本的には、以下のサイトの手順に従ってインストールするだけです。

* [https://github.com/gmarik/vundle](https://github.com/gmarik/vundle)

~~~
$ git clone git://github.com/gmarik/vundle.git ~/.vim/bundle/vundle
~~~

本家サイトには、`.vimrc` に設定を追記していく方法が記述されていますが、個人的には `~/my_vundle.vimrc` のような別ファイルに Vundle 用の設定を書いて、`~/.vimrc` からこのファイルを `source` でインクルードするようにしてます。

#### ~/.vimrc

~~~ vim
source ~/my_vundle.vimrc
~~~

#### ~/my_vundle.vimrc

~~~ vim
""" Enable vundle commands
set nocompatible
filetype off
set rtp+=~/.vim/bundle/vundle
call vundle#rc()

" let Vundle manage Vundle
Bundle 'gmarik/vundle'
~~~

上記の設定により、`:BundleList`, `:BundleInstall`, `:BundleSearch`, `:BundleClean` などのコマンドを実行できるようになります。


Vundle を使ったプラグインのインストール
----

Vundle を使ってインストールしたいプラグインは、以下のように `Bundle` コマンドで設定ファイルに列挙しておきます。
ここでは、モードラインを格好よく表示する、`vim-powerline` をインストールしてみます。

~~~ vim
...
Bundle 'Lokaltog/vim-powerline'
~~~

上記のように列挙したプラグインを実際にインストールするには、`:BundleInstall` コマンドを実行します。
現在インストールされているプラグインを列挙するには、`:BundleList` コマンドを実行します。

