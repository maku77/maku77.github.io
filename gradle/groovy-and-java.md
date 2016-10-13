---
title: Groovy と Java の違い
created: 2015-07-07
---

Groovy は Java と異なり、下記のような特徴を持っています。

* 行末のセミコロンは省略できる
* クラス、コンストラクタ、メソッドはデフォルトで `public` となる
* `return` の記述は省略できる（最後に評価された式が戻り値となる）
* getter/setter メソッドが自動的に作られる（`obj.prop` のようにアクセスすれば `obj.getProp` や `setProp` が呼び出される）
* `==` によるオブジェクトの比較は、自動的に `equals` による比較となる（しかも `null` チェックの必要はない）
* `assert` は常に有効（Java の場合は `-ea or --enable-assertion` オプションの指定が必要）
* 変数の型や、メソッドの戻り値の型は `def` としておけば、自動で判別してくれる
* コレクション（List や Map）の扱いがシンプルで Python や Ruby に近い構文で記述できる

