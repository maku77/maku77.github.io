---
title: "Windows で実行中のプロセス（タスク）の一覧を表示する (tasklist)"
created: 2015-04-13
description: "tasklist コマンドを使用すると、現在実行中のプロセスの一覧を確認することができます。様々な条件でフィルタして目的のプロセスを探すこともできます。"
---

tasklist コマンドの基本
----

**tasklist** コマンドを使って、現在動作しているタスクの一覧を表示することができます。

~~~
C:\> tasklist

イメージ名                     PID セッション名     セッション# メモリ使用量
========================= ======== ================ =========== ============
System Idle Process              0 Services                   0         24 K
System                           4 Services                   0     10,924 K
smss.exe                       416 Services                   0      1,452 K
csrss.exe                      656 Services                   0      7,464 K
wininit.exe                    744 Services                   0      6,292 K
csrss.exe                      760 Console                    1     39,000 K
winlogon.exe                   796 Console                    1     39,588 K
services.exe                   856 Services                   0     47,656 K
lsass.exe                      872 Services                   0     41,420 K
lsm.exe                        880 Services                   0      5,300 K
svchost.exe                    988 Services                   0     44,984 K
svchost.exe                    192 Services                   0     51,544 K
...
~~~

**イメージ名** というのは、プロセスを開始した実行ファイル (.exe) の名前です。
ただし、先頭には "System Idle Process" という、システムリソースを追跡するための特別なシステムプロセスも表示されています。

`/v` オプションを付けて実行すると、より詳細な情報（ウィンドウタイトルや CPU 時間など）を表示することができます。

~~~
C:\> tasklist /v （詳細情報まで表示）

イメージ名       PID セッション名 セッション# メモリ使用量 状態           ユーザー名 CPU 時間 ウィンドウ タイトル
============== ===== ============ =========== ============ ============== ========== ======== ===========================
gvim.exe        1496 Console                1     44,140 K Running        maku        0:00:05 memo.md (D:\memo.md) - GVIM
EXCEL.EXE       3984 Console                1     46,856 K Running        maku        0:00:02 Microsoft Excel - Test.xlsx
cmd.exe         6772 Console                1      5,204 K Running        maku        0:00:00 command
conhost.exe     5900 Console                1     13,816 K Not Responding maku        0:00:00 OLEChannelWnd
chrome.exe      9492 Console                1     81,316 K Unknown        maku        0:00:04 N/A
chrome.exe     10084 Console                1    228,852 K Unknown        maku        0:01:24 N/A
chrome.exe      6952 Console                1     60,704 K Unknown        maku        0:00:02 N/A
chrome.exe      3916 Console                1     82,120 K Unknown        maku        0:00:04 N/A
chrome.exe      3084 Console                1    153,452 K Unknown        maku        0:00:26 N/A
powershell.exe  7500 Console                1    118,048 K Running        maku        0:00:01 Windows PowerShell
conhost.exe     7472 Console                1     13,948 K Not Responding maku        0:00:00 OLEChannelWnd
more.com        5156 Console                1      3,552 K Unknown        maku        0:00:00 N/A
cmd.exe         6508 Console                1      5,444 K Running        maku        0:00:00 command - tasklist  /v
~~~


出力形式を切り替える (/fo list, /fo csv)
----

`tasklist` のデフォルトの出力形式はテーブル形式 (`/fo table`) になっています。
オプションを指定することで、リスト形式 (`/fo list`)、CSV 形式 (`/fo csv`) で出力することが可能です。

#### リスト形式で出力 (/fo list)

~~~
C:\> tasklist /v /fo list

イメージ名:          System Idle Process
PID:                 0
セッション名:        Services
セッション#:         0
メモリ使用量:        24 K
状態:                Unknown
ユーザー名:          NT AUTHORITY\SYSTEM
CPU 時間:            39:43:18
ウィンドウ タイトル: N/A

イメージ名:          System
PID:                 4
セッション名:        Services
セッション#:         0
メモリ使用量:        10,928 K
状態:                Unknown
ユーザー名:          N/A
CPU 時間:            0:09:42
ウィンドウ タイトル: N/A

...
~~~

#### CSV 形式で出力 (/fo csv)

~~~
C:\> tasklist /v /fo csv
"イメージ名","PID","セッション名","セッション#","メモリ使用量","状態","ユーザー名","CPU 時間","ウィンドウ タイトル"
"System Idle Process","0","Services","0","24 K","Unknown","NT AUTHORITY\SYSTEM","39:52:34","N/A"
"System","4","Services","0","10,928 K","Unknown","N/A","0:09:43","N/A"
"smss.exe","416","Services","0","1,452 K","Unknown","N/A","0:00:00","N/A"
"csrss.exe","656","Services","0","7,468 K","Unknown","N/A","0:00:19","N/A"
"wininit.exe","744","Services","0","6,292 K","Unknown","N/A","0:00:00","N/A"
...
~~~

一行当たりの文字数が制限されるメール文書などで引用したいときは、リスト形式で出力するのがよいかもしれません。
`tasklist` の出力結果をプログラムで処理したい場合などは、CSV 形式による出力が役に立つでしょう。


プロセスとサービスの関連付けを調べる (/svc)
----

オプションで `/svc` を指定すると、そのプロセス上で動作しているサービスの一覧を確認することができます。

~~~
C:\> tasklist /svc

Image Name                     PID Services
========================= ======== ============================================
System Idle Process              0 N/A
System                           4 N/A
smss.exe                       504 N/A
csrss.exe                      648 N/A
wininit.exe                    712 N/A
csrss.exe                      736 N/A
services.exe                   772 N/A
lsass.exe                      796 KeyIso, Netlogon, SamSs
lsm.exe                        804 N/A
winlogon.exe                   872 N/A
svchost.exe                    956 DcomLaunch, PlugPlay, Power
svchost.exe                    540 RpcEptMapper, RpcSs
atiesrxx.exe                   828 AMD External Events Utility
svchost.exe                    852 AudioSrv, Dhcp, eventlog, lmhosts, wscsvc
svchost.exe                   1088 AudioEndpointBuilder, CscService, hidserv,
                                   Netman, PcaSvc, SysMain, TrkWks,
                                   UmRdpService, UxSms, wudfsvc
svchost.exe                   1112 Appinfo, BITS, Browser, CertPropSvc, gpsvc,
                                   iphlpsvc, LanmanServer, ProfSvc, Schedule,
                                   SENS, SessionEnv, ShellHWDetection, Themes,
                                   Winmgmt, wuauserv
svchost.exe                   1260 EventSystem, netprofm, nsi, W32Time,
                                   WdiServiceHost, WinHttpAutoProxySvc
...
~~~

ここで表示されるサービス名は、サービス管理用の `sc` コマンドで使用されるのと同様に、省略名で表示されます。


プロセスが使用している DLL の一覧を調べる (/m)
----

`tasklist` コマンドに `/m` オプションを指定すると、動作中のプロセスが使用している DLL の一覧を関連付けて表示することができます。
すべてのプロセスの情報を表示するには、しばらく時間がかかります。

~~~
C:\> tasklist /m

イメージ名                     PID モジュール
========================= ======== ============================================
...
SearchIndexer.exe             5828 N/A
MSOSYNC.EXE                   5972 ntdll.dll, wow64.dll, wow64win.dll,
                                   wow64cpu.dll
OneDrive.exe                  4980 ntdll.dll, wow64.dll, wow64win.dll,
                                   wow64cpu.dll
LeapControlPanel.exe          6172 ntdll.dll, wow64.dll, wow64win.dll,
                                   wow64cpu.dll
Launchy.exe                   6428 ntdll.dll, wow64.dll, wow64win.dll,
                                   wow64cpu.dll
...
~~~

上記では、すべてのプロセスの情報を列挙していますが、`/m` オプションに DLL 名を渡すようにすれば、その DLL を使用しているプロセスの情報だけを検索して表示することができます（こちらも検索にしばらく時間がかかります）。

#### 例: wow64.dll を使用しているプロセスを検索する

~~~
C:\> tasklist /m wow64.dll

イメージ名                     PID モジュール
========================= ======== ============================================
UpdaterUI.exe                 5504 wow64.dll
IMECMNT.EXE                   5680 wow64.dll
mctray.exe                    5416 wow64.dll
MSOSYNC.EXE                   5972 wow64.dll
OneDrive.exe                  4980 wow64.dll
...
~~~


いろいろな条件でプロセスを検索する (/fi)
----

`tasklist` コマンドの `/fi` オプションを使用すると、実行ファイル名や、メモリ使用量など、いろいろな条件で実行中のプロセスを検索することができます。

#### 例: 実行ファイル名が "gvim.exe" のプロセスの情報を検索する

~~~
C:\> tasklist /fi "imagename eq gvim.exe"

Image Name                     PID Session Name        Session#    Mem Usage
========================= ======== ================ =========== ============
gvim.exe                      6112 Console                    1     21,796 K
~~~

#### 例: 応答しなくなったプロセス (Not Responding) を表示する

~~~
C:\> tasklist /fi "status eq not responding"

イメージ名                     PID セッション名     セッション# メモリ使用量
========================= ======== ================ =========== ============
jusched.exe                   7096 Console                    1      5,488 K
conhost.exe                   2932 Console                    1     13,440 K
conhost.exe                   5900 Console                    1     13,816 K
~~~

<div class="note">
<code>tasklist</code> コマンドのデフォルトの出力形式では、状態（Running や Not Responding など）のフィールドは表示されませんが、<code>/fi</code> オプションでその値を使ってフィルタすることは可能です。状態のフィールドを出力するには、<code>/v</code> オプションを付けて実行してください。
</div>

#### 例: CPU 時間が 5 分以上のプロセスを検索する

~~~
C:\> tasklist /fi "cputime gt 00:05:00"

イメージ名                     PID セッション名     セッション# メモリ使用量
========================= ======== ================ =========== ============
System Idle Process              0 Services                   0         24 K
System                           4 Services                   0     10,936 K
csrss.exe                      760 Console                    1     38,572 K
mcshield.exe                  3416 Services                   0     59,148 K
AIMP.exe                      9900 Console                    1     30,192 K
OUTLOOK.EXE                   9896 Console                    1    222,716 K
ConEmuC64.exe                 4280 Console                    1      6,516 K
conhost.exe                   8224 Console                    1      8,316 K
ruby.exe                     12228 Console                    1    103,168 K
Dropbox.exe                   2532 Console                    1    188,692 K
~~~

<div class="note">
フィルタの条件文字列の中の <code>gt</code> は、greater than (＞）を示しています。他にも <code>lt</code>（＜）、<code>ge</code>（≧）、<code>le</code>（≦）などを使用することができます。
</div>


#### 例: メモリ使用量が 200,000KB 以上のプロセスを検索する

~~~
C:\> tasklist /fi "memusage gt 200000"

イメージ名                     PID セッション名     セッション# メモリ使用量
========================= ======== ================ =========== ============
svchost.exe                    564 Services                   0    403,516 K
explorer.exe                  5172 Console                    1    323,708 K
slack.exe                    10048 Console                    1    266,992 K
OUTLOOK.EXE                   9896 Console                    1    222,808 K
chrome.exe                    7260 Console                    1    259,172 K
chrome.exe                    6292 Console                    1    217,608 K
chrome.exe                   10084 Console                    1    247,532 K
monitor.exe                  10472 Console                    1    212,872 K
monitor.exe                  11024 Console                    1    394,844 K
~~~

複数のフィルタ条件（`/fo` オプション）を指定すると、AND 条件で検索することができます。

#### 例: メモリ使用量が 100,000KB 以上、かつ、CPU 時間が 5 分以上のプロセスを検索する

~~~
C:\> tasklist /fi "memusage gt 100000" /fi "cputime gt 00:05:00"

イメージ名                     PID セッション名     セッション# メモリ使用量
========================= ======== ================ =========== ============
OUTLOOK.EXE                   9896 Console                    1    222,892 K
ruby.exe                     12228 Console                    1    106,584 K
Dropbox.exe                   2532 Console                    1    189,100 K
~~~


tasklist コマンドのヘルプ
----

`tasklist` コマンドについてさらに詳しく調べたいときは、下記のようにしてヘルプを参照することができます。

~~~
C:\> help tasklist
~~~

あるいは、

~~~
C:\> tasklist /?
~~~

