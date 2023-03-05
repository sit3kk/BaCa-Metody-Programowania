//Konrad Sitek - 4

import java.util.Scanner;



/*/

Operacje na elementach listy z wykorzystaniem listy cyklicznej
Funckja reverse zamienia pierwszy z osatnim elementem, calkowita zamiana wykonuje sie podczas uzycia funkcji display



 */


public class Source
{
    public static class DLL
    {
        Node first;
        int size = 0;

        DLL()
        {
            first = null;
            size = 0;
        }

        class Node
        {
            String name;
            Node next;
            Node prev;
        }

        void insertFirst(String name_)
        {
            Node newNode = new Node();
            newNode.name = name_;

            newNode.next = null;
            newNode.prev = null;

            if(first == null) //Jezeli lista pusta zastap glowe
            {
                first = newNode;
                newNode.next = first;
                newNode.prev = first;
            }
            else
            {


                if(first.next.prev != first) //Naprawa po revers
                {
                    Node tmp = first.next;
                    Node tmp2 = tmp.next;
                    tmp.next = tmp.prev;
                    tmp.prev = tmp2;
                }

                //Dodanie nowego wagonu
                Node tmp = new Node();
                tmp = first.prev;

                tmp.next = newNode;
                newNode.prev = tmp;
                newNode.next = first;
                first.prev = newNode;
                first = newNode;

            }

            size++;


        }


        void insertLast(String name_)
        {
            Node newNode = new Node();
            newNode.name = name_;

            newNode.next = null;
            newNode.prev = null;



            if(first == null) // Jezeli lista pusta
            {
                first = newNode;
                newNode.next = first;
                newNode.prev = first;
            }
            else
            {

                //Naprawa po reverse
                if(first.next.prev != first)
                {
                    Node tmp = first.next;
                    Node tmp2 = tmp.next;
                    tmp.next = tmp.prev;
                    tmp.prev = tmp2;
                }


                Node tmp = new Node();
                tmp = first;


                if(first.next == first) //Jezeli jeden element
                {
                    first.next = newNode;
                    first.prev = newNode;
                    newNode.prev = first;
                    newNode.next = first;
                }
                else //Jezeli wiecej niz jeden
                {

                    first.prev.next = newNode;
                    newNode.prev = first.prev;
                    newNode.next = first;
                    first.prev = newNode;
                }


            }
            size++;


        }


        void display()
        {
            Node tmp = first;

            for(int i = 0 ; i < size ; i++)
            {

                if(tmp.next.prev != tmp) //Naprawa po reverse
                {

                    System.out.print(tmp.name + " ");
                    tmp = tmp.next;
                    Node tmp2 = tmp.next;
                    tmp.next = tmp.prev;
                    tmp.prev = tmp2;

                }
                else
                {
                    System.out.print(tmp.name + " ");
                    tmp = tmp.next;

                }

            }

        }


        void reverse()
        {
            Node w1 = first;
            Node w2 =  first.next;
            first = first.prev;

            Node w3 = first.prev;

            w1.next = first;
            w1.prev = w2;


            first.prev = w1;
            first.next = w3;


        }


        void delFirst()
        {

            if(first != null)
            {


                if(first.next == first)
                {
                    first = null;
                }
                else
                {

                    Node tmp = first;
                    Node firstNode = first;
                    tmp = first.prev;
                    first = first.next;
                    first.prev = tmp;
                    tmp.next = first;
                    firstNode = null;
                }

                size--;
            }
        }

        void delLast()
        {
            if(first!=null)
            {
                if(first.next == first)
                {
                    first = null;
                }
                else
                {

                    first = first.prev; //Przestawienie na pociag poprzedni i usuniecie

                    delFirst();
                }
            }

        }

    }

    public static class SLL
    {

        public Node trains;

        SLL()
        {
            trains = null;
        }

        class Node
        {
            String name;
            Node next;

            Node()
            {
                next = null;
            }

            public DLL cars = new DLL();

            public void display()
            {
                cars.display();
            }//Wypisanie wagonow
        }


        void insert(String name_) //Wprowadzenie nowego pociagu
        {
            Node tmp = new Node();
            tmp.name = name_;

            if(trains != null) tmp.next = trains;

            trains = tmp;
        }

        public Node findObj(String name_) //Znalezienie obiektu za pomoca id
        {
            Node tmp = trains;

            while(tmp != null)
            {
                if(tmp.name.equals(name_)) return tmp;
                tmp = tmp.next;
            }

            return null;
        }


        public void delById(Node obj) //Usuniecie pustego pociagu z listy
        {

            Node tmp1 = trains , tmp2 = trains;
            while (tmp2 != obj) {
                tmp1 = tmp2;
                tmp2 = tmp2.next;
            }
            if (obj == trains) trains = obj.next;
            else tmp1.next = obj.next;

        }


        void print() //Wypisanie pociagu
        {
            Node tmp = trains;

            if(tmp == null) return;

            while(tmp != null)
            {
                if(tmp.cars.first != null) System.out.print(tmp.name + " ");
                tmp = tmp.next;
            }
        }


    }




    public static void main(String[] args)
    {

        Scanner input = new Scanner(System.in);


        int z = Integer.parseInt(input.nextLine());


        for(int i = 0; i < z ; i++)
        {
            int n = Integer.parseInt(input.nextLine());
            SLL list = new SLL();

            if(n>=1 && n<=1000000)
            {
                for(int j = 0 ; j < n ; j++)
                {

                    String[] command = input.nextLine().split(" ");
                    SLL.Node tmp = null;

                    if(command.length == 3)
                    {
                        SLL.Node tmp2 = list.findObj(command[2]);
                        tmp = list.findObj(command[1]);

                        if(command[0].equals("New"))
                        {
                            if(tmp == null)
                            {
                                list.insert(command[1]);
                                list.trains.cars.insertFirst(command[2]);
                            }
                            else
                            {
                                System.out.println("Train " + command[1] + " already exists");
                            }

                        }
                        else if(command[0].equals("InsertFirst"))
                        {
                            if(tmp == null)
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }
                            else
                            {
                                tmp.cars.insertFirst(command[2]);
                            }

                        }
                        else if(command[0].equals("InsertLast"))
                        {
                            if(tmp == null)
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }
                            else
                            {
                                tmp.cars.insertLast(command[2]);
                            }

                        }
                        else if(command[0].equals("Union"))
                        {
                            if(tmp == null)
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }
                            else if(list.findObj(command[2]) == null)
                            {
                                System.out.println("Train " + command[2] + " does not exist");
                            }
                            else
                            {

                                //Zamiana referencji

                                DLL.Node w1 = tmp.cars.first;
                                DLL.Node w2 = tmp.cars.first.prev;
                                DLL.Node w3 = tmp2.cars.first;
                                DLL.Node w4 = tmp2.cars.first.prev;

                                w2.next = w3;
                                w3.prev = w2;

                                w1.prev = w4;
                                w4.next = w1;



                                tmp.cars.size += tmp2.cars.size;
                                list.delById(tmp2); //Usuniecie jezeli pusty

                            }
                        }
                        else if(command[0].equals("DelFirst"))
                        {
                            if(list.findObj(command[1]) == null)
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }
                            else if(list.findObj(command[2]) != null)
                            {
                                System.out.println("Train " + command[2] + " already exists");
                            }
                            else
                            {
                                list.insert(command[2]);
                                list.trains.cars.insertFirst(tmp.cars.first.name);
                                tmp.cars.delFirst();
                                if(tmp.cars.first == null) list.delById(tmp);

                            }

                        }
                        else if(command[0].equals("DelLast"))
                        {
                            if(list.findObj(command[1]) == null)
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }
                            else if(list.findObj(command[2]) != null)
                            {
                                System.out.println("Train " + command[2] + " already exists");
                            }
                            else
                            {
                                list.insert(command[2]);
                                list.trains.cars.insertFirst(tmp.cars.first.prev.name);
                                tmp.cars.delLast();
                                if(tmp.cars.first == null) list.delById(tmp);

                            }


                        }

                    }
                    else if(command.length == 2)
                    {
                        tmp = list.findObj(command[1]);


                        if(command[0].equals("Display"))
                        {
                            if(tmp!= null)
                            {
                                System.out.print(command[1]+": ");
                                tmp.cars.display();
                                System.out.println();
                            }
                            else
                            {
                                System.out.println("Train " + command[1] + " does not exist");
                            }

                        }
                        else if(command[0].equals("Reverse"))
                        {
                            tmp.cars.reverse();

                        }
                    }
                    else if(command.length == 1 && command[0].equals("Trains"))
                    {
                        System.out.print("Trains: ");
                        list.print();
                        System.out.println();

                    }

                }
            }
        }

    }
}


/*/

1
65
New T1 W2
InsertLast T1 W3
InsertFirst T1 W1
Display T1
New T2 W6
InsertLast T2 W5
InsertLast T2 W4
Display T2
Reverse T2
Display T2
Trains
Union T1 T2
Trains
Display T1
Display T2
Reverse T1
Display T1
Reverse T1
Display T1
New T2 W7
InsertFirst T2 W8
Reverse T2
Display T2
Union T1 T2
Trains
Display T1
InsertFirst T1 W0
Display T1
InsertLast T1 W9
Display T1
Reverse T1
Display T1
Reverse T1
Display T1
New T3 W10
InsertLast T3 W11
InsertLast T3 W12
InsertLast T3 W13
Union T3 T1
Trains
Display T3
Reverse T3
Display T3
DelFirst T3 T1
DelLast T3 T2
Trains
Display T1
Display T2
Display T3
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
DelLast T3 T1
Display T3
Union T1 T3
Union T2 T1
Trains
Display T2
Reverse T2
Display T2





T1: W1 W2 W3
T2: W6 W5 W4
T2: W4 W5 W6
Trains: T2 T1
Trains: T1
T1: W1 W2 W3 W4 W5 W6
Train T2 does not exist
T1: W6 W5 W4 W3 W2 W1
T1: W1 W2 W3 W4 W5 W6
T2: W7 W8
Trains: T1
T1: W1 W2 W3 W4 W5 W6 W7 W8
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
T1: W9 W8 W7 W6 W5 W4 W3 W2 W1 W0
T1: W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
Trains: T3
T3: W10 W11 W12 W13 W0 W1 W2 W3 W4 W5 W6 W7 W8 W9
T3: W9 W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11 W10
Trains: T2 T1 T3
T1: W9
T2: W10
T3: W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
Train T1 already exists
T3: W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
Trains: T2
T2: W10 W9 W8 W7 W6 W5 W4 W3 W2 W1 W0 W13 W12 W11
T2: W11 W12 W13 W0 W1 W2 W3 W4 W5 W6 W7 W8 W9 W10

 */