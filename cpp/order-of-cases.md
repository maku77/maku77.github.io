---
title: switch の中の case の順序について
date: "2005-07-27"
---

switch ステートメント内のラベルの順番について
----

C の仕様では `case` ラベルの順番は任意なので、次のように `default` ラベルが最初に来ても大丈夫です。

```cpp
int main() {
    int n = 1;

    switch (n) {
    case default:
        std::cout << "default";
        break;
    case 1:
        std::cout << "one";
        break;
    }
}
```

#### 実行結果：

```
one
```

これは C/C++ の `switch` が `int` あるいは `enum` 型にしか使えないという制約があり、与えられた値に対してジャンプ先が一意に決まるからできることです。

Ruby などのスクリプト言語の場合は、`switch` に正規表現を指定できたり、数値の範囲を指定できたりするものがあるので、ラベルの順番が実行シーケンスに関係「ある」ということになります。
スクリプトだけに、単純に上から順に評価しているだけともいえます。

例えば、Ruby の `case` ステートメントの場合、

```ruby
$n = 4
case $n
    when 1 .. 5
        p "1-5"
    when 3 .. 8
        p "3-8"
end
```

こうすると `1-5` と表示されますが、`when` の位置を入れ替えて

```ruby
$n = 4
case $n
    when 3 .. 8
        p "3-8"
    when 1 .. 5
        p "1-5"
end
```

このようにすると `3-8` と表示されます。
当然次のように `else` を一番最初に持ってきたりすると、エラーになります。

```ruby
$n = 4
case $n
    else
        p "Else"
    when 1 .. 5
        p "1-5"
    when 3 .. 8
        p "3-8"
end
```


switch ステートメント内で case ラベルに同じ値のものがあってはいけない
----

C/C++ では `switch` ステートメント内のラベルに同じ値が出てきてはいけません。

```cpp
enum MyEnum {
    A = 0,
    B = 1,
    C = 0
};

int main() {
    MyEnum e = C;

    switch (e) {
    case A:
        break;
    case B:
        break;
    case C:       // ← A と同じ値だから NG!
        break;
    }
}
```

Ruby の場合は次のようなコードも普通に通ります。

```ruby
$age = 1

case $age
    when 1
        p "one A"
    when 1
        p "one B"
end
```

