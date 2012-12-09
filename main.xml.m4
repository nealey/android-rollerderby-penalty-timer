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
    row(b1l, b1r)
    row(b2l, b2r)
    row(b3l, b3r)
    <Button 
            android:id="@+id/pause"
            android:text="Jam End"
            android:layout_width="fill_parent"
            android:layout_height="0dp"
            android:layout_weight="2"
            android:onClick="pauseButton"
            />
</LinearLayout>
