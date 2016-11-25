---
title: 文字列を置換する
created: 2012-07-03


単純な置換 (str.replace)
----

```python
s = 'AABBCCAABBCC'
s = s.replace('AA', 'xx')  #=> 'xxBBCCxxBBCC'
```

置換した結果は戻り値として返されるので、元の文字列を変更したい場合は、自分自身に代入する必要があります。
デフォルトではパターンに一致した全ての文字列が置換されますが、第３引数 (`count`) で、置換の最大数を指定することも可能です。
下記の例では、`count=1` と設定することで、最初に一致した部分だけを置換しています。

```python
s = 'AABBCCAABBCC'
s = s.replace('AA', 'xx', 1)  #=> 'xxBBCCAABBCC'
```


正規表現を使った置換 (re.sub, re.subn)
----

`re` モジュールを使うと、正規表現による置換を行えるようになります。
正規表現を含む文字列リテラルを記述する場合は、`r` プレフィックス付けた raw string 記法 (`r'...'`) を使用することで、記号類のエスケープ処理が不要になります。

#### 例: '<' と '>' で囲まれた数値を '#' に置換

```python
>>> import re
>>> re.sub(r'<\d+>', '#', 'aaa<100>bbb<200>ccc')
'aaa#bbb#ccc'
```

`str.replace` と同様に、第３引数 (`count`) を指定することで、いくつまで置換するか制限することができます。

```python
>>> re.sub(r'<\d+>', '###', 'aaa<100>bbb<200>ccc', 1)
'aaa###bbb<200>ccc'
```

正規表現を使って置換するときに、マッチした文字列を置換後の文字列に反映させることができます。
正規表現パターンを `(` と `)` で囲んでグルーピングしておくと、括弧で囲まれた部分に一致した文字列を `\1`、`\2`、`\3` などで参照できます（後方参照）。
この後方参照を利用するときは、文字列リテラルの前に `r` を付ける必要があります。

#### 例: * で囲まれたテキストを &lt;b> タグで囲まれたテキストに置換する

```python
>>> import re
>>> re.sub(r'\*(.+)\*', r'<b>\1</b>', 'AAA *BBB* CCC')
'AAA <b>BBB</b> CCC'
```

実際にいくつ置換されたかを知る必要がある場合は、`re.subn()` を使用します。

```python
import re

result, num = re.subn(r'(\d+)', r'<\1>', 'aa11bb22cc')
print(result)  #=> 'aa<11>bb<22>cc'
print(num)     #=> 2
```


正規表現パターンをコンパイルして高速化する
----

いろいろなパターンで `re.sub()` による置換を繰り返し実行する場合は、あらかじめ正規表現パターンを `re.compile()` を使用して正規表現オブジェクトにコンパイルしておくと高速に置換処理を行えるようになります（ただし、１つのパターンのみを連続して使用する場合は、内部でキャッシュが効くため、`re.compile()` の必要はありません）。

下記の例では、HTML ファイル内の日付、時刻と思われる部分を見つけて強調表示する（em タグで囲む）ように置換しています。

```python
import re

# 正規表現パターンをコンパイル
DATE_PATTERN = re.compile(r'(\d{4}-\d{2}-\d{2})')
TIME_PATTERN = re.compile(r'(\d\d:\d\d:\d\d)')

# 置換処理の実行
with open('input.html') as f:
    for line in f:
        line = line.rstrip('\r\n')
        line = DATE_PATTERN.sub(r'<em>\1</em>', line)
        line = TIME_PATTERN.sub(r'<em>\1</em>', line)
        print(line)
```


文字列中の 1 文字を変更する
----

Python の文字列は Java や C# と同様に immutable（不変）なので、以下のように文字列内の１文字だけを置き換えるということはできません。

```python
s = 'ABCDE'
s[2] = 'x'  #=> TypeError: 'str' object does not support item assignment
```

文字列中の 1 文字を変更したい場合は、以下のように新しい文字列を作成します。

```python
s = 'ABCDE'
s = s[:2] + 'x' + s[3:]  #=> 'ABxDE'
```

あるいは、`bytearray` オブジェクトを変更可能な文字列のように扱うこともできます。

```python
s = bytearray('ABCDE'.encode())
s[2] = ord('x')
print(s.decode())  #=> ABxDE
```

