---
title: "組み合わせ (combination) を作成する"
date: "2012-03-13"
---


C++ で順列 (permutation) を生成するには `std::next_permutation()` が使えばよいのですが、組み合わせ (combination) を生成するものがないので作っておきます。


再帰を使わずに組み合わせを作成する例
----

#### combination.cpp

~~~ cpp
#include <iostream>
#include <vector>
using namespace std;

template<class T>
void combination(const vector<T>& seed, int target_size, bool (*callback)(const vector<T>&)) {
    vector<int> indices(target_size);
    const int seed_size = seed.size();
    int start_index = 0;
    int size = 0;

    while (size >= 0) {
        for (int i = start_index; i < seed_size; ++i) {
            indices[size++] = i;
            if (size == target_size) {
                vector<T> comb(target_size);
                for (int x = 0; x < target_size; ++x) {
                    comb[x] = seed[indices[x]];
                }
                if (callback(comb)) return;
                break;
            }
        }
        --size;
        if (size < 0) break;
        start_index = indices[size] + 1;
    }
}

///////////////// How to use

bool my_callback(const vector<int>& comb) {
    int n = comb.size();
    for (int i=0; i<n; ++i)
        cout << comb[i] << " ";
    cout << endl;
    return false;
}

int main() {
    vector<int> vals;
    vals.push_back(1);
    vals.push_back(2);
    vals.push_back(3);
    vals.push_back(4);
    vals.push_back(5);

    combination(vals, 3, my_callback);
}
~~~

上記のコードでは、`[1,2,3,4]` というリストから 2つ選ぶ組み合わせを列挙してます。
結果は、`[1,2][1,3][1,4][2,3][2,4][3,4]` のような感じになります。

組み合わせごとに、コールバック関数が呼ばれます。
コールバック関数で true を返すと、探索をストップします。

template で任意の型の vector を渡せるようにしてみましたが、`next_permutation` みたいに iterator を渡す方法を考えたほうがいいかもしれません（string もそのまま使えるし）。



再帰を使って組み合わせを作成する例
----

#### combination2.cpp

~~~ cpp
#include <iostream>
#include <vector>
using namespace std;

template<class T>
class Combination {
public:
    Combination(void (*callback)(const vector<T>&)) {
        this->callback = callback;
    }

    void go(const vector<T>& vals, int target_size) {
        vector<int> used(vals.size(), 0);
        vector<T> comb(target_size);
        next_comb(vals, used, comb, target_size);
    }

private:
    void (*callback)(const vector<T>&);

    void next_comb(const vector<T>& vals, vector<int>& used, vector<T>& comb,
            int target_size, int curr_size=0, int start_index=0) {
        if (curr_size == target_size) {
            callback(comb);
            return;
        }
        for (int i = start_index; i < vals.size(); ++i) {
            if (used[i]) continue;
            used[i] = 1;
            comb[curr_size] = vals[i];
            next_comb(vals, used, comb, target_size, curr_size+1, i+1);
            used[i] = 0;
        }
    }
};

//// 使用例（ [1,2,3,4] というリストから、２つ選んだ場合の組み合わせ）

void my_callback(const vector<int>& comb) {
    int n = comb.size();
    for (int i=0; i<n; ++i) cout << comb[i] << " ";
    cout << endl;
}

int main() {
    vector<int> vals;
    vals.push_back(1);
    vals.push_back(2);
    vals.push_back(3);
    vals.push_back(4);

    Combination<int> comb(my_callback);
    comb.go(vals, 2);
}
~~~

う～ん、こちらはイケてないですね。。。


関連記事
----

* [順列 (permutation) を作成する (C++)](permutation.html)
* [順列 (permutation) を作成する (Ruby)](/ruby/number/permutation.html)
* [順列 (permutation) を作成する (Java)](/java/numstr/permutation.html)
* [重複順列 (repeated permutation) を作成する (Ruby)](/ruby/number/repeated-permutation.html)
* [組み合わせ (combination) を作成する (Ruby)](/ruby/number/combination.html)

