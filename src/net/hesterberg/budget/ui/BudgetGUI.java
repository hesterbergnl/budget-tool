package net.hesterberg.budget.ui;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.transaction.Transaction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Flow;

import javax.swing.*;
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
    /** Stores the container */
    Container c;
    /** Stores the left JPanel */
    JPanel left;
    /** Stores the right JPanel */
    JPanel right;
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
        c = this.getContentPane();

        left = new JPanel();
        right = new JPanel();
        c.setLayout(new GridLayout(1, 2));
        c.add(left);
        c.add(right);
        left.setLayout(new FlowLayout());
        right.setLayout(new FlowLayout());

        left.setBorder(blackline);
        right.setBorder(blackline);

        JPanel budgetPanel = new BudgetList();
        JPanel purchasePanel = new PurchaseList();
        left.add(budgetPanel);
        right.add(purchasePanel);

        //---------------------  Sets up the Bottom row of Budget statistics below the budget pane ----------------//
        JPanel statsPanel = new StatsPanel();

        left.add(new ButtonPanel());
        left.add(statsPanel);
    }

    /**
     * Makes the GUI Menu bar that contains options for loading a file containing
     * issues or for quitting the application.
     */
    private void setUpMenuBar()
    {
        /** Menu bar for the GUI that contains Menus. */
        JMenuBar menuBar;
        /** Menu for the GUI. */
        JMenu menu;
        /** Menu item for creating a new list of Races. */
        JMenuItem itemNewFile;
        /** Menu item for loading a file. */
        JMenuItem itemLoadFile;
        /** Menu item for saving the list to a file. */
        JMenuItem itemSaveFile;
        /** Menu item for quitting the program. */
        JMenuItem itemQuit;

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

    public class BudgetList extends JPanel {
        JList budgetList;
        JScrollPane budgetScrollPane;
        DefaultListModel<Transaction> list;

        public BudgetList() {
            list = new DefaultListModel<>();
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 1", 5000, "Category 1", false));
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 2", 5000, "Category 1", false));
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 3", 5000, "Category 1", false));

            budgetList = new JList(list);
            budgetScrollPane = new JScrollPane(budgetList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            budgetScrollPane.setPreferredSize(new Dimension(700, 400));
            add(budgetScrollPane);
        }
    }

    public class PurchaseList extends JPanel {
        JList purchaseList;
        JScrollPane purchaseScrollPane;
        DefaultListModel<Transaction> list;

        public PurchaseList() {
            list = new DefaultListModel<>();
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 1", 5000, "Category 1", false));
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 2", 5000, "Category 1", false));
            list.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 3", 5000, "Category 1", false));

            purchaseList = new JList(list);
            purchaseScrollPane = new JScrollPane(purchaseList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            purchaseScrollPane.setPreferredSize(new Dimension(700, 400));
            add(purchaseScrollPane);
        }
    }

    public class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new GridLayout(1, 3));
            JButton deleteBudgetBtn = new JButton("Delete Category");
            JButton updateBudgetBtn = new JButton("Update Budget");
            JButton addBudgetBtn = new JButton("Add Category");

            deleteBudgetBtn.setActionCommand("DeleteBudget");
            updateBudgetBtn.setActionCommand("UpdateBudget");
            addBudgetBtn.setActionCommand("AddBudget");

            deleteBudgetBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Clicked!");
                }
            });

            add(deleteBudgetBtn);
            add(updateBudgetBtn);
            add(addBudgetBtn);
        }
    }

    public class StatsPanel extends JPanel {
        public StatsPanel() {
            setLayout(new GridLayout(1,3));
            JPanel overallBudget = new JPanel();
            JPanel overallSpent = new JPanel();
            JPanel overallRemaining = new JPanel();
            overallBudget.setLayout(new GridLayout(1, 2));
            overallSpent.setLayout(new GridLayout(1, 2));
            overallRemaining.setLayout(new GridLayout(1, 2));

            JLabel budgetLabel = new JLabel("Budget:");
            JLabel budgetAmt = new JLabel("$3000");
            overallBudget.add(budgetLabel);
            overallBudget.add(budgetAmt);

            JLabel spentLabel = new JLabel("Spent:");
            JLabel spentAmt = new JLabel("$1000");
            overallSpent.add(spentLabel);
            overallSpent.add(spentAmt);

            JLabel remainingLabel = new JLabel("Remaining: ");
            JLabel remainingAmt = new JLabel("$2000");
            overallRemaining.add(remainingLabel);
            overallRemaining.add(remainingAmt);

            add(overallBudget);
            add(overallSpent);
            add(overallRemaining);
        }
    }
}
