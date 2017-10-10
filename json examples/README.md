## Texstdrive

The .osm file for the area is [here](https://github.com/TheGreatRefrigerator/ba_thesis/tree/master/data)

### Emergency Changes

I think [these](https://gitlab.gistools.geog.uni-heidelberg.de/giscience/openrouteservice/core/commit/94abcf170f3cc001c7c4844e168ed4fab1042608) should be the ony changes, i applied to the files, that are not yet in the github repository. 

- Few changes to the speedmap for "Überlandstraßen"
- Added Pedestrian and Cycleway support (speedmap values + allow bits)
- adjusted speed limit for restricted zones (30 / 70) *but not on links*
- commented the whole hgv stuff out of the collect function (with the new indentation the commit looks larger than it is!) 

### First Isochrones with current speedmap

First requests:
Fireengine:
https://disaster-api.openrouteservice.org/emergency/isochrones?attributes=area|reachfactor&format=json&interval=60&location_type=start&locations=10.824415,48.454063&options={"profile_params":{"weightings":{},"restrictions":{"width":"2.5","height":"3","weight":"7.5","length":"7"}},"maximum_speed":"80"}&profile=driving-emergency&range=300&range_type=time

basic emergency:
https://disaster-api.openrouteservice.org/emergency/isochrones?attributes=area|reachfactor&format=json&interval=60&location_type=start&locations=10.824415,48.454063&options={"maximum_speed":"130"}&profile=driving-emergency&range=300&range_type=time

hgv:
https://disaster-api.openrouteservice.org/emergency/isochrones?attributes=area|reachfactor&format=json&interval=60&location_type=start&locations=10.824415,48.454063&options={"profile_params":{"weightings":{},"restrictions":{"width":"2.5","height":"3","weight":"7.5","length":"7"}},"vehicle_type":"hgv","maximum_speed":"80"}&profile=driving-hgv&range=300&range_type=time

car:
https://disaster-api.openrouteservice.org/emergency/isochrones?attributes=area|reachfactor&format=json&interval=60&location_type=start&locations=10.824415,48.454063&options={"maximum_speed":"130"}&profile=driving-car&range=300&range_type=time

![5min_big](https://user-images.githubusercontent.com/23240110/31310514-9775a498-ab99-11e7-8c8c-284abd5e3dc9.png)

Way too far for First Test Drive.

Testdrive with minute Markers needed!

### Duration and Distances 

I was not exactly sure, which of the distances and speeds will be needed, so here are all, haha.
I tried to display the values as good as possible, so it will be easy for to work with them:

#### waypoints as minutes

[This](http://emergency.openrouteservice.org/directions?n1=48.460358&n2=10.807242&n3=15&a=48.45414,10.824698,48.45568,10.822102,48.460917,10.811641,48.464054,10.799421,48.46362,10.793488,48.466555,10.789776&b=5b&c=0&f3=3&f1=7.5&f2=2.5&f5=7&d=80&k1=en-US&k2=km 
) is the test drive with the Fire truck where each waypoint is 1 minute:

![time](https://user-images.githubusercontent.com/23240110/30568811-8d74bd56-9cd7-11e7-86e9-930772f2151d.png)

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

#### waypoints as avgspeed separator

And [this](http://emergency.openrouteservice.org/directions?n1=48.454211&n2=10.81059&n3=15&a=48.45414,10.824698,48.45529,10.824318,48.455721,10.824511,48.458745,10.816139,48.463354,10.802576,48.463704,10.792684,48.46656,10.789777&b=5b&c=0&f3=3&f1=7.5&f2=2.5&f5=7&d=80&k1=en-US&k2=km) is the same route with the avgspeed segments as waypoints, so point "A" to "1" has the same speed for the whole segment.
There is one segment for each "extras.avgspeed.values" of the response form the first request:

![avgspeed](https://user-images.githubusercontent.com/23240110/30568818-8fb7baf0-9cd7-11e7-9293-0d7063a4f61c.png)

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

Here you can see both in one picture with. Yellow waypoints are minutes, blue avgspeed separators.

![overlay](https://user-images.githubusercontent.com/23240110/30567994-26d4b2b2-9cd3-11e7-846a-563c7ab7734f.png)


### Testdrive 1 Repeat

Front
http://localhost:3005/directions?n1=48.454186&n2=10.825616&n3=18&a=48.454111,10.824569,48.45568,10.822102,48.460917,10.811641,48.464054,10.799421,48.46362,10.793488,48.466555,10.789776&b=5b&c=0&f3=3&f1=7.5&f2=2.5&f5=7&d=80&k1=en-US&k2=km

Back
http://localhost:8082/openrouteservice-4.3.0/routes?api_key=58d904a497c67e00015b45fcbd837ca3e137425f653e26a676ecd396&attributes=detourfactor|percentage&coordinates=10.824569,48.454111|10.822102,48.45568|10.811641,48.460917|10.799421,48.464054|10.793488,48.46362|10.789776,48.466555&elevation=true&extra_info=steepness|waytype|surface|avgspeed&geometry=true&geometry_format=geojson&instructions=true&instructions_format=html&language=en-US&options={"profile_params":{"restrictions":{"width":"2.5","height":"3","weight":"7.5","length":"7"}},"maximum_speed":"80"}&preference=fastest&profile=driving-emergency&units=m

Start
10.824569,48.454111

10.822102,48.45568
10.811641,48.460917
10.799421,48.464054
10.793488,48.46362
10.789776,48.466555


### All in one
![crop](https://user-images.githubusercontent.com/23240110/31310522-cc49c2a8-ab99-11e7-88a7-75a0731e9315.png)

Calibration on drive 1
:
### Weighting 1

Start/End 15 sek
Turn 20 sek

#### Drive 1 

<!-- Wegpunkt  Sekunden  Differenz
1min  78,3   +18,3
2min  138.5  +18,5
3min  188.5  +8,5
4min  226.7  -3,3
5min  298.9  -1,1 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|     1min |     78,3 |     +18,3 |
|     2min |    138.5 |     +18,5 |
|     3min |    188.5 |      +8,5 |
|     4min |    226.7 |      -3,3 |
|     5min |    298.9 |      -1,1 |

### Testdrive 2

Front
http://localhost:3005/directions?n1=48.454236&n2=10.826409&n3=18&a=48.454111,10.824569,48.456986,10.827488,48.459512,10.835567,48.46324,10.84622,48.461468,10.852572,48.457434,10.85755&b=5b&c=0&d=80&f3=3&f1=7.5&f2=2.5&f5=7&k1=en-US&k2=km

Back
http://localhost:8082/openrouteservice-4.3.0/routes?api_key=58d904a497c67e00015b45fcbd837ca3e137425f653e26a676ecd396&attributes=detourfactor|percentage&coordinates=10.824569,48.454111|10.827488,48.456986|10.835567,48.459512|10.84622,48.46324|10.852572,48.461468|10.85755,48.457434&elevation=true&extra_info=steepness|waytype|surface|avgspeed&geometry=true&geometry_format=geojson&instructions=true&instructions_format=html&language=en-US&options={"profile_params":{"restrictions":{"width":"2.5","height":"3","weight":"7.5","length":"7"}},"maximum_speed":"80"}&preference=fastest&profile=driving-emergency&units=m

Start
10.824569,48.454111

WPS 1-5
10.827488,48.456986
10.835567,48.459512
10.84622,48.46324
10.852572,48.461468
10.85755,48.457434


### Testdrive 3

Front
http://localhost:3005/directions?n1=48.44654&n2=10.826383&n3=15&a=48.454111,10.824569,48.454111,10.821297,48.448041,10.819463,48.438369,10.816973,48.440618,10.806824,48.443849,10.805097&b=5b&c=0&d=80&f3=3&f1=7.5&f2=2.5&f5=7&k1=en-US&k2=km

Back
http://localhost:8082/openrouteservice-4.3.0/routes?api_key=58d904a497c67e00015b45fcbd837ca3e137425f653e26a676ecd396&attributes=detourfactor|percentage&coordinates=10.824569,48.454111|10.821297,48.454111|10.819463,48.448041|10.816973,48.438369|10.806824,48.440618|10.805097,48.443849&elevation=true&extra_info=steepness|waytype|surface|avgspeed&geometry=true&geometry_format=geojson&instructions=true&instructions_format=html&language=en-US&options={"profile_params":{"restrictions":{"width":"2.5","height":"3","weight":"7.5","length":"7"}},"maximum_speed":"80"}&preference=fastest&profile=driving-emergency&units=m

Start
10.824569,48.454111

WPS 1-5
10.821297,48.454111
10.819463,48.448041
10.816973,48.438369
10.806824,48.440618
10.805097,48.443849


#### Drive 2 

<!-- 1min:  86.6  +26.6
2min:  149.9  +29.9
3min:  207.5  +27.5
4min:  253.5  +13.5
5min:  318.6  +18.6 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|    1min: |     86.6 |     +26.6 |
|    2min: |    149.9 |     +29.9 |
|    3min: |    207.5 |     +27.5 |
|    4min: |    253.5 |     +13.5 |
|    5min: |    318.6 |     +18.6 |


#### Drive 3

<!-- 1min:   89.8  +29.8
2min:   162.4  +42.4
3min:   213  +33
4min:   282.3  +42.3
5min:   343.9  +43.9 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|    1min: |     89.8 |     +29.8 |
|    2min: |    162.4 |     +42.4 |
|    3min: |      213 |       +33 |
|    4min: |    282.3 |     +42.3 |
|    5min: |    343.9 |     +43.9 |


#### Implement new Features

- Elevation
- Waytypes
- Surface
- Avg Speed

with elevation Height Profile visible
-> 70 m Incline

### Realization

Start end as well as turn penalty too high

###weighting 2

Start/End: 7.5
Turn: 16


#### Drive 1

<!-- 1min:  59.5  -0.5
2min:  119.5  -0.5
3min:  169.5  -10.5
4min:  207.7  -32.2
5min:  271.9  -28.1 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|    1min: |     59.5 |      -0.5 |
|    2min: |    119.5 |      -0.5 |
|    3min: |    169.5 |     -10.5 |
|    4min: |    207.7 |     -32.2 |
|    5min: |    271.9 |     -28.1 |

#### Drive 2

<!-- 1min:  67.6  +7.6
2min:  126.9  +6.9
3min:  184.5  +4.5
4min:  230.5  -10.5
5min:  291.6  -8.4 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|    1min: |     67.6 |      +7.6 |
|    2min: |    126.9 |      +6.9 |
|    3min: |    184.5 |      +4.5 |
|    4min: |    230.5 |     -10.5 |
|    5min: |    291.6 |      -8.4 |


#### Drive 3

<!-- 1min:   70.8    +10.8
2min:   139.4  +19.4
3min:   190  +10
4min:   255.2  +15.2
5min:   312.9  +12.9 -->

| Wegpunkt | Sekunden | Differenz |
|----------|----------|-----------|
|    1min: |     70.8 |     +10.8 |
|    2min: |    139.4 |     +19.4 |
|    3min: |      190 |       +10 |
|    4min: |    255.2 |     +15.2 |
|    5min: |    312.9 |     +12.9 |


<!-- 10.824569,48.454111|10.822102,48.45568
10.824569,48.454111|10.811641,48.460917
10.824569,48.454111|10.799421,48.464054
10.824569,48.454111|10.793488,48.46362
10.824569,48.454111|10.789776,48.466555
 -->

### Conclusions

Drive 1:
no Incline
52% 1-3% 
25% 4-5%
Larger sharp turns not detected
34% 80km/h segment a bit too fast

Drive 2:
mostly ok +-10s
bit of holdup around the railwaytunnel
(one lane only,)

32 % 80km/h segment a bit too fast

Drive 3:

30km/h Zone not detected and parsed as maxspeed 50
long 80km/h (56%) segment that speeds u
Turn after WP 3 increases WP 3 to  WP 4 of 16 sec (too much)
copyright of clip goes to (c) 2017 Google,Kartendaten (c)2017 GeoBasis-DE/BKG ((c)2009),Google
no incline 
for 150 m 7-9 % incline!

Next Steps for Profile
Bugfixes:
oneway factor for "parallel" streets
include weighting for incline
fix 30km/h Zone
long 80 segment, should fix nothing, failure should be maxspeed beeing set a bit too high.
decrease turn time a little bit 
bzw. calculate Turn time with actual acceleration (given as parameter or factor of vehicleweight)
grade of the track overwrites given max speed (Drive 3 last step: grade 3 -> 15 km/h although maxspeed=50)
get acceleration working for isochrones

