import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Laboratory extends ManagePatientRecords {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char choice;
        ArrayList<Patient> PatientList = new ArrayList<>();
        ArrayList<Services> ServiceList = new ArrayList<>();
        ArrayList<LabRequest> ReqList = new ArrayList<>();

        //scanning Patient.txt
        File file = new File("Patients.txt");
        boolean value = false;
        try {
            value = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!value) {
            File patient = new File("Patients.txt");
            Scanner input = null;
            try {
                input = new Scanner(patient);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (Objects.requireNonNull(input).hasNextLine()) {
                String line = input.nextLine();

                String[] ctrPatient = line.split(";");
                if (line.contains(";D;")) {
                    PatientList.add(new Patient(ctrPatient[0], ctrPatient[1], ctrPatient[2], ctrPatient[3], ctrPatient[4], ctrPatient[5].charAt(0), ctrPatient[6], ctrPatient[7], ctrPatient[8], ctrPatient[9].charAt(0), ctrPatient[10]));
                } else {
                    PatientList.add(new Patient(ctrPatient[0], ctrPatient[1], ctrPatient[2], ctrPatient[3], ctrPatient[4], ctrPatient[5].charAt(0), ctrPatient[6], ctrPatient[7], ctrPatient[8]));
                }
            }
            input.close();
        }

        //scanning Services.txt
        file = new File("Services.txt");
        value = false;

        try {
            value = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!value) {
            File patient = new File("Services.txt");
            Scanner input = null;
            try {
                input = new Scanner(patient);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (Objects.requireNonNull(input).hasNextLine()) {
                String line = input.nextLine();

                String[] ctrServices = line.split(";");
                if (line.contains(";D;")) {
                    ServiceList.add(new Services(ctrServices[0], ctrServices[1], Integer.parseInt(ctrServices[2]), ctrServices[3].charAt(0), ctrServices[4]));
                } else {
                    ServiceList.add(new Services(ctrServices[0], ctrServices[1], Integer.parseInt(ctrServices[2])));
                }
            }
            input.close();
        }

        //scanning [service code]_Requests.txt
        if (ServiceList.size() != 0)
        {
            for (Services services : ServiceList) {
                File LABfile = new File(services.getServiceCode() + "_Requests.txt");
                boolean LABvalue = false;
                try {
                    LABvalue = LABfile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!LABvalue) {
                    String filename = services.getServiceCode() + "_Requests.txt";
                    File LABpatient = new File(filename);
                    Scanner LABinput = null;
                    try {
                        LABinput = new Scanner(LABpatient);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (LABinput != null) {
                        while (LABinput.hasNextLine()) {
                            String line = LABinput.nextLine();

                            String[] ctrPatient = line.split(";");
                            if (line.contains(";D;"))
                            {
                                ReqList.add(new LabRequest(ctrPatient[0], ctrPatient[1], ctrPatient[2], ctrPatient[3], ctrPatient[4], ctrPatient[5].charAt(0), ctrPatient[6]));
                            }
                            else {
                                ReqList.add(new LabRequest(ctrPatient[0], ctrPatient[1], ctrPatient[2], ctrPatient[3], ctrPatient[4]));
                            }
                        }
                    }
                    if (LABinput != null) {
                        LABinput.close();
                    }
                }
            }
        }

        //Main Menu
        do{
            do{
                System.out.println();
                System.out.println("Medical Laboratory Information System");
                System.out.println("[1] Manage Patient Records");
                System.out.println("[2] Manage Services");
                System.out.println("[3] Manage Laboratory Results");
                System.out.println("[X] End Program");
                System.out.println();
                System.out.println("Select a transaction: ");
                choice= sc.next().charAt(0);

                if (choice != '1' && choice != '2' && choice != '3' && choice != 'X')
                    System.out.println("Input is not in the choices. Please enter valid input.");
            }while (choice != '1' && choice != '2' && choice != '3' && choice != 'X');
            sc.nextLine();

            switch (choice)
            {
                case '1' -> ManagePatientRecord(PatientList, ServiceList, ReqList);
                case '2' -> ManageService(ServiceList);
                case '3' -> ManageLabResults(PatientList, ServiceList, ReqList);
            }
        } while(choice!='X');
    }
}
