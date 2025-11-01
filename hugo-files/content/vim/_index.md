---
title: "Vim"
url: "/vim/"

categoryName: "まくまく Vim ノート"
categoryUrl: "/vim/"
categoryIcon: logo-vim.svg
---

基本 <!-- basic -->
----

- [ヘルプコマンドの使い方 (`:help`, `:helpgrep`)](/p/fdep5i7/)
- 移動
  - [カーソルの移動方法まとめ](/p/etvrhdd/)
  - [Vim/Neovim の画面スクロール方法まとめ (`scroll`, `scrolljump`, `scrolloff`)](/p/gu9om5z/)
  - [カーソル位置にマークしてジャンプしてこれるようにする](/p/i3ao6oc/)
  - [C/C++ の変数／マクロの定義位置にジャンプする](/p/96itrdp/)
- [文字の削除方法まとめ（テキストオブジェクト） (`d`, `delete`)](/p/qbmdoef/)
- [文字の置換方法まとめ (`:s`, `:substitute`)](/p/f8v2npx/)
- [ウィンドウを分割する](/p/ym9pa88/)
- [アンドゥとリドゥ操作 (`u`, `Ctrl-r`, `undolevels`)](/p/rihibef/)
- [ビジュアルモードの基本 (`v`, `Shift-v`, `Ctrl-v`)](/p/iumn4xs/)

### コマンドモード (`:`, `/`, `?`)

- [コマンドモードでのカーソル移動 (`Ctrl-B`, `Ctrl-E`, `Ctrl-H`, `Ctrl-U`)](/p/f92ub3d/)
- [コマンドモードで入力補完する (`Ctrl-D`, `Tab`))](/p/ow6rvbz/)
- [ex コマンドでの行範囲指定方法いろいろ](/p/fw7q8q7/)

### 検索

- [Vim 内で grep を実行して見つかったファイルへジャンプする (`:vimgrep`, `:grep`)](/p/c4q8amz/)
- [Vim でカレントファイル内の文字列を検索する (`/`, `?`, `*`, `#`)](/p/u4gw7c3/)
- [Vim で大文字と小文字を区別しないで検索する (`ignorecase`, `\c`)](/p/mfcba2q/)
- [Vim の検索に関する設定 (`ignorecase`, `smartcase`, `wrapscan`, `hlsearch`, `incsearch`)](/p/v4cuc9g/)

### コマンドライン

- [Vim を起動するときに行番号や検索パターンを指定してファイルを開く (`+n`, `+/pattern`)](/p/x8f9e87/)
- [サイレントバッチモードで ex スクリプトをファイルに適用する (`-es`)](/p/q7eoz2z/)


ファイル <!-- file -->
----

### ファイルを開く

- [ファイル名を指定してファイルを開く (`:e`, `:edit`, `:view`)](/p/6aycfga/)
- [カーソル位置の単語をファイル名としてファイルを開く (`gf`, `Ctrl-w gf`, `Ctrl-w f`)](/p/sok5efu/)
- [編集中のファイルと拡張子だけが異なるファイルを開く (`%<`)（Hello.cpp を編集中に Hello.h を開く）](/p/j3w2dpc/)
- [ディレクトリエクスプローラー (Explore) を使用してファイルを開く (`:E`, `:Ve`, `:He`)](/p/928ca4t/)
- [Vim でカレントディレクトリを Windows エクスプローラーで開く](/p/s8wxbon/)

### ファイルを保存する

- [Vim のファイルの保存コマンド `:w`、`:x` の基本](/p/9pyh4ip/)
- [Vim で指定した行範囲だけ別のファイルに保存する](/p/zmnwu3z/)

### その他

- [Vim で別ファイルの内容や外部コマンドの出力を挿入する (`:read`)](/p/oh8nhyw/)
- [Vim でファイルのエンコーディング形式、改行コードを変更する (`fenc`, `ff`)](/p/g2qyu9b/)
- [Vim でスワップファイルからファイルを復旧する (`:recover`)](/p/z8fh4xj/)
- [Vim で 2 つのファイルの差分を取る・マージする (`vimdiff`, `vim -d`)](/p/78gvwox/)


編集操作 <!-- edit -->
----

* [Vim で挿入モード（インサートモード）のまま実行できる操作の一覧](/p/hk22h6b/)
* [Vim でXML/HTML の編集に便利なコマンド](/p/sznjxob/)

### 文字の追加
* [Vim で補完機能を使用してテキストを入力する](/p/qmuxpqp/)
* [Vim で同じテキストを指定した回数だけ繰り返し挿入する](/p/cismh7n/)
* [Vim で複数行の行頭に同じテキストを挿入する](/p/mp48ku2/)
* [Vim で上の行の文字、下の行の文字をコピーする (CTRL-Y, CTRL-E)](/p/4xbztoe/)

### 削除
* [Vim で空行（改行だけの行）を削除する](/p/6idyjje/)
* [Vim で行末の余分なスペースをまとめて削除する](/p/pdvgf4v/)
* [Vim で記号（引用符など）で囲まれた範囲のテキストを削除する](/p/bbsprto/)
* [Vim で正規表現にマッチする行を削除する](/p/ngf6w24/)

### 整形／変換
* [Vim で行を連結する (`J`, `gJ`)](/p/etgm2a4/)
* [Vim で文字を入れ替える、行を入れ替える（スワップ操作） (`xp`, `ddp`)](/p/xr9stqi/)
* [Vim/Neovim でインデント用のスペースを入力する（シフトコマンド） (`>>`, `<<`, `Ctrl-T`, `Ctrl-D`)](/p/i2m4nqt/)
* [Vim/Neovim で選択した範囲を自動インデントする (=)](/p/pxpgasg/)
* [Vim/Neovim ですでに入力されているタブをスペースに変換する (:retab)](/p/w4qm7ok/)
* [Vim でテキストを中央寄せ／左寄せ／右寄せする](/p/sh79ewb/)
* [Vim で大文字と小文字を変換する (`U`, `u`)](/p/iin6vwz/)
* [Vim でカーソル位置の数字をインクリメント／デクリメントする (`CTRL-A`, `CTRL-X`)](/p/in6mo7q/)
* ソート
  * [Vim で行をソートして重複行を削除する (`:sort u`)](/p/ybvwp6b/)
  * [Vim で行を逆順にソートする (`:sort!`)](/p/g9cckyt/)

### カット＆ペースト
* [Vim で挿入モード、コマンドモードでの貼り付け](/p/xqq89n2/)
* [Vim で最後にヤンクしたテキストを確実に貼り付ける](/p/7jkhjzf/)
* [Vim で切り取り＆貼り付け操作、レジスタの扱いについて理解する](/p/fmbya48/)


応用操作
----
* [Vim 上でディレクトリツリーを表示する (NERDTree)](/p/8qeuow8/)
* [Vim で折りたたみ機能 (folding) を使用する](/p/sxouvi5/)
* [Vim で指定した行範囲を一時的に表示する](/p/k7r37a7/)
* [Vim でキーボードマクロを記録して一連のキー入力を繰り返し実行する](/p/bmriv55/)
* [Vim で編集中のファイルを印刷する (`:hardcopy`)](/p/nw89tw2/)
* [Vim で行情報を表示する](/p/3m53tcz/)
* [Vim で外部の ex スクリプトを実行する](/p/rocrnht/)
* プログラマ向け操作
  * [Vim でプログラムの関数定義などにジャンプする（tags ファイルの利用）](/p/qt8z5d3/)
* [カーソル位置の単語に対して任意のコマンド（ヘルプなど）を実行する (`keywordprg`)](/p/3hp29j9/)
* [Neovim を manpager として使用する（man ページを Neovim で表示する）](/p/hfwenni/)

インストール／環境
----
* [Neovim 用のプラグインマネージャー lazy.nvim をインストールする](/p/cfc9tpn/)
* [Vundle をインストールして Vim のプラグイン環境を作る](/p/b85489c/)
* [Windows の右クリックメニューに「Vimで開く」を追加する](/p/wwatzd9/)
* [Windows でファイルを開くときに既存の GVim 内のタブで開く](/p/6mdd9es/)


Vim の設定 <!-- settings -->
----

### Lua
- [Neovim カスタマイズのために Lua 言語をざっと理解する](/p/d3exkpu/)
- [Neovim の設定ファイル (`init.lua`) を GitHub や Dropbox で管理する](/p/pzakg36/)

{{% private %}}
- [Neovim の設定ファイルをモジュール化して管理する](/p/t76nvuq/)
{{% /private %}}

### ファイル・ディレクトリに関する設定と操作
- [Vim/Neovim の設定ファイルのパスを確認する (`$MYVIMRC`)](/p/7mabuvq/)
- [Vim/Neovim で設定ファイル (`.vimrc`, `init.lua`) を開く、リロードする](/p/zneoq8d/)
- [設定ファイルから別の設定ファイルを読み込む (`source`, `dofile`)](/p/dnso7ds/)
- [Vimの設定: モードラインを使ってファイル単位で書式設定する (`modeline`)](/p/39fajvt/)
- [Vimの設定: バックアップファイル／スワップファイル／アンドゥファイルの設定 (`backup`, `swapfile`, `undofile`)](/p/xv4yhu2/)
- [ウィンドウ（タブ）移動時にカレントディレクトリも自動で移動する (`autochdir`)](/p/dpccbv7/)

### キーマップ設定 <!-- keymap -->
- [キーマップの基本 (`map`, `noremap`)](/p/nqqixxy/)
- [`map`、`noremap` 系コマンドで使用できる特殊キーの一覧 (`key-notation`, `key-codes`)](/p/ibpmg65/)
- [現在のキーマップの一覧を表示する (`map`)](/p/7uxykzp/)
- [`<Leader>` キーを使ったキーコンビネーションを定義する (`mapleader`)](/p/c9kmay4/)
- キーマップ設定の例
  - [ショートカットキーで設定ファイル (`.vimrc`, `init.lua`) を開く](/p/r5fcfgk/)
  - [ショートカットキーで日時を挿入する](/p/8xk6jnw/)
  - [ショートカットキーでタブを切り替える](/p/ksmwhv8/)
  - [ショートカットキーでカレントディレクトリを Windows エクスプローラーや Mac の Finder で開く](/p/tqmr4od/)

### autocmd（自動コマンド）による設定
- [`autocmd` で自動コマンドを登録する](/p/rj6oatw/)
- `autocmd` 設定の例
  - [Neovim設定例: ヤンク時に選択範囲をハイライト表示する (`TextYankPost`)](/p/9qyei4z/)
  - [編集中のスクリプト（Ruby や Python など）をショートカットキーで実行する](/p/nwqjyx8/)
  - [開いたファイルがあるディレクトリをカレントディレクトリにする](/p/4ekh9ba/)

### 表示の設定
* [行番号を表示する (`set number`, `vim.opt.number`)](/p/t8o6tum/)
* [Vim で80文字目に縦線を表示する (`colorcolumn`)](/p/ngz6n6i/)
* [Vim で構文強調（シンタックスハイライト）を有効にする (`syntax`)](/p/wcfbnqj/)
* [Vim でカーソル下の行や列をハイライト表示する (`cursorline`, `cursorcolumn`)](/p/osrx94g/)
* [Vim/Neovim で GUI モード用のフォントを設定する (`guifont`)](/p/e3xdbxe/)
* [制御文字（改行、タブ文字、行末のスペースなど）を表示する (`list`, `listchars`)](/p/s596qii/)
* [全角スペースを見えるように表示する](/p/preoa93/)
* [Vim で■や※などの記号が重なって表示される問題の解決 (`ambiwidth`)](/p/fp2xk7y/)
* [Vim でGVim 起動時のウィンドウの幅、高さを設定する (`columns`, `lines`)](/p/syn3cyx/)
* [Vim でステータスラインの表示内容を設定する (`statusline`, `laststatus`)](/p/oegfris/)

### 入力に関する設定
* [OS のクリップボードとヤンクレジスターを連動させる (`clipboard`)](/p/nnhefs3/)
* [タブ文字の設定 (`tabstop`, `expandtab`, `softtabstop`)](/p/8okf7d3/)
* [インデント（シフトコマンド）を設定する (`shiftwidth`, `shiftround`)](/p/b5o6ksu/)
* [自動インデントモードを有効にする (`autoindent`, `smartindent`, `cindent`)](/p/oe94dkh/)
* [ビジュアルモードで簡単にインデントを行えるようにする](/p/hoihkfy/)
* [インサートモード中に `Backspace` キーや `CTRL-W` で文字を削除するときの振る舞いを変更する (`backspace`)](/p/b9tsccu/)
* [Vim で省略形を用いたテキスト入力を行えるようにする (`abbrevaite`)](/p/qxjtb39/)
* [Vim でスペルチェックを有効にする (`spell`)](/p/fweeaqx/)
* [Vimの設定: Java や C/C++ のコメント記述に便利な `formatoptions` 設定](/p/i9y5wsd/)
* [Vim のインサートモードを抜けるときに自動で IME をオフにする (macOS, Karabiner-Elements)](/p/bbkb63f/)


Vim プログラミング（マクロ）／特殊用途
----
* [カーソル位置の文字の文字コードを表示する (`:ascii`)](/p/6bpmxey/)
* [カーソル位置の単語を取得する (`<cword>`)](/p/f448waf/)
* [（参考サイト）Learn Vimscript the Hard Way](http://learnvimscriptthehardway.stevelosh.com/)


