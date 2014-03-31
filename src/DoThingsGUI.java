import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.events.KeyEvent;


public class DoThingsGUI extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text UserInput;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DoThingsGUI(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(51, 51, 51));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(null);
		
		UserInput = new Text(this, SWT.BORDER);
		UserInput.setBounds(54, 59, 675, 50);
		UserInput.setFont(SWTResourceManager.getFont("Pluto Sans ExtraLight", 14, SWT.NORMAL));
		UserInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		String input = UserInput.getText();
		DoThings.run(input);		
		toolkit.adapt(UserInput, true, true);
		
		TextViewer textViewer = new TextViewer(this, SWT.BORDER);
		StyledText ShowFeedback = textViewer.getTextWidget();
		ShowFeedback.setBounds(54, 130, 675, 143);
		ShowFeedback.setFont(SWTResourceManager.getFont("Pluto Sans ExtraLight", 15, SWT.NORMAL));
		toolkit.paintBordersFor(ShowFeedback);
	}

	public static void main(String[] args){
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    DoThingsGUI dt = new DoThingsGUI(shell, SWT.NONE);
	    dt.pack();
	    shell.pack();
	    shell.open();
	    while(!shell.isDisposed()){
	        if(!display.readAndDispatch()) display.sleep();
	    }
	}
}
