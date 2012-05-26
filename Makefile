install: debug-install
debug-install: bin/PenaltyTimer-debug.apk
	adb install -r $<

release-install: bin/PenaltyTimer-release.apk
	adb install -r $<

clean:
	ant clean
	
bin/PenaltyTimer-release.apk: src/org/woozle/penaltytimer/* res/*/*
	ant release

bin/PenaltyTimer-debug.apk: src/org/woozle/penaltytimer/* res/*/*
	ant debug
