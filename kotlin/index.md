---
title: "Kotlin"
layout: category-index
---

基本
----

* [Kotlin とは？ Kotlin をインストールする](basic/install.html)

### 構文

* 変数／関数
    * [変数を定義する (val, var)](basic/var.html)
    * [基本的な型の一覧](basic/types.html)
    * lateinit による変数宣言
    * [関数を定義する (fun)](basic/fun.html)
* クラス
    * [クラスを定義する／コンストラクタを定義する (class)](basic/class.html)
    * [継承可能なクラスを作成する (open, abstract, override)](basic/extend.html)
    * [シールクラスで継承可能なクラスを制限する (sealed class)](basic/sealed-class.html)
    * [クラスにプロパティのアクセサメソッドを定義する (set, get)](basic/setter-getter.html)
    * [データクラスを定義する (data class)](basic/data-class.html)
    * [by を使ったメソッド呼び出しの委譲](basic/class-delegation.html)
* [インタフェースを定義する (interface)](basic/interface.html)
* [列挙型を定義する (enum)](basic/enum.html)
* [スマートキャストでキャストを自動化する](basic/smart-cast.html)
* [例外処理を記述する (try, catch, finally)](basic/exception.html)
* [あるインスタンスがどのクラスのオブジェクトなのか調べる (javaClass)](basic/java-class.html)
* [既存のクラスに関数やプロパティを追加する（拡張関数）](basic/ext-func.html)
* object キーワード
    * [オブジェクト宣言でシングルトンを作成する (object declaration)](basic/object-declarations.html)
    * [オブジェクト式で無名オブジェクトを生成する (object expression)](basic/object-expression.html)
    * [コンパニオンオブジェクトでクラスに静的メソッドを追加する (companion object)](basic/companion-object.html)
* ラムダ式 (lambda expression)
    * [ラムダ式の基本 (lambda expression)](basic/lambda.html)
    * メンバ参照 (member reference)
    * レシーバー付きラムダ (with)

### 文字列／数値
* [文字列リテラルの中で変数や式を展開する（文字列テンプレート）](numstr/string-template.html)
* [文字列と数値を変換する (toIntOrNull, toInt)](numstr/convert.html)
* [文字列をデリミタ文字で分割する (split)](numstr/split.html)

### 制御構文
* [if による分岐処理と if 式](basic/if.html)
* [when 式による条件分岐](basic/when.html)
* [for ループと while ループ](basic/loop.html)

### パッケージ
* [Kotlin のパッケージの考え方を理解する](package/basic.html)
* [パッケージのトップレベルに関数、プロパティ、定数を定義する](package/top-level.html)


配列／コレクション
----
* [immutable なコレクションと mutable なコレクション (List, Set, Map)](collection/immutable-and-mutable.html)
* List と MutableList の使い方
* Set と MutableSet の使い方
* Map と MutableMap の使い方
* [配列やコレクションの要素をループ処理する](collection/loop-collection.html)
* [配列やリストから null 以外の要素のみを抽出する (filterNotNull)](collection/filter-not-null.html)
* [ある値がコレクションに含まれているか調べる (in)](collection/in.html)
* [コレクションの最初・最後の要素を取得する (first, last)](collection/first-last.html)
* [コレクション内の最小・最大の値を見つける (min, max, minBy, maxBy)](collection/min-max.html)


その他
----
* [ドキュメンテーションコメントを記述する (KDoc)](misc/kdoc.html)
* [コンパニオンオブジェクトとクラス内オブジェクト宣言の違い](misc/companion-vs-declaration.html)
