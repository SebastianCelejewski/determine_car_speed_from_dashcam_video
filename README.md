# Determine Car Speed From Dashcam Video

![asdfasdf](https://github.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/raw/master/doc/screenshot.png)

Usage:

1. Get a dashcam video (e.g. download it from YouTube using http://www.clipconverter.cc).
2. Convert video into frames (e.g. using Corel Video Studio). You may also use frames from /sample directory.
3. Run CSFDV application.
4. Create a new project and point to a directory where your video frames reside.
5. Navigate through the frames and select those for which the lane separation line crosses the frame boundary.
6. Enter video fps.
7. Enter distance between two starting points of lane separation lines (e.g. 12 meters for Polish highways).
8. The application should calculate the speed of the car.

Enjoy.

## Accuracy

- Absolute time error = 1 frame = 1/FPS

- Absolute distance error = 1 meter

- Relative time error = absolute time error / time difference between data points

- Relative distance error = absolute distance error / distance between lane separation lines

- Total relative error = relative time error + relative distance error

- Total absolute error = total relative error * calculated speed

Example:

- FPS = 30 fps

- Distance between lane separation lines = 12 m

- Time difference between data points = 10 frames = 1/3 s

- Calculated speed = 36 m/s = 130 km/h

- Relative time error = 10%

- Relative distance error = 8.3%

- Total relative error = 20%

- Total absolute error = +/- 30 km/h

- Result: speed = 130 +/- 30 km/h = between 100 and 160 km/h

- Conclusion: data point have to be averaged

## Validation

Results from this program have been compared to the speed indicated by a dashcam camera (i.e. calculated from GPS data).

![](https://raw.githubusercontent.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/master/doc/chart_test_01.PNG)

Red line: speed indicated by the dashcam itself.

Black line: speed calculated by the program (average from 9 data points).


## Examples

### Example 1: https://www.youtube.com/watch?v=OynRyNZD8s4

![](https://github.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/blob/master/doc/movie_clip_01.PNG?raw=true)
![](https://github.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/blob/master/doc/chart_01.PNG?raw=true)

### Example 2: https://www.youtube.com/watch?v=kKx9Hj4ssWo

![](https://github.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/blob/master/doc/movie_clip_02.PNG?raw=true)
![](https://github.com/SebastianCelejewski/determine_car_speed_from_dashcam_video/blob/master/doc/chart_02.PNG?raw=true)
