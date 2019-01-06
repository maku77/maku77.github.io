---
title: "時制や単数形・複数形を考慮して命名する"
date: "2015-06-25"
---

関数名や、変数名は、英語の文法と同様に、**時制**と**単数形・複数形**にも気を配るようにしましょう。
複数のオブジェクトを含む配列なのに変数名が単数形になっていたり、真偽値を返すメソッドだからといって、すべて isXxx のような名前にしていたのでは、読みやすいコードにはなりません。

#### 例: 複数の要素を持つ可能性があるのであれば複数形、あるいは xxxList のような命名をする
```java
Bitmap[] thumbnails;  // Good
Bitmap[] thumbnail;   // NG
```

#### 例: 真偽値は isXxx という形にこだわらず、意図の分かる命名をする
```java
boolean canShowTitle;     // Good（タイトルを表示可能な状態だということが分かる）
boolean shouldShowTitle;  // Good（タイトルを表示すべきということが分かる）
boolean isTitleEnabled;   // OK（何が可能なのか分かりにくい）
boolean isShowTitle;      // NG（表示すべきということ？表示されていること？）
```

#### 例: 時制を意識した命名をする
```java
bool areAllColorsLoaded;        // Good（現在の状態を表している）
bool wasNameDeletedBeforeExit;  // Good（過去の変化を表している）
bool isDisplayAddress;  // NG（状態なのか、可能性なのか、何を表しているのか分からない。そもそも文法がおかしい）
```
