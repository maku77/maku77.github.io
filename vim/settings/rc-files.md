---
title: 現在の環境で読み込まれる設定ファイルの一覧を確認する
date: "2007-12-26"
---

`:version` コマンドの出力の中央あたりを見ると、起動時に VIM がロードする設定ファイルの一覧を確認することができます。

#### 例: VIM7.0 (Windows) の場合

~~~
:version
...
      システム vimrc: "$VIM\vimrc"
        ユーザ vimrc: "$HOME\_vimrc"
     第2ユーザ vimrc: "$VIM\_vimrc"
         ユーザ exrc: "$HOME\_exrc"
      第2ユーザ exrc: "$VIM\_exrc"
     システム gvimrc: "$VIM\_gvimrc"
       ユーザ gvimrc: "$HOME\_gvimrc"
    第2ユーザ gvimrc: "$VIM\_gvimrc"
    システムメニュー: "$VIMRUNTIME\menu.vim"
...
~~~

実際にどのようなファイルが読み込まれるかは、以下のコマンドで確認することができます。

~~~
:set runtimepath?
~~~

