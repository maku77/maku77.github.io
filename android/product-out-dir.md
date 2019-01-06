---
title: "make 後の生成イメージが格納されるディレクトリを調べる"
date: "2010-11-08"
---

`lunch` コマンドでビルドターゲットを指定すると、ビルドイメージが作成される先のディレクトリ名を `$ANDROID_PRODUCT_OUT` 変数で参照できるようになります。

```
$ echo $ANDROID_PRODUCT_OUT
/home/maku/work/android/out/target/product/your_product
```

上記の変数は、`build/envsetup.sh` の中で定義されるようになっています。

この変数を使用すれば、例えば、`adb push` で `/system/app` ディレクトリ内のファイルを端末に転送するための下記のような関数を定義することができます（`/system` 用のデバイスが `/dev/sda5` になっている場合）。

#### $HOME/.bashrc
```bash
function adb-push-all {
    adb shell 'mount -o remount -rw /dev/sda5 /system'
    adb push $ANDROID_PRODUCT_OUT/system/app /system/app
    adb shell 'sync'
}
```

