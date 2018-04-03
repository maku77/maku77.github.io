---
title: "Date オブジェクトの日付を加算／減算する"
date: "2018-04-03"
---

Date オブジェクトは、次のようなイディオムを使用して、日付を１日進めたり、戻したりすることができます。

~~~ javascript
date.setDate(date.getDate() + 1);  // 1日進む
date.setDate(date.getDate() - 1);  // 1日戻る
~~~

例えば、date オブジェクトが1月31日を保持している場合、`getDate()` は 31 を返すので、`setDate(getDate() + 1)` は 32 という値を設定することになります。
この場合、内部的にちゃんと計算してくれて、2月1日に進めてくれます。

下記の例では、2000年12月25日を表す Date オブジェクトの日付を1日ずつ進めながら、どのようにその値が変化するかを調べています。

~~~ javascript
var date = new Date(2000, 11, 25); // 2000年12月25日

for (var i = 0; i < 10; ++i) {
  date.setDate(date.getDate() + 1);
  console.log(date.toDateString());
}
~~~

#### 実行結果

~~~
Tue Dec 26 2000
Wed Dec 27 2000
Thu Dec 28 2000
Fri Dec 29 2000
Sat Dec 30 2000
Sun Dec 31 2000
Mon Jan 01 2001
Tue Jan 02 2001
Wed Jan 03 2001
Thu Jan 04 2001
~~~

単純に1日ずつ加算しているだけですが、ちゃんと月、年をまたいで計算してくれていることが分かります。

