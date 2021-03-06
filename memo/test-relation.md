---
title: "単体テスト、結合テスト、システムテスト、受入テストの関係を理解する"
date: "2012-08-15"
---

ソフトウェアのテストの話をするときは、どの粒度、観点からのテストの話をしているのかを認識することが大切です。
ここでは、単体テスト、結合テスト、システムテスト、受入テストの関係と全体像を掴みましょう。

![test-relation](test-relation.jpg)

システムテストと受け入れテスト
====

赤色の枠は、システム全体を示しています。
「システムテスト」は、システム全体を*開発者視点*でテストします。
「受入テスト」は、「システムテスト」と同様に、システム全体がテスト対象であり、テスト項目も多少かぶる部分がありますが、システム全体の振る舞いを*ユーザ（発注者）視点*でテストするところが異なります。

単体テストと結合テスト
====

青色の丸と、緑色の丸は、システム内の構成要素を捉えるときの粒度の違いを示しています。
例えば、

* 青丸 -- クラス
* 緑丸 -- 複数のクラスから構成されたコンポーネント

だと思ってください。
青丸の粒度の世界での「単体テスト」「結合テスト」もあるし、緑丸の粒度の世界での「単体テスト」「結合テスト」もあります。
「単体テスト」では、テスト対象を１つの要素に限定するため、問題を発見したときの問題個所の絞り込みが容易です。
「結合テスト」だけでは、単一要素に含まれる不具合を発見できないことがあります。
潜在的な不具合を防ぐには、「単体テスト」が重要な役割を果たすといえます。
大規模なシステムになるほど「単体テスト」の重要性は増してきますが、各モジュールの依存関係をあらかじめ考慮した上で設計を進めないと、「単体テスト」が行えないコードができてしまいます。
そうならないためにも、実装を進める前に、

* テスト仕様（インタフェース仕様）
* テストコード

の作成を行い、テストファーストで実装していくのが効果的です。
特に、複数人で開発を進める場合、テストを作成することで、最終的に完成するもののイメージを共有するという目的もあります。
ただし、テスト対象となる要素が単純な演算ライブラリのようなものでない限り、いざ、「単体テスト」を行う場面になったときに、他の要素との依存関係に悩まされるでしょう。
「単体テスト」は、あくまで単一の要素の動作を検証しなければならないので、他の要素の実装の進捗によってテスト結果が変わるようなことがあってはいけません。
そんなときに、以下のようなダミーモジュールを作成し、他の要素の動作を切り離します。

* ドライバ -- テスト対象を起動、操作するためのモジュール
* スタブ -- テスト対象内部で呼び出している下位のモジュール

ドライバ、スタブモジュールの作成自体が困難だと思った場合は、そのモジュールに責務を詰め込みすぎていないか、設計を見直すべき合図でもあります。

システムの実装は、下位モジュールからボトムアップで完成していくものなので、テストが成功していく順番も、「単体テスト」→「結合テスト」の順になります。
とはいえ、最終的なシステム全体の振る舞いを表現しているのは、より上位のテストです。
テスト仕様は、トップダウンで作っていくのが望ましいでしょう。
