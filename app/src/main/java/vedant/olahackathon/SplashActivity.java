package vedant.olahackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import vedant.olahackathon.Model.Booking;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new GCMAcitivityRegister(this);

        Intent intent = new Intent(this, LauncherActivity.class);
        String bookingData = "{\"error\":0,\"id\":890679,\"pickup\":{\"latitude\":13.204628,\"longitude\":77.707702},\"passengers\":[{\"id\":1,\"name\":\"Chetan\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.959823,\"longitude\":77.648615}},{\"id\":1,\"name\":\"Vedant\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.959279,\"longitude\":77.660974}},{\"id\":1,\"name\":\"Rajesh\",\"mobile\":\"+91-1234567890\",\"address\":\"\",\"geo\":{\"latitude\":12.9625,\"longitude\":77.628187}}]}";
        intent.putExtra(Booking.TAG, bookingData);
        startActivity(intent);
    }
}
