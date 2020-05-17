package net.hesterberg.budget.ui;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.transaction.Purchase;
import net.hesterberg.budget.transaction.Transaction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

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

    //-----------The lists that store data in the JScrollList and associated Model-----------//
    //TODO: Update the comments on these to make them more accurate
    JList budgetList;
    JScrollPane budgetScrollPane;
    DefaultListModel<Transaction> budgetListModel;
    JList purchaseList;
    JScrollPane purchaseScrollPane;
    DefaultListModel<Transaction> purchaseListModel;

    public BudgetGUI() {
        super();

        // Set up general GUI info
        setSize(1500, 600);
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

        //--------------------- Adds the main budget and purchase panels that contain the JScrollPanes----------------//
        JPanel budgetPanel = new BudgetList();
        JPanel purchasePanel = new PurchaseList();
        left.add(budgetPanel);
        right.add(purchasePanel);

        //--------------------- Adds the bottom left button and stats panel -------------------//
        JPanel statsPanel = new StatsPanel();
        JPanel budgetButtonPanel = new BudgetButtonPanel();
        left.add(budgetButtonPanel);
        left.add(statsPanel);

        //--------------------- Adds the bottom right button panel ------------------------------//
        JPanel purchaseButtonPanel = new PurchaseButtonPanel();
        right.add(purchaseButtonPanel);

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


        public BudgetList() {
            budgetListModel = new DefaultListModel<>();
            budgetListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 1", 5000, "Category 1", false));
            budgetListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 2", 5000, "Category 1", false));
            budgetListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 3", 5000, "Category 1", false));

            budgetList = new JList(budgetListModel);
            budgetScrollPane = new JScrollPane(budgetList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            budgetScrollPane.setPreferredSize(new Dimension(700, 400));
            add(budgetScrollPane);
        }
    }

    public class PurchaseList extends JPanel {


        public PurchaseList() {
            purchaseListModel = new DefaultListModel<>();
            purchaseListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 1", 5000, "Category 1", false));
            purchaseListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 2", 5000, "Category 1", false));
            purchaseListModel.addElement(new Purchase(new Date(15, 5, 2015), "Purchase 3", 5000, "Category 1", false));

            purchaseList = new JList(purchaseListModel);
            purchaseScrollPane = new JScrollPane(purchaseList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            purchaseScrollPane.setPreferredSize(new Dimension(700, 400));
            add(purchaseScrollPane);
        }
    }

    public class BudgetButtonPanel extends JPanel {
        public BudgetButtonPanel () {
            setupPanel();
        }

        private void setupPanel() {
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
                    JOptionPaneBudgetInputPanel budgetInput = new JOptionPaneBudgetInputPanel();
                    int result = JOptionPane.showConfirmDialog(null, budgetInput, "Please enter " +
                            "purchase details", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        System.out.println("Description: " + budgetInput.getDescription());
                        System.out.println("Category: " + budgetInput.category.getText());
                    }
                }
            });

            add(deleteBudgetBtn);
            add(updateBudgetBtn);
            add(addBudgetBtn);
        }
    }

    public class StatsPanel extends JPanel {
        public StatsPanel() {
            setupPanel();
        }

        private void setupPanel() {
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

    public class JOptionPaneBudgetInputPanel extends JPanel {
        private JTextField dateDay;
        private JTextField dateMonth;
        private JTextField dateYear;
        private JTextField description;
        private JTextField cost;
        private JTextField category;

        public JOptionPaneBudgetInputPanel() {
            setupPanel();
        }

        private void setupPanel() {
            //TODO: Initialize these values with current date, etc
            //TODO: Make a new constructor that takes parameters when editing a purchase
            dateDay = new JTextField();
            dateMonth = new JTextField();
            dateYear = new JTextField();
            description = new JTextField();
            cost = new JTextField();
            category = new JTextField();

            JPanel budgetInputPanel = new JPanel();
            budgetInputPanel.setLayout(new GridLayout(4, 2));

            //Builds a date panel to hold the date components
            JPanel datePanel = new JPanel();
            datePanel.setLayout(new GridLayout(1, 3));
            datePanel.add(dateDay);
            datePanel.add(dateMonth);
            datePanel.add(dateYear);

            //Adds all the inputs to the budget input panel
            budgetInputPanel.add(new Label("Date (DD/MM/YYYY)"));
            budgetInputPanel.add(datePanel);
            budgetInputPanel.add(new Label("Description"));
            budgetInputPanel.add(description);
            budgetInputPanel.add(new Label("Cost ($)"));
            budgetInputPanel.add(cost);
            budgetInputPanel.add(new Label("Category"));
            budgetInputPanel.add(category);
            add(budgetInputPanel);
        }

        public JTextField getDateDay() {
            return dateDay;
        }

        public JTextField getDateMonth() {
            return dateMonth;
        }

        public JTextField getDateYear() {
            return dateYear;
        }

        public JTextField getDescription() {
            return description;
        }

        public JTextField getCost() {
            return cost;
        }

        public JTextField getCategory() {
            return category;
        }
    }

    public class PurchaseButtonPanel extends JPanel {
        public PurchaseButtonPanel () {
            setupPanel();
        }

        private void setupPanel() {
            setLayout(new GridLayout(1, 3));
            JButton deletePurchaseBtn = new JButton("Delete Purchase");
            JButton updatePurchaseBtn = new JButton("Update Purchase");
            JButton addPurchaseBtn = new JButton("Add Purchase");

            deletePurchaseBtn.setActionCommand("DeleteBudget");
            updatePurchaseBtn.setActionCommand("UpdateBudget");
            addPurchaseBtn.setActionCommand("AddBudget");

            //-------------------------Add action listeners for each button press ------------------------------------//
            deletePurchaseBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Deletes the purchase");
                }
            });

            updatePurchaseBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    //TODO: Build the JOptionPanel so that it contains the existing purchase that is edited
                    //TODO: Delete the modified purchase from the list
                    JOptionPaneBudgetInputPanel budgetInput = new JOptionPaneBudgetInputPanel();
                    int result = JOptionPane.showConfirmDialog(null, budgetInput, "Please enter " +
                            "purchase details", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        System.out.println("Description: " + budgetInput.getDescription());
                        System.out.println("Category: " + budgetInput.category.getText());
                    }
                }
            });

            addPurchaseBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JOptionPaneBudgetInputPanel budgetInput = new JOptionPaneBudgetInputPanel();
                    int result = JOptionPane.showConfirmDialog(null, budgetInput, "Please enter " +
                            "purchase details", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        purchaseListModel.addElement(new Purchase(new Date(Integer.getInteger(
                                budgetInput.getDateDay().getText()), Integer.getInteger(budgetInput.getDateMonth().getText()),
                                Integer.getInteger(budgetInput.getDateYear().getText())), budgetInput.getDescription().getText(),
                                Integer.getInteger(budgetInput.getCost().getText()), budgetInput.getCategory().getText(), false));
                    }
                }
            });

            add(deletePurchaseBtn);
            add(updatePurchaseBtn);
            add(addPurchaseBtn);
        }
    }
}
