---
title: "Facebook や Twitter の SNS で URL をシェアするときの表示設定 (OGP: Open Graph Protocol)"
date: "2020-03-15"
description: "Web ページに OGP (Open Graph Protocol) に基づいたメタ情報を記述しておくと、SNS アプリで URL をシェアしたときに表示される内容をカスタマイズできます。"
---

![ogp-001.png](ogp-001.png){: .center }

Open Graph のメタ情報として、どのような HTML タグを記述すればよいかは、下記のサイトを参考にしてください。

- 参考: [Facebook や Twitter でシェアするときに画像や説明文が表示されるようにする (OGP: Open Graph Protocol)](https://maku.blog/p/awakw8i/)


Open Graph タグを出力するためのパーシャルテンプレート
----

ここでは、Hugo のパーシャルテンプレートで Open Graph タグを出力するようにしてみます。

#### layouts/partials/head/ogp.html

```
<meta property="og:site_name" content="{{ "{{" }} .Site.Title }}" />
<meta property="og:title" content="{{ "{{" }} .Title }}" />
<meta property="og:type" content="website" />
<meta property="og:url" content="{{ "{{" }} .Permalink }}" />
<meta property="og:locale" content="ja_JP" />

{{ "{{" }}- if .Params.image }}
  {{ "{{" }}- $imageRes := .Resources.GetMatch .Params.image -}}
  <meta property="og:image" content="{{ "{{" }} $imageRes.Permalink }}" />
{{ "{{" }}- else if .Site.Params.image }}
  <meta property="og:image" content="{{ "{{" }} .Site.Params.image | absURL }}" />
{{ "{{" }}- end }}

{{ "{{" }}- if .Description }}
  <meta property="og:description" content="{{ "{{" }} .Description }}" />
{{ "{{" }}- else if .Summary }}
  <meta property="og:description" content="{{ "{{" }} .Summary }}" />
{{ "{{" }}- else if .Site.Params.description }}
  <meta property="og:description" content="{{ "{{" }} .Site.Params.description }}" />
{{ "{{" }}- end }}

{{ "{{" }}- with .Site.Params.facebookAppId }}
  <meta property="fb:app_id" content="{{ "{{" }} . }}" />
{{ "{{" }}- end }}
```

画像 (`og:image`) や説明文 (`og:description`) などの内容は、いくつかフォールバックの仕組みを入れて、次のように出力するようにしています。

### og:image

ページバンドルとして画像ファイルを含んでいる場合、フロントマターの `image` プロパティでそのファイル名を指定することで、Open Graph の画像として表示できるようにしています。

#### フロントマターの記述例

```
---
title: "ページタイトル"
date: "2020-03-15"
image: "sample.png"
---
```

その指定がない場合は、サイトの設定ファイル (`config.toml`) の独自プロパティ `params.image` で指定した画像を使用します。

#### config.toml の記述例（抜粋）

```toml
[params]
  image = "assets/img/site-logo-large.png"
```

### og:description

ページの説明文を表す `og:description` には、下記のような優先度で見つかったものを設定します。

1. フロントマターの `description` プロパティに記述されたテキスト
2. ページ本文があれば、`.Page.Summary` で求められる要約テキスト（本文の先頭部分）
3. サイトの設定ファイル `config.toml` の独自プロパティ `params.description` に記述されたテキスト

これは 1 番だけにしておいた方がいいかもしれません。。。

### fb:app_id

Facebook の公式ドキュメントによると、Facebook のアプリ ID も指定しておく必要があるとされています。
Facebook のアプリ ID は、サイトの設定ファイル `config.toml` の独自プロパティ `params.facebookAppId` で指定できるようにしています。

```toml
[params]
  facebookAppId = "1234567890123456"
```


head 要素に Open Graph メタ情報を埋め込む
----

上記のように作成したパーシャルテンプレートは、各ページの `head` 要素内に展開されるようにします。
ここでは、全てのページに適用するために、[ベーステンプレート](../template/base-template.html) 内から読み込むようにしてみます。

#### layouts/_default/baseof.html の抜粋

```
<!DOCTYPE html>
<html lang="{{ "{{" }} .Site.LanguageCode }}">
<head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb#">
<head>
  <meta charset="UTF-8">
  {{ "{{" }} partial "head/ogp" . }}
  ...
```

`html` 要素か `head` 要素で、メタ情報用のプレフィックス定義が必要なことに注意してください。

これで、Facebook や Twitter といった SNS アプリでサイトの URL が共有されたときに、アイキャッチ画像や説明文が表示されるようになります。


（おまけ）親セクションの image プロパティを採用する
----

細かいページをたくさん作っている場合は、各ページごとにアイキャッチ画像を用意するのは大変です。
そこで、各ページが所属するセクションや、親セクションのフロントマターに設定された `image` プロパティを見て `og:image` 情報を設定するようにしてみます。
もちろん、自分自身のページのフロントマターに `image` プロパティが指定されていれば、そちらを優先的に使用します。

#### layouts/partials/head/ogp.html の og:image 出力部分抜粋

```
{{ "{{" }}- define "og_image" }}
  {{ "{{" }}- if .Params.image }}
    {{ "{{" }}- $imageRes := .Resources.GetMatch .Params.image -}}
    <meta property="og:image" content="{{ "{{" }} $imageRes.Permalink }}" />
  {{ "{{" }}- else if .Parent }}
    {{ "{{" }}- template "og_image" .Parent }}
  {{ "{{" }}- else if .Site.Params.image }}
    <meta property="og:image" content="{{ "{{" }} .Site.Params.image | absURL }}" />
  {{ "{{" }}- end }}
{{ "{{" }}- end }}

{{ "{{" }}- template "og_image" . }}
```

