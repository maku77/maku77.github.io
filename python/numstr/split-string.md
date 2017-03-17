---
title: 文字列をデリミタで分割する (split)
created: 2017-03-17
---

文字列の `split` メソッドを使用して、任意のデリミタ文字で文字列を分割することができます。

~~~ python
s = 'AAA, BBB, CCC'
arr = s.split(',')  #=> ['aaa', ' bbb', ' ccc']
~~~

セパレータを省略すると、空白文字で分割され、さらに各要素の先頭／末尾の空白文字が削除されます。

~~~ python
s = ' AAA BBB  CCC   '
arr = s.split()  #=> ['AAA', 'BBB', 'CCC']
~~~

各要素の先頭／末尾の空白を自力で削除する場合は、例えば下記のようにします。

~~~ python
s = ' AAA, BBB, CCC '
arr = s.split(',')  #=> [' AAA', ' BBB', ' CCC ']
arr = list(map(lambda x: x.strip(), arr))  #=> ['AAA', 'BBB', 'CCC']
~~~

