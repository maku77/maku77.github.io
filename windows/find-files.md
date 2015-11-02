---
title: DIR コマンドでファイルを再帰的に検索する
created: 2008-11-30
---

カレントディレクトリ以下の `index.html` という名前のファイル（またはディレクトリ）を検索するには以下のように **dir** コマンドを使用します。

```
C:\> dir /b /s index.html
```

任意の拡張子を指定して検索することもできます。

```
C:\> dir /b /s *.html
```

ワイルドカードを 2 箇所に指定すれば、ファイル名に特定の文字列を含んでいるファイルをすべて検索することができます。

```
C:\> dir /b /s *dummy*
```

オプションの意味
----
```
C:\> find /?
...
  /B          Uses bare format (no heading information or summary).
              （余計な情報を表示せず、ファイル名（パス）のみ表示する）
  /S          Displays files in specified directory and all subdirectories.
              （サブディレクトリも再帰的に検索する）
```

