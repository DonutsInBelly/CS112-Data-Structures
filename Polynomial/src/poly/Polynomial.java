package poly;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
        if(this.poly == null)
        {
            return p;
        }
        else if (p.poly == null)
        {
            Polynomial nList = new Polynomial();
            nList.poly = this.poly;
            return nList;
        }
        Polynomial n = new Polynomial();
        Node curr1, curr2, addNode;
        n.poly = new Node(0,0,null);
        Node nFront = n.poly;
        curr1 = this.poly;
        //curr1 = sort(curr1);
        curr2 = p.poly;
        //curr2 = sort(curr2);
        /*do{
            addNode = compareToAdd(curr1,curr2);
            nFront.term.coeff = addNode.term.coeff;
            nFront.term.degree = addNode.term.degree;
            System.out.println(nFront.term.coeff);
            if(nFront.term.coeff == (curr1.term.coeff + curr2.term.coeff)){
                curr1 = curr1.next;
                curr2 = curr2.next;
            }
            else if(nFront.term.coeff == curr1.term.coeff)
            {
                curr1 = curr1.next;
            }
            else if(nFront.term.coeff == curr2.term.coeff)
            {
                curr2 = curr2.next;
            }
            nFront.next = new Node(0,0,null);
            nFront = nFront.next;
            System.out.println("coeff="+nFront.term.coeff);
        }while(curr1 != null && curr2 != null);*/

        n = adder(curr1,curr2);
        n = sorter(n);
        return n;
    }

    public int highDegAdd(Node p1, Node p2)
    {
        int highDeg = 0;
        while(p1!=null)
        {
            if(p1.term.degree>highDeg)
            {
                highDeg = p1.term.degree;
            }
            p1 = p1.next;
        }
        while(p2!=null)
        {
            if(p2.term.degree>highDeg)
            {
                highDeg = p2.term.degree;
            }
            p2 = p2.next;
        }
        return highDeg;
    }

    public int lowDegAdd(Node p1, Node p2)
    {
        int lowDeg = 0;
        while(p1!=null)
        {
            if(p1.term.degree<lowDeg)
            {
                lowDeg = p1.term.degree;
            }
            p1 = p1.next;
        }
        while(p2!=null)
        {
            if(p2.term.degree<lowDeg)
            {
                lowDeg = p2.term.degree;
            }
            p2 = p2.next;
        }
        return lowDeg;
    }

    public Polynomial adder(Node p1, Node p2)
    {
        Node p1head = p1;
        Node p2head = p2;
        int highDeg = highDegAdd(p1,p2);
        int lowDeg = lowDegAdd(p1,p2);
        Node ohead = new Node(0,lowDeg,null);
        Node head = ohead;
        for(int count = lowDeg; count<=highDeg; count++)
        {
            p1head = p1;
            p2head = p2;
            while(p2head!=null)
            {
                if(p2head.term.degree == head.term.degree)
                {
                    head.term.coeff += p2head.term.coeff;
                }
                p2head = p2head.next;
            }
            while(p1head!=null)
            {
                if(p1head.term.degree == head.term.degree)
                {
                    head.term.coeff += p1head.term.coeff;
                }
                p1head = p1head.next;
            }
            head.next = new Node(0,count+1,null);
            head = head.next;
        }
        Polynomial n = new Polynomial();
        n.poly = ohead;
        return n;
    }

    public Node sort(Node plist){
        int deg=0;
        int count=0;
        Node temp = plist;
        while(temp!=null){
            temp.term.degree=deg;
            count++;
            temp = temp.next;
        }
        if(deg>count){
            Node curr = plist.next;
            Node prev = plist;
            while (curr!=null){
                if(curr.term.degree-prev.term.degree>1){
                    Node replace = new Node(0,curr.term.degree+1,curr.next);
                    curr.next=replace;
                    prev = curr;
                    curr = curr.next;
                }
            }
        }
        return plist;
    }

    public Node compareToAdd(Node p1, Node p2)
    {

        if(p1.term.degree == p2.term.degree){
            Node sum = new Node(0,0,null);
            sum.term.coeff = (p1.term.coeff + p2.term.coeff);
            System.out.println("compareToAdd p1=" + p1.term.coeff + "p2=" + p2.term.coeff);
            System.out.println("compareToAdd coeff=" + sum.term.coeff);
            sum.term.degree = p1.term.degree;
            System.out.println("compareToAdd degree=" + sum.term.degree);
            return sum;
        }
        else if(p1.term.degree > p2.term.degree){
            return p1;
        }
        else if(p1.term.degree < p2.term.degree){
            return p2;
        }
        else if(p1 == null){
            return p2;
        }
        else if(p2 == null){
            return p1;
        }
        return null;
    }
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */

    public Polynomial multiply(Polynomial p)
    {
        Polynomial n = new Polynomial();
        Node p1head, p2head;
        p1head = this.poly;
        p2head = p.poly;
        if(p1head == null || p2head == null)
        {
            Polynomial zeroPoly = new Polynomial();
            return zeroPoly;
        }
        int highDeg, lowDeg;
        highDeg = highDeg(p1head,p2head);
        lowDeg = lowDeg(p1head,p2head);
        Node list = new Node(0,lowDeg,null);
        Node listFront = list;
        int count = lowDeg;
        System.out.println("count = " +count);
        while(count != highDeg+1)
        {
            list.next = new Node(0,count,null);
            list = list.next;
            count++;
        }
        Node temp = listFront;
        /*while(temp!=null){
            System.out.println(temp.term.coeff +"x"+ temp.term.degree);
            temp = temp.next;
        }*/
        n = multiPoly(p1head, p2head, listFront, highDeg, lowDeg);
        n = sorter(n);
        n = combinator(n,highDeg);
        n = sorter(n);
        return n;
    }

    public Polynomial sorter(Polynomial p)
    {
        Node nFront = p.poly;
        Node current = p.poly;
        Node previous = null;
        while(current!=null)
        {
            if(previous == null && current.term.coeff != 0)
            {
                previous = current;
                current = current.next;
                continue;
            }
            else if(previous == null && current.term.coeff == 0)
            {
                previous = current.next;
                current = current.next.next;
                continue;
            }
            if(current.term.coeff == 0 && current.next == null)
            {
                previous.next = null;
                break;
            }
            if(current.term.coeff==0)
            {
                previous.next = current.next;
                current = current.next;
            }
            previous = current;
            current = current.next;
        }
        return p;
    }

    public Polynomial combinator(Polynomial p, int highDeg)
    {
        Node ohead = p.poly;
        Node head = p.poly;
        Node olist = new Node(0,0,null);
        Node list = olist;
        for(int count = 0;count<=highDeg;count++)
        {
            head = p.poly;
            while(head!=null)
            {
                if(head.term.degree==count)
                {
                    list.term.coeff +=head.term.coeff;
                }
                head = head.next;
            }
            list.next = new Node(0,count+1,null);
            ohead = ohead.next;
            list = list.next;
        }
        p.poly = olist;
        return p;
    }

    public Polynomial multiPoly(Node p1, Node p2, Node list, int highDeg, int lowDeg)
    {
        Polynomial n = new Polynomial();
        n.poly = list;
        Node nFront = n.poly;

        Node p1head, p2head;
        p1head = p1;
        p2head = p2;

        Node ohead = new Node(0,0,null);
        Node head = ohead;

        while(p1head!=null)
        {
            p2head = p2;
            while(p2head!=null)
            {
                head.term.coeff = p1head.term.coeff * p2head.term.coeff;
                head.term.degree = p1head.term.degree + p2head.term.degree;
                head.next = new Node(0,0,null);
                head = head.next;
                p2head = p2head.next;
            }
            p1head = p1head.next;
        }

        n.poly = ohead;
        /*Node temp = ohead;
        while(temp!=null){
            System.out.println(temp.term.coeff+"x"+temp.term.degree);
            temp = temp.next;
        }*/
        /*while(p1head!=null)
        {
            p2head = p2;
            while(p2head!=null)
            {
                int deg = p2head.term.degree + p1head.term.degree;
                nFront = n.poly;
                while(nFront!=null)
                {
                    if(nFront.term.degree == deg)
                    {
                        nFront.term.coeff = p2head.term.coeff * p1head.term.coeff;
                    }
                    nFront = nFront.next;
                }
                p2head = p2head.next;
            }
            p1head = p1head.next;
        }*/

        //nFront = n.poly;

        /*while(nFront!=null)
        {
            if(nFront.term.degree == highDeg)
            {
                nFront.next = null;
                nFront = nFront.next;
            }
            else
            {
                nFront = nFront.next;
            }
        }*/
        //n.poly = nFront;
        //nFront = n.poly;
        /*while(nFront!=null){
            System.out.println(nFront.term.coeff + "x" + nFront.term.degree);
            nFront = nFront.next;
        }*/
        return n;
    }

    public int highDeg(Node p1, Node p2)
    {
        int highDegree=0;
        Node p1Front, p2Front;
        p1Front = p1;
        p2Front = p2;
        while(p1Front!=null)
        {
            p2Front = p2;
            while(p2Front!=null)
            {
                if(p2Front.term.degree + p1Front.term.degree > highDegree)
                {
                    highDegree = p2Front.term.degree + p1Front.term.degree;
                }
                p2Front= p2Front.next;
            }
            p1Front = p1Front.next;
        }
        System.out.println("high deg = " + highDegree);
        return highDegree;
    }
    public int lowDeg(Node p1, Node p2)
    {
        int lowDegree=0;
        Node p1Front, p2Front;
        p1Front = p1;
        p2Front = p2;
        while(p1Front!=null)
        {
            p2Front = p2;
            while(p2Front!=null)
            {
                if(p2Front.term.degree + p1Front.term.degree < lowDegree)
                {
                    lowDegree = p2Front.term.degree + p1Front.term.degree;
                }
                p2Front= p2Front.next;
            }
            p1Front = p1Front.next;
        }
        System.out.println("low deg = " + lowDegree);
        return lowDegree;
    }

    /*public Polynomial multiply(Polynomial p) {
        Node p1head, p2head;
        p1head = this.poly;
        p2head = p.poly;
        if(p1head == null || p2head == null)
        {
            Polynomial zeroPoly = new Polynomial();
            return zeroPoly;
        }
        Polynomial n = new Polynomial();
        n = multPoly(p1head,p2head);
		return n;
	}*/
    /*public Polynomial multPoly(Node p1, Node p2){
        Node p1front, p2front;
        Polynomial retPoly = new Polynomial();
        p1front = p1;
        p2front = p2;
        Node retFront = new Node(0,0,null);
        retPoly.poly = retFront;
        while(p1front!=null){
            p2front = p2;
            while(p2front!=null){
                retFront.term.coeff = (p1front.term.coeff * p2front.term.coeff);
                retFront.term.degree = (p1front.term.degree + p2front.term.degree);
                retFront.next = new Node(0,0,null);
                retFront = retFront.next;
                p2front = p2front.next;
            }
            p1front = p1front.next;
        }
        retPoly = combinatoring(retFront);
        retFront = retPoly.poly;
        while(p2front!=null){
            while(p1front!=null){
                retFront.term.coeff = (p1front.term.coeff * p2front.term.coeff);
                retFront.term.degree = (p1.term.degree + p2front.term.degree);
                retFront.next = new Node(0,0,null);
                retFront = retFront.next;
                p2front = p2front.next;
            }
            p1front = p1front.next;
        }
        retPoly = combinatoring(retFront);

        return retPoly;
    }*/

    /*public Polynomial combinatoring(Node p){
        Polynomial retPoly = new Polynomial();
        Node temp1 = p;
        while(temp1!=null){
            Node curr = temp1;
            Node temp2 = curr.next;
            while(temp2!=null){
                if(temp1.term.degree == temp2.term.degree){
                    temp1.term.coeff += temp2.term.coeff;
                    curr.next = temp2.next;
                }
                curr = curr.next;
                temp2 = curr.next;
            }
            temp1 = temp1.next;
        }
        retPoly.poly=p;
        return retPoly;
    }*/

    /*public Polynomial combinator(Node p, Node list){
        Polynomial n = new Polynomial();
        Node nFront = n.poly;
        Node p1,p2;
        p1 = list;
        p2 = list.next;
        while(p1 != null){
            while(p2 != null){
                if(p2.term.degree==p1.term.degree) {
                    nFront.term.coeff = (p2.term.coeff + p1.term.coeff);
                    nFront.term.degree = p2.term.degree;
                }

            }
        }
        return n;
    }*/

    /*public Polynomial combineTerms(Node p1, Node p2){
        Polynomial retPoly = new Polynomial();
        Node retNode = new Node(0,0,null);
        retPoly.poly = retNode;
        while(p1 != null && p2 != null){
            if(p1.term.degree == p2.term.degree){
                retNode.term.coeff = (p1.term.coeff + p2.term.coeff);
                retNode.term.degree = p1.term.degree;
            }
            else if(p1.term.degree > p2.term.degree){
                retNode.term.coeff = p1.term.coeff;
                retNode.term.degree = p1.term.degree;
            }
            else if(p1.term.degree < p2.term.degree){
                retNode.term.coeff = p2.term.coeff;
                retNode.term.degree = p2.term.degree;
            }
            else if(p1==null&&p2!=null){
                retNode.term.coeff = p2.term.coeff;
                retNode.term.degree = p2.term.degree;
            }
            else if(p1!=null&&p2==null){
                retNode.term.coeff = p1.term.coeff;
                retNode.term.degree = p1.term.degree;
            }
            retNode.next = new Node(0,0,null);
        }
        return retPoly;
    }*/
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
        Node head = this.poly;
        float sum = 0;
        while(head!= null){
            sum += (head.term.coeff*Math.pow(x,head.term.degree));
            //sum = sum*head.term.coeff;
            System.out.println("Sum = " + sum);
            head = head.next;
        }
		return sum;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
