## README MAX!!

Hey max,
i hope this is something you can work with.

The .osm file for the area is [here](https://github.com/TheGreatRefrigerator/ba_thesis/tree/master/data)

#### Emergency Changes

I think [these](https://gitlab.gistools.geog.uni-heidelberg.de/giscience/openrouteservice/core/commit/94abcf170f3cc001c7c4844e168ed4fab1042608) should be the ony changes, i applied to the files, that are not yet in the github repository. 

- Few changes to the speedmap for "Überlandstraßen"
- Added Pedestrian and Cycleway support (speedmap values + allow bits)
- adjusted speed limit for restricted zones (30 / 70) *but not on links*
- commented the whole hgv stuff out of the collect function (with the new indentation the commit looks larger than it is!) 


#### Duration and Distances 

I was not exactly sure, which of the distances and speeds you need, so have them all, haha.
I tried to display the values as good as possible, so it will be easy for you to work with them:

##### waypoints as minutes

This is the test drive with the Fire truck where each waypoint is 1 minute:
http://emergency.openrouteservice.org/directions?n1=48.460358&n2=10.807242&n3=15&a=48.45414,10.824698,48.45568,10.822102,48.460917,10.811641,48.464054,10.799421,48.46362,10.793488,48.466555,10.789776&b=5b&c=0&f3=3&f1=7.5&f2=2.5&f5=7&d=80&k1=en-US&k2=km 

[The response](https://github.com/TheGreatRefrigerator/ba_thesis/blob/master/json%20examples/time.json)

Here are the Results for each point:

|                                     |   A   |   1   |    2   |    3   |    4   |    B   |
|:-----------------------------------:|:-----:|:-----:|:------:|:------:|:------:|:------:|
|     Distance from last point (m)    |   0,0 | 363,2 |  989,6 |  979,1 | 530,4  | 525,0  |
|       Duration from last point      |   0,0 |  28,6 |   60,1 |   50,7 |  38,2  |  32,2  |
| Actual duration (test drive)        |   0,0 |  60,0 |  60,0  |  60,0  |  60,0  |  60,0  |
|                                     |       |       |        |        |        |        |
| avg speed (km/h)                    |   0,0 |  45,7 |  59,3  |  69,5  |  50,0  |  58,7  |
| avg speed (test drive) (km/h)       |   0,0 |  21,8 |  59,4  |  58,7  |  31,8  |  31,5  |
|                                     |       |       |        |        |        |        |
| Distance total (m)                  |   0,0 | 363,2 | 1352,8 | 2331,9 | 2862,3 | 3387,3 |
| Duration total (s)                  |   0,0 |  28,6 |  88,7  | 139,4  | 177,6  | 209,8  |
| Durat total (test drive)            |   0,0 |  60,0 | 120,0  | 180,0  | 240,0  | 300,0  |
|                                     |       |       |        |        |        |        |
| avg speed total (km/h)              |   0,0 |  45,7 |  52,5  |  58,2  |  56,1  |  56,6  |
| avg speed total (test drive) (km/h) |   0,0 |  21,8 |  40,6  |  46,6  |  42,9  |  40,6  |

(table as .xls in this folder)

##### waypoints as avgspeed separator

And this is the same route with the avgspeed segments as waypoints, so point "A" to "1" has the same speed for the whole segment.
There is one segment for each "extras.avgspeed.values" of the response form the first request:
http://emergency.openrouteservice.org/directions?n1=48.454211&n2=10.81059&n3=15&a=48.45414,10.824698,48.45529,10.824318,48.455721,10.824511,48.458745,10.816139,48.463354,10.802576,48.463704,10.792684,48.46656,10.789777&b=5b&c=0&f3=3&f1=7.5&f2=2.5&f5=7&d=80&k1=en-US&k2=km

[The response](https://github.com/TheGreatRefrigerator/ba_thesis/blob/master/json%20examples/speed.json)

Here are the Results for each point:
**The "real" durations are estimated from the comparison to the first request and made to add up to a total duration of 300 s** 

|                                         |   A   |   1   |   2   |   3   |    4   |    5   |    B   |
|:---------------------------------------:|:-----:|:-----:|:-----:|:-----:|:------:|:------:|:------:|
| Distance from last point (m)            |   0,0 | 132,6 |  50,3 | 757,8 | 1146,6 | 836,9  | 465,1  |
| Duration from last point                |   0,0 |   9,5 |   6,0 |  54,5 |  51,6  |  60,2  |  27,9  |
| duration (test drive) (estimated !!)    |   0,0 |  25,0 |  10,0 |  70,0 |  55,0  |  85,0  |  55,0  |
|                                         |       |       |       |       |        |        |        |
| avg speed (km/h)                        |   0,0 |  50,2 |  30,2 |  50,1 |  80,0  |  50,0  |  60,0  |
| avg speed (test drive) (km/h)           |   0,0 |  19,1 |  18,1 |  39,0 |  75,1  |  35,4  |  30,4  |
|                                         |       |       |       |       |        |        |        |
| Distance total (m)                      |   0,0 | 132,6 | 182,9 | 940,7 | 2087,3 | 2924,2 | 3389,3 |
| Duration total (s)                      |   0,0 |   9,5 |  15,5 |  70,0 | 121,6  | 181,8  | 209,7  |
| Durat total (test drive) (estimated !!) |   0,0 |  25,0 |  35,0 | 105,0 | 160,0  | 245,0  | 300,0  |
|                                         |       |       |       |       |        |        |        |
| avg speed total (km/h)                  |   0,0 |  50,2 |  40,2 |  43,5 |  52,6  |  52,1  |  60,0  |
| avg speed total (test drive) (km/h)     |   0,0 |  19,1 |  18,6 |  25,4 |  37,8  |  37,3  |  30,4  |

(table as .xls in this folder)

Here you can see both in one picture with.

![overlay](https://user-images.githubusercontent.com/23240110/30567994-26d4b2b2-9cd3-11e7-846a-563c7ab7734f.png)