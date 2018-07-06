---
title: "組み合わせ (combination) を作成する"
date: "2011-12-23"
---

Array#combination() を使って組み合わせを作成する
----

Ruby の `Array#combination` メソッドは、与えられた配列要素の組み合わせ (Combinateion) を作成します。
順序は保証されないので、小さい順に組み合わせを取り出したい場合は、生成後にソートする必要があります。

~~~ ruby
comb = [3,1,4,2].combination(2)
arr = comb.map {|x| x.sort}.sort
p arr  #=> [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
~~~


自力で組み合わせを作成する（再帰による実装）
----

自力で `combination` を実装する場合は、以下のような単純なバックトラック（再帰）で実装することができます。

~~~ ruby
def combination(seed_size, comb_size, comb=[0]*comb_size, count=0)
  if count == comb_size
    p comb
    return
  end

  start_index = count == 0 ? 0 : comb[count-1]+1
  (start_index...seed_size).each do |i|
    comb[count] = i
    combination(seed_size, comb_size, comb, count+1)
  end
end

combination(5, 4)  # 5C4
~~~

#### 実行結果

~~~
[0, 1, 2, 3]
[0, 1, 2, 4]
[0, 1, 3, 4]
[0, 2, 3, 4]
[1, 2, 3, 4]
~~~


関連記事
----

* [順列 (permutation) を作成する (Ruby)](permutation.html)
* [順列 (permutation) を作成する (Java)](/java/numstr/permutation.html)
* [順列 (permutation) を作成する (C++)](/cpp/number/permutation.html)
* [重複順列 (repeated permutation) を作成する (Ruby)](repeated-permutation.html)
* [組み合わせ (combination) を作成する (C++)](/cpp/number/combination.html)

