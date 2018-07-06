---
title: "順列 (permutation) を作成する"
date: "2012-01-31"
---

Java には順列 (permutation) 作成用の API がないので、自力で順列を作成する必要があります。
下記は、再帰（バックトラック）で順列を作成する簡単な例です。

`permutation(int[] seed)` に適当な `int` 型配列を渡すと、その要素から作成される順列が、順番に `buildPerm(int[] perm)` に渡されるようになっています。

~~~ java
import java.util.Arrays;

public class Main {
    private static void permutation(int[] seed) {
        int[] perm = new int[seed.length];
        boolean[] used = new boolean[seed.length];
        buildPerm(seed, perm, used, 0);
    }

    private static void buildPerm(int[] seed, int[] perm, boolean[] used, int index) {
        if (index == seed.length) {
            procPerm(perm);
            return;
        }

        for (int i = 0; i < seed.length; ++i) {
            if (used[i]) continue;
            perm[index] = seed[i];
            used[i] = true;
            buildPerm(seed, perm, used, index + 1);
            used[i] = false;
        }
    }

    private static void procPerm(int[] perm) {
        // Use the perm[] here.
        System.out.println(Arrays.toString(perm));
    }

    public static void main(String[] args) {
        permutation(new int[]{1, 2, 3});
    }
}
~~~

やっていることは単純で、`seed[]` からひとつずつ要素を `perm[]` に詰めていっているだけです。
すでに詰め終わった要素を再度詰めてしまわないように、`used[]` 配列を便宜的に用意しています。

最初のシードとなる `seed[]` の要素が、昇順に並んでいれば（例: 1, 2, 3, 4）、順列も小さい順に生成されることが保証されます。


関連記事
----

* [順列 (permutation) を作成する (Java)](permutation.html)
* [順列 (permutation) を作成する (Ruby)](/ruby/number/permutation.html)
* [順列 (permutation) を作成する (C++)](/cpp/number/permutation.html)
* [組み合わせ (combination) を作成する (Ruby)](/ruby/number/combination.html)
* [組み合わせ (combination) を作成する (C++)](/cpp/number/combination.html)

