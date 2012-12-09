<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/main"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:orientation="vertical"
        >
define(row, `<LinearLayout
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="2"
            android:orientation="horizontal"
            >
        <Button
                android:id="@+id/$1"
                android:text="★ $1"
                android:textSize="24sp"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="fill_parent"
                />
        <Button
                android:id="@+id/$2"
                android:text="$2"
                android:textSize="24sp"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="fill_parent"
                />
    </LinearLayout>')
    row(jl, jr)
    row(bl1, br1)
    row(bl2, br2)
    row(bl3, br3)
    <Button 
            android:id="@+id/pause"
            android:text="Jam End"
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="2"
            android:onClick="pauseButton"
            />
</LinearLayout>
