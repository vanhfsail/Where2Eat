package com.where2eat.util;

import java.util.ArrayList;  
import java.util.List;    

import org.json.JSONArray;  
import org.json.JSONObject;  

import com.where2eat.entiy.Reserve;
import com.where2eat.entiy.Restaurant;
import com.where2eat.entiy.User;
 
public class JSONTools {  
  
    public JSONTools() {  
        // TODO Auto-generated constructor stub  
    }  
      
    public static List<User> getUsers(String key, String jsonString){  
        List<User> list = new ArrayList<User>();  
        try {  
            JSONObject jsonObject = new JSONObject(jsonString);  
            //返回json的数组  
            JSONArray jsonArray = jsonObject.getJSONArray(key);  
            for(int i = 0; i < jsonArray.length(); i++){  
                JSONObject jObject = jsonArray.getJSONObject(i);  
                User ins = new User();  
                ins.setUserId(jObject.getString("userId"));  
                ins.setuMail(jObject.getString("uMail"));
                ins.setuPhone(jObject.getString("uPhone"));
                ins.setUserName(jObject.getString("userName"));
                ins.setUserPassword(jObject.getString("userPassword"));
                list.add(ins);  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
        return list;  
    }  
    
    
    
    public static List<Restaurant> getRestaurant(String key, String jsonString){  
        List<Restaurant> list = new ArrayList<Restaurant>();  
        try {  
            JSONObject jsonObject = new JSONObject(jsonString);  
            //返回json的数组  
            JSONArray jsonArray = jsonObject.getJSONArray(key);  
            for(int i = 0; i < jsonArray.length(); i++){  
                JSONObject jObject = jsonArray.getJSONObject(i);  
                Restaurant r = new Restaurant();
                r.setId(jObject.getString("restId"));
                r.setAddress(jObject.getString("restAddress"));
                r.setAvgConcume(jObject.getString("avgConsume"));
                r.setDescription(jObject.getString("description"));
                r.setImage(jObject.getString("imgUrl"));
                r.setPhone(jObject.getString("restPhone"));
                r.setType(jObject.getString("name"));
                r.setName(jObject.getString("restName"));
                r.setTime(jObject.getString("time"));
               r.setScore(jObject.getString("avgScore"));
             
               
                list.add(r);  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
        return list;  
    }  
    
    public static List<Reserve> getReserve(String key, String jsonString){  
        List<Reserve> list = new ArrayList<Reserve>();  
        try {  
            JSONObject jsonObject = new JSONObject(jsonString);  
            //返回json的数组  
            JSONArray jsonArray = jsonObject.getJSONArray(key);  
            for(int i = 0; i < jsonArray.length(); i++){  
                JSONObject jObject = jsonArray.getJSONObject(i);  
                Reserve r = new Reserve();
                r.setrImg(jObject.getString("imgUrl"));
                r.setNote(jObject.getString("note"));
                r.setId(jObject.getString("reserveId"));
                r.setrType(jObject.getString("name")); 
               r.setReserveTime(jObject.getString("reserveDate"));
               r.setrName(jObject.getString("restName"));
              
               
                list.add(r);  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
        return list;  
    }  
    
    public static List<Reserve> getDetailReserve(String key, String jsonString){  
        List<Reserve> list = new ArrayList<Reserve>();  
        try {  
            JSONObject jsonObject = new JSONObject(jsonString);  
            //返回json的数组  
            JSONArray jsonArray = jsonObject.getJSONArray(key);  
            for(int i = 0; i < jsonArray.length(); i++){  
                JSONObject jObject = jsonArray.getJSONObject(i);  
                Reserve r = new Reserve();
                r.setComment(jObject.getString("comments"));
                r.setUserName(jObject.getString("userName"));
               r.setrName(jObject.getString("restName"));
             
               r.setReserveTime(jObject.getString("reserveDate"));
              // r.setId(jObject.getString("reserveId"));
               r.setrPhone(jObject.getString("restPhone"));
               r.setReservePhone(jObject.getString("phone"));
               r.setrAddress(jObject.getString("restAddress"));
               
                list.add(r);  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
        return list;  
    }  
      
    
}  
