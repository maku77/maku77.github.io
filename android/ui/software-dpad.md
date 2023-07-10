---
title: "カスタムビューでソフトウェア D-Pad を表示する"
date: "2014-08-11"
---

D-Pad（いわゆる十字キー）をカスタムビューとして作成するサンプルです。
上下左右キーのタッチ入力を、`DPadListener` で監視できます。

![software-dpad.png](./software-dpad.png)


- [DPadListener.java](./DPadListener.java)
- [DPadView.java](./DPadView.java)
- [DPadEvent.java](./DPadEvent.java)

### 使用例

#### res/layout/activity_main.xml（抜粋）

~~~ xml
<io.github.ojisancancode.widget.DPadView
    android:id="@+id/dpad"
    android:layout_width="200dp"
    android:layout_height="200dp" />
~~~

#### MainActivity.java

~~~ java
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
~~~

