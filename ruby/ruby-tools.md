---
title: 知っておくべき Ruby 関連ツール
date: "2015-11-22"
---

すべては RubyGems から始まる
====
**RubyGems** は、デファクトスタンダードとなった Ruby のパッケージ管理システムです。`gem` コマンドで **Gem** というパッケージをインストールします。Ruby 関連の多くのライブラリやコマンドは、今やこの `gem` コマンドでインストールすることができるようになっており、後述の `rdoc`, `yard`, `rake`, `rvm` などは全て **Gem** として提供されています。

知っておくべき Gem の紹介
====

環境関連
----

### [bundler](https://rubygems.org/gems/bundler/)

プロジェクトで使用する Gem パッケージを一括でインストールできるようにするツールです。`Gemfile` というファイルに必要な Gem を記述しておくと、`bundle install` コマンドで一括インストールできるようになります。

`gem install` コマンドが、システム全体で使用する Gem をインストールするのに比べ、`bundle install` コマンドは、特定のプロジェクト用に限られたスコープで使用する Gem をインストールします。

### [rake](https://rubygems.org/gems/rake/)

C/C++ の世界でいう Make の Ruby 版です（Java であれば ant や Gradle に相当するもの、Javascript であれば Grunt や gulp に相当するものです）。`Rakefile` という Ruby 構文で記述されたファイルにビルドタスクを定義していくことができます。例えば、rdoc による API ドキュメント生成、RSpec によるユニットテスト実行、Gem のパッケージング処理といったタスクを定義することができます。

### [rvm](https://rubygems.org/gems/rvm/)

Ruby 実行環境を管理するツールです。複数バージョンの Ruby 処理系を切り替えて使うことができるようになるので、様々なバージョンの Ruby でプログラムの実行テストを行うことができます。


ドキュメント関連
----

### [rdoc](https://rubygems.org/gems/rdoc/)

伝統的に使用されてきた API ドキュメント生成ツールです。RD というフォーマットがまわりくどく、メソッドのパラメータ情報などを記述するときに手間がかかるのが難点です。

### [yard](https://rubygems.org/gems/yard/)

後発の API ドキュメント生成ツールで、rdoc の置き換えを狙っています。Javadoc のように、`@param` というタグ形式でパラメータなどの情報を記述していくことができるので、見やすく、かつ簡潔に記述できます。HTML 生成時などは、内部的に rdoc を使用しているようです。


ユニットテスト関連
----

### [rspec](https://rubygems.org/gems/rspec/)

Ruby のユニットテストフレームワーク。rspec 用のテストコードは、記述的な表現になるため、英文のように読めるコードを作成できるのが特徴的です。

### [test-unit](https://rubygems.org/gems/test-unit/)

Ruby のユニットテストフレームワーク。JUnit などのように、いわゆる xUnit 記法でテストコードを記述していくための assert メソッドを提供しています。

