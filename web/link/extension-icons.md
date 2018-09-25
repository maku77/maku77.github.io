---
title: "リンク先ファイルの拡張子によって自動的にアイコンを付けて表示する"
date: "2012-12-14"
---

以下のようにスタイル記述すると、リンク先 URL の拡張子によって、アイコンを自動表示できます。
ここでは、`a` 要素の `href` 属性の値が、特定の拡張子で終わっているかを調べるようにしています。

~~~ css
/* Common files */
a[href$=".zip"]:after {
    padding-left: 7px;
    content: url(/assets/img/zip.ico);
}
a[href$=".pdf"]:after {
    padding-left: 7px;
    content: url(/assets/img/pdf16.ico);
}
a[href$=".txt"]:after, a[href$=".TXT"]:after, a[href$=".js"]:after, a[href$=".xml"]:after {
    padding-left: 7px;
    content: url(/assets/img/txt16.ico);
}
a[href$="jpg"]:after, a[href$="JPG"]:after, a[href$="png"]:after, a[href$="PNG"]:after  {
    padding-left: 7px;
    content: url(/assets/img/img16.ico);
}

/* Office icons */
a[href$=".ppt"]:after, a[href$=".pptx"]:after, a[href$=".potx"]:after {
    padding-left: 7px;
    content: url(/assets/img/ppt16.ico);
}
a[href$=".xls"]:after, a[href$=".xlsx"]:after {
    padding-left: 7px;
    content: url(/assets/img/excel16.ico);
}
a[href$=".doc"]:after, a[href$=".docx"]:after {
    padding-left: 7px;
    content: url(/assets/img/word16.ico);
}
~~~

