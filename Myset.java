class Myset{

  LinkedList lis = new LinkedList();

  public Boolean IsEmpty(){
    if(lis.head == null) return true;
    else return false;
  }

  public Boolean IsMember(Object o){
    Node temp = lis.head;
    while(temp != null){
      if(temp.data == o) {
        return true;
      }
      else{
        temp = temp.next;
      }
    }
    return false;
  }

  public void Insert(Object o){
    if(IsMember(o) == false){
      if(lis.head == null){
        lis.head = new Node(o);
      } else {
        Node temp = lis.head;
        while(temp.next != null){
          temp = temp.next;
        }
        temp.next = new Node(o);
      }
    }
  }

  public void Delete(Object o){
    try{
      if(IsMember(o) == true){
        Node temp = lis.head;
        if(temp.data == o){
          lis.head = lis.head.next;
        } else {
          while(temp.next != null){
            if(temp.next.data == o){
              temp.next = temp.next.next;
            } else {
              temp = temp.next;
            }
          }
        }
      } else {
        System.out.println(o + " not found");
      }
    } catch(Exception e){
      e.printStackTrace();
    }

  }

  public Myset Union(Myset a){
    Myset union = new Myset();
    Node temp1 = lis.head, temp2 = a.lis.head;

    while(temp2 != null){
      union.Insert(temp2.data);
      temp2 = temp2.next;
    }

    while(temp1 != null){
      if(union.IsMember(temp1.data)){
        temp1 = temp1.next;
      } else {
        union.Insert(temp1.data);
        temp1 = temp1.next;
      }
    }
    return union;
  }

  public Myset Intersection(Myset a){
    Myset inter = new Myset();
    Node temp1 = lis.head;

    while(temp1 != null){
      if(a.IsMember(temp1.data)){
        inter.Insert(temp1.data);
      }
      temp1 = temp1.next;
    }
    return inter;
  }

//prints myset
  public void printList(){
    Node printTemp = lis.head;
    System.out.print("{");
    while(printTemp != null){
      if(printTemp.next == null) System.out.print(printTemp.data);
      else System.out.print(printTemp.data + ",");
      printTemp = printTemp.next;
    }
    System.out.println("}");
  }

//ggives length of the linked list in myset
  public int length(){
    Node temp = lis.head;
    int count = 0;
    while(temp != null){
      count++;
      temp = temp.next;
    }
    return count;
  }

//inserts at a particular index in the linked list
  public void InsertAt(Object o, int i){
    if(IsMember(o) == false){
      Node temp = lis.head;
      if(i == 0){
        Node a = new Node(o);
        a.next = lis.head;
        lis.head = a;
      } else {
        for(int j=1;j<i;j++){
          temp = temp.next;
        }
        Node x = new Node(o);
        Node y = temp;
        x.next = y.next;
        temp.next = x;
      }
    }
  }

//gets the index of any particular node
  public int IndexOf(Object o){
    int count = 0;
    if(IsMember(o)){
      Node temp = lis.head;
      while(temp != null){
        if(temp.data == o){
          return count;
        }
        count++;
        temp = temp.next;
      }
      return -1;
    } else {
      return -1;
    }
  }

//prints the identitites of mobile phones stored in mobile phone set
  public void printIdentifier(){
    Node n = lis.head;
    if(n == null){
      System.out.println("mobile phone set is empty");
    }
    while(n != null){
      MobilePhone mp = (MobilePhone) n.data;
      System.out.println(mp.id);
      n = n.next;
    }
  }
}

//linked list class with some functions from myset explicitly for linked lists
class LinkedList{
  Node head;

  public Boolean IsMember(Object o){
    Node temp = head;
    while(temp != null){
      if(temp.data == o) {
        return true;
      }
      else{
        temp = temp.next;
      }
    }
    return false;
  }

  public int length(){
    Node temp = head;
    int count = 0;
    while(temp != null){
      count++;
      temp = temp.next;
    }
    return count;
  }

public void Insert(Object o){
  if(IsMember(o) == false){
    if(head == null){
      head = new Node(o);
    } else {
      Node temp = head;
      while(temp.next != null){
        temp = temp.next;
      }
      temp.next = new Node(o);
    }
  }
}

public void InsertAt(Object o, int i){
    if(IsMember(o) == false){
      Node temp = head;
      if(i == 0){
        Node a = new Node(o);
        a.next = head;
        head = a;
      } else {
        for(int j=1;j<i;j++){
          temp = temp.next;
        }
        Node x = new Node(o);
        Node y = temp;
        x.next = y.next;
        temp.next = x;
      }
    }
  }

  public int IndexOf(Object o){
    int count = 0;
    if(IsMember(o)){
      Node temp = head;
      while(temp != null){
        if(temp.data == o){
          return count;
        }
        count++;
        temp = temp.next;
      }
      return -1;
    } else {
      return -1;
    }
  }

  public void printList(){
    Node printTemp = head;
    while(printTemp != null){
      if(printTemp.next == null) System.out.print(((Exchange)printTemp.data).id);
      else System.out.print(((Exchange)printTemp.data).id + ",");
      printTemp = printTemp.next;
    }
  }
}

class Node{
 Object data;
 Node next;

  public Node(Object d){
   data = d;
   next = null;
  }

  public Node(Object d, Node n){
    data = d;
    next = n;
  }
}
