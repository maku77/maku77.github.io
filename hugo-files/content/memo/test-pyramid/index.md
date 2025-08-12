---
title: "テストピラミッドを意識してテストの自動化を進める"
date: 2015-09-24
url: "p/y4ezh5u/"
tags: ["memo"]
aliases: ["/memo/test-pyramid.html"]
---

[Mike Cohn](https://www.mountaingoatsoftware.com/blog/the-forgotten-layer-of-the-test-automation-pyramid) が『Succeeding with Agile』で紹介した **test automation pyramid** というものがあります。
テストの自動化を進めるときは、このような比率になるように構築していくと、効率のよいテストを行えるようになります。

{{< image src="img-001.png" >}}

一言でいうと、UI のテストと比べ、ユニットテストの方が費用対効果 (ROI: Return on Investment) が高いということです。
ヒューリスティックな要素が入り込む End-to-End のテストは、頑張って自動化しても、思ったほどの効果が得られないことが多くなります。
テスト記述にかかるコスト、メンテナンスにかかるコストなどを総合的に判断すると、ユニットテストの比率を最も大きくし、その次に統合テスト、最後に End-to-End (UI) テストの順になるようにテスト配分を考えるべきです。
Google も下記のような比率で考えるとよいといっています。

* 10%: UI test (end-to-end test)
* 20%: Integration test (components and services)
* **70%**: Unit test

ごく当たり前のように感じる構成ですが、開発の現場でよく見られるのは、テスト専門チームによるテストに頼ってしまう逆ピラミッド型 (inverted pyramid/ice cream cone) の構成です。

* **70%**: UI test (end-to-end test)
* 20%: Integration test (components and services)
* 10%: Unit test

リファクタリングの技術、ユニットテストの技術を向上させ、理想的なピラミッド型のテスト配分に近づけていくことで、ソフトウェアの品質は上がっていきます。

