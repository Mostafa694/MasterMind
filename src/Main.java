//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MasterMind.startGame();
    }
}

class MasterMind {

    private static int readRandomNum(int from, int to) {
        return ((int) (Math.random() * Integer.MAX_VALUE) % (to - from + 1) + from);
    }

    private static String returnColor(int num) {

        switch (num) {
            case 1:
                return "red";
            case 2:
                return "green";
            case 3:
                return "blue";
            case 4:
                return "yellow";
            case 5:
                return "orange";
        }
        return null;
    }

    private static String[] fillArrayWithRandom(int n) {
        String colors[] = new String[n];
        for (int i = 0; i < n; i++) {
            colors[i] = returnColor(readRandomNum(1, 5)).toLowerCase();
        }
        return colors;
    }

    private static String[] readColors(int n) {
        String colors[] = new String[n];
        Scanner in = new Scanner(System.in);
        System.out.println("Write the colors with an arrangement "
                + "from 5 colors [Red , Green ,Blue , Yellow , Orange]");
        String color = "";
        String repeatGuess;
        for (int i = 0; i < n; i++) {
            do {
                System.out.print("Enter the Color" + (i + 1) + " : ");
                color = in.next().toLowerCase();
                boolean invalidInput = false;
                do {
                    System.out.print("Do you want to submit this color surely ? : [Y/N] : ");
                    repeatGuess = in.next().toLowerCase();
                    if (!(repeatGuess.equals("n")) && !(repeatGuess.equals("y"))) {
                        System.out.println("Invalid Input , try again!");
                        invalidInput = true;
                    } else {
                        invalidInput = false;
                    }
                } while (invalidInput);
                if (repeatGuess.equals("n")) {
                    System.out.println("No problem , The Process Will be repeated\n");
                }

            } while (repeatGuess.equals("n"));
            colors[i] = color;
            if (!(colors[i].equals("red") || colors[i].equals("blue") || colors[i].equals("green")
                    || colors[i].equals("yellow") || colors[i].equals("orange"))) {
                i--;
                System.out.println("There is no color Like that , please try again : ");
            }
        }
        return colors;
    }

    private static boolean isBlackCircle(String randomArray[], String readArray[], int index) {

        if (randomArray[index].equals(readArray[index])) {
            return true;
        }
        return false;
    }

    private static boolean numsOfBlackCircle(String randomArray[], String readArray[]) {
        int numsOfBlackCircle = 0;
        for (int i = 0; i < randomArray.length; i++) {
            if (randomArray[i].equals(readArray[i])) {
                numsOfBlackCircle++;
            }
        }
        if (numsOfBlackCircle == randomArray.length) {
            System.out.println("Winner Winner , Chicken Dinner");
            return true;
        } else {
            System.out.println("Black Circles : " + numsOfBlackCircle);
            return false;
        }
    }

    private static void convertToTrue(String randomArray[] , boolean[] randomUsed , String str){
        for (int i = 0; i < randomArray.length; i++) {
            if (randomArray[i].equals(str)) {
                randomUsed[i] = true;
            }
        }
    }

    private static int numsOfWhiteCircle(String randomArray[], String readArray[]) {
        int numsOfWCircle = 0;

        int length = readArray.length;

        boolean[] randomUsed = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (randomArray[i].equals(readArray[i])) {
                randomUsed[i] = true;
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (readArray[i].equals(randomArray[j]) && !randomUsed[j]) {
                    numsOfWCircle++;
                    convertToTrue( randomArray ,  randomUsed ,  readArray[i]);
                    break;
                }
            }
        }

        return numsOfWCircle;
    }

    private static int readTheDifficulty(){
        Scanner in = new Scanner(System.in);
        System.out.println("================================================================================================================");
        System.out.println("                1:Easy[3 Positions] , 2:Medium[4 Positions] , 3:Hard[5 Positions] , All of 5 Colors ");
        System.out.println("================================================================================================================");
        String n;
        do {
            System.out.print("Enter The Number Of Level : ");
            n = in.next();
            if (n.length() != 1 || (n.charAt(0) != '1' && n.charAt(0) != '2' && n.charAt(0) != '3')) {
                System.out.println("Invalid Input , Please Try Again");
            }
        } while ((n.length() != 1 || (n.charAt(0) != '1' && n.charAt(0) != '2' && n.charAt(0) != '3')));
        System.out.println("================================================================================================================");
        return Integer.parseInt(n) ;
    }

    public static void startGame() {
        boolean restart = false ;
        int n = readTheDifficulty();
        n = (n == 1 ? 3 : n == 2 ? 4 : 5);
        String randomArray[] = fillArrayWithRandom(n);
        // this block of code for me only to check that the code works well
        System.out.println("This Output only for checking , in the real game it will be deleted");
        {
            for (int i = 0; i < randomArray.length; i++) {
                System.out.println(randomArray[i]);
            }
        }
        String numberOfTests = "";
        System.out.println("================================================================================================================");
        System.out.println("                            You have " + n + " Chances to Guess The Colors With Arrangement");
        System.out.println("================================================================================================================");
        for (int i = 0; i < n; i++) {
            numberOfTests = (i != n-1? "Test " + (i + 1):"Last Test");
            System.out.println("                        =============================================");
            System.out.println("                                          " +  numberOfTests );
            System.out.println("                        =============================================");
            String readArray[] = readColors(n);
            for (int j = 0; j < randomArray.length; j++) {
                if (isBlackCircle(randomArray, readArray, j)) {
                    System.out.println("Position " + (j + 1) + " is correct");
                }
            }
            if (numsOfBlackCircle(randomArray, readArray)) {
                restart = true ;
                break;
            } else {
                System.out.println("White Circles : " + numsOfWhiteCircle(randomArray, readArray ));
            }
        }
        if (!restart) {
            System.out.println("                            =============================================");
            System.out.println("                                             Game Over");
            System.out.println("                            =============================================");
            System.out.println("The Circles Were : \n");
            System.out.println("-----------------------------------------------------------------------------");
            for (int i = 0; i < randomArray.length; i++) {
                System.out.print("|    " + randomArray[i] + "    |");
            }
            System.out.println("\n-----------------------------------------------------------------------------");
        }
        Scanner in = new Scanner (System.in);
        String toContinue ;
        do {
            System.out.print("Do you want to play again ? , [Y/N] : ");
            toContinue = in.next().toLowerCase();
            if (!toContinue.equals("y") && !toContinue.equals("n")) {
                System.out.println("Invalid Input , Please Try Again!");
            }else if (toContinue.equals("n")) {
                showTheEnd();
                break;
            }
            else {
                startGame();
            }
        } while (!toContinue.equals("y") && !toContinue.equals("n"));
    }
    static void showTheEnd(){
        System.out.println("===========================================================");
        System.out.println("                     End Of The Game");
        System.out.println("===========================================================");

    }
}