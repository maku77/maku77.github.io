---
title: .NET - 時刻部分をすべて 0 にした DateTime インスタンスを作成する
date: "2009-03-22"
---

下記のユーティリティ関数を使用すると、パラメータで渡した `DateTime` オブジェクトの、時刻部分をクリアすることができます（00時00分00秒になる）。

~~~ csharp
/// <summary>
/// DateTime の時刻部分をすべて 0 にした DateTime を作成します。
/// </summary>
/// <param name="dt">元になる DateTime オブジェクト</param>
/// <returns>時刻部分をすべて 0 にした DateTime オブジェクト</returns>
public static DateTime ClearTimePart(DateTime dt) {
    return new DateTime(dt.Year, dt.Month, dt.Day, 0, 0, 0, dt.Kind);
}
~~~

