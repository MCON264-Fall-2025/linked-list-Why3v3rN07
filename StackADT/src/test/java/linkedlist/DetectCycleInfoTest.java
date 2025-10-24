package linkedlist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.CycleInfo;
import support.LLNode;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetectCycleInfoTest  {
    private LLNode<Integer> build(int[] vals, int pos) {
        if (vals.length == 0) return null;
        LLNode<Integer> head = new LLNode<>(vals[0]);
        LLNode<Integer> tail = head, entry = (pos == 0 ? head : null);
        for (int i = 1; i < vals.length; i++) {
            tail.setLink(new LLNode<>(vals[i]));
            tail = tail.getLink();
            if (i == pos) entry = tail;
        }
        if (pos >= 0) tail.setLink(entry); // create cycle
        return head;
    }

    @DisplayName("Contains cycle - odd")
    @Test void oddCycle() {
        LLNode<Integer> head = build(new int[]{3,2,0,-4}, 1);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(1, info.entryIndex);
        assertEquals(3, info.cycleLength);
    }

    @DisplayName("Contains cycle - even")
    @Test void evenCycle() {
        LLNode<Integer> head = build(new int[]{4,-8,0,-2}, 2);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(2, info.entryIndex);
        assertEquals(2, info.cycleLength);
    }

    @DisplayName("Cycle from index 0")
    @Test void example2() {
        LLNode<Integer> head = build(new int[]{1,2}, 0);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(0, info.entryIndex);
        assertEquals(2, info.cycleLength);
    }

    @DisplayName("No cycle")
    @Test void example4_noCycle() {
        LLNode<Integer> head = build(new int[]{1,3,2,4}, -1);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(-1, info.entryIndex);
        assertEquals(0, info.cycleLength);
    }

    @DisplayName("No cycle - single")
    @Test void example3_noCycle() {
        LLNode<Integer> head = build(new int[]{1}, -1);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(-1, info.entryIndex);
        assertEquals(0, info.cycleLength);
    }

    @DisplayName("Cycle - single")
    @Test void selfLoop() {
        LLNode<Integer> head = build(new int[]{7}, 0);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(0, info.entryIndex);
        assertEquals(1, info.cycleLength);
    }

    @DisplayName("Cycle - single")
    @Test void longList() {
        int[] longList = new int[100000];
        for (int i = 0; i < 100000; i++) { longList[i] = i; }
        LLNode<Integer> head = build(longList, 50000);
        CycleInfo info = LinkedListCycleAnalyzer.detectCycleInfo(head);
        assertEquals(50000, info.entryIndex);
        assertEquals(50000, info.cycleLength);
    }
}
