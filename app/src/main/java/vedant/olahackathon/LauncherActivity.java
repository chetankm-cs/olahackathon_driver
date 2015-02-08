package vedant.olahackathon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import vedant.olahackathon.Model.Booking;
import vedant.olahackathon.Model.Passenger;
import vedant.olahackathon.Model.Position;

public class LauncherActivity extends Activity {
    private Booking mBooking;

    private ListView mListView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        String data = getIntent().getStringExtra(Booking.TAG);
        Log.d("fuck 2", data);
        mBooking = (Booking) POJOToJSON.fromJson(data, Booking.class);
        if(mBooking==null) {
//            String bookingData = "{\"error\":0,\"id\":890679,\"pickup\":{\"latitude\":13.204628,\"longitude\":77.707702},\"passengers\":[{\"id\":1,\"name\":\"Chetan\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.959823,\"longitude\":77.648615}},{\"id\":1,\"name\":\"Vedant\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.959279,\"longitude\":77.660974}},{\"id\":1,\"name\":\"Rajesh\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.9625,\"longitude\":77.628187}}]}";
//            mBooking = (Booking) POJOToJSON.fromJson(bookingData, Booking.class);
            throw new IllegalStateException();
        }
        mListView = (ListView) findViewById(R.id.listView);
        mTextView = (TextView) findViewById(R.id.textView);
        PassengerAdapter adapter = new PassengerAdapter(this);
        adapter.addAll(mBooking.getPassengers());
        mListView.setAdapter(adapter);
        String text = String.format("Welcome Shyam,\nYou need to drop off %d people", mBooking.getPassengers().size());
        mTextView.setText(text);
    }

    public void mapButtonClicked(View v){
        String data = getIntent().getStringExtra(Booking.TAG);
        Intent mapIntent = new Intent(this, DirectionsActivity.class);
        mapIntent.putExtra(Booking.TAG, data);
        startActivity(mapIntent);
    }

    public void directionsButtonClicked(View v) {
        Position destination = mBooking.getPassengers().get(0).getGeo();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination.getLatitude() + "," + destination.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private class PassengerAdapter extends ArrayAdapter<Passenger> {
        private LayoutInflater mInflater;

        /**
         * Constructor
         *
         * @param context  The current context.
         */
        public PassengerAdapter(Context context) {
            super(context, -1);

            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null)
                view = mInflater.inflate(R.layout.list_item, parent, false);

            TextView textView = (TextView) view.findViewById(R.id.textView);
            ImageButton callButton = (ImageButton) view.findViewById(R.id.callButton);

            final Passenger passenger = getItem(position);
            textView.setText(passenger.getName());
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + passenger.getMobile()));
                    startActivity(callIntent);
                }
            });

            return view;
        }
    }
}
