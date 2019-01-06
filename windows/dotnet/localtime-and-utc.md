---
title: ".NET - 現地時刻（ローカルタイム）と世界協定時刻 (UTC) の扱いを理解する"
date: "2009-03-07"
---

.NET の `DateTime` オブジェクトが、local time を表しているか UTC を表しているかは、`DateTime.Kind` プロパティ（型: `DateTimeKind`）で調べることができます。

- `DateTimeKind.UTC` -- 世界協定時刻を表す
- `DateTimeKind.Local` -- 現地時刻を表す
- `DateTimeKind.Unspecified` -- どちらか判別不能

`DateTime` オブジェクト作成時の `Kind` プロパティの値は、どのようにインスタンスを生成したかによって変わってきます。
例えば、`Date.Now` メソッドで作成すると local time になり、`Date.UtcNow` メソッドで作成すると UTC を表す `DateTime` オブジェクトになります。

`DateTime.Kind` プロパティの値は、文字列に変換した場合（`Console.WriteLine` で出力した場合）にも影響を与えます。
`Kind` プロパティが `Local` の場合は現地時刻を表す文字列に変換れ、`Utc` の場合は世界標準時を表す文字列に変換されます。

~~~ csharp
DateTime dtLocal = DateTime.Now;
Console.WriteLine(dtLocal.Kind);  // Local
Console.WriteLine(dtLocal);  // 2009/03/07 16:53:26

DateTime dtUtc = DateTime.UtcNow;
Console.WriteLine(dtUtc.Kind);  // Utc
Console.WriteLine(dtUtc);  // 2009/03/07 7:53:26  （日本 (JST) で実行した場合）
~~~

日時を表す数値や文字列を直接指定して `DateTime` を生成した場合は、`Kind` プロパティの値は最初 `Unspecified`（どちらとも判別不能）に設定されます。

この状態で文字列に変換しようとすると、デフォルトで現地時刻を表す文字列に変換されます。
つまり、`DateTime` インスタンスを生成するときに、現地時刻で値を指定したとみなされます。

~~~ csharp
DateTime dt = new DateTime(2009, 1, 1, 15, 0, 0);
Console.WriteLine(dt.Kind);  // Unspecified
Console.WriteLine(dt.ToString("yyyy-MM-dd HH:mm:ss"));  // 2009-01-01 15:00:00
~~~

static メソッドである、`DateTime.SpecifyKind` を使うと、指定した `DateTime` オブジェクトが local time か UTC どちらを示しているかを設定できます（正確に言うと、新しい `DateTime` オブジェクトを生成します）。

`Kind` プロパティの値を変更しても、内部で保持している値が local time なのか UTC なのか区別するために使われるだけなので、文字列表現への変換結果は変わりません。

~~~ csharp
DateTime dt = new DateTime(2009, 1, 1, 15, 0, 0);

dt = DateTime.SpecifyKind(dt, DateTimeKind.Utc);
Console.WriteLine(dt.Kind);  // Utc
Console.WriteLine(dt.ToString("yyyy-MM-dd HH:mm:ss"));  // 2009-01-01 15:00:00

dt = DateTime.SpecifyKind(dt, DateTimeKind.Local);
Console.WriteLine(dt.Kind);  // Local
Console.WriteLine(dt.ToString("yyyy-MM-dd HH:mm:ss"));  // 2009-01-01 15:00:00
~~~

`DateTime.ToUniversalTime()` メソッドや `DateTime.ToLocalTime()` メソッドを使用すると、local time を表す `DateTime` を UTC に変換したり、逆に UTC を表す `DateTime` を local time に変換したりできます。
`Kind` プロパティが `Unspecified` の状態でこれらのメソッドを呼び出すと、現状の `Kind` が、変換後の `Kind` ではないと仮定して変換されます。

|      | `dt = dt.ToUniversalTime(dt)` の結果 | `dt = dt.ToLocalTime(dt)` の結果 |
| ---- | ---- | ---- |
| 変換前の `Kind` が `Utc` の場合 | 変化しない | local time に変換される |
| 変換前の `Kind` が `Local` の場合 | UTC に変換される | 変化しない |
| 変換前の `Kind` が `Unspecified` の場合 | UTC に変換される（変換前は Local であったとみなす） | local time に変換される（変換前は Utc であったとみなす） |

~~~ csharp
DateTime dt = DateTime.ParseExact("2009-01-01 15:00:00", "yyyy-MM-dd HH:mm:ss", null);
Console.WriteLine(dt.Kind);  // Unspecified
dt = dt.ToUniversalTime();   // 現地時刻で指定されたと仮定して UTC に変換
Console.WriteLine(dt.Kind);  // Utc
Console.WriteLine(dt.ToString("yyyy-MM-dd HH:mm:ss"));  // 2009-01-01 06:00:00
~~~

