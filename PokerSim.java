import java.util;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class PokerSim{


    
    

    final static String[] allCards = {"2h", "2d", "2s", "2c",
                                 "3h", "3d", "3s", "3c",
                                 "4h", "4d", "4s", "4c",
                                 "5h", "5d", "5s", "5c",
                                 "6h", "6d", "6s", "6c",
                                 "7h", "7d", "7s", "7c",
                                 "8h", "8d", "8s", "8c",
                                 "9h", "9d", "8s", "9c",
                                 "1h", "1d", "1s", "1c",
                                 "jh", "jd", "js", "jc",
                                 "qh", "qd", "qs", "kc",
                                 "kh", "kd", "ks", "qc",
                                 "ah", "ad", "as", "ac"};

    
    final static String scoreIndex = "  234567891jqka";




    static long[] scores = {0,0,0,0,0};
    public static boolean runSim(String a, String b){
        ArrayList<String> remainingCards = new ArrayList<>(Arrays.asList(allCards));
        


        int playerAmount = 5;

        String [] playerCards1 = new String[playerAmount];
        String [] playerCards2 = new String[playerAmount];
        String [] tableCards = new String[5];


        
        playerCards1[0] = remainingCards.remove(remainingCards.indexOf(a));
        playerCards2[0] = remainingCards.remove(remainingCards.indexOf(b));
        


        //Draws cards for each player
        for(int i = 1; i < 5; i++){
            playerCards1[i] = drawCard(remainingCards);
            playerCards2[i] = drawCard(remainingCards);
        }
        //Draws table cards
        for(int i = 0; i < 5; i++){
            tableCards[i] = drawCard(remainingCards);
        }

        


        for(int i = 0; i < 5; i++){
            String temp;
            if(scoreIndex.indexOf(playerCards2[i].charAt(0)) > scoreIndex.indexOf(playerCards1[i].charAt(0))){
                temp = playerCards2[i];
                playerCards2[i] = playerCards1[i];
                playerCards1[i] = temp;
            }
        }

        


        for(int i = 0; i < 5; i++){
            calculateScore(tableCards, playerCards1[i], playerCards2[i], i);
        }


        boolean ans = false;
        if(scores[0] > scores[1] &&
        scores[0] > scores[2] &&
        scores[0] > scores[3] &&
        scores[0] > scores[4]) ans = true;


        for(int i = 0; i < 5; i++){
            scores[i] = 0;
        }

        return ans;
    }

    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String b = sc.nextLine();
        double count = 0;
        for(int i = 0; i < 100000; i++){
            if(runSim(a, b)) count++;
        }
        System.out.println((double)((count/(double)100000)*(double)100));
        sc.close();


    }
    

    
    
    public static void calculateScore(String[] tableCards, String card1, String card2, int player){
        String[] currentArrayCheck = new String[5];
        long currentScore;
        for(int i = 0; i < 5; i++){
            currentArrayCheck[i] = tableCards[i];
        }

        scores[player] = checkCombo(currentArrayCheck);



        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(i == j){
                    currentArrayCheck[j] = card1;
                }else{
                    currentArrayCheck[j] = tableCards[j];
                }
            }
            currentScore = checkCombo(currentArrayCheck);
            if(currentScore > scores[player]) scores[player] = currentScore;

        }


        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(i == j){
                    currentArrayCheck[j] = card2;
                }else{
                    currentArrayCheck[j] = tableCards[j];
                }
            }
            currentScore = checkCombo(currentArrayCheck);
            if(currentScore > scores[player]) scores[player] = currentScore;

        }


        for(int i = 0; i < 4; i++){
            currentArrayCheck[i] = card1;
            for(int j = i+1; j < 5; j++){
            currentArrayCheck[j] = card2;
            {
                for(int o = 0; o < 5; o++){
                    if (o != i && o != j)currentArrayCheck[o] = tableCards[o];
                }
                currentScore = checkCombo(currentArrayCheck);
                if(currentScore > scores[player]) scores[player] = currentScore;

            }

            




            }
        }


    }


    public static long checkCombo(String[] cards){
        long score = 0;
        insertSort(cards);
        score+= highCard(cards);
        score+= checkPair(cards);
        score+= checkTwoPair(cards);
        score+= checkThreeOfAKind(cards);
        score+= checkStraight(cards);
        score+= checkFlush(cards);
        score+= checkFullHouse(cards);
        score+= checkFourOfAKind(cards);
        score+= checkStraightFlush(cards);
        score+= checkRoyalFlush(cards);
        return score;


    }



    
    static Random randomNum = new Random();
    public static String drawCard(ArrayList<String> x){
        
        return x.remove(randomNum.nextInt(x.size()));
    }

    public static String[] insertSort(String[] cards)
    {
        int j;
        String temp;  
        for (int i = 1; i < 5; i++) {  
            temp = cards[i];  
            j = i - 1;  
      
            while(j>=0 && scoreIndex.indexOf(temp.charAt(0)) <= scoreIndex.indexOf(cards[j].charAt(0)))  /* Move the elements greater than temp to one position ahead from their current position*/  
            {    
                cards[j+1] = cards[j];     
                j = j-1;    
            }    
            cards[j+1] = temp;    
        }  
        return cards;
    }  


    public static int highCard(String[] cards){
        int highest = 0;
        int card;

        for(int i = 0; i < 5; i++){
            card = scoreIndex.indexOf(cards[i].charAt(0));
            if(card > highest) highest = card;
        }
        return highest;
    }

    public static int checkPair(String[] cards){
        int count;
        int score = 0;
        for(int i = 0; i < 5; i++){
            count = 1;
            for(int j = 0; j < 5; j++){
                if(cards[i].charAt(0) == cards[j].charAt(0) && i!=j) count++;
            }
            if(count == 2 && scoreIndex.indexOf(cards[i].charAt(0)) > score) score = scoreIndex.indexOf(cards[i].charAt(0));
        }
        return score*10;
    }

    public static int checkTwoPair(String[] cards){
        int count;
        int score = 0;
        char pair1 = 'z';
        for(int i = 0; i < 5; i++){
            count = 1;
            for(int j = 0;  j < 5; j++){
                if(cards[i].charAt(0) == cards[j].charAt(0) && i!=j) count++;
            }
            if(count == 2){
                if('z' == pair1){
                    pair1 = cards[i].charAt(0);
                }
                if(pair1 != cards[i].charAt(0)){
                    if(scoreIndex.indexOf(cards[i].charAt(0)) > scoreIndex.indexOf(pair1)){
                        score = scoreIndex.indexOf(cards[i].charAt(0));
                    }
                    else if(scoreIndex.indexOf(cards[i].charAt(0)) < scoreIndex.indexOf(pair1)){
                        score = scoreIndex.indexOf(pair1);
                    }
                }
                
            }
            
        }
        return score*100;
    }

    public static int checkThreeOfAKind(String[] cards){
        int count;
        int score = 0;
        for(int i = 0; i < 5; i++){
            count = 1;
            for(int j = 0; j < 5; j++){
                if(cards[i].charAt(0) == cards[j].charAt(0)&& i!=j) count++;
            }
            if(count == 3 && scoreIndex.indexOf(cards[i].charAt(0)) > score) score = scoreIndex.indexOf(cards[i].charAt(0));
        }
        return score*1000;
    }

    public static int checkStraight(String[] cards){
        if(scoreIndex.indexOf(cards[0].charAt(0)) == scoreIndex.indexOf(cards[1].charAt(0))-1
        && scoreIndex.indexOf(cards[1].charAt(0)) == scoreIndex.indexOf(cards[2].charAt(0))-1
        && scoreIndex.indexOf(cards[2].charAt(0)) == scoreIndex.indexOf(cards[3].charAt(0))-1
        && scoreIndex.indexOf(cards[3].charAt(0)) == scoreIndex.indexOf(cards[4].charAt(0))-1)return scoreIndex.indexOf(cards[4].charAt(0))*10000;
        return 0;
        

    }

    public static int checkFlush(String[] cards){
        if(cards[0].charAt(1) == cards[1].charAt(1) &&
        cards[0].charAt(1) == cards[2].charAt(1) &&
        cards[0].charAt(1) == cards[3].charAt(1) &&
        cards[0].charAt(1) == cards[4].charAt(1)) return 500000;
        return 0;
    }
    
    public static int checkFullHouse(String[] cards){
        int count;
        int score = 0;
        char pair1 = 'z';
        char triple = 'z';



        for(int i = 0; i < 5; i++){
            count = 1;
            for(int j = 0;  j < 5; j++){
                if(cards[i].charAt(0) == cards[j].charAt(0) && i!=j) count++;
            }
            if(count == 2){
                if('z' == pair1){
                    pair1 = cards[i].charAt(0);
                }
            }
            if(count == 3){
                if('z' == triple){
                    triple = cards[i].charAt(0);
                }
            }
            
        }

        if(triple != 'z' && pair1 != 'z') score = scoreIndex.indexOf(triple);
        return score*1000000;

    }

    public static int checkFourOfAKind(String[] cards){
        int count;
        int score = 0;
        for(int i = 0; i < 5; i++){
            count = 1;
            for(int j = 0; j < 5; j++){
                if(cards[i].charAt(0) == cards[j].charAt(0) && i!=j) count++;
            }
            if(count == 4 && scoreIndex.indexOf(cards[i].charAt(0)) > score) score = scoreIndex.indexOf(cards[i].charAt(0));
        }
        return score * 10000000;
    }

    public static int checkStraightFlush(String[] cards){


        if(scoreIndex.indexOf(cards[0].charAt(0)) == scoreIndex.indexOf(cards[1].charAt(0))-1
        && scoreIndex.indexOf(cards[1].charAt(0)) == scoreIndex.indexOf(cards[2].charAt(0))-1
        && scoreIndex.indexOf(cards[2].charAt(0)) == scoreIndex.indexOf(cards[3].charAt(0))-1
        && scoreIndex.indexOf(cards[3].charAt(0)) == scoreIndex.indexOf(cards[4].charAt(0))-1){
            if(cards[0].charAt(1) == cards[1].charAt(1) &&
                cards[1].charAt(1) == cards[2].charAt(1) &&
                cards[2].charAt(1) == cards[3].charAt(1) &&
                cards[3].charAt(1) == cards[4].charAt(1)){
            return scoreIndex.indexOf(cards[4].charAt(0))*100000000;
        }
        }
        return 0;

    }

    public static long checkRoyalFlush(String[] cards){
        if(scoreIndex.indexOf(cards[0].charAt(0)) == '1'
        && scoreIndex.indexOf(cards[1].charAt(0)) == 'j'
        && scoreIndex.indexOf(cards[2].charAt(0)) == 'q'
        && scoreIndex.indexOf(cards[3].charAt(0)) == 'k'
        && scoreIndex.indexOf(cards[3].charAt(0)) == 'a'){
            if(cards[0].charAt(1) == cards[1].charAt(1) &&
                cards[1].charAt(1) == cards[2].charAt(1) &&
                cards[2].charAt(1) == cards[3].charAt(1) &&
                cards[3].charAt(1) == cards[4].charAt(1)){
            return scoreIndex.indexOf(cards[4].charAt(0))*2000000000;
        }
        }
        return 0;
    }











}
