---
title: POD フォーマットについて
created: 2008-07-06
---

POD は下記の頭文字をとった単語で、POD フォーマットは、Perl モジュールのドキュメント記述などに使用されます。

- Perl Online Documentation
- Perl Old Documentation

POD に関する詳しい説明は、下記のようにして読むことができます。

```
$ perldoc perlpod
```

POD ドキュメントはモジュールのインストール時に自動的に Unix man ページなどに変換されます。インストールされているモジュールの POD ドキュメントは、`perldoc` コマンドで参照することができます。

```
$ perldoc YourModule
```

POD ファイルを `troff -man` コマンドで変換すると、man ページを作成することができます。

