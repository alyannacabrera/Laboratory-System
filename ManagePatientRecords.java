
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class ManagePatientRecords extends ManageServices
{
    public static void ManagePatientRecord(ArrayList<Patient> PatientList, ArrayList<Services> ServiceList, ArrayList<LabRequest> ReqList)
    {
        Scanner sc = new Scanner(System.in);
        char choice;
        do {
            System.out.println();
            System.out.println("Manage Patient Records");
            System.out.println("[1] Add New Patient");
            System.out.println("[2] Edit Patient Record");
            System.out.println("[3] Delete Patient Record");
            System.out.println("[4] Search Patient Record");
            System.out.println("[X] Return Main Menu");
            System.out.println();
            System.out.print("Select a transaction: ");
            choice= sc.next().charAt(0);

            if (choice!='1' && choice!='2' && choice!='3' && choice!='4' && choice!='X')
                System.out.println("Input is not in the choices. Please enter valid input.");
        }while (choice!='1' && choice!='2' && choice!='3' && choice!='4' && choice!='X');
        sc.nextLine();

        switch (choice)
        {
            case '1' -> AddPatient(PatientList);
            case '2' -> EditPatient(PatientList);
            case '3' -> DeletePatient(PatientList);
            case '4' -> {
                try {
                    SearchPatient(PatientList, ServiceList, ReqList);
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static boolean isNumber(String num)
    {
        for (int i = 0; i < num.length(); i++)
            if (!Character.isDigit(num.charAt(i)))
                return false;

        return true;
    }

    public static void AddPatient(ArrayList<Patient> PatientList) {
        Scanner sc = new Scanner(System.in);
        char Gender;
        char SaveRecord;
        char again;
        String Bday;
        String PhoneNo;
        String NIDNo;
        boolean bdayIntIndicator;
        boolean phoneIntIndicator;
        boolean NIDIntIndicator;

        do
        {
            System.out.println();
            System.out.println("First Name: ");
            String Fname = sc.nextLine();
            System.out.println("Last Name: ");
            String Lname = sc.nextLine();
            System.out.println("Middle Name: ");
            String Mname = sc.nextLine();

            do {
                System.out.println("Birthday(YYYYMMDD): ");
                Bday = sc.nextLine();

                bdayIntIndicator = isNumber(Bday);

                if (Bday.length()!=8 || !bdayIntIndicator) {
                    System.out.println("Invalid input (not an integer/integer is too big/small). Please enter valid input (YYYYMMDD).");
                    System.out.println();
                }
            } while (Bday.length()!=8 || !bdayIntIndicator);

            do {
                System.out.println("Gender (F/M): ");
                Gender = sc.next().charAt(0);
                if (Gender != 'F' && Gender != 'M') {
                    System.out.println("Invalid Input (not F or M). Please enter valid input (F/M).");
                    System.out.println();
                }
            } while (Gender != 'F' && Gender != 'M');
            sc.nextLine();

            System.out.println("Address: ");
            String Address = sc.nextLine();

            do {
                System.out.println("Phone No.: ");
                PhoneNo = sc.nextLine();
                phoneIntIndicator = isNumber(PhoneNo);

                if (!phoneIntIndicator) {
                    System.out.println("Invalid input (not an integer). Please enter valid input (YYYYMMDD).");
                    System.out.println();
                }
            } while (!phoneIntIndicator);

            do {
                System.out.println("National ID No.: ");
                NIDNo = sc.nextLine();
                NIDIntIndicator = isNumber(NIDNo);

                if (!NIDIntIndicator) {
                    System.out.println("Invalid input (not an integer). Please enter valid input (YYYYMMDD).");
                    System.out.println();
                }
            } while (!NIDIntIndicator);

            do {
                System.out.println("Save Patient Record[Y/N]? ");
                SaveRecord = sc.next().charAt(0);
                if (SaveRecord != 'Y' && SaveRecord != 'N')
                {
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            } while (SaveRecord != 'Y' && SaveRecord != 'N');

            if (SaveRecord == 'Y') {
                PatientList.add(new Patient(GeneratePUI(PatientList), Lname, Fname, Mname, Bday, Gender, Address, PhoneNo, NIDNo));
                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(PatientList.size() - 1).getPUI(), PatientList.get(PatientList.size() - 1).getLname(), PatientList.get(PatientList.size() - 1).getFname(), PatientList.get(PatientList.size() - 1).getMname(), PatientList.get(PatientList.size() - 1).getBday(), PatientList.get(PatientList.size() - 1).getGender(), PatientList.get(PatientList.size() - 1).getAddress(), PatientList.get(PatientList.size() - 1).getPhoneNo(), PatientList.get(PatientList.size() - 1).getNIDNo());

                File file = new File("Patients.txt");
                try {
                    boolean value = file.createNewFile();
                    System.out.println();
                    if (value) {
                        System.out.println("The file (Patients.txt) is created.");
                        try {
                            FileWriter output = new FileWriter("Patients.txt");
                            output.write(PatientList.get(PatientList.size() - 1).getPUI() + ";" + PatientList.get(PatientList.size() - 1).getLname() + ";" + PatientList.get(PatientList.size() - 1).getFname() + ";" + PatientList.get(PatientList.size() - 1).getMname() + ";" + PatientList.get(PatientList.size() - 1).getBday() + ";" + PatientList.get(PatientList.size() - 1).getGender() + ";" + PatientList.get(PatientList.size() - 1).getAddress() + ";" + PatientList.get(PatientList.size() - 1).getPhoneNo() + ";" + PatientList.get(PatientList.size() - 1).getNIDNo() + ";\n");
                            System.out.println("Patient has been added to file (Patients.txt).");
                            System.out.println();
                            output.close();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    } else {
                        System.out.println("The file (Patients.txt) already exists.");
                        try (FileWriter f = new FileWriter("Patients.txt", true);
                             BufferedWriter b = new BufferedWriter(f);
                             PrintWriter p = new PrintWriter(b)) {
                            p.println(PatientList.get(PatientList.size() - 1).getPUI() + ";" + PatientList.get(PatientList.size() - 1).getLname() + ";" + PatientList.get(PatientList.size() - 1).getFname() + ";" + PatientList.get(PatientList.size() - 1).getMname() + ";" + PatientList.get(PatientList.size() - 1).getBday() + ";" + PatientList.get(PatientList.size() - 1).getGender() + ";" + PatientList.get(PatientList.size() - 1).getAddress() + ";" + PatientList.get(PatientList.size() - 1).getPhoneNo() + ";" + PatientList.get(PatientList.size() - 1).getNIDNo() + ";");
                            System.out.println("Patient has been added to file (Patients.txt).");
                            System.out.println();
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            do {
                System.out.println("Do you want to add patient again? (Y/N)");
                again = sc.next().charAt(0);
                if (again != 'Y' && again != 'N')
                {
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            } while (again != 'Y' && again != 'N');
            sc.nextLine();
        }while(again == 'Y');
    }

    public static String GeneratePUI(ArrayList<Patient> PatientList)
    {
        Format M = new SimpleDateFormat("MM");
        Format Y = new SimpleDateFormat("yyyy");
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Number;
        int tmp = 0;
        char tenThousandsPlace, thousandsPlace, hundredsPlace;

        for (Patient patient : PatientList) {
            if (patient.getPUI().substring(1, 7).equals(Y.format(new Date()) + M.format(new Date()))) {
                tmp++;
            }
        }

        tenThousandsPlace = Alphabet.charAt(tmp / (26*26*10*10));
        tmp = tmp % (26*26*10*10);
        thousandsPlace = Alphabet.charAt(tmp / (26*10*10));
        tmp = tmp % (26*10*10);
        hundredsPlace = Alphabet.charAt(tmp / (10*10));
        tmp = tmp % (10*10);

        if (tmp < 10)
            Number = "0" + tmp;
        else
            Number = String.valueOf(tmp);

        return "P" + Y.format(new Date()) + M.format(new Date()) +tenThousandsPlace + thousandsPlace + hundredsPlace + Number;
    }

    public static void EditPatient(ArrayList<Patient> PatientList)
    {
        Scanner sc = new Scanner(System.in);
        char again = 'a';

        do
        {
            char KnowUID;
            do{
                System.out.println("Do you know the UID?(Y/N)");
                KnowUID = sc.next().charAt(0);
                if (KnowUID != 'Y' && KnowUID != 'N')
                {
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            } while(KnowUID != 'Y' && KnowUID != 'N');
            sc.nextLine();
            if (KnowUID=='Y')
            {
                System.out.println("Enter Patient's UID: ");
                String toSearchUID = sc.nextLine();
                int foundUIDctr = 0;
                int tmp = 0;

                for (int j = 0; j < PatientList.size(); j++) {
                    if (toSearchUID.equals(PatientList.get(j).getPUI()) && PatientList.get(j).getDelete() != 'D')
                    {
                        System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(j).getPUI(), PatientList.get(j).getLname(), PatientList.get(j).getFname(), PatientList.get(j).getMname(), PatientList.get(j).getBday(), PatientList.get(j).getGender(), PatientList.get(j).getAddress(), PatientList.get(j).getPhoneNo(), PatientList.get(j).getNIDNo());
                        foundUIDctr++;
                        tmp = j;
                    }
                }

                if (foundUIDctr == 0)
                {
                    do {
                        System.out.println("The UID ("+toSearchUID+") was not found or was deleted");
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

                else
                {
                    char updateAns;
                    do{
                        System.out.println("Do you want to update? (Y/N)");
                        updateAns = sc.next().charAt(0);
                        if (updateAns != 'Y' && updateAns != 'N')
                        {
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (updateAns != 'Y' && updateAns != 'N');
                    sc.nextLine();

                    char dataToupdate;

                    if (updateAns == 'Y')
                    {
                        System.out.println("Before: "+ PatientList.get(tmp).getPUI() + ";" + PatientList.get(tmp).getLname() + ";" + PatientList.get(tmp).getFname() + ";" + PatientList.get(tmp).getMname() + ";" + PatientList.get(tmp).getBday() + ";" + PatientList.get(tmp).getGender() + ";" + PatientList.get(tmp).getAddress() + ";" + PatientList.get(tmp).getPhoneNo() + ";" + PatientList.get(tmp).getNIDNo());
                        System.out.println();
                        do{
                            System.out.println("[1] Update address");
                            System.out.println("[2] Update phone no.");
                            System.out.println("Which data do you want to update? (1 or 2) ");
                            dataToupdate = sc.nextLine().charAt(0);

                            if (dataToupdate != '1' && dataToupdate != '2')
                            {
                                System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1 or 2).");
                                System.out.println();
                            }
                        }while(dataToupdate != '1' && dataToupdate != '2');

                        if (dataToupdate == '1')
                        {
                            System.out.println("New Address: ");
                            PatientList.get(tmp).setAddress(sc.nextLine());
                            System.out.println("After: "+ PatientList.get(tmp).getPUI() + ";" + PatientList.get(tmp).getLname() + ";" + PatientList.get(tmp).getFname() + ";" + PatientList.get(tmp).getMname() + ";" + PatientList.get(tmp).getBday() + ";" + PatientList.get(tmp).getGender() + ";" + PatientList.get(tmp).getAddress() + ";" + PatientList.get(tmp).getPhoneNo() + ";" + PatientList.get(tmp).getNIDNo());

                            File file = new File("Patients.txt");

                            try {
                                FileWriter output = new FileWriter(file);

                                for (Patient patient : PatientList) {
                                    if (patient.getDelete() != 'D')
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                    else
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                }
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("The Patient's Address has been updated.");
                        }

                        if (dataToupdate == '2')
                        {
                            System.out.println("New Phone Number: ");
                            PatientList.get(tmp).setPhoneNo(sc.nextLine());
                            System.out.println("After: "+ PatientList.get(tmp).getPUI() + ";" + PatientList.get(tmp).getLname() + ";" + PatientList.get(tmp).getFname() + ";" + PatientList.get(tmp).getMname() + ";" + PatientList.get(tmp).getBday() + ";" + PatientList.get(tmp).getGender() + ";" + PatientList.get(tmp).getAddress() + ";" + PatientList.get(tmp).getPhoneNo() + ";" + PatientList.get(tmp).getNIDNo());

                            File file = new File("Patients.txt");

                            try {
                                FileWriter output = new FileWriter(file);

                                for (Patient patient : PatientList) {
                                    if (patient.getDelete() != 'D')
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                    else
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                }
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("The Patient's Phone Number has been updated.");
                        }

                        do {
                            System.out.println("Do you want to edit patient again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                    }

                    else
                    {
                        do {
                            System.out.println("Do you want to edit patient again? (Y/N)");
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
                char KnowNID;
                do{
                    System.out.println("Do you know the National ID no.?(Y/N)");
                    KnowNID = sc.next().charAt(0);
                    if (KnowNID != 'Y' && KnowNID != 'N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                } while(KnowNID != 'Y' && KnowNID != 'N');
                sc.nextLine();

                if (KnowNID=='Y') //NID
                {
                    System.out.println("Enter National ID no.: ");
                    String toSearchNID = sc.nextLine();
                    int NIDfoundctr = 0;
                    int tmp1 = 0;

                    for (int k = 0; k < PatientList.size(); k++)
                    {
                        if (toSearchNID.equals(PatientList.get(k).getNIDNo()) && PatientList.get(k).getDelete() != 'D')
                        {
                            System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(k).getPUI(), PatientList.get(k).getLname(), PatientList.get(k).getFname(), PatientList.get(k).getMname(), PatientList.get(k).getBday(), PatientList.get(k).getGender(), PatientList.get(k).getAddress(), PatientList.get(k).getPhoneNo(), PatientList.get(k).getNIDNo());

                            NIDfoundctr = 1;
                            tmp1 = k;
                        }
                    }

                    if (NIDfoundctr == 0)
                    {
                        do {
                            System.out.println("The NID ("+toSearchNID+") was not found or deleted");
                            System.out.println("Do you want to try again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    else
                    {
                        char updateAns;
                        do{
                            System.out.println("Do you want to update? (Y/N)");
                            updateAns = sc.next().charAt(0);
                            if (updateAns != 'Y' && updateAns != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (updateAns != 'Y' && updateAns != 'N');
                        sc.nextLine();
                        char dataToupdate;
                        if (updateAns == 'Y')
                        {
                            System.out.println("Before: "+ PatientList.get(tmp1).getPUI() + ";" + PatientList.get(tmp1).getLname() + ";" + PatientList.get(tmp1).getFname() + ";" + PatientList.get(tmp1).getMname() + ";" + PatientList.get(tmp1).getBday() + ";" + PatientList.get(tmp1).getGender() + ";" + PatientList.get(tmp1).getAddress() + ";" + PatientList.get(tmp1).getPhoneNo() + ";" + PatientList.get(tmp1).getNIDNo());

                            System.out.println();
                            do{
                                System.out.println("[1] Update address");
                                System.out.println("[2] Update phone no.");
                                System.out.println("Which data do you want to update? (1 or 2) ");
                                dataToupdate = sc.nextLine().charAt(0);
                                if (dataToupdate != '1' && dataToupdate != '2'){
                                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1 or 2).");
                                    System.out.println();
                                }
                            }while(dataToupdate != '1' && dataToupdate != '2');

                            if (dataToupdate == '1')
                            {
                                System.out.println("New Address: ");
                                PatientList.get(tmp1).setAddress(sc.nextLine());
                                System.out.println("After: "+ PatientList.get(tmp1).getPUI() + ";" + PatientList.get(tmp1).getLname() + ";" + PatientList.get(tmp1).getFname() + ";" + PatientList.get(tmp1).getMname() + ";" + PatientList.get(tmp1).getBday() + ";" + PatientList.get(tmp1).getGender() + ";" + PatientList.get(tmp1).getAddress() + ";" + PatientList.get(tmp1).getPhoneNo() + ";" + PatientList.get(tmp1).getNIDNo());

                                File file = new File("Patients.txt");
                                try {
                                    FileWriter output = new FileWriter(file);

                                    for (Patient patient : PatientList) {
                                        if (patient.getDelete() != 'D')
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                        else
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                    }
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("The patient's address has been updated!");
                            }

                            if (dataToupdate == '2')
                            {
                                System.out.println("New Phone Number: ");
                                PatientList.get(tmp1).setPhoneNo(sc.nextLine());
                                System.out.println("After: "+ PatientList.get(tmp1).getPUI() + ";" + PatientList.get(tmp1).getLname() + ";" + PatientList.get(tmp1).getFname() + ";" + PatientList.get(tmp1).getMname() + ";" + PatientList.get(tmp1).getBday() + ";" + PatientList.get(tmp1).getGender() + ";" + PatientList.get(tmp1).getAddress() + ";" + PatientList.get(tmp1).getPhoneNo() + ";" + PatientList.get(tmp1).getNIDNo());

                                File file = new File("Patients.txt");
                                try {
                                    FileWriter output = new FileWriter(file);

                                    for (Patient patient : PatientList) {
                                        if (patient.getDelete() != 'D')
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                        else
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                    }
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("The patient's phone number has been updated!");
                            }
                            do {
                                System.out.println("Do you want to edit patient again? (Y/N)");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while (again != 'Y' && again != 'N');
                        }

                        else
                        {
                            do {
                                System.out.println("Do you want to edit patient again? (Y/N)");
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

                else //Names and Birthdays
                {
                    System.out.println("Enter Last name: ");
                    String toSearchLname = sc.nextLine();
                    System.out.println("Enter First name: ");
                    String toSearchFname = sc.nextLine();
                    System.out.println("Enter Birthday: ");
                    String toSearchBday = sc.nextLine();

                    int foundctr2 = 0;
                    int headerctr = 0;
                    int tmp0 = 0;

                    for (int m = 0; m < PatientList.size(); m++)
                    {
                        if ((toSearchLname.equals(PatientList.get(m).getLname())) && (toSearchFname.equals(PatientList.get(m).getFname())) && (toSearchBday.equals(PatientList.get(m).getBday())) && PatientList.get(m).getDelete()!='D')
                        {
                            foundctr2++;
                            if (headerctr == 0)
                            {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(m).getPUI(), PatientList.get(m).getLname(), PatientList.get(m).getFname(), PatientList.get(m).getMname(), PatientList.get(m).getBday(), PatientList.get(m).getGender(), PatientList.get(m).getAddress(), PatientList.get(m).getPhoneNo(), PatientList.get(m).getNIDNo());
                                tmp0 = m;
                                headerctr++;
                            }
                            else
                            {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(m).getPUI(), PatientList.get(m).getLname(), PatientList.get(m).getFname(), PatientList.get(m).getMname(), PatientList.get(m).getBday(), PatientList.get(m).getGender(), PatientList.get(m).getAddress(), PatientList.get(m).getPhoneNo(), PatientList.get(m).getNIDNo());
                            }
                        }
                    }

                    if (foundctr2 == 0) //not found
                    {
                        do{
                            System.out.println("The First Name (" + toSearchFname+ "), Last Name ("+ toSearchLname+") and Birthday Combination ("+toSearchBday+") was not found.");
                            System.out.println("Do you want to search again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    if (foundctr2 == 1)
                    {
                        char updateAns;

                        do{
                            System.out.println("Do you want to update? (Y/N)");
                            updateAns = sc.next().charAt(0);
                            if (updateAns != 'Y' && updateAns != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (updateAns != 'Y' && updateAns != 'N');
                        sc.nextLine();
                        char dataToupdate;
                        if (updateAns == 'Y')
                        {
                            System.out.println("Before: "+ PatientList.get(tmp0).getPUI() + ";" + PatientList.get(tmp0).getLname() + ";" + PatientList.get(tmp0).getFname() + ";" + PatientList.get(tmp0).getMname() + ";" + PatientList.get(tmp0).getBday() + ";" + PatientList.get(tmp0).getGender() + ";" + PatientList.get(tmp0).getAddress() + ";" + PatientList.get(tmp0).getPhoneNo() + ";" + PatientList.get(tmp0).getNIDNo());

                            System.out.println();
                            do{
                                System.out.println("[1] Update address");
                                System.out.println("[2] Update phone no.");
                                System.out.println("Which data do you want to update? (1 or 2) ");
                                dataToupdate = sc.nextLine().charAt(0);
                                if (dataToupdate != '1' && dataToupdate != '2'){
                                    System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1 or 2).");
                                    System.out.println();
                                }
                            }while(dataToupdate != '1' && dataToupdate != '2');


                            if (dataToupdate == '1')
                            {
                                System.out.println("New Address: ");
                                PatientList.get(tmp0).setAddress(sc.nextLine());
                                System.out.println("After: "+ PatientList.get(tmp0).getPUI() + ";" + PatientList.get(tmp0).getLname() + ";" + PatientList.get(tmp0).getFname() + ";" + PatientList.get(tmp0).getMname() + ";" + PatientList.get(tmp0).getBday() + ";" + PatientList.get(tmp0).getGender() + ";" + PatientList.get(tmp0).getAddress() + ";" + PatientList.get(tmp0).getPhoneNo() + ";" + PatientList.get(tmp0).getNIDNo());

                                File file = new File("Patients.txt");
                                try {
                                    FileWriter output = new FileWriter(file);

                                    for (Patient patient : PatientList) {
                                        if (patient.getDelete() != 'D')
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                        else
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                    }
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("The patient's address has been updated!");
                            }

                            if (dataToupdate == '2')
                            {
                                System.out.println("New Phone Number: ");
                                PatientList.get(tmp0).setPhoneNo(sc.nextLine());
                                System.out.println("After: "+ PatientList.get(tmp0).getPUI() + ";" + PatientList.get(tmp0).getLname() + ";" + PatientList.get(tmp0).getFname() + ";" + PatientList.get(tmp0).getMname() + ";" + PatientList.get(tmp0).getBday() + ";" + PatientList.get(tmp0).getGender() + ";" + PatientList.get(tmp0).getAddress() + ";" + PatientList.get(tmp0).getPhoneNo() + ";" + PatientList.get(tmp0).getNIDNo());

                                File file = new File("Patients.txt");

                                try {
                                    FileWriter output = new FileWriter(file);

                                    for (Patient patient : PatientList) {
                                        if (patient.getDelete() != 'D')
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                        else
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                    }
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("The patient's phone number has been updated!");
                            }
                            do {
                                System.out.println("Do you want to edit patient again? (Y/N)");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while (again != 'Y' && again != 'N');
                        }
                        else
                        {
                            do {
                                System.out.println("Do you want to edit patient again? (Y/N)");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            } while (again != 'Y' && again != 'N');
                            sc.nextLine();
                        }
                    }

                    else if (foundctr2 > 1) //multiple same names
                    {
                        char updateAns;

                        System.out.println("Enter the patient's UID that you want to display: ");
                        String toSearchUID = sc.nextLine();
                        int foundctr = 0;
                        int tmp2 = 0;

                        System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");

                        for (int l = 0; l < PatientList.size(); l++) {
                            if (toSearchUID.equals(PatientList.get(l).getPUI())) {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(l).getPUI(), PatientList.get(l).getLname(), PatientList.get(l).getFname(), PatientList.get(l).getMname(), PatientList.get(l).getBday(), PatientList.get(l).getGender(), PatientList.get(l).getAddress(), PatientList.get(l).getPhoneNo(), PatientList.get(l).getNIDNo());
                                foundctr++;
                                tmp2 = l;
                            }
                        }

                        if (foundctr == 0)
                        {
                            do{
                                System.out.println("The UID ("+toSearchUID+") was not found");
                                System.out.println("Do you want to try again? (Y/N)");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while(again != 'Y' && again != 'N');
                            sc.nextLine();
                        }

                        do{
                            System.out.println("Do you want to update? (Y/N)");
                            updateAns = sc.next().charAt(0);
                            if (updateAns != 'Y' && updateAns != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (updateAns != 'Y' && updateAns != 'N');
                        sc.nextLine();
                        char dataToupdate;
                        if (updateAns == 'Y')
                        {
                            System.out.println("Before: "+ PatientList.get(tmp0).getPUI() + ";" + PatientList.get(tmp0).getLname() + ";" + PatientList.get(tmp0).getFname() + ";" + PatientList.get(tmp0).getMname() + ";" + PatientList.get(tmp0).getBday() + ";" + PatientList.get(tmp0).getGender() + ";" + PatientList.get(tmp0).getAddress() + ";" + PatientList.get(tmp0).getPhoneNo() + ";" + PatientList.get(tmp0).getNIDNo());

                            if (foundctr != 0) {
                                do {
                                    System.out.println("[1] Update address");
                                    System.out.println("[2] Update phone no.");
                                    System.out.println("Which data do you want to update? (1 or 2) ");
                                    dataToupdate = sc.nextLine().charAt(0);
                                    if (dataToupdate != '1' && dataToupdate != '2'){
                                        System.out.println("Invalid Input (not 1 or 2). Please enter valid input (1 or 2).");
                                        System.out.println();
                                    }
                                } while (dataToupdate != '1' && dataToupdate != '2');


                                if (dataToupdate == '1') {
                                    System.out.println("New Address: ");
                                    PatientList.get(tmp2).setAddress(sc.nextLine());
                                    System.out.println("After: " + PatientList.get(tmp2).getPUI() + ";" + PatientList.get(tmp2).getLname() + ";" + PatientList.get(tmp2).getFname() + ";" + PatientList.get(tmp2).getMname() + ";" + PatientList.get(tmp2).getBday() + ";" + PatientList.get(tmp2).getGender() + ";" + PatientList.get(tmp2).getAddress() + ";" + PatientList.get(tmp2).getPhoneNo() + ";" + PatientList.get(tmp2).getNIDNo());

                                    File file = new File("Patients.txt");

                                    try {
                                        FileWriter output = new FileWriter(file);

                                        for (Patient patient : PatientList) {
                                            if (patient.getDelete() != 'D')
                                                output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                            else
                                                output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                        }
                                        output.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("The Patient's Address has been updated.");
                                }

                                if (dataToupdate == '2') {
                                    System.out.println("New Phone Number: ");
                                    PatientList.get(tmp2).setPhoneNo(sc.nextLine());
                                    System.out.println("After: " + PatientList.get(tmp2).getPUI() + ";" + PatientList.get(tmp2).getLname() + ";" + PatientList.get(tmp2).getFname() + ";" + PatientList.get(tmp2).getMname() + ";" + PatientList.get(tmp2).getBday() + ";" + PatientList.get(tmp2).getGender() + ";" + PatientList.get(tmp2).getAddress() + ";" + PatientList.get(tmp2).getPhoneNo() + ";" + PatientList.get(tmp2).getNIDNo());

                                    File file = new File("Patients.txt");

                                    try {
                                        FileWriter output = new FileWriter(file);

                                        for (Patient patient : PatientList) {
                                            if (patient.getDelete() != 'D')
                                                output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                            else
                                                output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                        }
                                        output.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("The Patient's Phone Number has been updated.");
                                }

                                do {
                                    System.out.println("Do you want to edit patient again? (Y/N)");
                                    again = sc.next().charAt(0);
                                    if (again != 'Y' && again != 'N'){
                                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                        System.out.println();
                                    }
                                } while (again != 'Y' && again != 'N');
                            }
                        }
                        else
                        {
                            do {
                                System.out.println("Do you want to edit patient again? (Y/N)");
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
            }
        }while(again == 'Y');
    }

    public static void DeletePatient(ArrayList<Patient> PatientList)
    {
        Scanner sc = new Scanner(System.in);
        char again = 'a';

        do
        {
            char KnowUID;
            do{
                System.out.println("Do you know the UID?(Y/N)");
                KnowUID = sc.next().charAt(0);
                if (KnowUID != 'Y' && KnowUID != 'N'){
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            } while(KnowUID != 'Y' && KnowUID != 'N');
            sc.nextLine();

            if (KnowUID=='Y')
            {
                System.out.println("Enter Patient's UID: ");
                String toSearchUID = sc.nextLine();
                int foundUIDctr = 0;
                int tmp = 0;

                for (int j = 0; j < PatientList.size(); j++)
                {
                    if (toSearchUID.equals(PatientList.get(j).getPUI()) && PatientList.get(j).getDelete()!='D')
                    {
                        System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(j).getPUI(), PatientList.get(j).getLname(), PatientList.get(j).getFname(), PatientList.get(j).getMname(), PatientList.get(j).getBday(), PatientList.get(j).getGender(), PatientList.get(j).getAddress(), PatientList.get(j).getPhoneNo(), PatientList.get(j).getNIDNo());
                        foundUIDctr=1;
                        tmp = j;
                    }
                }

                if (foundUIDctr == 0)
                {
                    do {
                        System.out.println("The UID ("+toSearchUID+") was not found");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }

                else
                {
                    char deleteAns;
                    do{
                        System.out.println("Do you want to delete? (Y/N)");
                        deleteAns = sc.next().charAt(0);
                        if (deleteAns != 'Y' && deleteAns != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (deleteAns != 'Y' && deleteAns != 'N');
                    sc.nextLine();
                    String reason;

                    if (deleteAns == 'Y')
                    {
                        System.out.println("Enter Reason for Deletion: ");
                        reason = sc.nextLine();

                        PatientList.get(tmp).setDelete('D');
                        PatientList.get(tmp).setReason(reason);

                        File file = new File("Patients.txt");
                        try {
                            FileWriter output = new FileWriter(file);

                            for (Patient patient : PatientList) {
                                if (patient.getDelete() != 'D')
                                    output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                else
                                    output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                            }
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println(PatientList.get(tmp).getPUI() + ";" + PatientList.get(tmp).getLname() + ";" + PatientList.get(tmp).getFname() + ";" + PatientList.get(tmp).getMname() + ";" + PatientList.get(tmp).getBday() + ";" + PatientList.get(tmp).getGender() + ";" + PatientList.get(tmp).getAddress() + ";" + PatientList.get(tmp).getPhoneNo() + ";" + PatientList.get(tmp).getNIDNo() + ";" + PatientList.get(tmp).getDelete() + ";" + PatientList.get(tmp).getReason() + ";");
                        System.out.println("The Patient's ("+PatientList.get(tmp).getPUI()+") has been deleted.");
                        System.out.println();
                    }
                    do {
                        System.out.println("Do you want to delete patient again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    } while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }
            }

            else
            {
                char KnowNID;
                do{
                    System.out.println("Do you know the National ID no.?(Y/N)");
                    KnowNID = sc.next().charAt(0);
                    if (KnowNID != 'Y' && KnowNID != 'N')
                    {
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                } while(KnowNID != 'Y' && KnowNID != 'N');
                sc.nextLine();

                if (KnowNID=='Y') //NID
                {
                    System.out.println("Enter National ID no.: ");
                    String toSearchNID = sc.nextLine();
                    int NIDfoundctr = 0;
                    int tmp1 = 0;
                    for (int k = 0; k < PatientList.size(); k++)
                    {
                        if ((toSearchNID).equals(PatientList.get(k).getNIDNo()) && PatientList.get(k).getDelete()!='D')
                        {
                            System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(k).getPUI(), PatientList.get(k).getLname(), PatientList.get(k).getFname(), PatientList.get(k).getMname(), PatientList.get(k).getBday(), PatientList.get(k).getGender(), PatientList.get(k).getAddress(), PatientList.get(k).getPhoneNo(), PatientList.get(k).getNIDNo());

                            NIDfoundctr = 1;
                            //again = 'N';
                            tmp1 = k;
                        }
                    }

                    if (NIDfoundctr == 0)
                    {
                        do {
                            System.out.println("The NID ("+toSearchNID+") was not found");
                            System.out.println("Do you want to try again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    else
                    {
                        char deleteAnsNID;
                        do{
                            System.out.println("Do you want to delete? (Y/N)");
                            deleteAnsNID = sc.next().charAt(0);
                            if (deleteAnsNID != 'Y' && deleteAnsNID != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (deleteAnsNID != 'Y' && deleteAnsNID != 'N');
                        sc.nextLine();
                        String reason;
                        if (deleteAnsNID == 'Y')
                        {
                            System.out.println("Enter Reason for Deletion: ");
                            reason = sc.nextLine();

                            PatientList.get(tmp1).setDelete('D');
                            PatientList.get(tmp1).setReason(reason);

                            File file = new File("Patients.txt");

                            try {
                                FileWriter output = new FileWriter(file);

                                for (Patient patient : PatientList) {
                                    if (patient.getDelete() != 'D')
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                    else
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                }
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.out.println(PatientList.get(tmp1).getPUI() + ";" + PatientList.get(tmp1).getLname() + ";" + PatientList.get(tmp1).getFname() + ";" + PatientList.get(tmp1).getMname() + ";" + PatientList.get(tmp1).getBday() + ";" + PatientList.get(tmp1).getGender() + ";" + PatientList.get(tmp1).getAddress() + ";" + PatientList.get(tmp1).getPhoneNo() + ";" + PatientList.get(tmp1).getNIDNo() + ";" + PatientList.get(tmp1).getDelete() + ";" + PatientList.get(tmp1).getReason() + ";");
                            System.out.println("The Patient's ("+PatientList.get(tmp1).getPUI()+") has been deleted.");

                        }
                        do {
                            System.out.println("Do you want to delete patient again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        } while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }
                }

                else //Names and Birthdays
                {
                    System.out.println("Enter Last name: ");
                    String toSearchLname = sc.nextLine();
                    System.out.println("Enter First name: ");
                    String toSearchFname = sc.nextLine();
                    System.out.println("Enter Birthday: ");
                    String toSearchBday = sc.nextLine();

                    int foundctr2 = 0;
                    int headerctr = 0;
                    int tmp3 = 0;

                    for (int m = 0; m < PatientList.size(); m++)
                    {
                        if ((toSearchLname.equals(PatientList.get(m).getLname())) && (toSearchFname.equals(PatientList.get(m).getFname())) && (toSearchBday.equals(PatientList.get(m).getBday())) && PatientList.get(m).getDelete()!='D')
                        {
                            if (headerctr == 0)
                            {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");
                                headerctr=1;
                            }
                            System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(m).getPUI(), PatientList.get(m).getLname(), PatientList.get(m).getFname(), PatientList.get(m).getMname(), PatientList.get(m).getBday(), PatientList.get(m).getGender(), PatientList.get(m).getAddress(), PatientList.get(m).getPhoneNo(), PatientList.get(m).getNIDNo());
                            tmp3 = m;
                            foundctr2++;
                        }
                    }

                    if (foundctr2 == 1)
                    {
                        char deleteAnsThird;
                        do{
                            System.out.println("Do you want to delete? (Y/N)");
                            deleteAnsThird = sc.next().charAt(0);
                            if (deleteAnsThird != 'Y' && deleteAnsThird != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (deleteAnsThird != 'Y' && deleteAnsThird != 'N');
                        sc.nextLine();

                        String reason;
                        if (deleteAnsThird == 'Y')
                        {
                            System.out.println("Enter Reason for Deletion: ");
                            reason = sc.nextLine();

                            PatientList.get(tmp3).setDelete('D');
                            PatientList.get(tmp3).setReason(reason);

                            File file = new File("Patients.txt");
                            try {
                                FileWriter output = new FileWriter(file);
                                for (Patient patient : PatientList) {
                                    if (patient.getDelete() != 'D')
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                    else
                                        output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                }
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.out.println(PatientList.get(tmp3).getPUI() + ";" + PatientList.get(tmp3).getLname() + ";" + PatientList.get(tmp3).getFname() + ";" + PatientList.get(tmp3).getMname() + ";" + PatientList.get(tmp3).getBday() + ";" + PatientList.get(tmp3).getGender() + ";" + PatientList.get(tmp3).getAddress() + ";" + PatientList.get(tmp3).getPhoneNo() + ";" + PatientList.get(tmp3).getNIDNo() + ";" +PatientList.get(tmp3).getDelete() + ";" + PatientList.get(tmp3).getReason() + ";");
                            System.out.println("The Patient's ("+PatientList.get(tmp3).getPUI()+") has been deleted.");

                        }
                        do {
                            System.out.println("Do you want to delete patient again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        } while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    if (foundctr2 > 1) //multiple same names
                    {
                        System.out.println("Enter the patient's UID that you want to display: ");
                        String toSearchUID = sc.nextLine();
                        int foundctr = 0;
                        int tmp2 = 0;

                        System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");

                        for (int l = 0; l < PatientList.size(); l++)
                        {
                            if (toSearchUID.equals(PatientList.get(l).getPUI()))
                            {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(l).getPUI(), PatientList.get(l).getLname(), PatientList.get(l).getFname(), PatientList.get(l).getMname(), PatientList.get(l).getBday(), PatientList.get(l).getGender(), PatientList.get(l).getAddress(), PatientList.get(l).getPhoneNo(), PatientList.get(l).getNIDNo());
                                foundctr++;
                                tmp2 = l;
                            }
                        }

                        if (foundctr == 0)
                        {
                            do{
                                System.out.println("The UID ("+toSearchUID+") was not found");
                                System.out.println("Do you want to try again? (Y/N)");
                                again = sc.next().charAt(0);
                                if (again != 'Y' && again != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while(again != 'Y' && again != 'N');
                            sc.nextLine();
                        }

                        if (foundctr != 0)
                        {
                            char deleteAnsThird;
                            do{
                                System.out.println("Do you want to delete? (Y/N)");
                                deleteAnsThird = sc.next().charAt(0);
                                if (deleteAnsThird != 'Y' && deleteAnsThird != 'N'){
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            }while (deleteAnsThird != 'Y' && deleteAnsThird != 'N');
                            sc.nextLine();

                            String reason;
                            if (deleteAnsThird == 'Y')
                            {
                                System.out.println("Enter Reason for Deletion: ");
                                reason = sc.nextLine();

                                PatientList.get(tmp2).setDelete('D');
                                PatientList.get(tmp2).setReason(reason);

                                File file = new File("Patients.txt");
                                try {
                                    FileWriter output = new FileWriter(file);
                                    for (Patient patient : PatientList) {
                                        if (patient.getDelete() != 'D')
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";\n");
                                        else
                                            output.write(patient.getPUI() + ";" + patient.getLname() + ";" + patient.getFname() + ";" + patient.getMname() + ";" + patient.getBday() + ";" + patient.getGender() + ";" + patient.getAddress() + ";" + patient.getPhoneNo() + ";" + patient.getNIDNo() + ";" + patient.getDelete() + ";" + patient.getReason() + ";\n");
                                    }
                                    output.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(PatientList.get(tmp2).getPUI() + ";" + PatientList.get(tmp2).getLname() + ";" + PatientList.get(tmp2).getFname() + ";" + PatientList.get(tmp2).getMname() + ";" + PatientList.get(tmp2).getBday() + ";" + PatientList.get(tmp2).getGender() + ";" + PatientList.get(tmp2).getAddress() + ";" + PatientList.get(tmp2).getPhoneNo() + ";" + PatientList.get(tmp2).getNIDNo() + ";" + PatientList.get(tmp2).getDelete() + ";" + PatientList.get(tmp2).getReason() + ";");
                                System.out.println("The Patient's ("+PatientList.get(tmp2).getPUI()+") has been deleted.");
                            }

                            else
                            {
                                do {
                                    System.out.println("Do you want to delete patient again? (Y/N)");
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

                    if (foundctr2 == 0) //not found
                    {
                        do{
                            System.out.println("The First Name (" + toSearchFname+ "), Last Name ("+ toSearchLname+") and Birthday Combination ("+toSearchBday+") was not found.");
                            System.out.println("Do you want to search again? (Y/N)");
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

    public static void SearchPatient(ArrayList<Patient> PatientList, ArrayList<Services> ServiceList, ArrayList<LabRequest> ReqList) throws FileNotFoundException, DocumentException {
        sortDescending(ReqList);

        Scanner sc = new Scanner(System.in);
        char again;

        do
        {
            char KnowUID;
            int foundUIDctr = 0;
            int NIDfoundctr = 0;
            int foundctr2 = 0;
            String toSearchUID;
            int UIDindex = 0;

            do{
                System.out.println("Do you know the UID?(Y/N)");
                KnowUID = sc.next().charAt(0);
                if (KnowUID != 'Y' && KnowUID != 'N'){
                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                    System.out.println();
                }
            } while(KnowUID != 'Y' && KnowUID != 'N');
            sc.nextLine();

            if (KnowUID=='Y')
            {
                System.out.println("Enter Patient's UID: ");
                toSearchUID = sc.nextLine();

                for (int x=0; x < PatientList.size(); x++) {
                    if (toSearchUID.equals(PatientList.get(x).getPUI()) && PatientList.get(x).getDelete() != 'D')
                    {
                        System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(x).getPUI(), PatientList.get(x).getLname(), PatientList.get(x).getFname(), PatientList.get(x).getMname(), PatientList.get(x).getBday(), PatientList.get(x).getGender(), PatientList.get(x).getAddress(), PatientList.get(x).getPhoneNo(), PatientList.get(x).getNIDNo());
                        UIDindex = x;
                        foundUIDctr++;
                    }
                }

                if (foundUIDctr == 0)
                {
                    do {
                        System.out.println("The UID ("+toSearchUID+") was not found or was deleted");
                        System.out.println("Do you want to try again? (Y/N)");
                        again = sc.next().charAt(0);
                        if (again != 'Y' && again != 'N'){
                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                            System.out.println();
                        }
                    }while (again != 'Y' && again != 'N');
                    sc.nextLine();
                }

                else
                {
                    System.out.println();
                    System.out.println("Patient's UID: " +PatientList.get(UIDindex).getPUI());
                    System.out.println("Name: "+ PatientList.get(UIDindex).getLname()+", "+PatientList.get(UIDindex).getFname()+" "+PatientList.get(UIDindex).getMname());
                    System.out.println("Birthday: "+PatientList.get(UIDindex).getBday());
                    System.out.println("Address: "+PatientList.get(UIDindex).getAddress());
                    System.out.println("Phone Number: "+PatientList.get(UIDindex).getPhoneNo());
                    System.out.println("National ID no.: "+PatientList.get(UIDindex).getNIDNo());

                    System.out.println();
                    System.out.println();

                    int printHeader = 0;
                    int TMP = 0;
                    int TMPserv = 0;
                    int foundCtr1 = 0;
                    char printPDF;

                    for (int g = 0; g < ReqList.size(); g++) {
                        if (ReqList.get(g).getPatientUID().equals(toSearchUID) && ReqList.get(g).getDelete() != 'D')
                        {
                            if (printHeader == 0) {
                                System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                printHeader = 1;
                            }

                            for (int x = 0; x < ServiceList.size(); x++) {
                                if (ServiceList.get(x).getServiceCode().equals(ReqList.get(g).getRequestUID().substring(0, 3))) {
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(x).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                    TMP = g;
                                    foundCtr1++;
                                    TMPserv = x;
                                }
                            }
                        }
                    }


                    if (foundCtr1 == 0) {
                        System.out.println("No Laboratory Request Found.");
                        System.out.println();
                    }

                    if (foundCtr1!=0) {
                        System.out.println();
                        System.out.println();
                        do {
                            System.out.println("Do you want to print a laboratory test result? [Y/N]: ");
                            printPDF = sc.next().charAt(0);
                            if (printPDF != 'Y' && printPDF != 'N') {
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        } while (printPDF != 'Y' && printPDF != 'N');
                        sc.nextLine();


                        do {
                            if (printPDF == 'Y') {
                                String RIDtoSearch;

                                if (foundCtr1 == 1) {
                                    System.out.println();
                                    System.out.println("Record Breakdown: ");
                                    System.out.println(ReqList.get(TMP).getRequestUID());
                                    System.out.println(ReqList.get(TMP).getPatientUID());
                                    System.out.println(ReqList.get(TMP).getRequestDate());
                                    System.out.println(ReqList.get(TMP).getRequestTime());
                                    System.out.println(ReqList.get(TMP).getResult());

                                    generatePDF(PatientList, UIDindex, ReqList, TMP);
                                }
//                            else if (foundCtr1 == 0) {
//                                System.out.println("No Record Found.");
//                                printPDF = 'N';
//                            }
                                else if (foundCtr1 > 1) {
                                    System.out.println("Enter the Request's UID you want to its result to be edited: ");
                                    RIDtoSearch = sc.nextLine();

                                    boolean foundRIDflagEDIT = false;
                                    int RIDindex = 0;

                                    for (int g = 0; g < ReqList.size(); g++) {
                                        if (ReqList.get(g).getRequestUID().equals(RIDtoSearch) && ReqList.get(g).getDelete() != 'D') {
                                            if (!foundRIDflagEDIT) {
                                                System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                                System.out.println(TMPserv);
                                                System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(TMPserv).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                                foundRIDflagEDIT = true;
                                                RIDindex = g;
                                            }
                                        }
                                    }

                                    if (foundRIDflagEDIT) {
                                        System.out.println();
                                        System.out.println("Record Breakdown: ");
                                        System.out.println(ReqList.get(RIDindex).getRequestUID());
                                        System.out.println(ReqList.get(RIDindex).getPatientUID());
                                        System.out.println(ReqList.get(RIDindex).getRequestDate());
                                        System.out.println(ReqList.get(RIDindex).getRequestTime());
                                        System.out.println(ReqList.get(RIDindex).getResult());

                                        generatePDF(PatientList, UIDindex, ReqList, RIDindex);
                                    } else {
                                        System.out.println("No Record Found.");
                                    }
                                }

                                do {
                                    System.out.println("Do you want to print laboratory request again? (Y/N)");
                                    printPDF = sc.next().charAt(0);
                                    if (printPDF != 'Y' && printPDF != 'N') {
                                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                        System.out.println();
                                    }
                                } while (printPDF != 'Y' && printPDF != 'N');
                                sc.nextLine();
                            }
                        } while (printPDF == 'Y');
                    }

                    do {
                        System.out.println("Do you want to search patient again? (Y/N)");
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
                char KnowNID;
                do{
                    System.out.println("Do you know the National ID no.?(Y/N)");
                    KnowNID = sc.next().charAt(0);
                    if (KnowNID != 'Y' && KnowNID != 'N'){
                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                        System.out.println();
                    }
                } while(KnowNID != 'Y' && KnowNID != 'N');
                sc.nextLine();

                if (KnowNID=='Y') //NID
                {
                    System.out.println("Enter National ID no.: ");
                    String toSearchNID = sc.nextLine();

                    for (int c=0;c <PatientList.size();c++) {
                        if ((toSearchNID).equals(PatientList.get(c).getNIDNo()) && PatientList.get(c).getDelete() != 'D')
                        {
                            UIDindex = c;
                            System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(c).getPUI(), PatientList.get(c).getLname(), PatientList.get(c).getFname(), PatientList.get(c).getMname(), PatientList.get(c).getBday(), PatientList.get(c).getGender(), PatientList.get(c).getAddress(), PatientList.get(c).getPhoneNo(), PatientList.get(c).getNIDNo());
                            NIDfoundctr = 1;
                        }
                    }

                    if (NIDfoundctr == 0)
                    {
                        do {
                            System.out.println("The UID ("+toSearchNID+") was not found or was deleted");
                            System.out.println("Do you want to try again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    else
                    {
                        char printPDF;
                        System.out.println();
                        System.out.println("Patient's UID: " +PatientList.get(UIDindex).getPUI());
                        System.out.println("Name: "+ PatientList.get(UIDindex).getLname()+", "+PatientList.get(UIDindex).getFname()+" "+PatientList.get(UIDindex).getMname());
                        System.out.println("Birthday: "+PatientList.get(UIDindex).getBday());
                        System.out.println("Address: "+PatientList.get(UIDindex).getAddress());
                        System.out.println("Phone Number: "+PatientList.get(UIDindex).getPhoneNo());
                        System.out.println("National ID no.: "+PatientList.get(UIDindex).getNIDNo());

                        System.out.println();
                        System.out.println();

                        int printHeader = 0;
                        int foundCtr1 = 0;
                        int TMP = 0;
                        int ServiceIndex=-1;


                        for (int g = 0; g < ReqList.size(); g++) {
                            if (ReqList.get(g).getPatientUID().equals(PatientList.get(UIDindex).getPUI()) && ReqList.get(g).getDelete() != 'D') {
                                if (printHeader == 0) {
                                    System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                    printHeader = 1;
                                }

                                for (int i = 0; i < ServiceList.size(); i++) {
                                    if (ServiceList.get(i).getServiceCode().equals(ReqList.get(g).getRequestUID().substring(0, 3))) {
                                        System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(i).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                        TMP = g;
                                        foundCtr1++;
                                        ServiceIndex = i;
                                    }
                                }
                            }
                        }

                        if (foundCtr1 == 0) {
                            System.out.println("No Record Found.");
                            System.out.println();
                        }

                        else {
                            System.out.println();
                            System.out.println();
                            do {
                                System.out.println("Do you want to print a laboratory test result? [Y/N]: ");
                                printPDF = sc.next().charAt(0);
                                if (printPDF != 'Y' && printPDF != 'N') {
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            } while (printPDF != 'Y' && printPDF != 'N');
                            sc.nextLine();


                            do {
                                if (printPDF == 'Y') {
                                    String RIDtoSearch;

                                    if (foundCtr1 == 1) {
                                        System.out.println();
                                        System.out.println("Record Breakdown: ");
                                        System.out.println(ReqList.get(TMP).getRequestUID());
                                        System.out.println(ReqList.get(TMP).getPatientUID());
                                        System.out.println(ReqList.get(TMP).getRequestDate());
                                        System.out.println(ReqList.get(TMP).getRequestTime());
                                        System.out.println(ReqList.get(TMP).getResult());

                                        generatePDF(PatientList, UIDindex, ReqList, TMP);
                                    }
//                                    else if (foundCtr1 == 0) {
//                                        System.out.println("No Record Found.");
//                                        printPDF = 'N';
//                                    }
                                    else if (foundCtr1 > 1) {
                                        System.out.println("Enter the Request's UID you want to its result to be edited: ");
                                        RIDtoSearch = sc.nextLine();

                                        boolean foundRIDflagEDIT = false;
                                        int RIDindex = 0;

                                        for (int g = 0; g < ReqList.size(); g++) {
                                            if (ReqList.get(g).getRequestUID().equals(RIDtoSearch) && ReqList.get(g).getDelete() != 'D') {
                                                for (int j = 0; j < ServiceList.size(); j++) {

                                                    if (!foundRIDflagEDIT) {
                                                        System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                                        System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(g).getRequestUID(), ServiceList.get(ServiceIndex).getServiceDescription(), ReqList.get(g).getRequestDate(), ReqList.get(g).getResult());
                                                        foundRIDflagEDIT = true;
                                                        RIDindex = g;
                                                    }
                                                }
                                            }
                                        }

                                        if (foundRIDflagEDIT) {
                                            System.out.println();
                                            System.out.println("Record Breakdown: ");
                                            System.out.println(ReqList.get(RIDindex).getRequestUID());
                                            System.out.println(ReqList.get(RIDindex).getPatientUID());
                                            System.out.println(ReqList.get(RIDindex).getRequestDate());
                                            System.out.println(ReqList.get(RIDindex).getRequestTime());
                                            System.out.println(ReqList.get(RIDindex).getResult());

                                            generatePDF(PatientList, UIDindex, ReqList, RIDindex);
                                        } else {
                                            System.out.println("No Record Found.");
                                        }
                                    }
                                    do {
                                        System.out.println("Do you want to print laboratory request again? (Y/N)");
                                        printPDF = sc.next().charAt(0);
                                        if (printPDF != 'Y' && printPDF != 'N') {
                                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                            System.out.println();
                                        }
                                    } while (printPDF != 'Y' && printPDF != 'N');
                                    sc.nextLine();
                                }
                            } while (printPDF == 'Y');
                        }
                        do {
                            System.out.println("Do you want to search patient again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }
                }

                else //Names and Birthdays
                {
                    System.out.println("Enter Last name: ");
                    String toSearchLname = sc.nextLine();
                    System.out.println("Enter First name: ");
                    String toSearchFname = sc.nextLine();
                    System.out.println("Enter Birthday: ");
                    String toSearchBday = sc.nextLine();

                    int headerctr = 0;

                    for (int x=0; x<PatientList.size();x++)
                    {
                        if ((toSearchLname.equals(PatientList.get(x).getLname())) && (toSearchFname.equals(PatientList.get(x).getFname())) && (toSearchBday.equals(PatientList.get(x).getBday())) && PatientList.get(x).getDelete() != 'D')
                        {
                            foundctr2++;
                            if (headerctr == 0) {
                                System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");
                                headerctr++;
                            }
                            System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(x).getPUI(), PatientList.get(x).getLname(), PatientList.get(x).getFname(), PatientList.get(x).getMname(), PatientList.get(x).getBday(), PatientList.get(x).getGender(), PatientList.get(x).getAddress(), PatientList.get(x).getPhoneNo(), PatientList.get(x).getNIDNo());
                            UIDindex = x;
                        }
                    }
                    System.out.println();

                    if (foundctr2 == 1) {
                        char printPDF;
                        System.out.println();
                        System.out.println("Patient's UID: " + PatientList.get(UIDindex).getPUI());
                        System.out.println("Name: " + PatientList.get(UIDindex).getLname() + ", " + PatientList.get(UIDindex).getFname() + " " + PatientList.get(UIDindex).getMname());
                        System.out.println("Birthday: " + PatientList.get(UIDindex).getBday());
                        System.out.println("Address: " + PatientList.get(UIDindex).getAddress());
                        System.out.println("Phone Number: " + PatientList.get(UIDindex).getPhoneNo());
                        System.out.println("National ID no.: " + PatientList.get(UIDindex).getNIDNo());
                        System.out.println();
                        System.out.println();

                        int ReqINDEX = 0;
                        int toPrint = 0;
                        int serviceINDEX=0;
                        for (int y = 0; y < ReqList.size(); y++) {
                            if (ReqList.get(y).getPatientUID().equals(PatientList.get(UIDindex).getPUI()) && ReqList.get(y).getDelete()!='D') {
                                System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                for (int i=0; i < ServiceList.size();i++)
                                {
                                    if (ServiceList.get(i).getServiceCode().equals(ReqList.get(y).getRequestUID().substring(0,3)))
                                        serviceINDEX = i;
                                }
                                System.out.printf("%-15s %-25s %-15s %-15s %n", ReqList.get(y).getRequestUID(), ServiceList.get(serviceINDEX).getServiceDescription(), ReqList.get(y).getRequestDate(), ReqList.get(y).getResult());
                                ReqINDEX = y;
                                toPrint++;
                                break;
                            }
                        }
                        if (toPrint == 0) {
                            System.out.println("No Record Found");
                        }

                        else {
                            System.out.println();
                            System.out.println();
                            do {
                                System.out.println("Do you want to print a laboratory test result? [Y/N]: ");
                                printPDF = sc.next().charAt(0);
                                if (printPDF != 'Y' && printPDF != 'N') {
                                    System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                    System.out.println();
                                }
                            } while (printPDF != 'Y' && printPDF != 'N');
                            sc.nextLine();

                            do {
                                if (printPDF == 'Y') {
                                    generatePDF(PatientList, UIDindex, ReqList, ReqINDEX);
                                     do {
                                         System.out.println("Do you want to print laboratory request again? (Y/N)");
                                         printPDF = sc.next().charAt(0);
                                         if (printPDF != 'Y' && printPDF != 'N') {
                                             System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                             System.out.println();
                                         }
                                     } while (printPDF != 'Y' && printPDF != 'N');
                                     sc.nextLine();
                                }
                            } while (printPDF == 'Y');
                        }
                    }

                    if (foundctr2 > 1) //multiple same names
                    {
                        char printPDF='Y';
                        do {
                            System.out.println("Enter the patient's UID that you want to display: ");
                            toSearchUID = sc.nextLine();
                            int foundctr = 0;
                            for (int x=0; x<PatientList.size();x++) {
                                if (toSearchUID.equals(PatientList.get(x).getPUI()) && PatientList.get(x).getDelete() != 'D') {
                                    System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", "Patient's UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address", "Phone Number", "National ID Number");
                                    System.out.printf("%-15s %-10s %-20s %-20s %-20s %-8s %-30s %-15s %-20s %n", PatientList.get(x).getPUI(), PatientList.get(x).getLname(), PatientList.get(x).getFname(), PatientList.get(x).getMname(), PatientList.get(x).getBday(), PatientList.get(x).getGender(), PatientList.get(x).getAddress(), PatientList.get(x).getPhoneNo(), PatientList.get(x).getNIDNo());
                                    UIDindex=x;
                                    foundctr++;
                                }
                            }

                            if (foundctr == 0)
                            {
                                do{
                                    System.out.println("The UID ("+toSearchUID+") was not found");
                                    System.out.println("Do you want to try again? (Y/N)");
                                    again = sc.next().charAt(0);
                                    if (again != 'Y' && again != 'N'){
                                        System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                        System.out.println();
                                    }
                                }while(again != 'Y' && again != 'N');
                                sc.nextLine();
                            }

                            if (foundctr == 1) {
                                System.out.println();
                                System.out.println("Patient's UID: " +PatientList.get(UIDindex).getPUI());
                                System.out.println("Name: "+ PatientList.get(UIDindex).getLname()+", "+PatientList.get(UIDindex).getFname()+" "+PatientList.get(UIDindex).getMname());
                                System.out.println("Birthday: "+PatientList.get(UIDindex).getBday());
                                System.out.println("Address: "+PatientList.get(UIDindex).getAddress());
                                System.out.println("Phone Number: "+PatientList.get(UIDindex).getPhoneNo());
                                System.out.println("National ID no.: "+PatientList.get(UIDindex).getNIDNo());

                                System.out.println();
                                System.out.println();
                                int printheader=0;
                                int serviceINDEX=0;
                                int hasLabReq=0;
                                for (LabRequest labRequest : ReqList) {
                                    if (labRequest.getPatientUID().equals(PatientList.get(UIDindex).getPUI()) && labRequest.getDelete() != 'D') {
                                        for (int j = 0; j < ServiceList.size(); j++) {
                                            if (ServiceList.get(j).getServiceCode().equals(labRequest.getRequestUID().substring(0, 3))) {
                                                serviceINDEX = j;
                                            }
                                        }
                                        if (printheader == 0) {
                                            System.out.printf("%-15s %-25s %-15s %-15s %n", "Request’s UID", "Lab Test Type", "Request Date", "Result");
                                            printheader = 1;
                                        }
                                        System.out.printf("%-15s %-25s %-15s %-15s %n", labRequest.getRequestUID(), ServiceList.get(serviceINDEX).getServiceDescription(), labRequest.getRequestDate(), labRequest.getResult());
                                        hasLabReq++;
                                    }
                                }

                                if (hasLabReq==0)
                                {
                                    System.out.println("No Laboratory Request Found.");
                                    printPDF = 'N';
                                    System.out.println();
                                    System.out.println();
                                }

                                else {
                                    System.out.println();
                                    System.out.println();
                                    do {
                                        System.out.println("Do you want to print a laboratory test result? [Y/N]: ");
                                        printPDF = sc.next().charAt(0);
                                        if (printPDF != 'Y' && printPDF != 'N') {
                                            System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                            System.out.println();
                                        }
                                    } while (printPDF != 'Y' && printPDF != 'N');
                                    sc.nextLine();


                                    int ReqListctr;
                                    int pdfSuccess = 0;
                                    if (printPDF == 'Y') {
                                        for (int y = 0; y < ReqList.size(); y++) {
                                            if ((ReqList.get(y).getPatientUID().equals(PatientList.get(UIDindex).getPUI())) && ReqList.get(y).getDelete() != 'D') {
                                                ReqListctr = y;
                                                generatePDF(PatientList, UIDindex, ReqList, ReqListctr);
                                                pdfSuccess = 1;
                                                break;
                                            }
                                        }
                                        if (pdfSuccess == 0) {
                                            System.out.println("No Request Found.");
                                            printPDF = 'N';
                                        }
                                    }
                                    if (pdfSuccess != 0) {
                                        do {
                                            System.out.println("Do you want to print laboratory request again? (Y/N)");
                                            printPDF = sc.next().charAt(0);
                                            if (printPDF != 'Y' && printPDF != 'N') {
                                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                                System.out.println();
                                            }
                                        } while (printPDF != 'Y' && printPDF != 'N');
                                        sc.nextLine();
                                    }
                                }
                            }
                        }while (printPDF=='Y');
                    }

                    if (foundctr2 == 0) //not found
                    {
                        do{
                            System.out.println("The First Name (" + toSearchFname+ "), Last Name ("+ toSearchLname+") and Birthday Combination ("+toSearchBday+") was not found or was deleted.");
                            System.out.println("Do you want to search again? (Y/N)");
                            again = sc.next().charAt(0);
                            if (again != 'Y' && again != 'N'){
                                System.out.println("Invalid Input (not Y or N). Please enter valid input (Y/N).");
                                System.out.println();
                            }
                        }while (again != 'Y' && again != 'N');
                        sc.nextLine();
                    }

                    else
                    {
                        do {
                            System.out.println("Do you want to search patient again? (Y/N)");
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

    public static void sortDescending(ArrayList<LabRequest> ReqList)
    {
        for (int p = 0; p < ReqList.size(); p++)
            ReqList.sort((o1, o2) -> (o2.getRequestUID()).compareTo(o1.getRequestUID()));
    }

    public static void generatePDF (ArrayList<Patient> PatientList, int UIDindex, ArrayList<LabRequest> ReqList, int TMP)
    {
        Document document = new Document();
        String filename = PatientList.get(UIDindex).getLname() + "_" + ReqList.get(TMP).getRequestUID() + "_" + ReqList.get(TMP).getRequestDate()+".pdf";
        String dir = System.getProperty("user.dir");

        try {
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(dir+"\\PDFs\\"+filename));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        //Logo
        Image logo = null;
        try {
            try {
                logo = Image.getInstance(dir+"\\logo.png");
            } catch (BadElementException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (logo != null) {
            logo.scaleAbsolute(80f, 80f);
        }
        if (logo != null) {
            logo.scalePercent(5);
        }
        if (logo != null) {
            logo.setAlignment(Element.ALIGN_CENTER);
        }
        try {
            document.add(logo);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //Address
        Paragraph Address = new Paragraph("5792 W. JEFFERSON BLVD. LOS ANGELES CA 90045");
        Address.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(Address);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //Phone Number
        Paragraph PhoneNo = new Paragraph("4242980196");
        PhoneNo.setAlignment(Element.ALIGN_CENTER);
        try {
            document.add(PhoneNo);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //Table for Info
        PdfPTable Info = new PdfPTable(2);
        Info.setWidthPercentage(90f);

        //name
        PdfPCell cell1 = new PdfPCell(new Phrase("Name: "+PatientList.get(UIDindex).getLname()+", "+PatientList.get(UIDindex).getFname()+" "+PatientList.get(UIDindex).getMname()));
        cell1.setBorder(Rectangle.TOP);
        Info.addCell(cell1);

        //rui
        PdfPCell cell2 = new PdfPCell(new Phrase("Specimen ID: "+ReqList.get(TMP).getRequestUID()));
        cell2.setBorder(Rectangle.TOP);
        Info.addCell(cell2);

        //PUI
        PdfPCell cell3 = new PdfPCell(new Phrase("Patient ID: "+PatientList.get(UIDindex).getPUI()));
        cell3.setBorder(Rectangle.NO_BORDER);
        Info.addCell(cell3);

        //Req Date
        PdfPCell cell4 = new PdfPCell(new Phrase("Collection Date: "+ReqList.get(TMP).getRequestDate()));
        cell4.setBorder(Rectangle.NO_BORDER);
        Info.addCell(cell4);

        //Age
        Format M = new SimpleDateFormat("MM");
        Format Y = new SimpleDateFormat("yyyy");
        Format D = new SimpleDateFormat("dd");

        int age = (Integer.parseInt(Y.format(new Date()) + M.format(new Date()) + D.format(new Date()))-Integer.parseInt(PatientList.get(UIDindex).getBday()))/10000;
        PdfPCell cell5 = new PdfPCell(new Phrase("Age: "+age));
        cell5.setBorder(Rectangle.NO_BORDER);
        Info.addCell(cell5);

        //Birthday
        PdfPCell cell6 = new PdfPCell(new Phrase("Birthday: "+PatientList.get(UIDindex).getBday()));
        cell6.setBorder(Rectangle.NO_BORDER);
        Info.addCell(cell6);

        //Gender
        PdfPCell cell7 = new PdfPCell(new Phrase("Gender: "+PatientList.get(UIDindex).getGender()));
        cell7.setBorder(Rectangle.BOTTOM);
        Info.addCell(cell7);

        //Phone Num
        PdfPCell cell8 = new PdfPCell(new Phrase("Phone Number: "+PatientList.get(UIDindex).getPhoneNo()));
        cell8.setBorder(Rectangle.BOTTOM);
        Info.addCell(cell8);

        Info.completeRow();
        try {
            document.add(Info);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //Test and Result
        PdfPTable TestResult = new PdfPTable(2);
        TestResult.setWidthPercentage(80f);

        //BOLD test
        Chunk test = new Chunk("Test");
        test.getFont().setStyle(Font.BOLD);
        PdfPCell cell9 = new PdfPCell(new Phrase(test));
        TestResult.addCell(cell9);

        //BOLD result
        Chunk result = new Chunk("Result");
        result.getFont().setStyle(Font.BOLD);
        PdfPCell cell10 = new PdfPCell(new Phrase(result));
        TestResult.addCell(cell10);

        //test
        PdfPCell cell11 = new PdfPCell(new Phrase(ReqList.get(TMP).getRequestUID().substring(0,3)));
        TestResult.addCell(cell11);

        //result
        PdfPCell cell12 = new PdfPCell(new Phrase(ReqList.get(TMP).getResult()));
        TestResult.addCell(cell12);

        //blank spaces
        PdfPCell cell13 = new PdfPCell(new Phrase(" "));
        PdfPCell cell14 = new PdfPCell(new Phrase(" "));
        PdfPCell cell15 = new PdfPCell(new Phrase(" "));
        PdfPCell cell16 = new PdfPCell(new Phrase(" "));
        PdfPCell cell17 = new PdfPCell(new Phrase(" "));
        PdfPCell cell18 = new PdfPCell(new Phrase(" "));
        cell13.setBorder(Rectangle.NO_BORDER);
        cell14.setBorder(Rectangle.NO_BORDER);
        cell15.setBorder(Rectangle.NO_BORDER);
        cell16.setBorder(Rectangle.NO_BORDER);
        cell17.setBorder(Rectangle.BOTTOM);
        cell18.setBorder(Rectangle.BOTTOM);
        TestResult.addCell(cell13);
        TestResult.addCell(cell14);
        TestResult.addCell(cell15);
        TestResult.addCell(cell16);
        TestResult.addCell(cell17);
        TestResult.addCell(cell18);

        //Testing Professionals
        PdfPCell cell19 = new PdfPCell(new Phrase(" "));
        PdfPCell cell20 = new PdfPCell(new Phrase(" "));
        PdfPCell cell21 = new PdfPCell(new Phrase(" "));
        PdfPCell cell22 = new PdfPCell(new Phrase(" "));
        PdfPCell cell23 = new PdfPCell(new Phrase(" "));
        PdfPCell cell24 = new PdfPCell(new Phrase(" "));
        cell19.setBorder(Rectangle.NO_BORDER);
        cell20.setBorder(Rectangle.NO_BORDER);
        cell21.setBorder(Rectangle.NO_BORDER);
        cell22.setBorder(Rectangle.NO_BORDER);
        cell23.setBorder(Rectangle.NO_BORDER);
        cell24.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell19);
        TestResult.addCell(cell20);
        TestResult.addCell(cell21);
        TestResult.addCell(cell22);
        TestResult.addCell(cell23);
        TestResult.addCell(cell24);

        //Professional Name
        PdfPCell cell25 = new PdfPCell(new Phrase("Alyanna Cabrera"));
        cell25.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell25);
        PdfPCell cell26 = new PdfPCell(new Phrase("Wayne Tam"));
        cell26.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell26);

        //Position
        PdfPCell cell27 = new PdfPCell(new Phrase("Medical Technologist"));
        cell27.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell27);
        PdfPCell cell28 = new PdfPCell(new Phrase("Pathologist"));
        cell28.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell28);

        //License No
        PdfPCell cell29 = new PdfPCell(new Phrase("Lic.# 987654321"));
        cell29.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell29);
        PdfPCell cell30 = new PdfPCell(new Phrase("Lic.# 123456789"));
        cell30.setBorder(Rectangle.NO_BORDER);
        TestResult.addCell(cell30);

        TestResult.completeRow();
        try {
            document.add(TestResult);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
        System.out.println(filename + " has been saved to "+dir+"\\PDFs");
    }
}
