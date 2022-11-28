---
title: "GitHub Actions で GitHub wiki ページを自動更新する"
url: "p/f2eggno/"
date: "2022-11-28"
tags: ["GitHub/Actions"]
---

何をするか？
----

GitHub で管理しているリポジトリに対してコミット＆プッシュが行われたときに、自動的に GitHub wiki 側のリポジトリの内容（Markdown ファイル）を更新するようにしてみます。
例えば、プロダクトのソースコードからドキュメントを自動生成して、開発者がいつでも GitHub wiki ページで参照できるようにしておくと便利です。

{{< image src="img-002.png" title="GitHub Wiki ページの自動生成" >}}

Wiki ページの更新には GitHub Actions のワークフローを使い、次のように自動実行されるよう設定します。

1. メインリポジトリを対象としたプッシュで GitHub Actions ワークフローを起動
2. メインリポジトリと Wiki リポジトリのソースコードを取得
3. 何らかの外部ツールを実行して Markdown ファイルを生成
3. Markdown ファイルを Wiki リポジトリにコミット＆プッシュ


ワークフローファイルの作成
----

GitHub wiki を自動更新するための、Actions のワークフローファイルを作成します。
`.yml` 拡張子のファイルを __`.github/workflows`__ ディレクトリ以下に配置してコミットすれば、GitHub サービス側でワークフローとして認識してくれます。
ここでは、__`update-wiki.yml`__ というファイル名にしてみました。

{{< code lang="yaml" title=".github/workflows/update-wiki.yml" >}}
name: Update wiki pages

on:
  push:
    branches: [ main, master ]
  workflow_dispatch:

jobs:
  update-wiki:
    runs-on: ubuntu-latest
    timeout-minutes: 3

    steps:
      - name: Check out main repo
        uses: actions/checkout@v3

      - name: Check out wiki repo
        uses: actions/checkout@v3
        with:
          repository: "${{ github.repository }}.wiki"
          path: .wiki

      - name: Update wiki pages
        run: echo -e "# Hello\n\n$(date)" > .wiki/hello.md

      - name: Stage and count changes
        working-directory: .wiki
        id: staging
        run: |
          git add .
          echo "NUM_OF_STAGED=$(git diff --staged --name-only | wc -l)" >> $GITHUB_OUTPUT

      - name: Commit wiki pages
        working-directory: .wiki
        if: steps.staging.outputs.NUM_OF_STAGED > 0
        run: |
          git config user.email "41898282+github-actions[bot]@users.noreply.github.com"
          git config user.name "github-actions[bot]"
          git commit -m "${GITHUB_WORKFLOW}"
          git push
{{< /code >}}

定義しているジョブは、`update-wiki` というジョブ 1 つだけです。
以下、このジョブの各ステップを順番に見ていきます。

### メインリポジトリのチェックアウト

```yaml
- name: Check out main repo
  uses: actions/checkout@v3
```

公式の `actions/checkout` アクションを使って、メインリポジトリのコードをチェックアウトしておきます。
実は今回のサンプルでは、メインリポジトリのコードは利用していないのですが、ソースコードをもとに Wiki ページを生成するのであれば必要になるでしょう。
Markdown ファイルを生成するためのスクリプトが、メインリポジトリの `tools` ディレクトリなどに入っているかもしれません。

### Wiki リポジトリのチェックアウト

```yaml
- name: Check out wiki repo
  uses: actions/checkout@v3
  with:
    repository: "${{ github.repository }}.wiki"
    path: .wiki
```

Wiki リポジトリの名前は、__`${{ github.repository }}.wiki`__ で参照できるので、`actions/checkout` アクションでリポジトリ名を指定してチェックアウトします。
チェックアウト先として、__`.wiki`__ という名前の作業ディレクトリを作成しています。
後のステップで、この中に Markdown ファイルを生成（修正）してコミットすることになります。

### Wiki ページ（Markdown ファイル）更新

```yaml
- name: Update wiki pages
  run: echo -e "# Hello\n\n$(date)" > .wiki/hello.md
```

ここが Wiki ページ生成の本質的なステップで、`.wiki` ディレクトリの中に Markdown ファイルを生成（修正）します。
ここではシンプルに、Hello というタイトルと現在の日時が書き込まれた `hello.md` ファイルを作成していますが、通常はもっと複雑な更新作業になるので、外部ツールなどを呼び出して Markdown ファイルを生成することになります。
例えば、次のようにメインリポジトリ側のシェルスクリプトを呼び出します（引数で出力先ディレクトリ `.wiki` の絶対パスを渡しておくと親切です）。

```yaml
- name: Update wiki pages
  run: ./tools/update-wiki.sh $GITHUB_WORKSPACE/.wiki
```

ちなみに、シェルスクリプトを Windows でコミットするときは、次のようにして実行権限を付けてコミットします。

```console
$ git update-index --add --chmod=+x tools/update-wiki.sh
```

### 変更内容のステージング

```yaml
- name: Stage and count changes
  working-directory: .wiki
  id: staging
  run: |
    git add .
    echo "NUM_OF_STAGED=$(git diff --staged --name-only | wc -l)" >> $GITHUB_OUTPUT
```

前述のステップで生成した Markdown ファイルを、Wiki リポジトリの方にステージング (`git add`) します。
ここでのポイントは、いくつのファイルがステージング状態になったか（変更されたか）を、__`$GITHUB_OUTPUT`__ に出力しておくことです。
例えば、3 つのファイルが変更されている場合は、`NUM_OF_STATE=3` のようなキー＆バリューを出力しておきます。
この値は、次のステップで使用します。

### Wiki リポジトリへのコミット＆プッシュ

```yaml
- name: Commit wiki pages
  working-directory: .wiki
  if: steps.staging.outputs.NUM_OF_STAGED > 0
  run: |
    git config user.email "41898282+github-actions[bot]@users.noreply.github.com"
    git config user.name "github-actions[bot]"
    git commit -m "${GITHUB_WORKFLOW}"
    git push
```

最後に、自動生成した Markdown ファイルを Wiki リポジトリへコミット＆プッシュします。
このステップは、前段のステップでセットした出力内容 __`steps.staging.outputs.NUM_OF_STAGED`__ の値が 0 より大きいとき、つまり、コミットすべきファイルが存在するときのみ実行します。
Wiki ページを変更する必要がない場合は、このステップはスキップされて、ワークフローの実行は終了します。

上記のように自力で `git commit` コマンドを呼び出す場合は、Git の `user.email` と `user.name` を設定しておかないとエラーになることに注意してください。
上記の例では、コミュニティで提案されている [`github-actions[bot]`](https://api.github.com/users/github-actions%5Bbot%5D) というユーザーを設定しています（参考: [Set git user and email](https://github.com/actions/checkout/issues/13#issuecomment-724415212)）。
このユーザーを指定しておくと、コミット履歴などで次のようなアイコンが表示されるようになります。

{{< image src="img-001.png" title="GitHub Actions ユーザーによる変更履歴" >}}


テスト
----

ワークフローファイルを `main` ブランチにコミットし、GitHub へプッシュすると、自動的に Markdown ファイル (`hello.md`) が生成され、Wiki リポジトリ側にコミットされます。
次のようなページが生成されていれば成功です。

{{< image src="img-002.png" title="GitHub Actions で自動生成された Wiki ページ" >}}

できたー ٩(๑❛ᴗ❛๑)۶ わーぃ
