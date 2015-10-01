package jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;


@SuppressWarnings("serial")
public class Note extends JPanel {
	
	public JFrame frame = new JFrame();
	JPopupMenu notePopup = new JPopupMenu();
	JPanel middlePanel = new JPanel();
	JTextArea display = new JTextArea(20,40);
	String noteName = null;
	String data, noteColor = null;
	String framePosX, framePosY = null;
	String frameSizeX, frameSizeY = null;
	int x,y = 0;
	Color displayColor = null;
	Img frameIcon = new Img("icon.png");
	UndoManager undoManager = new UndoManager();
	JMenuItem undo = new JMenuItem("Undo",new ImageIcon(this.getClass().getResource("resources/undo_icon.png")));
	JMenuItem redo = new JMenuItem("Redo",new ImageIcon(this.getClass().getResource("resources/redo_icon.png")));
	
	public Note(final String noteFileName) {

		final File note = new File(Main.userNotesPath+"/data/"+noteFileName);

		createPopupMenu(noteFileName);
		
		
		if (!note.isFile()) {
			createNewNote(noteFileName);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(note));
			try {
				noteName = br.readLine();
				noteColor = br.readLine();
				framePosX = br.readLine();
				framePosY = br.readLine();
				frameSizeX = br.readLine();
				frameSizeY = br.readLine();
				while ((data = br.readLine()) != null) {
					display.append(data+"\n");
				}
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		frame.setTitle(noteName);
		frame.setIconImage(frameIcon.img);
	    middlePanel.setBorder(new TitledBorder(new EtchedBorder(),""));
	    middlePanel.setLayout(new BorderLayout());
	    display.setEditable(true);
	    display.setLineWrap(true);
	    display.setWrapStyleWord(true);
	    display.setBackground(Color.decode(noteColor));
	    JScrollPane scroll = new JScrollPane(display);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    middlePanel.add (scroll,BorderLayout.CENTER);
	    frame.add(middlePanel);
	    frame.pack();
	    // Восстанавливаем положение
	    x = Integer.parseInt(frameSizeX);
	    y = Integer.parseInt(frameSizeY);
	    frame.setSize(x, y);
	    // Восстанавливаем размеры
	    x = Integer.parseInt(framePosX);
	    y = Integer.parseInt(framePosY);
	    frame.setLocation(x, y);
	    frame.setVisible(true);
	    frame.toFront();
	    
	    DocumentListener documentListener = new DocumentListener() {
	    	public void changedUpdate(DocumentEvent documentEvent) {
	    		saveNote(noteFileName);
	    	}
	    	public void insertUpdate(DocumentEvent documentEvent) {
	    		saveNote(noteFileName);
	    	}
	    	public void removeUpdate(DocumentEvent documentEvent) {
	    		saveNote(noteFileName);
	    	}
	    };


	    display.getDocument().addDocumentListener(documentListener);

	    frame.addWindowListener(new WindowAdapter() {
	    	
	    	@Override
            public void windowClosing(WindowEvent e) {
	    		saveNote(noteFileName);
            }
        });
	    
	    display.addMouseListener(new MouseAdapter() {
			 
	        @Override
	        public void mousePressed(MouseEvent e) {
	            showPopup(e);
	        }

	        @Override
	        public void mouseReleased(MouseEvent e) {
	            showPopup(e);
	        }

	        private void showPopup(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	            	notePopup.show(e.getComponent(), e.getX(), e.getY());
	            }
	        }
	    });
	    
	    display.getDocument().addUndoableEditListener(new UndoableEditListener() {
	          public void undoableEditHappened(UndoableEditEvent e) {
	              undoManager.addEdit(e.getEdit());
	              updateButtons();
	          }
	    });
	}
	
	public void saveNote(String noteFileName)  {
		final File noteToSave = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		data=noteName+"\n"+display.getBackground().getRGB()+"\n"+frame.getX()+"\n"+frame.getY()+"\n"+frame.getSize().width+"\n"+frame.getSize().height+"\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(noteToSave);
			fileWriter.write(data);
			fileWriter.append(display.getText());
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createNewNote(String noteFileName) {
		final File newNote = new File(Main.userNotesPath+"/data/"+noteFileName);
		final File settingsFile = new File(Main.userNotesPath+"/jnotes_settings.ini");
		String defaultNoteColor = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingsFile));
			try {
				defaultNoteColor = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		data=noteFileName+"\n"+defaultNoteColor+"\n600\n330\n500\n350\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(newNote);
			fileWriter.write(data);
			fileWriter.append(display.getText());
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		recreatePopupMenu();
	}
	
	public void createPopupMenu(final String noteFile) {
		final File note = new File(Main.userNotesPath+"/data/"+noteFile);
		
		JMenuItem renameNote = new JMenuItem("Rename note",new ImageIcon(this.getClass().getResource("resources/rename_icon.png")));
		renameNote.setMnemonic(KeyEvent.VK_R);
		renameNote.getAccessibleContext().setAccessibleDescription("Rename note");
		renameNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String s = (String)JOptionPane.showInputDialog (
            			null, 
            			"Enter new note name:", 
            			"Rename note", 
            			JOptionPane.PLAIN_MESSAGE,
            			null, 
            			null,
            			noteName);
            	
            	if (s != null) {
            		frame.setTitle(s);
            		noteName = s;
            		saveNote(noteFile);
            		recreatePopupMenu();
            	}
            }
        });
		notePopup.add(renameNote);
		
		notePopup.addSeparator(); //======================================
		
		JMenuItem onTopNote = new JMenuItem("Always on top",new ImageIcon(this.getClass().getResource("resources/always_on_top_icon.png")));
		onTopNote.setMnemonic(KeyEvent.VK_A);
		onTopNote.getAccessibleContext().setAccessibleDescription("Always on top");
		onTopNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Note.this.frame.setAlwaysOnTop(true);
            }
        });
		notePopup.add(onTopNote);
		
		notePopup.addSeparator(); //======================================
		
		JMenuItem setColor = new JMenuItem("Set color",new ImageIcon(this.getClass().getResource("resources/color_icon.png")));
		setColor.setMnemonic(KeyEvent.VK_S);
		setColor.getAccessibleContext().setAccessibleDescription("Set color");
		setColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor = JColorChooser.showDialog(
            			null, 
            			"Pick a Color", 
            			Color.YELLOW);
            	if (selectedColor != null) {
	            	display.setBackground(selectedColor);
	            	saveNote(noteFile);
	        		recreatePopupMenu();
            	}
            }
        });
		notePopup.add(setColor);
		
		notePopup.addSeparator(); //======================================
		
		undo.setMnemonic(KeyEvent.VK_U);
		undo.getAccessibleContext().setAccessibleDescription("Undo");
		undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
                    undoManager.undo();
            	} catch (CannotRedoException cre) {
                    cre.printStackTrace();
            	}
            	updateButtons();
            }
        });
		notePopup.add(undo);
		undo.setEnabled(false);
		
		redo.setMnemonic(KeyEvent.VK_O);
		redo.getAccessibleContext().setAccessibleDescription("Redo");
		redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
                    undoManager.redo();
            	} catch (CannotRedoException cre) {
                    cre.printStackTrace();
            	}
            	updateButtons();
            }
        });
		notePopup.add(redo);
		redo.setEnabled(false);
		
		notePopup.addSeparator(); //======================================
		
		JMenuItem cutToClipboard = new JMenuItem("Cut",new ImageIcon(this.getClass().getResource("resources/cut_icon.png")));
		cutToClipboard.setMnemonic(KeyEvent.VK_T);
		cutToClipboard.getAccessibleContext().setAccessibleDescription("Cut");
		cutToClipboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
            	StringSelection stringSelection = new StringSelection(display.getSelectedText());
            	clipBoard.setContents(stringSelection,null);
            	final int start = display.getSelectionStart();
            	final int end = display.getSelectionEnd();
            	String startText = display.getText().substring(0, start);
            	String endText = display.getText().substring(end, display.getText().length());
            	display.setText(startText + endText);
            }
        });
		notePopup.add(cutToClipboard);
		
		JMenuItem copyToClipboard = new JMenuItem("Copy",new ImageIcon(this.getClass().getResource("resources/copy_icon.png")));
		copyToClipboard.setMnemonic(KeyEvent.VK_C);
		copyToClipboard.getAccessibleContext().setAccessibleDescription("Copy");
		copyToClipboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
            	StringSelection stringSelection = new StringSelection(display.getSelectedText());
            	clipBoard.setContents(stringSelection,null);
            }
        });
		notePopup.add(copyToClipboard);
		
		JMenuItem pasteFromClipboard = new JMenuItem("Paste",new ImageIcon(this.getClass().getResource("resources/paste_icon.png")));
		pasteFromClipboard.setMnemonic(KeyEvent.VK_P);
		pasteFromClipboard.getAccessibleContext().setAccessibleDescription("Paste");
		pasteFromClipboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
            	Transferable clipData = clipBoard.getContents(clipBoard);
                if (clipData != null) {
                	try {
                		if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                			String s = (String)(clipData.getTransferData(DataFlavor.stringFlavor));
                			display.replaceSelection(s);
                		}
                	} catch (UnsupportedFlavorException ufe) {
                		System.err.println("Flavor unsupported: " + ufe);
                	} catch (IOException ioe) {
                		System.err.println("Data not available: " + ioe);
                	}
                }
            }
        });
		notePopup.add(pasteFromClipboard);
		
		notePopup.addSeparator(); //======================================
		
		JMenuItem deleteNote = new JMenuItem("Delete note",new ImageIcon(this.getClass().getResource("resources/delete_icon.png")));
		deleteNote.setMnemonic(KeyEvent.VK_D);
		deleteNote.getAccessibleContext().setAccessibleDescription("Delete note");
		deleteNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int n = JOptionPane.showConfirmDialog(
            		    frame,
            		    "Are you sure want to delete note: "+noteName+" ?",
            		    "WARNING!!!",
            		    JOptionPane.YES_NO_OPTION);
            	
            	if (n == 0) {
            		note.delete();
            		frame.setVisible(false);
            		recreatePopupMenu();
            	}
            }
        });
		notePopup.add(deleteNote);
	}
	
	public void updateButtons() {
		undo.setEnabled(undoManager.canUndo());
		redo.setEnabled(undoManager.canRedo());
	}
	
	public void recreatePopupMenu() {
		Tray.popup.removeAll();
		Tray.firstPartOfMenu();
		Tray.dynamicMenu();
		Tray.secondPartOfMenu();
	}
}
