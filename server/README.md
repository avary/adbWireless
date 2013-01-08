adbWireless Service
----------
adbWireless Service is a small server written in Java that can be installed as a Windows service

Use [Procrun](http://commons.apache.org/daemon/procrun.html) from Apache Commons Daemon

**This service allows adbWireless automatic connection to your computer**


Requirements
----------
* Java Runtime Enviroment (jre)
* Android ADB (adb binary in PATH)


Windows installation
----------
Go to folder `adbWireless\server\release\`, in this folder you'll find two files:

* `install-service.bat`: installs the service
* `uninstall-service.bat`: uninstall the service

The default service name is `adbWirelessService`

The default port is `8555`

Edit the file "install-service.bat" can change these values

Start the service `net start adbWirelessService` as **administrator** or `Run (Win+R) -> services.msc -> ...`

If you have problems you can run the service from the command line and see the debug:
`adbWirelessService.exe //TS/adbWirelessService`


Automatic connection
----------
Install and start the service

In the phone activates the `Auto connection` and enter the IP of the computer where you installed the service

When you enable ADB in adbWireless, will automatically connect to the configured IP


Linux
----------
I'm watching [jsvc](http://commons.apache.org/daemon/jsvc.html) for linux service