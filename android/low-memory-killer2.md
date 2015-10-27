---
title: LowMemoryKiller の too many background によるプロセス kill の仕組み
created: 2011-09-07
---

ActivityManagerService.java の中の `updateOomAdjLocked()` は、Android の動作中に頻繁に呼び出され、バックグラウンドで走っているプロセスが多くなると、プロセスを自動的に kill するようになっています。

- hidden 状態のプロセスが MAX_HIDDEN_APPS(15) より多くなると、プロセスが kill される。
- プロセスの adj が computeOomAdjLocked() によって計算され、HIDDEN_APP_MIN_ADJ(7) 以上の値になったプロセスが kill 候補になる。というか 7 以上になる場合は、そのプロセスは hidden 状態のはず。
- プロセスの使用履歴上で、古いものから順番に kill の対象になる。

引数なしの `updateOomAdjLocked()` の最後の方で、`numHidden` の値をダンプするようにすれば、その瞬間にいくつの `hidden` プロセスが存在するか調べられます。
基本的には動的に計算される adj の値は 0～15 の範囲になりますが、アプリケーションのフラグとして `SYSTEM` と `PERSISTENT` が設定されている場合は、`CORE_SERVER_ADJ (-12)` が採用されるようです。

```java
final ProcessRecord appAppLocked(ApplicationInfo info) {
    ...
    if ((info.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_PERSISTENT))
            == (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_PERSISTENT)) {
        app.persistent = true;
        app.maxAdj = CORE_SERVER_ADJ;  // ★
    }
    ...
}
```

絶対に kill されてはいけない電源管理系のサービスなどは、このフラグを設定しておかなければいけません。
`ApplicationInfo.FLAG_SYSTEM` の方は、プリインストールアプリ (`/system/app/*`) の場合は自動で付加され、`ApplicationInfo.FLAG_PERSISTENT` の方は、AndroidManifest.xml の application 要素で以下のように設定するようです。

### AndroidManifest.xml

```xml
...
<application ... android:persistent="true">
...
```

