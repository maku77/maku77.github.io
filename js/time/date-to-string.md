---
title: "Date オブジェクトをいろいろな文字列表現に変換する"
date: "2018-04-03"
---

toXxxString() 系のメソッド
----

`Date` オブジェクトの `toXxxString()` 系のメソッドを使用すると、保持している日時情報をいろいろな書式の文字列で取得することができます。
大きく分けて、

* 日付＋時刻
* 日付のみ
* 時刻のみ

の文字列に変換するメソッドに分類できます。
下記はそれぞれのメソッドを呼び出したときの結果の例です。

#### 日付＋時刻

| メソッド | 結果の例 | 説明 |
| ---- | ---- | ---- |
| `date.toString()` | `Tue Apr 03 2018 21:30:55 GMT+0900 (東京 (標準時))` | デフォルトの実装で人間が読みやすい形式の文字列に変換 |
| `date.toISOString()` | `2018-04-03T12:30:55.000Z` | 日付を ISO 8601 Extended Format に準じた文字列に変換 |
| `date.toUTCString()` | `Tue, 03 Apr 2018 12:30:55 GMT` | 日時を UTC タイムゾーンを使用する文字列に変換 |
| `date.toLocaleString()` | `2018-4-3 21:30:55` | 日付を地域の日付書式に従った文字列に変換（現在のロケール） |
| `date.toLocaleString('en-US')` | `4/3/2018, 9:30:55 PM` | 日付を地域の日付書式に従った文字列に変換（アメリカ英語） |

#### 日付のみ

| メソッド | 結果の例 | 説明 |
| ---- | ---- | ---- |
| `date.toDateString()` | `Tue Apr 03 2018` | 「日付」部を、デフォルトの実装で人間が読みやすい形式の文字列に変換 |
| `date.toLocaleDateString()` | `2018-4-3` | 「日付」部を、地域の日付書式に従った文字列に変換（現在のロケール） |
| `date.toLocaleDateString('en-US')` | `4/3/2018` | 「日付」部を、地域の日付書式に従った文字列に変換（アメリカ英語）|

#### 時刻のみ

| メソッド | 結果の例 | 説明 |
| ---- | ---- | ---- |
| `date.toTimeString()` | `21:30:55 GMT+0900 (東京 (標準時))` | 「時刻」部を、デフォルトの実装で人間が読みやすい形式の文字列に変換 |
| `date.toLocaleTimeString()` | `21:30:55` | 「時刻」部を、地域の日付書式に従った文字列に変換（現在のロケール） |
| `date.toLocaleTimeString('en-US')` | `9:30:55 PM` | 「時刻」部を、地域の日付書式に従った文字列に変換（アメリカ英語） |


下記は上記の結果を調べるためのサンプルコードです。

~~~ javascript
var d = new Date(2018, 3, 3, 21, 30, 55);

// 日付＋時刻
console.log(d.toString());  // Tue Apr 03 2018 21:30:55 GMT+0900 (日本標準時)
console.log(d.toISOString());  // 2018-04-03T12:30:55.000Z
console.log(d.toUTCString());  // Tue, 03 Apr 2018 12:30:55 GMT
console.log(d.toLocaleString());  // 2018/4/3 21:30:55
console.log(d.toLocaleString('en-US'));  // 4/3/2018, 9:30:55 PM

// 日付のみ
console.log(d.toDateString());  // Tue Apr 03 2018
console.log(d.toLocaleDateString());  // 2018/4/3
console.log(d.toLocaleDateString('en-US'));  // 4/3/2018

// 時刻のみ
console.log(d.toTimeString());  // 21:30:55 GMT+0900 (日本標準時)
console.log(d.toLocaleTimeString());  // 21:30:55
console.log(d.toLocaleTimeString('en-US'));  // 9:30:55 PM
~~~


YYYY-MM-DD の文字列で取得する
----

### ISO 8601 形式の日時文字列を取得する

[Date クラスの toISOString() メソッド](https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Date/toISOString)を使用すると、ISO 8601 Extended Format (`YYYY-MM-DDTHH:mm:ss.sssZ`) 形式の文字列を取得することができます（得られるのは UTC の日時になります）。

~~~
2012-10-04T12:52:00.889Z
~~~

上記の文字列を、`T` の文字で分割すれば、日付 (`YYYY-MM-DD`) 部分だけを簡単に取得できます。

~~~ javascript
var now = new Date();
var text = now.toISOString().split('T')[0];
alert(text);  //=> "2012-10-04"
~~~


### 自力で日時文字列を構成する

次の `getTimeStr` 関数は、Date オブジェクトの年、月、日などの各フィールドの値を利用して、自力で `YYYYMMDDThh:mm:ssZ` という形式の日時文字列を作成しています。
時刻などを2ケタ表示に揃えるため、先頭に `0` という文字をひっつけておいてから、末尾の2文字を取り出すという小技を使っています。

~~~ javascript
function getTimeStr() {
    var d = new Date();
    var yyyy = d.getUTCFullYear();
    var MM = ('0' + (d.getUTCMonth() + 1)).slice(-2);
    var dd = ('0' + d.getUTCDate()).slice(-2);
    var hh = ('0' + d.getUTCHours()).slice(-2);
    var mm = ('0' + d.getUTCMinutes()).slice(-2);
    var ss = ('0' + d.getUTCSeconds()).slice(-2);
    return yyyy + '-' + MM + '-' + dd + 'T' + hh + ':' + mm + ':' + ss + 'Z';
}
~~~

#### 実行結果

~~~
2013-11-04T11:42:29Z
~~~

