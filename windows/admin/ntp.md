---
title: "NTP による時刻同期情報を取得する (w32tm)"
date: "2011-08-30"
---

- [参考: Windows Time Service Tools and Settings](http://technet.microsoft.com/en-us/library/cc773263%28WS.10%29.aspx)

**`w32tm`** コマンドを使用すると、コマンドラインから NTP 関連の操作を行うことができます。


NTP で直ちに時刻同期する
----

~~~
C:\> w32tm /resync
Sending resync command to local computer
The computer did not resync because no time data was available.
~~~


NTP による同期情報を確認する
----

~~~
C:\> w32tm /query /status
Leap Indicator: 0(no warning)
Stratum: 5 (secondary reference - syncd by (S)NTP)
Precision: -6 (15.625ms per tick)
Root Delay: 0.2496490s
Root Dispersion: 16.0100000s
ReferenceId: 0xF33322B3 (MD5 hash fraction of the IPv6 address: )
Last Successful Sync Time: 2011/08/30 9:56:39
Source: hoge.example.com
Poll Interval: 10 (1024s)
~~~


関連レジストリの情報を表示する
----

~~~
C:\> w32tm /dumpreg
Value Name             Value Type          Value Data
--------------------------------------------------------
DisplayName            REG_SZ              @%SystemRoot%\system32\w32time.dll,-200
ImagePath              REG_EXPAND_SZ       %SystemRoot%\system32\svchost.exe -k LocalService
Description            REG_SZ              @%SystemRoot%\system32\w32time.dll,-201
ObjectName             REG_SZ              NT AUTHORITY\LocalService
ErrorControl           REG_DWORD           1
Start                  REG_DWORD           3
Type                   REG_DWORD           32
ServiceSidType         REG_DWORD           1
RequiredPrivileges     REG_MULTI_SZ        SeAuditPrivilege, SeChangeNotifyPrivilege, SeCreateGlobal
Privilege, SeSystemTimePrivilege
FailureActions         REG_BINARY          80510100000000000000000003000000140000000100000060EA00000
1000000C0D401000000000000000000
~~~

