---
title: "Kotlin"
url: "/kotlin/"

categoryName: "まくまく Kotlin ノート"
categoryUrl: "/kotlin/"
categoryIcon: _logo-kotlin.svg
---

はじめに／環境
----
* [Kotlin とは？ Kotlin をインストールする](/p/5zibnsn/)
* [VS Code で Kotlin の開発環境を構築する](/p/2hbwijq/)


変数／関数
----
* [変数を定義する (val, var)](/p/qzkfnsq/)
* [基本的な型の一覧](/p/hqup7mp/)
* [関数を定義する (fun)](/p/ttacror/)
* [可変長引数をとる関数を定義する (vararg)](/p/c2f65zt/)
* [lateinit による変数の初期化](/p/ymzvh9q/)
  * [lateinit 変数が初期化されているかどうかを調べる (isInitialized)](/p/qpc53iy/)
* [分解宣言 (destructuring declarations) による `Pair` 要素や `Triple` 要素の分解](/p/fr4jjf6/)
* [infix 記法で関数呼び出し時の記述を簡略化する (to)](/p/6qamkcy/)
* [インライン関数の特徴を理解する (inline fun)](/p/8ieeha7/)


制御構文
----
* [`if` による分岐処理と `if` 式](/p/4t7x4k4/)
* [`when` 式による条件分岐](/p/v2ezcum/)
* [`for` ループと `while` ループ（そして `forEach`）](/p/7gfaxun/)
* [例外処理を記述する (`try`, `catch`, `finally`)](/p/bvu8qmi/)


クラス
----
* [クラスを定義する／コンストラクタを定義する (`class`)](/p/4qqytis/)
* [継承可能なクラスを作成する (`open`, `abstract`, `override`)](/p/r4wcjss/)
* [シールクラスで継承可能なクラスを制限する (sealed class)](/p/d8kkspv/)
* [クラスにプロパティのアクセサメソッドを定義する (`set`, `get`)](/p/g2bj9zs/)
* [データクラスを定義する (data class)](/p/fc4unhr/)
* [by を使ったメソッド呼び出しの委譲 (class delegation)](/p/kh358jg/)
* [既存のクラスに関数やプロパティを追加する（拡張関数）](/p/du53m3v/)

### 演算子 (Operator) の定義
* [算術演算子を定義してオブジェクトに `+` や `+=` を適用できるようにする](operator/operator-arithmetic.html)
* [比較演算子を定義してオブジェクト同士の比較やソートを行えるようにする (`equals`, `compareTo`)](operator/operator-compare.html)
* [インデックス演算子を定義して配列のようにアクセス可能なクラスを作る (index operator)](operator/operator-index.html)
* in 演算子を定義して要素の包含チェックを行えるようにする (`contains`) <!-- (operator/operator-contains.html) -->
* iterator を定義して for-in ループで要素を処理できるようにする (iterator) <!-- (operator/operator-iterator.html) -->
* 範囲演算子を定義して .. で指定した範囲の要素を取得できるようにする (`rangeTo`) <!-- (operator/operator-range.html) -->

### 応用
* [コンパニオンオブジェクトとクラス内オブジェクト宣言の違い](misc/companion-vs-declaration.html)
* [クラス名を文字列で取得する](misc/class-name.html)
* [クラス内の `MutableList`を immutable な `List` にして公開する](misc/return-as-immutable.html)
* [インラインクラスでプリミティブ型の型安全性を確保する (inline class)](misc/inline-class.html)


object キーワード
----
* [オブジェクト宣言でシングルトンを作成する (object declaration)](/p/wc8f9y8/)
* [オブジェクト式で無名オブジェクトを生成する (object expression)](/p/8yzdom9/)
* [コンパニオンオブジェクトでクラスに静的メソッドを追加する (companion object)](/p/n2jphu2/)


型（タイプ）
----
* [インタフェースを定義する (`interface`)](/p/ep23xid/)
* [列挙型を定義する (`enum`)](/p/st6xako/)
  * [列挙型 (enum) の値をインデックスで取得する](/p/nsvub5w/)
  * [列挙型 (enum) の値をループ処理する (`values`)](/p/q3y3jhr/)
* [スマートキャストでキャストを自動化する](/p/rk5dgjh/)
* [safe call (`?.`) や elvis operator (`?:`)、`let` で `null` をうまく扱う](/p/qrrnw9b/)
* [あるインスタンスがどのクラスのオブジェクトなのか調べる (`javaClass`)](/p/tc9c9z8/)


ラムダ式 (lambda expression)
----
* [ラムダ式の基本 (lambda expression)](/p/rdoseay/)
* [メンバ参照、結合メンバ参照を理解する (member reference)](/p/r2gqqnt/)
* レシーバー付きラムダ (with)


ジェネリクス (Generics)
----
* [ジェネリクス: ジェネリクスの基本](generics/basic.html)
* [ジェネリクス: `reified` でジェネリクスの型情報を維持する](/p/u32ykpo/)
* [ジェネリクス: 共変 (covariant) と不変 (invariant) について理解する](generics/variant.html)


文字列／数値
----
* [文字列リテラルの中で変数や式を展開する（文字列テンプレート）](numstr/string-template.html)
* [文字列と数値を変換する (`toIntOrNull`, `toInt`)](numstr/convert.html)
* [文字列をデリミタ文字で分割する (`split`)](numstr/split.html)


配列／コレクション
----
### 共通
* [コレクションの基本的な使い方 (`List`, `Set`, `Map`)](collection/basic.html)
* [immutable なコレクションと mutable なコレクション (`List`, `Set`, `Map`)](collection/immutable-and-mutable.html)
* [immutable なコレクションを mutable に変換する (toMutableList, toMutableSet, toMutableMap)](collection/to-mutable.html)
* [配列とリストの生成方法まとめ（連番からなる配列やリストを作成する）](collection/create-sequence.html)
* [プリミティブ型の配列には IntArray や LongArray などの専用クラスを使うことを検討する](collection/primitive-array.html)
* [プリミティブ型配列 (IntArray) の内容を見やすく出力する (contentToString)](collection/content-to-string.html)
* [配列やコレクションの要素をループ処理する (for-in, forEach, withIndex)](collection/loop-collection.html)
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

### セット (Set)
* [セットの集合演算を行う](collection/set-operation.html)

日時（日付／時刻）
----

* [Kotlin で日時（日付／時刻）を扱う方法いろいろ](misc/time.html)

パッケージ
----
* [Kotlin のパッケージの考え方を理解する](package/basic.html)
* [パッケージのトップレベルに関数、プロパティ、定数を定義する](/p/xgn46vo/)


その他
----
* [ドキュメンテーションコメントを記述する (KDoc)](misc/kdoc.html)
* [Kotlin で読みやすいコードを書く方法（可読性の高い Kotlin コードとは）](misc/readability.html)
* [ある処理にかかった時間を計測する (measureTimeMillis/Micros)](misc/measure-time.html)
* [演算子の前後で改行したいときは必ず後ろで改行する](misc/break-after-operator.html)

