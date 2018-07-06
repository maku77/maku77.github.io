---
title: "順列 (permutation) を作成する"
date: "2011-11-04"
---

Array#permutation() を使って順列を作成する
----

`Array#permutation()` は、各要素を 1 回ずつ使用した順列を作成します。
n 個の要素からなる順列の数は n! になります（例: 7! = 5040）。

#### 配列要素からなる順列を作成する

~~~ ruby
[1, 2, 3].permutation do |x|
  p x
end
~~~

#### 実行例

~~~ ruby
[1, 2, 3]
[1, 3, 2]
[2, 1, 3]
[2, 3, 1]
[3, 1, 2]
[3, 2, 1]
~~~

`permutation()` のパラメータとして、各順列の要素数を指定することができます。

~~~ ruby
[1, 2, 3].permutation(2) do |x|
  p x
end
~~~

~~~
[1, 2]
[1, 3]
[2, 1]
[2, 3]
[3, 1]
[3, 2]
~~~

以下のように `Enumeration#to_a` を使い、順列を配列として取得することもできますが、順列が大きくなると効率が悪くなるので、できるだけブロックで処理するのがよいでしょう。

~~~ ruby
perm = [1, 2, 3].permutation.to_a
p perm    # => [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
~~~

以下、参考までに自力で permutation を実装する例をいくつか示しますが、組み込みの `Array#permutation` 使った方が全然速いです（特に、再帰で独自実装するとかなり遅くなってしまいます）。


順列を自力で実装する方法（再帰による実装1）
----

ここでは、seed となる配列から順番に要素を取り出して、新たな配列を組み立てるという実装をしています。

~~~ ruby
def my_permutation(seed, curr=[], &proc)
  if seed.empty?
    proc.call(curr)
    return
  end

  0.upto(seed.size-1) do |i|
    newSeed = seed.dup
    newSeed.delete_at(i)
    my_permutation(newSeed, curr + [seed[i]], &proc)
  end
end

my_permutation([1, 2, 3]) do |x|
  p x
end
~~~

配列のコピーをしているところが効率悪いかも。。。


順列を自力で実装する方法（再帰よる実装2）
----

~~~ ruby
class Array
  def swap!(a, b)
    self[a], self[b] = self[b], self[a]
  end
end

def backtrack_permutate(seed, n=seed.size, &proc)
  if n == 1
    proc.call(seed.dup)
    return
  end

  0.upto(n-1) do |i|
    seed.swap!(i, n-1)
    backtrack_permutate(seed, n-1, &proc)
    seed.swap!(i, n-1)
  end
end

arr = [1, 2, 3]
backtrack_permutate(arr) do |x|
  p x
end
~~~


順列を自力で実装する方法 (Heap's method)
----

この方法は、シードとして与えた配列自体を破壊してしまうので、配列をキープしたければ `Array#dup` して渡すこと。

~~~ ruby
class Array
  def swap!(a, b)
    self[a], self[b] = self[b], self[a]
  end
end

def heap_permutate(seed, n=seed.size, &proc)
  if n == 1
    proc.call(seed.dup)
  else
    0.upto(n-1) do |i|
      heap_permutate(seed, n-1, &proc)
      if n % 2 == 1
        seed.swap!(0, n-1)
      else
        seed.swap!(i, n-1)
      end
    end
  end
end

arr = [1, 2, 3]
heap_permutate(arr) do |x|
  p x
end
~~~


関連記事
----

* [順列 (permutation) を作成する (Java)](/java/numstr/permutation.html)
* [順列 (permutation) を作成する (C++)](/cpp/number/permutation.html)
* [重複順列 (repeated permutation) を作成する (Ruby)](repeated-permutation.html)
* [組み合わせ (combination) を作成する (Ruby)](combination.html)
* [組み合わせ (combination) を作成する (C++)](/cpp/number/combination.html)

