---
title: Best Practices メモ
layout: category-index
---

設計ノウハウ／ベストプラクティス
====
* [不具合修正に対する向き合い方](attitude-to-defect.html)
* [動的品質よりも静的品質の向上に力を入れる](static-and-dynamic-quality.html)
* [プログラム内のコメントの書き方 (Javadoc ドキュメンテーションコメントの書き方）](how-to-write-comment.html)
* [アプリ内の名前空間（Java のパッケージ階層）に迷った時のヒント](namespace-in-app.html)
* [PMD が警告するゴッドクラス (God Class) とは](god-class.html)
* [スケジューリングや見積りの段階では要求と設計要素のマトリクスで考える](request-factor-matrix.html)
* [可変オブジェクトのメンバ参照を返さない](avoid-returning-mutable-reference.html)
* [プロジェクト内での null の扱い方をルール化する](clarify-how-to-use-null.html)
* [時制や単数形・複数形を考慮して命名する](tense-and-plural.html)
* [肯定形で表現する](prefer-positive-sentence.html)
* [単位を明確にする](clarify-unit.html)
* [設定値の伝搬タイミングを意識する](timing-of-propagation.html)
* [型変換用メソッドは受け取り側クラスに作る](api-convert-type.html)
* [静的チェックのレベルはプロジェクト初期に厳しくする](strict-analysis-in-the-beginning.html)
* [リソースは finally ブロックで閉じる](finally-close.html)
* [コメント内で使える特殊キーワード（XXX、TODO など）を理解する](practice/keyword-in-comment.html)
* [よいツールの条件](good-tools.html)

デザインパターン
====
* [Visitor パターン](dp-visitor-pattern.html)

テスト
====
* [テストピラミッドを意識してテストの自動化を進める](test-pyramid.html)
* [単体テスト、結合テスト、システムテスト、受入テストの関係を理解する](test-relation.html)

アジャイルプラクティス
====
* [無駄な汎用性ではなくシンプルな設計を (YAGNI)](simple-design.html)

設計ドキュメント／設計ツール
====
* [Enterprise Architect の図をシンプルにして Power Point に貼り付ける](tool/ea-to-powerpoint.html)
* [テキストからシーケンス図を作成するツール](tool-sdedit.html)

ブックレビュー／読書メモ
====
* [『シンプルに生きれば、すべてがうまくいく！』西村豪庸](book/simple-living.html)
* [『僕らが毎日やっている最強の読み方』佐藤優／池上彰](book/saikyo-no-yomikata.html)
* [『99％の会社はいらない』堀江貴文](book/99percent.html)
* [『ゼロ』堀江貴文](book/zero.html)
* [『仕事は楽しいかね』デイル・ドーテン](book/shigo-tano1.html)
* [『仕事は楽しいかね２』デイル・ドーテン](book/shigo-tano2.html)
* [『ピープルウェア』トム・デマルコ](book/peopleware.html)
* [『レバレッジ・シンキング』本田直之](book/reverage-thinking.html)
* [『ピーター流外国語習得術』ピーター・フランクル](book/peter.html)
* [『わたしの外国語学習法』ロンブ・カトー](book/kato-lomb.html)

Jenkins
====
* [Jenkins CLI を使ってコマンドラインから Jenkins を操作する](jenkins/cli.html)
* [Groovy スクリプトで Jenkins サーバを制御する](jenkins/cli-groovy.html)
* [Groovy スクリプトで Jenkins 上のすべての Job を制御する](jenkins/handle-jobs.html)
* [Groovy スクリプトで Jenkins 上のすべてのスレーブを制御する](jenkins/handle-slaves.html)
* [Jenkins ジョブの設定 (config.xml) を確認する](jenkins/config-xml.html)
* [お試しインスタンスとして Jenkins サーバを起動する](jenkins/trial-server.html)
* [Jenkins サーバの設定ファイルの場所](jenkins/settings-dir.html)
* [Amazon EC2 に Jenkins をインストールする](jenkins/amazon-ec2.html)

XML
====
* [意外と知られていない XML 記述のルール](xml/xml-rules.html)
* [XML の名前空間](xml/namespace.html)

言語別の構文
====

* [スクリプト言語の文法比較など](program/comparison.html)

ツール
====
* [CPD でコードクローンを発見する](tool/detect-code-clones.html)
* [TFTP の使い方（TFTP によるファイル転送）](tool/tftp.html)
* [Windows (MinGW) で GLUT を使用する](tool/glut-in-windows.html)
* [Tera Term のマクロで特定の文字列を検出して処理を実行する](tool/teraterm-detect-pattern.html)

パッケージ管理
----
* [パッケージ管理ツールいろいろ](common-package-management.html)
* [Python のパッケージ管理ツール (pip)](/python/pip.html)


暗号／セキュリティ
====
* [共通鍵暗号化方式と公開鍵暗号化方式](security/encryption-scheme.html)
* [SSH 鍵の作成と登録](security/ssh-keygen.html)
* [SSH 接続用のエイリアスを設定する (~/.ssh/config)](security/ssh-alias.html)


ネットワーク
====
* [IP によるインターネットワーキングの基礎（昔々2000年くらいに書いた記事）](./ip-networking/)
* [GENA と SSDP プロトコルを理解する](network/ssdp.html)


ソフトウェア開発参考リンク
====
* [Introduction to Software Engineering/Quality/Metrics](https://en.wikibooks.org/wiki/Introduction_to_Software_Engineering/Quality/Metrics) いろんなソフトウェアメトリクス

