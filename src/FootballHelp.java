import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

class Helpful {
    String [] trigger;
    String [] response;
}

public class FootballHelp {

    public static Helpful setTrigger(String[] trigger, Helpful record) {
        record.trigger = trigger;
        return record;
    }

    public static Helpful setResponse(String[] response, Helpful record) {
        record.response = response;
        return record;
    }

    public static String[] getTrigger(Helpful record) {
        return record.trigger;
    }

    public static String[] getResponse(Helpful record) {
        return record.response;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner (System.in);
        String filename = "";
        String run = "";
        final int LOOP = 3;

        System.out.println("=================================================");
        System.out.println("Welcome! I am a Dream League Soccer help-bot!");
        System.out.println("I can help in answering any questions you have :)");
        System.out.println("=================================================");

        System.out.print("\nWould you like to input data of triggers and responses (make your own new help-bot)? ");
        String yesData = scanner.nextLine();

        if (yesData.equals("Yes")) {
            System.out.print("What is the topic of these triggers/responses (help-bot)? ");
            filename = scanner.nextLine();

            PrintWriter outputStream = new PrintWriter(new FileWriter(filename + ".txt"));

            System.out.println("");
            for (int i=1; i <= LOOP; i++) {
                System.out.print("Please enter trigger word 1." + i + ": ");
                outputStream.println(scanner.nextLine());
            }
            saveFileResponse(outputStream);

            System.out.println("");
            for (int i=1; i <= LOOP; i++) {
                System.out.print("Please enter trigger word 2." + i + ": ");
                outputStream.println(scanner.nextLine());
            }
            saveFileResponse(outputStream);

            System.out.println("");
            for (int i=1; i <= LOOP; i++) {
                System.out.print("Please enter trigger word 3." + i + ": ");
                outputStream.println(scanner.nextLine());
            }
            saveFileResponse(outputStream);

            outputStream.close();

            System.out.println("\nType 0 to run newly created " + filename + " Helpbot\nType 1 to run pre-exiting Football Helpbot");
            run = scanner.nextLine();

            allRecords(run,filename);
        }

       allRecords(run,filename);
    }

    public static void saveFileResponse(PrintWriter outputStream) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        for (int i=1; i<=3; i++) {
            System.out.print("Please enter response word " + i + ", for those three triggers: ");
            outputStream.println(scanner.nextLine());
        }
    }

    public static void allRecords(String run, String filename) throws IOException {
        if (run.equals("0")) {
            BufferedReader inputStream = new BufferedReader(new FileReader(filename + ".txt"));

            String [] recordOneTriggers = new String[3];
            String [] recordOneResponses = new String[3];

            String [] recordTwoTriggers = new String[3];
            String [] recordTwoResponses = new String[3];

            String [] recordThreeTriggers = new String[3];
            String [] recordThreeResponses = new String[3];

            readFile(inputStream,recordOneTriggers,recordOneResponses);
            readFile(inputStream,recordTwoTriggers,recordTwoResponses);
            readFile(inputStream,recordThreeTriggers,recordThreeResponses);

            Helpful recordOne = createRecords(new String[]{recordOneTriggers[0],recordOneTriggers[1],recordOneTriggers[2]}, new String[]{recordOneResponses[0],recordOneResponses[1],recordOneResponses[2]});
            Helpful recordTwo = createRecords(new String[]{recordTwoTriggers[0],recordTwoTriggers[1],recordTwoTriggers[2]}, new String[]{recordTwoResponses[0],recordTwoResponses[1],recordTwoResponses[2]});
            Helpful recordThree = createRecords(new String[]{recordThreeTriggers[0],recordThreeTriggers[1],recordThreeTriggers[2]}, new String[]{recordThreeResponses[0],recordThreeResponses[1],recordThreeResponses[2]});

            inputStream.close();
            askQuestion(recordOne,recordTwo,recordThree);
        }

        else {
            Helpful recordOne = createRecords(new String[]{"tackle", "slide", "intercept"}, new String[]{"Press and Hold 'B', whilst moving the joystick towards the player you'd like to tackle.", "Press 'A' to slide tackle.", "Run into the player to tackle."});
            Helpful recordTwo = createRecords(new String[]{"pass", "give", "deliver"}, new String[]{"Press and Hold 'B', longer hold = power in pass.","Press and Hold 'C' to lob pass the ball.","Swipe towards the player to pass."});
            Helpful recordThree = createRecords(new String[]{"shoot", "hit", "score"}, new String[]{"Press and Hold 'A' for an air shot.","Press and Hold 'B' for a ground shot.","Press and Hold 'C' to lob the goalkeeper and score."});

            askQuestion(recordOne, recordTwo, recordThree);
        }
    }

    public static void readFile(BufferedReader inputStream, String[] recordTriggers, String[] recordResponses) throws IOException {
        for (int i=0; i<3; i++) {
            recordTriggers[i] = inputStream.readLine();
        }
        for (int i=0; i<3; i++) {
            recordResponses[i] = inputStream.readLine();
        }
    }


    public static Helpful createRecords(String[] trigger, String[] response) {
        Helpful f = new Helpful();

        f = setTrigger(trigger, f);
        f = setResponse(response, f);

        return f;
    }

    public static void askQuestion(Helpful recordOne, Helpful recordTwo, Helpful recordThree) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nPlease enter a question: ");
        String question = scanner.nextLine();//getting the question from user

        while (!question.equals("Bye")) {//loop to keep asking user for questions until input Bye

            answerQuestion(question, recordOne, recordTwo, recordThree);

            System.out.print("\nPlease enter another question? (If not just say Bye): "); //printing the answer for the question
            question = scanner.nextLine();//get the next question

        }
    }

    public static void answerQuestion(String question, Helpful recordOne, Helpful recordTwo, Helpful recordThree) {
        String [] recordOneTriggers = getTrigger(recordOne);
        String [] recordOneResponses = getResponse(recordOne);

        String [] recordTwoTriggers = getTrigger(recordTwo);
        String [] recordTwoResponses = getResponse(recordTwo);

        String [] recordThreeTriggers = getTrigger(recordThree);
        String [] recordThreeResponses = getResponse(recordThree);

        String [] allCurrentResponses = new String[3];

        //first runs the checkTriggers method for recordOne
        //if checkedAll equals to three that means the question did not have a recordOne trigger word.
        //therefore, if it is three, it'll run the checkTriggers method for recordTwo
        //Again if checkedAll equals to three that means the question did not have a recordTwo trigger word.
        //therefore runs the checkTriggers method for recordThree

        //However, for example if the question had a recordOne trigger, that means the checkedAll won't equal to 3, therefore those if statements won't run.
        //Same again, that if the question had a recordTwo trigger word, that means checkedAll won't equal to 3, therefore second if statement doesn't run.

        int checkedAll;
        checkedAll = checkRecordTriggers(question, allCurrentResponses, recordOneTriggers, recordOneResponses);

        if (checkedAll == 3) {
            checkedAll= checkRecordTriggers(question, allCurrentResponses, recordTwoTriggers, recordTwoResponses);

            if (checkedAll == 3) {
                checkedAll= checkRecordTriggers(question, allCurrentResponses, recordThreeTriggers, recordThreeResponses);
            }
        }

       if (checkedAll == 3) {
           System.out.println("I am sorry, but I don't have a response for that question.");
        }
       //After checking all of the trigger words for each record, if checkedAll still is three, that means the question contained no trigger words in any of the three records.
    }

    public static int checkRecordTriggers(String question, String[]allCurrentResponses, String []recordTriggers, String [] recordResponses) {
        int checkedAll = 0;

        for (int i = 0; i < recordTriggers.length; i++) {
            if (!question.contains(recordTriggers[i])){
                checkedAll = checkedAll + 1;
            }
        }

        for (int i = 0; i < recordTriggers.length; i++) {
            if (question.contains(recordTriggers[i])) {
                allCurrentResponses[0] = recordResponses[0];
                System.out.println(allCurrentResponses[0]);

                checkHelpful(allCurrentResponses, recordResponses);
            }
        }
        return checkedAll;
    }

    public static void checkHelpful (String [] allCurrentResponses, String [] recordResponses) {
        Scanner scanner = new Scanner(System.in);
        int count = 0; //holds count for allCurrentResponses array index
        int [] helpfulScore = new int[3];

        System.out.print("\nWas my response given helpful? (Yes/No) ");
        String helpful = scanner.nextLine();

        System.out.print("On a scale of 1-10. how helpful was the response? ");
        helpfulScore[0] = Integer.parseInt(scanner.nextLine());


        if (helpful.equals("No")) {
            for (int i = 0; i < allCurrentResponses.length; i++) {
                for (int j = 0; j < recordResponses.length; j++) {
                    if (Arrays.equals(allCurrentResponses, recordResponses)) {
                        System.out.println("\nSorry, I am out of responses!");
                        sortingResponses(helpfulScore, allCurrentResponses);
                        return;
                    }

                    if (!allCurrentResponses[i].equals(recordResponses[j])) {
                        System.out.println("\n" + recordResponses[j]);
                        count = count + 1;
                        allCurrentResponses[count] = recordResponses[j];

                        System.out.print("Was this response given helpful? (Yes/No) ");
                        helpful = scanner.nextLine();

                        System.out.print("On a scale of 1-10. how helpful was the response? ");
                        helpfulScore[count] = Integer.parseInt(scanner.nextLine());

                        if (helpful.equals("Yes")) {
                            System.out.println("\nI am glad that I was helpful!");
                            sortingResponses(helpfulScore, allCurrentResponses);
                            return;
                        }
                    }
                }
            }
        }
        else {
            System.out.println("\nI am glad that I was helpful!");
            sortingResponses(helpfulScore, allCurrentResponses);
        }
    }

    public static void sortingResponses(int [] helpfulScore, String [] helpfulResponse) {
        boolean sorted = false;
        final int ARRAY_SIZE = 3;

        while (!sorted) {
            sorted = true; //array may be already sorted. Used to exit out of the while loop body.

            for (int i=0; i < ARRAY_SIZE - 1; i++) {
                if (helpfulScore[i] != 0 ) { //this if statement accounts for if not all of the array is filled.
                    if (helpfulScore[i] < helpfulScore[i+1]) {
                        int tmp = helpfulScore[i+1];
                        helpfulScore[i+1] = helpfulScore[i];
                        helpfulScore[i] = tmp;

                        String tmp2 = helpfulResponse[i+1];
                        helpfulResponse[i+1] = helpfulResponse[i];
                        helpfulResponse[i] = tmp2;

                        sorted = false;
                    }
                }
            }
        }

        System.out.println("\nThese are your responses given, in order of most helpful to you:");
        for (int i=0; i < ARRAY_SIZE; i++) {
            if (helpfulScore[i] != 0) {
                System.out.println(helpfulResponse[i] + " RATING: " + helpfulScore[i]);
            }
        }
    }
}