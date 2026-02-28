---
title: "Rubyメモ: Gem パッケージを作成する (3) パッケージを公開する"
url: "p/sjzsisu/"
date: "2017-08-07"
tags: ["ruby"]
aliases: ["/ruby/gem/publish-gem.html"]
---

## Gem パッケージの準備

ここでは、作成した Gem パッケージを [RubyGems.org](https://rubygems.org/) へ公開する手順を示します。
Gem パッケージの作成方法に関しては、下記の記事を参考にしてください。

* [Gem パッケージを作成する (1) 基本](/p/k6gcrgj/)
* [Gem パッケージを作成する (2) 実行可能コマンドを追加する](/p/sf4zv8i/)


## RubyGems.org にアカウントを作成する

作成した Gem パッケージを [RubyGems.org](https://rubygems.org/) にアップロードするには、下記のサインアップページからアカウントを作成しておく必要があります。

* [RubyGems.org - Sign up](https://rubygems.org/sign_up)

メールアドレスとアカウント名、パスワードを入力すると確認メールが届くので、そこに記載されたアドレスにアクセスして Verify すれば完了です。


## Gem パッケージをアップロードする

RubyGems.org にアカウントを作成したら、`gem push` コマンドを使って Gem パッケージを公開できます。

```
$ gem push hello-1.0.0.gem
Enter your RubyGems.org credentials.
Don't have an account yet? Create one at https://rubygems.org/sign_up
   Email: yourname@example.com
Password: ********
Signed in.
Pushing gem to RubyGems.org...
Successfully registered gem: hello (1.0.0)
```

初めて `gem push` コマンドを実行するときは、上記のように、RubyGems.org のアカウント名とパスワードを入力する必要があります。

数分後、RubyGems.org 上で、登録した Gem パッケージを参照できるようになります。

```
$ gem list -r -d hello

*** REMOTE GEMS ***

gradleww (1.0.0)
    Author: maku77
    Homepage: https://github.com/maku77/hello

    Hello Gem!
```


## （コラム）アクセス用の鍵ファイルを取得・設定する

`gem push` による Gem パッケージのアップロード時には、アクセス用の鍵ファイルとして `~/.gem/credentials` というファイルが参照されます。
ここには RubyGems.org の Web API を使用するための API キーが保存されており、このファイルがあれば `gem push` 実行時に、アカウント名とパスワードを入力する必要がなくなります。
このファイルは、最初に `gem push` の実行に成功したときに自動的に作成されるため、手動で作成する必要はありません（最初の実行時だけアカウント名とパスワードの入力を求められます）。

必要があれば、下記のように RubyGems.org のサイトから手動で取得できます。

### Linux / macOS の場合

Linux ベースのシステムでは、下記のように curl コマンドで取得できます（このコマンドは RubyGems.org の[プロファイル設定ページ](https://rubygems.org/profile/edit)で確認できます）。

```
$ curl -u UserName https://rubygems.org/api/v1/api_key.yaml > ~/.gem/credentials; chmod 0600 ~/.gem/credentials

Enter host password for user 'UserName': *******
```

### Windows の場合

鍵ファイル (`api_key.yaml`) は、Web ブラウザでダウンロードすることもできます。
下記のサイトにブラウザからアクセスし、登録したユーザ名とパスワードを入力してください。

* [RubyGems.org - api_key.yaml](https://rubygems.org/api/v1/api_key.yaml)

ダウンロードしたら、ファイル名を `credentials` に変更し、ホームディレクトリの `.gem` ディレクトリ内に保存してください。
Windows 環境で、Ruby がホームディレクトリとして認識しているディレクトリは、下記のコマンドで確認できます（通常は `%HOME%` か `%USERPROFILE%` 環境変数で参照できるディレクトリが表示されるはずです）。

```
C:\> gem environment gempath
C:/Users/maku/.gem/ruby/2.4.0;C:/app/Ruby/lib/ruby/gems/2.4.0
```

上記の場合は、`C:\Users\maku\.gem\credentials` として保存すればよいことになります。
