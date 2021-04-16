package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.cleancode.clickcounter.R;
import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.clicks.ClicksActivity;

public class CounterActivity
    extends AppCompatActivity implements CounterContract.View {

  public static String TAG = CounterActivity.class.getSimpleName();

  private CounterContract.Presenter presenter;
  private TextView tvCounter;
  private Button btnIncrement, btnReset, btnClicks;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_counter);
    getSupportActionBar().setTitle(R.string.counter_title);

    tvCounter = findViewById(R.id.tvCounter);
    btnIncrement = findViewById(R.id.btnIncrement);
    btnClicks = findViewById(R.id.btnClicks);
    btnReset = findViewById(R.id.btnReset);


    if (savedInstanceState == null) {
      AppMediator.resetInstance();
    }

    // do the setup
    CounterScreen.configure(this);

    if (savedInstanceState == null) {
      presenter.onStart();

    } else {
      presenter.onRestart();
    }
  }

  @Override
  public void finish(){
    finish();
  }


  @Override
  protected void onResume() {
    super.onResume();

    // load the data
    presenter.onResume();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

    presenter.onBackPressed();
  }

  @Override
  protected void onPause() {
    super.onPause();

    presenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroy();
  }


  @Override
  public void navigateToNextScreen() {
    Intent intent = new Intent(this, ClicksActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void onClicksPressed(View view) {
    presenter.onClicksPressed();
  }

  public void onResetPressed(View view) {
    presenter.onResetPressed();
  }

  public void onIncrementPressed(View view) {
    presenter.onIncrementPressed();
  }

  @Override
  public void onDataUpdated(CounterViewModel viewModel) {
    //Log.e(TAG, "onDataUpdated()");

    // deal with the data
    ((TextView) findViewById(R.id.tvCounter)).setText(viewModel.data);
  }

  @Override
  public void injectPresenter(CounterContract.Presenter presenter) {
    this.presenter = presenter;
  }

}
