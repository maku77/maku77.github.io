---
title: Vundle をインストールして Vim のプラグイン環境を作る
date: "2013-04-07"
---

Vundle とは
----

Vundle をインストールすると、Vim のプラグインを BundleInstall コマンドで簡単にインストールすることができるようになります。

Vundle のインストール
----

基本的には、以下のサイトの手順に従ってインストールするだけです。

* [https://github.com/gmarik/vundle](https://github.com/gmarik/vundle)

下記のようにインストールすると、Vim のプラグインを `~/.vim/bundle` ディレクトリ内で管理することができるようになります。
インストールしたプラグインを消す場合も、ディレクトリごと削除するだけなのでお手軽です。

~~~
$ git clone git://github.com/gmarik/vundle.git ~/.vim/bundle/vundle
~~~

本家サイトには、`.vimrc` に Vundle 用の設定と、インストールするプラグインの一覧を記述する方法が示されています。
個人的には、プラグイン関連の情報は別ファイルで管理したいので、`~/my_vundle.vimrc` というファイルを作成し、そこに Vundle の設定を記述しています。

#### ~/.vimrc（Vundle 用の設定をインクルード）

~~~ vim
source ~/my_vundle.vimrc
~~~

#### ~/my_vundle.vimrc（Vundle 用の設定）

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

