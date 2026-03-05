---
title: "C++メモ: signed が使える場所では signed を使え"
url: "p/54n7gqf/"
date: "2008-04-22"
tags: ["cpp"]
aliases: /cpp/misc/signed.html
---

C/C++ において、`signed int` が使用できる場所で無理に `unsigned int` を使おうとすると、思わぬ落とし穴にはまることがあります。

例 (1) signed int と unsigned int の演算結果は unsigned int
----

```cpp
int iVal = -1;
unsigned int uiVal = 1;
cout << iVal / uiVal << endl;
```

{{< code title="実行結果" >}}
4294967295 （-1 ではない！）
{{< /code >}}

例 (2) unsigned int に単項演算子の - を付けても unsigned int のまま
----

```cpp
unsigned int uiVal = 1;
cout << -uiVal << endl;
```

{{< code title="実行結果" >}}
4294967295 （-1 ではない！）
{{< /code >}}

要するに、計算結果が負の値になる可能性のある整数同士の計算式で `unsigned int` の変数を使ってはいけないということです。

ちなみに、被演算子の型のどちらかが `float` や `double` になっていれば、上記のような符号の問題は起こりません。
この辺りの細かい話は、C++ 言語を作ったストラウストラップの著書「プログラミング言語 C++」の算術変換の項を参照するとよくわかります。

{{< amazon
  itemId="4797375957"
  title="プログラミング言語C++ 第4版"
  author="Bjarne Stroustrup"
  publisher="SBクリエイティブ"
  imageUrl="https://m.media-amazon.com/images/I/71krxBESNzL._SL1078_.jpg"
>}}

よく理解していない人が書いた C++ の書籍には、「`unsigned` を使える場所では `unsigned` を使え」と簡単に書いてあったりしますが、この考え方は間違いだということがわかります。
C++ を正しく学ぶには、ストラウストラップの本を読みましょう。
分厚い本ですが、仕事で C++ を使用する人にとってはバイブルともいえる本です。

