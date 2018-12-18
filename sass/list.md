---
title: "リスト変数を使用する"
date: "2018-12-18"
description: "Sass には、複数の値をまとめて管理するためのデータタイプとして list 型が用意されています。"
---

リスト型の値の表現方法
----

リスト型の値を定義するときは、スペース区切り、あるいはカンマ区切りで複数の値を列挙します。
リスト型の値は、例えば、次のようなプロパティの値を表現するために使用されます。

~~~ css
.sample {
  margin: 10px 15px 0 0;
  font-face: Helvetica, Arial, sans-serif;
}
~~~

上記の値部分をリスト型の変数に入れて表現すると、下記のように記述できます。

~~~ scss
$my-margin: 10px 15px 0 0;
$my-font: Helvetica, Arial, sans-serif;

.sample {
  margin: $my-margin;
  font-face: $my-font;
}
~~~

### リストのリスト

リストの要素にリストを含めることもできます。
下記の例では、`1px 2px` と `3px 4px` の 2 つのリストを含むリスト変数を定義しています。

~~~ scss
$my-list1: 1px 2px, 3px 4px;
$my-list2: (1px 2px) (3px 4px);
~~~

### 空リストと要素が1つだけのリスト

空のリストを定義するときは、`()` という表現方法を使用します。

~~~ scss
$my-list: ();
~~~

<div class="note">
Sass では、<code>()</code> は空のリストであり、空のマップでもあります。マップは実質的には、キー＆バリューのペア値を格納した「リスト」として扱われるためです。
</div>

要素が 1 つだけのリストを表現するには、次のように、要素の後ろにカンマ (`,`) を記述します。

~~~ scss
$my-list1: ("aaa",);  // 要素として "aaa" を 1 つだけ含むリスト
$my-list2: (1 2 3,);  // 要素として 1 2 3 というリストを 1 つだけ含むリスト
~~~

上記のような簡単な例では括弧 (`()`) は省略できますが、リストであることを明確にするためにも括弧は付けておいた方がよいでしょう。


リストをループ処理する (@each)
----

### 単純なリストのループ処理

リストの要素をループ処理するには、**`@each`** ディレクティブを使用します。
あるルールの定義内で、リストやマップの内容をループ処理しながらスタイルのセットを出力するために使用することができます。
`@each` ディレクティブの構文は下記の通りです。

~~~
@each $var in <リストorマップ> {
  スタイル定義
}
~~~

リスト内の要素を 1 つずつ `$var` 変数で参照しながらループ処理することができます。
イテレータとして使用する変数の名前は `$var` でなくても構いません。

次の例では、`$fruits` リスト変数に格納された要素をループ処理してスタイル定義を出力しています。

#### 入力 (SCSS)

~~~ scss
$fruits: "apple", "banana", "orange";

@each $x in $fruits {
  .#{$x}-icon {
    background-image: url('/assets/images/#{$x}.png');
  }
}
~~~

#### 出力 (CSS)

~~~ css
.apple-icon {
  background-image: url("/assets/images/apple.png"); }

.banana-icon {
  background-image: url("/assets/images/banana.png"); }

.orange-icon {
  background-image: url("/assets/images/orange.png"); }
~~~

### リストのリストのループ処理

`@each` ディレクティブを使用して、リストのリストをループ処理することもできます。
下記の例では、リストのリストを格納した `$fruits` 変数の値をループ処理しています。

#### 入力 (SCSS)

~~~ scss
$fruits: ("apple", red), ("banana", yellow), ("orange", orange);

@each $x in $fruits {
  $name: nth($x, 1);   // 1番目の要素を取り出す（フルーツ名）
  $color: nth($x, 2);  // 2番目の要素を取り出す（色）
  .#{$name}-icon {
    background-image: url('/assets/images/#{$name}.png');
    border: 2px solid $color;
  }
}
~~~

`@each` によるループ処理では、`$fruits` 内の各リスト要素が順番に取り出されるため、さらに **`nth`** 関数を使って、それらの 1 番目の要素、2 番目の要素を取り出しています。

#### 出力 (CSS)

~~~ css
.apple-icon {
  background-image: url("/assets/images/apple.png");
  border: 2px solid red; }

.banana-icon {
  background-image: url("/assets/images/banana.png");
  border: 2px solid yellow; }

.orange-icon {
  background-image: url("/assets/images/orange.png");
  border: 2px solid orange; }
~~~

このように、`@each` ディレクティブと `nth` 関数を組み合わせてリストのリストをループ処理してもよいのですが、**多重代入 (Multiple assignment)** の仕組みを使用すると、より簡潔にリストのリストをループ処理できます。

#### 入力 (SCSS) ─ 多重代入を使用する方法

~~~ scss
$fruits: ("apple", red), ("banana", yellow), ("orange", orange);

@each $name, $color in $fruits {
  .#{$name}-icon {
    background-image: url('/assets/images/#{$name}.png');
    border: 2px solid $color;
  }
}
~~~

上記のように、`@each` の後ろに複数の変数を記述すると、2階層目の要素がそれらの変数に展開されながらループ処理されます。
`nth` 関数で個々の要素を取り出す必要がなくなるので、簡潔かつ分かりやすいコードになります。


リスト用の関数一覧
----

Sass のリストを操作するための関数として、下記のような組み込み関数が定義されています。
Sass のリストは immutable （不変）であるため、パラメータとして渡したリストの内容は変更されることはなく、新しいリストが生成されて戻り値として返されることに注意してください。

### length($list): リストの要素数を取得する

~~~
length(10px)  //=> 1
length(10px 20px 30px)  //=> 3
length((width: 10px, height: 20px))  //=> 2
length((1 2 3,))  //=> 1
~~~

### nth($list, $n): リストの指定した位置の要素を取得する

~~~
nth(10px 20px 30px, 1)  //=> 10px
nth(10px 20px 30px, -1)  //=> 30px
nth((Helvetica, Arial, sans-serif), 3)  //=> sans-serif
nth((width: 10px, length: 20px), 2)  //=> length, 20px
~~~

Sass のインデックスは、0 始まりではなく 1 始まりであることに注意してください。
インデックスとして負の値を指定すると、リストの末尾からの位置を示すことができます。

### set-nth($list, $n, $value): 指定した位置の要素を置換したリストを作成する

~~~
$list: 1, 2, 3, 4, 5;
$new-list: set-nth($list, 3, "AAA");  //=> 1, 2, "AAA", 4, 5
~~~

元の `$list` 変数の値は変更されず、新しいリストを生成して返すことに注意してください。
つまり、置換後のリストを扱いたい場合は、上記のように `set-nth` 関数の戻り値を変数で受け取ったり、そのままプロパティ値として出力したりして使用する必要があります。
存在しないインデックスを指定すると、SyntaxError が発生します。

### join($list1, $list2, [$separator, $bracketed]): 2つのリストを結合したリストを作成する

~~~
join(10px 20px, 30px 40px)  //=> 10px 20px 30px 40px
join((blue, red), (#abc, #def))  //=> blue, red, #abc, #def
join(10px, 20px)  //=> 10px 20px
join(10px, 20px, comma)  //=> 10px, 20px
join((blue, red), (#abc, #def), space)  //=> blue red #abc #def
join([10px], 20px)  //=> [10px 20px]
~~~

`append` や `set-nth` 関数と同様に、`join` 関数は新しいリストを生成して戻り値として返すことに注意してください（`$list1` と `$list2` の内容は変化しません）。
生成されるリストの各要素は、`$separator` で指定されたセパレータ (`space` or `comma`) で区切られます。
`$separator` パラメータが省略された場合は、1つ目のリストに使用されているセパレータ（スペース or カンマ）がセパレータとして使用されます。
`$bracketed` パラメータが省略された場合は、1つ目のリストがブラケット (`[]`) で囲まれたリストである場合に、ブラケットリストとして返します。

### append($list, $val, [$separator]): リストの末尾に1つの要素を追加したリストを作成する

~~~
append(10px 20px, 30px)  //=> 10px 20px 30px
append((blue, red), green)  //=> blue, red, green
append(10px 20px, 30px 40px)  //=> 10px 20px (30px 40px)
append((), 10px)  //=> 10px
append(10px, 20px)  //=> 10px 20px
append(10px, 20px, comma)  //=> 10px, 20px
append((blue, red), green, space)  //=> blue red green
~~~

`$separator` が省略された場合は、元のリスト (`$list`) のセパレータが使用されますが、元のリストの要素数が 1 だった場合は、スペースがセパレータとして使用されます。

### zip($lists...): 複数のリストを結合し、同じインデックスの要素をリスト化した要素を持つリストを作成する

~~~
zip(1px 1px 3px, solid dashed solid, red green blue)  //=> 1px solid red, 1px dashed green, 3px solid blue
zip((1px, 1px, 3px), (solid, dashed, solid), (red, green, blue))  //=> 同上
~~~

生成されたリストの各要素は、スペースで区切られます（セパレータとしてカンマを指定できません）。

### index($list, $value): 指定した値がリストのどの位置にあるか調べる

~~~
index(1px solid red, solid)  //=> 2
index(1px solid red, dashed)  //=> null
index((width: 10px, height: 20px), (height 20px))  //=> 2
~~~

先頭要素のインデックスは 0 ではなく 1 になることに注意してください。
指定した値が見つからない場合は `null` を返します。

### list-separator($list): リストのセパレータ文字を取得する

~~~
list-separator(1px 2px 3px)  //=> space
list-separator((1px, 2px, 3px))  //=> comma
list-separator(((1 2), (3 4)))  //=> comma
list-separator(((1, 2) (3, 4)))  //=> space
list-separator('foo')  //=> space
list-separator(())  //=> space
list-separator(null)  //=> space
~~~

要素数が 1 の場合と、空リストの場合は `space` を返します。

### is-bracketed($list): ブラケットで囲まれたリストか調べる

~~~
is-bracketed(1px 2px 3px)  //=> false
is-bracketed([1px, 2px, 3px])  //=> true
~~~

