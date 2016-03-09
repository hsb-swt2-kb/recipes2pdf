# Installation
## Requirements
- [git](https://git-scm.com/downloads)
- [gradle](http://gradle.org/gradle-download/) or use project delivered gradlew:
```
sudo chmod +x gradlew
./gradlew -v
```

## Init
```
git clone https://github.com/hsb-swt2-kb/recipes2pdf.git
cd recipes2pdf
gradle ideaModule (recommended) or
gradle eclipse (untested)
```
To generate your project files again (in rare cases, e.g. corrupted project files or added dependency to `build.gradle`) use
```
gradle cleanIdea ideaModule
```