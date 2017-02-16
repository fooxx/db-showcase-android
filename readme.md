#Android local database showcase

![DbShowcase](./mobile/src/main/res/mipmap-hdpi/ic_launcher.png "DbShowcase") <a href="https://play.google.com/store/apps/details?id=cz.koto.misak.dbshowcase.android"><img src="./extras/banner/google-play-badge.png" height="72"/></a>

[![CircleCI](https://circleci.com/gh/kotomisak/db-showcase-android/tree/develop.svg?style=shield)](https://circleci.com/gh/kotomisak/db-showcase-android/tree/develop)


This is sample of local database implementation for small set of Android related local databases.

##Model
Every implemented database is based on the same application model:  

**SCHOOL CLASS** _(can have more students, more teachers)_  
**TEACHER** _(can teach more classes)_  
**STUDENT** _(can be in one class only, can have more teachers)_  

![ModelIllustration](./extras/screens/scr_showcase_int1.png "ModelIllustration")&nbsp; ![ModelIllustration](./extras/screens/scr_showcase_int2.png "ModelIllustration") <br/><br/>
![Security control](./extras/screens/scr_showcase_con1.png "Security control") &nbsp; ![ModelIllustration](./extras/screens/scr_showcase_lockscreen.png "ModelIllustration") <br/>


## Build app
./gradlew assembleRelease

## Dependency diagnostic ##

  `./gradlew dependencyReport --configuration compile`
  `./gradlew dependencyInsighty --configuration compile --dependency com.android.support:appcompat-v7`
  `./gradlew dependencyInsighty --configuration compile --dependency org.jetbrains.kotlin:kotlin-stdlib`

<!--**Couchbase Lite** (http://developer.couchbase.com/mobile/) - a lightweight embedded NoSQL database engine for Android with the built-in ability to sync to Couchbase Server.  
-->
## Realm.io
_Engine: TightDB_ <br/>
<a href="https://realm.io/"><img src="./extras/banner/android_realm.png" height="72" width="72"/></a><br/>

Look for my notes from Android meetup:<br/>
[ ![STRV](./extras/talks/STRV-Black_small.png) ](https://www.strv.com/)<br/>
[Android Developer Meetup 11/2016 pdf](./extras/talks/realm.meetup.11-2016.pdf)<br/>
[Android Developer Meetup 11/2016 Google slides](https://docs.google.com/presentation/d/1kT0RNL0JYa1TZ2Vs9Ft34XAIS5ej3DEpecz7RnXXXQ8/edit?usp=sharing)<br/>

### Inspect unencrypted data with Stetho/Chrome
chrome://inspect/#devices

### Inspect encrypted data with adb & Realm browser
adb pull /data/data/cz.koto.misak.dbshowcase.android.debug/files/open.realm
adb pull /data/data/cz.koto.misak.dbshowcase.android.debug/files/default.realm



## Open to implement

- **ObjectBox**
https://github.com/greenrobot/ObjectBox
NoSQL object database from the team of GreenRobot.

- **DBFlow**
Refactor/Refresh DBFlow implementation & add this feature to app
(https://github.com/Raizlabs/DBFlow/) - fastest ORM on top of SQLLite engine  
_Engine: SQLLite_  


- **Couchbase Lite**
(http://developer.couchbase.com/mobile/) - NoSQL database solution that delivers NoSQL to mobile.   
_Engine: ForestDB_  



