package com.example.colmapsrxjavatask4.view.mapsView;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapView extends MvpView {

        @StateStrategyType(AddToEndSingleStrategy.class)
        void showButStatus(boolean butStatus);
        @StateStrategyType(AddToEndSingleStrategy.class)
        void showTimeResult(String[] timeResult);
        @StateStrategyType(AddToEndSingleStrategy.class)
        void showPbStatus(Boolean[] pbStatus);

}
