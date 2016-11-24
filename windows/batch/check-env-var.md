---
title: バッチファイルで環境変数が設定されているかどうか調べる
created: 2015-12-01
---

Windows バッチファイルの中で下記の構文を使用すると、指定した環境変数が設定されているか、あるいは設定されていないかに応じて処理を振り分けることができます。

```bat
if defined 環境変数 (命令文...)
if not defined 環境変数 (命令文...)
```

#### 例: 環境変数 HOME が設定されているかどうかを調べる

```bat
@echo off

if defined HOME (
  echo 環境変数 HOME が定義されています
  echo %HOME%
)

if not defined HOME (
  echo 環境変数 HOME が定義されていません
  exit /b
)
```

