package edu.upc.eetac.dsa.where;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.where.Client.Entity.Restaurant;
import edu.upc.eetac.dsa.where.Client.WhereClient;
import edu.upc.eetac.dsa.where.Client.WhereClientException;

/**
 * Created by Carolina on 18/01/2016.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    GetRestaurantTask mGetRestaurantTask = null;
    String uri = null;
    String name = null;
    int likes =0 ;
    String slikes = null;
    String address = null;
    String description = null;
    String phone = null;
    private final static String TAG = RestaurantDetailActivity.class.toString();


    TextView textViewName= null;
    TextView textViewDescription= null;
    TextView textViewLikes= null ;
    TextView textViewaddress= null ;
    TextView textViewPhone= null;
    Button btcomments = null;


    class GetRestaurantTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetRestaurantTask(String uri) {
            this.uri = uri;

        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonSting = null;
            try {
                jsonSting = WhereClient.getInstance().getRestaurant(uri);
            } catch (WhereClientException e) {
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonSting;
        }

        @Override
        protected void onPostExecute(String jsonRestaurant) {
            Log.d(TAG, jsonRestaurant);
            Restaurant restaurant = (new Gson()).fromJson(jsonRestaurant, Restaurant.class);


            name=restaurant.getName();
            description=restaurant.getDescription();
            likes= restaurant.getLikes();
            slikes = String.valueOf(likes);
            address= restaurant.getAddress();
            phone = restaurant.getPhone();

            textViewName.setText(name);
            textViewDescription.setText(description);
            textViewLikes.setText(slikes);
            textViewaddress.setText(address);
            textViewPhone.setText(phone);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        uri = (String) getIntent().getExtras().get("uri");


        textViewName= (TextView) findViewById(R.id.textViewName);
        textViewDescription= (TextView) findViewById(R.id.textViewDescription);
        textViewLikes= (TextView) findViewById(R.id.textViewLikes);;
        textViewaddress= (TextView) findViewById(R.id.textViewAddress);
        textViewPhone= (TextView) findViewById(R.id.textViewPhone);


        // Execute AsyncTask
        mGetRestaurantTask = new GetRestaurantTask(uri);
        mGetRestaurantTask.execute((Void) null);

        btcomments = (Button) findViewById(R.id.Comments);
        // set list OnItemClick listener
        /*btcomments.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailActivity.this, RestaurantCommentsActivity.class);
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });*/

    }

}

