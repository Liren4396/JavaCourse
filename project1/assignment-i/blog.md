The class BlackoutController contains list of Device class and list of Satellite Class. and devicesIds list and satelliteIds list is just to improve listDeviceIds and listSatelliteIds functions, let time complexity from O(n) to O(1) (Trade space for time).

For different types of satellite and device, a little polymorphism is used for initialization.

Class connector is used for file transfer between satellite and satellite or satellite and device.

For sendFile function, which I think is interesting is I can divide the sendFile process into three case :
1.fromSatelliteToDevice
2.fromSatelliteToSatellite
3.fromDevicetoSatellite
That was helpful when I implemented this function.

Finally, I learned that when doing this kind of project again, I should use UML diagrams or conceptualize the rough structure on paper from the beginning, otherwise we need to keep changing the underlying structure like me.

When we have a perfect class, everything will be easy like When I was doing ShrinkingSatellite. I just spent few minutes and finish the roughly logic.
