import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Base class for an OK/Cancel dialog.
 */
public class OkCancelDialog extends JDialog {
    private static final long serialVersionUID = 210407895459206462L;
    private boolean isOkPressed = false;

    /**
     * Creates a dialog with OK and Cancel buttons.
     * @param owner the owner of this dialog
     */
    public OkCancelDialog(JFrame owner) {
        // Create as a modal dialog.
        super(owner, true);
        initComponents();
    }

    /**
     * Sets a panel as the main panel of this dialog.
     * @param panel the panel to be set as the main panel
     */
    public void setMainPanel(JPanel panel) {
        add(panel, BorderLayout.CENTER);
        pack();
    }

    /**
     * Opens a dialog with OK and Cancel buttons.
     *
     * <p>This method returns when the either button is pressed.
     *
     * @return true if the user presses the OK button.
     */
    public boolean open() {
        isOkPressed = false;
        setVisible(true);
        // Returns when setVisible(false) is called.
        return isOkPressed;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(createButtonPanel(), BorderLayout.SOUTH);
        pack();
    }

    private JPanel createButtonPanel() {
        // Create an OK button.
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                isOkPressed = true;
                setVisible(false);
            }
        });

        // Create a Cancel button.
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                isOkPressed = false;
                setVisible(false);
            }
        });

        // Create a panel including the above buttons.
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(okButton);
        panel.add(cancelButton);
        return panel;
    }
}

