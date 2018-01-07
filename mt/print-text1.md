---
title: 画面上にテキスト出力する (1) Alert, Comment, Print の基本
date: "2015-01-31"
---

プログラムのテストや、ユーザへの通知のために画面上にテキストを表示したい場合は、下記の関数を使用できます。

* [Alert()](http://www.mql5.com/en/docs/common/alert) -- アラートボックスでテキスト表示（Strategy Tester での実行時は無視される）
* [Comment()](https://www.mql5.com/en/docs/common/comment) -- チャートの左上にテキスト表示
* [Print()](https://www.mql5.com/en/docs/common/print) -- Terminal ビュー (Ctrl+T) の Expert タブ内にテキスト表示


#### 使用例

メソッドごとに出力先は異なりますが、どのメソッドも渡されたパラメータの型によって適切な形式でテキスト表示してくれます。

```mql
string s = "Hello";
bool b = true;
int i = 100;
float f = 0.123456789f;
double d = 0.123456789;
datetime dt = TimeLocal();
color cl = C'0xFF,0xC0,0x80';

Alert(s);  //=> Hello
Alert(b);  //=> true
Alert(i);  //=> 100
Alert(f);  //=> 0.12346
Alert(d);  //=> 0.123456789
Alert(dt); //=> 2014.12.19 23:59:59
Alert(cl); //=> 255,192,128
```

パラメータは複数渡すことができ、それぞれが連結されて表示されます。

```mql
Print("value = ", value);  //=> "value = 100"
```

出力時に改行を入れたい場合は、文字列中に改行コード (`\n`) を含めれば OK です。

```mql
Comment("AAA\nBBB\nCCC");
```
