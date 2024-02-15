# classbuddy4j [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.classbuddy4j/classbuddy4j/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.classbuddy4j/classbuddy4j)

classbuddy4j Java library

# Maven usage

```
<dependency>
    <groupId>io.github.classbuddy4j</groupId>
    <artifactId>classbuddy4j</artifactId>
    <version>USE-LATEST-VERSION</version>
    <scope>test</scope>
</dependency>

```

## How to release a new version?

1. Every change on the main development branch is released as `-SNAPSHOT` version to Sonatype snapshot repo
   at https://oss.sonatype.org/content/repositories/snapshots/io/github/classbuddy4j/classbuddy4j/
2. In order to release a non-snapshot version to Maven Central push an annotated tag, for example:

```
git tag -a -m "Release 0.x.y" v0.x.y
git push origin v0.x.y
```

3. At the moment, you **may not create releases from GitHub Web UI**. Doing so will make the CI build fail because the
   CI creates the changelog and posts to GitHub releases. We'll support this in the future.
