---
title: Java アプリケーションの国際化 (i18n) と Locale クラス
date: "2010-05-16"
---

Locale クラスについて
----

- [Locale (Java Platform SE 8)](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html)
- [Locale (Java Platform SE 7)](https://docs.oracle.com/javase/7/docs/api/java/util/Locale.html)

Java の Locale クラスは、language と region のペアを表します。
Locale オブジェクトを元に、数値の表示フォーマット (NumberFormat) や、文言リソース (ResourceBundle) オブジェクトを取得することで i18n 対応のアプリを開発できます。

下記は、Locale クラスのコンストラクタのバリエーションです。

```java
Locale(String language)
Locale(String language, String country)
Locale(String language, String country, String variant)
```

最後のオーバーロードバージョンでは、language、country の他に、3 番目のパラメータである variant を指定できます。
これはアプリケーションが language、country の他にもロケールを区別しなければならない場合に自由に使用できます。
例えば、Windows と Linux で言語リソースを切り替えたい場合などに使用できます。


現在のデフォルト Locale を取得する
----

現在の（Java VM の）デフォルト Locale を取得するには、以下のメソッドを使用します。

```java
public static Locale Locale.getDefault()
```

Java VM のデフォルト Locale は、VM が立ち上がるときのホスト PC の環境変数などをもとに設定されます。
VM のデフォルト Locale をプログラムから変更するには、以下の API を使用します。

```java
public static void Locale.setDefault(Locale newLocale)
```


システムで利用可能な Locale を列挙する
----

```java
public static Locale[] Locale.getAvailableLocales()
```

を使うと、システムにインストールされているすべての Locale を取得することができます。
Locale オブジェクトからは、言語、国を表す文字列を以下のような API で取得することができます。

```java
String lang = locale.getDisplayLanguage();     // 例: "English"
String country = locale.getDisplayCountry();   // 例: "United States"
```

言語、国をセットにした文字列表現を取得することもできます。

```java
String name = locale.getDisplayName();  // 例: "English (United States)"
```

これらは引数なしで呼び出した場合は、現在のロケールの言語で表示用の文字列を返しますが、引数に Locale オブジェクトを指定することで、任意の言語で表示用の文字列を取得することができます。

```java
String nameInEnglish = locale.getDisplayName(Locale.ENGLISH);    // 例: "German"
String nameInJapanese = locale.getDisplayName(Locale.JAPANESE);  // 例: "ドイツ語"
```

下記のサンプルでは、すべての Locale を取得し、Language、Country 名をソートして表示します。

#### Sample.java

```java
import java.util.Arrays;
import java.util.Locale;

public class Sample {
    public static void main(String[] args) {
        displayAllLocales();
    }

    private static void displayAllLocales() {
        Locale[] locales = Locale.getAvailableLocales();
        sortLocales(locales);
        for (Locale loc : locales) {
            System.out.println(loc.toString() + ":\t" +
                    loc.getDisplayName(Locale.ENGLISH) + " / " +
                    loc.getDisplayName(Locale.JAPANESE));
        }
    }

    private static void sortLocales(Locale[] locales) {
        Arrays.sort(locales, new java.util.Comparator<Locale>() {
            @Override public int compare(Locale loc1, Locale loc2){
                String a = loc1.toString();
                String b = loc2.toString();
                return a.compareTo(b);
            }
        });
    }
}
```

#### 実行結果

```
ar:     Arabic / アラビア語
ar_AE:  Arabic (United Arab Emirates) / アラビア語 (アラブ首長国連邦)
ar_BH:  Arabic (Bahrain) / アラビア語 (バーレーン)
ar_DZ:  Arabic (Algeria) / アラビア語 (アルジェリア)
ar_EG:  Arabic (Egypt) / アラビア語 (エジプト)
ar_IQ:  Arabic (Iraq) / アラビア語 (イラク)
ar_JO:  Arabic (Jordan) / アラビア語 (ヨルダン)
ar_KW:  Arabic (Kuwait) / アラビア語 (クウェート)
ar_LB:  Arabic (Lebanon) / アラビア語 (レバノン)
ar_LY:  Arabic (Libya) / アラビア語 (リビア)
ar_MA:  Arabic (Morocco) / アラビア語 (モロッコ)
ar_OM:  Arabic (Oman) / アラビア語 (オマーン)
ar_QA:  Arabic (Qatar) / アラビア語 (カタール)
ar_SA:  Arabic (Saudi Arabia) / アラビア語 (サウジアラビア)
ar_SD:  Arabic (Sudan) / アラビア語 (スーダン)
ar_SY:  Arabic (Syria) / アラビア語 (シリア)
ar_TN:  Arabic (Tunisia) / アラビア語 (チュニジア)
ar_YE:  Arabic (Yemen) / アラビア語 (イエメン)
be:     Belarusian / 白ロシア語
be_BY:  Belarusian (Belarus) / 白ロシア語 (ベラルーシ)
bg:     Bulgarian / ブルガリア語
bg_BG:  Bulgarian (Bulgaria) / ブルガリア語 (ブルガリア)
ca:     Catalan / カタロニア語
ca_ES:  Catalan (Spain) / カタロニア語 (スペイン)
cs:     Czech / チェコ語
cs_CZ:  Czech (Czech Republic) / チェコ語 (チェコ)
da:     Danish / デンマーク語
da_DK:  Danish (Denmark) / デンマーク語 (デンマーク)
de:     German / ドイツ語
de_AT:  German (Austria) / ドイツ語 (オーストリア)
de_CH:  German (Switzerland) / ドイツ語 (スイス)
de_DE:  German (Germany) / ドイツ語 (ドイツ)
de_LU:  German (Luxembourg) / ドイツ語 (ルクセンブルク)
el:     Greek / ギリシア語
el_CY:  Greek (Cyprus) / ギリシア語 (キプロス)
el_GR:  Greek (Greece) / ギリシア語 (ギリシア)
en:     English / 英語
en_AU:  English (Australia) / 英語 (オーストラリア)
en_CA:  English (Canada) / 英語 (カナダ)
en_GB:  English (United Kingdom) / 英語 (イギリス)
en_IE:  English (Ireland) / 英語 (アイルランド)
en_IN:  English (India) / 英語 (インド)
en_MT:  English (Malta) / 英語 (マルタ)
en_NZ:  English (New Zealand) / 英語 (ニュージーランド)
en_PH:  English (Philippines) / 英語 (フィリピン)
en_SG:  English (Singapore) / 英語 (シンガポール)
en_US:  English (United States) / 英語 (アメリカ合衆国)
en_ZA:  English (South Africa) / 英語 (南アフリカ)
es:     Spanish / スペイン語
es_AR:  Spanish (Argentina) / スペイン語 (アルゼンチン)
es_BO:  Spanish (Bolivia) / スペイン語 (ボリビア)
es_CL:  Spanish (Chile) / スペイン語 (チリ)
es_CO:  Spanish (Colombia) / スペイン語 (コロンビア)
es_CR:  Spanish (Costa Rica) / スペイン語 (コスタリカ)
es_DO:  Spanish (Dominican Republic) / スペイン語 (ドミニカ共和国)
es_EC:  Spanish (Ecuador) / スペイン語 (エクアドル)
es_ES:  Spanish (Spain) / スペイン語 (スペイン)
es_GT:  Spanish (Guatemala) / スペイン語 (グアテマラ)
es_HN:  Spanish (Honduras) / スペイン語 (ホンジュラス)
es_MX:  Spanish (Mexico) / スペイン語 (メキシコ)
es_NI:  Spanish (Nicaragua) / スペイン語 (ニカラグア)
es_PA:  Spanish (Panama) / スペイン語 (パナマ)
es_PE:  Spanish (Peru) / スペイン語 (ペルー)
es_PR:  Spanish (Puerto Rico) / スペイン語 (プエルトリコ)
es_PY:  Spanish (Paraguay) / スペイン語 (パラグアイ)
es_SV:  Spanish (El Salvador) / スペイン語 (エルサルバドル)
es_US:  Spanish (United States) / スペイン語 (アメリカ合衆国)
es_UY:  Spanish (Uruguay) / スペイン語 (ウルグアイ)
es_VE:  Spanish (Venezuela) / スペイン語 (ベネズエラ)
et:     Estonian / エストニア語
et_EE:  Estonian (Estonia) / エストニア語 (エストニア)
fi:     Finnish / フィンランド語
fi_FI:  Finnish (Finland) / フィンランド語 (フィンランド)
fr:     French / フランス語
fr_BE:  French (Belgium) / フランス語 (ベルギー)
fr_CA:  French (Canada) / フランス語 (カナダ)
fr_CH:  French (Switzerland) / フランス語 (スイス)
fr_FR:  French (France) / フランス語 (フランス)
fr_LU:  French (Luxembourg) / フランス語 (ルクセンブルク)
ga:     Irish / アイルランド語
ga_IE:  Irish (Ireland) / アイルランド語 (アイルランド)
hi_IN:  Hindi (India) / ヒンディー語 (インド)
hr:     Croatian / クロアチア語
hr_HR:  Croatian (Croatia) / クロアチア語 (クロアチア)
hu:     Hungarian / ハンガリー語
hu_HU:  Hungarian (Hungary) / ハンガリー語 (ハンガリー)
in:     Indonesian / インドネシア語
in_ID:  Indonesian (Indonesia) / インドネシア語 (インドネシア)
is:     Icelandic / アイスランド語
is_IS:  Icelandic (Iceland) / アイスランド語 (アイスランド)
it:     Italian / イタリア語
it_CH:  Italian (Switzerland) / イタリア語 (スイス)
it_IT:  Italian (Italy) / イタリア語 (イタリア)
iw:     Hebrew / ヘブライ語
iw_IL:  Hebrew (Israel) / ヘブライ語 (イスラエル)
ja:     Japanese / 日本語
ja_JP:  Japanese (Japan) / 日本語 (日本)
ja_JP_JP_#u-ca-japanese:        Japanese (Japan,JP) / 日本語 (日本,JP)
ko:     Korean / 韓国語
ko_KR:  Korean (South Korea) / 韓国語 (大韓民国)
lt:     Lithuanian / リトアニア語
lt_LT:  Lithuanian (Lithuania) / リトアニア語 (リトアニア)
lv:     Latvian / ラトビア語 (レット語)
lv_LV:  Latvian (Latvia) / ラトビア語 (レット語) (ラトビア)
mk:     Macedonian / マケドニア語
mk_MK:  Macedonian (Macedonia) / マケドニア語 (マケドニア)
ms:     Malay / マライ語
ms_MY:  Malay (Malaysia) / マライ語 (マレーシア)
mt:     Maltese / マルタ語
mt_MT:  Maltese (Malta) / マルタ語 (マルタ)
nl:     Dutch / オランダ語
nl_BE:  Dutch (Belgium) / オランダ語 (ベルギー)
nl_NL:  Dutch (Netherlands) / オランダ語 (オランダ)
no:     Norwegian / ノルウェー語
no_NO:  Norwegian (Norway) / ノルウェー語 (ノルウェー)
no_NO_NY:       Norwegian (Norway,Nynorsk) / ノルウェー語 (ノルウェー,Nynorsk)
pl:     Polish / ポーランド語
pl_PL:  Polish (Poland) / ポーランド語 (ポーランド)
pt:     Portuguese / ポルトガル語
pt_BR:  Portuguese (Brazil) / ポルトガル語 (ブラジル)
pt_PT:  Portuguese (Portugal) / ポルトガル語 (ポルトガル)
ro:     Romanian / ルーマニア語
ro_RO:  Romanian (Romania) / ルーマニア語 (ルーマニア)
ru:     Russian / ロシア語
ru_RU:  Russian (Russia) / ロシア語 (ロシア)
sk:     Slovak / スロバキア語
sk_SK:  Slovak (Slovakia) / スロバキア語 (スロバキア)
sl:     Slovenian / スロベニア語
sl_SI:  Slovenian (Slovenia) / スロベニア語 (スロベニア)
sq:     Albanian / アルバニア語
sq_AL:  Albanian (Albania) / アルバニア語 (アルバニア)
sr:     Serbian / セルビア語
sr_BA:  Serbian (Bosnia and Herzegovina) / セルビア語 (ボスニア・ヘルツェゴビナ)
sr_BA_#Latn:    Serbian (Latin,Bosnia and Herzegovina) / セルビア語 (ラテン文字,ボスニア・ヘルツェゴビナ)
sr_CS:  Serbian (Serbia and Montenegro) / セルビア語 (セルビア・モンテネグロ)
sr_ME:  Serbian (Montenegro) / セルビア語 (モンテネグロ)
sr_ME_#Latn:    Serbian (Latin,Montenegro) / セルビア語 (ラテン文字,モンテネグロ)
sr_RS:  Serbian (Serbia) / セルビア語 (セルビア)
sr_RS_#Latn:    Serbian (Latin,Serbia) / セルビア語 (ラテン文字,セルビア)
sr__#Latn:      Serbian (Latin) / セルビア語 (ラテン文字)
sv:     Swedish / スウェーデン語
sv_SE:  Swedish (Sweden) / スウェーデン語 (スウェーデン)
th:     Thai / タイ語
th_TH:  Thai (Thailand) / タイ語 (タイ)
th_TH_TH_#u-nu-thai:    Thai (Thailand,TH) / タイ語 (タイ,TH)
tr:     Turkish / トルコ語
tr_TR:  Turkish (Turkey) / トルコ語 (トルコ)
uk:     Ukrainian / ウクライナ語
uk_UA:  Ukrainian (Ukraine) / ウクライナ語 (ウクライナ)
vi:     Vietnamese / ベトナム語
vi_VN:  Vietnamese (Vietnam) / ベトナム語 (ベトナム)
zh:     Chinese / 中国語
zh_CN:  Chinese (China) / 中国語 (中華人民共和国)
zh_HK:  Chinese (Hong Kong) / 中国語 (香港)
zh_SG:  Chinese (Singapore) / 中国語 (シンガポール)
zh_TW:  Chinese (Taiwan) / 中国語 (台湾)
```


ResourceBundle による多言語化
----

- [ResourceBundle (Java Platform SE 8)](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)
- [ResourceBundle (Java Platform SE 7)](http://docs.oracle.com/javase/7/docs/api/java/util/ResourceBundle.html)

言語、国を表す `Locale` オブジェクトを元に `ResourceBundle` オブジェクトを取得し、キーから表示用の文字列を取得するようにすると、アプリケーションの文字列表示を多言語化することができます。

```java
Locale locale = new Locale("en", "US")
ResouceBundle bundle = ResourceBundle.getBundle("strings", locale);
String hello = bundle.getString("hello");
```

上記のようにすると、`strings_en_US.properties` という名前のプロパティファイルが読み込まれます。プロパティファイルの中には、キーと値のペアを以下のように記述しておきます。

#### strings_en_US.properties

```
hello = Hello!
```

`ResouceBundle.getBundle()` で指定した `Locale` に対応するプロパティファイルが見つからない場合は、デフォルトのプロパティファイルがロードされます。
デフォルトのプロパティファイル名は、言語名、国名のサフィックスを除いたもので、上記の場合は、`strings.properties` になります。

### 複数の言語に対応した例

#### Sample.java

```java
import java.util.Locale;
import java.util.ResourceBundle;

public class Sample {
    public static void main(String[] args) {
        Locale localeJp = new Locale("jp");
        Locale localeUs = new Locale("en", "US");
        Locale localeFr = new Locale("fr");

        ResourceBundle bundle = ResourceBundle.getBundle("strings", localeJp);
        System.out.println(bundle.getString("hello"));

        bundle = ResourceBundle.getBundle("strings", localeUs);
        System.out.println(bundle.getString("hello"));

        bundle = ResourceBundle.getBundle("strings", localeFr);
        System.out.println(bundle.getString("hello"));
    }
}
```

#### strings.properties

```
hello = Default Hello!
```

#### strings_jp.properties）

```
hello = \u3053\u3093\u306b\u3061\u306f\uff01
```

"こんにちは！" と書いたファイルを `native2ascii` コマンドを使って Unicode に変換してあります。

#### strings_en_US.properties

```
hello = Hello!
```

#### 実行結果

```
こんにちは！
Hello!
Default Hello!
```

