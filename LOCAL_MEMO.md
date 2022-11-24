パーマリンク対応手順 <!-- 2022-05-15 -->
----

- maku77.github.io 内 Hugo へ移行する場合
  - `url: "p/xxxxxxx/"` を追加（Hugo 標準）
  - `aliases: /java/xxxx.html`（元の Jekyll 版の URL）
  - インデックスページのリンクを __`/p/xxxxxxx/`__ に変える（前後にスラッシュを付ける）

- Blog へ移行する場合
  - maku77.github.io (Hugo) 内に転送用のページを作る
  - まとめて aliases ディレクトリとかに入れておく（ファイル名はもとの html ファイル名が分かりやすい感じで）
    ```yaml
    ---
    title: "SSH 鍵の作成と登録"
    url: "/memo/security/ssh-keygen.html" （元の Jekyll 版の URL）
    layout: redirect
    redirectTo: "https://maku.blog/p/ftducs9/"
    _build: { list: false }
    ---
    ```
  - maku.blog 側のページは普通に書けば OK

- maku77.github.io 内の Jekyll でパーマリンク化する場合
  - __`permalink: "p/xxxxxxx/"`__ と記述（後ろのスラッシュだけ付ける）
  - __`redirect_from: "/java/file-basename"`__ で過去の HTML パスを記述（前のスラッシュだけ付ける）
  - インデックスページのリンクを __`/p/xxxxxxx/`__ に変える（前後にスラッシュを付ける）

