---
title: "コマンドプロンプトでディレクトリ内のファイルをすべて削除する (del, rmdir)"
date: "2019-10-11"
---

Windows のコマンドプロンプトから、ディレクトリ内のファイルをすべて削除するには次のようにします。

```
del /S /Q *    # カレントディレクトリ内のファイルをすべて削除
del /S /Q foo  # foo ディレクトリ内のファイルをすべて削除
```

- `/S` オプション: サブディレクトリも再帰的に削除します
- `/Q` オプション: 削除のたびに Y/N を聞かれないようにします

ただし、`del` コマンドは「ファイル」の削除だけを行い、空になった「ディレクトリ」は削除してくれません。

代わりに `rmdir` コマンドを使用すると、指定したディレクトリを丸ごと削除することができます。

```
rmdir /S /Q foo  # foo ディレクトリを丸ごと削除する
```

