---
title: "Pythonメモ: 文字列をデリミタで分割する (split)"
url: "p/hhyq5vh/"
date: "2017-03-17"
tags: ["python"]
description: "Python で文字列をあるデリミタ（区切り文字列）で区切るには split を使用します。"
aliases: /python/numstr/split-string.html
---

スペースで文字列を分割する
----

文字列の **`split`** メソッドをパラメータなしで実行すると、空白（あるいは改行）をデリミタとして文字列を分割できます。

{{< code lang="python" title="文字列を空白で分割する" >}}
s = 'AAA BBB CCC'
arr = s.split()  #=> ['AAA', 'BBB', 'CCC']
{{< /code >}}

さらに、**各要素の先頭と末尾の余分な空白も削除されます**。

```python
s = '  AAA   BBB  CCC  '
arr = s.split()  #=> ['AAA', 'BBB', 'CCC']
```


任意のデリミタ文字で文字列を分割する
----

`split` メソッドの第 1 引数（**`sep`** パラメータ）を指定すると、任意のデリミタ文字（文字列）で文字列を分割できます。

{{< code lang="python" title="カンマで分割する" >}}
s = 'AAA, BBB, CCC'
arr = s.split(',')  #=> ['AAA', ' BBB', ' CCC']
{{< /code >}}

デリミタ文字を指定して分割する場合は、デリミタ文字の前後のスペースは削除されないことに注意してください。
分割後の各要素の先頭・末尾の空白を削除する場合は、例えば下記のように `strip` を組み合わせて使用します。

{{< code lang="python" title="カンマで分割して、各要素の前後の空白を削除する" >}}
s = ' AAA, BBB, CCC '
arr = [x.strip() for x in s.split(',')]
{{< /code >}}

次のように記述しても同様の結果になりますが、上記のように内包表記で記述した方がすっきり書けます。

```python
s = ' AAA, BBB, CCC '
arr = s.split(',')  #=> [' AAA', ' BBB', ' CCC ']
arr = list(map(lambda x: x.strip(), arr))  #=> ['AAA', 'BBB', 'CCC']
```


分割の最大サイズを指定する
----

`split` メソッドの第 2 引数（**`maxsplit`** パラメータ）を使うと、最大で何回分割するかを指定できます。
指定するのは「分割の回数」なので、分割後のサイズは最大で `maxsplit + 1` になることに注意してください。

{{< code lang="python" title="最大 1 回だけ分割" >}}
s = 'AAA BBB CCC DDD'
arr = s.split(maxsplit=1)  #=> ['AAA', 'BBB CCC DDD']
{{< /code >}}

分割数が決まっているのなら、分割後の値を別々の変数に代入すると、可読性の高いコードを記述できます。
下記は、コロン (`:`) で区切られたキー＆バリューの文字列を分割する例ですが、最大分割数を 1 に設定しているので、バリュー部分にコロンが含まれていても問題なく動作します。

```python
s = 'title: This sentence includes : what will happen?'
key, val = [x.strip() for x in s.split(':', 1)]

print(key)  #=> title
print(val)  #=> This sentence includes : what will happen?
```
