---
title: "corepack で yarn や pnpm などのパッケージマネージャーを管理する"
url: "p/tzwio3y/"
date: "2024-01-25"
tags: ["nodejs"]
---

corepack とは
----

Node.js の実行環境には、パッケージマネージャーとして標準で `npm` コマンドが搭載されていますが、`npm` の代わりに `yarn` や `pnpm` などを導入しているプロジェクトが多くあります。
従来は、これらのコマンドを `npm install -g pnpm` のように別途インストールする必要がありましたが、Node.js に標準搭載された [`corepack`](https://github.com/nodejs/corepack) を使用すると、初めからインストールされているかのように`yarn` や `pnpm` コマンドを実行できるようになります。

また、使用するパッケージマネージャーを、`package.json` の __`packageManager`__ プロパティで管理することができるので、プロジェクト内で使用するパッケージマネージャーとそのバージョンを統一できます。


corepack を有効化する
----

`corepack` コマンドは `Node.js` 環境に組み込まれていますが、Node.js 21 の時点ではまだ実験段階 (experimental status) のため、__`corepack enable`__ コマンドを実行して明示的に有効化 (Opt-in) しておく必要があります。
このコマンドは、システム内で一度だけ実行すれば大丈夫です。

{{< code lang="console" title="corepack によるパッケージマネージャー管理を有効化" >}}
$ corepack enable
{{< /code >}}

このコマンドを実行することで、`node` コマンドの実行ファイルが置かれたディレクトリ内に、`yarn` や `pnpm` などのシンボリックリンクが生成されます。
これで、システム全体で `yarn` や `pnpm` コマンドを実行できるようになります。

もし、`npm install` や `brew` でグローバルインストールした `yarn` や `pnpm` がある場合は、もう必要ないのでアンインストールしておきましょう。

{{< code lang="console" title="不要な yarn や pnpm はアンインストール" >}}
$ npm uninstall -g yarn
$ brew uninstall yarn
{{< /code >}}

各パッケージマネージャーのコマンド（シンボリックリンク）が、`node` と同じ場所に存在していれば、おそらくうまくいっています。

```console
$ which node npm yarn pnpm
/Users/maku/.nvm/versions/node/v21.6.1/bin/node
/Users/maku/.nvm/versions/node/v21.6.1/bin/npm
/Users/maku/.nvm/versions/node/v21.6.1/bin/yarn
/Users/maku/.nvm/versions/node/v21.6.1/bin/pnpm
```


corepack で yarn や pnpm を使用するよう設定する
----

`corepack` 経由の `yarn` や `pnpm` を使用する場合、`package.json` ファイルの __`packageManager`__ プロパティで、使用するパッケージマネージャーとそのバージョンを定義しておくことができます。
ここでは、テスト用の Node.js アプリを使って設定してみます。

{{< code lang="console" title="テスト用のアプリを新規作成" >}}
$ mkdir myapp && cd myapp
$ npm init -y
{{< /code >}}

プロジェクト内で使用するパッケージマネージャーは、__`corepack use`__ コマンドで指定します。

{{< code lang="console" title="使用するパッケージマネージャーを定義" >}}
$ corepack use npm@*   # npm の最新版を使う場合
$ corepack use npm@10  # npm バージョン 10.x.x を使う場合

$ corepack use yarn@*  # yarn の最新版を使う場合
$ corepack use yarn@4  # yarn バージョン 4.x.x を使う場合

$ corepack use pnpm@*  # pnpm の最新版を使う場合
$ corepack use pnpm@9  # pnpm バージョン 9.x.x を使う場合
{{< /code >}}

例えば、パッケージマネージャーとして `pnpm` を使うよう指定した場合、`package.json` ファイルに次のように記録されます。

{{< code lang="js" title="package.json" hl_lines="5" >}}
{
  "name": "myapp",
  "version": "1.0.0",
  // ...
  "packageManager": "pnpm@9.0.0-alpha.0+sha256.73ada1a82ae5a6...（省略）..."
}
{{< /code >}}

あとは、通常通り `yarn` や `pnpm` コマンドを使って Node パッケージをインストールしていけば OK です。

```console
$ pnpm install dotenv
```

使用する `pnpm` のバージョンは `package.json` に記述されているので、チーム内の他のメンバーが `pnpm` コマンドを実行するときも同じバージョンが使われるようになります。

{{< code lang="console" title="他のメンバー（や CI/CD）の環境でビルドするとき" >}}
$ corepack enable  # （システム内で一度だけ実行）
$ pnpm install     # 指定された pnpm バージョンを使って依存パッケージをインストール
$ pnpm build       # build スクリプトの実行
{{< /code >}}

