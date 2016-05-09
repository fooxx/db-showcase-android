<h1>Android local database showcase</h1>
![DbShowcase](./mobile/src/main/res/mipmap-hdpi/ic_launcher.png "DbShowcase")

<h2>SQL/NoSQL</h2>

**DBFlow** (https://github.com/Raizlabs/DBFlow/) - fastest ORM on top of SQLLite engine
**Realm.io** (https://realm.io/) - Replacement for SQLLite.
**Couchbase Lite** (http://developer.couchbase.com/mobile/) - a lightweight embedded NoSQL database engine for Android with the built-in ability to sync to Couchbase Server on the backend.

<h2>2. SQL Lite - DBFlow</h2>
_Engine: SQLLite_

<h2>3. Realm.io</h2>
_Engine: Realm_

<h2>4. Couchbase Lite</h2>
_Engine: ForestDB_

Couchbase Mobile is a NoSQL database solution that delivers NoSQL to mobile. 
It's engineered to provide fast and consistent access to data, 
with or without a network connection, removing network dependency. 
It is comprised of three components:

* Couchbase Lite: An embedded NoSQL database that lives locally on mobile devices.
* Couchbase Sync Gateway: An internet-facing cloud component that securely synchronizes data between the mobile device and the cloud.
* Couchbase Server: An enterprise NoSQL database with elastic scalability and consistent high performance.

Among the enhancements to the solution is increased security on mobile devices 
by encrypting data at rest on the device using enterprise level 256-bit AES full database encryption. 
Couchbase Mobile 1.2 offers new management controls for adding and removing nodes 
and new install capabilities that simplify in place upgrades, 
making it easier to administer large enterprise deployments.