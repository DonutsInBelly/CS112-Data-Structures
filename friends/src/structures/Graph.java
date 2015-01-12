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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;
public class Graph {
    int ester;
    ArrayList<Fre> value1;
    Hashtable<String , Integer>  tree;
    int brety2;
    int i123;
    String large;
    public Graph (ArrayList<Fre> value1){
        this.brety2 = 1;
        this.i123 = 1;
        this.ester = value1.size();
        this.tree = new Hashtable<String, Integer>(this.ester);
        this.value1 = value1;
        try{
            this.large = value1.get(0).center;
        }catch (Exception indexOutOfBoundsException){
            return;
        }
        int sir;
        for(sir = 0; sir < value1.size(); sir++) {
            this.tree.put(value1.get(sir).metre, sir);
        }
        int poly;
        for(poly = 0; poly<this.value1.size(); poly++){
            this.tree.put(this.value1.get(poly).metre, poly);
            Mefrp polar = null;
            Mefrp node = (this.value1.get(poly).moyfrn);
            while(node != null){

                node.count2 = this.i(node.lana3bac);
                if(node.count2 != -1){
                    polar = new Mefrp(node.count2, polar);
                }
                node = node.ptne;
            }
            this.value1.get(poly).moyfrn = polar;
        }
    }
    public Graph atSchool(String large){
        ArrayList<Fre> new1 = new ArrayList<Fre>();
        int xty;
        for (xty = 0; xty < value1.size(); xty++){
            Fre student = value1.get(xty);
            if(student.center != null && student.center.equals(large)){
                Mefrp newfriends = null;
                Mefrp temp = student.moyfrn;
                while(temp != null){
                    if(temp.sc != null && temp.sc.equals(large)){
                        newfriends = new Mefrp(temp.count2, newfriends);
                    }
                    temp = temp.ptne;
                }
                new1.add(new Fre(student.metre, large, newfriends));
            }
        }
        Graph ngr = new Graph(new1);
        return ngr;
    }
    public Graph(Scanner sc) {
        this.ester = Integer.parseInt(sc.nextLine().trim());
        this.value1 = new ArrayList<Fre>(this.ester);
        this.tree = new Hashtable<String, Integer>(this.ester);
        this.brety2 = 1;
        this.i123 = 1;
        this.large = null;
        int counter;
        for(counter=0; counter<this.ester; counter++){
            String make = sc.nextLine().trim().toLowerCase();
            if (make.charAt(make.indexOf('|')+1) == 'y'){
                value1.add(new Fre(make.substring(0, make.indexOf('|')), make.substring((make.indexOf('|'))+3, make.length()), null));
            }
            else{
                value1.add(new Fre(make.substring(0, make.indexOf('|')), null, null));
            }
            tree.put(make.substring(0, make.indexOf('|')), counter);
        }
        while(sc.hasNextLine()){
            String friend = sc.nextLine().trim().toLowerCase();
            int mg = friend.indexOf('|');
            int ls2 = i(friend.substring(0,mg));
            int tr1= i(friend.substring(mg+1));
            value1.get(ls2).moyfrn = new Mefrp(tr1, value1.get(ls2).moyfrn);
            value1.get(tr1).moyfrn = new Mefrp(ls2, value1.get(tr1).moyfrn);
        }
    }
    int i(String name){
        try{
            return tree.get(name);
        }catch(Exception NullPointerException){
            return -1;
        }
    }
    String nameForIndex(int count2){
        return value1.get(count2).metre;
    }
    public class Mefrp{
        public int count2;
        public Mefrp ptne;
        public String lana3bac;
        public String sc;
        public Mefrp(String lana3bac, Mefrp ptne){
            this.count2 = i(lana3bac);
            this.lana3bac = lana3bac;
            this.sc = value1.get(count2).center;
            this.ptne = ptne;
        }
        public Mefrp(int count2, Mefrp ptne){
            this.count2 = count2;
            this.ptne = ptne;
            this.sc = value1.get(count2).center;
            this.lana3bac = value1.get(count2).metre;
        }
    }
    public class Fre {
        String metre;
        String center;
        Mefrp moyfrn;
        int fon2;
        int exit;
        int robn1;
        public Fre(String metre, String center, Mefrp moyfrn ) {
            this.robn1 = -1;
            this.metre = metre;
            this.center = center;
            this.moyfrn = moyfrn;
            this.fon2 = 0;
            this.exit = 0;
        }
    }
    public ArrayList<Graph> nich(){
        ArrayList<Graph> pocl = new ArrayList<Graph>();
        boolean[] recent = new boolean[ester];
        boolean[] trya = new boolean[ester];
        int awe;
        for(awe=0; awe<ester; awe++){
            ArrayList<Fre> nlc = new ArrayList<Fre>();
            if (!recent[awe]){
                System.out.println("Starting on new clique");
                polc(trya, awe, recent);
                int awe2;
                for(awe2=0; awe2<recent.length; awe2++){
                    if(trya[awe2])
                        nlc.add(new Fre(nameForIndex(awe2), large, value1.get(awe2).moyfrn));
                    trya[awe2] = false;
                }
                Graph tgr = new Graph(nlc);
                pocl.add(tgr);
            }
        }
        return pocl;
    }
    private void oprt(int point, boolean[] lsne){
        lsne[point] = true;
        Mefrp fre;
        for (fre = value1.get(point).moyfrn; fre != null; fre=fre.ptne){
            if(!lsne[fre.count2]){
                value1.get(fre.count2).robn1 = i123;
                i123++;
                oprt(fre.count2, lsne);
                i123--;
                value1.get(point).robn1 = Math.min(value1.get(point).robn1, value1.get(fre.count2).robn1 + 1);
            }
            else{
                value1.get(point).robn1 = Math.min(value1.get(fre.count2).robn1 + 1, value1.get(point).robn1);
            }
        }
    }
    public void Harg(){
        if(ester == 0){
            System.out.println("empty");
            return;
        }
        System.out.println(ester);
        int zew;
        for(zew = 0; zew < ester; zew++){
            if(value1.get(zew).center != null){
                System.out.println(value1.get(zew).metre + "|y|" + value1.get(zew).center);
            }
            else{
                System.out.println(value1.get(zew).metre + "|n");
            }
        }
        int res;
        for(res  = 0; res < ester; res++){
            Mefrp node2 = value1.get(res).moyfrn;
            while(node2 != null){
                if(res < node2.count2){
                    System.out.println(value1.get(res).metre + "|" + nameForIndex(node2.count2));
                }
                node2 = node2.ptne;
            }
        }
    }
    public void find(String trn2, String trn1){
        if(i(trn1) == -1 || i(trn2) == -1){
            System.out.println("invalid name!");
            return;
        }
        Stack<String> chain = new Stack<String>();
        int snum = i(trn1);
        Fre start = value1.get(snum);
        Fre end = value1.get(i(trn2));
        boolean[] visited = new boolean[ester];

        value1.get(i(trn1)).robn1 = 0;
        oprt(i(trn1), visited);
        if(end.robn1 == -1){
            System.out.println("error, crash");
            return;
        }
        Fre node = end;
        while(node.robn1 != 0){
            chain.push(node.metre);
            Mefrp moyfrn = node.moyfrn;
            int pop = node.robn1;
            while(moyfrn != null){
                if(value1.get(moyfrn.count2).robn1 < pop){
                    node = value1.get(moyfrn.count2);
                    break;
                }
                moyfrn = moyfrn.ptne;
            }
        }
        chain.push(start.metre);
        String end2 = "";
        while(chain.size() > 1){
            end2 = end2 + chain.pop() + ", ";
        }
        end2 = "Shortest path: " + end2 + chain.pop() + ".";
        System.out.println(end2);
        int xav;
        for(xav = 0; xav < value1.size(); xav++){
            value1.get(xav).robn1 = -1;
        }
    }
    private void polc(boolean[] nprt, int last, boolean[] rpt) {
        rpt[last] = true;
        nprt[last] = true;
        Mefrp myfr;
        for (myfr=value1.get(last).moyfrn; myfr != null; myfr=myfr.ptne) {
            if (!rpt[myfr.count2]) {
                polc(nprt, myfr.count2, rpt);
            }
        }
    }
    private void  miscr(int rapid, boolean[] it, boolean[] crit) {
        it[rapid] = true;
        value1.get(rapid).fon2 = brety2;
        value1.get(rapid).exit = brety2;
        brety2++;
        Mefrp eng2;
        for (eng2 = value1.get(rapid).moyfrn; eng2 != null; eng2 = eng2.ptne) {
            if (!it[eng2.count2]) {
                miscr(eng2.count2, it, crit);
                if(value1.get(rapid).fon2 > value1.get(eng2.count2).exit) {
                    value1.get(rapid).exit = Math.min(value1.get(rapid).exit , value1.get(eng2.count2).exit);
                }
                if(value1.get(rapid).fon2 <= value1.get(eng2.count2).exit && rapid != 0){
                    crit[rapid]=true;
                }
            }
            else{
                value1.get(rapid).exit = Math.min(value1.get(rapid).exit, value1.get(eng2.count2).fon2);
            }
        }

    }
    public void label(){
        boolean[] ltsne = new boolean[ester];
        boolean[] yvste = new boolean[ester];
        int lk;
        for (lk = 0; lk < yvste.length; lk++) {
            if (!yvste[lk]) {
                miscr(lk, yvste, ltsne);
            }
        }
        int bar = 0;
        int srel;
        for(srel = value1.size()-1; srel >= 0; srel--){
            if(value1.get(srel).exit == 1 && srel != 0){
                bar++;
                if(bar >= 2){ltsne[0] = true;}
            }
        }
        System.out.print("Connectors:");
        String blk = "";
        int neg;
        for(neg=0; neg<ltsne.length; neg++){
            if(ltsne[neg]){
                blk = value1.get(neg).metre + ", "+ blk;
            }
        }
        System.out.println(blk);
        brety2 = 1;
        for(Fre we: value1){
            we.fon2 = 0;
            we.exit = 0;
        }
    }
}