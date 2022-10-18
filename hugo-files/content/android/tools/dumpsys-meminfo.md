---
title: "メモリの使用状況を確認する (dumpsys meminfo)"
url: "p/t6wfxev/"
date: "2014-07-29"
tags: ["Android"]
aliases: /android/dumpsys-meminfo.html
---

各プロセスのメモリ使用状況を確認する
----

__`dumpsys`__ コマンドの __`meminfo`__ セクションでは、各プロセスのメモリ使用状況を確認することができます。

```console
$ adb shell dumpsys meminfo
Applications Memory Usage (kB):
Uptime: 169910958 Realtime: 170196415

Total PSS by process:
    96302 kB: system (pid 728)
    44547 kB: com.android.systemui (pid 965)
    32601 kB: com.sonyericsson.android.pobox.imecore (pid 1042)
    29610 kB: com.android.settings (pid 8006)
    26500 kB: com.sonyericsson.home (pid 1321)
    23027 kB: com.facebook.katana (pid 7371)
    17963 kB: com.facebook.katana:dash (pid 7641)
    13579 kB: com.google.android.gms (pid 5171)
    ...
```


プロセスを指定してメモリ使用状況を確認する
----

上記で表示されたパッケージ名や pid を、`dumpsys meminfo` コマンドの後ろに続けて指定することで、そのプロセスにおける詳細なメモリ使用情報を調べられます。

```console
$ adb shell dumpsys meminfo com.android.phone
Applications Memory Usage (kB):
Uptime: 170096486 Realtime: 170381943

** MEMINFO in pid 1066 [com.android.phone] **
                         Shared  Private     Heap     Heap     Heap
                   Pss    Dirty    Dirty     Size    Alloc     Free
                ------   ------   ------   ------   ------   ------
       Native       20        8       20     2376     2323       48
       Dalvik     3236     5256     3156    11108     3032     8076
       Cursor        0        0        0
       Ashmem        2        4        0
    Other dev        4       48        0
     .so mmap      564     2232      464
    .jar mmap        6        0        0
    .apk mmap      955        0        0
    .ttf mmap        0        0        0
    .dex mmap        0        0        0
   Other mmap     2113       16      184
      Unknown     1058      484     1056
        TOTAL     7958     8048     4880    13484     5355     8124

 Objects
               Views:        0         ViewRootImpl:        0
         AppContexts:       13           Activities:        0
              Assets:        7        AssetManagers:        7
       Local Binders:       45        Proxy Binders:       25
    Death Recipients:        5
     OpenSSL Sockets:        0

 SQL
         MEMORY_USED:      178
  PAGECACHE_OVERFLOW:       32          MALLOC_SIZE:       62

 DATABASES
      pgsz     dbsz   Lookaside(b)          cache  Dbname
         4       16             22       131/19/2  /data/data/com.android.providers.telephony/databases/telephony.db
         4       20             25         1/19/2  /data/data/com.android.phone/databases/rejectmsgs.db
```


プログラムからメモリ使用状況を確認する
----

Android プログラムの実装からメモリ情報を取得するには、[android.os.Debug](https://developer.android.com/reference/android/os/Debug) クラスを使用します。

```java
// Native ヒープの使用可能最大サイズ
long total = Debug.getNativeHeapSize();

// Native ヒープの残りサイズ
long free = Debug.getNativeHeapFreeSize()

// Native ヒープの割り当て済みサイズ
long alloc = Debug.getNativeHeapAllocatedSize();
```


デバイス上で procrank が使用できる場合
----

Android デバイスを debug ビルドパッケージで開発しているときは、Linux の __`procrank`__ コマンドを使用できるかもしれません。
`adb shell` で Android デバイスに接続して次のように実行します。

```console
# procrank
  PID      Vss      Rss      Pss      Uss  cmdline
  890   84456K   48668K   25850K   21284K  system_server
 1231   50748K   39088K   17587K   13792K  com.android.launcher2
  947   34488K   28528K   10834K    9308K  com.android.wallpaper
  987   26964K   26956K    8751K    7308K  com.google.process.gapps
  954   24300K   24296K    6249K    4824K  com.android.phone
  948   23020K   23016K    5864K    4748K  com.android.inputmethod.latin
  888   25728K   25724K    5774K    3668K  zygote
  977   24100K   24096K    5667K    4340K  android.process.acore
  ...
```

