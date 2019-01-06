---
title: "Usage の出力例"
date: "2009-07-08"
---

簡単な Usage 文を表示してプログラムを終了させる関数のサンプルです。
プログラムを `exit(1)` で終了させるのがポイント。

~~~ cpp
static void Usage() {
    fprintf(stderr, "Usage: expand [-t tablist] [file ...]\n");
    exit(1);
}
~~~


### 参考文献

<table><tr><td><a href="https://hb.afl.rakuten.co.jp/hgc/15c85ffb.3f2107b5.15c85ffc.6efbe34e/?pc=https%3A%2F%2Fitem.rakuten.co.jp%2Frakutenkobo-ebooks%2F749ea6e601503a4d8e1d3b8cf414bdcd%2F&m=http%3A%2F%2Fm.rakuten.co.jp%2Frakutenkobo-ebooks%2Fi%2F16136841%2F&link_type=picttext&ut=eyJwYWdlIjoiaXRlbSIsInR5cGUiOiJwaWN0dGV4dCIsInNpemUiOiIzMDB4MzAwIiwibmFtIjoxLCJuYW1wIjoicmlnaHQiLCJjb20iOjAsImNvbXAiOiJkb3duIiwicHJpY2UiOjEsImJvciI6MCwiY29sIjowfQ%3D%3D" target="_blank" rel="nofollow" style="word-wrap:break-word;"  ><img src="https://hbb.afl.rakuten.co.jp/hgb/15c85ffb.3f2107b5.15c85ffc.6efbe34e/?me_id=1278256&item_id=16136841&m=https%3A%2F%2Fthumbnail.image.rakuten.co.jp%2F%400_mall%2Frakutenkobo-ebooks%2Fcabinet%2F1813%2F2000004911813.jpg%3F_ex%3D80x80&pc=https%3A%2F%2Fthumbnail.image.rakuten.co.jp%2F%400_mall%2Frakutenkobo-ebooks%2Fcabinet%2F1813%2F2000004911813.jpg%3F_ex%3D300x300&s=300x300&t=picttext" border="0" style="margin:2px" alt="[商品価格に関しましては、リンクが作成された時点と現時点で情報が変更されている場合がございます。]" title="[商品価格に関しましては、リンクが作成された時点と現時点で情報が変更されている場合がございます。]"></a></td><td style="vertical-align:top;width:310px;"><p style="font-size:12px;line-height:1.4em;text-align:left;margin:0px;padding:2px 6px;word-wrap:break-word"><a href="https://hb.afl.rakuten.co.jp/hgc/15c85ffb.3f2107b5.15c85ffc.6efbe34e/?pc=https%3A%2F%2Fitem.rakuten.co.jp%2Frakutenkobo-ebooks%2F749ea6e601503a4d8e1d3b8cf414bdcd%2F&m=http%3A%2F%2Fm.rakuten.co.jp%2Frakutenkobo-ebooks%2Fi%2F16136841%2F&link_type=picttext&ut=eyJwYWdlIjoiaXRlbSIsInR5cGUiOiJwaWN0dGV4dCIsInNpemUiOiIzMDB4MzAwIiwibmFtIjoxLCJuYW1wIjoicmlnaHQiLCJjb20iOjAsImNvbXAiOiJkb3duIiwicHJpY2UiOjEsImJvciI6MCwiY29sIjowfQ%3D%3D" target="_blank" rel="nofollow" style="word-wrap:break-word;"  >Code Reading　プレミアムブックス版　オープンソースから学ぶソフトウェア開発技法【電子書籍】[ Diomidis Spinellis ]</a><br><span >価格：7344円</span> <span style="color:#BBB">(2017/6/6時点)</span></p></td><tr></table>

