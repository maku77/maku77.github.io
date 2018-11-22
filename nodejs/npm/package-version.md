---
title: "npm でインストール可能なパッケージのバージョンを調べる"
date: "2014-09-23"
---

`npm info` コマンドを使用すると、NPM repository で管理されているパッケージの情報を調べることができます。

#### 例: 最新の Express のバージョンを表示する

```
$ npm info express version
4.9.4
```

#### 例: インストール可能な Express のバージョンのリストを表示する

```
$ npm info express versions
[ '0.14.0',
  '0.14.1',
  ...
  '4.9.3',
  '4.9.4' ]
```

最後のパラメータを変更することで、パッケージに関するいろいろな情報を表示することができます。

| コマンド | 説明 |
| ---- | ---- |
| `npm info <pkg> version` | 最新のバージョン番号を表示する |
| `npm info <pkg> versions` | 有効なバージョン番号の一覧を表示する |
| `npm info <pkg> homepage` | ホームページの URL を表示する |
| `npm info <pkg> repository` | ソースコードのリポジトリの URL を表示する |
| `npm info <pkg> author` | 作者の情報を表示する |
| `npm info <pkg> contributors` | コントリビューターの一覧を表示する |
| `npm info <pkg>` | すべての情報を表示する |

