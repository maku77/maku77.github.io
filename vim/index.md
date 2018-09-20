---
title: Vim メモ
layout: category-index
---

基本
----
* [ヘルプコマンドの使い方 (:help)](basic/help.html)
* [カーソル移動の方法いろいろ](basic/move-cursor.html)
* [C/C++ の変数／マクロの定義位置にジャンプする](basic/cpp-jump.html)
* [画面スクロールの方法いろいろ](basic/scroll.html)
* [文字の削除方法いろいろ](basic/delete.html)
* [文字の置換方法いろいろ (:substitute)](basic/substitute.html)
* [ウィンドウを分割する](basic/window.html)
* [アンドゥとリドゥ操作](basic/undo-and-redo.html)

### 検索
* [Vim 内で grep を実行して見つかったファイルへジャンプする](advanced/grep.html)
* [ファイル内の文字列を検索する](basic/search.html)
* [カーソル位置の単語を検索する](basic/search-current-word.html)
* [大文字と小文字を区別しないで検索する](advanced/ignorecase.html)
* [検索に関する設定](settings/search.html)

### コマンドライン
* [Vim を起動するときに行番号を指定してファイルを開く](file/command-line-options.html)
* [サイレントバッチモードで Ex スクリプトをファイルに適用する](file/silent-batch-mode.html)


ファイル
----

### ファイルを開く
* [ファイル名を指定してファイルを開く](file/open-specified-file.html)
* [カーソル位置の単語をファイル名としてファイルを開く (gf)](file/open-file-at-cursor.html)
* [編集中のファイルと拡張子だけが異なるファイルを開く（Hello.cpp を編集中に Hello.h を開く）](file/open-other-ext.htmL)
* [ディレクトリエクスプローラー (Explore) を使用してファイルを開く (:E, :Ve, :He)](file/explore.html)

### ファイルを保存する
* [ファイルへの保存コマンド :w、:x の基本](file/save.html)
* [指定した行範囲だけ別のファイルに保存する](file/save-lines.html)

### その他
* [別ファイルの内容やコマンドの実行結果をカーソル位置に挿入する (:read)](file/read.html)
* [ファイルのエンコーディング形式、改行コードを変更する (fenc, ff)](file/encoding.html)
* [スワップファイルからファイルを復旧する (:recover)](file/recover.html)


編集操作
----

* [大文字と小文字を変換する](edit/uppercase-lowercase.html)
* [補完機能を使用してテキストを入力する](edit/complete.html)
* [行を連結する](edit/join.html)
* [空行（改行だけの行）を削除する](edit/remove-empty-lines.html)
* [XML/HTML の編集に便利なコマンド](edit/xml-and-html.html)
* [複数行の行頭に同じテキストを追加する](edit/insert-to-head.html)
* [カーソル位置の数字をインクリメント／デクリメントする](edit/increment.html)
* [挿入モード（インサートモード）のまま実行できる操作の一覧](edit/insert-mode.html)

### 整形
* [インデント用のスペースを入力する（シフトコマンド）](edit/indent.html)
* [選択した範囲を自動インデントする](edit/re-indent.html)
* [すでに入力されているタブをスペースに変換する (:retab)](edit/retab.html)
* [テキストを中央寄せ／左寄せ／右寄せする](edit/adjust.html)

### カット＆ペースト
* [挿入モード、コマンドモードでの貼り付け](edit/paste-in-insert-mode.html)
* [最後にヤンクしたテキストを確実に貼り付ける](edit/paste-register-0.html)
* [切り取り＆貼り付け操作、レジスタの扱いについて理解する](edit/register.html)

### ソート
* [行をソートして重複行を削除する](edit/unique-lines.html)
* [行を逆順にソートする](edit/reverse-sort.html)


応用操作
----
* [Vim 上でディレクトリツリーを表示する (NERD Tree)](advanced/nerd-tree.html)
* [折りたたみ機能 (folding) を使用する](advanced/folding.html)
* [指定した行範囲を一時的に表示する](advanced/show-lines.html)
* [一時的にシェル（ターミナルやコマンドプロンプト）を起動する](advanced/shell.html)
* [キーボードマクロを記録して一連のキー入力を繰り返し実行する](advanced/macro.html)
* [プリンタで印刷する (hardcopy)](advanced/hardcopy.html)

インストール／環境
----
* [MacOSX に香り屋版の GVim をインストールする](install/kaoriya-gvim.html)
* [Vundle をインストールして Vim のプラグイン環境を作る](install/vundle.html)
* [Windows の右クリックから Vim を開けるようにする](install/windows-right-click.html)
* [Windows でファイルを開くときに既存の GVim 内のタブで開く](install/windows-open-tab.html)

Vim の設定
----

### キーマップ
* [ショートカットキーで設定ファイル (.vimrc) を開く](settings/open-vimrc-quickly.html)
* [ショートカットキーで日時を挿入する](insert-date.html)
* [ショートカットキーでタブを切り替える](keymap/tab.html)
* [map コマンドなどで指定できる特殊なキーの名前一覧](keymap/keycodes.html)

### ファイルに関する設定と操作
* [設定ファイル (.vimrc) を開く、リロードする](settings/reload-vimrc.html)
* [現在の環境で読み込まれる設定ファイルの一覧を確認する](settings/rc-files.html)
* [モードラインでファイルごとに書式を設定する](settings/modeline.html)
* [バックアップファイル／スワップファイル／アンドゥファイルの設定 (backup, swapfile, undofile)](settings/backup.html)

### autocmd（自動コマンド）による設定
* [autocmd で自動コマンドを登録する](settings/autocmd.html)
* [編集中のスクリプト（Ruby や Python など）をショートカットキーで実行する](settings/autocmd-exec.html)

### 見た目の設定
* [行番号を表示する (number)](settings/number.html)
* [構文強調（シンタックスハイライト）を有効にする (syntax)](settings/syntax.html)
* [カーソル下の行や列をハイライト表示する (cursorline, cursorcolumn)](settings/cursorline.html)
* [フォント設定ダイアログを開いてフォントを設定する (guifont)](settings/font-dialog.html)
* [タブ文字と行末のスペースを表示する (list, listchars)](settings/show-space.html)
* [全角スペースを表示する](settings/show-double-byte-space.html)
* [■や※などの記号が重なって表示される問題の解決 (ambiwidth)](settings/ambiwidth.html)
* [GVim 起動時のウィンドウの幅、高さを設定する (columns, lines)](settings/window-size.html)
* [ステータスラインの表示内容を設定する (statusline, laststatus)](settings/statusline.html)

### 入力に関する設定
* [タブ文字の設定 (tabstop, expandtab)](settings/tab.html)
* [インデント（シフトコマンド）の設定 (shiftwidth, shiftround)](settings/indent.html)
* [省略形を用いたテキスト入力を行えるようにする (abbrevaite)](settings/abbreviate.html)
* [スペルチェックを有効にする (spell)](settings/spell.html)
* [自動インデントモードを有効にする (autoindent, cindent, smartindent)](settings/auto-indent.html)
* [Java や C/C++ のコメント記述に便利な formatoptions の設定](settings/formatoptions.html)
* [ビジュアルモードで簡単にインデントを行えるようにする](settings/visual-indent.html)


Vim プログラミング（マクロ）／特殊用途
----
* [（外部サイト）Learn Vimscript the Hard Way](http://learnvimscriptthehardway.stevelosh.com/)
* [カーソル位置の単語を取得する (cword)](misc/cword.html)
* [カーソル位置の文字の文字コードを表示する (ascii)](misc/ascii.html)

