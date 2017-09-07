## 2.0.0
Require at least Android Studio 3.0 Canary 5!
### Features
* Add working example of db-flow implementation
* Add ability to switch Realm & DBFlow at runtime in the app

### Enhancement
* Remove retrolamda library



## 1.3.1
### Bug fixing
* Fix situation after first uninstall/install cycle. If the realm file is not deleted after uninstall,
next installation will recognize this encrypted zombie file and will ignore it. This is necessary behaviour until
there will be implemented BACKUP PLAN (to restore encryption key base on original password) - which is not implemented yet.

## 1.3.0
### Enhancement
* Bump KeystoreCompat to KC-1.0.3-BETA (It change encryption from RSA to AES on API 23+)
This change also brings different behaviour since API 23 (encryption also requires lockScreen)

## 1.2.0
### Enhancement
* Update with usage of KeystoreCompat KC-0.6.0-BETA (handling with rooted devices).


## 1.1.1
### Bug Fixes
* Fix buggy refresh behaviour after adding new class or student.

## 1.1.0

### Enhancement
* Add ability to encrypt database and store encryption key to Android Keystore

## 1.0.1
### Bug Fixes
* Fix bug in bottom navigation bar

## 1.0.0

* First release with basic Realm.io implementation