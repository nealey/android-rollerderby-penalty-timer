install: bin/PenaltyTimer-debug.apk
	adb install -r $<

clean:
	ant clean
	
bin/PenaltyTimer-debug.apk: src/org/woozle/penaltytimer/* res/*/*
	ant debug
