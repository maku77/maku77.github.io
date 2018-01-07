---
title: MacOSX に香り屋版の GVim をインストールする
date: "2011-03-23"
---

MacVim をインストール
----

* [http://code.google.com/p/macvim-kaoriya/](http://code.google.com/p/macvim-kaoriya/)

から macvim-kaoriya-20110202.dmg などをダウンロードできます。
dmg ファイルを開くと、MacVim.app ファイルがあるので、それを Applications フォルダにドラッグ＆ドロップすればインストール完了です。


コマンドラインから GUI 版の GVim を起動できるように設定
----

* [http://code.google.com/p/macvim-kaoriya/wiki/Readme](http://code.google.com/p/macvim-kaoriya/wiki/Readme)

に書いてあるように、`~/.bash_profile` などに以下のように書いておくと、`gvim ＜ファイル名＞` と入力するだけで GUI の GVim ウィンドウを起動できるようになります。

~~~ bash
alias gvim='open -a /Applications/MacVim.app "$@"'
~~~

個人的には、`vi` や `vim` コマンドでも全部 `gvim` として開きたいので、さらに以下のようにエイリアス定義しちゃってます。

~~~ bash
alias vi=gvim
alias vim=gvim
~~~

.txt ファイルをダブルクリックしたときに GVim で開くようにする
----

特定の拡張子のファイルを GVim に関連づけるには、以下のようにします。

1. ファイルを右クリック ==> [情報を見る]
2. [このアプリケーションで開く] で MacVim.app を選択

