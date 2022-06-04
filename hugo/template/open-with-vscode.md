---
title: "Markdown (.md) ファイルを VS Code で開くリンクを表示する"
url: "p/9hkprvx/"
permalink: "p/9hkprvx/"
date: "2022-06-04"
tags: ["Hugo", "VS Code"]
---

VS Code で開くリンク
----

下記の Hugo テンプレートコードは、現在のページの生成元になった Markdown (.md) ファイルを VS Code で開くリンクを表示します。

```go
{{ "{{" }}- if .Site.IsServer }}
  {{ "{{" }}- with .File }}
    <a href="vscode://file/{{ "{{" }} .Filename }}">✎ VS Code で開く</a>
  {{ "{{" }}- end }}
{{ "{{" }}- end }}
```


仕組み
----

VS Code (Visual Studio Code) をインストールすると、ブラウザのアドレス欄に __`vscode://path<ファイルのフルパス>`__ という特殊なアドレスを入力することで、ローカル PC 内のファイルを VS Code で開くことができるようになります。
例えば、`/Users/maku/my-hugo/content/page.md` というファイルを開きたいときは次のようなアドレスを入力します。

```
# Linux や macOS の場合
vscode://file/Users/maku/my-hugo/content/page.md

# Windows の場合
vscode://file/C:\Users\maku\my-hugo\content\page.md
```

Markdown ファイルのパス情報は [File 変数で参照できる](/p/8env4bi/) ので、それを利用して `a` 要素を出力してやれば、リンクのクリックだけで VS Code を開くことができるようになります。

