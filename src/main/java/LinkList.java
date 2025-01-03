import DS.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LinkList {

    public static void main(String[] args) {

    }

    //1. Inserting a node at last position in LinkedList
    Node insertAtEnd(Node head, int x) {
        if (head == null)
            return new Node(x);
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Node(x);
        return head;
    }

    //2. Delete Node in a Linked List (node is given and node is not last element)
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    //3. Find the length of the linkedlist
    public int getCount(Node head) {
        if (head == null)
            return 0;
        return 1 + getCount(head.next);
    }

    //4. Find element in linkedList
    static boolean searchKey(int n, Node head, int key) {
        for (int i = 0; i < n; i++, head = head.next) {
            if (head.data == key)
                return true;
        }
        return false;
    }

    //5. Insert a node in DLL
    DLLNode addNode(DLLNode head, int p, int x) {
        DLLNode curr = head;
        while (p > 0) {
            curr = curr.next;
            p--;
        }
        DLLNode newNode = new DLLNode(x);
        DLLNode next = curr.next;
        curr.next = newNode;
        newNode.prev = curr;
        if (next != null) {
            newNode.next = next;
            next.prev = newNode;
        }
        return head;
    }

    //6. Delete in a Doubly Linked List
    public DLLNode deleteNode(DLLNode head, int x) {
        DLLNode dummy = new DLLNode(-1);
        dummy.next = head;
        DLLNode curr = dummy;
        while (x > 0) {
            curr = curr.next;
            x--;
        }
        if (curr.prev == null) {
            curr.next.prev = null;
            return curr.next;
        }
        if (curr.next == null) {
            curr.prev.next = null;
            curr.prev = null;
            return head;
        }
        DLLNode next = curr.next;
        DLLNode prev = curr.prev;
        prev.next = next;
        next.prev = prev;
        return head;
    }

    //7. Reverse a Doubly Linked List
    public DLLNode reverseDLL(DLLNode head) {
        // Your code here
        DLLNode curr = head, prev = null, next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            curr.prev = next;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //8. Middle of the Linked List
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //9. Reverse a LinkedList [Recursive]
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode node = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return node;
    }

    //10. Reverse a LinkedList [Iterative]
    public ListNode reverseList1(ListNode head) {
        ListNode curr = head, prev = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //11. Detect a loop in LL
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    //12. Find the starting point of loop in LL
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    //13. Find length of Loop
    public int countNodesinLoop(Node head) {
        Node start = findNodeInLoop(head);
        if (start == null)
            return 0;

        Node it = start.next;
        int count = 1;
        while (start != it) {
            it = it.next;
            count++;
        }
        return count;
    }

    Node findNodeInLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return slow;
            }
        }
        return null;
    }

    //14.  check if ll is palindrome
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode slow = head, fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode head1 = head;
        ListNode head2 = reverse(slow.next);
        while (head1 != null && head2 != null) {
            if (head1.val != head2.val)
                return false;
            head1 = head1.next;
            head2 = head2.next;
        }
        return true;
    }

    ListNode reverse(ListNode curr) {
        ListNode prev = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //15. Segrregate odd and even nodes in LL
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode odd = head, even = head.next, evenHead = head.next;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    //16. Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return head;
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy, fast = dummy;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    //17. Delete the middle node of LL
    public ListNode deleteMiddle(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    //18. mergeSort on linkedList
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }

    ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode mid = findMid(head);

        ListNode lHead = head, rHead = mid.next;
        mid.next = null;

        lHead = mergeSort(lHead);
        rHead = mergeSort(rHead);

        return merge(lHead, rHead);
    }

    ListNode findMid(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode slow = head, fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    ListNode merge(ListNode head1, ListNode head2) {

        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;

        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                temp.next = head1;
                head1 = head1.next;
            } else {
                temp.next = head2;
                head2 = head2.next;
            }
            temp = temp.next;
        }

        if (head1 != null)
            temp.next = head1;
        else
            temp.next = head2;

        return dummy.next;
    }

    //19. Sort a LL of 0's 1's and 2's by changing links
    static Node segregate(Node head) {
        Node dummy0 = new Node(-1), dummy1 = new Node(-1), dummy2 = new Node(-1);
        Node temp0 = dummy0, temp1 = dummy1, temp2 = dummy2;

        while (head != null) {

            if (head.data == 0) {
                temp0.next = head;
                temp0 = temp0.next;
            } else if (head.data == 1) {
                temp1.next = head;
                temp1 = temp1.next;
            } else {
                temp2.next = head;
                temp2 = temp2.next;
            }

            head = head.next;
        }
        temp2.next = null;
        temp1.next = dummy2.next;
        temp0.next = dummy1.next;
        return dummy0.next;
    }

    //20. Find the intersection point of Y LL
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode h1 = headA, h2 = headB;

        while (h1 != h2) {
            h1 = h1 == null ? headB : h1.next;
            h2 = h2 == null ? headA : h2.next;
        }
        return h1;
    }

    //21. Add 1 to a number represented by LL
    public Node addOne(Node head) {
        head = reverse(head);
        int carry = 1;
        Node temp = head;
        while (temp != null) {
            int val = (temp.data + carry);
            carry = val / 10;
            temp.data = val % 10;
            if (temp.next == null)
                break;
            temp = temp.next;

        }
        if (carry > 0) {
            temp.next = new Node(carry);
        }
        return reverse(head);
    }

    Node reverse(Node curr) {
        Node prev = null, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //22. Add 2 numbers in LL
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        while (l1 != null || l2 != null || carry > 0) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }
            temp.next = new ListNode(carry % 10);
            temp = temp.next;
            carry = carry / 10;
        }
        return dummy.next;
    }

    //23. Delete all occurrences of a key in DLL
    static DLLNode deleteAllOccurOfX(DLLNode head, int x) {
        if (head == null)
            return head;
        head.next = deleteAllOccurOfX(head.next, x);
        if (head.data == x)
            return head.next;
        else if (head.next != null) {
            head.next.prev = head;
        }
        return head;
    }

    //24. Find pairs with given sum in sorted DLL
    public static ArrayList<ArrayList<Integer>> findPairsWithGivenSum(int target, DLLNode head) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (head == null || head.next == null)
            return res;
        DLLNode tail = findTail(head);

        while (head.data < tail.data) {
            int val = head.data + tail.data;
            if (val == target) {
                res.add(new ArrayList<>(Arrays.asList(head.data, tail.data)));
                tail = tail.prev;
            } else if (val > target) {
                tail = tail.prev;
            } else
                head = head.next;
        }
        return res;
    }

    static DLLNode findTail(DLLNode head) {
        while (head != null && head.next != null)
            head = head.next;
        return head;
    }

    //25. Remove duplicates from a sorted doubly linked list
    DLLNode removeDuplicates(DLLNode head) {
        if (head == null || head.next == null)
            return head;
        DLLNode next = removeDuplicates(head.next);
        if (next.data == head.data) {
            next.prev = null;
            return next;
        }

        head.next = next;
        next.prev = head;
        return head;
    }
    //26. Reverse LL in group of given size K
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null)
            return head;
        int k1 = k;
        ListNode curr = head, prev=null, next=null;

        while(k1 > 0 && curr != null){
            k1 --;
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        if(k1 > 0){
            return reverseKGroup(prev, k-k1);
        }else
            head.next = reverseKGroup(next, k);
        return prev;
    }
    //27. Rotate List
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k == 0)
            return head;
        ListNode temp = head;
        int size = 1;
        while(temp != null && temp.next != null){
            temp = temp.next;
            size++;
        }
        temp.next = head;
        k = k %size;
        int end = size - k;

        while(end > 0){
            end --;
            temp = temp.next;
        }
        head = temp.next;
        temp.next = null;
        return head;
    }
    //28. Flattening a Linked List
    DLL flatten(DLL root) {
        if(root == null || root.next == null)
            return root;

        root.next = flatten(root.next);
        return merge(root, root.next);
    }
    DLL merge(DLL root1, DLL root2){
        DLL dummy = new DLL(-1);
        DLL temp = dummy;
        while(root1 != null && root2 != null){
            if(root1.data <= root2.data){
                temp.bottom = root1;
                root1 = root1.bottom;
            }else {
                temp.bottom = root2;
                root2 = root2.bottom;
            }
            temp = temp.bottom;
        }

        if(root1 != null)
            temp.bottom = root1;
        if(root2 != null)
            temp.bottom = root2;
        return dummy.bottom;
    }
    //29. Clone a linked list with next and random pointer
    public DlLLRandom copyRandomList(DlLLRandom head) {
        DlLLRandom temp = head;
        while(temp != null){
            temp.next = new DlLLRandom(temp.val, temp.next);
            temp = temp.next.next;
        }

        temp = head;
        while(temp != null){

            if(temp.random != null){
                temp.next.random = temp.random.next;
            }
            temp = temp.next.next;
        }

        temp = head;
        DlLLRandom dummy = new DlLLRandom(-1);
        DlLLRandom res = dummy;
        while(temp != null){
            res.next = temp.next;
            res = res.next;
            temp.next = res.next;
            temp = temp.next;
        }
        return dummy.next;
    }
}
