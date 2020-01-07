package com.example.android.zhetai.fingerboss

import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.example.android.zhetai.fingerboss.manager.TouchAreaManager

class MainActivity : Activity() {
    private lateinit var mainConstraintLayout: ConstraintLayout
    private lateinit var touchAreaManager: TouchAreaManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainConstraintLayout = findViewById(R.id.main_constraint_layout)
        touchAreaManager = TouchAreaManager(applicationContext, mainConstraintLayout)
    }
}
