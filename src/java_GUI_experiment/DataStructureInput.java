package java_GUI_experiment;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;

//import org.eclipse.wb.swt.SWTResourceManager;
import java.io.File;  							// Import the File class
import java.io.FileWriter;   					// Import the FileWriter class
import java.io.IOException;  					// Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent; 						// Import the Scanner class to read text files

public class DataStructureInput extends Composite {
	private Label lbl_Title;
	private Label lbl_statusAlert;
	private Label lbl_fileNameInput;
	private Label lbl_textInput;
	private Label lbl_fileNameOutput;
	private Label lbl_textOutput;
	private Text txt_fileNameInput;
	private Text txt_input;
	private Text txt_fileNameOutput;
	private Text txt_output;
	private Button btn_submit;
	private Button btn_read;
	private Button btn_delete;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DataStructureInput(Composite parent, int style) {
		super(parent, style);
		
		lbl_Title = new Label(this, SWT.BORDER);
		lbl_Title.setBounds(10, 10, 140, 19);
		lbl_Title.setText("File read/ write manager");
		
		lbl_statusAlert = new Label(this, SWT.NONE);
		lbl_statusAlert.setBounds(224, 10, 102, 19);
		lbl_statusAlert.setText("Status alert: ");
		
		lbl_fileNameInput = new Label(this, SWT.BORDER);
		lbl_fileNameInput.setBounds(10, 35, 208, 19);
		lbl_fileNameInput.setText("Please specify a filename to write to.");
		
		lbl_textInput = new Label(this, SWT.BORDER);
		lbl_textInput.setBounds(10, 85, 208, 19);
		lbl_textInput.setText("Please type some text to save.");
		
		lbl_fileNameOutput = new Label(this, SWT.BORDER);
		lbl_fileNameOutput.setBounds(224, 35, 208, 19);
		lbl_fileNameOutput.setText("Please specify a filename to read from.");
		
		lbl_textOutput = new Label(this, SWT.BORDER);
		lbl_textOutput.setBounds(224, 85, 208, 19);
		lbl_textOutput.setText("File content posted below.");
		
		txt_fileNameInput = new Text(this, SWT.BORDER);
		txt_fileNameInput.setBounds(10, 60, 208, 19);
		
		txt_input = new Text(this, SWT.BORDER);
		txt_input.setBounds(10, 110, 208, 82);
		
		txt_fileNameOutput = new Text(this, SWT.BORDER);
		txt_fileNameOutput.setBounds(224, 60, 208, 19);
		
		txt_output = new Text(this, SWT.BORDER);
		txt_output.setBounds(224, 110, 208, 82);

		btn_submit = new Button(this, SWT.BORDER);
		btn_submit.setBounds(10, 198, 208, 23);
		btn_submit.setText("Submit");
//		btn_submit.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btn_submit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createFile(txt_fileNameInput.getText());
				writeFile(txt_fileNameInput.getText(), txt_input.getText());
			}
		});
		
		btn_read = new Button(this, SWT.BORDER);
		btn_read.setBounds(224, 198, 208, 23);
		btn_read.setText("Read");
		btn_read.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				readFile(txt_fileNameOutput.getText());
			}
		});
		
		btn_delete = new Button(this, SWT.BORDER);
		btn_delete.setBounds(140, 227, 164, 23);
		btn_delete.setText("Delete file ");
		btn_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteFile(txt_fileNameOutput.getText());
			}
		});
		
		
	}

	public static void main(String... args) {
		
		String fileName = "filename";
		String fileContent = "Files in Java might be tricky, but it is fun enough!";
		
//		createFile(fileName);
//		writeFile(fileName, fileContent);
//		getFileInfo(fileName);
//		readFile(fileName);
		
		Display display = new Display();
//		Shell shell = new Shell(display);
		Shell shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX);
		shell.setLayout(new GridLayout(1, false));
		
		shell.setSize(444, 256);
//		shell.setResizable( false );
		
		DataStructureInput dataStructureInput = new DataStructureInput(shell, SWT.NONE);
		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	  
	public void createFile(String fileName) {
		try {
			File myObj = new File(fileName + ".txt");
			if (myObj.createNewFile()) {
//				System.out.println("File created: " + myObj.getName());
		        lbl_statusAlert.setText("File created: " + myObj.getName());
			} else {
//				System.out.println("File already exists.");
				lbl_statusAlert.setText("File already exists.");
			}
		} catch (IOException e) {
//			System.out.println("An error occurred.");
			lbl_statusAlert.setText("Error!");
			e.printStackTrace();
		}
	}
	
	public void getFileInfo(String fileName) {
		File myObj = new File(fileName + ".txt");
		if (myObj.exists()) {
			System.out.println("File name: " + myObj.getName());
			System.out.println("Absolute path: " + myObj.getAbsolutePath());
			System.out.println("Writeable: " + myObj.canWrite());
			System.out.println("Readable " + myObj.canRead());
			System.out.println("File size in bytes " + myObj.length());
		} else {
//	        System.out.println("The file does not exist.");
			lbl_statusAlert.setText("The file does not exist.");
		}
	}
	
	public void readFile(String fileName) {
		try {
			File myObj = new File(fileName + ".txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
//		        System.out.println(data);
				txt_output.setText(data);
				lbl_statusAlert.setText("Successfully read from the file.");
			}
			myReader.close();
		} catch (FileNotFoundException e) {
//		      System.out.println("An error occurred.");
			lbl_statusAlert.setText("Error!");
			e.printStackTrace();
		}
	}
	
	public void writeFile(String fileName, String fileContent) {
		try {
			FileWriter myWriter = new FileWriter(fileName + ".txt");
			myWriter.write(fileContent);
			myWriter.close();
//			System.out.println("Successfully wrote to the file.");
			lbl_statusAlert.setText("Successfully wrote to the file.");
		} catch (IOException e) {
//			System.out.println("An error occurred.");
			lbl_statusAlert.setText("Error!");
			e.printStackTrace();
		}
	}
	
	public void deleteFile(String fileName) {
		File myObj = new File(fileName + ".txt"); 
		if (myObj.delete()) { 
//			System.out.println("Deleted the file: " + myObj.getName());
			lbl_statusAlert.setText("Deleted the file: " + myObj.getName());
		} else {
//			System.out.println("Failed to delete the file.");
			lbl_statusAlert.setText("Failed!");
		} 
	}
}
