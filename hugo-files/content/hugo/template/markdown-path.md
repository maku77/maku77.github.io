---
title: "Hugo で Markdown (.md) ファイルのパス情報を取得する"
url: "p/8env4bi/"
date: "2018-06-14"
tags: ["Hugo"]
description: "File 変数を参照すると、記事ページのもとになった Markdown ファイルのパス情報を取得することができます。"
aliases: /hugo/template/markdown-path.html
---

Hugo のテンプレートファイル内で、__`File`__ 変数を参照すると、記事ページのもとになった Markdown ファイルのパス情報を取得することができます。
`File` 変数の一覧は下記のページで参照できます。

- [File Variables｜Hugo](https://gohugo.io/variables/files/)


File 変数の使用例
----

テンプレートファイルに下記のようなコードを追加すれば、`File` 変数の内容を簡単に確認できます。

{{< code lang="go-html-template" title="layouts/_default/baseof.html（抜粋）" >}}
<pre>
.File.Filename = {{ .File.Filename }}
.File.Path = {{ .File.Path }}
.File.Dir = {{ .File.Dir }}
.File.LogicalName = {{ .File.LogicalName }}
.File.BaseFileName = {{ .File.BaseFileName }}
.File.Extension = {{ .File.Extension }}
</pre>
{{< /code >}}

例えば、`C:\Users\maku\website\content\diary\2018.md` から生成されたページ (`diary/2018.html`) にアクセスすると、以下のようなパス情報を取得することができます。

```ini
.File.Filename = C:\Users\maku\website\content\diary\2018.md
.File.Path = diary\2018.md
.File.Dir = diary\
.File.LogicalName = 2018.md
.File.BaseFileName = 2018
.File.Extension = md
```


Hugo サーバ動作させているときに Markdown ファイルのパスを表示する
----

テンプレート内に下記のように記述しておけば、Hugo のローカルサーバで Web サイトをホスティングしている場合のみ、ローカル PC 内の Markdown ファイルのパスを表示することができます。

```go-html-template
{{- /* Markdown ファイルのパスを表示 */}}
{{- if .Site.IsServer -}}
  <div style="text-align:right; font-size: smaller;">
    {{- with .File }}{{ .Filename }}{{ end -}}
  </div>
{{- end }}
```

記事内に表示されたパスをターミナル上にコピペして、任意のエディタで開くということが素早く行えるようになります。
タグの一覧ページなど、生成元の Markdown ファイルが存在しない場合は、`.File.Filename` の値は空っぽになることに注意してください。


応用
----

* [Hugo ページの生成元になった Markdown (.md) ファイルを VS Code で開くリンクを表示する](/p/9hkprvx/)

