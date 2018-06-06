import java.util.LinkedList;
import java.util.Stack;

public class LinkedListImpl {

    private Node head;

    private class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;

        }

    }



    public static Node reverseLinkedList(Node head) {
        Node prev = null;
        Node curr = head;
        Node next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;

        }
        head = prev;
        return head;

    }

    public static Node groupOddEvenLL(Node head) {
        Node newHead = head;
        Node oddHead = head;
        Node evenHead = head.next;
        Node connect = head.next;
        while (oddHead != null && evenHead != null && evenHead.next != null) {
            oddHead.next = evenHead.next;
            oddHead = oddHead.next;

            evenHead.next = oddHead.next;
            evenHead = evenHead.next;

        }
        oddHead.next = connect;
        return newHead;

    }

    public static void displayLinkedlist(Node head) {
        System.out.println("");

        System.out.print("Head -->");

        while (head != null) {
            System.out.print(" -> ");
            System.out.print(head.data);
            head = head.next;
        }
    }

    public static Node findLoopStart(Node headerLink) {
        Node slowLink = headerLink, fastLink = headerLink;
        if (headerLink == null) {
            return null;
        }
        while (true) {
            slowLink = slowLink.next;
            if (fastLink.next != null) {
                fastLink = fastLink.next.next;
            } else {
                return null;
            }
            // slowLink and fastLink collide
            if (slowLink == fastLink) {
                break;
            }
            if (slowLink == null || fastLink == null) {
                return null;
            }
        }
        // move slowLink to headerLink
        slowLink = headerLink;

        // Now move slowLink as well as fastLink by one jump at a time.
        // The collision point is the start of the loop.
        while (true) {
            if (slowLink == fastLink) {
                return slowLink;
            }
            slowLink = slowLink.next;
            fastLink = fastLink.next;
        }
    }

    public static boolean hasLoop(Node headerLink) {
        Node slowLink = headerLink, fastLink = headerLink;
        if (headerLink == null) {
            return false;
        }
        while (true) {
            slowLink = slowLink.next;
            if (fastLink.next != null) {
                fastLink = fastLink.next.next;
            } else {
                return false;
            }
            if (slowLink == fastLink) {
                return true;
            }
            if (slowLink == null || fastLink == null) {
                return false;
            }
        }
    }


    public static Node addTwoNumbers(Node l1, Node l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();

        while(l1 != null) {
            s1.push(l1.data);
            l1 = l1.next;
        };
        while(l2 != null) {
            s2.push(l2.data);
            l2 = l2.next;
        }

        int sum = 0;
        LinkedListImpl ll = new LinkedListImpl();
        Node list = ll.new Node(0);
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) sum += s1.pop();
            if (!s2.empty()) sum += s2.pop();
            list.data = sum % 10;
            Node head = ll.new Node(sum/10);
            head.next = list;
            list = head;
            sum /= 10;
        }

        return list.data == 0 ? list.next : list;
    }

    public static Node swapPairs(Node head) {
        if ((head == null)||(head.next == null))
            return head;
        Node n = head.next;
        head.next = swapPairs(head.next.next);
        n.next = head;
        return n;
    }


    public static void main(String[] args) {

        LinkedListImpl ll = new LinkedListImpl();

        Node n1 = ll.new Node(1);
        Node n2 = ll.new Node(2);
        Node n3 = ll.new Node(3);
        Node n4 = ll.new Node(4);
        Node n5 = ll.new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        ll.displayLinkedlist(n1);
        ll.displayLinkedlist(ll.groupOddEvenLL(n1));
        ll.displayLinkedlist(ll.reverseLinkedList(n1));


    }

}
