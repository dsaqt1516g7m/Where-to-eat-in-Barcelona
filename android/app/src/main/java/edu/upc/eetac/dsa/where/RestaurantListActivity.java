package edu.upc.eetac.dsa.where;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;

import edu.upc.eetac.dsa.where.Client.Entity.Restaurant;
import edu.upc.eetac.dsa.where.Client.Entity.RestaurantCollection;
import edu.upc.eetac.dsa.where.Client.WhereClient;
import edu.upc.eetac.dsa.where.Client.WhereClientException;

/**
 * Created by Carolina on 18/01/2016.
 */
public class RestaurantListActivity extends AppCompatActivity {

    private final static String TAG = RestaurantListActivity.class.toString();
    private GetStingsTask mGetStingsTask = null;
    private RestaurantCollection restaurants = new RestaurantCollection();
    private RestaurantCollectionAdapter adapter = null;



    class GetStingsTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetStingsTask(String uri) {
            this.uri = uri;
        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonRestaurantCollection = null;
            try{
                jsonRestaurantCollection = WhereClient.getInstance().getRestaurants(uri);
                Log.d("CAROL", jsonRestaurantCollection);
            }catch(WhereClientException e){
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonRestaurantCollection;
        }

        @Override
        protected void onPostExecute(String RestCollect) {
            JSONObject jo = null;
            JSONArray resta = null;
            try {
                jo = new JSONObject(RestCollect);
                resta = jo.getJSONArray("restaurants");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("CAROL LINK", resta.toString());
            RestaurantCollection restCollection = (new Gson()).fromJson(RestCollect , RestaurantCollection.class);

            for(Restaurant rest : restCollection.getRestaurants()){
                restaurants.getRestaurants().add(restaurants.getRestaurants().size(), rest);
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurants_list);


        // Execute AsyncTask
        mGetStingsTask = new GetStingsTask(null);
        mGetStingsTask.execute((Void) null);

        // set list adapter
        ListView list = (ListView)findViewById(R.id.list);
        adapter = new RestaurantCollectionAdapter(this, restaurants);
        list.setAdapter(adapter);




        // set list OnItemClick listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
                String uri = WhereClient.getLink(restaurants.getRestaurants().get(position).getLinks(), "self").getUri().toString();
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });



    }


}
