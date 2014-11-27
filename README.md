textie-mvn-repo
======

Wenn du textie als Abhängigkeit in deinem Projekt setzen willst, füge folgendes in deine pom.xml ein, um das Repo zu nutzen:

```XML
<repositories>
    <repository>
        <id>textie-mvn-repo</id>
        <url>https://raw.github.com/lfuelling/textie/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

Dann noch folgenden Code zu den Dependencies:

```XML
<dependency>
  <groupId>de.micromata.azubi</groupId>
  <artifactId>textie</artifactId>
  <version>4.0-SNAPSHOT</version>
</dependency>
```
