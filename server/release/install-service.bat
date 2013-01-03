@echo off
adbWirelessService.exe //IS//adbWirelessService --Jvm=auto --Startup=auto --StartMode=jvm --Classpath=%CLASSPATH%;adbWirelessServer.jar --DisplayName="adbWireless Server" --StartClass=siir.es.adbWireless.Server --StartParams=start;8555 --StopClass=siir.es.adbWireless.Server --StopParams=stop

echo Done
@pause
@exit