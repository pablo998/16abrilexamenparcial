package es.ulpgc.eite.cleancode.clickcounter.counter;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class CounterPresenter implements CounterContract.Presenter {

  public static String TAG = CounterPresenter.class.getSimpleName();

  private WeakReference<CounterContract.View> view;
  private CounterState state;
  private CounterContract.Model model;

  private int valorClicks = 0;

  private AppMediator mediator;

  public CounterPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCounterState();
  }


  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new CounterState();
    }

    // call the model and update the state
    state.data = model.getStoredData();
    view.get().onDataUpdated(state);

    /*
    // use passed state if is necessary
    PreviousToCounterState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }
    */
  }

  @Override
  public void onRestart() {
    // Log.e(TAG, "onRestart()");

    // update the model if is necessary
    valorClicks = Integer.parseInt(state.Clicks);
    model.onRestartScreen(state.data);
    state.data = model.getStoredData();
    view.get().onDataUpdated(state);
  }

  @Override
  public void onResume() {
    // Log.e(TAG, "onResume()");

    // use passed state if is necessary
    ClicksToCounterState savedState = getStateFromNextScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromNextScreen(savedState.data);

      // update the state if is necessary
      state.Clicks = savedState.data;
    }

    // call the model and update the state
    //state.data = model.getStoredData();

    // update the view
    view.get().onDataUpdated(state);

  }


  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClicksPressed() {
    // Log.e(TAG, "onClicksPressed()");

    String valorPasado = String.valueOf(valorClicks);
    CounterToClicksState counterToClicksState = new CounterToClicksState();
    counterToClicksState.data = valorPasado;
    passStateToNextScreen(counterToClicksState);
    view.get().navigateToNextScreen();
  }

  @Override
  public void onResetPressed() {
    // Log.e(TAG, "onResetPressed()");
    state.data = "0";
    model.updateData(state.data);
    view.get().onDataUpdated(state);
  }

  @Override
  public void onIncrementPressed() {
    // Log.e(TAG, "onIncrementPressed()");
    valorClicks++;
    state.Clicks = String.valueOf(valorClicks);
    view.get().enableButtons();

    int dataNumero = Integer.parseInt(state.data);
    dataNumero++;

    if(dataNumero>9){
      dataNumero = 0;
    }

    state.data = String.valueOf(dataNumero);
    model.updateData(state.data);
    view.get().onDataUpdated(state);

  }

  private void passStateToNextScreen(CounterToClicksState state) {
    mediator.setCounterNextScreenState(state);
  }

  private ClicksToCounterState getStateFromNextScreen() {
    return mediator.getCounterNextScreenState();
  }

  @Override
  public void injectView(WeakReference<CounterContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CounterContract.Model model) {
    this.model = model;
  }

}
