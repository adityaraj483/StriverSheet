class Node{
    int data;
    Node next;
    Node(int x){
        data = x;
        next = null;
    }
}
class Node2
{
    int data;
    Node2 next;
    Node2 prev;
    Node2(int data)
    {
        this.data = data;
        next = prev = null;
    }
}
class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
      }
}
public class LinkList {

    public static void main(String[] args) {

    }
    //1. Inserting a node at last position in LinkedList
    Node insertAtEnd(Node head, int x) {
        if(head == null)
            return new Node(x);
        Node temp = head;
        while(temp.next != null){
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
        if(head == null)
            return 0;
        return 1+ getCount(head.next);
    }
    //4. Find element in linkedList
    static boolean searchKey(int n, Node head, int key) {
        for(int i=0;i<n;i++, head=head.next){
            if(head.data == key)
                return true;
        }
        return false;
    }
    //5. Insert a node in DLL
    Node2 addNode(Node2 head, int p, int x) {
        Node2 curr = head;
        while(p>0){
            curr = curr.next;
            p--;
        }
        Node2 newNode = new Node2(x);
        Node2 next = curr.next;
        curr.next = newNode;
        newNode.prev = curr;
        if(next != null){
            newNode.next = next;
            next.prev = newNode;
        }
        return head;
    }
    //6. Delete in a Doubly Linked List
    public Node2 deleteNode(Node2 head, int x) {
        Node2 dummy = new Node2(-1);
        dummy.next = head;
        Node2 curr = dummy;
        while(x>0){
            curr = curr.next;
            x--;
        }
        if(curr.prev == null){
            curr.next.prev = null;
            return curr.next;
        }
        if(curr.next == null){
            curr.prev.next = null;
            curr.prev = null;
            return head;
        }
        Node2 next = curr.next;
        Node2 prev = curr.prev;
        prev.next = next;
        next.prev = prev;
        return head;
    }
    //7. Reverse a Doubly Linked List
    public Node2 reverseDLL(Node2 head) {
        // Your code here
        Node2 curr = head, prev = null, next = null;

        while(curr != null){
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
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    //9. Reverse a LinkedList [Recursive]
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;

        ListNode node = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return node;
    }
    //10. Reverse a LinkedList [Iterative]
    public ListNode reverseList1(ListNode head) {
        ListNode curr = head, prev = null, next = null;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    //11. Detect a loop in LL
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null)
            return false;
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast)
                return true;
        }
        return false;
    }
    //12. Find the starting point of loop in LL
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                slow = head;
                while(slow != fast){
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
        if(start == null)
            return 0;

        Node it = start.next;
        int count = 1;
        while(start != it){
            it = it.next;
            count ++;
        }
        return count;
    }
    Node findNodeInLoop(Node head){
        Node slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                return slow;
            }
        }
        return null;
    }
    //14.

}
