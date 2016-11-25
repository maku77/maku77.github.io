---
title: Python の文字列リテラル
created: 2009-11-19
---

Python の文字列リテラルは、シングルクォーテーションとダブルクォーテーションに違いはありません。

```python
s = 'ABCDE'
s = "ABCDE"
```

以下のような理由から、**シングルクォーテーションが好まれる**傾向にあるようです。

* Python の本家ドキュメントも多くの場合シングルクォーテーションを使っている
* ```'``` の方が ```"``` よりも速くタイプできる（英字配列キーボードのみ）

文字列リテラル内にクォーテーションマークが存在する場合は、文字列リテラルを囲む記号をうまく使い分けて可読性を上げるようにしましょう。

```python
>>> "Let's go!"
"Let's go!"
```

文字列リテラルをスペースで区切って並べると、Python は自動的に 1 つの文字列に結合します。

```python
>>> "I've been told, " '"Get out of here!"'
'I\'ve been told, "Get out of here!"'
```

ただし、変数に格納された後の文字列を結合するには `+` 演算子を使用する必要があります。

```python
>>> x = 'AAA'
>>> y = 'BBB'
>>> x + y
'AAABBB'
```

複数行に渡る文字列リテラルや、シングルクォート、ダブルクォートの両方を含む文字列リテラルを記述したい場合は、3 つのクォートで囲みます。シングルクォートでもダブルクォートでも構いません。

```python
>>> x = '''aaaa
... bbb ccc ddd
... eee fff ggg'''

>>> '''You can include both single(') and double(") quotes.'''
'You can include both single(\') and double(") quotes.'
```

文字列リテラルのプレフィックスに `r` (raw string) を付けると、文字列リテラル中のバックスラッシュがバックスラッシュ文字そのものとして扱われます（エスケープされない）。

```python
>>> print('aaa\nbbb')
aaa
bbb

>>> print(r'aaa\nbbb')
aaa\nbbb
```

