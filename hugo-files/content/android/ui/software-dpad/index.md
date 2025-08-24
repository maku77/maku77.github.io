---
title: "Androidメモ: カスタムビューでソフトウェア D-Pad を表示する"
url: "p/nfguckx/"
date: "2014-08-11"
tags: ["android"]
aliases: ["/android/ui/software-dpad.html"]
---

D-Pad（十字キー）をカスタムビューとして実装するサンプルコードです。
上下左右キーのタッチ入力は、`DPadListener` を使って監視できます。

{{< image src="img-001.png" >}}

## カスタムビューの実装例

- [DPadListener.java](./DPadListener.java)
- [DPadView.java](./DPadView.java)
- [DPadEvent.java](./DPadEvent.java)

## 使用例

{{< code lang="xml" title="res/layout/activity_main.xml（抜粋）" >}}
<io.github.maku77.widget.DPadView
    android:id="@+id/dpad"
    android:layout_width="200dp"
    android:layout_height="200dp" />
{{< /code >}}

{{< code lang="java" title="MainActivity.java" >}}
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DPadView dpad = (DPadView) findViewById(R.id.dpad);
        dpad.setDPadListener(new DPadListener() {
            @Override
            public void onCursor(DPadEvent event) {
                Log.d("DEBUG", event.toString());
            }
        });
    }
}
{{< /code >}}

