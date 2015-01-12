package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		// COMPLETE THIS METHOD
        if(intervals.isEmpty())
            return;
        int leftMaxEndpoint = 0;
        for(int i = 0; i < intervals.size(); i++)
        {
            if(leftMaxEndpoint<intervals.get(i).leftEndPoint)
            {
                leftMaxEndpoint = intervals.get(i).leftEndPoint;
            }
        }
        int rightMaxEndpoint = 0;
        for(int x = 0; x < intervals.size(); x++)
        {
            if(rightMaxEndpoint<intervals.get(x).rightEndPoint)
            {
                rightMaxEndpoint = intervals.get(x).rightEndPoint;
            }
        }
        //System.out.println("Old Intervals: " + intervals);
		if(lr == 'l')
			intervals = sortLeft(intervals, leftMaxEndpoint);
		else if(lr == 'r')
			intervals = sortRight(intervals, rightMaxEndpoint);
        //System.out.println("New Intervals: " + intervals);
	}
	
	private static ArrayList<Interval> sortLeft(ArrayList<Interval> intervals, int leftMaxEndpoint)
	{
        ArrayList<Interval> leftSorted = new ArrayList<Interval>();

        for(int count = 0; count <= leftMaxEndpoint; count++)
        {
            for(int count2 = 0; count2 < intervals.size(); count2++)
            {
                if(intervals.get(count2).leftEndPoint == count)
                {
                    leftSorted.add(intervals.get(count2));
                }
            }
        }
        //System.out.println("Left SORTED!: " + leftSorted);
		return leftSorted;
	}
	
	private static ArrayList<Interval> sortRight(ArrayList<Interval> intervals, int rightMaxEndpoint)
	{
        ArrayList<Interval> rightSorted = new ArrayList<Interval>();

        for(int count = 0; count <= rightMaxEndpoint; count++)
        {
            for(int count2 = 0; count2 < intervals.size(); count2++)
            {
                if(intervals.get(count2).rightEndPoint == count)
                {
                    rightSorted.add(intervals.get(count2));
                }
            }
        }
        //System.out.println("Right SORTED!: " + rightSorted);
		return rightSorted;
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE

        int leftMaxEndpoint = 0;
        for(int i = 0; i < leftSortedIntervals.size(); i++)
        {
            if(leftMaxEndpoint<leftSortedIntervals.get(i).leftEndPoint)
            {
                leftMaxEndpoint = leftSortedIntervals.get(i).leftEndPoint;
            }
        }
        int rightMaxEndpoint = 0;
        for(int i = 0; i < rightSortedIntervals.size(); i++)
        {
            if(rightMaxEndpoint<rightSortedIntervals.get(i).rightEndPoint)
            {
                rightMaxEndpoint = rightSortedIntervals.get(i).rightEndPoint;
            }
        }
        int maxEndpoint = Math.max(leftMaxEndpoint,rightMaxEndpoint);
        ArrayList<Integer> sortedEndpoints = new ArrayList<Integer>();
        for(int count = 0; count <= maxEndpoint; count++)
        {
            for(int count2 = 0; count2 < leftSortedIntervals.size(); count2++)
            {
                if(leftSortedIntervals.get(count2).leftEndPoint==count||rightSortedIntervals.get(count2).rightEndPoint==count)
                {
                    sortedEndpoints.add(count);
                    break;
                }
            }
        }
        //System.out.println(sortedEndpoints);
        return sortedEndpoints;
	}
}
