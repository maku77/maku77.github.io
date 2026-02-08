---
title: "JavaScriptメモ: 他の国のローカルタイム（現地時刻）を文字列形式で取得する"
url: "p/q6c5kzi/"
date: "2019-02-21"
tags: ["javascript"]
aliases: [/js/time/other-country-time.html]
---

タイムゾーンを指定して時刻文字列を取得する
----

`Date` クラスの下記のメソッドは、第 2 引数のオプションオブジェクトで、**`timeZone`** プロパティを指定できるようになっています (メソッド名は `toLocalXxx` ではなく、`toLocaleXxx` であることに注意してください）。


- [Date#toLocaleString メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleString): 日付＋時刻
- [Date#toLocaleDateString メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleDateString): 日付のみ
- [Date#toLocaleTimeString メソッド](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Date/toLocaleDateString): 時刻のみ

下記の例では、ユーザ環境、ロンドン、米国ニューヨークのローカルタイムを出力しています。

{{< code lang="js" title="ユーザ環境、ロンドン、ニューヨークの現地時刻を出力する" >}}
var date = new Date();
console.log(date.toLocaleString());
console.log(date.toLocaleString('en-US', { timeZone: 'America/New_York' }));
console.log(date.toLocaleString('en-GB', { timeZone: 'Europe/London' }));
{{< /code >}}

{{< code title="出力結果" >}}
2019/2/21 18:31:08
2/21/2019, 4:31:08 AM
21/02/2019, 09:31:08
{{< /code >}}

第 1 引数はロケール（簡単に言うと使用する言語）の設定であり、ここはタイムゾーン（時差）の設定とは関係がないことに注意してください。
`America/New_York` といったタイムゾーンの設定は、あくまで第 2 引数で渡すオブジェクトの、`timeZone` プロパティで指定します。

ちなみに、第 2 引数のオプションオブジェクトでは、`timeZone` プロパティ以外にもいろいろな設定を行えるようになっています。
例えば下記のようなプロパティがあります。

- `hour12`: `true` - 12 時間表記。`false` - 24 時間表記。
- `year`: 年の表現。"numeric", "2-digit" から選択。
- `month`: 月の表現。"numeric", "2-digit", "narrow", "short", "long" から選択。
- `day`: 日の表現。"numeric", "2-digit" から選択。
- `hour`: 時間の表現。"numeric", "2-digit" から選択。
- `minute`: 分の表現。"numeric", "2-digit" から選択。
- `second`: 秒の表現。"numeric", "2-digit" から選択。


タイムゾーン名の一覧
----

指定できるタイムゾーン名は、[IANA time zone database](https://www.iana.org/time-zones) の定義によるとされていますが、下記の Wikipedia ページで手っ取り早く調べることができます。

- [List of tz database time zones - Wikipedia](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)


おまけ：いろんなタイムゾーンの日時を表示してみる
----

下記は、いろんな国（タイムゾーン）の現地時間を `Date#toLocaleString` で表示するサンプルです。

```javascript
var timeZones = [
  'Pacific/Auckland',  // オークランド（ニュージーランド）
  'Asia/Tokyo',  // 東京（日本）
  'Asia/Singapore',  // シンガポール
  'Asia/Shanghai',  // 上海
  'Asia/Hong_Kong',  // 香港
  'Asia/Calcutta',  // カルカッタ（インド）
  'Asia/Bahrain',  // バーレーン
  'Africa/Johannesburg',  // ヨハネスブルグ（南アフリカ）
  'Europe/Berlin',  // ベルリン（ドイツ）
  'Europe/Zurich',  // チューリッヒ（スイス）
  'Europe/London',  // ロンドン（イギリス）
  'America/New_York',  // ニューヨーク（アメリカ）
  'America/Toronto',  // トロント（カナダ）
];

var date = new Date();
for (var i = 0; i < timeZones.length; ++i) {
  var tz = timeZones[i];
  var options = { timeZone: tz, hour12: false };
  console.log(date.toLocaleString('en-US', options) + ' ' + tz);
}
```

{{< code title="出力結果" >}}
2/21/2019, 23:12:16 Pacific/Auckland
2/21/2019, 19:12:16 Asia/Tokyo
2/21/2019, 18:12:16 Asia/Singapore
2/21/2019, 18:12:16 Asia/Shanghai
2/21/2019, 18:12:16 Asia/Hong_Kong
2/21/2019, 15:42:16 Asia/Calcutta
2/21/2019, 13:12:16 Asia/Bahrain
2/21/2019, 12:12:16 Africa/Johannesburg
2/21/2019, 11:12:16 Europe/Berlin
2/21/2019, 11:12:16 Europe/Zurich
2/21/2019, 10:12:16 Europe/London
2/21/2019, 05:12:16 America/New_York
2/21/2019, 05:12:16 America/Toronto
{{< /code >}}
