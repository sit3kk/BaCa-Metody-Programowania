//Konrad Sitek - 4



import java.util.Scanner;

class Source {

    public static Scanner scan = new Scanner(System.in);


    public static boolean lexic(int i, int j, int k, int l, int iCurr,int jCurr, int kCurr, int lCurr) //Funkcja sprawdzajaca czy ciag jest leksykograficzny
    {
        if(i<iCurr) return true;
        else if(iCurr>i) return false;
        else
        {
            if(j<jCurr) return true;
            else if(jCurr<j) return false;
            else
            {
                if(k<kCurr) return true;
                else if(kCurr<k) return false;
                else
                {
                    if(l < lCurr) return true;
                    else if(lCurr < l) return false;
                    else return false;
                }
            }
        }

    }



    public static void maxSumRectangle(int[][] tab,int nz)
    {
        int maxSum = tab[0][0];
        int[] mst = new int[tab.length+1];
        int elements = 0;
        int maxLeft = 0, maxRight = 0;
        int maxTop = 0, maxBottom = 0;


        for(int left = 0 ; left < tab[0].length ; left++) //Petla wyznaczajace skrajnie lewa komorke
        {
            for(int right = left ; right < tab[0].length ; right++) //Petla wychodzaca od skrajnie lewej komorki do konca wiersza
            {
                for(int row = 0 ; row < tab.length ; row++) //Petla przechodzaca po kazdym wierszu
                {
                    mst[row]+=tab[row][right]; //Sumowanie komorek dla kazdego wiersza

                    if(tab[row][right] > maxSum) //Wyznaczanie nowej maksymalnej komorki
                    {
                        maxSum = tab[row][right];
                        maxLeft = left;
                        maxRight = right;
                        maxTop = row;
                        maxBottom = row;
                        elements = (right - left + 1); //Liczba elementow
                    }
                }

                int maxEnd = 0 , s = 0; //Algorytm kadane
                for(int row = 0 ; row < tab.length ; row++)
                {
                    maxEnd += mst[row]; //Sumowanie wierszy
                    if(maxEnd > maxSum) //Jesli suma wieksza od maxa przypisanie nowej wartosci
                    {
                        maxSum = maxEnd;
                        maxLeft = left;
                        maxRight = right;
                        maxTop = s;
                        maxBottom = row;
                        elements = (row-s+1)*(right-left+1); //Liczba elementow
                    }
                    else if(maxEnd == maxSum ) //Jezli max rowny sumie sprawdzenie liczby elementow i ciagu leksykograficznego
                    {

                        if((row-s+1)*(right-left+1) < elements)
                        {
                            maxSum = maxEnd;
                            maxLeft = left;
                            maxRight = right;
                            maxTop = s;
                            maxBottom = row;
                            elements = (row-s+1)*(right-left+1); //Liczba elementow

                        }
                        else if((row-s+1)*(right-left+1) == elements &&  lexic(row,row,left,right,maxTop,maxBottom,maxLeft,maxRight))
                        {


                                maxSum = maxEnd;
                                maxLeft = left;
                                maxRight = right;
                                maxTop = s;
                                maxBottom = row;
                                elements = (row-s+1)*(right-left+1); //Liczba elementow

                        }
                    }
                    if(maxEnd <= 0)
                    {
                        maxEnd = 0;
                        s = row + 1;
                    }

                    if(right == tab[0].length-1) mst[row] = 0; //Zerowanie tablicy sumujacej
                }
            }

        }

        //Wypisanie wyniku
        System.out.println(nz+":"+" n = "+ tab.length + " m = " + tab[0].length+", s = "+maxSum+", mst = a["+maxTop+ ".."+maxBottom+"]["+maxLeft+".."+maxRight+"]");

    }


    public static void main(String[] args) {


       //System.out.println(lexic(0,0,0,0,0,0,0,1));

        int set_num = scan.nextInt(); //Pobranie ilosci zestawow od uzytkownika

        for (int set = 0; set < set_num; set++) {

            boolean isempty = true;
            int nz = scan.nextInt();
            scan.next().charAt(0);

            int n = scan.nextInt();
            int m = scan.nextInt();


            int[][] tab = new int[n][m];


            for (int row = 0; row < n; row++) {
                for (int cell = 0; cell < m; cell++) {
                    tab[row][cell] = scan.nextInt();

                    if (tab[row][cell] >= 0) isempty = false; //Sprawdzanie czy tablica zawiera nieujemne liczby
                }
            }

            if (isempty == true) //Wynik jezeli tablica zawierala tylko liczby ujemne
            {
                System.out.println(nz + ":" + " n = " + n + " m = " + m + ", s = 0, mst is empty");
            } else //Wynik jezeli tablica zawierala nieujemne liczby
            {
                maxSumRectangle(tab,nz);
            }
        }

    }

}

/*/
input/output
6
1 : 2 2
1 2
3 4
2 : 3 3
-1 -2 -3
-4 -5 -6
-7 -8 9
3 : 5 1
-1 1 -2 2 5
4 : 3 3
0 0 0
0 0 1
0 0 1
5 : 4 4
-1 -1 -1 -1
-1  1  1 -1
-1  1  1 -1
-1 -1 -1 -1
6 : 4 4
0 0 0 0
0 0 1 2
0 0 0 2
0 0 0 0




1: n = 2 m = 2, s = 10, mst = a[0..1][0..1]
2: n = 3 m = 3, s = 9, mst = a[2..2][2..2]
3: n = 5 m = 1, s = 7, mst = a[3..4][0..0]
4: n = 3 m = 3, s = 2, mst = a[1..2][2..2]
5: n = 4 m = 4, s = 4, mst = a[1..2][1..2]
6: n = 4 m = 4, s = 5, mst = a[1..2][2..3]




 */