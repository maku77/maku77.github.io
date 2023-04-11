---
title: "Hugo でサイト構造を変えてもページの URL が変わらないようにする (Permalink)"
url: "p/u9r9p7n/"
date: "2019-10-20"
lastmod: "2022-07-27"
tags: ["Hugo"]
---

各ページの URL は変わってはいけない
----

Hugo によって出力される HTML ファイルのパスは、デフォルトでは `content` ディレクトリ以下のセクション構成（ディレクトリ構成）やファイル名によって決定されます。
サイトの規模が小さいうちはこれでもよいのですが、Markdown ファイル数が増えてくると、ディレクトリ構造を変えたり、ファイル名を整理したくなってきます。
それによってページの URL がころころ変わってしまうと、せっかくリンクを張ってくれたサイトがあっても、すべてリンク切れになってしまいます。

このようなリンク切れを起こさないようにするのが __パーマリンク (permalink)__ という考え方で、各ページに不変の ID を割り当てて URL が変わらないようにします。
例えば、Amazon の各商品のページは 10 桁の ISBN-10 という ID でアクセスできるようになっています。

```
https://www.amazon.co.jp/dp/4592146980/
```

Hugo でも、各ページにこのような ID ベースの URL を割り当てることができます。

{{< code title="不変の URL を割り当てた例" >}}
https://<your-hugo-site>/p/abc1234/
{{< /code >}}


Hugo で出力するページに固定の URL を割り当てる
----

Markdown ファイルのフロントマターに __`url`__ プロパティを設定すると、そのページの URL を固定することができます。

```yaml
---
title: "ページタイトル"
url: "p/abc1234/"
date: "2019-10-20"
---
```

例えば、上記のようなフロントマターを記述しておくと、そのページには必ず `https://ドメイン名/p/abc1234/` というアドレスでアクセスできるようになります。
一階層目のパスは別の用途で使用する可能性があるため、ここでは `p` という階層を掘って、permalink 化した記事を格納するようにしています（Amazon の `dp` と同様です）。
このようにすることで、Markdown ファイルのファイル名や記事タイトル、ディレクトリ構成を変えても、最終的な URL を変化させずに済みます。

### Hugo 搭載の permalink 機能は使わない

Hugo には他にも、設定ファイルで permalink に関する設定を行う仕組みが用意されていますが、こちらは日付や記事タイトルに基づいて URL が決まるものであり、URL を固定する用途にはあまり向いていません。
また、フロントマターで `aliases` プロパティを指定することもできますが、こちらはリダイレクト用の HTML ファイルを追加出力するものであり、最終ターゲットとなるページの URL を制御することはできません。
このような理由により、ページのリンク切れの心配をなくすには、__`url` プロパティで変化しない ID を割り当てる__ ことをお勧めします。

### 多言語対応ページでは url の先頭にスラッシュを付けない

Hugo は 1 つのページを [多言語化してホスティング](https://gohugo.io/content-management/multilingual/) する機能を持っており、例えば、フランス語に翻訳したページの URL は、`/fr/p/xxxxxxx/` のように、先頭に `/fr` という言語プレフィックスが付いたものになります。
各言語用の `.md` ファイルは次のようなファイル名で作成するのですが、

- `hello.md`（デフォルト言語のページ）
- `hello.en.md`（英語のページ）
- `hello.fr.md`（フランス語のページ）

このとき、フロントマターの `url` プロパティには `en` や `fr` やなどの言語プレフィックスを付ける必要はありません。

```yaml
url: "p/abc1234/"  ← 全ページ共通でこの指定で OK
```

多言語対応ページをビルドしたときに、自動的に言語プレフィックスを考慮したパスに HTML ファイルを出力してくれます（[Hugo 0.55 以降](https://gohugo.io/content-management/urls/#set-url-in-front-matter)）。

- `/p/abc1234/index.html`
- `/en/p/abc1234/index.html`
- `/fr/p/abc1234/index.html`

ただし、フロントマターの `url` プロパティの値をスラッシュ (`/`) で始めると、こういった言語プレフィックスの考慮はなくなり、指定したままのパスで HTML 出力されることになるので注意してください。

```yaml
url: "/p/abc1234/"  ← スラッシュで始めるように変えた
```

上記のように記述すると、必ず次のようなパスで HTML 出力されることになります。

- `/p/abc1234/index.html`

通常は、__`url` の値はスラッシュで始めないようにする__ のがよさそうです。


ランダム ID の生成方法
----

本記事の例では、ページの URL に 7 桁の ID を割り当てていますが、この ID は下記のサイトでランダム文字列として生成することができます。

- [ランダムID生成 - まくろぐ](https://maku.blog/p/3nx9is3)

毎回手動で ID を生成するのが面倒であれば、エディタのマクロなどで自動的に Markdown ファイルのフロントマターを挿入できるようにしておくと便利です。
下記にフロントマターの自動挿入方法をいくつか紹介します。

### （応用）Archetypes でフロントマターを自動挿入

Hugo プロジェクトのルートに、`archetypes/default.md` というファイルを作成しておくと、`hugo new` コマンドで記事ページを生成したときに、その内容が雛形として使用されます（Hugo の Archetypes という機能です）。

次のサンプルでは、自動でフロントマターにランダムな `url` プロパティを挿入するようにしています。

{{< code lang="go-html-template" title="archetypes/default.md" >}}
---
title: ""
url: "p/{{ template "permanentId" }}/"
date: "{{ now.Format "2006-01-02" }}"
tags: [""]
---

{{ define "permanentId" -}}
  {{- $scratch := newScratch -}}
  {{- range (seq 7) -}}
    {{- $nextCh := slice "2" "3" "4" "5" "6" "7" "8" "9" "a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "m" "n" "o" "p" "q" "r" "s" "t" "u" "v" "w" "x" "y" "z" | shuffle | first 1 -}}
    {{- $scratch.Add "id" $nextCh -}}
  {{- end -}}
  {{- delimit ($scratch.Get "id") "" -}}
{{- end -}}
{{< /code >}}

ここでは、`permanentId` という内部テンプレートを定義して、`xpya1z3` という感じのランダム文字列を生成しています（もうちょっと綺麗に生成する方法があればいいんですが）。

Hugo プロジェクトのルートで次のようにコマンドを実行すれば、新しい記事ファイルが生成され、その先頭にフロントマターが挿入されます。

```console
$ hugo new sample.md
```

例えば、次のような感じのファイルが生成されます（デフォルトで `content` ディレクトリ以下に生成されます）。

{{< code lang="yaml" title="content/sample.md" >}}
---
title: ""
url: "p/es3qakw/"
date: "2020-03-16"
tags: [""]
---
{{< /code >}}

### （応用）Vim エディタでフロントマターを自動挿入

Vim などのエディタを使っている人は、エディタのマクロ機能でフロントマターを挿入できるようにしておくと便利です。
下記の Vim スクリプトは、現在編集中のファイルの先頭に、`date` プロパティや `url` プロパティを挿入する `Hugo` コマンドを定義します。

{{< code lang="vim" title="~/vimrc_hugo.vim" >}}
" 0〜n-1 の範囲のランダム整数を生成する
function! s:RandNum(n)
  return reltime()[1] % (a:n)
endfunction

" 指定した長さのランダム文字列を生成する
function! s:RandStr(length)
  let chars = '23456789abcdefghijkmnopqrstuvwxzy'
  let n = len(chars)
  let result = ''
  for i in range(a:length)
    let result .= chars[s:RandNum(n)]
  endfor
  return result
endfunction

" 先頭行に Hugo 用のフロントマターを挿入する
function! s:InsertHugoFrontMatter()
  let title = "title: \"\"\n"
  let url = "url: \"p/" . s:RandStr(7) . "/\"\n"
  let date = "date: \"" . strftime("%Y-%m-%d") . "\"\n"
  let tags = "tags: [\"\"]\n"
  execute ":normal gg"
  execute ":normal I" . "---\n" . title . url . date . tags . "---\n\n"
  execute ":normal gg"
endfunction

" Hugo コマンドを定義する
command! Hugo call s:InsertHugoFrontMatter()
{{< /code >}}

このスクリプトファイルを `~/.vimrc` などから下記のように読みこんでおくか、直接コードを `~/.vimrc` にコピペしておけば、Vim エディタ上で __`Hugo`__ コマンドを実行できるようになります。

{{< code lang="vim" title="~/.vimrc（Windows の場合は %HOME%/_vimrc）" >}}
source <sfile>:p:h/vimrc_hugo.vim
{{< /code >}}

ファイル名の前のあやしい記号群は、同じディレクトリにある別のファイルを読み込むためのおまじないです。
フルパスで指定しておいても OK です。

Vim エディタから下記のようにコマンドを実行すると、

```vim
:Hugo
```

現在編集中のファイルに、次のようなフロントマターが挿入されます。

```yaml
---
title: ""
url: "p/4nzaju5/"
date: "2019-10-20"
tags: [""]
---
```


（応用）GitHub の Issue や PullRequest からショートカット記法でリンクを張る
----

GitHub のオートリンク機能を使うと、GitHub リポジトリの Issue や PR の Markdown ドキュメントの中から、簡単な ID 指定で Hugo のページにリンクを張れるようにようになります。

- 参考: [GitHub のオートリンク機能で外部サイトへ ID ベースでリンクする (autolink) - まくまくGitノート](/p/ias8kt3/)

例えば、`https://blog.example.com/p/1234abc/` というアドレスの代わりに、`BLOG-1234abc` と記述するだけで済むようになります。
本記事で紹介したように Hugo のページを ID ベースで管理する場合は、GitHub のオートリンク機能も合わせて利用すると幸せになれるかもしれません。


参考
----

出力される HTML ファイルのパスが変更されると、それに付随する画像ファイルとの位置関係が変わって、画像の表示ができなくなってしまうかもしれません。
下記の記事で紹介しているショートコードを使って画像を表示するようにしておくと、このような心配がなくなります。

- 参考: [画像ファイルを Markdown ファイルと同じディレクトリに置く (Page Bundle)](/p/9n8p6n4/)

