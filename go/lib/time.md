---
title: "時刻データを扱う (time)"
url: "p/sy58beh"
permalink: "p/sy58beh"
date: "2017-09-12"
tags: ["Go"]
description: "Go 言語で日付や時刻の情報を扱うには time パッケージの time.Time 型を使用します。"
redirect_from:
  - /hugo/go/time
---

時刻データ (time.Time) を作成する
----

### 現在時刻を取得する

Go 言語で日時を表す型は、`time` パッケージで定義されている `time.Time` 型です。
現在の時刻を取得するには、`time.Now` 関数を使用します。

~~~ go
t := time.Now()
fmt.Printf("%v\n", t)  // time.Time が保持する値を表示
fmt.Printf("%T\n", t)  // 型名を表示
~~~

#### 実行結果

~~~
2017-09-12 21:23:23.7770078 +0900 JST m=+0.003000200
time.Time
~~~


### 日時を指定して作成する

[time.Date 関数](https://golang.org/pkg/time/#Date)を使用して、指定した日時の `time.Time` を作成することができます。
パラメータには、年、月、日、時、分、秒、ナノ秒、Location を指定します。

~~~ go
t := time.Date(2017, 9, 12, 23, 0, 0, 0, time.Local)
fmt.Println(t)  // "2017-09-12 23:00:00 +0900 JST"
~~~

Location には、ローカルタイムで設定することを表す `time.Local` や、協定世界時で設定することを表す `time.UTC` を指定することができます。


### 文字列表現から作成する

[time.Parse 関数](https://golang.org/pkg/time/#Parse) を使用すると、日時を表す文字列から `time.Time` オブジェクトを作成することができます。
第一パラメータに、日時文字列の形式を表す layout string、第二パラメータに、実際に変換する文字列を渡します。

~~~ go
const layout = "2006-01-02"
t, err := time.Parse(layout, "2017-09-27")
if err != nil {
	panic(err)
}
fmt.Println(t)  // 2017-09-27 00:00:00 +0000 UTC
~~~

layout string には、下記のようにいろいろなパターンを指定することができます。

~~~ go
const layout = "Jan 2, 2006 at 3:04pm (MST)"
t, _ := time.Parse(layout, "Feb 3, 2013 at 7:54pm (PST)")
fmt.Println(t)  // 2013-02-03 19:54:00 +0000 PST
~~~

指定しなかったフィールドは、0 で初期化されます。
例えば、下記のように日付だけを指定した場合は、０時０分０秒になります。

~~~ go
const layout = "2006-Jan-02"
t, _ := time.Parse(layout, "2013-Feb-03")
fmt.Println(t)  // 2013-02-03 00:00:00 +0000 UTC
~~~

layout string 中に `JST` などのタイムゾーンを示す文字列を指定しない場合は、`time.Parse` が生成する時刻データは世界協定時 `UTC` の時刻が指定されたものとして扱われます。
ローカルタイムの時刻を指定して `time.Time` を生成したい場合は、次のように `time.ParseInLocation` 関数を使用します。

~~~ go
const layout = "2006-01-02 15:04:05"
t, err := time.ParseInLocation(layout, "2017-01-01 00:00:00", time.Local)
if err != nil {
	panic(err)
}
fmt.Println(t)  // 2017-01-01 00:00:00 +0900 JST
~~~


layout string
----

`time.Parse` 関数を使用して文字列から `time.Time` を作成するときや、`Format` メソッドで `time.Time` を文字列表現に変換するときには、どのような形式の文字列として扱うかを示す **layout string** を指定する必要があります。

layout string に指定する文字列は、**2006年1月2日 15時4分5秒 (MST)** の日時を表すパーツを組み合わせて指定する必要があります（Go 言語の time パッケージの決まりです）。
この日時を扱うのは、次のように 1 ～ 7 の数字が並べられることが理由のようです。
詳しくは[公式ドキュメント](https://golang.org/pkg/time/#pkg-constants)を参照してください。

~~~
01/02 03:04:05PM '06 -0700
~~~

例えば、次のような文字列を layout string として使用することができます。

- `Mon Jan 2 15:04:05 -0700 MST 2006`
- `2006-01-02`
- `2006/01/02`
- `2006-Jan-02`
- `2006-01-02 15:04:05`
- `2006-01-02T15:04:05`
- `2006-01-02 (Mon) 15:04:05`
- `Jan 2, 2006 at 3:04pm (MST)`

`time` パッケージには、下記のような layout string があらかじめ定義されています。

~~~ go
const (
	ANSIC       = "Mon Jan _2 15:04:05 2006"
	UnixDate    = "Mon Jan _2 15:04:05 MST 2006"
	RubyDate    = "Mon Jan 02 15:04:05 -0700 2006"
	RFC822      = "02 Jan 06 15:04 MST"
	RFC822Z     = "02 Jan 06 15:04 -0700" // RFC822 with numeric zone
	RFC850      = "Monday, 02-Jan-06 15:04:05 MST"
	RFC1123     = "Mon, 02 Jan 2006 15:04:05 MST"
	RFC1123Z    = "Mon, 02 Jan 2006 15:04:05 -0700" // RFC1123 with numeric zone
	RFC3339     = "2006-01-02T15:04:05Z07:00"
	RFC3339Nano = "2006-01-02T15:04:05.999999999Z07:00"
	Kitchen     = "3:04PM"
	// Handy time stamps.
	Stamp      = "Jan _2 15:04:05"
	StampMilli = "Jan _2 15:04:05.000"
	StampMicro = "Jan _2 15:04:05.000000"
	StampNano  = "Jan _2 15:04:05.000000000"
)
~~~


時刻情報を参照する
----

### 年、月、日、曜日、時、分、秒を取得する

`time.Time` 構造体には、時分秒などの各パートの情報を切り出して取得するためのメソッドが用意されています。

~~~ go
t := time.Now()
fmt.Printf("%d\n", t.Year())    // 2017（年）
fmt.Printf("%d\n", t.Month())   // 9（月）
fmt.Printf("%d\n", t.Day())     // 12（日）
fmt.Printf("%d\n", t.YearDay()) // 255（年内通算日数）
fmt.Printf("%d\n", t.Weekday()) // 2（曜日: time.Sunday==0）
fmt.Printf("%d\n", t.Hour())    // 21（時）
fmt.Printf("%d\n", t.Minute())  // 37（分）
fmt.Printf("%d\n", t.Second())  // 11（秒）
~~~

月や曜日は単純な `int` ではなく、`time.Month` と `time.Weekday` という型で定義されており、それぞれの月や曜日を表す定数も定義されています（`time.January` や `time.Sunday`）。

- [time.Month 型](https://golang.org/pkg/time/#Month)
- [time.Weekday 型](https://golang.org/pkg/time/#Weekday)

上記の型には `String()` メソッドが実装されているため、次のようにして文字列表現の月、曜日を取得することができます。

~~~ go
t := time.Now()
month := t.Month().String()      //=> "September"
weekday := t.Weekday().String()  //=> "Tuesday"
~~~

もちろん、`Printf` 系メソッドのフォーマットで `%s` を指定して文字列表現で出力することもできます。

~~~ go
t := time.Now()
fmt.Printf("%s\n", t.Month())    //=> "September"
fmt.Printf("%s\n", t.Weekday())  //=> "Tuesday"
~~~

### 年月日データのみ、時分秒データのみを取得する

パラメータなしの `Date()` メソッドは、年、月、日の情報を戻り値として返します。
同様に、`Clock()` メソッドは、時、分、秒の情報を戻り値として返します。

~~~ go
t := time.Now()
year, month, day := t.Date()
hour, minute, second := t.Clock()
~~~


日時を文字列表現で取得する
----

[time.Format 関数](https://golang.org/pkg/time/#Time.Format) を使用すると、`time.Time` オブジェクトを任意のフォーマットの文字列に変換することができます。
`Format` 関数には、取得する文字列の形式を表す layout string を指定します。

#### sample.go

~~~ go
package main

import "fmt"
import "time"

func main() {
	const layout = "2006-01-02 Mon 15:04:05 (MST)"
	t := time.Now()
	fmt.Println(t.Format(layout))
}
~~~

#### 実行結果

~~~
2017-09-12 Tue 23:30:58 (JST)
~~~

ちなみに、`time.Time` 構造体の `String()` 関数は下記のようなフォーマットで、文字列を返すように実装されています。

~~~
2006-01-02 15:04:05.999999999 -0700 MST
~~~

