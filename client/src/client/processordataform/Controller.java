package client.processordataform;

import models.Processor;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller {
    private client.dataform.Controller.OnOKButtonClickListener onOKButtonClickListener;
    private Processor processor = null;

    public void setOnOKButtonClickListener(client.dataform.Controller.OnOKButtonClickListener listener) {
        onOKButtonClickListener = listener;
    }

    public Processor getProcessor() {
        return processor;
    }
}
