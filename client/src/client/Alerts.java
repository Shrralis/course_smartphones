package client;

import javafx.scene.control.Alert;

/**
 * Created by shrralis on 2/27/17.
 */
public class Alerts {
    public static void showBadResultAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Помилка");
        alert.setHeaderText("Калічна відповідь");
        alert.setContentText("Прийшло щось доволі неприйнятне... Я не буду працювати з цим!");
        alert.showAndWait();
    }

    public static void showBadInputStreamAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Помилка");
        alert.setHeaderText("Неможливо отримати результат");
        alert.setContentText("Вхідне з'єднання з сервером інвалідне!");
        alert.showAndWait();
    }

    public static void showBadOutputStreamAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Помилка");
        alert.setHeaderText("Неможливо відправити запит");
        alert.setContentText("Вихідне з'єднання з сервером інвалідне!");
        alert.showAndWait();
    }

    public static void showSomeExceptionAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Помилка");
        alert.setHeaderText("Якась хрінь... ЕКСЕПШОН!!1");
        alert.setContentText("Ви не запускались через консоль?? Що ж... Це означає, що віддебажити це вже не можна");

        if (e != null) {
            e.printStackTrace();
        }
        alert.showAndWait();
    }
}
