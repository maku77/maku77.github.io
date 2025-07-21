---
title: "Vim 上でディレクトリツリーを表示する (NERDTree)"
url: "p/8qeuow8/"
date: "2012-07-22"
lastmod: "2025-07-22"
changes:
  2025-07-22: リポジトリパスを `preservim/nerdtree` に変更
tags: ["vim"]
---

NERDTree とは
----

NERDTree を使うと、Vim の左端にエクスプローラバーのようなものを表示できます。

- [https://github.com/preservim/nerdtree](https://github.com/preservim/nerdtree)


NERDTree のインストール
----

公式サイトにはいくつかインストール方法が書かれていますが、ここでは [Vundle](/p/b85489c/) を使ってインストールします。

{{< code lang="vim" title="~/.vimrc" >}}
call vundle#begin()
  Plugin 'preservim/nerdtree'
call vundle#end()
{{< /code >}}

上記のように `.vimrc` などに記述しておけば、次のように実行するだけで簡単に NERDTree をインストールできます。

```vim
:PluginInstall
```


NERDTree tabs のインストール
----

NERDTree プラグインである NERDTree tabs をインストールしておくと、NERDTree とタブを組み合わせた操作がより直感的になります。

- [https://github.com/jistr/vim-nerdtree-tabs](https://github.com/jistr/vim-nerdtree-tabs)

このプラグインをインストールすると、NERDTree が以下のような振る舞いをするようになります。

* Vim 起動時に NERDTree を自動的に開く
* NERDTree 上で `t` キーを押してファイルをタブで開いたときに、NERDTree のウィンドウを開いたままにする


NERDTree tabs プラグインも、NERDTree 本体と同様に、Vundle を使って簡単にインストールすることができます。

{{< code lang="vim" title="~/.vimrc" >}}
call vundle#begin()
  " NERDTree 本体
  Plugin 'preservim/nerdtree'

  " NERDTree tabs プラグイン
  Plugin 'jistr/vim-nerdtree-tabs'
call vundle#end()
{{< /code >}}

上記のように `.vimrc` に記述しておき、以下のようにインストールします。

```vim
:BundleInstall
```


NERDTree の使い方の基本
----

まずは、以下のコマンドでツリーを開きます。

```vim
:NERDTree
```

次のコマンドを使うと、ツリーをトグル開閉できます。

```
:NERDTreeToggle
```

ツリーにカーソルがある状態で以下のような操作を行うことができます。

- 全般的な操作
  - **`q`**: NERDTree を終了
  - **`?`**: Quick ヘルプを表示（もう一度押すとツリー表示に戻る）
- ファイルのオープン
  - **`Enter`** or **`o`**: ファイルを開く
  - **`s`**: 縦分割してファイルを開く
  - **`i`**: 横分割してファイルを開く
  - **`t`**: タブでファイルを開く
--- ディレクトリの展開、移動
  - **`Enter`** or **`o`**: ディレクトリを開く／閉じる
  - **`O`**: ディレクトリを再帰的に開く
  - **`x`**: 親ディレクトリを閉じる
  - **`u`**: 表示上のルートを 1 つ上位へ移動
  - **`U`**: 表示上のルートを 1 つ上位へ移動（開いたディレクトリをキープしたまま）
  - **`C`**: 表示上のルートをカーソルのあるディレクトリに移動


NERDTree を使用したファイル操作
----

NERDTree のウィンドウ上で、`m` を入力すると、ファイルやディレクトリを操作するためのメニューが表示されます。

```
NERDTree menu. Use j/k/enter and the shortcuts indicated
==========================================================
> (a)dd a child node
  (m)ove the current node
  (d)elete the current node
  (r)eveal in Finder the current node
  (o)pen the current node with system editor
  (q)uicklook the current node
  (c)opy the current node
```

**`j`** キーや **`k`** キーで項目を選択するか、直接ショートカットキーを入力することで様々な操作を行うことができます。
よく使用するのは以下のコマンドです。

- **`a`**: ファイル/ディレクトリを新規作成
- **`m`**: ファイル/ディレクトリ移動 or ファイル名変更
- **`d`**: ファイル/ディレクトリを削除


現在編集中のファイルを NERDTree でフォーカスする (NERDTreeFind)
----

Vim で何らかのファイルを編集しているときに、新しく別のファイルを開くと、NERDTree で表示しているツリー階層とファイルの位置が一致しなくなることがあります。
そのような場合は、次のコマンドを実行することで、NERDTree 上で現在編集中のファイルをフォーカスできます。

```
:NERDTreeFind
```

この `NERDTreeFind` コマンドはよく使うので、`NERDTreeToggle` コマンドと合わせて、キーマッピング定義しておくと便利です。

{{< code lang="vim" title="~/.vimrc" >}}
" NERDTree 用のキーマップ
nnoremap <Leader>nt :<C-u>NERDTreeToggle<CR>
nnoremap <Leader>nf :<C-u>NERDTreeFind<CR>
{{< /code >}}


NERDTree のブックマーク機能を使用する
----

NERDTree のブックマーク機能を使用すると、よく使用するディレクトリやファイルをすぐに開けるようになります。
NERDTree 上で `B` と入力すると、ブックマークの一覧を表示／非表示できます。

{{< code title="表示例" >}}
----------Bookmarks----------
maku-sample <box/maku-sample/
mongoose <maku-test-mongoose/
{{< /code >}}

ブックマークを追加するには、対象のノードを選択した状態で以下のように入力します。
プロジェクトの起点となるディレクトリを追加しておくと便利です。

- **`:Bookmark`**: ブックマークを追加
- **`:Bookmark <名前>`**: 指定した名前でブックマークを追加

ブックマークを削除するには、一覧上で **`D`** と入力します。


参考
----

* [ディレクトリエクスプローラー (Explore) を使用してファイルを開く](/p/928ca4t/)

