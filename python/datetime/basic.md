---
title: "Python で日時（日付／時刻）の情報を扱う (datetime, date, time, timedelta)"
date: "2021-01-13"
---

日時を表すデータ型
----

Python で日時を扱うには [datetime モジュール](https://docs.python.org/ja/3/library/datetime.html) を使用します。
`datetime` には次のようなデータ型が定義されています。

| データ型 | 保持する情報 |
| ---- | ---- |
| `datetime.datetime` | 日付＋時刻 |
| `datetime.date` | 日付 |
| `datetime.time` | 時刻 |
| `datetime.timedelta` | 時間差（マイクロ秒） |

すべてのオブジェクトは immutable（不変）で、ハッシュのキーとして使用できます。


日時を表す datetime オブジェクト
----

[datetime.datetime オブジェクト](https://docs.python.org/ja/3/library/datetime.html#datetime-objects) は、日付と時刻の両方の情報を保持します。
`datetime.date` と `datetime.time` の情報を合わせたものです。
以下、`datetime.datetime` オブジェクトを `datetime` オブジェクトと記述します。

### 現在の日時を表す datetime オブジェクト

`datetime.now()` で、ローカルな現在日時を表す `datetime` オブジェクトを取得できます。
`datetime` オブジェクトには、年月日時分秒などの各フィールドの値を取り出す属性が定義されています。

```python
import datetime from datetime

dt = datetime.now()
print(dt)  # 2021-01-13 20:10:58.598240
print(dt.year)        # 2021
print(dt.month)       # 1
print(dt.day)         # 13
print(dt.hour)        # 20
print(dt.minute)      # 10
print(dt.second)      # 58
print(dt.microsecond) # 598240
print(dt.tzinfo)      # None
```

### タイムゾーン (Aware/Naive)

`datetime` オブジェクトはタイムゾーン情報（`tzinfo` 属性）を持つことができ、この情報が存在するとき（`tzinfo` が `None` でないとき）、そのオブジェクトは __Aware__ であるといいます。
逆に、タイムゾーン情報を持たないとき（`tzinfo` が `None` のとき）、そのオブジェクトは __Naive__ であるといいます。

アプリケーション内で、タイムゾーンを意識した時刻情報を扱いたい場合は、Aware な `datetime` オブジェクトを生成する必要があります。

下記の `datetime` クラスメソッドはすべて現在日時を表す `datetime` オブジェクトを生成しますが、内部的に保持するタイムゾーン情報に違いがあります。

| 現在日時の取得方法 | 意味 |
| ---- | ---- |
| `datetime.now()` | Naive なローカル日時 (`tzinfo == None`) |
| `datetime.utcnow()` | Naive な UTC 日時 (`tzinfo == None`) |
| `datetime.now().astimezone()` | Aware なローカル日時 (`tzinfo == システム設定`) |
| `datetime.now(timezone.utc)` | Aware な UTC 日時 (`tzinfo == timezone.utc`) |

下記のサンプルコードを実行すると、これらの違いを調べることができます。

```python
from datetime import datetime, timezone

dt_local_naive = datetime.now()
print(dt_local_naive.isoformat())  # 2021-01-13T20:46:50.587950
print(dt_local_naive.tzinfo)       # None

dt_utc_naive = datetime.utcnow()
print(dt_utc_naive.isoformat())    # 2021-01-13T11:46:50.587950
print(dt_utc_naive.tzinfo)         # None

dt_local_aware = datetime.now().astimezone()
print(dt_local_aware.isoformat())  # 2021-01-13T20:46:50.587950+09:00
print(dt_local_aware.tzinfo)       # JST

dt_utc_aware = datetime.now(timezone.utc)
print(dt_utc_aware.isoformat())    # 2021-01-13T11:46:50.587950+00:00
print(dt_utc_aware.tzinfo)         # UTC
```


日付を表す date オブジェクト
----

[datetime.date オブジェクト](https://docs.python.org/ja/3/library/datetime.html#date-objects) は、日付（年月日）の情報を保持します。
タイムゾーン情報（Aware/Naive）は意識しません。

```python
from datetime import date

dt = date.today()
print(dt.isoformat())  # 2021-01-13
print(dt.year)         # 2021
print(dt.month)        # 1
print(dt.day)          # 13
```


時刻を表す time オブジェクト
----

[datetime.time オブジェクト](https://docs.python.org/ja/3/library/datetime.html#time-objects) は、時刻（時分秒）の情報を保持します。
`datetime` オブジェクトと同様、`tzinfo` 属性でタイムゾーン情報を扱うため、Aware/Naive の区別があります。

```python
from datetime import datetime, time, timezone

# t = time(hour=20, minute=30, second=55, tzinfo=timezone.utc)
t = datetime.now().astimezone().timetz()

print(t.isoformat())  # 20:30:55.364310+09:00
print(t.hour)         # 20
print(t.minute)       # 30
print(t.second)       # 55
print(t.microsecond)  # 0
print(t.tzinfo)       # JST
```


時間差を表す timedelta オブジェクト
----

[datetime.timedelta オブジェクト](https://docs.python.org/ja/3/library/datetime.html#timedelta-objects) は、2 つの日時の差分を表します。

`datetime`、`date`、`time` すべてのオブジェクトに対して、`timedelta` オブジェクトを加算、減算することができます。
次の例では、`datetime` オブジェクトに対して、1 日の時間差を表す `timedelta` オブジェクトを使って演算処理しています。
`timedelta` の属性名は、`days`、`hours`、`seconds` のように複数形になることに注意してください。

```python
from datetime import datetime, timedelta

dt = datetime(year=2021, month=1, day=1, hour=15, minute=30, second=0)
delta = timedelta(days=1)

print(dt)              # 2021-01-01 15:30:00
print(dt + delta)      # 2021-01-02 15:30:00
print(dt + delta * 2)  # 2021-01-03 15:30:00
print(dt - delta)      # 2020-12-31 15:30:00
print(dt - delta * 2)  # 2020-12-30 15:30:00
```

日時を表すオブジェクト (`datetime` / `date` / `time`) 同士で引き算すると、その時間差を表す `timedelta` オブジェクトを取得することができます。

```python
from datetime import date, timedelta

d1 = date(year=2021, month=1, day=1)
d2 = date(year=2021, month=1, day=20)
delta = d2 - d1

print(delta)       # 19 days, 0:00:00
print(delta.days)  # 19
print(delta.total_seconds())  # 1641600.0
```

あと、時間差ではないですが、等号・不等号で 2 つの日時データの大小を比較することができます。

```python
from datetime import datetime

dt1 = datetime(year=2000, month=1, day=1)
dt2 = datetime(year=2000, month=1, day=2)
print(dt1 > dt2)  # False
print(dt1 < dt2)  # True
```

これを利用して、ある日時の範囲を次のようにループ処理したりできます。

```python
from datetime import date, timedelta

START_DATE = date(year=2000, month=1, day=1)
END_DATE = date(year=2000, month=1, day=3)
STEP = timedelta(days=1)

d = START_DATE
while (d <= END_DATE):
    print(d)
    d += STEP
```

```
2000-01-01
2000-01-02
2000-01-03
```


各フィールドの値を変更する (replace)
----

`datetime`、`date`、`time` オブジェクトの `replace` メソッドを使用すると、年月日時分秒などの各フィールドの値を個別に変更することができます。
`datetime`、`time` オブジェクトに関しては、`tzinfo` フィールドの値を変更することで、Naive と Aware を変換することも可能です。

```python
from datetime import date, datetime, time

# 現在時刻から datetime, date, time オブジェクトを作成
dt = datetime.now().astimezone()
d = dt.date()
t = dt.timetz()

# datetime の内容を変更する
print(dt)                              # 2021-01-13 18:23:46.827369+09:00
print(dt.replace(year=1970, hour=21))  # 1970-01-13 21:23:46.827369+09:00

# date の内容を変更する
print(d)                           # 2021-01-13
print(d.replace(month=12, day=4))  # 2021-12-04

# time の内容を変更する
print(t)                       # 18:23:46.827369+09:00
print(t.replace(hour=7))       # 07:23:46.827369+09:00
print(t.replace(tzinfo=None))  # 18:23:46.827369
```


日時系オブジェクトの変換方法一覧
----

| 変換内容 | コード |
| ---- | ---- |
| `date` → ISO文字列 | [d.isoformat()](https://docs.python.org/ja/3/library/datetime.html#datetime.date.isoformat) |
| `date` → 指定形式文字列 | [d.strftime(format)](https://docs.python.org/ja/3/library/datetime.html#datetime.date.strftime) |
| `time` → ISO文字列 | [t.isoformat()](https://docs.python.org/ja/3/library/datetime.html#datetime.time.isoformat) |
| `time` → 指定形式文字列 | [t.strftime(format)](https://docs.python.org/ja/3/library/datetime.html#datetime.time.strftime) |
| `datetime` → ISO文字列 | [dt.isoformat()](https://docs.python.org/ja/3/library/datetime.html#datetime.date.isoformat) |
| `datetime` → 指定形式文字列 | [dt.strftime(format)](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.strftime) |
| `datetime` (Naive) → `datetime` (Aware) | [dt.astimezone(tz=None)](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.astimezone) |
| `datetime` (Aware) → `datetime` (Naive) | [dt.replace(tzinfo=None)](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.replace) |
| `datetime` → `date` | [dt.date()](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.date) |
| `datetime` → `time` (Naive) | [dt.time()](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.time) |
| `datetime` → `time` (Aware) | [dt.timetz()](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.timetz) |
| `datetime` → POSIXタイムスタンプ | [dt.timestamp()](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.timestamp) |
| ISO文字列 → `date` | [date.fromisoformat('2019-12-04')](https://docs.python.org/ja/3/library/datetime.html#datetime.date.fromisoformat) |
| ISO文字列 → `time` (Naive or Aware) | [time.fromisoformat('04:23:01')](https://docs.python.org/ja/3/library/datetime.html#datetime.time.fromisoformat) |
| ISO文字列 → `datetime` (Naive or Aware) | [datetime.fromisoformat('2011-11-04T00:05:23')](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.fromisoformat)
| POSIXタイムスタンプ → `date` | [date.fromtimestamp(timestamp)](https://docs.python.org/ja/3/library/datetime.html#datetime.date.fromtimestamp) |
| POSIXタイムスタンプ → `datetime` (Naive or Aware) | [datetime.fromtimestamp(timestamp, tz=None)](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.fromtimestamp) |
| POSIXタイムスタンプ → `datetime` (UTC Naive) | [datetime.utcfromtimestamp(timestamp)](https://docs.python.org/ja/3/library/datetime.html#datetime.datetime.utcfromtimestamp) |

