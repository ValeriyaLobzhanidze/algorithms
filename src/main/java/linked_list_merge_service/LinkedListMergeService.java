package linked_list_merge_service;

import data.ListNode;

import java.util.Arrays;

/**
 * My decision for task https://leetcode.com/problems/merge-k-sorted-lists/
 */
public class LinkedListMergeService {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;

        } else if (lists.length == 1) {
            return lists[0];

        } else if (lists.length == 2) {
            return merge(lists[0], lists[1]);

        } else {
            int splitIndex = lists.length / 2;
            ListNode[] firstPart = Arrays.copyOfRange(lists, 0, splitIndex);
            ListNode[] secondPart = Arrays.copyOfRange(lists, splitIndex, lists.length);
            return merge(mergeKLists(firstPart), mergeKLists(secondPart));
        }
    }

    private ListNode merge(ListNode first, ListNode second) {
        if (first == null) {
            return second;
        }

        if (second == null) {
            return first;
        }

        ListNode head = null;
        ListNode tail = null;

        while (first != null && second != null) {

            ListNode minNode = first.val <= second.val ? first : second;
            ListNode newNode = new ListNode(minNode.val);

            if (head == null) {
                head = newNode;
                tail = head;
            } else {
                tail.next = newNode;
                tail = tail.next;
            }

            if (first == minNode) {
                first = first.next;
            } else {
                second = second.next;
            }
        }

        if (first != null) {
            tail.next = first;
        } else {
            tail.next = second;
        }

        return head;
    }
}
