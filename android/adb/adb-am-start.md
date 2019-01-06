---
title: "ADB でアプリを起動する intent を投げる (am start/broadcast)"
date: "2016-10-03"
---

以下のコマンドは、`adb shell` でデバイスに接続した状態から実行することを想定しています。
接続と同時に実行するには、`adb shell` を先頭に追加して実行してください。


am start コマンドによる Activity の起動
----

Intent をコマンドによって発生させるには、下記のように実行します。

```
am start ＜INTENT＞
```

Intent の指定方法の詳細は、[Specification for INTENT arguments](https://developer.android.com/studio/command-line/shell.html#IntentSpec) のページに記載されています。

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

下記は追加の Bundle データを渡すためのオプション。

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


### am start の使用例

#### Activity のクラス名（コンポーネント名）を指定して起動する

```
am start -n com.example.myapp/.MainActivity
```

#### ブラウザを起動する（URL をハンドルするアプリを起動する）

```
am start -a android.intent.action.VIEW http://google.com/
```

#### 電話を掛ける

```
am start -a android.intent.action.CALL tel:123456789
```

#### 着信音の選択画面を表示する

```
am start -a android.intent.action.RINGTONE_PICKER
```

#### HOME 画面を起動する

```
am start -a android.intent.action.MAIN
am start -a android.intent.action.MAIN -c android.intent.category.HOME
```


am broadcast で Broadcast Intent を投げる
----

Intent をブロードキャストするには、`am start` の代わりに `am broadcast` を使用します。

```
am broadcast -a android.intent.action.xxx
```

### am broadcast の使用例

#### BOOT_COMPLETED インテントを投げる（root ユーザで実行）

```
am broadcast -a android.intent.action.BOOT_COMPLETED
```

