---
title: "Pythonメモ: Java や C/C++ のコード中のコメントを削除する"
url: "p/cwgffj8/"
date: "2016-12-09"
tags: ["python"]
aliases: /python/io/remove-java-comments.html
---

下記の `filter_java_comment` 関数は、受け取った Java や C/C++ のコードからコメントを削除して返します。
C 言語の `/* ... */` というスタイルのコメントと、C++ 言語の `// ...` というスタイルのコメントの両方を削除します。

```python
from enum import Enum

class State(Enum):
    CODE = 1
    C_COMMENT = 2
    CPP_COMMENT = 3
    STRING_LITERAL = 4

def filter_java_comment(text):
    """ Removes Java (C/C++) style comments from text. """
    result = []  # filtered text (char array)
    prev = ''  # previous char
    prevprev = ''  # previous previous char
    state = State.CODE

    for ch in text:
        # Skip to the end of C-style comment
        if state == State.C_COMMENT:
            if prevprev != '\\' and prev == '*' and ch == '/':  # End comment
                state = State.CODE
                prevprev = prev = ''
            elif ch == '\n':
                result.append('\n')
                prevprev = prev = ''
            else:
                prevprev, prev = prev, ch
            continue

        # Skip to the end of the line (C++ style comment)
        if state == State.CPP_COMMENT:
            if ch == '\n':  # End comment
                state = State.CODE
                result.append('\n')
                prevprev = prev = ''
            continue

        # Skip to the end of the string literal
        if state == State.STRING_LITERAL:
            if prev != '\\' and ch == '"':  # End literal
                state = State.CODE
            result.append(prev)
            prevprev, prev = prev, ch
            continue

        # Starts C-style comment?
        if prevprev != '\\' and prev == '/' and ch == '*':
            state = State.C_COMMENT
            prevprev = prev = ''
            continue

        # Starts C++ style comment?
        if prevprev != '\\' and prev == '/' and ch == '/':
            state = State.CPP_COMMENT
            prevprev = prev = ''
            continue

        # Comment has not started yet
        if prev:
            result.append(prev)

        # Starts string literal?
        if ch == '"':
            state = State.STRING_LITERAL
        prevprev, prev = prev, ch

    # Returns filtered text
    if prev:
        result.append(prev)
    return ''.join(result)
```

{{< code lang="python" title="使用例" >}}
if __name__ == '__main__':
    with open('Sample.java', encoding='utf-8') as f:
        content = f.read()  # テキストファイルを一括読み込み
        print(filter_java_comment(content))
{{< /code >}}

{{< code lang="java" title="入力ファイル (Sample.java)" >}}
public class Sample {
    /**
     * Greets someone.
     * @param name the name of who you want to greet
     */
    public void greet(String name) {
        // Dummy comment
        System.out.println("Hello " + name);
        /* Dummy *//* Dummy */
    }
}
{{< /code >}}

{{< code lang="java" title="出力結果" >}}
public class Sample {




    public void greet(String name) {

        System.out.println("Hello " + name);

    }
}
{{< /code >}}

改行はそのまま残しているので、フィルタ後のコード行数は変化しません。
