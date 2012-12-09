ALIAS = dartcatcher
KEYSTORE = /home/neale/.local/android.keystore

SDK = /opt/android-sdk

ZIPALIGN = $(SDK)/tools/zipalign

default: release-install

install: debug-install
debug-install: bin/PenaltyTimer-debug.apk
	adb install -r $<

release: bin/PenaltyTimer-release.apk
release-install: bin/PenaltyTimer-release.apk
	adb install -r $<

clean:
	ant clean

res/layout/main.xml: main.xml.m4
	m4 $< > $@

bin/PenaltyTimer-release.apk: bin/PenaltyTimer-release-unaligned.apk
	$(ZIPALIGN) -f 4 $< $@
	
bin/PenaltyTimer-release-unaligned.apk: bin/PenaltyTimer-release-unsigned.apk
	jarsigner -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore $(KEYSTORE) -signedjar $@ $< $(ALIAS)

bin/PenaltyTimer-release-unsigned.apk: src/org/woozle/penaltytimer/* res/*/* res/layout/main.xml
	ant release

bin/PenaltyTimer-debug.apk: src/org/woozle/penaltytimer/* res/*/* res/layout/main.xml
	ant debug
