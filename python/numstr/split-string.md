---
title: "文字列をデリミタで分割する (split)"
date: "2017-03-17"
description: "Python で文字列をあるデリミタ（区切り文字列）で区切るには split を使用します。"
---

スペースで文字列を分割する
----

文字列の `split` メソッドをパラメータなしで実行すると、空白（あるいは改行）をデリミタとして文字列を分割してくれます。

#### 文字列を空白で分割する

~~~ python
s = 'AAA BBB CCC'
arr = s.split()  #=> ['AAA', 'BBB', 'CCC']
~~~

さらに各要素の先頭と末尾の余分な空白を削除してくれます。

~~~ python
s = '  AAA   BBB  CCC  '
arr = s.split()  #=> ['AAA', 'BBB', 'CCC']
~~~


任意のデリミタ文字で文字列を分割する
----

`split` メソッドの第一引数（`sep` パラメータ）を指定すると、任意のデリミタ文字（文字列）で文字列を分割することができます。

#### カンマで分割する

~~~ python
s = 'AAA, BBB, CCC'
arr = s.split(',')  #=> ['AAA', ' BBB', ' CCC']
~~~

デリミタ文字の前後のスペースは削除されないことに注意してください。
分割後の各要素の先頭／末尾の空白を削除する場合は、例えば下記のように `strip` を組み合わせて使用します。

#### カンマで分割して、各要素の前後の空白を削除する

~~~ python
s = ' AAA, BBB, CCC '
arr = [x.strip() for x in s.split(',')]
~~~

次のように記述しても同様の結果になりますが、内包表記で記述した方がすっきり書けますね。

~~~ python
s = ' AAA, BBB, CCC '
arr = s.split(',')  #=> [' AAA', ' BBB', ' CCC ']
arr = list(map(lambda x: x.strip(), arr))  #=> ['AAA', 'BBB', 'CCC']
~~~


分割の最大サイズを指定する
----

`split` メソッドの第2引数（`maxsplit` パラメータ）を使うと、最大で何回分割するかを指定することができます。
指定するのは「分割の回数」なので、分割後のサイズは最大で「maxsplit + 1」になることに注意してください。

#### 最大 1 回だけ分割

~~~ python
s = 'AAA BBB CCC DDD'
arr = s.split(maxsplit=1)  #=> ['AAA', 'BBB CCC DDD']
~~~

分割数が決まっているのなら、分割後の値を別々の変数に代入すると可読性の高いコードを記述することができます。
下記は、コロン (`:`) で区切られたキー＆バリューの文字列を分割する例ですが、最大分割数を 1 に設定しているので、バリュー部分にコロンが含まれていても問題なく動作します。

~~~ python
s = 'Key: This value includes : What will happen?'
key, val = [x.strip() for x in s.split(':', 1)]

print(key)  #=> Key
print(val)  #=> This value includes : What will happen?
~~~

