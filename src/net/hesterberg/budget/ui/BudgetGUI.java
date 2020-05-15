package net.hesterberg.budget.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;

/**
 * GUI Class that uses Swing UI library to build a basic user interface
 *
 * @author Nikolai Hesterberg
 */
public class BudgetGUI extends JFrame implements ActionListener {
    /** Title for top of GUI. */
    private static final String APP_TITLE = "Budget";
    /** Text for the File Menu. */
    private static final String FILE_MENU_TITLE = "File";
    /** Text for the New Issue XML menu item. */
    private static final String NEW_FILE_TITLE = "New";
    /** Text for the Load Issue XML menu item. */
    private static final String LOAD_FILE_TITLE = "Load";
    /** Text for the Save menu item. */
    private static final String SAVE_FILE_TITLE = "Save";
    /** Text for the Quit menu item. */
    private static final String QUIT_TITLE = "Quit";
    /** Menu bar for the GUI that contains Menus. */
    private JMenuBar menuBar;
    /** Menu for the GUI. */
    private JMenu menu;
    /** Menu item for creating a new list of Races. */
    private JMenuItem itemNewFile;
    /** Menu item for loading a file. */
    private JMenuItem itemLoadFile;
    /** Menu item for saving the list to a file. */
    private JMenuItem itemSaveFile;
    /** Menu item for quitting the program. */
    private JMenuItem itemQuit;
    /** Stores the container */
    Container c;
    /** Stores the left JPanel */
    JPanel left;
    /** Stores the right JPanel */
    JPanel right;
    /** Top left JPanel */
    JPanel topLeft;
    /** Bottom left JPanel */
    JPanel bottomLeft;
    /** Top right JPanel */
    JPanel topRight;
    /** Bottom right JPanel */
    JPanel bottomRight;
    /** Races Jpanel */
    JPanel racesPanel;
    /** Race Selector Panel */
    JPanel raceSelector;
    /** Budget list Panel */
    JPanel budgetList;
    /** budgetDetails panel */
    JPanel budgetDetails;
    /** Filter results panel */
    JPanel filterResults;
    /** Purchase list panel */
    JPanel purchaseList;
    /** Race backend array */
    ArrayList backendBudgetArray = new ArrayList();;
    /** Race default list model */
    DefaultListModel budgetModel = new DefaultListModel();
    /** Race Jlist */
    JList jListBudget = new JList(budgetModel);
    /** Races list box */
    JScrollPane budgetScrollList;
    /** Add race button */
    JButton btnAddBudget = new JButton("Add Budget");
    /** Remove race button */
    JButton btnRemoveBudget = new JButton("Remove Budget");
    /** Unselect race button */
    JButton btnUnselectBudget = new JButton("Unselect Budget");
    /** Border for JPanels */
    Border blackline = BorderFactory.createLineBorder(Color.black);
    /** Button Filter */
    JButton btnFilter = new JButton("Filter");
    /** Results backend array */
    ArrayList backendPurchasesArray = new ArrayList();;
    /** Results default list model */
    DefaultListModel purchasesModel = new DefaultListModel();
    /** Results Jlist */
    JList jListPurchases = new JList(purchasesModel);
    /** Table for jListResults */
    JTable tablePurchases;
    /** Results list box */
    JScrollPane purchasesScrollList;
    /** Add result button */
    JButton btnAddPurchase = new JButton("Add Purchase");
    /** Results table model class instance */
    //private ResultsTableModel resultsTableModel;
    /** Used to check what item is selected in results list */
    ListSelectionModel selectionModel;
    /** Data stored in the table */
    private Object [][] data;

    public BudgetGUI() {
        super();

        // Set up general GUI info
        setSize(1500, 500);
        setLocation(50, 50);
        setTitle(APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUpMenuBar();

        initializeGUI();

        // Set the GUI visible
        setVisible(true);
    }

    /**
     * Initializes GUI
     */
    private void initializeGUI()
    {
        // We encourage creating inner classes for the major components of the GUI
        c = this.getContentPane();
        //WolfResultsManager instance = WolfResultsManager.getInstance();

        left = new JPanel();
        right = new JPanel();
        c.setLayout(new GridLayout(1, 2));
        c.add(left);
        c.add(right);

        left.setLayout(new GridLayout(2, 1));
        right.setLayout(new GridLayout(2, 1));

        // add the top/bottom left panels
        topLeft = new JPanel();
        bottomLeft = new JPanel();
        left.add(topLeft);
        left.add(bottomLeft);

        // add the top/bottom right panels
        topRight = new JPanel();
        bottomRight = new JPanel();
        right.add(topRight);
        right.add(bottomRight);

        // Create and add the Races jpanel
        topLeft.setBorder(blackline);
        topLeft.setLayout(new GridLayout(2, 1));
        racesPanel = new JPanel();
        racesPanel.setLayout(new GridLayout(1, 2));
        topLeft.add(racesPanel);
        budgetDetails = new JPanel();
        topLeft.add(budgetDetails);

        raceSelector = new JPanel();
        racesPanel.add(raceSelector);
        budgetList = new JPanel();
        racesPanel.add(budgetList);

        // Add buttons to Race selector panel
        raceSelector.setLayout(new GridLayout(4, 1));
        raceSelector.add(btnAddBudget);
        raceSelector.add(btnRemoveBudget);
        raceSelector.add(btnUnselectBudget);

        /**
        // Add the race list
        fillRaceModel();
        raceScrollList = new JScrollPane(jListRace, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        raceScrollList.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        raceList.add(raceScrollList);

        // add the results box and add result button
        topRight.setLayout(new GridLayout(2, 1));

        //Setup results table model
        resultsTableModel = new ResultsTableModel();
        tableResults = new JTable(resultsTableModel);
        tableResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableResults.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tableResults.setFillsViewportHeight(true);

        resultsScrollList = new JScrollPane(tableResults, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        topRight.add(resultsScrollList);
        topRight.add(btnAddResult);

        // Add the runner name, age, and time labels + text fields
        bottomRight.setLayout(new GridLayout(3, 2));
        bottomRight.add(lblResultName);
        bottomRight.add(txtResultName);
        bottomRight.add(lblResultAge);
        bottomRight.add(txtResultAge);
        bottomRight.add(lblResultTime);
        bottomRight.add(txtResultTime);

        //add action listeners
        btnAddRace.addActionListener(this);
        btnRemoveRace.addActionListener(this);
        btnEditRace.addActionListener(this);
        btnUnselectRace.addActionListener(this);
        btnFilter.addActionListener(this);
        btnAddResult.addActionListener(this);

        //This has an anonymous inner class to handle selecting items in the race scroll list
        //When selected, the results table will switch to the correct results list
        //When selected, the race details will be displayed
        jListRace.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && jListRace.getSelectedIndex() > -1){
                    resultsTableModel.updateData();
                    resultsTableModel.fireTableDataChanged();
                    txtRaceDetailsName.setText(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getName());
                    txtRaceDetailsDistance.setText(Double.toString(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getDistance()));
                    txtRaceDetailsDate.setText(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getDate().toString());
                    txtRaceDetailsLocation.setText(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getLocation());
                }
            }
        });

        selectionModel = tableResults.getSelectionModel();

        //When a result is selected from the scroll list, the details are displayed in the text boxes below
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if(!event.getValueIsAdjusting() && tableResults.getSelectedRow() > -1 && tableResults.getSelectedRow() < instance.getRaceList().getRace(jListRace.getSelectedIndex()).getResults().size())
                {
                    txtResultName.setText(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getResults().getResult(tableResults.getSelectedRow()).getName());
                    txtResultAge.setText(Integer.toString(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getResults().getResult(tableResults.getSelectedRow()).getAge()));
                    txtResultTime.setText(instance.getRaceList().getRace(jListRace.getSelectedIndex()).getResults().getResult(tableResults.getSelectedRow()).getTime().toString());
                }
            }
        });
         */
    }

    /**
     * Makes the GUI Menu bar that contains options for loading a file containing
     * issues or for quitting the application.
     */
    private void setUpMenuBar()
    {
        // Construct Menu items
        menuBar = new JMenuBar();
        menu = new JMenu(FILE_MENU_TITLE);
        itemNewFile = new JMenuItem(NEW_FILE_TITLE);
        itemLoadFile = new JMenuItem(LOAD_FILE_TITLE);
        itemSaveFile = new JMenuItem(SAVE_FILE_TITLE);
        itemQuit = new JMenuItem(QUIT_TITLE);
        itemNewFile.addActionListener(this);
        itemLoadFile.addActionListener(this);
        itemSaveFile.addActionListener(this);
        itemQuit.addActionListener(this);

        // Start with save button disabled
        itemSaveFile.setEnabled(false);

        // Build Menu and add to GUI
        menu.add(itemNewFile);
        menu.add(itemLoadFile);
        menu.add(itemSaveFile);
        menu.add(itemQuit);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    /**
     * Starts the application
     *
     * @param args command line args
     */
    public static void main(String[] args)
    {
        new BudgetGUI();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
