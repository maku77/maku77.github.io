---
title: "Kotlin で日時（日付／時刻）を扱う方法いろいろ"
date: "2020-12-04"
lastmod: "2020-12-21"
---

日時を表すクラス
----

日付や時刻を表現するクラスとしては、`java.time` パッケージの次のようなクラスを使用できます。

| クラス名 | 表現する情報 | ISO-8601 表現 |
| ---- | ---- |
| `LocalDate` | 日付（年-月-日） | `2020-12-04` |
| `LocalTime` | 時刻（時-分-秒） | `20:26:47.549110600` |
| `LocalDateTime` | 日付＆時刻（年-月-日-時-分-秒） | `2020-12-04T20:26:47.549110600` |
| `ZonedDateTime` | 日付＆時刻（年-月-日-時-分-秒）＋ タイムゾーン | `2020-12-04T20:26:47.549110600+09:00[Asia/Tokyo]` |

時刻はナノ秒の精度まで保持可能ですが、実際にどの精度までの値が格納されているかはオブジェクトの生成方法に依存します。

### 特徴: Local 系と Zoned 系がある

日時を表すクラスは、大きく `LocalXxx` 系と `ZonedXxx` に分かれています。

`LocalDateTime` は単純な日時情報（年-月-日-時-分-秒）を保持するためのクラスで、内部にタイムゾーン情報を保持していません。
つまり、一度 `2020-02-15T21:30:50` という情報を持つオブジェクトを生成したら、そのオブジェクトはどの国で使っても `2020-02-15T21:30:50` という情報として扱うということです（時差の概念は持たない）。

一方で、`ZonedDateTime` は、`JST` などのタイムゾーン情報 (`ZoneId`) を内部に保持しているため、地域ごとの時差を表現できます。
日本であれば UTC 時刻＋9時間なので、`2020-02-15T21:30:50+09:00` といった情報を保持することになります。

つまり、`ZonedDateTime = LocalDateTime + ZoneId` なので、`LocalDateTime` オブジェクトに `ZoneId` オブジェクトを付加してやれば、`ZonedDateTime` オブジェクトを生成することができます。
具体的には、`ZonedDateTime.of()` か `LocalDateTime#atZone()`を使用します。

```kotlin
val localDateTime = LocalDateTime.now()
val zonedDateTime1 = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
val zonedDateTime2 = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Tokyo"))
val zonedDateTime3 = ZonedDateTime.of(localDateTime, ZoneId.of("America/Los_Angeles"))

println(localDateTime)   //=> 2020-12-04T20:32:26.214124900
println(zonedDateTime1)  //=> 2020-12-04T20:32:26.214124900+09:00[Asia/Tokyo]
println(zonedDateTime2)  //=> 2020-12-04T20:32:26.214124900+09:00[Asia/Tokyo]
println(zonedDateTime3)  //=> 2020-12-04T20:32:26.214124900-08:00[America/Los_Angeles]
```

生成された `ZonedDateTime` オブジェクトにはタイムゾーン情報が付加されていますが、時-分-秒の情報は変化していないことに注意してください。

### 特徴: 不変オブジェクト (immutable) である

日時を表すクラスはすべて不変 (immutable) であり、スレッドセーフに利用することができます。
既存の日時オブジェクトの一部を変更して新しい日時を表現したいときは、変換メソッドの戻り値で新しいオブジェクトとして受け取ります。
例えば、現在の時刻 (`LocalTime`) から 12 時間後の時刻を取得したい場合は、`plusHours` メソッドの戻り値で新しい `LocalTime` オブジェクトを受け取ります。

```kotlin
val time1: LocalTime = LocalTime.now()
val time2: LocalTime = time1.plusHours(12)
println(time1)  //=> 19:44:25.216369900
println(time2)  //=> 07:44:25.216369900
```

変換元の `LocalTime` オブジェクトの値は変化していないところがポイントです。


日時オブジェクトの生成方法いろいろ
----

### 現在のシステム時刻から生成

```kotlin
val date = LocalDate.now()     //=> 2020-12-04
val time = LocalTime.now()     //=> 20:39:30.973274100
val ldt = LocalDateTime.now()  //=> 2020-12-04T20:39:30.973274100
val zdt = ZonedDateTime.now()  //=> 2020-12-04T20:39:30.973274100+09:00[Asia/Tokyo]
```

`ZonedDateTime.now()` は、デフォルトで `ZoneId.systemDefault()` のタイムゾーン情報を使用します。

### 年-月-日-時-分-秒を指定して生成

```kotlin
// 2020年2月15日
val date1 = LocalDate.parse("2020-02-15")
val date2 = LocalDate.of(2020, 2, 15)
val date3 = LocalDate.ofYearDay(2020, 46) // 2020年の46日目

// 21時30分50秒
val time1 = LocalTime.parse("21:30:50")
val time2 = LocalTime.of(21, 30, 50)
val time3 = LocalTime.ofSecondOfDay(77450) // 00:00:00から77450秒後

// 2020年2月15日21時30分50秒（タイムゾーン情報なし）
val dateTime1 = LocalDateTime.parse("2020-02-15T21:30:50")
val dateTime2 = LocalDateTime.of(2020, 2, 15, 21, 30, 50)
val dateTime3 = LocalDateTime.of(date1, time2) // LocalDate + LocalTime

// 2020年2月15日21時30分50秒（タイムゾーン情報あり）
val zoned1 = ZonedDateTime.parse("2020-02-15T21:30:50+09:00[Asia/Tokyo]")
val zoned2 = ZonedDateTime.of(dateTime1, ZoneId.of("Asia/Tokyo"))  // LocalDateTime + ZoneId
```


日時オブジェクトから各フィールドの情報を取り出す
----

```kotlin
val dt = LocalDateTime.parse("2020-02-15T21:30:50")
```

上記のように生成した `LocalDateTime` オブジェクトからは、次のようなプロパティで各フィールドの値を取得できます。

| メソッド | 結果 | 意味 |
| ---- | ---- | ---- |
| `dt.year` | `2020` | 年 |
| `dt.monthValue` | `2` | 月 |
| `dt.dayOfMonth` | `15` | 日 |
| `dt.hour` | `21` | 時 |
| `dt.minute` | `30` | 分 |
| `dt.second` | `50` | 秒 |
| `dt.month` | `Month.FEBRUARY` | 月 (enum) |
| `dt.dayOfWeek` | `DayOfWeek.SATURDAY` | 曜日 (enum) |
| `dt.dayOfYear` | `46` | 年始からの日数 |


日時オブジェクトの演算
----

日時オブジェクトには、年月日時分秒の各フィールドの演算を行うためのメソッドが用意されています。
演算結果は新しい日時オブジェクトとして戻り値で返されます。
次の例では、10年後の日時を求めています。

```kotlin
val dt1 = LocalDateTime.parse("2020-02-15T21:30:50")
val dt2 = dt1.plusYears(10)  //=> 2030-02-15T21:30:50
```

他にもいろいろな演算方法があります。

| メソッド | 結果 | 意味 |
| ---- | ---- | ---- |
| `dt.plusYears(1)` | `2021-02-15T21:30:50` | 1年後 |
| `dt.plusMonths(1)` | `2020-03-15T21:30:50` | 1ヵ月後 |
| `dt.plusDays(1)` | `2020-02-16T21:30:50` | 1日後 |
| `dt.plusHours(1)` | `2020-02-15T22:30:50` | 1時間後 |
| `dt.plusMinutes(1)` | `2020-02-15T21:31:50` | 1分後 |
| `dt.plusSeconds(1)` | `2020-02-15T21:30:51` | 1秒後 |
| `dt.withYear(2000)` | `2000-02-15T21:30:50` | 2000年に書き換え |
| `dt.withMonth(7)` | `2020-07-15T21:30:50` | 7月に書き換え |
| `dt.withDayOfMonth(10)` | `2020-02-10T21:30:50` | 10日に書き換え |
| `dt.withHour(18)` | `2020-02-15T18:30:50` | 18時に書き換え |
| `dt.withMinute(45)` | `2020-02-15T21:45:50` | 45分に書き換え |
| `dt.withSecond(0)` | `2020-02-15T21:30:00` | 0秒に書き換え |

あるフィールド以下の値をすべて 0 にしたいときは、次のように `LocalDateTime#truncatedTo()` や `ZonedDateTime#truncatedTo()` を使用します。

```kotlin
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun main() {
    val dt = LocalDateTime.parse("2020-02-15T21:30:50")

    // 「日」以下のフィールドを切り捨て（その日の 0 時 0 分にする）
    val truncated1: LocalDateTime = dt.truncatedTo(ChronoUnit.DAYS)
    println(truncated1)  //=> 2020-02-15T00:00

    // 「時」以下のフィールドを切り捨て
    val truncated2: LocalDateTime = dt.truncatedTo(ChronoUnit.HOURS)
    println(truncated2)  //=> 2020-02-15T21:00

    // 「分」以下のフィールドを切り捨て
    val truncated3: LocalDateTime = dt.truncatedTo(ChronoUnit.MINUTES)
    println(truncated3)  //=> 2020-02-15T21:30
}
```


日時オブジェクトを文字列にフォーマットする
----

日付オブジェクトの `format` メソッドに、__`java.time.format.DateTimeFormatter`__ オブジェクトを渡すと、任意の形式の日時文字列に変換することができます。
独自のフォーマットを作成することもできますし、あらかじめ用意されている定数を使用することもできます。

```kotlin
val dt = ZonedDateTime.now()
println(dt)  //=> 2020-12-04T21:52:23.473136500+09:00[Asia/Tokyo]

// 日付のみ
println(dt.format(DateTimeFormatter.ISO_DATE))         //=> 2020-12-04+09:00
println(dt.format(DateTimeFormatter.ISO_OFFSET_DATE))  //=> 2020-12-04+09:00
println(dt.format(DateTimeFormatter.ISO_LOCAL_DATE))   //=> 2020-12-04

// 時刻のみ
println(dt.format(DateTimeFormatter.ISO_INSTANT))      //=> 2020-12-04T12:52:23.473136500Z
println(dt.format(DateTimeFormatter.ISO_TIME))         //=> 21:52:23.4731365+09:00
println(dt.format(DateTimeFormatter.ISO_OFFSET_TIME))  //=> 21:52:23.4731365+09:00
println(dt.format(DateTimeFormatter.ISO_LOCAL_TIME))   //=> 21:52:23.4731365

// 日付＆時刻
println(dt.format(DateTimeFormatter.ISO_DATE_TIME))         //=> 2020-12-04T21:52:23.4731365+09:00[Asia/Tokyo]
println(dt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME))   //=> 2020-12-04T21:52:23.4731365+09:00[Asia/Tokyo]
println(dt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))  //=> 2020-12-04T21:52:23.4731365+09:00
println(dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))   //=> 2020-12-04T21:52:23.4731365
println(dt.format(DateTimeFormatter.RFC_1123_DATE_TIME))    //=> Fri, 4 Dec 2020 21:52:23 +0900

// その他
println(dt.format(DateTimeFormatter.BASIC_ISO_DATE))    //=> 20201204+0900
println(dt.format(DateTimeFormatter.ISO_ORDINAL_DATE))  //=> 2020-339+09:00
println(dt.format(DateTimeFormatter.ISO_WEEK_DATE))     //=> 2020-W49-5+09:00
```

独自形式の `DateTimeFormatter` を作成するには次のようにします。

```kotlin
val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd (E) HH:mm:ss")
println(dt.format(formatter))  //=> 2020-12-04 (金) 21:52:23
```


その他
----

### エポックタイム（ミリ秒）←→ ZonedDateTime

`System.currentTimeMillis()` で取得したミリ秒から `ZonedDateTime` オブジェクトを生成するには次のようにします。
`Instant` クラスは、エポックタイム（1970年からの経過秒数）をナノ秒の単位で扱うためクラスです。

```kotlin
val epochMillis = System.currentTimeMillis()
val zonedDt = ZonedDateTime.ofInstant(
    Instant.ofEpochMilli(epochMillis),
    ZoneId.systemDefault()
)

println(epochMillis)  //=> 1607088295866
println(zonedDt)      //=> 2020-12-04T22:24:55.866+09:00[Asia/Tokyo]
```

逆に、`ZonedDateTime` からエポックタイムに変換するには `toEpochSecond()` が使えますが、`toEpochMillis()` はありません。
`toInstant()` で `Instant` オブジェクトを取得してから、`toEpochMilli()` を使えばミリ秒単位で取得できます。

```kotlin
val millis: Long = zonedDt.toInstant().toEpochMilli()
println(millis)  //=> 1607090443203

// 下記でも同じような値は取得できるけど、下3桁が 000 に丸められてしまう
val millis2: Long = zonedDt.toEpochSecond() * 1000
println(millis2)  //=> 1607090443000
```

### 時刻文字列 → エポックタイム（ミリ秒）

```kotlin
fun strTimeToMillis(timeStr: String): Long {
    return Instant.parse(timeStr).toEpochMilli()
    // return ZonedDateTime.parse(timeStr).toInstant().toEpochMilli()
}

val millis: Long = strTimeToMillis("2020-12-04T22:49:30Z")
```

### エポックタイム（ミリ秒）を 0 時 0 分の値に切り詰める

次のユーティリティクラス `TimeUtil` の `startTimeOfDay()` は、渡されたタイムスタンプを、その日の 0 時 0 分になるように切り詰めます。

```kotlin
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.format.DateTimeFormatter

object TimeUtil {
    /**
     * エポックタイム（ミリ秒）を切り詰めて「00時00分00秒」の値にします。
     */
    fun startTimeOfDay(epochMillis: Long): Long {
        val zoned = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault())
        return zoned.truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli()
    }

    /**
     * エポックタイム（ミリ秒）を切り詰めて「24時00分00秒」の値にします。
     */
    fun endTimeOfDay(epochMillis: Long): Long {
        val zoned = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault())
        return zoned.plusDays(1).truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli()
    }

    /**
     * タイムスタンプを分かりやすい形式で表示します（デバッグ用）。
     */
    fun prettyPrintTimeStamp(epochMillis: Long) {
        val zoned = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault())
        println(zoned.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

// 使用例
fun main() {
    val currentTime = System.currentTimeMillis()
    val startTimeOfDay = TimeUtil.startTimeOfDay(currentTime)
    val endTimeOfDay = TimeUtil.endTimeOfDay(currentTime)

    // 結果の確認
    println(currentTime)     //=> 1608624565893
    println(startTimeOfDay)  //=> 1608595200000
    println(endTimeOfDay)    //=> 1608681600000
    TimeUtil.prettyPrintTimeStamp(currentTime)     //=> 2020-12-22T08:09:25.893Z[UTC]
    TimeUtil.prettyPrintTimeStamp(startTimeOfDay)  //=> 2020-12-22T00:00:00Z[UTC]
    TimeUtil.prettyPrintTimeStamp(endTimeOfDay)    //=> 2020-12-23T00:00:00Z[UTC]
}
```

