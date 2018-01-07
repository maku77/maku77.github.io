---
title: ヘッダ要素に連番を振る
date: "2012-12-13"
---

h1 要素に連番を振る
====

`h1` 要素のテキストの先頭に、Chapter 1. ○○○、Chapter 2. ○○○ のように自動的に連番を振るには以下のようにします。

#### CSS

```css
body {
    counter-reset: chapter;
}
h1:before {
    content: "Chapter " counter(chapter) ". ";
    counter-increment: chapter;
}
```

#### 表示例
```
Chapter 1. ○○○
Chapter 2. ○○○
Chapter 3. ○○○
```


h2 要素にも入れ子で連番を振る
====

`h1` 要素の下に入れ子構造になっている `h2` 要素に対して、1-1:、1-2:、1-3: のような感じで連番を振るには以下のようにします。

#### CSS

```css
body {
    counter-reset: chapter;
}
h1 {
    counter-reset: section;
}
h1:before {
    content: counter(chapter) ": ";
    counter-increment: chapter;
}
h2:before {
    content: counter(chapter) "-" counter(section) ": ";
    counter-increment: section;
}
```

#### 表示例
```
1: ○○○
1-1: △△△
1-2: △△△
2: ○○○
2-1: △△△
2-2: △△△
```

簡単に説明すると、以下のような動作をしています。

* h1 要素用のカウンタ chapter は、body 要素の登場時にリセット
* h2 要素用のカウンタ section は、h1 要素の登場時にリセット

