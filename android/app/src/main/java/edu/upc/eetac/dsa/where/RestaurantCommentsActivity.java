package edu.upc.eetac.dsa.where;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.upc.eetac.dsa.where.Client.Entity.Comment;
import edu.upc.eetac.dsa.where.Client.Entity.CommentsCollection;
import edu.upc.eetac.dsa.where.Client.WhereClient;
import edu.upc.eetac.dsa.where.Client.WhereClientException;

/*public class RestaurantCommentsActivity extends AppCompatActivity {

    private final static String TAG = RestaurantCommentsActivity.class.toString();
    private GetCommentsTask mGetCommentsTask = null;
    private CommentsCollection restaurants = new CommentsCollection();
    //private CommentsCollectionAdapter adapter = null;



    class GetCommentsTask extends AsyncTask<Void, Void, String> {
        private String uri;

        public GetCommentsTask(String uri) {
            this.uri = uri;
        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonRestaurantCollection = null;
            try{
                jsonRestaurantCollection = WhereClient.getInstance().getComments(uri);
                Log.d("CAROL", jsonRestaurantCollection);
            }catch(WhereClientException e){
                // TODO: Handle gracefully
                Log.d(TAG, e.getMessage());
            }
            return jsonRestaurantCollection;
        }

        @Override
        protected void onPostExecute(String commentsCollect) {
            JSONObject jo = null;
            JSONArray comments = null;
            try {
                jo = new JSONObject(commentsCollect);
                comments = jo.getJSONArray("restaurants");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("CAROL LINK", comments.toString());
            CommentsCollection commentsCollection = (new Gson()).fromJson(commentsCollect , CommentsCollection.class);

            for(Comment comments : commentsCollection.getComments()){
                comments.getComment().add(comments.getComment().size(), comments);
            }
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_comments);


        // Execute AsyncTask
        mGetCommentsTask = new GetCommentsTask(null);
        mGetCommentsTask.execute((Void) null);

        // set list adapter
        ListView list = (ListView)findViewById(R.id.list);
        adapter = new CommentsCollectionAdapter(this, restaurants);
        list.setAdapter(adapter);





    }


}*/
