---
title: DateTime クラスで時刻を扱う
created: 2012-07-31
---

DataTime オブジェクトとは
----

- 参考: [PHP: DateTime - Manual](http://www.php.net/manual/ja/class.datetime.php)

`DateTime` オブジェクトは日時を表すデータを保持しており、タイムスタンプへの変換や、日時の差分を求めたりすることができます。
`DateTime` オブジェクトはタイムゾーンを表す `DateTimeZone` オブジェクトを保持しているため、その地域のローカルタイム情報を取得することができます（`format()` メソッドを使って文字列形式で取得することができます）。


現在時刻の DateTime オブジェクトを作成する
----

`DateTime` のコンストラクタのパラメータで `now` を指定すれば、現在時刻を保持する `DateTime` オブジェクトを生成することができます。

~~~ php
$dt = new DateTime('now', new DateTimeZone('GMT'));  // タイムゾーンを GMT に設定
$dt = new DateTime('now', new DateTimeZone('Asia/Tokyo'));  // タイムゾーンを東京 (GMT+09:00) に設定
$dt = new DateTime('now');  // システムにタイムゾーンが設定されているならこれでも OK
~~~


文字列から DataTime オブジェクトを作成する
----

`YYYY-MM-DD hh:mm:ss` といった日時を表す文字列から、`DataTime` オブジェクトを生成することもできます。
第二引数でタイムゾーン (`DateTimeZone`) を指定することで、その日時がどの地域におけるローカルタイムなのかを示すことができます。

~~~ php
$dt = new DateTime('2001-01-01 20:00:00', new DateTimeZone('GMT'));  // GMT の時刻で初期化
$dt = new DateTime('2001-01-01 20:00:00', new DateTimeZone('Asia/Tokyo'));  // 東京 (GMT+09:00) の時刻で初期化
~~~


DateTime オブジェクトを文字列に変換する
----

`DateTime::format(string $format)` を使って、`DateTime` オブジェクトが保持する値を、任意の形式の文字列に変換することができます。

~~~ php
echo $dt->format('Y-m-d H:i:sP');    // 2000-01-01 15:30:00+09:00
~~~

ここで取得される文字列は、`DateTime` オブジェクトに設定したタイムゾーン (DateTimeZone) に基づいたローカルタイムになります。
GMT での時刻を取得したい場合は、以下のように `DateTime` オブジェクトのタイムゾーンを変更します。

~~~ php
$dt->setTimezone(new DateTimeZone('GMT'));
echo $dt->format('Y-m-d H:i:sP');    // 2000-01-01 06:30:00+00:00
~~~


応用例: 東京のローカルタイムを GMT に変換する
----

ローカルタイムの `DateTime` オブジェクトを作成した後で、`setTimezone()` によって別のタイムゾーンを設定することにより、異なるタイムゾーンにおける日時文字列を取得することができます。

下記のサンプルでは、東京時間における日時文字列を使って `DateTime` オブジェクトを作成し、GMT 時間の日時文字列に変換しています。
コンストラクタの第二引数で `Asia/Tokyo` タイムゾーンを明示的に指定していますが、実行する PC のタイムゾーンが適切に設定されていれば、このタイムゾーン指定は省略することができます。

#### サンプルコード

~~~ php
$date = new DateTime('2000-01-01 15:30:00', new DateTimeZone('Asia/Tokyo'));
echo 'Tokyo: ' . $date->format('Y-m-d H:i:sP') . "\n";

$date->setTimezone(new DateTimeZone('GMT'));
echo '  GMT: ' . $date->format('Y-m-d H:i:sP') . "\n";
~~~

#### 実行結果

~~~ php
Tokyo: 2000-01-01 15:30:00+09:00
  GMT: 2000-01-01 06:30:00+00:00
~~~

