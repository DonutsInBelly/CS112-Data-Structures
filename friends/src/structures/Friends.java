package structures;

/**
 * User:
 * Carlin Au
 * Deeraj Subramanian
 * +      o     +              o
 * +             o     +       +
 * o          +
 * o  +           +        +
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-""  ""
 * +      o         o   +       o
 * +         +
 * o        o         o      o     +
 * o           +
 * +      +     o        o      +
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Friends {


    static Scanner userin = new Scanner(System.in);

    static char List() {
        System.out.print("Choose action: \n");
        System.out.print("\t1) Shortest intro chain\n");
        System.out.print("\t2) Cliques at school\n");
        System.out.print("\t3) Connectors\n");
        System.out.print("\t4) Quit\n");
        char ptr = userin.nextLine().toLowerCase().charAt(0);
        while (ptr != '1' && ptr != '2' && ptr != '3' && ptr != '4') {
            System.out.print("user error");
            ptr = userin.nextLine().toLowerCase().charAt(0);
        }
        return ptr;
    }
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Enter graph file name: ");
        String friendGraph = userin.nextLine();
        try{
            Graph x = new Graph(new Scanner(new File(friendGraph)));
            char sop;
            while((sop = List()) != 'q'){
                if(sop == '1'){
                    System.out.println("Enter name of person:");
                    String sName = userin.nextLine().toLowerCase();
                    System.out.println("Enter name of person they want to meet:");
                    String eName = userin.nextLine().toLowerCase();
                    x.find(sName, eName);
                }
                else if(sop == '2'){
                    System.out.println("Enter school name: ");
                    Graph subgraph = x.atSchool(userin.nextLine().toLowerCase());
                    ArrayList<Graph> cliques = subgraph.nich();
                    for(int k = 1; k < cliques.size()+1; k++){
                        System.out.println("Clique " + k + ":");
                        cliques.get(k-1).Harg();
                    }
                }
                else if(sop == '3'){
                    x.label();

                }
                else if(sop == '4'){
                	System.out.println("Quitting.");
                	break;
                }
            }
        }catch(Exception FileNotFoundException){
            System.err.println("system error");
        }
    }
}