package linkedlist;

import support.CycleInfo;
import support.LLNode;

public class LinkedListCycleAnalyzer {
    public static <T> CycleInfo detectCycleInfo(LLNode<T> head) {
        LLNode<T> tortoise = head, hare = head;

        //run around till t and h meet, or return -1 if hare hits null
        do {
            if (hare == null || hare.getLink() == null) return new CycleInfo(-1, 0);
            tortoise = tortoise.getLink();
            hare = hare.getLink().getLink();
        } while (tortoise != hare);

        //now they're at the same place, sent tortoise around till it meets hare again, to count cycle length
        int cycleLength = 0;
        do {
            tortoise = tortoise.getLink();
            cycleLength++;
        } while (tortoise != hare);

        //hare still at meeting point, send tortoise back to head and have them both move one space at a time,
        //rounds it takes to  meet up should equal distance from head to start of cycle = index of cycle entry point
        int entryIndex = 0;
        tortoise = head;
        while (tortoise != hare) {
            tortoise = tortoise.getLink();
            hare = hare.getLink();
            entryIndex++;
        }
        return new CycleInfo(entryIndex,  cycleLength);
    }
}
