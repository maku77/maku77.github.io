---
title: "ADB で Activity や Service を起動するインテントを投げる (am start/start-service/broadcast)"
url: "p/eezeq4g/"
date: "2016-10-03"
lastmod: "2022-04-21"
tags: ["Android"]
aliases: /android/adb/adb-am-start.html
---

以下のコマンドは、`adb shell` でデバイスに接続した状態から実行することを想定しています。
接続と同時に実行するには、`adb shell` を先頭に追加して実行してください（例: `adb shell am start ...`）。


Activity（アクティビティ）を起動する
----

シェル上で __`am start`__ コマンドを実行すると、Intent を発生させて Activity を起動することができます。

```
am start <INTENT>
```

Intent 引数（`<INTENT>` の部分）の指定方法は、[Specification for INTENT arguments](https://developer.android.com/studio/command-line/shell.html#IntentSpec) のページに詳しく記載されています。
具体的には、次のようなオプションの組み合わせで Intent を構成します。

`-a <ACTION>`
: 例: `-a android.intent.action.VIEW`

`-d <DATA_URI>`
: 例: `-d content://contacts/people/1`

`-t <MIME_TYPE>`
: 例: `-t image/png`

`-c <CATEGORY>`
: 例: `-c android.intent.category.APP_CONTACTS`

`-n <COMPONENT>`
: 例: `-n com.example.app/.ExampleActivity`

必要に応じて、Bundle データを渡すこともできます。

- `--esn <EXTRA_KEY>` （キーのみ）
- `-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE>`
- `--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE>`
- `--ei <EXTRA_KEY> <EXTRA_INT_VALUE>`
- `--el <EXTRA_KEY> <EXTRA_LONG_VALUE>`
- `--ef <EXTRA_KEY> <EXTRA_FLOAT_VALUE>`
- `--eu <EXTRA_KEY> <EXTRA_URI_VALUE>`
- `--ecn <EXTRA_KEY> <EXTRA_COMPONENT_NAME_VALUE>`
- `--eia <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]`
- `--ela <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]`
- `--efa <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]`

下記は、`am start` コマンドによる Activity の起動例です。

### Activity のクラス名（コンポーネント名）を直接指定して起動する

```console
$ am start -n com.example.myapp/.MainActivity
```

### アクションを指定して起動する

{{< code lang="console" title="ブラウザを起動する（URL をハンドルするアプリを起動する）" >}}
$ am start -a android.intent.action.VIEW http://google.com/
{{< /code >}}

{{< code lang="console" title="電話を掛ける" >}}
$ am start -a android.intent.action.CALL tel:123456789
{{< /code >}}

{{< code lang="console" title="着信音の選択画面を表示する" >}}
am start -a android.intent.action.RINGTONE_PICKER
{{< /code >}}

{{< code lang="console" title="HOME 画面を起動する" >}}
$ am start -a android.intent.action.MAIN
$ am start -a android.intent.action.MAIN -c android.intent.category.HOME
{{< /code >}}


サービス (Service) を起動・停止する
----

### サービスの起動

__`am start-service`__ コマンドで任意のサービスを起動することができます。
例えば、アプリのパッケージ名 (`com.example.myapp`) とサービスのクラス名 (`com.example.myapp.services.MyApp`) が分かっているのであれば、次のようにサービスを起動できます。
ちなみに、`-n` オプションの後ろの部分を「コンポーネント名」と呼びます。

```console
$ adb shell am start-service -n com.example.myapp/.services.MyService
Starting service: Intent { cmp=com.example.myapp/.services.MyService }
```

Foreground サービスとして起動する場合は、`am start-service` の代わりに、__`am start-foreground-service`__ を使用します。

```console
$ adb shell am start-foreground-service -n com.example.myapp/.services.MyService
Starting service: Intent { cmp=com.example.myapp/.services.MyService }
```

サービスがちゃんと Foreground で動作しているかは、`dumpsys` コマンドで確認できます。

```console
$ adb shell dumpsys activity s MyService | grep isForeground
    isForeground=true foregroundId=2 ...
```

`am start-service` や `am start-foreground-service` コマンドでサービスを起動するには、そのサービスが `AndroidManifext.xml` で公開設定されている必要があります。
`android:exported="false"` になっているサービスを起動しようとすると、`Error: Requires permission not exported from uid 10077` といったエラーになります。

```xml
<service
  android:name="com.example.myapp.services.MyService"
  android:exported="true"
```

### サービスの停止

サービスを停止するには、__`am stop-service`__ コマンドを使います。

```console
$ adb shell am stop-service -n com.example.myapp/.services.MyService
Stopping service: Intent { cmp=com.example.myapp/.services.MyService }
Service stopped
```

（おまけ）`adb shell am help` によるヘルプ表示の抜粋です。

```
start-service [--user <USER_ID> | current] <INTENT>
    Start a Service.  Options are:
    --user <USER_ID> | current: Specify which user to run as; if not
        specified then run as the current user.

start-foreground-service [--user <USER_ID> | current] <INTENT>
    Start a foreground Service.  Options are:
    --user <USER_ID> | current: Specify which user to run as; if not
        specified then run as the current user.

stop-service [--user <USER_ID> | current] <INTENT>
    Stop a Service.  Options are:
    --user <USER_ID> | current: Specify which user to run as; if not
        specified then run as the current user.
```


ブロードキャストインテント (Broadcast Intent) を投げる
----

Intent をブロードキャストするには、`am start` の代わりに __`am broadcast`__ を使用します。

```console
$ am broadcast -a android.intent.action.XXX
```

下記は、端末の起動時にブロードキャストされる `BOOT_COMPLETED` インテントを明治的に投げる例です（root ユーザで実行）。

```console
$ am broadcast -a android.intent.action.BOOT_COMPLETED
```

（おまけ）`adb shell am help` によるヘルプ表示の抜粋です。

```
broadcast [--user <USER_ID> | all | current]
        [--receiver-permission <PERMISSION>]
        [--allow-background-activity-starts]
        [--async] <INTENT>
    Send a broadcast Intent.  Options are:
    --user <USER_ID> | all | current: Specify which user to send to; if not
        specified then send to all users.
    --receiver-permission <PERMISSION>: Require receiver to hold permission.
    --allow-background-activity-starts: The receiver may start activities
        even if in the background.
    --async: Send without waiting for the completion of the receiver.
```

