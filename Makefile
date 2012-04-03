install: bin/PenaltyTimer-debug.apk
	adb install -r $<
	
bin/PenaltyTimer-debug.apk:
	ant debug
