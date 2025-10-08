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
### クラスの基本
* [クラスを定義する／コンストラクタを定義する (`class`)](/p/4qqytis/)
* [継承可能なクラスを作成する (`open`, `abstract`, `override`)](/p/r4wcjss/)
* [シールクラスで継承可能なクラスを制限する (sealed class)](/p/d8kkspv/)
* [クラスにプロパティのアクセサメソッドを定義する (`set`, `get`)](/p/g2bj9zs/)
* [データクラスを定義する (data class)](/p/fc4unhr/)
* [by を使ったメソッド呼び出しの委譲 (class delegation)](/p/kh358jg/)
* [既存のクラスに関数やプロパティを追加する（拡張関数）](/p/du53m3v/)

### 演算子 (Operator) の定義
* [算術演算子を定義してオブジェクトに `+` や `+=` を適用できるようにする](/p/wf7yjbm/)
* [比較演算子を定義してオブジェクト同士の比較やソートを行えるようにする (`equals`, `compareTo`)](/p/ojwakt8/)
* [インデックス演算子を定義して配列のようにアクセス可能なクラスを作る (index operator)](/p/rtajezj/)
* in 演算子を定義して要素の包含チェックを行えるようにする (`contains`) <!-- (operator/operator-contains.html) -->
* iterator を定義して for-in ループで要素を処理できるようにする (iterator) <!-- (operator/operator-iterator.html) -->
* 範囲演算子を定義して .. で指定した範囲の要素を取得できるようにする (`rangeTo`) <!-- (operator/operator-range.html) -->

### object キーワード
* [オブジェクト宣言でシングルトンを作成する (object declaration)](/p/wc8f9y8/)
* [オブジェクト式で無名オブジェクトを生成する (object expression)](/p/8yzdom9/)
* [コンパニオンオブジェクトでクラスに静的メソッドを追加する (companion object)](/p/n2jphu2/)

### ジェネリクス (Generics)【応用】
* [ジェネリクスの基本](/p/yepnwyf/)
* [`reified` でジェネリクスの型情報を維持する](/p/u32ykpo/)
* [ジェネリクスの共変 (covariant) と不変 (invariant) について理解する](/p/irsz3gs/)

### その他 <!-- misc -->
* [コンパニオンオブジェクトとクラス内オブジェクト宣言の違い](/p/rej4be5/)
* [クラス名を文字列で取得する (`class.java.simpleName`)](/p/omkgowq/)
* [クラス内の `MutableList` を不変 (immutable) な `List` にして公開する](/p/9fwrpnu/)
* [インラインクラスでプリミティブ型の型安全性を確保する (inline class)](/p/khn4o5z/)


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


パッケージ
----
* [Kotlin のパッケージの扱い方を理解する](/p/u8mz82t/)
* [パッケージのトップレベルに関数、プロパティ、定数を定義する](/p/xgn46vo/)


配列／コレクション
----
### 共通
* [コレクションの基本的な使い方 (`List`, `Set`, `Map`)](/p/by55kee/)
* [immutable なコレクションと mutable なコレクション (`List`, `Set`, `Map`)](/p/9557oxs/)
* [immutable なコレクションを mutable に変換する (`toMutableList`, `toMutableSet`, `toMutableMap`)](/p/r5f583b/)
* [配列とリストの生成方法まとめ（連番からなる配列やリストを作成する）](/p/fo685z4/)
* [プリミティブ型の配列には `IntArray` や `LongArray` などの専用クラスを使うことを検討する](/p/az53za7/)
* [プリミティブ型配列 (`IntArray`) の内容を見やすく出力する (`contentToString`)](/p/4j2zidk/)
* [配列やコレクションの要素をループ処理する (`for-in`, `forEach`, `withIndex`)](/p/9byrmzs/)
* [コレクションの要素をもとに別のリストを作成する (`map`)](/p/v5wjmyk/)
* [コレクションから条件に一致する要素のみを取り出す (`filter`, `filterNot`, `filterKeys`, `filterValues`)](/p/y2z9krm/)
* [配列やリストから null 以外の要素のみを抽出する (`filterNotNull`, `mapNotNull`)](/p/wz5cnqq/)
* [ある値がコレクションに含まれているか調べる (`in`)](/p/4ycigqq/)
* [コレクションの最初・最後の要素を取得する (`first`, `last`)](/p/swvgt89/)
* [コレクション内の最小・最大の値を見つける (`min`, `max`, `minBy`, `maxBy`)](/p/kqpvcpj/)
* [配列やリストの先頭・末尾の n 要素を取り出す・削除する (`take`, `drop`)](/p/mmiwzqj/)

### マップ (Map)
* [マップからキーのリスト、値のリストを作成する (`keys`, `values`)](/p/6r9c6vu/)
* [マップのキー／値をまとめて変更する (`mapKeys`, `mapValues`)](/p/633znjc/)
* [マップをソートしてループ処理する (`toSortedMap`)](/p/8msfk3j/)
* [マップの値を初めて取得しようとしたときに初期化する（`Map` の遅延初期化）(`getOrPut`)](/p/u4j3e9t/)

### セット (Set)
* [セットの集合演算を行う](/p/oc2j8u9/)


文字列／数値
----
* [文字列リテラルの中で変数や式を展開する（文字列テンプレート）](/p/n3mn3og/)
* [文字列と数値を変換する (`toIntOrNull`, `toInt`)](/p/cdxns9p/)
* [文字列をデリミタ文字で分割する (`split`)](/p/mngmt9c/)


日時（日付／時刻） <!-- misc -->
----

* [Kotlin で日時（日付／時刻）を扱う方法いろいろ](/p/9c9d2t7/)


その他 <!-- misc -->
----
* [Kotlin のコードにドキュメンテーションコメントを記述する (KDoc)](/p/wjgs3fp/)
* [Kotlin で読みやすいコードを書く方法（可読性の高い Kotlin コードとは）](/p/otwhjds/)
* [ある処理にかかった時間を計測する (`measureTimeMillis`/`Micros`)](/p/zmr4tpu/)
* [演算子の前後で改行したいときは必ず後ろで改行する](/p/bmwa9t8/)

