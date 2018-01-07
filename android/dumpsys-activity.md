---
title: Activity の情報を表示する (dumpsys)
date: "2011-06-08"
---

**dumpsys** コマンドで、Activity core サービスの情報を表示できます。
例えば、Activity スタックなどが表示されるので、最前面に表示されているアプリの正体などをすぐに調べることができます。

`dumpsys` コマンドをパラメータなしで実行すると、大量のメッセージが表示されてしまうので、`activity` パラメータを指定することで、Activity に関する情報だけに絞り込みます。


#### 端末上で実行する場合（adb shell でログイン後）
```
# dumpsys activity
```

#### adb 経由で実行する場合
```
$ adb shell dumpsys activity
```

例えば、以下のようにすれば、Stop 状態のアクティビティ含め、現在起動中のアクティビティの一覧を簡単に調べることができます（ここでは Windows の `findstr` コマンドを使用しています）。

```
C:\> adb shell dumpsys activity activities | findstr /c:"* TaskRecord"
    * TaskRecord{2758379e #1 A=com.google.android.leanbacklauncher U=0 sz=1}
    * TaskRecord{18c788c2 #7 A=com.example.myapp1 U=0 sz=1}
    * TaskRecord{3cffb420 #4 A=com.example.myapp2 U=0 sz=1}
```

