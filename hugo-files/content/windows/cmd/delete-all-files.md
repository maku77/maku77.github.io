---
title: "Windowsメモ: コマンドプロンプトでディレクトリ内のファイルをすべて削除する (del, rmdir)"
url: "p/pv8cbr7/"
date: "2019-10-11"
tags: ["windows"]
aliases: /windows/cmd/delete-all-files.html
---

Windows のコマンドプロンプトから、ディレクトリ内のファイルをすべて削除するには次のようにします。

{{< code lang="bat" title="カレントディレクトリ内のファイルをすべて削除" >}}
del /S /Q *
{{< /code >}}

{{< code lang="bat" title="foo ディレクトリ内のファイルをすべて削除" >}}
del /S /Q foo
{{< /code >}}

- **`/S`** オプション ... サブディレクトリも再帰的に削除します
- **`/Q`** オプション ... 削除のたびに Y/N を聞かれないようにします

ただし、`del` コマンドは「ファイル」の削除だけを行い、空になった「ディレクトリ」は削除してくれません。

代わりに `rmdir` コマンドを使用すると、指定したディレクトリを丸ごと削除することができます。

{{< code lang="bat" title="foo ディレクトリを丸ごと削除する" >}}
rmdir /S /Q foo
{{< /code >}}

