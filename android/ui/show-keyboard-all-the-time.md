---
title: ソフトウェアキーボードを常に表示する
date: "2014-07-28"
---

ソフトウェアキーボードを常時表示する方法です。

### 方法 1) Activity の onCreate() で設定する

~~~ java
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
~~~

### 方法 2) Dialog 表示時に設定

~~~ java
final AlertDialog dialog = ...;
dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
~~~

