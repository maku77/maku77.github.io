---
title: "Androidメモ: リソース ID を示す変数やパラメータにアノテーションを付ける"
url: "p/33jn7zy/"
date: "2019-02-18"
tags: ["android"]
description: "Android の Annotation Support Library は、int 型変数の用途を限定するためのアノテーションを提供しています。このアノテーションを使用することで、想定外の値が int 変数に格納されてしまうのを防ぐことができます。"
aliases: ["/android/fw/res-annotation.html"]
---

リソース ID を表す int 変数に付けるアノテーション
----

Android の各種リソースの ID は int 型で管理されています。
この値を変数に格納したり、メソッドのパラメータとして受け取るように実装した場合、不正な int 値が渡されて実行時に落ちてしまうリスクがあります。

このようなことをできるだけ防ぐため、Android Support ライブラリには下記のようなアノテーションが用意されており、これらを付加した int 型変数には、指定した種類のリソースの ID しか格納できなくなります（AndroidStudio で警告が表示されるようになります）。

| アノテーション | リソース ID の種類 |
| ---- | ---- |
| [@AnimatorRes](https://developer.android.com/reference/android/support/annotation/AnimatorRes) | `R.animator.xxx` で参照するリソース ID |
| [@AnimRes](https://developer.android.com/reference/android/support/annotation/AnimRes) | `R.anim.xxx` で参照するリソース ID |
| [@AnyRes](https://developer.android.com/reference/android/support/annotation/AnyRes) | いずれかの種類のリソースの ID（他の具体的なアノテーションを使用することを推奨） |
| [@ArrayRes](https://developer.android.com/reference/android/support/annotation/ArrayRes) | `R.array.xxx` で参照するリソース ID |
| [@AttrRes](https://developer.android.com/reference/android/support/annotation/AttrRes) | `R.attr.xxx` で参照するリソース ID |
| [@ColorRes](https://developer.android.com/reference/android/support/annotation/ColorRes) | `R.color.xxx` で参照するリソース ID |
| [@DimenRes](https://developer.android.com/reference/android/support/annotation/DimenRes) | `R.dimen.xxx` で参照するリソース ID |
| [@DrawableRes](https://developer.android.com/reference/android/support/annotation/DrawableRes) | `R.drawable.xxx` で参照するリソース ID |
| [@FontRes](https://developer.android.com/reference/android/support/annotation/FontRes) | `R.font.xxx` で参照するリソース ID |
| [@IdRes](https://developer.android.com/reference/android/support/annotation/IdRes) | `R.id.xxx` で参照するリソース ID |
| [@IntegerRes](https://developer.android.com/reference/android/support/annotation/IntegerRes) | `R.interger.xxx` で参照するリソース ID |
| [@InterpolatorRes](https://developer.android.com/reference/android/support/annotation/InterpolatorRes) | `R.interpolator.xxx` で参照するリソース ID |
| [@LayoutRes](https://developer.android.com/reference/android/support/annotation/LayoutRes) | `R.layout.xxx` で参照するリソース ID |
| [@NavigationRes](https://developer.android.com/reference/android/support/annotation/NavigationRes) | `R.navigation.xxx` で参照するリソース ID |
| [@RawRes](https://developer.android.com/reference/android/support/annotation/RawRes) | `R.raw.xxx` で参照するリソース ID |
| [@StringRes](https://developer.android.com/reference/android/support/annotation/StringRes) | `R.string.xxx` で参照するリソース ID |
| [@StyleableRes](https://developer.android.com/reference/android/support/annotation/StyleableRes) | `R.styleable.xxx` で参照するリソース ID |
| [@StyleRes](https://developer.android.com/reference/android/support/annotation/StyleRes) | `R.style.xxx` で参照するリソース ID |
| [@TransitionRes](https://developer.android.com/reference/android/support/annotation/TransitionRes) | `R.transition.xxx` で参照するリソース ID |


int 変数用アノテーションの使用例
----

上記のようなアノテーションは Annotation Support Library で提供されているため、`build.gradle` に下記のような依存を追加しておく必要があります（参考: [本家ドキュメント](https://developer.android.com/topic/libraries/support-library/features?hl=ja#annotations)）。

```
com.android.support:support-annotations:24.2.0
```

下記は、color 値のリソース ID のみを格納できる int 型フィールドを定義する例です。

```java
@ColorRes
private int mainColorResId = R.color.white;
```

メソッドのパラメータにもアノテーションを付けることができます。
下記のように指定された int 型パラメータには、文字列リソースを示す ID しか渡せなくなります。

```java
public class ResourceResolver {
    ...
    public String getString(@StringRes int resId) {
        return context.getString(resId);
    }
}
```

[android.support.annotation パッケージ](https://developer.android.com/reference/android/support/annotation/package-summary) には他にもいろいろなアノテーションが定義されているので、ざっと見ておくとよいでしょう。
例えば、int 型用のアノテーションとしては、上記のようなリソース ID 関連のものだけではなく、`AARRGGBB` 形式のカラー値であることを示すアノテーションが定義されていたりします。

- [@ColorInt](https://developer.android.com/reference/android/support/annotation/ColorInt): `AARRGGBB` 形式の int 値

