---
title: "順列 (permutation) を作成する"
date: "2011-11-11"
---

- 参考: [http://www.cplusplus.com/reference/algorithm/next_permutation/](http://www.cplusplus.com/reference/algorithm/next_permutation/)

`bool std::next_permutation(first, last)` は、指定したコンテナ要素の順番を変更し、次に大きな値になるように並び替えます。

一番大きな値となる組み合わせを渡した場合は false を返し、要素の組み合わせは最小になるように巻き戻されます。
つまり、最小の組み合わせで `next_permutation` の呼び出しを開始し、false を返すまで繰り返し実行すれば、最後には元の値に戻っていることになります。


配列の順列を作成する場合
----

~~~ cpp
#include <iostream>
#include <algorithm>

int main() {
    int arr[] = {0, 1, 2};
    std::sort(arr, arr + 3);

    do {
        std::cout << arr[0] << arr[1] << arr[2] << std::endl;
    } while (std::next_permutation(arr, arr + 3));
}
~~~

#### 実行結果

~~~
012
021
102
120
201
210
~~~


文字列の順列を作成する場合
----

~~~ cpp
#include <iostream>
#include <string>
#include <algorithm>

int main() {
    std::string s = "012";

    do {
        std::cout << s[0] << s[1] << s[2] << std::endl;
    } while (std::next_permutation(s.begin(), s.end()));
}
~~~


std::vector の順列を作成する場合
----

~~~ cpp
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    std::vector<int> vec;
    vec.push_back(0);
    vec.push_back(1);
    vec.push_back(2);

    do {
        std::cout << vec[0] << vec[1] << vec[2] << std::endl;
    } while (std::next_permutation(vec.begin(), vec.end()));
}
~~~


関連記事
----

* [順列 (permutation) を作成する (Ruby)](/ruby/number/permutation.html)
* [順列 (permutation) を作成する (Java)](/java/numstr/permutation.html)
* [重複順列 (repeated permutation) を作成する (Ruby)](/ruby/number/repeated-permutation.html)
* [組み合わせ (combination) を作成する (C++)](combination.html)
* [組み合わせ (combination) を作成する (Ruby)](/ruby/number/combination.html)

