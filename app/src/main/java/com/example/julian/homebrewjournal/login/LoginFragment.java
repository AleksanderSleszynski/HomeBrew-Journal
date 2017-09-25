package com.example.julian.homebrewjournal.login;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julian.homebrewjournal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract {

  private LoginPresenter presenter;

  public LoginFragment() {
    // Required empty public constructor
  }

//  public void setPresenter(LoginContract. presenter);

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // It helps View/Presenter/Service survive orientation change
    this.setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if(presenter == null){
      presenter =  new LoginPresenter();
    }

//    presenter.subscribe();

  }
}
