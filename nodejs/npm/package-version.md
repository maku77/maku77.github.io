---
title: npm でインストール可能なパッケージの最新バージョンを調べる
date: "2014-09-23"
---

`npm info` コマンドを使用すると、NPM repository で管理されているパッケージの情報を調べることができます。

#### 例: 最新の Express のバージョンを調べる

```
$ npm info express version
4.9.4
```

実は最後のパラメータは、`npm info <pkg>` としてズラーッと表示される JSON データのプロパティを指定しているだけです。
なので、他にもいろんな情報を表示することが可能です。

#### 例: ホームページの URL
```
$ npm info <pkg> homepage
```

#### 例: ソースコードリポジトリの URL
```
$ npm info <pkg> repository
```

#### 例: すべてのバージョンを表示する
```
$ npm info <pkg> versions
```

#### 例: 作者の表示
```
$ npm info <pkg> author
```

#### 例: コントリビュータの表示
```
$ npm info <pkg> contributors
```

