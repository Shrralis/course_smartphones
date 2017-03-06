package client.manufacturerdataform;

import models.Manufacturer;

/**
 * Created by shrralis on 3/2/17.
 */
public class Controller {
    private client.dataform.Controller.OnOKButtonClickListener onOKButtonClickListener;
    private Manufacturer manufacturer = null;

    public void setOnOKButtonClickListener(client.dataform.Controller.OnOKButtonClickListener listener) {
        onOKButtonClickListener = listener;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }
}
