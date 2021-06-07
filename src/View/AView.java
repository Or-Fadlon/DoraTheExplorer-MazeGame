package View;

import ViewModel.MyViewModel;
import java.util.Observable;
import java.util.Observer;


public abstract class AView implements IView, Observer {
    protected MyViewModel myViewModel;

    @Override
    public void update(Observable o, Object arg) {
    }
}

