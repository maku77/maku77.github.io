---
title: "Jenkins ジョブの設定 (config.xml) を確認する"
date: "2016-10-20"
---

下記のようなアドレスにアクセスすると、そのジョブの設定情報 **config.xml** を確認することができます。
この情報は、Jenkins の REST API などを使用してジョブを作成するときに必要になります。

```
http://localhost:8080/job/＜ジョブ名＞/config.xml
```

#### config.xml の例

```xml
<?xml version='1.0' encoding='UTF-8'?>
<project>
  <description></description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.scm.NullSCM"/>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers/>
  <concurrentBuild>false</concurrentBuild>
  <builders/>
  <publishers/>
  <buildWrappers/>
</project>
```

