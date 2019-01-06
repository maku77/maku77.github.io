---
title: "サポートされている Locale の一覧 (Android 4.0)"
date: "2012-09-18"
---

下記は、Android 4.0 においてサポートされている Locale の一覧です。

```
af_ZA: Afrikaans (South Africa) / アフリカーンス語 (南アフリカ)
am_ET: Amharic (Ethiopia) / アムハラ語 (エチオピア)
ar_EG: Arabic (Egypt) / アラビア語 (エジプト)
bg_BG: Bulgarian (Bulgaria) / ブルガリア語 (ブルガリア)
ca_ES: Catalan (Spain) / カタロニア語 (スペイン)
cs_CZ: Czech (Czech Republic) / チェコ語 (チェコ)
da_DK: Danish (Denmark) / デンマーク語 (デンマーク)
de_DE: German (Germany) / ドイツ語 (ドイツ)
el_GR: Greek (Greece) / ギリシア語 (ギリシア)
en_GB: English (United Kingdom) / 英語 (イギリス)
en_US: English (United States) / 英語 (アメリカ合衆国)
es_ES: Spanish (Spain) / スペイン語 (スペイン)
es_US: Spanish (United States) / スペイン語 (アメリカ合衆国)
fa_IR: Persian (Iran) / ペルシア語 (イラン)
fi_FI: Finnish (Finland) / フィンランド語 (フィンランド)
fr_FR: French (France) / フランス語 (フランス)
hi_IN: Hindi (India) / ヒンディー語 (インド)
hi_IN: Hindi (India) / ヒンディー語 (インド)
hr_HR: Croatian (Croatia) / クロアチア語 (クロアチア)
hu_HU: Hungarian (Hungary) / ハンガリー語 (ハンガリー)
in_ID: Indonesian (Indonesia) / インドネシア語 (インドネシア)
it_IT: Italian (Italy) / イタリア語 (イタリア)
iw_IL: Hebrew (Israel) / ヘブライ語 (イスラエル)
ja_JP: Japanese (Japan) / 日本語 (日本)
ko_KR: Korean (South Korea) / 韓国語 (大韓民国)
lt_LT: Lithuanian (Lithuania) / リトアニア語 (リトアニア)
lv_LV: Latvian (Latvia) / ラトビア語 (レット語) (ラトビア)
ms_MY: Malay (Malaysia) / マライ語 (マレーシア)
nb_NO: Norwegian Bokm?l (Norway) / ノルウェー語 (ボークモール) (ノルウェー)
nl_NL: Dutch (Netherlands) / オランダ語 (オランダ)
pl_PL: Polish (Poland) / ポーランド語 (ポーランド)
pt_BR: Portuguese (Brazil) / ポルトガル語 (ブラジル)
pt_PT: Portuguese (Portugal) / ポルトガル語 (ポルトガル)
rm_CH: Raeto-Romance (Switzerland) / レト＝ロマン語 (スイス)
ro_RO: Romanian (Romania) / ルーマニア語 (ルーマニア)
ru_RU: Russian (Russia) / ロシア語 (ロシア)
sk_SK: Slovak (Slovakia) / スロバキア語 (スロバキア)
sl_SI: Slovenian (Slovenia) / スロベニア語 (スロベニア)
sr_RS: Serbian (Serbia) / セルビア語 (セルビア)
sv_SE: Swedish (Sweden) / スウェーデン語 (スウェーデン)
sw_TZ: Swahili (Tanzania) / スワヒリ語 (タンザニア)
th_TH: Thai (Thailand) / タイ語 (タイ)
tl_PH: Tagalog (Philippines) / タガログ語 (フィリピン)
tr_TR: Turkish (Turkey) / トルコ語 (トルコ)
uk_UA: Ukrainian (Ukraine) / ウクライナ語 (ウクライナ)
vi_VN: Vietnamese (Vietnam) / ベトナム語 (ベトナム)
zh_CN: Chinese (China) / 中国語 (中華人民共和国)  ★「簡体」
zh_TW: Chinese (Taiwan) / 中国語 (台湾)  ★「繁体」
zu_ZA: Zulu (South Africa) / ズールー語 (南アフリカ)
```

★Android の UI 上の言語名は ICU のライブラリから取得した情報をもとに表示されるのですが、中国語に関しては例外的に、表示上の言語名が「繁体字」or「簡体字」のようになるように拡張されています。
これらの文言は `android/packages/apps/Settings/res/values/arrays.xml` 内で定義されています。


上記の一覧は、通常の PC 上でロケールシンボル（`en_US` など）に対応する言語名、国名を出力したものです。
下記のようなコードを実行して出力しています。

#### Main.java

```java
import java.util.Arrays;
import java.util.Locale;

public class Main {
    // Copied from "build/target/product/languages_full.mk"
    private static String LOCALES = "en_US fr_FR it_IT es_ES de_DE nl_NL cs_CZ pl_PL ja_JP zh_TW zh_CN ru_RU ko_KR nb_NO es_US da_DK el_GR tr_TR pt_PT pt_BR rm_CH sv_SE bg_BG ca_ES en_GB fi_FI hi_IN hr_HR hu_HU in_ID iw_IL lt_LT lv_LV ro_RO sk_SK sl_SI sr_RS uk_UA vi_VN tl_PH ar_EG fa_IR th_TH sw_TZ ms_MY af_ZA zu_ZA am_ET hi_IN";

    public static void main(String[] args) {
        String[] arr = LOCALES.trim().split("\\s+");
        Arrays.sort(arr);

        for (int i = 0; i < arr.length; ++i) {
            Locale loc = createLocale(arr[i]);
            System.out.println(loc.toString() + ": " +
                    loc.getDisplayName(Locale.ENGLISH) + " / " +
                    loc.getDisplayName(Locale.JAPANESE));
        }
    }

    private static Locale createLocale(String locale) {
        String[] splitted = locale.split("_");
        if (splitted.length == 1) {
            return new Locale(splitted[0]);
        } else {
            return new Locale(splitted[0], splitted[1]);
        }
    }
}
```

