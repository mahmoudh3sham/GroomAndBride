package com.groomandbride.data.network;

import com.groomandbride.data.body.LoginBody;
import com.groomandbride.data.body.RegisterBody;
import com.groomandbride.data.body.ReqCategoryHallBody;
import com.groomandbride.data.body.ReqFavoritesBody;
import com.groomandbride.data.body.ReqSearchHallBody;
import com.groomandbride.data.body.UpdatePassBody;
import com.groomandbride.data.models.AddToFavModel;
import com.groomandbride.data.models.FavoritesModel;
import com.groomandbride.data.models.FeedBackModel;
import com.groomandbride.data.models.ForgetPassword;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.models.PrivacyPolicyTermsModel;
import com.groomandbride.data.models.RateModel;
import com.groomandbride.data.models.RegisterModel;
import com.groomandbride.data.models.RemoveFromFavModel;
import com.groomandbride.data.models.UpdatePassword;
import com.groomandbride.data.models.UpdateProfile;
import com.groomandbride.data.models.UserModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //@Headers({"Content-Type:application/x-www-form-urlencoded"})


    @POST("halls/listHalls")
    Call<Hall> getAllHalls(@Body HashMap<String, Integer> mHashBody);

    @POST("halls/searchByCategory")
    Call<Hall> getCategoryHalls(@Body ReqCategoryHallBody reqCategoryHallBody);

    @FormUrlEncoded
    @POST("policyAndPrivacy/getPolicyAndPrivacy")
    Call<PrivacyPolicyTermsModel> getPrivacyPolicy(@Field("type") String type);


    @POST("users/signup")
    Call<RegisterModel> userRegister(@Body RegisterBody registerBody);


    @POST("users/signin")
    Call<UserModel> userLogin(@Body LoginBody loginBody);

    @POST("users/updatePassword")
    Call<UpdatePassword> updatePassword(@Body UpdatePassBody updatePassBody);

    @FormUrlEncoded
    @POST("users/forgetPassword")
    Call<ForgetPassword> forgetPassReq(@Field("email") String email);

    @FormUrlEncoded
    @POST("users/updateBasicInfo")
    Call<UpdateProfile> updateUsername(@Field("userName") String username);

    @FormUrlEncoded
    @POST("feedback/addFeedback")
    Call<FeedBackModel> sendFeedBack(@Field("email") String email, @Field("text") String textMsg);

    @FormUrlEncoded
    @POST("favorites/addToFavorites")
    Call<AddToFavModel> addToFav(@Field("hallId") String hallId);

    @FormUrlEncoded
    @POST("favorites/deleteFromFavorites")
    Call<RemoveFromFavModel> removeFromFav(@Field("hallId") String hallId);

    @FormUrlEncoded
    @POST("rating/rateHalls")
    Call<RateModel> rateHall(@Field("hallId") String hallId, @Field("rating") int rate);

    @POST("halls/searchByName")
    Call<Hall> getSearchRes(@Body ReqSearchHallBody reqSearchHallBody);


    @POST("favorites/listFavorites")
    Call<FavoritesModel> getFavs(@Body ReqFavoritesBody reqFavorites);
}


