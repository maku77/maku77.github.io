---
title: "マップ変数を使用する"
date: "2018-12-18"
---

マップ変数を作成する
----

Sass においてマップはリストの特殊バージョンで、リストの各要素がキー＆バリューの形 (`key1:value1`) になったものです。
マップを定義するには、キー＆バリューをカンマで並べ、全体を括弧 `()` で囲みます。
リストを定義するときは、全体の括弧を省略できるケースがありますが、マップの場合は必ず全体を括弧で囲む必要があります。

#### マップ変数を定義する

~~~ scss
$map: (key1: value1, key2: value2, key3: value3);
~~~

空のマップを作成したいときは、空のリストを作成するときと同様に `()` を使用します。
空のマップを作成しておいて、動的に要素を追加していくという使い方ができます。

#### 空のマップ変数を定義する（リストでもある）

~~~ scss
$map: ();
~~~


マップの要素をループで処理する (@each)
----

マップの要素もリストの要素と同様に `@each` ディレクティブを使用してループ処理することができます。
あるルール定義の中で、マップの要素を使用してスタイル定義を出力するために使用します。

#### 入力 (SCSS)

~~~ scss
$headers: (h1: 1.6rem, h2: 1.4rem, h3: 1.3rem, h4: 1.2rem);

@each $tag, $size in $headers {
  #{$tag} {
    font-size: $size;
  }
}
~~~

#### 出力 (CSS)

~~~ css
h1 {
  font-size: 1.6rem; }

h2 {
  font-size: 1.4rem; }

h3 {
  font-size: 1.3rem; }

h4 {
  font-size: 1.2rem; }
~~~


マップ用の関数一覧
----

リスト系の関数と同様に、マップの内容を編集する関数は、新しいマップを作成して戻り値として返します。
パラメータとして渡したマップの内容は不変であることに注意してください。

### length($map): マップの要素数を取得する

マップもリストも `length` 関数を使って要素数を調べることができます。

~~~
length(())  //=> 0
length((width: 10px, height: 20px))  //=> 2
length((key1: null, null: 100))  //=> 2
~~~

### map-get($map, $key): 指定したキーに対応する値を取得する

~~~
map-get(("foo": 1, "bar": 2), "foo")  //=> 1
map-get(("foo": 1, "bar": 2), "bar")  //=> 2
map-get(("foo": 1, "bar": 2), "baz")  //=> null
~~~

存在しないキーを指定した場合は、`null` を返します。

### map-merge($map1, $map2): 2つのマップをマージして新しいマップを作成する

~~~
map-merge(("foo": 1), ("bar": 2))  // => ("foo": 1, "bar": 2)
map-merge(("foo": 1, "bar": 2), ("bar": 3))  //=> ("foo": 1, "bar": 3)
~~~

体裁としては2つのマップをマージする関数ですが、**マップに1つのエントリ（キー＆バリュー）を追加するときも、この `map-merge` 関数を使用します**（要素数が1のマップをマージすればよい）。
2つのマップに同じキーのエントリがある場合は、`$map2` の値が優先的に使用されます。

### map-remove($map, $keys...): マップから指定したキーを削除したマップを作成する

~~~
map-remove(("foo": 1, "bar": 2), "bar")  //=> ("foo": 1)
map-remove(("foo": 1, "bar": 2, "baz": 3), "bar", "baz")  //=> ("foo": 1)
map-remove(("foo": 1, "bar": 2), "baz")  //=> ("foo": 1, "bar": 2)
~~~

最後のパラメーター `$keys` は可変長引数になっているので、複数のキーをまとめて削除することができます。
存在しないキーを指定した場合は何も起こりません。

### map-keys($map): マップのキーをリストにして返す

~~~
map-keys(("foo": 1, "bar": 2))  //=> "foo", "bar"
map-keys(())  //=> ()
~~~

### map-values($map): マップの値をリストにして返す

~~~
map-values(("foo": 1, "bar": 2))  //=> 1, 2
map-values(("foo": 1, "bar": 2, "baz": 1))  //=> 1, 2, 1
~~~

複数のエントリが同じ値を持っている場合、返されるリスト内には重複した値が格納されます。

### map-has-key($map, $key): マップが指定されたキーを含んでいるかを調べる

~~~
map-has-key(("foo": 1, "bar": 2), "foo")  //=> true
map-has-key(("foo": 1, "bar": 2), "baz")  //=> false
map-has-key((null: null), null)  //=> true
map-has-key((), null)  //=> false
~~~

### keywords($args): 可変長引数として渡された引数の内容を取得する

~~~ scss
@mixin foo($args...) {
  @debug keywords($args);  //=> (arg1: val, arg2: val)
}

@include foo($arg1: val, $arg2: val);
~~~

`keywords` 関数を使用すると、Mixin や関数に渡された可変長引数の内容を、マップ形式にして取得することができます。


マップの要素をダンプする (inspect, @debug)
----

Sass の inspect 関数を使用すると、マップ変数の内容を文字列形式で取得することができます。
開発時に一時的にマップ要素の内容を確認したい場合に便利です。

#### 入力 (SCSS)

~~~ scss
$map: (key1: value1, key2: value2, key3: value3);

main {
  dummy: inspect($map);
}
~~~

#### 出力 (CSS)

~~~ css
main {
  dummy: (key1: value1, key2: value2, key3: value3); }
~~~

もっとも、コンソール上でデバッグしているのであれば、`@debug` ディレクティブを使用して内容を出力してしまった方が早いです。

#### sample.scss

~~~ scss
$map: (key1: value1, key2: value2, key3: value3);
@debug $map;
~~~

#### 実行結果

~~~
$ sass sample.scss
sample.scss:2 DEBUG: (key1: value1, key2: value2, key3: value3)
~~~

