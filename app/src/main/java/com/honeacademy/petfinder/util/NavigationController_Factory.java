package com.honeacademy.petfinder.util;

import com.honeacademy.petfinder.activity.MainActivity;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NavigationController_Factory implements Factory<NavigationController> {
  private final Provider<MainActivity> mainActivityProvider;

  public NavigationController_Factory(Provider<MainActivity> mainActivityProvider) {
    assert mainActivityProvider != null;
    this.mainActivityProvider = mainActivityProvider;
  }

  @Override
  public NavigationController get() {
    return new NavigationController(mainActivityProvider.get());
  }

  public static Factory<NavigationController> create(Provider<MainActivity> mainActivityProvider) {
    return new NavigationController_Factory(mainActivityProvider);
  }
}
