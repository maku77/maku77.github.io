---
title: "sed/awk メモ"
layout: category-index
---

sed
----
* [sed の基本的な使い方](sed/basic.html)
* [sed で自分自身のファイルを書き換えるときの注意](sed/replace-itself.html)
* [sed でパターンに一致する行を抽出する](sed/extract-lines.html)
* [sed でパターンに一致する行を削除する](sed/delete-matched-lines.html)
* [sed でパターンに一致する文字列を抽出する](sed/extract-words.html)
* [sed でパターンに一致する文字列を削除する](sed/remove-words.html)
* [sed でパターンに一致する行が現れるまで削除する](sed/remove-until.html)
* [sed で指定した範囲の行を表示する](sed/print-specified-lines.html)
* [sed で指定した範囲の行を置換する](sed/replace-specified-lines.html)
* [sed で指定した範囲の行を削除する](sed/delete-specified-lines.html)
* [sed で大文字と小文字を区別せずに置換する](sed/ignore-case.html)
* [sed の置換パターンの中でシェルの変数を参照する](sed/shell-variables.html)
* [sed で文字単位の置換を行う](sed/replace-char-by-char.html)
* [sed で置換するときに後方参照を使用する](sed/backward-reference.html)

### サンプル
* [sed で空白行を削除する](sed/remove-empty-lines.html)
* [sed で行末のスペースを削除する](sed/remove-trailing-spaces.html)
* [sed で連続するスペースを１つにする](sed/shrink-spaces.html)
* [sed で行頭や行末に文字列を追加する](sed/head-and-end.html)
* [sed で C 言語のコメントを削除する](sed/remove-c-comment.html)
* [sed でパス文字列からディレクトリ名を抽出する](sed/remove-dir-path.html)

awk
----
* [awk の基本的な使い方](awk/basic.html)
* [awk でフィールドセパレータを変更する](awk/change-separator.html)
* [awk で任意のコマンド出力の N 番目のフィールドのみを抜き出す](awk/display-specified-field.html)
* [awk で指定したフィールドを削除する](awk/delete-specified-field.html)
* [awk でパターンに一致する行を削除する](awk/delete-matched-lines.html)
* [awk でテキストファイル内の単語数と行数をカウントする](awk/count-words.html)

その他のテキスト編集ツール
----

### tr

tr は translate や trunsliterate の略で、標準入力で渡された文字列内の文字を、別の文字に置換する簡単なテキスト処理ツールです。

* [tr コマンドで空白行を削除する](tr/remove-empty-lines.html)

### cut

cut コマンドを使用すると、任意のデリミタで区切られた文字列（フィールド）を位置指定で抽出することができます。

* [cut コマンドでデリミタ区切りの文字列を扱う](cut/extract-field.html)
* [cut コマンドで行頭の数文字をカットする](cut/cut-line-head.html)

