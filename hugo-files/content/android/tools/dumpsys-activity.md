---
title: "Activity の情報を表示する (dumpsys activity)"
url: "p/mbnw7gq/"
date: "2011-06-08"
tags: ["Android"]
aliases: /android/dumpsys-activity.html
---

Android 端末に `adb shell` で接続後、__`dumpsys`__ コマンドを実行すると Activity core サービスの情報を表示できます。
例えば、出力の Activity スタックを確認することで、最前面に表示されているアプリの正体を調べることができます。
`dumpsys` コマンドをパラメータなしで実行すると、大量のメッセージが表示されてしまうので、__`activity`__ パラメータを指定して Activity に関する情報だけに絞り込むと見やすくなります。

{{< code title="端末上で実行する場合（adb shell でログイン後）" >}}
# dumpsys activity
{{< /code >}}

{{< code lang="console" title="adb 経由で実行する場合" >}}
$ adb shell dumpsys activity
{{< /code >}}

例えば、以下のようにすれば、Stop 状態のアクティビティ含め、現在起動中のアクティビティの一覧を簡単に調べることができます（ここでは Windows の `findstr` コマンドを使用しています）。

```bat
C:\> adb shell dumpsys activity activities | findstr /c:"* TaskRecord"
    * TaskRecord{2758379e #1 A=com.google.android.leanbacklauncher U=0 sz=1}
    * TaskRecord{18c788c2 #7 A=com.example.myapp1 U=0 sz=1}
    * TaskRecord{3cffb420 #4 A=com.example.myapp2 U=0 sz=1}
```

