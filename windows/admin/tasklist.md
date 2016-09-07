---
title: Windows で実行中のタスクの一覧を表示する (tasklist)
created: 2015-04-13
---

**tasklist** コマンドを使って、現在動作しているタスクの一覧を表示することができます。

```
C:\> tasklist
C:\> tasklist /V （詳細情報まで表示）
```

オプションで `/SVC` を指定すると、そのプロセス上で動作しているサービスの一覧も確認することができます。

```
D:\> tasklist /SVC

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
```

オプション `/FI` を使用して、特定のイメージ名で出力をフィルタリングすることもできます。

```
D:\> tasklist /FI "imagename eq gvim.exe"

Image Name                     PID Session Name        Session#    Mem Usage
========================= ======== ================ =========== ============
gvim.exe                      6112 Console                    1     21,796 K
```

オプション `/M` を指定すると、タスクが使用している DLL の一覧を確認することができます。

```
D:\> tasklist /M

Image Name                     PID Modules
========================= ======== ============================================
cmd.exe                       4980 ntdll.dll, kernel32.dll, KERNELBASE.dll,
                                   msvcrt.dll, WINBRAND.dll, USER32.dll,
                                   GDI32.dll, LPK.dll, USP10.dll, IMM32.DLL,
                                   MSCTF.dll, ADVAPI32.dll, sechost.dll,
                                   RPCRT4.dll, apphelp.dll, clink_dll_x64.dll,
                                   dbghelp.dll, SHELL32.dll, SHLWAPI.dll,
                                   ole32.dll
...
```

オプション `/FO` を使って、出力形式（フォーマット）を変更することができます。

- `/FO TABLE` -- 表形式（デフォルト）
- `/FO CSV` -- CSV 形式
- `/FO LIST` -- リスト形式

```
D:\> tasklist /FO TABLE
（省略）
```

