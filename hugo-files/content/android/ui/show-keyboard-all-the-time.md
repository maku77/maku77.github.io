---
title: "Androidメモ: ソフトウェアキーボードを常に表示する (setSoftInputMode)"
url: "p/p4y55j2/"
date: "2014-07-28"
tags: ["android"]
aliases: ["/android/ui/show-keyboard-all-the-time.html"]
---

Android アプリケーションでソフトウェアキーボードを常に表示したいときは次のように実装します。

{{< code lang="java" title="方法 1) Activity の onCreate() で設定する" >}}
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
{{< /code >}}

{{< code lang="java" title="方法 2) Dialog 表示時に設定" >}}
final AlertDialog dialog = ...;
dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
{{< /code >}}

