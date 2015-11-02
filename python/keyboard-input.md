---
title: キーボードからのユーザ入力を取得する
created: 2009-11-18
---

組み込み関数の **input** を使用して、プロンプトを表示して、ユーザからの入力を取得することができます。

#### sample.py
```python
x = input('Input your name: ')
print('Hello ' + x)
```

#### 実行例
```
$ python sample.py
Input your name: Jack
Hello Jack
```

Python 3.0 からは、`input` 関数は `string` オブジェクトを返すようになりました。

#### 参考: Python 3.1.1 のヘルプ
```
>>> help(input)
Help on built-in function input in module builtins:

input(...)
   input([prompt]) -> string

    Read a string from standard input.  The trailing newline is stripped.
    If the user hits EOF (Unix: Ctl-D, Windows: Ctl-Z+Return), raise EOFError.
    On Unix, GNU readline is used if enabled.  The prompt string, if given,
    is printed without a trailing newline before reading.
```

#### 参考: Python 2.4.3 のヘルプ
```
>>> help(input)
Help on built-in function input in module __builtin__:

input(...)
    input([prompt]) -> value

    Equivalent to eval(raw_input(prompt)).
```

