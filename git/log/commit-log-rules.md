---
title: コミットログの書き方
date: "2008-05-20"
---

[Tim Pope さんのブログ](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html)に Git のコミットログの書き方の要約が書いてあります。
わかりやすいので、抜粋してご紹介します。
下記は、コミットログの書き方（フォーマット）の説明ですが、この説明自体がコミットログの書き方に従って書かれています。

~~~
Capitalized, short (50 chars or less) summary

More detailed explanatory text, if necessary.  Wrap it to about 72
characters or so.  In some contexts, the first line is treated as the
subject of an email and the rest of the text as the body.  The blank
line separating the summary from the body is critical (unless you omit
the body entirely); tools like rebase can get confused if you run the
two together.

Write your commit message in the imperative: "Fix bug" and not "Fixed bug"
or "Fixes bug."  This convention matches up with commit messages generated
by commands like git merge and git revert.

Further paragraphs come after blank lines.

- Bullet points are okay, too

- Typically a hyphen or asterisk is used for the bullet, followed by a
  single space, with blank lines in between, but conventions vary here

- Use a hanging indent
~~~

ポイントをざっとまとめるとこんな感じです。

* 一行目は50文字くらいで簡潔に（メールの subject などになるため）。大文字で書き始める。ピリオドで終わらない。
* 詳細説明が必要な場合は、空行の次の行から書き始める。空行がないと rebase コマンドなどが混乱するので必須。72 文字以内で折り返す。
* 動詞の時勢は原形（○Fix bug、×Fixed Bug、×Fixes bug）。
* アスタリスクやハイフンでの箇条書きも OK。１つの項目が複数行に渡るときはぶらさげインデントする。

