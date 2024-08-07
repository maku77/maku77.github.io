---
title: "ADB で特定のブロードキャストインテントをレシーブするアプリを列挙する (dumpsys activity broadcasts)"
url: "p/jfqtfyn/"
date: "2016-07-15"
tags: ["Android"]
aliases: /android/adb/enum-receivers.html
---

__`dumpsys`__ コマンドの __`activity broadcast`__ サブコマンドを実行すると、ブロードキャストインテントをハンドルするように登録しているレシーバーの一覧を確認することができます。

```console
$ adb shell dumpsys activity broadcasts
```

{{< code title="実行結果（抜粋）" >}}
ACTIVITY MANAGER BROADCAST STATE (dumpsys activity broadcasts)
  Registered Receivers:
  ...
  * ReceiverList{82bcce 603 com.android.systemui/10019/u0 remote:83b3dc9}
    app=603:com.android.systemui/u0a19 pid=603 uid=10019 user=0
    Filter #0: BroadcastFilter{28557ef}
      Action: "android.intent.action.TIME_TICK"
      Action: "android.intent.action.TIME_SET"
      Action: "android.intent.action.BATTERY_CHANGED"
      ...
      AutoVerify=false
    Filter #1: BroadcastFilter{56b72fc}
      Action: "android.intent.action.BOOT_COMPLETED"
      mPriority=1000, mHasPartialTypes=false
      AutoVerify=false
  ...
{{< /code >}}

__`Registered Receivers`__ のセクションには、アプリごとにどのようなフィルタでインテントを監視しているかが表示されます。
上記の例では、`com.android.systemui` アプリが、`android.intent.action.TIME_TICK` インテントなどのブロードキャストを監視していること示しています。

さらに下の方を見ると、__`Receiver Resolver Table`__ というセクションがあります。

```
  Receiver Resolver Table:
    ...
    Non-Data Actions:
        ...
        android.intent.action.BOOT_COMPLETED:
          BroadcastFilter{10f199e u0 ReceiverList{b1ecbd9 493 system/1000/u0 local:28f4620}}
          BroadcastFilter{989a5ad u0 ReceiverList{ae0d2c4 493 system/1000/u0 local:48b9d7}}
          BroadcastFilter{213887b u0 ReceiverList{758680a 493 system/1000/u0 local:513e075}}
          BroadcastFilter{56b72fc u0 ReceiverList{82bcce 603 com.android.systemui/10019/u0 remote:83b3dc9}}
        ...
```

こちらは逆に、あるインテント (Action) をどのモジュールが監視しているかのマッピングを示しています。
この例では、`BOOT_COMPLETED` インテントを監視しているモジュールの一覧を表示しています。

