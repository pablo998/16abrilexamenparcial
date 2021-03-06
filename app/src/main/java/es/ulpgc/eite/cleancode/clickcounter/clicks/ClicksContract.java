package es.ulpgc.eite.cleancode.clickcounter.clicks;

import java.lang.ref.WeakReference;


public interface ClicksContract {

  interface View {
    void injectPresenter(Presenter presenter);
    void onDataUpdated(ClicksViewModel viewModel);
    void finish();
    void disableButton();
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void onResume();
    void onStart();
    void onRestart();
    void onBackPressed();
    void onPause();
    void onDestroy();
    void onClearPressed();
  }

  interface Model {
    String getStoredData();
    void updateData(String data);
    void onDataFromNextScreen(String data);
    void onRestartScreen(String data);
    void onDataFromPreviousScreen(String data);
  }
}
