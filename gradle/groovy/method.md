---
title: Groovy でメソッドを定義する
date: "2015-07-08"
---

下記のサンプルでは、2 つの変数を足し合わせる `add` メソッドを定義しています。

#### sample.groovy
```groovy
// メソッドの定義
def add(a, b) {
    a + b
}

// メソッドの呼び出し
def val = add(100, 200)
println val
```

Groovy では、他の多くのスクリプト言語と同様に、メソッド内で最後に評価された式の値がそのまま戻り値となるので、`return` キーワードを省略することができます。

また、パラメータの型や、戻り値の型も明示する必要はありません（最初に `def` とだけ書けばよい）。
もちろん、Java と同様に型を明示しておくこともできます。

```groovy
int add(int a, int b) {
    a + b
}
```

戻り値がないことを明示したいのであれば、Java と同様に `void` と定義することもできます。
戻り値の型が `void` のメソッドの戻り値を強引に取得しようとすると、`null` が返されます。

```groovy
void greet(String name) {
    println 'Hello, ' + name
}

def val = greet('Jack')
println val  //=> null
```

メソッドの呼び出し時にパラメータが 1 つ以上ある場合は、括弧を省略して呼び出すことができます。

```groovy
def val = add 100, 200
```

ただし、パラメータを 1 つも持たない場合は、呼び出し時の括弧を省略することはできません。

```groovy
void greet() {
    println 'Hello'
}

greet()  // 括弧は省略できない
```
