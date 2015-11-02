---
title: コマンドラインから Windows サービスを管理する
created: 2005-07-28
---

Windows サービスの一覧を表示する
====

**sc query** コマンドで、Windows サービスの一覧を表示できます。

```
C:\> sc query                  # 動作中のサービスの一覧
C:\> sc query state= inactive  # 停止中のサービス一覧
C:\> sc query state= all       # 登録されているサービス一覧
```

`state=` の後ろにはスペースが 1 つ必要なことに注意してください。

上記のコマンドを実行すると、サービスごとに細かい情報がたくさん表示されますが、サービス名だけの一覧を表示したいときは、下記のように `findstr` で出力を絞り込めば OK です。

```
C:\> sc query | findstr SERVICE_NAME
```


特定のサービスの情報を調べる
====
指定したサービスの情報だけを詳しく調べることもできます。

#### 例: Perforce Server のサービス情報
```
C:\> sc query Perforce

SERVICE_NAME: Perforce
        TYPE               : 10  WIN32_OWN_PROCESS
        STATE              : 4  RUNNING
                                (STOPPABLE, NOT_PAUSABLE, ACCEPTS_SHUTDOWN)
        WIN32_EXIT_CODE    : 0  (0x0)
        SERVICE_EXIT_CODE  : 0  (0x0)
        CHECKPOINT         : 0x0
        WAIT_HINT          : 0x0
```


Windows サービスを開始、停止する
====
コマンドラインから Windows サービスを開始、停止するには以下のようにします。
この操作を行うには、コマンドプロンプトを管理者権限で実行している必要があります（権限がないと Access is denied エラーになります）。

```
C:\> sc start <Service名>    ### サービスの開始
C:\> sc stop <Service名>     ### サービスの停止
```


Windows サービスの起動方法を設定する
====
システム起動時に Windows サービスを自動で起動するように設定することができます。

```
C:\> sc config mysql start= auto      ### 自動起動
C:\> sc config musql start= demand    ### 手動起動
```

`start=` の後ろにはスペースが 1 つ必要なことに注意してください。

