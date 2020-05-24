package net.hesterberg.budget.utility;

import net.hesterberg.budget.Date;
import net.hesterberg.budget.budget.Budget;
import net.hesterberg.budget.transaction.Purchase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BudgetFileIO {
    public static Budget LoadBudgetFile(String filename) throws IllegalArgumentException {
        Budget budget;
        String lineIn;
        int totalBudget;
        int bucketBudget;
        int day;
        int month;
        int year;
        String desc;
        int cost;
        String category;

        File fin = new File(filename);
        Scanner fileReader;
        Scanner lineReader;

        try{
            fileReader = new Scanner(fin);
        } catch (FileNotFoundException fnfe) {
            throw new IllegalArgumentException("File doesn't exist");
        }

        totalBudget = fileReader.nextInt();
        budget = new Budget(totalBudget);
        lineIn = fileReader.nextLine();

        while(true) {
            lineIn = fileReader.nextLine();

            if(lineIn.equals("-")) {
                break;
            }
            lineReader = new Scanner(lineIn);
            lineReader.useDelimiter(",");

            category = lineReader.next();
            bucketBudget = lineReader.nextInt();

            budget.addBudgetBucket(category, bucketBudget);
        }

        while(fileReader.hasNextLine()) {
            lineIn = fileReader.nextLine();
            lineReader = new Scanner(lineIn);
            lineReader.useDelimiter(",");

            day = lineReader.nextInt();
            month = lineReader.nextInt();
            year = lineReader.nextInt();
            desc = lineReader.next();
            cost = lineReader.nextInt();
            category = lineReader.next();

            //adds the purchase
            //if category doesn't exist, it'll be created
            try {
                budget.addPurchase(new Purchase(new Date(day, month, year), desc, cost, category, false));
            } catch (CategoryException ce) {
                budget.addBudgetBucket(category, 0);
            }
        }

        return budget;
    }

    public static void SaveBudgetFile(String filename, Budget budget) {

    }
}
