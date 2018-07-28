
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 10/15/2016.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        //http://stackoverflow.com/questions/38340526/firebase-fcm-token-when-to-send-to-server
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        //add token to sharepreference
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.i("generate token",recent_token);
        editor.putString(Config.SETTING_FCM_TOKEN,recent_token);
        editor.commit();

        //get token from sharepreference.
       // String token = sharedPreferences.getString(Config.SETTING_FCM_TOKEN,"");

        //sendRegistrationToServer(token);




    }

    //update latest token in on the database
    /*
    public void sendRegistrationToServer(String recent_token){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(ConfigBundle.SETTING, Context.MODE_PRIVATE);
        final String new_token =  sharedPreferences.getString(ConfigBundle.SETTING_CURRENT_FCM_TOKEN,"");
       final String id = sharedPreferences.getString(ConfigBundle.SETTING_ID,"");

        if(!(id.equals(0))){

            //initialize StringRequest (volley)
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigBundle.update_token_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //send data to server
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("fcm_token",new_token);
                    params.put("id",id);
                    return params;
                    //return super.getParams();
                }
            };
            MySingleton.getmInstances(FcmInstanceIdService.this).addRequestQueue(stringRequest);

        }

    }
    */
}
