---
title: "Markdown (.md) ファイルのパス情報を取得する"
date: "2018-06-14"
description: "File 変数を参照すると、記事ページのもとになった Markdown ファイルのパス情報を取得することができます。"
---

File 変数の一覧は下記のページで参照できます。

- [File Variables｜Hugo](https://gohugo.io/variables/files/)


File 変数の使用例
----

#### テンプレートファイルの抜粋（layouts/_default/baseof.html など）

~~~
<pre>
.File.Filename = {{ "{{" }} .File.Filename }}
.File.Path = {{ "{{" }} .File.Path }}
.File.Dir = {{ "{{" }} .File.Dir }}
.File.LogicalName = {{ "{{" }} .File.LogicalName }}
.File.BaseFileName = {{ "{{" }} .File.BaseFileName }}
.File.Extension = {{ "{{" }} .File.Extension }}
</pre>
~~~

例えば、`C:\Users\maku\website\content\diary\2018.md` から生成されたページ (diary/2018.html) にアクセスすると、以下のようなパス情報を取得することができます。

~~~
.File.Filename = C:\Users\maku\website\content\diary\2018.md
.File.Path = diary\2018.md
.File.Dir = diary\
.File.LogicalName = 2018.md
.File.BaseFileName = 2018
.File.Extension = md
~~~


Hugo サーバ動作させているときに Markdown ファイルのパスを表示する
----

テンプレート内に下記のように記述しておけば、Hugo のローカルサーバで Web サイトをホスティングしている場合のみ、ローカル PC 内の Markdown ファイルのパスを表示することができます。

~~~
{{ "{{" }} if .Site.IsServer }}
  <div style="color:gray; text-align:right; font-size: smaller;">
    {{ "{{" }} .File.Filename }}
  </div>
{{ "{{" }} end }}
~~~

記事内に表示されたパスをターミナル上にコピペして、任意のエディタで開くということが素早く行えるようになります。

