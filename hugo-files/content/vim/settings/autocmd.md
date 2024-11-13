---
title: "Vim の autocmd で自動コマンドを登録する"
url: "p/rj6oatw/"
date: "2017-09-04"
lastmod: "2024-11-13"
tags: ["vim", "neovim"]
description: "Vim の autocmd（自動コマンド）機能を使用すると、様々なイベントの発生を監視し、そのタイミングで自動的に任意の処理を実行することができます。例えば、ファイルを開くときに、拡張子の種類に応じた設定を行うといったことが実現できます。"
changes:
  - 2024-11-13: 文書を校正
aliases: /vim/settings/autocmd.html
---

autocmd の概要
----

Vim の **`autocmd`** コマンドを使って自動コマンドを登録しておくと、ファイルのオープン時、保存時、カーソル移動時など、様々なタイミングで任意の処理を行えるようになります。
例えば、下記のようなことが行えます。

* プログラミング言語のソースコードを開いたときに、言語に応じてインデントを設定する
* 圧縮されたテキストファイルを開いたときに、自動的に解凍してテキストを編集できるようにする（保存するときに再び自動で圧縮する）
* ノーマルモードに戻った時に、IME（日本語入力モード）をオフにする
* 日記ファイルを開いたときに自動で本日の日付を挿入する
* ファイルの種類によって異なる種類のキーワードハイライト設定を有効にする

下記は具体的な `autocmd` コマンドの使い方のサンプルです。

{{< code lang="vim" title="例: .txt ファイルを編集するときはタブキー入力をスペースに展開する" >}}
:autocmd BufEnter  *.txt  setlocal expandtab
{{< /code >}}

`autocmd` コマンドに関する詳細なドキュメントは、Vim から `:help autocmd.txt` と入力して参照することができます。
下記サイトでも同じ内容のドキュメントを読むことができます。

* [autocmd - Vim Documentation（日本語）](http://vim-jp.org/vimdoc-ja/autocmd.html)
* [autocmd - Vim Documentation（英語）](http://vim-jp.org/vimdoc-en/autocmd.html)

`autocmd` は構文が若干複雑なのでとっつきにくいのですが、使いこなせると非常に便利なので、是非ここで使い方をマスターしましょう。


autocmd で自動コマンドを設定する
----

```vim
:autocmd ＜イベント＞ ＜ファイルパターン＞ ＜実行コマンド＞
```

という形式で `:autocmd` を実行すると、`＜ファイルパターン＞` に一致するファイルにおいて、`＜イベント＞` が発生したときに、`＜実行コマンド＞` が実行されるようになります。

{{< code lang="vim" title="例: .html ファイルを読み込んだときに（この設定ファイルと）同じディレクトリにある html.vim を実行する" >}}
:autocmd BufNewFile,BufRead  *.html  source <sfile>:h/html.vim
{{< /code >}}

上記では、分かりやすくするために、`＜イベント＞`、`＜ファイルパターン＞`、`＜実行コマンド＞` を 2 文字分のスペースで区切っています。


### イベント

`＜イベント＞` の部分に指定できるイベント名は、下記のヘルプに一覧があります。

* [autocmd-events - Vim Documentation](http://vim-jp.org/vimdoc-en/autocmd.html#autocommand-events)

イベント名はヘルプファイル上では `CamelCase` の形式で記載されていますが、__イベント名の大文字と小文字の違いは無視されます__。
複数のイベントに対して同じ自動コマンドを登録したいときは、__イベント名をカンマで並べて指定することができます__。
ただし、__カンマの前後にスペースを入れてはいけないことに注意してください__（例: `BufNewFile,BufRead`）。
`BufNewFile` イベントは新しいファイルを作成するときに発生し、`BufRead` インベントは既存のファイルを読み込んだときに発生します。

### ファイルパターン

`＜ファイルパターン＞` の部分には、対象とするファイルを下記のような感じで指定します。

| パターン | 説明 |
| ---- | ---- |
| `*` | すべてのファイル |
| `*.html` | 拡張子 `.html` を持つファイル |
| `*.txt,*.info` | 拡張子 `.txt` あるいは `.info` を持つファイル（カンマの前後にスペースを入れてはいけません） |
| `*/tests/*.java` | ファイルパスのどこかに `tests` ディレクトリを含む `*.java` ファイル |
| `~/.vimrc` | ホームディレクトリ内の `.vimrc` ファイル |

ディレクトリセパレータは、Windows の場合でもスラッシュ (`/`) を使用することに注意してください。
バックスラッシュ (`\`) は特殊な意味を持つため、ディレクトリセパレータとしては使用できません。

ファイルパターンの指定には、`*.txt` のような指定だけではなく、`*/doc/*.txt` というワイルドカードの組み合わせも指定できます。
この場合、任意のパスにある `doc` ディレクトリ以下の `*.txt` ファイルにマッチするようになります。
例えば、あるプロジェクト内のソースコードだけ、特殊なインデントで編集したい場合などに利用できます。

{{< code lang="vim" title="例: mysite ディレクトリ以下の .md ファイルのみタブのスペース数を 2 に設定する" >}}
:autocmd BufEnter */mysite/*.md setl expandtab tabstop=2 shiftwidth=2 softtabstop=2 shiftround
{{< /code >}}

アスタリスク (`*`) は、ディレクトリセパレータを含む任意のパスにマッチするので、上記の指定は、`/Users/maku/mysite/aaa/bbb/ccc.md` といった深い階層にあるファイルにもマッチします。

`＜イベント＞` に `FileType` を指定した場合（ファイルタイプの変更イベントを監視）は、ファイル名のパターンの代わりに `c,cpp` や `java` のようなファイルタイプ名を指定することができます。

{{< code lang="vim" title="例: C/C++ と Java のソースコードのインデント方法を設定する" >}}
:autocmd FileType c,cpp,java setl cindent expandtab tabstop=4 shiftwidth=4 softtabstop=4 shiftround
{{< /code >}}


autocmd の一覧を表示する
----

`autocmd` コマンドをパラメータなしで実行すると、現在登録されている自動コマンド (autocommands) の一覧を表示することができます。

{{< code lang="vim" title="すべての autocmd を列挙する" >}}
:autocmd
{{< /code >}}

イベント名を指定すると、そのイベント用に登録されている自動コマンドのみを列挙することができます。

{{< code lang="vim" title="FileType イベント用の autocmd を列挙する" >}}
:autocmd FileType
{{< /code >}}


自動コマンドのグループ化と設定のクリア (augroup, autocmd!)
----

`autocmd` は、__実行するたびに自動コマンドを追加していきます__。
つまり、`.vimrc` に `autocmd` の定義が書かれている場合に、その `.vimrc` を何度も `source` で読み込んでいると、同じ自動コマンドが重複して登録されてしまいます。
このような自動コマンドの重複登録を避けるには、`autocmd` による各種自動コマンドの登録前に、**`autocmd!`** を呼び出すことで前回登録した自動コマンドをクリアするようにします。
Vim のヘルプ (`:help autocmd.txt`) には、次のようにグループ単位で自動コマンドの登録をクリアする方法が示されています。

{{< code lang="vim" title="~/.vimrc" >}}
augroup hoge
  autocmd!  " Clear the autocommands in the hoge group first
  au BufNewFile,BufRead  *.html  so <sfile>:h/html.vim
augroup END
{{< /code >}}

この例のように、**`augroup hoge` ... `augroup END`** というブロック内に処理を記述すると、`autocmd!` による自動コマンドのクリアや、`autocmd` による自動コマンドの登録が `hoge` という自動コマンドグループに対して作用するようになります。
`hoge` というグループ名の部分は、任意の名前を付けることができますが、`end` や `END` はグループブロックの終了を表すキーワードとして使用されるため、グループ名としては使用できません。
より明確に言うと、`augroup END` という宣言は、それ以降の `autocmd` 対象グループをデフォルトグループに変更するという意味を持っています。

結果として、上記の例のように記述しておくと、最初にすべての自動コマンドをクリアし、改めて `autocmd` で自動コマンドを登録し直していくという動作になります。
これにより、`.vimrc` ファイルをリロード (`:source ~/.vimrc`) したときに、自動コマンドが重複登録されるのを防ぐことができます。

ちなみに、自動コマンドの登録を上記のようにグルーピングしておくことで、登録されている自動コマンドの一覧をそのグループに絞って確認することができるようになります。

{{< code lang="vim" title="例: hoge グループに登録された自動コマンドの一覧を表示する" >}}
:autocmd hoge
{{< /code >}}


いろいろな autocmd の例
----

```vim
" C++ のソースコードを編集するときは、タブキーでスペースによるインデントを挿入する
:autocmd FileType  cpp  setlocal expandtab

" Makefile を編集するときは、タブキーでタブ文字をそのまま挿入する
:autocmd FileType  make  setlocal noexpandtab

" ~/.vimrc を保存したときは直ちにリロードする（source コマンドで変更を反映する）
:autocmd BufWritePost  ~/.vimrc  source ~/.vimrc

" 拡張子 .cpp のファイルを開いたバッファに入るとき、~/.vimrc_cpp を実行する
:autocmd BufEnter  *.cpp  source ~/.vimrc_cpp

" C 言語のソースコードを編集し始めるときに、カーソルを最初の関数に合わせる
:autocmd BufRead  *.c,*.h  1;/^{

" 新規に .java ファイルを作成する場合にテンプレートファイルを使用する（:e new.java や :tabnew new.java としたとき）
:autocmd BufNewFile  *.java  0r ~/vim/skeleton.java

" 新規に .hello ファイルを作成する場合に Hello というテキストを挿入する
:autocmd BufNewFile  *.hello  put='Hello'
```

