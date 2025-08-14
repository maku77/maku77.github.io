---
title: "Androidベンダー向けメモ: インストールされた各 APK パッケージに割り当てられたユーザー ID を調べる (packages.xml)"
url: "p/okshfzt/"
date: "2011-04-05"
tags: ["android"]
aliases: [/android/check-user-id.html]
---

`/data/system/packages.xml` ファイルを見ると、各 APK に割り当てられているユーザー ID を調べることができます。

{{< code lang="xml" title="/data/system/packages.xml" >}}
<package name="com.example.history" codePath="/system/app/History.apk" system="true" ts="1301918167000" version="7" userId="10006">
  <sigs count="1">
    <cert index="3" />
  </sigs>
</package>
{{< /code >}}

