# DevBot App

This app runs on Android and controls [DevBot](https://github.com/janapavlasek/devbot).
DevBot must be running both the backend server and the motor controls node:

```bash
rosrun android android_backend.py
rosrun controls motor_controls.py
```

The app currently only supports four motion commands:
* Left
* Right
* Forward
* Stop
Eventually, it will be able to support any number of motion commands with a
pretty UI.
