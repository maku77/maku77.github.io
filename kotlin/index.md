---
title: "Kotlin"
layout: category-index
---

はじめに
----
* [Kotlin とは？ Kotlin をインストールする](basic/install.html)


変数／関数
----

* [変数を定義する (val, var)](basic/var.html)
* [基本的な型の一覧](basic/types.html)
* [lateinit による変数の初期化](basic/lateinit.html)
    * [lateinit 変数が初期化されているかどうかを調べる (isInitialized)](basic/lateinit-isinitialized.html)
* [関数を定義する (fun)](basic/fun.html)
* [可変長引数をとる関数を定義する (vararg)](basic/vararg.html)
* [infix 記法で関数呼び出し時の記述を簡略化する (to)](basic/infix.html)
* [分解宣言 (destructuring declarations) による Pair 要素や Triple 要素の分解](basic/dest-decl.html)


クラス
----
* [クラスを定義する／コンストラクタを定義する (class)](basic/class.html)
* [継承可能なクラスを作成する (open, abstract, override)](basic/extend.html)
* [シールクラスで継承可能なクラスを制限する (sealed class)](basic/sealed-class.html)
* [クラスにプロパティのアクセサメソッドを定義する (set, get)](basic/setter-getter.html)
* [データクラスを定義する (data class)](basic/data-class.html)
* [by を使ったメソッド呼び出しの委譲](basic/class-delegation.html)
* [既存のクラスに関数やプロパティを追加する（拡張関数）](basic/ext-func.html)

### 演算子
* [数値演算用の演算子（+ や - など）をオーバーロードする (operator)](basic/overload-operator.html)
* [インデックス演算子を定義して配列のようにアクセス可能なクラスを作る (index operator)](collection/index-operator.html)
* in 演算子を定義して要素の包含チェックを行えるようにする (contains)
* iterator を定義して for-in ループで要素を処理できるようにする

### 応用
* [コンパニオンオブジェクトとクラス内オブジェクト宣言の違い](misc/companion-vs-declaration.html)
* [クラス名を文字列で取得する](misc/class-name.html)
* [クラス内の MutableList を immutable な List にして公開する](misc/return-as-immutable.html)


object キーワード
----
* [オブジェクト宣言でシングルトンを作成する (object declaration)](basic/object-declarations.html)
* [オブジェクト式で無名オブジェクトを生成する (object expression)](basic/object-expression.html)
* [コンパニオンオブジェクトでクラスに静的メソッドを追加する (companion object)](basic/companion-object.html)


型（タイプ）
----
* [インタフェースを定義する (interface)](basic/interface.html)
* [列挙型を定義する (enum)](basic/enum.html)
* [スマートキャストでキャストを自動化する](basic/smart-cast.html)
* [safe call (?.) や elvis operator (?:)、let で null をうまく扱う](basic/handle-null.html)
* [あるインスタンスがどのクラスのオブジェクトなのか調べる (javaClass)](basic/java-class.html)


制御構文
----
* [if による分岐処理と if 式](basic/if.html)
* [when 式による条件分岐](basic/when.html)
* [for ループと while ループ（そして forEach）](basic/loop.html)
* [例外処理を記述する (try, catch, finally)](basic/exception.html)


ラムダ式 (lambda expression)
----
* [ラムダ式の基本 (lambda expression)](basic/lambda.html)
* [メンバ参照、結合メンバ参照を理解する (member reference)](basic/member-reference.html)
* レシーバー付きラムダ (with)


ジェネリクス (Generics)
----
* [ジェネリクス: ジェネリクスの基本](generics/basic.html)
* [ジェネリクス: reified でジェネリクスの型情報を維持する](generics/reified.html)
* [ジェネリクス: 共変 (covariant) と不変 (invariant) について理解する](generics/variant.html)


文字列／数値
----
* [文字列リテラルの中で変数や式を展開する（文字列テンプレート）](numstr/string-template.html)
* [文字列と数値を変換する (toIntOrNull, toInt)](numstr/convert.html)
* [文字列をデリミタ文字で分割する (split)](numstr/split.html)


パッケージ
----
* [Kotlin のパッケージの考え方を理解する](package/basic.html)
* [パッケージのトップレベルに関数、プロパティ、定数を定義する](package/top-level.html)


配列／コレクション
----
### 共通
* [コレクションの基本的な使い方 (List, Set, Map)](collection/basic.html)
* [immutable なコレクションと mutable なコレクション (List, Set, Map)](collection/immutable-and-mutable.html)
* [immutable なコレクションを mutable に変換する (toMutableList, toMutableSet, toMutableMap)](collection/to-mutable.html)
* [配列やコレクションの要素をループ処理する](collection/loop-collection.html)
* [コレクションの要素をもとに別のコレクションを作成する (map)](collection/map.html)
* [コレクションから条件に一致する要素のみを取り出す (filter, filterNot, filterKeys, filterValues)](collection/filter.html)
* [配列やリストから null 以外の要素のみを抽出する (filterNotNull, mapNotNull)](collection/filter-not-null.html)
* [ある値がコレクションに含まれているか調べる (in)](collection/in.html)
* [コレクションの最初・最後の要素を取得する (first, last)](collection/first-last.html)
* [コレクション内の最小・最大の値を見つける (min, max, minBy, maxBy)](collection/min-max.html)
* [配列やリストの先頭・末尾の n 要素を取り出す・削除する (take, drop)](collection/take-drop.html)

### マップ (Map)
* [マップからキーのリスト、値のリストを作成する (keys, values)](collection/keys-values.html)
* [マップのキー／値をまとめて変更する (mapKeys, mapValues)](collection/map-keys.html)
* [マップをソートしてループ処理する (toSortedMap)](collection/sorted-map.html)
* [マップの値を始めて取得しようとしたときに初期化する（Map の遅延初期化）(getOrPut)](collection/map-get-or-put.html)


その他
----
* [ドキュメンテーションコメントを記述する (KDoc)](misc/kdoc.html)
* [Kotlin で読みやすいコードを書く方法（可読性の高い Kotlin コードとは）](misc/readability.html)
* [ある処理にかかった時間を計測する (measureTimeMillis/Micros)](misc/measure-time.html)
* [演算子の前後で改行したいときは必ず後ろで改行する](misc/break-after-operator.html)

