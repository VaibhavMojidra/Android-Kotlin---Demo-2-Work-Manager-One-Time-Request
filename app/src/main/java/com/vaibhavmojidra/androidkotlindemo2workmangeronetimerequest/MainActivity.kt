package com.vaibhavmojidra.androidkotlindemo2workmangeronetimerequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.vaibhavmojidra.androidkotlindemo2workmangeronetimerequest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        //Will set constraint that even if clicked on start button it will only start if mobile is charging
        val constraints=Constraints.Builder().setRequiresCharging(true).build()

        //To pass/input Data in work request
        val data=Data.Builder().putInt("UPPER_LIMIT",600000).build()


        //To start one time work request.
        val myWorkRequest=OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val workMangerInstance=WorkManager.getInstance(applicationContext)




        //Will start when button clicked
        binding.startWorkButton.setOnClickListener {
            workMangerInstance.enqueue(myWorkRequest)
        }


        workMangerInstance.getWorkInfoByIdLiveData(myWorkRequest.id).observe(this) {
            binding.workMangerStatusTextView.text=it.state.name
            //Getting Output Data
            if(it.state.isFinished){
                val outputData=it.outputData.getInt("TOTAL_EVEN_NUMBERS",0)
                Toast.makeText(this@MainActivity,"Total Even Numbers: $outputData",Toast.LENGTH_LONG).show()
            }
        }

    }
}