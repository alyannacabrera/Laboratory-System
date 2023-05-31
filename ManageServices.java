import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ManageServices extends ManageLab
{
    public static void ManageService(ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        char choice;
        do
        {
            System.out.println(" ");
            System.out.println("Manage Services");
            System.out.println("[1] Add New Service");
            System.out.println("[2] Search Service");
            System.out.println("[3] Delete Service");
            System.out.println("[4] Edit Service");
            System.out.println("[X] Return Main Menu");
            System.out.println();
            System.out.print("Select a transaction: ");
            choice= sc.next().charAt(0);

            if (choice!='1'&&choice!='2'&&choice!='3'&&choice!='4'&&choice!='X')
                System.out.println("Input is not in the choices. Please enter valid input.");
        }while (choice!='1'&&choice!='2'&&choice!='3'&&choice!='4'&&choice!='X');
        sc.nextLine();
        switch (choice)
        {
            case '1' -> AddService(ServiceList);
            case '2' -> SearchService(ServiceList);
            case '3' -> DeleteService(ServiceList);
            case '4' -> EditService(ServiceList);
        }
    }

    public static void sortAscending(ArrayList<Services> ServiceList)
    {
        for (int p = 0; p < ServiceList.size(); p++)
            ServiceList.sort(Comparator.comparing(Services::getServiceCode));
    }

    public static void AddService(ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        String threeCharServCode;
        String servDes;
        int price;
        char again = 'Y';
        boolean unique = true;

        do
        {
            do {
                System.out.println("Enter the Unique 3-character Service Code: ");
                threeCharServCode = sc.nextLine();

                //check if may existing na serv code na
                for (Services services : ServiceList) {
                    if (services.getServiceCode().equals(threeCharServCode) && services.getDelete()!='D') {
                        unique = false;
                        break;
                    }
                }

                if (!unique || threeCharServCode.length() != 3){
                    System.out.println("Invalid Input (not Unique 3-character Service Code). Please enter valid input (Unique 3-character Service Code).");
                    System.out.println();
                }
            } while (!unique || threeCharServCode.length() != 3);


            System.out.println("Enter the Service Description: ");
            servDes = sc.nextLine();
            System.out.println("Enter the Service Price: ");
            price = sc.nextInt();
            sc.nextLine();

            char SaveRecord;

            do
            {
                System.out.println("Save Service Record[Y/N]? ");
                SaveRecord = sc.next().charAt(0);
                if (SaveRecord != 'Y' && SaveRecord != 'N'){
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            }while(SaveRecord != 'Y' && SaveRecord != 'N');

            if (SaveRecord=='Y')
            {
                ServiceList.add(new Services(threeCharServCode, servDes, price));
                System.out.printf("%-15s %-25s %-15s %n", ServiceList.get(ServiceList.size()-1).getServiceCode(), ServiceList.get(ServiceList.size()-1).getServiceDescription(), ServiceList.get(ServiceList.size()-1).getServicePrice());

                int s = ServiceList.size()-1;

                File file = new File("Services.txt");

                try
                {
                    boolean value = file.createNewFile();
                    if (value)
                    {
                        System.out.println("The file (Services.txt) is created.");

                        FileWriter output = new FileWriter("Services.txt");

                        output.write(ServiceList.get(s).getServiceCode()+";"+ ServiceList.get(s).getServiceDescription()+";"+ ServiceList.get(s).getServicePrice() + ";\n");
                        System.out.println("Service has been added to file(Services.txt).");

                        output.close();
                    }

                    else
                    {
                        System.out.println("The file already exists.");

                        try (FileWriter f = new FileWriter("Services.txt", true);
                             BufferedWriter b = new BufferedWriter(f);
                             PrintWriter p = new PrintWriter(b))
                        {
                            p.println(ServiceList.get(s).getServiceCode()+";"+ ServiceList.get(s).getServiceDescription()+";"+ ServiceList.get(s).getServicePrice()+";");
                            System.out.println("Service has been appended to file (Services.txt).");
                        }
                        catch(Exception e) {
                            e.getStackTrace();
                        }
                    }

                }
                catch(Exception e) {
                    e.getStackTrace();
                }
                do {
                    System.out.println("Do you want to add another service? [Y/N]");
                    again = sc.next().charAt(0);

                    if (again != 'Y' && again != 'N')
                    {
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                }while(again != 'Y' && again != 'N');
                sc.nextLine();
            }
        }while(again == 'Y');
    }

    public static void SearchService(ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        char again = 'Y';
        char ans;
        do
        {
            do
            {
                System.out.println();
                System.out.println("[1] Service Code");
                System.out.println("[2] Keyword in Service Description");
                System.out.println("How would you like to search: ");
                ans = sc.next().charAt(0);
                if (ans != '1' && ans != '2'){
                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                    System.out.println();
                }
            } while (ans != '1' && ans != '2');
            sc.nextLine();

            if (ans == '1') //using service code to search
            {
                String toSearchCode;
                System.out.println("Enter Service Code: ");
                toSearchCode = sc.nextLine();
                int found = 0;

                for (Services services : ServiceList) {
                    if (toSearchCode.equals(services.getServiceCode()) && services.getDelete() != 'D') {
                        System.out.printf("%-15s %-25s %-15s %n", "Service Code", "Service Description", "Service Price");
                        System.out.printf("%-15s %-25s %-15s %n", services.getServiceCode(), services.getServiceDescription(), services.getServicePrice());
                        System.out.println();
                        found++;
                    }
                }
                if (found != 0)
                {
                    do {
                        System.out.println("Do you want to search again? [Y/N]");
                        again = sc.next().charAt(0);
                        if (again!='Y' && again!='N')
                        {
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again!='Y' && again!='N');
                    sc.nextLine();
                }
                if (found == 0)
                {
                    do
                    {
                        System.out.println("The Service Code ("+toSearchCode+") was not found");
                        System.out.println("No record found.");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N')
                        {
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }
            }

            else //use keyword to search
            {
                String toSearchKeyword;
                System.out.println("Enter Keyword: ");
                toSearchKeyword = sc.nextLine();
                int foundKeyword = 0;
                int headerCTR = 0;

                sortAscending(ServiceList);

                for (Services services : ServiceList) {
                    if (services.getServiceDescription().contains(toSearchKeyword) && services.getDelete() != 'D') {
                        if (headerCTR == 0) {
                            System.out.printf("%-15s %-25s %-15s %n", "Service Code", "Service Description", "Service Price");
                            headerCTR = 1;
                        }
                        System.out.printf("%-15s %-25s %-15s %n", services.getServiceCode(), services.getServiceDescription(), services.getServicePrice());
                        foundKeyword++;
                    }
                }
                if (foundKeyword != 0)
                {
                    do {
                        System.out.println("Do you want to search again? [Y/N]");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(again != 'Y' && again != 'N');
                    sc.nextLine();
                }
                if (foundKeyword == 0)
                {
                    do
                    {
                        System.out.println("The Keyword ("+toSearchKeyword+") was not found");
                        System.out.println("No record found.");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }
            }
        }while(again == 'Y');
    }


    public static void DeleteService(ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        char again = 'Y';
        char deleteAns;
        String deleteReason;
        do
        {
            char ans;
            do
            {
                System.out.println();
                System.out.println("[1] Service Code");
                System.out.println("[2] Keyword in Service Description");
                System.out.println("How would you like to delete [1/2]: ");
                ans = sc.next().charAt(0);
                if (ans != '1' && ans != '2'){
                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                    System.out.println();
                }
            } while (ans != '1' && ans != '2');
            sc.nextLine();

            if (ans == '1')
            {
                String toSearchCode;
                System.out.println("Enter Service Code: ");
                toSearchCode = sc.nextLine();
                int found = 0;
                int tmp = 0;
                for (int n = 0; n < ServiceList.size(); n++)
                {
                    if (toSearchCode.equals(ServiceList.get(n).getServiceCode()))
                    {
                        System.out.printf("%-15s %-25s %-15s %n", "Service Code", "Service Description", "Service Price");
                        System.out.printf("%-15s %-25s %-15s %n", ServiceList.get(n).getServiceCode(), ServiceList.get(n).getServiceDescription(), ServiceList.get(n).getServicePrice());
                        System.out.println();
                        found++;
                        tmp = n;
                    }
                }

                if (found > 0) // found using service code
                {
                    do {
                        System.out.println("Do you want to delete? [Y/N]");
                        deleteAns = sc.next().charAt(0);
                        if (deleteAns!= 'Y' && deleteAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(deleteAns!= 'Y' && deleteAns != 'N');
                    sc.nextLine();

                    if (deleteAns == 'Y')
                    {
                        System.out.println("Reason for Deletion: ");
                        deleteReason = sc.nextLine();

                        ServiceList.get(tmp).setDelete('D');
                        ServiceList.get(tmp).setReason(deleteReason);

                        System.out.println(ServiceList.get(tmp).getServiceCode() + ";" + ServiceList.get(tmp).getServiceDescription() + ";" + ServiceList.get(tmp).getServicePrice()+";"+ServiceList.get(tmp).getDelete()+";"+ServiceList.get(tmp).getReason());
                        System.out.println("The file (Services.txt) will be overwritten.");

                        File file = new File("Services.txt");
                        boolean value = false;
                        FileWriter output = null;
                        try
                        {
                            value = file.createNewFile();
                            output = new FileWriter("Services.txt");
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        if (!value) //file exist
                        {
                            try (FileWriter f = new FileWriter("Services.txt", true);
                                 BufferedWriter b = new BufferedWriter(f);
                                 PrintWriter p = new PrintWriter(b))
                            {

                                for (Services services : ServiceList) {

                                    if (services.getDelete() == 'D')
                                        p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");

                                    else
                                        p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");

                                    if (output != null) {
                                        output.close();
                                    }
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println("The service has been deleted.");
                            do {
                                System.out.println("Do you want to delete again? [Y/N]");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            } while (again != 'Y' && again != 'N');
                            sc.nextLine();
                        }
                    }
                }

                else
                {
                    do
                    {
                        System.out.println("The Service Code ("+toSearchCode+") was not found");
                        System.out.println("No record found.");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();

                }
            }

            else
            {
                String toSearchKeyword;
                System.out.println("Enter Keyword: ");
                toSearchKeyword = sc.nextLine();
                int foundKeyword = 0;
                int headerCTR = 0;
                int tmpKey = 0;
                for (int n = 0; n < ServiceList.size(); n++) {
                    if (ServiceList.get(n).getServiceDescription().contains(toSearchKeyword) && ServiceList.get(n).getDelete()!='D')
                    {
                        if(headerCTR == 0)
                        {
                            System.out.printf("%-15s %-25s %-15s %n","Service Code", "Service Description", "Service Price");
                            headerCTR = 1;
                        }
                        System.out.printf("%-15s %-25s %-15s %n", ServiceList.get(n).getServiceCode(), ServiceList.get(n).getServiceDescription(), ServiceList.get(n).getServicePrice());
                        foundKeyword++;
                        tmpKey=n;
                    }
                }

                if (foundKeyword == 0) // did not find service using keyword
                {
                    do {
                        System.out.println("Keyword not found.");
                        System.out.println("Do you want to delete services again? [Y/N]");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }
                if (foundKeyword == 1)//found service using keyword
                {
                    do {
                        System.out.println("Do you want to delete? [Y/N]");
                        deleteAns = sc.next().charAt(0);
                        if (deleteAns != 'Y' && deleteAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (deleteAns != 'Y' && deleteAns != 'N');
                    sc.nextLine();

                    if (deleteAns == 'Y')
                    {
                        System.out.println("Reason for Deletion: ");
                        deleteReason = sc.nextLine();
                        System.out.println(ServiceList.get(tmpKey).getServiceCode() + ";" + ServiceList.get(tmpKey).getServiceDescription() + ";" + ServiceList.get(tmpKey).getServicePrice()+";D;"+deleteReason);
                        System.out.println("The file (Services.txt) will be overwritten.");

                        ServiceList.get(tmpKey).setDelete('D');
                        ServiceList.get(tmpKey).setReason(deleteReason);

                        File file = new File("Services.txt");
                        boolean value = false;
                        FileWriter output = null;
                        try {
                            value = file.createNewFile();
                            output = new FileWriter("Services.txt");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (!value) //file exist
                        {
                            for (Services services : ServiceList) {
                                try (FileWriter f = new FileWriter("Services.txt", true);
                                     BufferedWriter b = new BufferedWriter(f);
                                     PrintWriter p = new PrintWriter(b)) {
                                    if (services.getDelete() == 'D')
                                        p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");

                                    else
                                        p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");

                                    if (output != null) {
                                        output.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("The service has been deleted.");
                            do {
                                System.out.println("Do you want to delete services again? [Y/N]");
                                again = sc.next().charAt(0);
                                if(again!='Y' && again!='N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while(again!='Y' && again!='N');
                            sc.nextLine();
                        }
                    }
                }


                if (foundKeyword > 1)
                {

                    System.out.println("Enter the service code you want to delete: ");
                    String manyChooseOne = sc.nextLine();
                    boolean foundTwoTry = false;
                    int TMPmult = 0;

                    for (int o = 0; o < ServiceList.size(); o++)
                    {
                        if (manyChooseOne.equals(ServiceList.get(o).getServiceCode()))
                        {
                            foundTwoTry = true;
                            TMPmult = o;
                        }
                    }

                    if (foundTwoTry)
                    {
                        System.out.println(manyChooseOne + " is equal to " + ServiceList.get(TMPmult).getServiceCode());

                        do {
                            System.out.println("Do you want to delete? [Y/N]");
                            deleteAns = sc.next().charAt(0);
                            if(deleteAns!='Y' && deleteAns!='N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (deleteAns!='Y' && deleteAns!='N');
                        sc.nextLine();
                        if (deleteAns == 'Y')
                        {
                            System.out.println("Reason for Deletion: ");
                            deleteReason = sc.nextLine();
                            System.out.println(ServiceList.get(tmpKey).getServiceCode() + ";" + ServiceList.get(tmpKey).getServiceDescription() + ";" + ServiceList.get(tmpKey).getServicePrice()+";D;"+deleteReason+";"+"\n");

                            ServiceList.get(TMPmult).setDelete('D');
                            ServiceList.get(TMPmult).setReason(deleteReason);

                            File file = new File("Services.txt");
                            boolean value = false;
                            FileWriter output = null;
                            try {
                                value = file.createNewFile();
                                output = new FileWriter("Services.txt");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (!value) //file exist
                            {
                                try (FileWriter f = new FileWriter("Services.txt", true);
                                     BufferedWriter b = new BufferedWriter(f);
                                     PrintWriter p = new PrintWriter(b))
                                {

                                    for (Services services : ServiceList) {

                                        if (services.getDelete() == 'D')
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");
                                        else
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");
                                        if (output != null) {
                                            output.close();
                                        }
                                    }
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("The service has been deleted.");
                            do {
                                System.out.println("Do you want to delete services again? [Y/N]");
                                again = sc.next().charAt(0);
                                if(again!='Y' && again!='N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while (again!='Y' && again!='N');
                            sc.nextLine();
                        }
                    }

                    else
                    {
                        do
                        {
                            System.out.println("The Service Code ("+manyChooseOne+") was not found");
                            System.out.println("Do you want to try again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }
                }
            }
        }while(again == 'Y');
    }

    public static void EditService(ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        char deleteAns;
        String deleteReason;
        int foundKeyword = 0;
        int found = 0;
        char choiceEdit; // approval to edit or not
        char editAgain = 'Y';
        do
        {
            do
            {
                System.out.println("The services cannot be edited. If you would like to edit an existing service, the service will first be deleted,");
                System.out.println("and new service will be created. Would you like to proceed? [Y/N]");
                choiceEdit = sc.next().charAt(0);
                if(choiceEdit != 'Y' && choiceEdit != 'N'){
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            }while (choiceEdit != 'Y' && choiceEdit != 'N');
            sc.nextLine();

            if (choiceEdit == 'N')
            {
                editAgain = 'N';
            }
            if (choiceEdit == 'Y')
            {
                char ans;
                do
                {
                    System.out.println();
                    System.out.println("[1] Service Code");
                    System.out.println("[2] Keyword in Service Description");
                    System.out.println("How would you like to delete [1/2]: ");
                    ans = sc.next().charAt(0);
                    if(ans != '1' && ans != '2'){
                        System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                        System.out.println();
                    }
                } while (ans != '1' && ans != '2');
                sc.nextLine();

                if (ans == '1') // use service code to find
                {
                    String toSearchCode;
                    System.out.println("Enter Service Code: ");
                    toSearchCode = sc.nextLine();

                    int tmp = 0;
                    for (int n = 0; n < ServiceList.size(); n++)
                    {
                        if (toSearchCode.equals(ServiceList.get(n).getServiceCode()))
                        {
                            System.out.printf("%-15s %-25s %-15s %n", "Service Code", "Service Description", "Service Price");
                            System.out.printf("%-15s %-25s %-15s %n", ServiceList.get(n).getServiceCode(), ServiceList.get(n).getServiceDescription(), ServiceList.get(n).getServicePrice());
                            System.out.println();
                            found++;
                            tmp = n;
                        }
                    }

                    if (found > 0) // found using Service code
                    {
                        do {
                            System.out.println("Do you want to delete? [Y/N]");
                            deleteAns = sc.next().charAt(0);
                            if(deleteAns!='Y' && deleteAns!='N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while(deleteAns!='Y' && deleteAns!='N');
                        sc.nextLine();
                        if (deleteAns == 'Y') //will start process
                        {
                            System.out.println("Reason for Deletion: ");
                            deleteReason = sc.nextLine();

                            ServiceList.get(tmp).setDelete('D');
                            ServiceList.get(tmp).setReason(deleteReason);

                            System.out.println(ServiceList.get(tmp).getServiceCode() + ";" + ServiceList.get(tmp).getServiceDescription() + ";" + ServiceList.get(tmp).getServicePrice()+";"+ServiceList.get(tmp).getDelete()+";"+ServiceList.get(tmp).getReason()+";\n");
                            System.out.println("Services.txt was overwritten\n");
                            File file = new File("Services.txt");
                            boolean value = false;
                            FileWriter output = null;
                            try {
                                value = file.createNewFile();
                                output = new FileWriter("Services.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (!value) //file exist
                            {
                                for (Services services : ServiceList) {
                                    try (FileWriter f = new FileWriter("Services.txt", true);
                                         BufferedWriter b = new BufferedWriter(f);
                                         PrintWriter p = new PrintWriter(b)) {
                                        if (services.getDelete() == 'D')
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");
                                        else
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");
                                        if (output != null) {
                                            output.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            String threeCharServCode;
                            String servDes;
                            int price;
                            System.out.println("Add Another Service\n");
                            System.out.println("Enter the Unique 3-character Service Code: ");
                            threeCharServCode = sc.nextLine();

                            System.out.println("Enter the Service Description: ");
                            servDes = sc.nextLine();

                            System.out.println("Enter the Service Price: ");
                            price = sc.nextInt();

                            if(threeCharServCode.length()==3) //no error
                            {
                                char SaveRecord;
                                do
                                {
                                    System.out.println("Save Service [Y/N]? ");
                                    SaveRecord = sc.next().charAt(0);
                                    if(SaveRecord != 'Y' && SaveRecord != 'N'){
                                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                        System.out.println();
                                    }
                                }while(SaveRecord != 'Y' && SaveRecord != 'N');

                                if (SaveRecord=='Y') // add new service after deleting old one
                                {
                                    ServiceList.add(new Services(threeCharServCode, servDes, price));

                                    int s = ServiceList.size()-1;
                                    try (FileWriter f = new FileWriter("Services.txt", true);
                                         BufferedWriter b = new BufferedWriter(f);
                                         PrintWriter p = new PrintWriter(b))
                                    {
                                        System.out.println(ServiceList.get(s).getServiceCode()+ ", " +ServiceList.get(s).getServiceDescription()+ ", "+ServiceList.get(s).getServicePrice()+" has been added.");
                                        p.println(ServiceList.get(s).getServiceCode()+";"+ ServiceList.get(s).getServiceDescription()+";"+ ServiceList.get(s).getServicePrice()+";");
                                        System.out.println("Service has been added to file(Services.txt).");
                                    }
                                    catch (Exception e)
                                    {
                                        e.getStackTrace();
                                    }
                                }
                            }

                            else // error message
                                System.out.println("Error in input\n");
                        }
                    }

                    if (found == 0) // not found using Service code
                    {
                        do
                        {
                            System.out.println("The Service Code ("+toSearchCode+") was not found");
                            System.out.println("No record found.");
                            System.out.println("Do you want to try again? (Y/N)");
                            editAgain = sc.next().charAt(0);
                            if(editAgain != 'Y' && editAgain != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (editAgain != 'Y' && editAgain != 'N');
                        sc.nextLine();
                    }
                }

                else // using keyword
                {
                    String toSearchKeyword;
                    System.out.println("Enter Keyword: ");
                    toSearchKeyword = sc.nextLine();

                    int headerCTR = 0;
                    int tmpKey = 0;
                    int multTMP=0;

                    for (int n = 0; n < ServiceList.size(); n++)
                    {
                        if (ServiceList.get(n).getServiceDescription().contains(toSearchKeyword) && ServiceList.get(n).getDelete()!='D')
                        {
                            if(headerCTR == 0)
                            {
                                System.out.printf("%-15s %-25s %-15s %n", "Service Code", "Service Description", "Service Price");
                                headerCTR = 1;
                            }
                            System.out.printf("%-15s %-25s %-15s %n", ServiceList.get(n).getServiceCode(), ServiceList.get(n).getServiceDescription(), ServiceList.get(n).getServicePrice());
                            foundKeyword++;
                            tmpKey=n;
                        }
                    }

                    if (foundKeyword == 1)
                    {
                        do {
                            System.out.println("Do you want to delete? [Y/N]");
                            deleteAns = sc.next().charAt(0);
                            if(deleteAns!='Y'&&deleteAns!='N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (deleteAns!='Y'&&deleteAns!='N');
                        sc.nextLine();
                        if (deleteAns == 'Y')
                        {
                            System.out.println("Reason for Deletion: ");
                            deleteReason = sc.nextLine();
                            System.out.println(ServiceList.get(tmpKey).getServiceCode() + ";" + ServiceList.get(tmpKey).getServiceDescription() + ";" + ServiceList.get(tmpKey).getServicePrice()+";D;"+deleteReason);

                            ServiceList.get(tmpKey).setDelete('D');
                            ServiceList.get(tmpKey).setReason(deleteReason);

                            File file = new File("Services.txt");
                            boolean value = false;
                            FileWriter output = null;
                            try {
                                value = file.createNewFile();
                                output = new FileWriter("Services.txt");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (!value) //file exist
                            {
                                for (Services services : ServiceList) {
                                    try (FileWriter f = new FileWriter("Services.txt", true);
                                         BufferedWriter b = new BufferedWriter(f);
                                         PrintWriter p = new PrintWriter(b)) {
                                        if (services.getDelete() == 'D')
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");
                                        else
                                            p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");
                                        if (output != null) {
                                            output.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            String threeCharServCode;
                            String servDes;
                            int price;

                            System.out.println("Enter the Unique 3-character Service Code: ");
                            threeCharServCode = sc.nextLine();

                            System.out.println("Enter the Service Description: ");
                            servDes = sc.nextLine();

                            System.out.println("Enter the Service Price: ");
                            price = sc.nextInt();
                            sc.nextLine();

                            if(threeCharServCode.length()==3) //no error
                            {
                                char SaveRecord;
                                do
                                {
                                    System.out.println("Save Service [Y/N]? ");
                                    SaveRecord = sc.next().charAt(0);
                                    if(SaveRecord != 'Y' && SaveRecord != 'N'){
                                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                        System.out.println();
                                    }
                                }while(SaveRecord != 'Y' && SaveRecord != 'N');

                                if (SaveRecord=='Y') // add new service after deleting old one
                                {
                                    ServiceList.add(new Services(threeCharServCode, servDes, price));

                                    int s = ServiceList.size()-1;
                                    try (FileWriter f = new FileWriter("Services.txt", true);
                                         BufferedWriter b = new BufferedWriter(f);
                                         PrintWriter p = new PrintWriter(b))
                                    {
                                        System.out.println(ServiceList.get(s).getServiceCode()+ ", " +ServiceList.get(s).getServiceDescription()+ ", "+ServiceList.get(s).getServicePrice()+" has been added.");
                                        p.println(ServiceList.get(s).getServiceCode()+";"+ ServiceList.get(s).getServiceDescription()+";"+ ServiceList.get(s).getServicePrice()+";");
                                        System.out.println("Service has been edited and added to file (Services.txt).");
                                    }
                                    catch (Exception e)
                                    {
                                        e.getStackTrace();
                                    }
                                }
                                else
                                    System.out.println("Error in input\n");
                            }
                        }
                    }

                    if (foundKeyword > 1)
                    {
                        System.out.println("Enter the service code you want to delete: ");
                        String manyChooseOne = sc.nextLine();
                        int foundTwoTry = 0;
                        for (int x= 0; x < ServiceList.size(); x++) {
                            if (manyChooseOne.equals(ServiceList.get(x).getServiceCode()) && ServiceList.get(x).getDelete()!='D') {
                                foundTwoTry = 1;
                                multTMP = x;
                                break;
                            }
                        }

                        if (foundTwoTry == 1)
                        {
                            do {
                                System.out.println("Do you want to delete? [Y/N]");
                                deleteAns = sc.next().charAt(0);
                                if(deleteAns != 'Y' && deleteAns != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            } while (deleteAns != 'Y' && deleteAns != 'N');
                            sc.nextLine();

                            if (deleteAns == 'Y')
                            {
                                System.out.println("Reason for Deletion: ");
                                deleteReason = sc.nextLine();

                                ServiceList.get(multTMP).setDelete('D');
                                ServiceList.get(multTMP).setReason(deleteReason);

                                System.out.println(ServiceList.get(multTMP).getServiceCode() + ";" + ServiceList.get(multTMP).getServiceDescription() + ";" + ServiceList.get(multTMP).getServicePrice()+";"+ ServiceList.get(multTMP).getDelete()+";"+ ServiceList.get(multTMP).getReason()+";\n");

                                File file = new File("Services.txt");
                                boolean value = false;
                                FileWriter output = null;
                                try {
                                    value = file.createNewFile();
                                    output = new FileWriter("Services.txt");

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (!value) //file exist
                                {
                                    for (Services services : ServiceList) {
                                        try (FileWriter f = new FileWriter("Services.txt", true);
                                             BufferedWriter b = new BufferedWriter(f);
                                             PrintWriter p = new PrintWriter(b)) {
                                            if (services.getDelete() == 'D')
                                                p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";" + services.getDelete() + ";" + services.getReason() + ";");
                                            else
                                                p.println(services.getServiceCode() + ";" + services.getServiceDescription() + ";" + services.getServicePrice() + ";");
                                            if (output != null) {
                                                output.close();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                String threeCharServCode;
                                String servDes;
                                int price;

                                System.out.println("Enter the Unique 3-character Service Code: ");
                                threeCharServCode = sc.nextLine();

                                System.out.println("Enter the Service Description: ");
                                servDes = sc.nextLine();

                                System.out.println("Enter the Service Price: ");
                                price = sc.nextInt();

                                if(threeCharServCode.length()==3)
                                {
                                    char SaveRecord;
                                    do
                                    {
                                        System.out.println("Save Service [Y/N]? ");
                                        SaveRecord = sc.next().charAt(0);
                                        if(SaveRecord != 'Y' && SaveRecord != 'N'){
                                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                            System.out.println();
                                        }
                                    }while(SaveRecord != 'Y' && SaveRecord != 'N');
                                    sc.nextLine();

                                    if (SaveRecord=='Y')
                                    {
                                        ServiceList.add(new Services(threeCharServCode, servDes, price));
                                        int s = ServiceList.size()-1;
                                        try (FileWriter f = new FileWriter("Services.txt", true);
                                             BufferedWriter b = new BufferedWriter(f);
                                             PrintWriter p = new PrintWriter(b))
                                        {
                                            System.out.println(ServiceList.get(s).getServiceCode()+ ", " +ServiceList.get(s).getServiceDescription()+ ", "+ServiceList.get(s).getServicePrice()+" has been added.");
                                            p.println(ServiceList.get(s).getServiceCode()+";"+ ServiceList.get(s).getServiceDescription()+";"+ ServiceList.get(s).getServicePrice()+";");
                                            System.out.println("Service has been edited and added to file (Services.txt).");
                                        }
                                        catch (Exception e)
                                        {
                                            e.getStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        if (foundTwoTry == 0)
                        {
                            do
                            {
                                System.out.println("The Service Code ("+manyChooseOne+") was not found");
                                System.out.println("No record found.");
                                System.out.println("Do you want to try again? (Y/N)");
                                editAgain = sc.next().charAt(0);
                                if(editAgain != 'Y' && editAgain != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while (editAgain != 'Y' && editAgain != 'N');
                            sc.nextLine();
                        }
                    }

                    if (foundKeyword == 0)
                    {
                        System.out.println("The Keyword ("+toSearchKeyword+") was not found");
                        System.out.println("No record found.");
                        do
                        {
                            System.out.println("Do you want to try again? (Y/N)");
                            editAgain = sc.next().charAt(0);
                            if(editAgain != 'Y' && editAgain != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (editAgain != 'Y' && editAgain != 'N');
                        sc.nextLine();
                    }
                }
                if(foundKeyword >=1 || found >=1) {
                    do {
                        System.out.println("Do you want to edit service again? (Y/N)");
                        editAgain = sc.next().charAt(0);
                        if(editAgain != 'Y' && editAgain != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (editAgain != 'Y' && editAgain != 'N');
                    sc.nextLine();
                }
            }
        }while (editAgain == 'Y');
    }
}
