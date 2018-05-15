package bank;

import javax.xml.parsers.ParserConfigurationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Szymon Szulkowski
 * Project Name: Zadanie Rekrutacyjne Java
 * Created for: Currenda Sp. z o.o.
 * Currency Module
 */

public class Currency {
    private float avgBidingRate = 0;
    private float AskDeviation = 0;
    private String code = null;
    private Date beginning = null;
    private Date end = null;
    private long difference = 0;
    private TwoFloatStruct[] rates = null;


    public Currency(String codeName){
        code = codeName;
        // TODO Add function asking for dates
    }
    public Currency(String codeName, Date beginningDate, Date endDate){
        code = codeName;
        beginning = beginningDate;
        end = endDate;
    }

    public Currency(String codeName, String beginningDate, String endDate){
        code = codeName;
        beginning = parseDate(beginningDate);
        end = parseDate(endDate);
        if(end != null && beginning != null) {
            difference = countDifference() + 1;
            rates = checkRates();
            if (rates != null) {
                avgBidingRate = countAvgRate(true);
                AskDeviation = countDeviation(false);
            }
        }
    }

    private Date parseDate(String dateToParse){
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(dateToParse);
        } catch (ParseException e) {
            System.out.println("Probably typo in date, try again");
        }
        return date;
    }

    private TwoFloatStruct[] checkRates(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Parser parser = new Parser(code, dateFormat.format(beginning),dateFormat.format(end), difference);
        try {
            return parser.getRates();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
    private float countAvgRate(boolean toBid){
        float sum = 0;
        for (TwoFloatStruct x: rates) {
            if(toBid)
                sum += x.bid;
            else
                sum += x.ask;
        }
        return sum/rates.length;
    }

    private float countDeviation( boolean toBid){
        float variance = 0;
        float avgRate =  countAvgRate(toBid);
        for (TwoFloatStruct x: rates) {
            variance += Math.pow(x.ask - avgRate, 2);
        }
        return (float) Math.sqrt(variance/rates.length);
    }

    private long countDifference(){
        long diff = end.getTime() - beginning.getTime();
        return diff/1000/60/60/24;
    }

    public void print(){
        if(avgBidingRate != 0 && AskDeviation != 0){
            System.out.println(avgBidingRate);
            System.out.println(AskDeviation);
        }
        else
            System.out.println("Error occurred, try again");
    }
}
