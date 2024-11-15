/**
 * Converts the given age (in years, months, and days) to a date of birth 
 * based on a reference date.
 * 
 * @param age The age in years, months, and days (in the format "YY-MM-DD").
 * @param referenceDate The reference date in the format "YYYY-MM-DD".
 * @since 1.0
 */

public class Age {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Type This ; java Age <DOB/AGE> <Reference Date> <Date Format> <Delimiter>");
            return;
        }

        String input = args[0];
        String referenceDate = args[1];
        String dateFormat = args[2]; 
        String delimiter = args[3];

        // Extract the type (DOB or AGE) and value from the input
        String[] inputParts = input.split("=");
        if (inputParts.length != 2) {
            System.out.println("Invalid input format. Use 'DOB=YYYY-MM-DD' or 'AGE=YY-MM-DD'");
            return;
        }

        String type = inputParts[0].trim();
        String value = inputParts[1].replace(delimiter, "-").trim();

        // Process DOB or AGE based on the type
        if ("DOB".equals(type)) {
            AgeToDOB(value, referenceDate);
        } else if ("AGE".equals(type)) {
            DOBToAge(value, referenceDate);
        } else {
            System.out.println("Invalid input. Use either 'DOB' or 'AGE'.");
        }
    }

    public static void AgeToDOB(String dob, String referenceDate) {
        try {
            // Split the dates into year, month, and day
            String[] dobParts = dob.split("-");
            String[] refParts = referenceDate.split("-");

            int birthYear = Integer.parseInt(dobParts[0]);
            int birthMonth = Integer.parseInt(dobParts[1]);
            int birthDay = Integer.parseInt(dobParts[2]);

            int refYear = Integer.parseInt(refParts[0]);
            int refMonth = Integer.parseInt(refParts[1]);
            int refDay = Integer.parseInt(refParts[2]);

            // Calculate the age in years, months, and days
            int years = refYear - birthYear;
            int months = refMonth - birthMonth;
            int days = refDay - birthDay;

            // Adjust the months and years if the day or month is negative
            if (days < 0) {
                months--;
                days += getDaysInMonth(refMonth - 1, refYear);  // Fix month calculation to subtract days properly
            }

            if (months < 0) {
                years--;
                months += 12;  // Adjust month back into the range
            }

            System.out.printf("Age: %d years, %d months, %d days%n", years, months, days);

        } catch (Exception e) {
            System.out.println("Invalid date format or value. Please use YYYY-MM-DD format.");
        }
    }

    public static void DOBToAge(String age, String referenceDate) {
        try {
            String[] ageParts = age.split("-");
            int years = Integer.parseInt(ageParts[0]);
            int months = Integer.parseInt(ageParts[1]);
            int days = Integer.parseInt(ageParts[2]);

            String[] refParts = referenceDate.split("-");
            int refYear = Integer.parseInt(refParts[0]);
            int refMonth = Integer.parseInt(refParts[1]);
            int refDay = Integer.parseInt(refParts[2]);

            // Adjust the year, month, and day for the date of birth calculation
            int dobYear = refYear - years;
            int dobMonth = refMonth - months;
            int dobDay = refDay - days;

            // Handle the month and day adjustments
            if (dobDay < 0) {
                dobMonth--;
                dobDay += getDaysInMonth(refMonth - 1, refYear);  // Adjust the day by getting the correct number of days in the previous month
            }

            if (dobMonth < 0) {
                dobYear--;
                dobMonth += 12;  // Adjust the month by adding 12 to wrap around to the correct previous year
            }

            System.out.printf("Date of Birth: %d-%02d-%02d%n", dobYear, dobMonth, dobDay);

        } catch (Exception e) {
            System.out.println("Invalid age format or value. Please follow YY-MM-DD for age and YYYY-MM-DD for date.");
        }
    }

    // Helper method to get the number of days in a month
    private static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                // Leap year check
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            default:
                return 0;  
        }
    }
}
