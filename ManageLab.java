import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManageLab
{
    public static void ManageLabResults(ArrayList<Patient> PatientList, ArrayList<Services> ServiceList, ArrayList<LabRequest> ReqList)
    {
        Scanner sc = new Scanner(System.in);

        char choice;
        do {
            System.out.println(" ");
            System.out.println("Manage Laboratory Request");
            System.out.println("[1] Add New Laboratory Request");
            System.out.println("[2] Search Laboratory Request");
            System.out.println("[3] Delete Laboratory Request");
            System.out.println("[4] Edit Laboratory Request");
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
            case '1' -> AddLabReq(PatientList, ServiceList, ReqList);
            case '2' -> SearchLabReq(ReqList, ServiceList);
            case '3' -> DeleteLabReq(ReqList, ServiceList);
            case '4' -> EditLabReq(ReqList, ServiceList);
        }
    }

    public static void AddLabReq(ArrayList<Patient> PatientList, ArrayList<Services> ServiceList, ArrayList<LabRequest> ReqList)
    {
        Scanner sc = new Scanner(System.in);
        Format M = new SimpleDateFormat("MM");
        Format Y = new SimpleDateFormat("yyyy");
        Format D = new SimpleDateFormat("dd");
        Format k = new SimpleDateFormat("kk");
        Format m = new SimpleDateFormat("mm");

        char again = 'a';

        do{
            int printWarning = 0;
            int patientFound = 0;
            int codeFound = 0;

            System.out.println("Enter Patient's UID: ");
            String toSearchUID = sc.next();
            System.out.println("Enter Service Code");
            String toSearchCode = sc.next();
            int j = 0;
            int d = 0;

            for (int b = 0; b < PatientList.size(); b++)
            {
                if (PatientList.get(b).getPUI().equals(toSearchUID))
                {
                    patientFound = 1;
                    j = b;
                }
            }

            for (int c = 0; c < ServiceList.size(); c++)
            {
                if (ServiceList.get(c).getServiceCode().equals(toSearchCode))
                {
                    codeFound = 1;
                    d = c;
                }
            }

            if (patientFound == 0)
            {
                System.out.println("Patient's UID not found.");
                do{
                    System.out.println("Do you want to try again? [Y/N]");
                    again = sc.next().charAt(0);
                    if(again != 'Y' && again != 'N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                }while(again != 'Y' && again != 'N');
                sc.nextLine();
                printWarning = 1;
            }

            if (codeFound == 0 && printWarning == 0)
            {
                System.out.println("Service Code not found.");
                do{
                    System.out.println("Do you want to try again? [Y/N]");
                    again = sc.next().charAt(0);
                    if(again != 'Y' && again != 'N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                }while(again != 'Y' && again != 'N');
                sc.nextLine();
            }

            if (patientFound == 1 && codeFound == 1)
            {
                ReqList.add(new LabRequest(GenerateRUI(d, ServiceList,ReqList), PatientList.get(j).getPUI(),
                        Y.format(new Date()) + M.format(new Date()) + D.format(new Date()), (k.format(new Date())+m.format(new Date())), "no result"));

                System.out.println(ReqList.get(ReqList.size()-1).getRequestUID()+";"+ReqList.get(ReqList.size()-1).getPatientUID()+";"+ReqList.get(ReqList.size()-1).getRequestDate()+";"+ReqList.get(ReqList.size()-1).getRequestTime()+";"+ReqList.get(ReqList.size()-1).getResult()+";");

                String serviceCODE = ReqList.get(ReqList.size() - 1).getRequestUID().charAt(0) + Character.toString(ReqList.get(ReqList.size() - 1).getRequestUID().charAt(1)) + ReqList.get(ReqList.size() - 1).getRequestUID().charAt(2);
                System.out.println();


                File file = new File(ReqList.get(ReqList.size()-1).getRequestUID().substring(0,3) + "_Requests.txt");
                try
                {
                    boolean value = file.createNewFile();
                    if (value)
                    {
                            FileWriter output = new FileWriter(ReqList.get(ReqList.size()-1).getRequestUID().substring(0,3) + "_Requests.txt");
                            output.write(ReqList.get(ReqList.size()-1).getRequestUID() + ";" + ReqList.get(ReqList.size()-1).getPatientUID() +";"+ReqList.get(ReqList.size()-1).getRequestDate()+";"+ReqList.get(ReqList.size()-1).getRequestTime()+";"+ReqList.get(ReqList.size()-1).getResult()+";"+"\n");
                            System.out.println("Patient's laboratory request has been added to file ("+ serviceCODE +"_Requests.txt).");
                            output.close();
                    }

                    else {
                        System.out.println("The file already exists.");
                        try (FileWriter f = new FileWriter(serviceCODE + "_Requests.txt", true);
                             BufferedWriter b = new BufferedWriter(f);
                             PrintWriter p = new PrintWriter(b))
                        {
                            p.println(ReqList.get(ReqList.size()-1).getRequestUID() + ";" + ReqList.get(ReqList.size()-1).getPatientUID() +";"+ReqList.get(ReqList.size()-1).getRequestDate()+";"+ReqList.get(ReqList.size()-1).getRequestTime()+";"+ReqList.get(ReqList.size()-1).getResult()+";");
                            System.out.println("Patient's laboratory request has been added to file ("+ serviceCODE +"_Requests.txt).");

                        }catch (Exception e) {
                            e.getStackTrace();}
                    }
                }
                catch(Exception e) {
                    e.getStackTrace();
                }

                do{
                    System.out.println("Do you want to add another Laboratory Request? [Y/N]");
                    again = sc.next().charAt(0);
                    if(again != 'Y' && again != 'N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                }while(again != 'Y' && again != 'N');
                sc.nextLine();
            }
        }while(again == 'Y');
    }

    public static String GenerateRUI(int ctr, ArrayList<Services> ServiceList, ArrayList<LabRequest> ReqList)
    {
        char thousandsPlace, hundredsPlace;
        String Number;
        int tmp = 0;

        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


        Format M = new SimpleDateFormat("MM");
        Format Y = new SimpleDateFormat("yyyy");
        Format D = new SimpleDateFormat("dd");


        System.out.println("Request’s UID;Patient’s UID;Request Date (YYYYMMDD);Request Time (HHMM);Result");

        for (LabRequest labRequest : ReqList)
            if (labRequest.getRequestUID().substring(0, 3).equals(ServiceList.get(ctr).getServiceCode()))
                tmp++;
        if (tmp == 0)
        {
            return ServiceList.get(ctr).getServiceCode() + Y.format(new Date()) + M.format(new Date()) + D.format(new Date()) + Alphabet.charAt(0) + Alphabet.charAt(0) + "00";
        }

        else
        {
            thousandsPlace = Alphabet.charAt(tmp / (26 * 10 * 10));
            tmp = tmp % (26 * 10 * 10);
            hundredsPlace = Alphabet.charAt(tmp / (10 * 10));
            tmp = tmp % (10 * 10);

            if (tmp < 10)
                Number = "0" + tmp;
            else
                Number = String.valueOf(tmp);

            return ServiceList.get(ctr).getServiceCode() + Y.format(new Date()) + M.format(new Date()) + D.format(new Date()) + thousandsPlace + hundredsPlace + Number;
        }
    }

    public static void SearchLabReq(ArrayList<LabRequest> ReqList, ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);

        sortDescending(ReqList);

        char modeSearch;
        char again = 'a';
        System.out.println();

        do{
            do
            {
                System.out.println("[1] Request's UID");
                System.out.println("[2] Patient's UID");
                System.out.println("Which way do you want to search: ");
                modeSearch = sc.next().charAt(0);
                if(modeSearch != '1' && modeSearch != '2'){
                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                    System.out.println();
                }
            }while(modeSearch != '1' && modeSearch != '2');
            sc.nextLine();

            if (modeSearch == '1')
            {
                int printHeader1 = 0;
                int foundCtr = 0;
                String toSearchRID;

                System.out.println("Enter the Request's UID: ");
                toSearchRID = sc.nextLine();

                int serviceIndex = 0;
                for (LabRequest labRequest : ReqList) {
                    if (labRequest.getRequestUID().equals(toSearchRID) && labRequest.getDelete()!='D') {
                        if (printHeader1 == 0) {
                            System.out.println();
                            System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                            printHeader1 = 1;
                        }
                        System.out.printf("%-15s %-25s %-15s %-15s %n", labRequest.getRequestUID(), ServiceList.get(serviceIndex).getServiceDescription(), labRequest.getRequestDate(), labRequest.getResult());
                        foundCtr = 1;
                    }
                }

                if (foundCtr == 0) {
                    System.out.println("No Record Found.");
                }

              do {
                  System.out.println("Do you want to search laboratory request again? (Y/N)");
                  again = sc.next().charAt(0);
                  if (again != 'Y' && again != 'N'){
                      System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                      System.out.println();
                  }
              } while (again != 'Y' && again != 'N');
              sc.nextLine();
            }

            else
            {
                int printHeader1 = 0;
                int foundCtr11 = 0;
                String toSearchPID;

                System.out.println("Enter the Patient's UID: ");
                toSearchPID = sc.nextLine();
                int serviceIndex=0;
                for (LabRequest labRequest : ReqList) {
                    if (labRequest.getPatientUID().equals(toSearchPID) && labRequest.getDelete() != 'D') {
                        for (Services services : ServiceList) {
                            if (services.getServiceCode().equals(labRequest.getRequestUID().substring(0, 3))) {
                                if (printHeader1 == 0) {
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                    printHeader1 = 1;
                                }
                                System.out.printf("%-15s %-25s %-15s %-15s %n", labRequest.getRequestUID(), ServiceList.get(serviceIndex).getServiceDescription(), labRequest.getRequestDate(), labRequest.getResult());
                                foundCtr11++;
                            }
                        }
                    }
                }

                if (foundCtr11 >= 1)
                {
                    do {
                        System.out.println("Do you want to search laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                }

                if (foundCtr11 == 0)
                {
                    System.out.println("No Record Found.");
                    do
                    {
                        System.out.println("Do you want to search laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }
            }
        }while(again == 'Y');
    }


    public static void sortDescending(ArrayList<LabRequest> ReqList)
    {
        for (int p = 0; p < ReqList.size(); p++)
            ReqList.sort((o1, o2) -> (o2.getRequestUID()).compareTo(o1.getRequestUID()));
    }

    public static void DeleteLabReq(ArrayList<LabRequest> ReqList, ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);

        char modeSearch;
        char again = 'a';
        char deleteAns;

        System.out.println();
        do{
            do
            {
                System.out.println("[1] Request's UID");
                System.out.println("[2] Patient's UID");
                System.out.println("Which way do you want to search: ");
                modeSearch = sc.next().charAt(0);
                if (modeSearch != '1' && modeSearch != '2'){
                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                    System.out.println();
                }
            }while(modeSearch != '1' && modeSearch != '2');
            sc.nextLine();

            if (modeSearch == '1')
            {
                boolean foundRIDflagDELETE = false;
                System.out.println("Enter the Request's UID: ");
                String toSearchRID = sc.nextLine();
                int RIDindex = 0;

                for (int f = 0; f < ReqList.size(); f++)
                {
                    if (ReqList.get(f).getRequestUID().equals(toSearchRID) && ReqList.get(f).getDelete()!='D')
                    {
                        for (Services services : ServiceList) {
                            if (services.getServiceCode().equals(ReqList.get(f).getRequestUID().substring(0, 3))) {
                                System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(f).getRequestUID(), services.getServiceDescription(), ReqList.get(f).getRequestDate(), ReqList.get(f).getResult());
                                RIDindex = f;
                                foundRIDflagDELETE = true;
                            }
                        }
                    }
                }

                if (!foundRIDflagDELETE)
                {
                    do {
                        System.out.println("Request's UID not found");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }

                else {
                    do {
                        System.out.println("Do you want to delete? (Y/N)");
                        deleteAns = sc.next().charAt(0);
                        if (deleteAns != 'Y' && deleteAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (deleteAns != 'Y' && deleteAns != 'N');
                    sc.nextLine();

                    if (deleteAns == 'Y') {
                        String deleteReason;
                        System.out.println("Reason for Deletion: ");
                        deleteReason = sc.nextLine();

                        ReqList.get(RIDindex).setDelete('D');
                        ReqList.get(RIDindex).setReason(deleteReason);

                        String filename = toSearchRID.substring(0, 3) + "_Requests.txt";
                        File file = new File(filename);
                        try {
                            FileWriter output = new FileWriter(file);
                            for (LabRequest labRequest : ReqList) {
                                if (labRequest.getDelete() != 'D' && labRequest.getRequestUID().substring(0,3).equals(toSearchRID.substring(0,3)))
                                    output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                else if (labRequest.getDelete() == 'D' && labRequest.getRequestUID().substring(0,3).equals(toSearchRID.substring(0,3)))
                                    output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete() + ";" + labRequest.getReason() + ";\n");
                            }
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    do{
                        System.out.println("Do you want to delete again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again!='Y' && again!='N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(again!='Y' && again!='N');
                    sc.nextLine();
                }
            }


            else
            {
                int foundCtr11 = 0;
                String toSearchPID;

                System.out.println("Enter the Patient's UID: ");
                toSearchPID = sc.nextLine();

                int CTR = 0;
                int printHeader = 0;

                for (int f = 0; f < ReqList.size(); f++)
                {
                    if (toSearchPID.equals(ReqList.get(f).getPatientUID()) && ReqList.get(f).getDelete()!='D')
                    {
                        for (Services services : ServiceList) {
                            if (services.getServiceCode().equals(ReqList.get(f).getRequestUID().substring(0, 3))) {
                                if (printHeader == 0) {
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", "Request UID", "Lab Test Type", "Request Date", "Result");
                                    printHeader = 1;
                                }
                                System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(f).getRequestUID(), services.getServiceDescription(), ReqList.get(f).getRequestDate(), ReqList.get(f).getResult());
                                CTR = f;
                                foundCtr11++;
                            }
                        }
                    }
                }

                String RIDtoSearch;
                String deleteReason;

                if (foundCtr11 == 1)
                {
                    do{
                        System.out.println("Do you want to delete? (Y/N)");
                        deleteAns = sc.next().charAt(0);
                        if(deleteAns != 'Y' && deleteAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(deleteAns != 'Y' && deleteAns != 'N');

                    sc.nextLine();

                    if (deleteAns == 'Y')
                    {
                        System.out.println("Reason for Deletion: ");
                        deleteReason = sc.nextLine();

                        ReqList.get(CTR).setDelete('D');
                        ReqList.get(CTR).setReason(deleteReason);

                        System.out.println(ReqList.get(CTR).getRequestUID() +";"+ReqList.get(CTR).getPatientUID()+";"+ReqList.get(CTR).getRequestDate()+";"+ReqList.get(CTR).getRequestTime()+";"+ReqList.get(CTR).getResult()+";"+ReqList.get(CTR).getDelete()+";"+ReqList.get(CTR).getReason()+";");

                        String filename = ReqList.get(CTR).getRequestUID().substring(0, 3) + "_Requests.txt";

                        File file = new File(filename);

                        try {
                            FileWriter output = new FileWriter(file);

                            for (LabRequest labRequest : ReqList) {
                                if (labRequest.getDelete() != 'D' && labRequest.getRequestUID().substring(0,3).equals(ReqList.get(CTR).getRequestUID().substring(0,3)))
                                    output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                else if (labRequest.getDelete() == 'D' && labRequest.getRequestUID().substring(0,3).equals(ReqList.get(CTR).getRequestUID().substring(0,3)))
                                    output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete() +";"+ labRequest.getReason() + ";\n");
                            }
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                else if (foundCtr11 > 1)
                {
                    System.out.println("Enter the Request's UID you want to delete: ");
                    RIDtoSearch = sc.nextLine();

                    int RIDindex = 0;
                    boolean foundRIDflag = false;

                    for (int f = 0; f < ReqList.size(); f++)
                    {
                        if (ReqList.get(f).getRequestUID().equals(RIDtoSearch) && ReqList.get(f).getDelete()!='D')
                        {
                            System.out.printf("%-15s %-25s %-15s %-15s %n", "Request UID", "Lab Test Type", "Request Date", "Result");

                            for (Services services : ServiceList) {
                                if (services.getServiceCode().equals(RIDtoSearch.substring(0, 3))) {
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(f).getRequestUID(), services.getServiceDescription(), ReqList.get(f).getRequestDate(), ReqList.get(f).getResult());
                                    RIDindex = f;
                                    foundRIDflag = true;
                                }
                            }
                        }
                    }

                    if (foundRIDflag) {
                        do {
                            System.out.println("Do you want to delete? (Y/N)");
                            deleteAns = sc.next().charAt(0);
                            if(deleteAns != 'Y' && deleteAns != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        } while (deleteAns != 'Y' && deleteAns != 'N');
                        sc.nextLine();
                        if (deleteAns == 'Y') {
                            System.out.println("Reason for Deletion: ");
                            deleteReason = sc.nextLine();

                            ReqList.get(RIDindex).setDelete('D');
                            ReqList.get(RIDindex).setReason(deleteReason);

                            String filename = RIDtoSearch.substring(0, 3) + "_Requests.txt";

                            File file = new File(filename);

                            try {
                                FileWriter output = new FileWriter(file);

                                for (LabRequest labRequest : ReqList) {
                                    if (labRequest.getDelete() != 'D' && labRequest.getRequestUID().substring(0,3).equals(RIDtoSearch.substring(0,3)))
                                        output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                    else if (labRequest.getDelete() == 'D' && labRequest.getRequestUID().substring(0,3).equals(RIDtoSearch.substring(0,3)))
                                        output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete()+";" + labRequest.getReason() + ";\n");
                                }
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


                if (foundCtr11 == 0)
                {
                    System.out.println("No Record Found.");
                    do{
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again!='Y' && again!='N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(again!='Y' && again!='N');
                    sc.nextLine();
                }

                if (foundCtr11 != 0 )
                {
                    do{
                        System.out.println("Do you want to delete laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again!='Y' && again!='N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while(again!='Y' && again!='N');
                    sc.nextLine();
                }
            }
        }while(again == 'Y');
    }

    public static void EditLabReq(ArrayList<LabRequest> ReqList, ArrayList<Services> ServiceList)
    {
        Scanner sc = new Scanner(System.in);
        String newResult;
        char modeSearch;
        char again = 'a';
        char editAns;

        System.out.println();
        do{
            do
            {
                System.out.println("[1] Request's UID");
                System.out.println("[2] Patient's UID");
                System.out.println("Which way do you want to search: ");
                modeSearch = sc.next().charAt(0);
                if(modeSearch != '1' && modeSearch != '2'){
                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1/2).");
                    System.out.println();
                }
            }while(modeSearch != '1' && modeSearch != '2');
            sc.nextLine();

            String toSearchRID;

            if (modeSearch == '1')
            {
                System.out.println("Enter the Request's UID: ");
                toSearchRID = sc.nextLine();

                boolean foundRIDflag = false;
                int serviceIndex = 0;
                int RIDindex = 0;

                for (int g = 0; g < ReqList.size(); g++)
                {
                    if (ReqList.get(g).getRequestUID().equals(toSearchRID) && ReqList.get(g).getDelete()!='D' && ReqList.get(g).getResult().equals("no result"))
                    {
                        for (int j = 0; j < ServiceList.size(); j++) {
                            if (toSearchRID.substring(0, 3).equals(ServiceList.get(j).getServiceCode())) {
                                serviceIndex = j;
                                break;
                            }
                        }

                        if (!foundRIDflag)
                        {
                            System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                            System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(serviceIndex).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                            foundRIDflag = true;
                            RIDindex = g;
                        }
                    }
                }

                if (foundRIDflag)
                {
                    do {
                        System.out.println("Do you want to edit result? (Y/N)");
                        editAns = sc.next().charAt(0);
                        if(editAns != 'Y' && editAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (editAns != 'Y' && editAns != 'N');
                    sc.nextLine();

                    if (editAns == 'Y')
                    {
                        System.out.println("New Result: ");
                        newResult = sc.nextLine();

                        ReqList.get(RIDindex).setResult(newResult);

                        String filename = toSearchRID.substring(0, 3) + "_Requests.txt";
                        File file = new File(filename);
                        FileWriter output = null;
                        try {
                            output = new FileWriter(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (output != null) {
                                output.write("");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (output != null) {
                                output.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        for (LabRequest labRequest : ReqList)
                        {
                            if (labRequest.getRequestUID().substring(0, 3).equals(toSearchRID.substring(0,3)))
                            {
                                output = null;
                                try {
                                    output = new FileWriter(file,true);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (labRequest.getDelete() != 'D') {
                                    try {
                                        if (output != null) {
                                            output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                else {
                                    try {
                                        if (output != null) {
                                            output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete() +";"+ labRequest.getReason() + ";\n");
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                try {
                                    if (output != null) {
                                        output.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                else
                {
                    System.out.println("No Record Found/Laboratory Result has already been edited.");
                }

                do{
                    System.out.println("Do you want to edit laboratory request again? (Y/N)");
                    again = sc.next().charAt(0);
                    if(again!='Y' && again!='N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                }while(again!='Y' && again!='N');
            }

            else
            {
                int printHeader = 0;
                int foundCtr1 = 0;

                System.out.println("Enter the Patient's UID: ");
                toSearchRID = sc.nextLine();
                int TMP = 0;

                for(int g = 0; g < ReqList.size(); g++)
                {
                    if (ReqList.get(g).getPatientUID().equals(toSearchRID) && ReqList.get(g).getDelete()!='D' && ReqList.get(g).getResult().equals("no result"))
                    {
                        if(printHeader == 0)
                        {
                            System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                            printHeader = 1;
                        }

                        for (Services services : ServiceList) {
                            if (services.getServiceCode().equals(ReqList.get(g).getRequestUID().substring(0, 3))) {
                                System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), services.getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                TMP = g;
                                foundCtr1++;
                            }
                        }
                    }
                }

                String RIDtoSearch;

                if (foundCtr1 == 1)
                {
                    do {
                        System.out.println("Do you want to edit the result? (Y/N)");
                        editAns = sc.next().charAt(0);
                        if(editAns != 'Y' && editAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (editAns != 'Y' && editAns != 'N');
                    sc.nextLine();
                    if (editAns == 'Y') {
                        System.out.println("New Result: ");
                        newResult = sc.nextLine();

                        ReqList.get(TMP).setResult(newResult);
                    }
                    for (Services services : ServiceList) {
                        if (services.getServiceCode().equals(ReqList.get(TMP).getRequestUID().substring(0, 3))) {
                            System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(TMP).getRequestUID(), services.getServiceDescription(), ReqList.get(TMP).getRequestDate(), ReqList.get(TMP).getResult());
                        }
                    }

                    String filename = ReqList.get(TMP).getRequestUID().substring(0, 3) + "_Requests.txt";
                    File file = new File(filename);
                    FileWriter output = null;
                    try {
                        output = new FileWriter(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (output != null) {
                            output.write("");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (LabRequest labRequest : ReqList) {
                        if (labRequest.getRequestUID().substring(0, 3).equals(ReqList.get(TMP).getRequestUID().substring(0,3)))
                        {
                            output = null;
                            try {
                                output = new FileWriter(file, true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (labRequest.getDelete() != 'D') {
                                try {
                                    if (output != null) {
                                        output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    if (output != null) {
                                        output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete() + ";" + labRequest.getReason() + ";\n");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                if (output != null) {
                                    output.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    do {
                        System.out.println("Do you want to edit laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                }

                if (foundCtr1 == 0)
                {
                    System.out.println("No Record Found/Laboratory Result has already been edited.");
                    do {
                        System.out.println("Do you want to edit laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                }

                if (foundCtr1 > 1) {
                    System.out.println("Enter the Request's UID you want to its result to be edited: ");
                    RIDtoSearch = sc.nextLine();

                    int serviceIndex = 0;
                    boolean foundRIDflagEDIT = false;
                    int printHeader1 = 0;
                    int RIDindex = 0;

                    for (int g = 0; g < ReqList.size(); g++) {
                        if (ReqList.get(g).getRequestUID().equals(RIDtoSearch) && ReqList.get(g).getDelete() != 'D') {
                            for (int j = 0; j < ServiceList.size(); j++) {
                                if (ReqList.get(g).getRequestUID().substring(0, 3).equals(ServiceList.get(j).getServiceCode()))
                                    serviceIndex = j;
                                if (!foundRIDflagEDIT) {
                                    if (printHeader1 == 0) {
                                        System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                        printHeader1 = 1;
                                    }
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(serviceIndex).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                    foundRIDflagEDIT = true;
                                    RIDindex = g;
                                }
                            }
                        }
                    }

                    if (foundRIDflagEDIT) {
                        do {
                            System.out.println("Do you want to edit result? (Y/N)");
                            editAns = sc.next().charAt(0);
                            if(editAns != 'Y' && editAns != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        } while (editAns != 'Y' && editAns != 'N');
                        sc.nextLine();

                        if (editAns == 'Y') {
                            System.out.println("New Result: ");
                            newResult = sc.nextLine();

                            ReqList.get(RIDindex).setResult(newResult);

                            String filename = RIDtoSearch.substring(0, 3) + "_Requests.txt";
                            File file = new File(filename);
                            FileWriter output = null;
                            try {
                                output = new FileWriter(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (output != null) {
                                    output.write("");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (output != null) {
                                    output.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            for (LabRequest labRequest : ReqList) {
                                if (labRequest.getRequestUID().substring(0, 3).equals(RIDtoSearch.substring(0,3))) {
                                    output = null;
                                    try {
                                        output = new FileWriter(file, true);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if (labRequest.getDelete() != 'D') {
                                        try {
                                            if (output != null) {
                                                output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";\n");
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            if (output != null) {
                                                output.write(labRequest.getRequestUID() + ";" + labRequest.getPatientUID() + ";" + labRequest.getRequestDate() + ";" + labRequest.getRequestTime() + ";" + labRequest.getResult() + ";" + labRequest.getDelete() + ";" + labRequest.getReason() + ";\n");
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    try {
                                        if (output != null) {
                                            output.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("No Record Found/Laboratory Result has already been edited.");
                    }

                    do {
                        System.out.println("Do you want to edit laboratory request again? (Y/N)");
                        again = sc.next().charAt(0);
                        if(again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                }
            }
        }while(again == 'Y');
    }
}
