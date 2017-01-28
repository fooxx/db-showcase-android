#Android local database showcase

![DbShowcase](./mobile/src/main/res/mipmap-hdpi/ic_launcher.png "DbShowcase") <a href="https://play.google.com/store/apps/details?id=cz.koto.misak.dbshowcase.android"><img src="./extras/banner/google-play-badge.png" height="72"/></a>

[![CircleCI](https://circleci.com/gh/kotomisak/db-showcase-android/tree/develop.svg?style=shield)](https://circleci.com/gh/kotomisak/db-showcase-android/tree/develop)


This is sample of local database implementation for small set of Android related local databases.

##Model
Every implemented database is based on the same application model:  

**SCHOOL CLASS** _(can have more students, more teachers)_  
**TEACHER** _(can teach more classes)_  
**STUDENT** _(can be in one class only, can have more teachers)_  

![ModelIllustration](./extras/screens/scr_showcase_dbflow_int1.png "ModelIllustration")| ![ModelIllustration](./extras/screens/scr_showcase_dbflow_int2.png "ModelIllustration")

./gradlew assembleRelease

<!--**Couchbase Lite** (http://developer.couchbase.com/mobile/) - a lightweight embedded NoSQL database engine for Android with the built-in ability to sync to Couchbase Server.  
-->
##2. Realm.io
:large_orange_diamond: In progress...  
(https://realm.io/) - replacement for SQLLite.   
_Engine: TightDB_

Look for my notes from
[Android Developer Meetup 11/2016 pdf](./extras/talks/realm.meetup.11-2016.pdf)
[Android Developer Meetup 11/2016 Google slides](https://docs.google.com/presentation/d/1kT0RNL0JYa1TZ2Vs9Ft34XAIS5ej3DEpecz7RnXXXQ8/edit?usp=sharing)

### Inspect data with chrome
chrome://inspect/#devices

##3. SQL Lite - DBFlow
:heavy_exclamation_mark: Prepared some early written code, but not refactored yet...  
(https://github.com/Raizlabs/DBFlow/) - fastest ORM on top of SQLLite engine  
_Engine: SQLLite_  



<h2>4. Couchbase Lite</h2>
:x: Not started yet...  
(http://developer.couchbase.com/mobile/) - NoSQL database solution that delivers NoSQL to mobile.   
_Engine: ForestDB_  

Couchbase Mobile is a NoSQL database solution that delivers NoSQL to mobile. 
It's engineered to provide fast and consistent access to data, 
with or without a network connection, removing network dependency. 
It is comprised of two components:

* Couchbase Lite: An embedded NoSQL database that runs on the device, in your application, with a very small footprint.
* Couchbase Sync Gateway: An internet-facing cloud component that securely synchronizes data between the mobile device and the cloud.

Among the enhancements to the solution is increased security on mobile devices 
by encrypting data at rest on the device using enterprise level 256-bit AES full database encryption. 

