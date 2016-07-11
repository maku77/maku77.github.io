---
title: "Express/Express の特徴"
created: 2013-10-21
---

Express は Node.js で簡単に Web サーバを構築できるようにするためのモジュールで、下記のような特徴を持っています。

* Node 自体に Web server API はあるが、Low level すぎて使いにくいので、Express モジュールを使うことで簡単に Web server を構築できる。
* URI ルーティング、セッション/クッキー管理、MIME、HAML、RESTful インタフェースなどのサポートをしている。
* Express は Ruby の Sinatra にインスパイアされて開発された。
* 昔似たようなプロジェクトとして、Ruby の Rack に影響された Connect という Node module もあったが、2010 年に Express と Connect の両方のプロジェクトに参加していた T.J. Holowaychuk が統合して Express v1.0.0 になった。
* メインのモジュール（require('express') でロードされるモジュール）は、基本的な機能だけを提供しており、Node module の形で提供される拡張モジュールを組み合わせて、機能を追加していく考え方。

