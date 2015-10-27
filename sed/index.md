---
title: sed/awk メモ
layout: category-index
---

sed、awk やその他のテキストプロセッシングに関するノートです。


sed
====
* [sed の基本的な使い方](sed-basic.html)

awk
====

awk について [2007-12-17]
----
awk という名前は、作者の頭文字に由来しています (Aho, Weinberger, Kernighan)。
特定のフォーマットのテキストファイルを処理するのに適した言語です。
sed と同じくストリーム指向で、テキストファイルを 1 行ずつ読み、結果を標準出力へ書き出すという動作をします。

* [awk の基本的な使い方](awk-basic.html)


tr
====

tr について
----
tr は translate や trunsliterate の略で、標準入力で渡された文字列内の文字を、別の文字に置換する簡単なテキスト処理ツールです。

* [tr でテキストファイルの空行を削除する](remove-empty-lines.html)

