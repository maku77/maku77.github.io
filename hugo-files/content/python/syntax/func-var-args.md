---
title: "Python の関数で可変長引数を扱う (*args, **kwargs)"
url: "p/rcaip63/"
date: "2014-06-02"
tags: ["Python"]
aliases: /python/syntax/variable-length-args.html
---


可変長引数
----

関数に任意の数の引数を渡せるようにするには、パラメーター名の前にアスタリスク (__`*`__) を付けます。
下記の `print_lines` 関数には、任意の数（0 個以上）の文字列を渡すことができます。

```python
def print_lines(*lines: str) -> None:
    for line in lines:
        print(line)

# 使用例
print_lines("Hello", "World", "Python")
```

{{< code title="実行結果" >}}
Hello
World
Python
{{< /code >}}

関数内では、`lines` パラメーターは文字列型 (`str`) のタプルとして参照できます。


任意のキーワード引数を渡せるようにする
----

パラメーター名の前に 2 つのアスタリスク (__`**`__) を付けると、任意のキーワード引数を受け取れる関数になります。
パラメーター名には慣例としてよく `kwargs` が使われます（keyword arguments の略）。

```python
def process_data(**kwargs) -> None:
    for key, value in kwargs.items():
        print(f"{key}: {value}")

# 使用例
process_data(name="John", age=30, city="New York")
process_data(subject="Python", level="Intermediate", duration=5)
```

{{< code title="実行結果" >}}
name: John
age: 30
city: New York
subject: Python
level: Intermediate
duration: 5
{{< /code >}}

関数内では、`kwargs` パラメーターは辞書オブジェクトとして参照できます。


その他
----

### 可変長引数の伝播

可変長引数として受け取ったタプルや辞書オブジェクトを、可変長引数を受け取る別の関数に渡すときは、一度展開して渡す必要があります。
変数名の前に __`*`__ や __`**`__ をつけると展開できます。

```python
def foo(*args, **kwargs):
    print(args, kwargs)

def bar(*args, **kwargs):
    foo(*args, **kwargs)  # タプルや辞書オブジェクトは展開して渡す
```

### 各種引数の組み合わせ

次のように、各種引数を組み合わせて定義することもできます。
ただし、可変長引数の後ろにある引数は、キーワード引数の形で指定して呼び出す必要があります。
これは、位置引数の終わり（下記例では `"Alias-2"`）が可変長引数に渡す引数の終わりと判断されるからです。

```python
def show_book_info(title: str, *aliases: str, note: str, **kwargs) -> None:
    print(f"Title: {title}")
    for alias in aliases:
        print(f"Alias: {alias}")
    print(f"Note: {note}")
    for key, value in kwargs.items():
        print(f"{key}: {value}")

# 使用例
show_book_info("Title", "Alias-1", "Alias-2", note="Note", tag1="A", tag2="B")
```

{{< code title="実行結果" >}}
Title: Title
Alias: Alias-1
Alias: Alias-2
Note: Note
tag1: A
tag2: B
{{< /code >}}


参考
----

- [Python の関数をキーワード引数を使って呼び出す](/p/jf6kyao/)

