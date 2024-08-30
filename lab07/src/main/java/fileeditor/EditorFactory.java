package fileeditor;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public interface EditorFactory {
    JFrame setWindowSize();
    JMenuBar createMenuBar(ActionListener listener);
    JToolBar createToolBar(ActionListener listener);
    JDesktopPane createDesktopPane();
    ActionListener createActionListener(JDesktopPane pane);
    WindowAdapter createWindowAdapter(JDesktopPane pane);
    JMenuBar createEditMenu(ActionListener listener);
}
