package bank;

import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Szymon Szulkowski
 * Project Name: Zadanie Rekrutacyjne Java
 * Created for: Currenda Sp. z o.o.
 * Main Module
 */

public class Main {

    public static void main(String[] args) {

        String code;
        String begDate;
        String endDate;
        System.out.println("Type \"help\" for help or make a request");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            code = scanner.next();
            if(Objects.equals(code, "help")) {
                showHelp();
            }
            else if(Objects.equals(code, "exit")){
                break;
            }
            else{
                if(code.length() == 3){
                    begDate = scanner.next();
                    endDate = scanner.next();
                    Currency currency = new Currency(code, begDate, endDate);
                    currency.print();
                }
                else {
                    System.out.println("Country Code must be 3 letters long");
                }

            }
        }
    }

    private static void showHelp(){
        System.out.println("Type: \"exit\" to exit a program");
        System.out.println("Format of a request must be similar to this: EUR 2017-11-20 2017-11-24");
        System.out.println("Where \"EUR\" is a country code, \"2017-11-20\" is a date when the request begins and \" 2017-11-24\" is a date when a request ends");
        System.out.println("Country code must me 3 letters long");
        System.out.println("You can check country codes list here: https://www.currency-iso.org/dam/downloads/lists/list_one.xml");
        System.out.println("Date must be written like: yyyy-MM-dd");
    }
}
