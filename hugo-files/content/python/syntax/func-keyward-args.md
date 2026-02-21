---
title: "Python の関数をキーワード引数を使って呼び出す"
url: "p/jf6kyao/"
date: "2023-11-12"
tags: ["Python"]
---

位置引数とキーワード引数
----

Python の関数を呼び出すときの引数の指定方法には、下記の 2 種類があります。

- __位置引数 (positional argument)__ ... 定義された順序通りに引数を渡す方法。
- __キーワード引数 (keyword argument)__ ... `引数名=値` のように引数名と値のペアで渡す方法。他の言語だと「名前付き引数」と呼ばれていたりします。

次の例では、2 つの文字列引数を受け取る `show_book` 関数を、それぞれの引数指定方法で呼び出しています。

```python
def show_book(title: str, author: str) -> None:
    print(f"{title} by {author}")

# 位置引数 (positional argument) による関数呼び出し
show_book("The Hobbit", "J.R.R. Tolkien")

# キーワード引数 (keyword argument) による関数呼び出し
show_book(author="J.R.R. Tolkien", title="The Hobbit")
```

位置引数による呼び出しの方が短く記述できますが、このように同じ型 (`str`) の引数が複数ある場合は、キーワード引数を使った方が呼び出し順序の間違いによる不具合が入りにくくなります。
後者の呼び出し方を見るとわかるように、キーワード引数を使うと、任意の順番で引数を記述できます。

位置引数とキーワード引数を混ぜて呼び出すこともできますが、すべての位置引数をキーワード引数よりも前に配置する必要があります。

```python
show_book("The Hobbit", author="J.R.R. Tolkien")  # OK
show_book(title="The Hobbit", "J.R.R. Tolkien")   # Syntax Error
```

ちなみに、キーワード引数を使う場合、`=` の前後にはスペースを入れないのが慣例です。


デフォルト引数との組み合わせ
----

キーワード引数による関数呼び出しの真価が発揮されるのは、引数にデフォルト値が設定されているときです。
次の `draw_rect` 関数は、3 つの引数を取りますが、それらすべてにデフォルト値が設定されています。
このような場合、キーワード引数を使って関数を呼び出すことで、任意の引数を省略することができます（位置引数を使うと、後方にある引数しか省略できません）。

```python
def draw_rect(width: int = 10, height: int = 3, char: str = "*") -> None:
    """指定したサイズの矩形を描画します。"""
    assert len(char) == 1, "char argument must be a single character"
    for _ in range(height):
        print(char * width)

draw_rect()
draw_rect(char="o")
draw_rect(width=20, height=2)
draw_rect(char="x", width=15)
```

{{< code title="実行結果" >}}
**********
**********
**********
oooooooooo
oooooooooo
oooooooooo
********************
********************
xxxxxxxxxxxxxxx
xxxxxxxxxxxxxxx
xxxxxxxxxxxxxxx
{{< /code >}}


シーケンスや辞書オブジェクトを展開して関数に渡す
----

引数として渡す値を、リストや辞書の形でまとめて定義しておいて、関数呼び出し時に展開して渡すことができます。

リストの内容を位置引数として関数に渡すには、リストオブジェクトの前に __`*`__ プレフィックスを付けます。

{{< code lang="python" title="リスト（シーケンス）を位置引数として渡す" >}}
args = [5, 2, "x"]
draw_square(*args)
# draw_square(5, 2, "x") と同様
{{< /code >}}

辞書の内容をキーワード引数として関数に渡すには、辞書オブジェクトの前に __`**`__ プレフィックスを付けます。

{{< code lang="python" title="辞書をキーワード引数として渡す" >}}
kwargs = {"char": "x", "height": 2}
draw_square(**kwargs)
# draw_square(char="x", height=2) と同様
{{< /code >}}

これらを組み合わせて使用する場合は、引数の基本ルール通り、位置引数を先に配置する必要があります。

```python
args = [3]
kwargs = {"char": "x"}
draw_square(5, *args, **kwargs)
# draw_square(5, 3, char="x") と同様
```


キーワード引数による呼び出しを強制する
----

関数のパラメーターとして __`*`__ とだけ記述すると、それ以降のパラメーターは、関数呼び出し時にキーワード引数の形でしか指定できなくなります。
本質的にオプショナルなパラメーターなどに使うとよさそうです。

次の例では、`delay` 引数の値を指定するときは、必ず `delay=1` のようなキーワード引数の形で指定するよう強制しています。

```python
import time

def greet(name: str, *, delay: int = 0) -> None:
    """Greets someone with a delay."""
    time.sleep(delay)
    print(f"Hello, {name}!")

# 使用例
greet("Alice")         # OK
greet("Bob", delay=1)  # OK
greet("Carol", 1)      # Error!
```

ちなみに、上記の `delay` パラメーターのように、キーワード引数での呼び出しを強制したものを、公式ドキュメントでは __keyword-only parameters__ と呼んでいます。
関数定義で、最初のパラメーターに `*` を配置すれば、すべてのパラメーターを keyword-only parameters にすることができます。
次の `show_book` 関数を呼び出すには、すべての引数をキーワード引数の形で指定する必要があります。

```python
def show_book(*, title: str, author: str) -> None:
    """Shows book information."""
    print(f"{title} by {author}")

# 使用例
show_book(title="The Hobbit", author="J.R.R. Tolkien")  # OK
show_book(author="J.R.R. Tolkien", title="The Hobbit")  # OK
show_book("The Hobbit", "J.R.R. Tolkien")  # Error!
```

この仕組みを使えば、関数の呼び出し時に引数の指定順序を間違える心配はなくなります（そもそも位置引数が使えなくなるので）。
もちろん、使いすぎは可読性を損なうので要注意です。


（おまけ）デフォルト引数は一度しか初期化されない
----

引数のデフォルト値としてミュータブル（可変）なオブジェクトを設定している場合は、関数呼び出し時にそのオブジェクトが使いまわされることに注意してください。
下記の関数の `buffer` 引数に設定しているデフォルト値 (`[]`) に値を追加すると、2 度目以降の関数呼び出し時にもその値が残っています。

```python
def append_and_dump(val: int, buffer: List[int] = []) -> None:
    buffer.append(val)  # 次の関数呼び出しにも影響する
    print(buffer)

append_and_dump(1)  # => [1]
append_and_dump(2)  # => [1, 2]
append_and_dump(3)  # => [1, 2, 3]
```

このような振る舞いを防ぐには、引数が省略されたときに明示的に新しいオブジェクトを作成するようにします。

```python
def append_and_dump(val: int, buffer: list[int] | None = None) -> None:
    if buffer is None:
        buffer = []
    buffer.append(val)
    print(buffer)

append_and_dump(1)  # => [1]
append_and_dump(2)  # => [2]
append_and_dump(3)  # => [3]
```


参考
----

- [Python の関数で可変長引数を扱う (`*args, **kwargs`)](/p/rcaip63/)

