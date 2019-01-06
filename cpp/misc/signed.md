---
title: "signed が使える場所では signed を使え"
date: "2008-04-22"
---

C/C++ において、`signed int` が使用できる場所で無理に `unsigned int` を使おうとすると、思わぬ落とし穴にはまることがあります。

例 (1) signed int と unsigned int の演算結果は unsigned int
----

~~~ cpp
int iVal = -1;
unsigned int uiVal = 1;
cout << iVal / uiVal << std;
~~~

#### 実行結果:

~~~
4294967295 （-1 ではない！）
~~~

例 (2) unsigned int に単項演算子の - を付けても unsigned int のまま
----

~~~ cpp
unsigned int uiVal = 1;
cout << -uiVal << endl;
~~~

#### 実行結果

~~~
4294967295 （-1 ではない！）
~~~

要するに、計算結果が負の値になる可能性のある整数同士の計算式で `unsigned int` の変数を使ってはいけないということです。

ちなみに、非演算子の型のどちらかが `float` や `double` になっていれば、上記のような符号の問題は起こりません。
この辺りの細かい話は、C++ 言語を作ったストラウストラップの著書「プログラミング言語 C++」の算術変換の項を参照するとよくわかります。

<table><tr><td><a href="https://hb.afl.rakuten.co.jp/hgc/144180a1.9ac213ee.144180a2.e4d0f394/?pc=https%3A%2F%2Fitem.rakuten.co.jp%2Fbook%2F13037267%2F&m=http%3A%2F%2Fm.rakuten.co.jp%2Fbook%2Fi%2F17220687%2F&link_type=picttext&ut=eyJwYWdlIjoiaXRlbSIsInR5cGUiOiJwaWN0dGV4dCIsInNpemUiOiIzMDB4MzAwIiwibmFtIjoxLCJuYW1wIjoicmlnaHQiLCJjb20iOjAsImNvbXAiOiJyaWdodCIsInByaWNlIjoxLCJib3IiOjAsImNvbCI6MH0%3D" target="_blank" rel="nofollow" style="word-wrap:break-word;"  ><img src="https://hbb.afl.rakuten.co.jp/hgb/144180a1.9ac213ee.144180a2.e4d0f394/?me_id=1213310&item_id=17220687&m=https%3A%2F%2Fthumbnail.image.rakuten.co.jp%2F%400_mall%2Fbook%2Fcabinet%2F5954%2F9784797375954.jpg%3F_ex%3D80x80&pc=https%3A%2F%2Fthumbnail.image.rakuten.co.jp%2F%400_mall%2Fbook%2Fcabinet%2F5954%2F9784797375954.jpg%3F_ex%3D300x300&s=300x300&t=picttext" border="0" style="margin:2px" alt="[商品価格に関しましては、リンクが作成された時点と現時点で情報が変更されている場合がございます。]" title="[商品価格に関しましては、リンクが作成された時点と現時点で情報が変更されている場合がございます。]"></a></td><td style="vertical-align:top;width:310px;"><p style="font-size:12px;line-height:1.4em;text-align:left;margin:0px;padding:2px 6px;word-wrap:break-word"><a href="https://hb.afl.rakuten.co.jp/hgc/144180a1.9ac213ee.144180a2.e4d0f394/?pc=https%3A%2F%2Fitem.rakuten.co.jp%2Fbook%2F13037267%2F&m=http%3A%2F%2Fm.rakuten.co.jp%2Fbook%2Fi%2F17220687%2F&link_type=picttext&ut=eyJwYWdlIjoiaXRlbSIsInR5cGUiOiJwaWN0dGV4dCIsInNpemUiOiIzMDB4MzAwIiwibmFtIjoxLCJuYW1wIjoicmlnaHQiLCJjb20iOjAsImNvbXAiOiJyaWdodCIsInByaWNlIjoxLCJib3IiOjAsImNvbCI6MH0%3D" target="_blank" rel="nofollow" style="word-wrap:break-word;"  >プログラミング言語C＋＋第4版 C＋＋11対応 [ ビャーン・ストラウストラップ ]</a><br><span >価格：9504円（税込、送料無料)</span> <span style="color:#BBB">(2017/6/6時点)</span></p></td><tr></table>

よく理解していない人が書いた C++ の書籍には、「unsigned を使える場所では unsigned を使え」と簡単に書いてあったりしますが、この考え方は間違いだということがわかります。
C++ を正しく学ぶには、ストラウストラップの本を読みましょう。
分厚い本ですが、仕事で C++ を使用する人にとってはバイブルともいえる本です。

