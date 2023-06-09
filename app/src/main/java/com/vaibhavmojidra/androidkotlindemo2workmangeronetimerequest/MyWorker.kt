package com.vaibhavmojidra.androidkotlindemo2workmangeronetimerequest

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class MyWorker(context: Context,parameters: WorkerParameters):Worker(context,parameters) {

    override fun doWork(): Result {
        try {
            val upperLimit=inputData.getInt("UPPER_LIMIT",0)
            var evenNumberCount=0
            for (i in 0..upperLimit){
                if(i%2==0){
                    evenNumberCount++
                }
                Log.i("MyTag","Number $i")
            }

            //Passing Output data/ returning data
            val outputData=Data.Builder().putInt("TOTAL_EVEN_NUMBERS",evenNumberCount).build()

            //throw IllegalAccessException("Failed")

            return Result.success(outputData)

        }catch (e:Exception){
            return Result.failure()
        }
    }

}