README.txt

This Akka project implements a url shortner service. The API are

CREATE a short url
localhost:8080/short?url=http://ducati.com

response
http://localhost:8080/6nHTMm

READ a short url back to the original
http://localhost:8080/6nHTMm

response
http://ducati.com

READ the statistics about a short url
localhost:8080/stats/6nHTMm

response
{"clicks":1}

How to run

The prerequisite to run this project is to have SBT installed. Unzip the project in a directory of your choosing, for example

/Users/mariotti/test

then type 
$ cd /Users/mariotti/test
$ sbt

once sbt is running, type

> run

You should see
***

[info] Loading global plugins from /Users/mariotti/.sbt/0.13/plugins
[info] Set current project to urlshortener (in build file:/Users/mariotti/troops.ai/)
> run
[info] Running com.mariotti.urlshortener.WebServer 
Server online at http://localhost:8080/
Press RETURN to stop...

***

Now you can test the service using, for example, curl commands from another terminal windows. For example 

curl localhost:8080/short?url=ducati.com

curl http://localhost:8080/7pD3-d

curl http://localhost:8080/stats/7pD3-d



Known limitations and designed expansions

1) the create API only accept base URL, no GET parameters can be present 
2) the cache is implemented using a simple hashmap object and it does not check for memory overflow nor it is distributed
3) the persistence is implemented as a file in the OS. While a simple change in the way the file name is generated can enable file system sharding, the expectation is that this would be replaced by a DB
4) the url can be mapped to short url any number of times. This is by design at this time and it is similar to many existing services
5) there is no authentication on the stats read API, this is a security vulnerability
6) the stats data structure can be easily expanded to store more data
7) the write operation are synchronous due to development time constraint: an AKKA actor approach should be used for all writes so to decouple this time intensive operation from the handling of requests. This is particularly harmful during the CREATE API since a write is performed to update the stats.
8) to make the read non blocking the mapping from a short url to a full url are stored in individual files that can be cached. However (7) virtually void the benefit of this approach.
9) race conditions are not handled for (7) and concurrency control is delegated to the file O/S