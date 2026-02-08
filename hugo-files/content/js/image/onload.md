---
title: "JavaScriptメモ: 複数の画像ファイルが読み込まれるのを待機する"
url: "p/4rni2hu/"
date: "2013-06-30"
tags: ["javascript"]
aliases: [/js/image/onload.html]
---

**Image** オブジェクトの **onload** コールバックは、ファイルロードが完了したときに呼び出されます。

```javascript
var img = new Image();
img.onload(function() {
    // ファイルロードが終了した時の処理
});

// ファイルのロード
img.src = './sample.png';
```

ただし、**onload** は、あくまで 1 つの画像ファイルの読み込みが完了したことだけを通知します。
複数の画像ファイルの読み込みがすべて終わったことを知るには、少し工夫が必要です。
以下の setAllLoadCallback 関数は、配列で指定したすべての要素の読み込みが終わった時に呼び出すコールバックをセットできます。

```javascript
function setLoadAllCallback(elems, callback) {
    var count = 0;
    for (var i = 0; i < elems.length; ++i) {
        elems[i].onload = function() {
            ++count;
            if (count == elems.length) {
                // All elements have been loaded.
                callback(elems);
            }
        };
    }
}
```

{{< code lang="javascript" title="使用例" >}}
var img1 = new Image();
var img2 = new Image();
var img3 = new Image();

setLoadAllCallback([img1, img2, img3], function(elems) {
    // すべての要素の読み込みが終わったときに呼び出される
});

// 画像ファイルの読み込みを開始
img1.src = './sample1.png';
img2.src = './sample2.png';
img3.src = './sample3.png';
{{< /code >}}

