---
title: "現在の日付を YYYY-MM-DD の文字列で取得する"
date: "2012-10-04"
---

ISO 8601 形式の日時文字列を取得する
----

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


任意の形式の日時文字列を取得する
----

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

