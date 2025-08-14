---
title: "Androidベンダー向けメモ: make 後の生成イメージが格納されるディレクトリを調べる ($ANDROID_PRODUCT_OUT)"
url: "p/xsdpj94/"
date: "2010-11-08"
tags: ["android"]
aliases: [/android/product-out-dir.html]
---

Android ビルド時に `lunch` コマンドでビルドターゲットを指定すると、ビルドイメージが作成される先のディレクトリ名を **`$ANDROID_PRODUCT_OUT`** 変数で参照できるようになります。

```console
$ echo $ANDROID_PRODUCT_OUT
/home/maku/work/android/out/target/product/your_product
```

上記の変数は、`build/envsetup.sh` の中で定義されるようになっています。

この変数を使用すれば、例えば、`adb push` で `/system/app` ディレクトリ内のファイルを端末に転送するための下記のような関数を定義することができます（`/system` 用のデバイスが `/dev/sda5` になっている場合）。

{{< code lang="bash" title="~/.bashrc" >}}
function adb-push-all {
    adb shell 'mount -o remount -rw /dev/sda5 /system'
    adb push $ANDROID_PRODUCT_OUT/system/app /system/app
    adb shell 'sync'
}
{{< /code >}}

