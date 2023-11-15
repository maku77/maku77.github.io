---
title: "Git リポジトリ内のコードを grep 検索する (git grep)"
url: "p/2c29gvn/"
date: "2023-11-15"
tags: ["Git", "grep"]
---

git grep とは
----

Git に組み込まれている __`git grep`__ コマンドを使うと、Git リポジトリ内のコードを対象にした `grep` 検索を行うことができます。
Linux の `grep` コマンドを使うのと比べて、次のような利点があります。

- OS に依存しない `grep` コマンドとして使用できる
- `.gitignore` で指定されているファイルを無視して検索してくれる
- ある時点のコードを対象に検索できる（コミットハッシュの指定）
- ある文字列を含む関数を検索できる（`-p` オプション）

git grep の基本
----

{{< code lang="console" title="カレントディレクトリ以下を検索する" >}}
$ git grep "検索文字列"
{{< /code >}}

カレントディレクトリ以下のファイルを対象として `git grep` するには、上記のように単純に検索文字列を指定するだけで OK です。
もちろん、Git 管理されているディレクトリ以下で実行する必要があります。

検索対象のディレクトリやファイルを指定するには、次のように __`--`__ で区切ってからディレクトリ名やファイル名のパターンを指定します。

{{< code lang="console" title="src ディレクトリ以下を検索する" >}}
$ git grep "検索文字列" -- src
{{< /code >}}

{{< code lang="console" title="src ディレクトリ以下の .py ファイルを検索する" >}}
$ git grep "検索文字列" -- "src/**.py"
{{< /code >}}

ワイルドカードを指定してファイル名のパターンを指定するときは、`"src/**.py"` のようにダブルクォートで囲む必要があることに注意してください。
これは、`git grep` にパラメーターが渡される前にシェルがファイルグロブとして展開してしまうのを防ぐためです。

特定のディレクトリやファイルを検索対象外にしたいときは、__`:^`__ というプレフィックスを付けます。

{{< code lang="console" title=".github ディレクトリや package.json を検索対象外にする" >}}
$ git grep react -- :^.github ":^package*.json"
{{< /code >}}

拡張正規表現を使って検索したいときは、__`-E`__ オプションを指定します。

{{< code lang="console" title="正規表現を使って検索する" >}}
$ git grep -E "https?://"
{{< /code >}}

特定のコミットハッシュやブランチ、タグのコードを検索対象としたいときは、検索文字列の後ろにコミットハッシュなどを指定します。

{{< code lang="console" title="コミットハッシュを指定して検索する" >}}
$ git grep "検索文字列" ba17d26
{{< /code >}}

複数の検索パターンを AND や OR で組み合わせたいときは、`-e 検索文字列`（または `-E 拡張正規表現`）というパターン指定を、__`--and`__ や __`--or`__ で繋げて指定します。

{{< code lang="console" title="AND 検索（game と book を両方含む行を検索）" >}}
$ git grep -e "game" --and -e "book"
{{< /code >}}

{{< code lang="console" title="OR 検索（game または book を含む行を検索）" >}}
$ git grep -e "game" --or -e "book"
{{< /code >}}


git grep の応用的な使い方
----

`git grep` のその他のオプションは、__`git grep -h`__ で確認することができます。
例えば、次のようなオプションが用意されています。

- __`-i, --ignore-case`__ ... 大文字・小文字を区別しない
  - 例: `git grep -i "github"`
- __`-n, --line-number`__ ... 行番号を表示する
  - 例: `git grep -n "foobar"`
- __`-w, --word-regexp`__ ... 単語境界を考慮する
  - 例: `git grep -w "Git"`__ （Git にはヒットし、GitHub にはヒットしなくなります）
- __`-p, --show-function`__ ... ヒットした行を含む関数の先頭行も表示する
  - 例: `git grep -p "findUsers" -- "*.ts"` （`findUsers` を呼び出している関数を探すことができます）
- __`-h`__ ... 各行の先頭にファイル名を表示しない
- __`--recurse-submodules`__ ... サブモジュールのコードも含めて検索する

関数名を検索できる `-p, --show-function` オプションなどは面白いですね。

