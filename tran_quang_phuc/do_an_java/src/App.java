import com.qlsv.view.LoginFrame;
import com.qlsv.view.UITheme;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        UITheme.install();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
