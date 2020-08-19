package com.example.commonui.samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.commonui.R
import kotlinx.android.synthetic.main.activity_common.*

class CommonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        initView()
    }

    private fun initView() {
        tv_count_down.setOnClickListener {
            tv_count_down.start()
            Toast.makeText(this@CommonActivity, "发送验证码", Toast.LENGTH_SHORT).show()
        }
    }
}
