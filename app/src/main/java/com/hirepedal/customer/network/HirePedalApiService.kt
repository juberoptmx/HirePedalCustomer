package com.elyeproj.wikisearchcount

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query




interface HirePedalApiService {

    @GET("api.php")
    fun hitCountCheck(@Query("action") action: String,
                      @Query("format") format: String,
                      @Query("list") list: String,
                      @Query("srsearch") srsearch: String): Observable<HirePedalModel.Result>

    @GET("customer")
    fun getAllCustomers(): Observable<HirePedalModel.AllCustomerResult>



    @GET("customer/5b3fb3fca2ea8bb0861611f8")
    fun getID(): Observable<HirePedalModel.AllCustomerResult>




    companion object {
        fun create(): HirePedalApiService {

            val defaultHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                                .addHeader("Authorization", "Basic YWRtaW46cGEkJHcwcmQ=").build()
                        chain.proceed(request)
                    }.build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://ec2-52-221-181-195.ap-southeast-1.compute.amazonaws.com:8080/hirePedal/").client(defaultHttpClient)
                    .build()

            return retrofit.create(HirePedalApiService::class.java)
        }
    }





    // ec2-52-221-181-195.ap-southeast-1.compute.amazonaws.com:8080/hirePedal/customer
    //ec2-52-221-181-195.ap-southeast-1.compute.amazonaws.com:8080/hirePedal/customer?filter="{'firstName':'Gaurav'}"&filter="{'lastName':'kumar'}"

    //https://en.wikipedia.org/w/api.php?action=query&format=json&list=search&srsearch=Trump,

}