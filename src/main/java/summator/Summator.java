package summator;

import data.ListNode;

/**
 * My decision for task https://leetcode.com/problems/add-two-numbers/submissions/
 */
public class Summator {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        int remainder = 0;
        ListNode head = l1;
        ListNode prevL1Node = l1;

        while (l1 != null) {
            int accumulator = l1.val + l2.val + remainder;
            if (accumulator > 9) {
                l1.val = accumulator % 10;
                remainder = accumulator / 10;
            } else {
                l1.val = accumulator;
                remainder = 0;
            }

            prevL1Node = l1;
            if (l1.next == null && l2.next != null) {
                l1.next = new ListNode(0);
            }

            if (l2.next == null && prevL1Node.next != null) {
                l2.next = new ListNode(0);
            }

            l1 = l1.next;
            l2 = l2.next;
        }

        if (remainder > 0) {
            prevL1Node.next = new ListNode(remainder);
        }

        return head;
    }
}
