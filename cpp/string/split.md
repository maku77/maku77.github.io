---
title: "文字列を区切り文字で分割する (istringstream)"
created: 2011-12-02
---

istringstream で空白文字で分割する
----

下記のサンプルでは、分割後の各トークンをループ処理しています。

~~~ cpp
#include <iostream>
#include <sstream>  // istringstream
#include <string>
using namespace std;

int main() {
    string line = "AAA BBB CCC";
    istringstream iss(line);
    string s;
    while (iss >> s) {
        cout << s << endl;
    }
}
~~~

分割後のトークン数が分かっているのであれば、下記のように別の変数に取得してしまうこともできます。

~~~ cpp
istringstream iss("AAA BBB");
string a, b;
iss >> a >> b;
~~~


任意のデリミタで分割する（CSV など）
----

下記は、任意のデリミタで文字列を分割する `split` 関数の実装例です。

~~~ cpp
#include <iostream>
#include <vector>
#include <string>
using namespace std;

vector<string> split(const string& src, const char* delim = ",") {
    vector<string> vec;
    string::size_type len = src.length();

    for (string::size_type i = 0, n; i < len; i = n + 1) {
        n = src.find_first_of(delim, i);
        if (n == string::npos) {
            n = len;
        }
        vec.push_back(src.substr(i, n - i));
    }

    return vec;
}

int main() {
    string s = "AAA,BBB,CCC";
    vector<string> arr = split(s);

    for (int i = 0; i < arr.size(); ++i) {
        cout << arr[i] << endl;
    }
}
~~~

`istringstream` を使用して、下記のように記述することもできます。

~~~ cpp
vector<string> split(const string& str, char delim = ','){
    istringstream iss(str);
    string tmp;
    vector<string> res;
    while (getline(iss, tmp, delim)) res.push_back(tmp);
    return res;
}
~~~


デリミタ文字を空白文字に置換してから istringstream で分割する方法
----

例えばカンマで文字列を分割したい場合は、カンマをあらかじめ空白に置換してしまえば `istringstream` でそのまま分割できます。
ただし、空白文字を含んだ文字列に対してはこの方法は使えません。
下記は CSV を分割する例です。

~~~ cpp
#include <iostream>
#include <sstream>  // istringstream
#include <string>
using namespace std;

int main() {
    string line = "aaa,bbb,ccc";
    replace(line.begin(), line.end(), ',', ' ');
    istringstream iss(line);

    string a, b, c;
    iss >> a >> b >> c;

    cout << a << endl;
    cout << b << endl;
    cout << c << endl;
}
~~~


string#find を使う方法
----

入力する文字列が、必ず空白を含んでいることが保証できるなら、以下のように空白の位置を見つけて分割することができます。

~~~ cpp
#include <iostream>
#include <string>
using namespace std;

int main() {
    string line = "aaa bbb";
    string a, b;
    int pos = line.find(' ');
    a = line.substr(0, pos);
    b = line.substr(pos + 1);
    ...
}
~~~

