---
title: "特定のサーバに接続するための p4v のショートカットを作成する"
date: "2010-03-03"
---

複数の Perforce サーバを使用している場合、それぞれのサーバ接続用に、p4v.exe のショートカットアイコンを作成しておくと便利です。

p4v.exe のショートカットアイコンを右クリックしてプロパティを開き、Shortcut タブの Target の部分の p4v のパスにオプションを追加することで、接続先の Perforce サーバなどを設定できます。

#### 例:
```
C:\app\Perforce\p4v.exe -p p4.example.com:1666 -u joe -P password -c joe.client1
```

起動パラメータの一覧は、以下のようにすると確認できます。

```
C:\> p4v -help
Usage: p4v [OPTION]...
    -p port     set p4port
    -u user     set p4user
    -c client   set p4client
    -log        log p4 user actions to stdout
    -logall     ...include all p4 operations

    -V          print client version
    -h -help    this message

    (Optional)
    [-C <charset>]
    [-P <password>]
    [-cmd "<command> <file>"]

Port, client and user are mandatory (in that order), everything else is optional.
If a command is given, quotes have to be placed around the command and the file
name so that both are passed as a single parameter.
```


