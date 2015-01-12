package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}

    /**
     * Constructs an interval tree based on input root IntervalTreeNode.
     *
     * @param root Root of the IntervalTree
     */
    private IntervalTree(IntervalTreeNode root)
    {
        this.root = root;
    }

	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE

        if(endPoints.isEmpty())
            return null;
        Queue<IntervalTreeNode> que = new Queue<IntervalTreeNode>();
        IntervalTreeNode root;
        for(int iter = 0; iter < endPoints.size(); iter++)
        {
            int currIter = endPoints.get(iter);
            IntervalTreeNode endPointNode = new IntervalTreeNode(currIter,currIter,currIter);
            endPointNode.leftIntervals = new ArrayList<Interval>();
            endPointNode.rightIntervals = new ArrayList<Interval>();
            que.enqueue(endPointNode);
            //System.out.println("First Node: " + endPointNode);
        }
        int queueSize = que.size();
        boolean lastInterval=false;
        //if(queueSize == 1)
        //{
        //    lastInterval = true;
        //    root = que.dequeue();
        //}
        //else
        //    lastInterval = false;
        do
        {

            if(que.size()<=1)
            {
                root = que.dequeue();
                break;
            }
            int tempQueueSize = que.size();
            while (tempQueueSize > 1)
            {
                IntervalTreeNode node1 = que.dequeue();
                //System.out.println("Node1: " + node1);
                IntervalTreeNode node2 = que.dequeue();
                //System.out.println("Node2: " + node2);
                float maxValNode1 = node1.maxSplitValue;
                float minValNode2 = node2.minSplitValue;
                float splitVal = ((maxValNode1+minValNode2)/2);
                IntervalTreeNode theNode = new IntervalTreeNode(splitVal,node1.minSplitValue,node2.maxSplitValue);
                theNode.leftChild = node1;
                theNode.rightChild = node2;
                theNode.leftIntervals = new ArrayList<Interval>();
                theNode.rightIntervals = new ArrayList<Interval>();
                //System.out.println("the Node: " + theNode);
                que.enqueue(theNode);
                tempQueueSize-=2;
            }
            if(tempQueueSize==1)
            {
                que.enqueue(que.dequeue());
            }
        }while(true);

        //root = que.dequeue();
        //System.out.println("Root: " + root);
		return root;
	}

	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
        if(leftSortedIntervals.isEmpty() || rightSortedIntervals.isEmpty())
            return;
        for(int iterLeft = 0; iterLeft < leftSortedIntervals.size(); iterLeft++)
        {
            IntervalTreeNode tempLeft = root;
            //System.out.println(tempLeft);
            while(tempLeft!=null)
            {
                Interval iLeft = leftSortedIntervals.get(iterLeft);
                if(iLeft.contains(tempLeft.splitValue))
                {
                    tempLeft.leftIntervals.add(iLeft);
                    break;
                }
                else
                {
                    //System.out.println("Curr: " + leftSortedIntervals.get(iterLeft).leftEndPoint);
                    //System.out.println("tempLeft: " + tempLeft.leftChild);
                    if(iLeft.leftEndPoint > tempLeft.leftChild.maxSplitValue)
                    {
                        tempLeft = tempLeft.rightChild;
                    }
                    else
                    {
                        tempLeft = tempLeft.leftChild;
                    }
                }
            }
        }
        for(int iterRight = 0; iterRight < rightSortedIntervals.size(); iterRight++)
        {
            IntervalTreeNode tempRight = root;
            while(tempRight!=null)
            {
                Interval iRight = rightSortedIntervals.get(iterRight);
                if(iRight.contains(tempRight.splitValue))
                {
                    tempRight.rightIntervals.add(iRight);
                    break;
                }
                else
                {
                    if(iRight.leftEndPoint>tempRight.maxSplitValue)
                    {
                        tempRight = tempRight.rightChild;
                    }
                    else
                    {
                        tempRight = tempRight.leftChild;
                    }
                }
            }
        }
	}

	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
        ArrayList<Interval> result = new ArrayList<Interval>();

        if((root.leftChild == null && root.rightChild == null) || root == null)
        {
            return result;
        }
        IntervalTree leftSubTree = new IntervalTree(root.leftChild);
        IntervalTree rightSubTree = new IntervalTree(root.rightChild);

        if(q.contains(root.splitValue))
        {
            result.addAll(root.leftIntervals);
            result.addAll(rightSubTree.findIntersectingIntervals(q));
            result.addAll(leftSubTree.findIntersectingIntervals(q));
        }
        else if(root.splitValue<q.leftEndPoint)
        {
            ArrayList<Interval> rightList = root.rightIntervals;
            for(int count = rightList.size()-1; count >= 0 && rightList.get(count).intersects(q); count--)
            {
                result.add(rightList.get(count));
            }
            result.addAll(rightSubTree.findIntersectingIntervals(q));
        }
        else if(root.splitValue>q.rightEndPoint)
        {
            ArrayList<Interval> leftList = root.leftIntervals;
            for(int count = 0; count < leftList.size() && leftList.get(count).intersects(q); count++)
            {
                result.add(leftList.get(count));
            }
            result.addAll(leftSubTree.findIntersectingIntervals(q));
        }
		return result;
	}

	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}

