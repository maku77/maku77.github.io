---
title: ".NET - DateTime インスタンスと文字列を相互に変換する"
date: "2009-03-07"
---

日時を表す文字列から DateTime インスタンスを生成する
----

~~~ csharp
DateTime dt1 = DateTime.ParseExact("2009-03-05", "yyyy-MM-dd", null);
Console.WriteLine(dt1);  // 2009/03/05 0:00:00

DateTime dt2 = DateTime.ParseExact("2009-03-05 19:30:45", "yyyy-MM-dd HH:mm:ss", null);
Console.WriteLine(dt2);  // 2009/03/05 19:30:45
~~~

指定したフォーマットで文字列をパースできない場合は、`FormatException` が投げられます。


DateTime インスタンスから日時を表す文字列を生成する
----

~~~ csharp
DateTime dt = DateTime.Now;
String s = dt.ToString("yyyy-MM-dd HH:mm:ss");  // "2009-03-07 16:34:41"
~~~

参考: [カスタムの日付と時刻の書式指定文字列 - MSDN](http://msdn.microsoft.com/ja-jp/library/8kb3ddd4.aspx)

