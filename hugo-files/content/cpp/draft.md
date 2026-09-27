---
title: "C++メモ: 雑多ドラフト"
url: "p/499494f/"
date: "2026-08-09"
tags: ["cpp"]
draft: true
---

2005/09/21 (Wed) [C/C++] 型の違う関数ポインタにキャスト
----

```cpp
#include <iostream>
using namespace std;

// int 型の引数をとる関数
void hoge(int n) {
    cout << n << endl;
}

int main() {
    // 引数無しの関数ポインタを用意
    void (*p)();

    // 型の違う関数ポインタに強引にキャストして代入
    p = (void (*)()) hoge;

    // 本来の型に戻して実行
    ((void (*)(int)) p)(100);
}
```

2005/08/04 (Thu) 文字列に指定した文字数ごとに区切り文字を挿入します
----

```cpp
#include <string>
// input 対象文字列
// each 何文字ごとに区切り文字を入れるか
// delimiter 挿入する区切り文字
std::string createInsertDelimiter(std::string input, int each, char delimiter)
{
	if (input.length() == 0) {
		return std::string("");
	}

	// 区切り文字の挿入を考慮して格納先バッファを用意
	std::string result;
	result.reserve(input.length() + ((input.length() - 1) / each));

	// 区切り文字列を入れながら文字列をコピーする
	result.push_back(input[0]);
	for (int i = 1; i < input.length(); ++i) {
		if (i % each == 0) {
			result.push_back(delimiter);
		}
		result.push_back(input[i]);
	}

	return result;
}
```

{{< code lang="cpp" title="例: 3 文字ごとに ',' を挿入する" >}}
std::cout << createInsertDelimiter("aaabbbccc", 3, ',');
{{< /code >}}

{{< code title="実行結果" >}}
aaa,bbb,ccc
{{< /code >}}


2005/08/04 (Thu) 文字列、数値の変換
----

### int 値を上位ビットから char 単位で取り出すイディオム

```cpp
union {
	int intValue;
	char charValue[sizeof(int)];
};

intValue = 123456789;
for (int i = sizeof(int) - 1; i >= 0; --i) {
	// ここで charValue[i] を使う
}
```

### 数値を 2 進数文字列に変換

```cpp
#include <climits>
#include <string>
// あとでテンプレート化
std::string toBinaryString(char c) {
	char buf[CHAR_BIT + 1] = {0};
	for (int i = CHAR_BIT - 1; i >= 0; --i) {
		buf[i] = (c & 1) == 1 ? '1' : '0';
		c >>= 1;
	}
	return std::string(buf);
}
```

### ストリームを使って数値から文字列へ変換

```cpp
#include <iostream>  // cout, endl
#include <sstream>   // ostringstream

std::ostringstream ostr;
ostr << 100;
std::cout << ostr.str() << std::endl;
```

### 文字列を逆順にする（参考： K&R 第 2 版）

```cpp
#include <string.h>
void my_reverse(char s[])
{
	int i, j;

	for (i = 0, j = strlen(s) - 1; i < j; ++i, --j) {
		char c = s[i];
		s[i] = s[j];
		s[j] = c;
	}
}
```

### itoa が使える環境での C++ 版 itoa 実装

```cpp
#include <climits>  // for CHAR_BIT
std::string my_itoa(int n, int base)
{
	char buf[CHAR_BIT * sizeof(int) + 1];
	itoa(n, buf, base);
	return std::string(buf);
}
```


2005/08/03 (Wed) ストリームの入力によって文字列を構築する (stringstream)
----

```cpp
#include <iostream>
#include <sstream>
#include <string>

int main () {
	std::stringstream ss;
	ss << "Value = " << 100;
	std::string s = ss.str();

	std::cout << s << std::endl;
	return 0;
}
```

2005/07/27 (Wed) 意外と分かってない switch ステートメント
----

### switch ステートメント内のラベルの順番について

C の仕様では `case` ラベルの順番は任意なので、次のように `default` ラベルが最初に来ても大丈夫です。

```cpp
int main() {
	int n = 1;

	switch (n) {
	case default:
		std::cout << "default";
		break;
	case 1:
		std::cout << "one";
		break;
	}
}
```

{{< code title="実行結果" >}}
one
{{< /code >}}

ただし、これは C/C++ の `switch` が `int` と `enum` 型にしか使えないという制約があり、与えられた値に対してジャンプ先が一意に決まるからできることです。

スクリプト言語の場合は、`switch` に正規表現を指定できたり、数値の範囲を指定できたりするものがあるので、ラベルの順番が実行シーケンスに関係「ある」ということになります。
スクリプトだけに、単純に上から順に評価しているだけということもあります。

例えば、Ruby の `case` ステートメントの場合、

```ruby
$n = 4
case $n
	when 1 .. 5
		p "1-5"
	when 3 .. 8
		p "3-8"
end
```

こうすると "1-5" と表示されますが、`when` の位置を入れ替えて、

```ruby
$n = 4
case $n
	when 3 .. 8
		p "3-8"
	when 1 .. 5
		p "1-5"
end
```

このようにすると "3-8" と表示されます。
当然次のように `else` を一番最初に持ってきたりすると、エラーになります。

```ruby
$n = 4
case $n
	else
		p "Else"
	when 1 .. 5
		p "1-5"
	when 3 .. 8
		p "3-8"
end
```

### switch ステートメント内で case ラベルに同じ値のものがあってはいけない

C/C++ では `switch` ステートメント内のラベルに同じ値が出てきてはいけません。

```cpp
enum MyEnum {
	A = 0,
	B = 1,
	C = 0
};

int main() {
	MyEnum e = C;

	switch (e) {
	case A:
		break;
	case B:
		break;
	case C:       // ← A と同じ値だから NG!
		break;
	}
}
```

Ruby の場合は次のようなコードも普通に通ります。

```ruby
$age = 1

case $age
	when 1
		p "one A"
	when 1
		p "one B"
end
```

### default ラベルの綴りを間違えてもエラーにならない
C/C++ では次のように `default` ラベルの綴りを間違えても普通にコンパイルが通ってしまいます。

```cpp
switch (n) {
case 1:
	break;
defalt:       // "default:" の間違い
	break;
}
```

これは綴りを間違えたラベルが単なるジャンプ先のラベルとしてみなされるからです。
例えば、次のようなコーディングがされるかもしれないのでコンパイラはエラーを出さないのです。

```cpp
switch (n) {
case 1:
	foo();
	break;
SUPER_DEFAULT:  // ここには色んな場所から飛んでくる可能性がある
	bar();
	break;
}

if (hoge) {
	goto SUPER_DEFAULT;  // goto ジャンプで switch の中へ
}
```

